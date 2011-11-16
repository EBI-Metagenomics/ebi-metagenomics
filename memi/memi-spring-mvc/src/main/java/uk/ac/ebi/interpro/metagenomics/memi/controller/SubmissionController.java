package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SubmissionForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.EmailNotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SubmissionModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SubmissionViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the controller for the submission forms.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping('/' + SubmissionController.VIEW_NAME)
public class SubmissionController extends CheckLoginController implements IMGController {

    private final static Log log = LogFactory.getLog(SubmissionController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "submit";

    public static final String SUCCESS_VIEW_NAME = "submitSuccess";

    @Resource(name = "emailNotificationServiceSubmitPage")
    private INotificationService emailService;

    @Resource
    private VelocityEngine velocityEngine;

    public ModelAndView doGet(ModelMap model) {
        if (isUserAssociatedToSession()) {
            final ModelPopulator modelPopulator = new SubmissionModelPopulator();
            model.addAttribute(SubmissionForm.MODEL_ATTR_NAME, new SubmissionForm());
            return buildModelAndView(getModelViewName(), model, modelPopulator);
        } else {
            return new ModelAndView("redirect:" + LoginPageController.VIEW_NAME);
        }
    }

    @RequestMapping(params = "submit", method = RequestMethod.POST)
    public ModelAndView doPost(@ModelAttribute("subForm") @Valid SubmissionForm subForm, BindingResult result,
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
                String msg = buildMsg(subForm);
                String sender = sessionManager.getSessionBean().getSubmitter().getEmailAddress();
                ((EmailNotificationService) emailService).setSender(sender);
                ((EmailNotificationService) emailService).setReceiverCC(sender);
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
            return new ModelAndView("redirect:" + LoginPageController.VIEW_NAME);
        }
    }

    @RequestMapping(params = "cancel", method = RequestMethod.POST)
    public ModelAndView doCancelSubmission(@ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm, BindingResult result, ModelMap model, SessionStatus status) {
        //create model and view
        return new ModelAndView("redirect:" + HomePageController.REDIRECT_VALUE, model);
    }

    /**
     * Builds the email message from the submission form using Velocity..
     *
     * @param subForm Submission form object from which the user input will be read out.
     * @return The email message as String representation.
     */
    protected String buildMsg(SubmissionForm subForm) {
        Map<String, Object> model = new HashMap<String, Object>();

        //Add submission form to Velocity model
        model.put("subForm", subForm);

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
            final ViewModelBuilder<SubmissionModel> builder = new SubmissionViewModelBuilder(sessionManager, "Metagenomics Submit", getBreadcrumbs(null), propertyContainer);
            final SubmissionModel submissionViewModel = builder.getModel();
            model.addAttribute(ViewModel.MODEL_ATTR_NAME, submissionViewModel);
        }
    }

}
