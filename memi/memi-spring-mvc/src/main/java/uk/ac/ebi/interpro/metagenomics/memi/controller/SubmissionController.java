package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.io.IOUtils;
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
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.apro.CountryDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SRARegistrationForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.EmailNotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SRARegistrationModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SubmissionModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SRARegistrationViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SubmissionViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
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

    @Resource(name = "emailNotificationServiceSRARegistration")
    private INotificationService emailService;

    private final String DISPLAY_PARAM = "?display=true";

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @Resource
    private CountryDAO countryDAO;

    @Resource
    private SubmissionContactDAO submissionContactDAO;

    public ModelAndView doGet(ModelMap model) {
        if (isUserAssociatedToSession()) {
            return buildModelAndView(model, false);
        } else {
            return new ModelAndView("redirect:" + LoginPageController.VIEW_NAME + DISPLAY_PARAM);
        }
    }

    private ModelAndView buildModelAndView(ModelMap model, boolean isMaxSizeError) {
//        final ModelPopulator modelPopulator = new SubmissionModelPopulator();
        final ModelPopulator modelPopulator = new SRARegistrationModelPopulator();
        //TODO: Try to remove that
        SRARegistrationForm registrationForm = new SRARegistrationForm();
        preFillRegistrationForm(registrationForm);
        model.addAttribute(SRARegistrationForm.MODEL_ATTR_NAME, registrationForm);
        model.addAttribute("isMaxSizeError", isMaxSizeError);
        model.addAttribute("hasSraAccount", submissionContactDAO.checkAccountByEmailAddress(getSessionSubmitter().getEmailAddress()));
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
    public ModelAndView doPost(@ModelAttribute(SRARegistrationForm.MODEL_ATTR_NAME) @Valid SRARegistrationForm registrationForm, BindingResult result,
                               ModelMap model, SessionStatus status) {
        if (isUserAssociatedToSession()) {
//            final ModelPopulator modelPopulator = new SubmissionModelPopulator();
            final ModelPopulator modelPopulator = new SRARegistrationModelPopulator();
            modelPopulator.populateModel(model);
            if (result.hasErrors()) {
                log.info("The submission form has still validation errors!");
                model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
                return new ModelAndView(VIEW_NAME, model);
            }
            if (registrationForm != null) {
                String email = registrationForm.getEmail();
                ((EmailNotificationService) emailService).setSender(email);
                ((EmailNotificationService) emailService).setReceiverCC(email);
                ((EmailNotificationService) emailService).setEmailSubject("EMG-SUB: " + registrationForm.getSubTitle());
                ((EmailNotificationService) emailService).setEmailSubject("EMG-SUB: " + registrationForm.getSubTitle());
                String msg = buildEmailMsg(registrationForm);
                //TODO: Send email to datasub
                emailService.sendNotification(msg);
//                log.info("Sent an email with hibernate submission details: " + msg);
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


    /**
     * Builds the email message from the SRA registration form using Velocity..
     *
     * @param registrationForm
     * @return The email message as String representation.
     */
    protected String buildEmailMsg(final SRARegistrationForm registrationForm) {
        Map<String, Object> model = new HashMap<String, Object>();

        //Add submission form to Velocity model
        model.put("institute", registrationForm.getInstitute());
        model.put("country", registrationForm.getCountry());
        model.put("department", registrationForm.getDepartment());
        model.put("name", registrationForm.getFirstName() + " " + registrationForm.getLastName());
        model.put("email", registrationForm.getEmail());
        model.put("summary", registrationForm.getDataDesc());
        model.put("title", registrationForm.getSubTitle());
        model.put("releaseDate", registrationForm.getReleaseDate());

        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "sra-registration-email.vm", model);
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

    class SRARegistrationModelPopulator implements ModelPopulator {
        @Override
        public void populateModel(ModelMap model) {

            final ViewModelBuilder<SRARegistrationModel> builder = new SRARegistrationViewModelBuilder(sessionManager, "Submit data - EBI metagenomics",
                    getBreadcrumbs(null), propertyContainer, countryDAO, getSessionSubmitter());
            final SRARegistrationModel sraRegistrationModel = builder.getModel();
            sraRegistrationModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SUBMIT_VIEW);
            model.addAttribute(ViewModel.MODEL_ATTR_NAME, sraRegistrationModel);
        }
    }

    private void preFillRegistrationForm(SRARegistrationForm registrationForm) {
        Submitter submitter = getSessionSubmitter();
        if (submitter != null) {
            log.info("Pre fill the SRA registration form with submitter details.");
            String email = submitter.getEmailAddress();
            //trim email address
            if (email != null)
                email.trim();
            registrationForm.setEmail(email);
            //
            String firstName = submitter.getFirstName();
            //trim email address
            if (firstName != null) {
                firstName.trim();
            }
            //
            registrationForm.setFirstName(firstName);
            String lastName = submitter.getSurname();
            //trim last name
            if (lastName != null) {
                lastName.trim();
            }
            registrationForm.setLastName(lastName);
            //
            if (submitter.getAddress() != null) {
                parseAddressDetails(registrationForm, submitter.getAddress());
            }
            //
            registrationForm.setCountry(submitter.getCountry());
        } else {
            log.info("No submitter attached to the session. Therefore no submitter details to pre fill the registration form.");
        }
    }

    private void parseAddressDetails(SRARegistrationForm registrationForm, String address) {
        try {
            List<String> lines = IOUtils.readLines(new StringReader(address));
            if (lines != null && lines.size() > 3) {
                registrationForm.setDepartment(lines.get(0));
                registrationForm.setInstitute(lines.get(1));
                registrationForm.setPostalAddress(lines.get(2));
                registrationForm.setPostalCode(lines.get(3));
            } else {
                log.warn("Cannot parse the address field, because it has an unexpected format!");
            }
        } catch (IOException e) {
            log.error("Exception occurred while parsing the address field!", e);
        }
    }
}