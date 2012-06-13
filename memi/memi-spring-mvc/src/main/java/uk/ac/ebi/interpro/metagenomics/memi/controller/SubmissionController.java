package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SubmissionForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.EmailNotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SubmissionModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SubmissionViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents the controller for the submission forms.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping('/' + SubmissionController.VIEW_NAME)
public class SubmissionController extends CheckLoginController implements IController, HandlerExceptionResolver {

    private final static Log log = LogFactory.getLog(SubmissionController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "submit";

    public static final String SUCCESS_VIEW_NAME = "submitSuccess";

    @Resource(name = "emailNotificationServiceSubmitPage")
    private INotificationService emailService;

    private final String DISPLAY_PARAM = "?display=true";

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    public ModelAndView doGet(ModelMap model) {
        if (isUserAssociatedToSession()) {
            return buildModelAndView(model, false);
        } else {
            return new ModelAndView("redirect:" + LoginPageController.VIEW_NAME + DISPLAY_PARAM);
        }
    }

    private ModelAndView buildModelAndView(ModelMap model, boolean isMaxSizeError) {
        final ModelPopulator modelPopulator = new SubmissionModelPopulator();
        model.addAttribute(SubmissionForm.MODEL_ATTR_NAME, new SubmissionForm());
        model.addAttribute("isMaxSizeError", isMaxSizeError);
        return buildModelAndView(getModelViewName(), model, modelPopulator);
    }

    @RequestMapping(value = "/doOpenTemplateDownload", method = RequestMethod.GET)
    public ModelAndView doOpenTemplateDownload(final ModelMap model, final HttpServletResponse response,
                                               final HttpServletRequest request) {
        return handleDownload(model, response, request);
    }

    private ModelAndView handleDownload(ModelMap model, HttpServletResponse response, HttpServletRequest request) {
        ClassPathResource resource = new ClassPathResource("/unfiltered/ERAMetadataVc5.2.xls");
        try {
            if (downloadService != null) {
                File file = resource.getFile();
                downloadService.openDownloadDialog(response, request, file, file.getName(), false);
            }
        } catch (IOException e) {
            log.warn("Couldn't get file from the following resource - " + resource.getPath(), e);
        }
        return buildModelAndView(model, false);
    }

    //    @RequestMapping(params = "submit", method = RequestMethod.POST)
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doPost(@ModelAttribute(SubmissionForm.MODEL_ATTR_NAME) @Valid SubmissionForm subForm, BindingResult result,
                               ModelMap model, SessionStatus status) {
        if (isUserAssociatedToSession()) {
            final ModelPopulator modelPopulator = new SubmissionModelPopulator();
            modelPopulator.populateModel(model);
            if (result.hasErrors()) {
                log.info("Submission form still has validation errors!");
                model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
                return new ModelAndView(VIEW_NAME, model);
            }
            if (subForm != null) {
                boolean isAttachmentStored = false;
                String pathName;
                CommonsMultipartFile commonsFile = subForm.getAttachment();
                String originalFileName = commonsFile.getOriginalFilename();
                String storedFileName = buildNewFileName(originalFileName);
                if (commonsFile.getFileItem() != null && commonsFile.getName().length() > 0 && commonsFile.getSize() > 0) {
                    pathName = buildFilePathName(storedFileName);
                    isAttachmentStored = writeFileToDisk(commonsFile, pathName);
                }
                String msg = buildMsg(subForm, isAttachmentStored, originalFileName, storedFileName);
                String sender = sessionManager.getSessionBean().getSubmitter().getEmailAddress();
                ((EmailNotificationService) emailService).setSender(sender);
                ((EmailNotificationService) emailService).setReceiverCC(sender);
                ((EmailNotificationService) emailService).setEmailSubject("EMG-SUB: " + subForm.getSubTitle());
                ((EmailNotificationService) emailService).setEmailSubject("EMG-SUB: " + subForm.getSubTitle());
                emailService.sendNotification(msg);
                log.info("Sent an email with hibernate submission details: " + msg);
                status.setComplete();
            } else {
                return new ModelAndView(CommonController.EXCEPTION_PAGE_VIEW_NAME);
            }
            model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
            return new ModelAndView(SUCCESS_VIEW_NAME, model);
        } else {
            return new ModelAndView("redirect:" + LoginPageController.VIEW_NAME + DISPLAY_PARAM);
        }
    }

    /**
     * Resolves MaxUploadSizeExceeded exceptions. This method is called before any Spring request method is called. So we lose users input unfortunately.
     * TODO: Try to use Spring's BindingResult object for a more consistent error handling.
     *
     * @param request
     * @param response
     * @param object
     * @param exception
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object object, Exception exception) {
        Object test = request.getAttribute(BindingResult.MODEL_KEY_PREFIX + "subTitle");
        ModelMap model = new ModelMap();
        if (exception instanceof MaxUploadSizeExceededException) {
            long megabytes = ((MaxUploadSizeExceededException) exception).getMaxUploadSize() / 1024 / 1024;
            model.put("attachment", "File size should be less then " + megabytes + " megabytes.");
        }
        return buildModelAndView(model, true);
    }

    private String buildNewFileName(String originalFileName) {
        long time = Calendar.getInstance().getTimeInMillis();
        //add time to 1 year in advance
        time = time + 1000L * 60L * 60L * 24L * 365L * 2L;
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        Random randomGenerator = new Random();

        return new StringBuilder(formatter.format(new Date(time))).
                append("_").
                append(randomGenerator.nextInt(1000)).
                append("_").
                append(originalFileName).toString();
    }

    private String buildFilePathName(String fileName) {
        return new StringBuilder(propertyContainer.getPathToSubmissionDirectory()).
                append(fileName).toString();
    }

    private boolean writeFileToDisk(CommonsMultipartFile attachment, final String pathName) {
        FileItem fileItem = attachment.getFileItem();
        if (fileItem != null) {
            try {
                fileItem.write(new File(pathName));
                return true;
            } catch (Exception e) {
                log.warn("Couldn't write attached file with name " + fileItem.getName() + " to the file system!", e);
            }
        }
        return false;
    }


    /**
     * Builds the email message from the submission form using Velocity..
     *
     * @param subForm Submission form object from which the user input will be read out.
     * @return The email message as String representation.
     */
    protected String buildMsg(SubmissionForm subForm, final boolean isAttachmentStored, final String originalFileName,
                              final String storedFileName) {
        Map<String, Object> model = new HashMap<String, Object>();

        //Add submission form to Velocity model
        model.put("subForm", subForm);
        model.put("attachment", (isAttachmentStored ? originalFileName + " (" + storedFileName + ")" : "No file provided!"));

        //Add logged in user to Velocity model
        if (sessionManager != null && sessionManager.getSessionBean() != null) {
            model.put("submitter", sessionManager.getSessionBean().getSubmitter());
        }
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "submission-confirmation.vm", model);
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Submit", "Submit new data", VIEW_NAME));
        return result;
    }

    class SubmissionModelPopulator implements ModelPopulator {
        @Override
        public void populateModel(ModelMap model) {
            final ViewModelBuilder<SubmissionModel> builder = new SubmissionViewModelBuilder(sessionManager, "Submit data - EBI metagenomics", getBreadcrumbs(null), propertyContainer);
            final SubmissionModel submissionViewModel = builder.getModel();
            submissionViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SUBMIT_VIEW);
            model.addAttribute(ViewModel.MODEL_ATTR_NAME, submissionViewModel);
        }
    }

}
