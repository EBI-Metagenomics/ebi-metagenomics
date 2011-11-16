package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.FeedbackForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.EmailNotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SubmissionModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * Represents the feedback form controller for the JQuery feedback dialog on the right hand side, which is display on each page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class FeedbackFormController extends AbstractController {
    private final Log log = LogFactory.getLog(FeedbackFormController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "feedback";

    @Resource
    private SubmitterDAO submitterDAO;

    @Resource(name = "emailNotificationServiceFeedbackPage")
    private INotificationService emailService;

    @Resource
    private VelocityEngine velocityEngine;

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model) {
        return buildModelAndView(
                getModelViewName(),
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Metagenomics Feedback", getBreadcrumbs(null), propertyContainer);
                        final ViewModel defaultViewModel = builder.getModel();
                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                        model.addAttribute("feedbackForm", new FeedbackForm());
                    }
                }
        );
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public String doPost(@ModelAttribute("feedbackForm") @Valid FeedbackForm feedbackForm,
                         BindingResult result, ModelMap model,
                         SessionStatus status) {
        if (result.hasErrors()) {
            log.info("Feedback form has validation errors!");
            return "/feedback";
        }
        //build contact email message
        if (feedbackForm != null) {
            String msg = buildMsg(feedbackForm.getEmailMessage());
            ((EmailNotificationService) emailService).setSender(feedbackForm.getEmailAddress());
            ((EmailNotificationService) emailService).setEmailSubject("[beta-feedback] " + feedbackForm.getEmailSubject());
            ((EmailNotificationService) emailService).setReceiverCC(feedbackForm.getEmailAddress());
            emailService.sendNotification(msg);
            log.info("Sent an email with contact details: " + msg);
            status.setComplete();
        } else {
            return CommonController.EXCEPTION_PAGE_VIEW_NAME;
        }
        return "/feedbackSuccess";
    }

    @RequestMapping(value = "**/feedbackSuccess", method = RequestMethod.GET)
    public ModelAndView doGetSuccessPage(final ModelMap model) {
        return buildModelAndView(
                "/feedbackSuccess",
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Metagenomics Thank you", getBreadcrumbs(null), propertyContainer);
                        final ViewModel defaultViewModel = builder.getModel();
                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                    }
                }
        );
    }

    @RequestMapping(value = "**/doFeedback", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, String> doProcessFeedback(@RequestParam(required = true, value = "emailAddress") String emailAddress,
                                          @RequestParam(required = true, value = "emailSubject") String emailSubject,
                                          @RequestParam(required = true, value = "emailMessage") String emailMessage,
                                          @RequestParam(value = "leaveIt") String leaveIt,
                                          final HttpServletResponse response) {
        // Server side feedback form validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        FeedbackForm feedbackForm = new FeedbackForm(emailAddress, emailSubject, emailMessage, leaveIt);
        Set<ConstraintViolation<FeedbackForm>> constraintViolations = validator.validate(feedbackForm);
        if (!constraintViolations.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> errorMessages = new HashMap<String, String>();
            Iterator<ConstraintViolation<FeedbackForm>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<FeedbackForm> violation = iterator.next();
                String id = violation.getPropertyPath().toString();
                String message = errorMessages.get(id);
                if (message != null) {
                    message = message + "<br>" + violation.getMessage();
                } else {
                    message = violation.getMessage();
                }
                errorMessages.put(id, message);
            }
            log.info("Feedback form has still validation errors! Error messages are: \n" + errorMessages);
            return errorMessages;
        } else {
            //build feedback email message
            if (leaveIt == null || leaveIt.equals("")) {
                String msg = buildMsg(emailMessage);
                ((EmailNotificationService) emailService).setSender(emailAddress);
                ((EmailNotificationService) emailService).setEmailSubject("[beta-feedback] " + emailSubject);
                ((EmailNotificationService) emailService).setReceiverCC(emailAddress);
                emailService.sendNotification(msg);
                if (log.isInfoEnabled()) {
                    log.info("Sent an email with feedback details: " + msg);
                }
            } // else we have a robot so don't want to actually send the email
        }
//            Returning NULL means everything is OK
        return null;
    }

    /**
     * Builds the email message using Velocity..
     *
     * @param message Feeback message.
     * @return The email message as String representation.
     */
    protected String buildMsg(String message) {
        Map<String, Object> model = new HashMap<String, Object>();
        //Add feedback form to Velocity model
        model.put("emailMessage", message);
        //Add logged in user to Velocity model
        if (sessionManager != null && sessionManager.getSessionBean() != null) {
            model.put("submitter", sessionManager.getSessionBean().getSubmitter());
        }
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "feedback-email.vm", model);
    }

    @Override
    protected String getModelViewName() {
        return VIEW_NAME;
    }

    @Override
    protected List<Breadcrumb> getBreadcrumbs(SecureEntity obj) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
