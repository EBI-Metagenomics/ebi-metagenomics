package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.FeedbackForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.EmailNotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
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

    @Resource
    private SubmitterDAO submitterDAO;

    @Resource(name = "emailNotificationServiceFeedbackPage")
    private INotificationService emailService;

    @Resource
    private VelocityEngine velocityEngine;

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model) {
        //build and add the default page model
        populateModel(model, "Metagenomics Feedback");
        return new ModelAndView("feedback", model);
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public ModelAndView doPost(final ModelMap model) {
        //build and add the default page model
        populateModel(model, "Metagenomics Thank you");
        return new ModelAndView("/feedbackSuccess", model);
    }

    @RequestMapping(value = "**/feedbackSuccess", method = RequestMethod.GET)
    public ModelAndView doGetSuccessPage(final ModelMap model) {
        //build and add the default page model
        populateModel(model, "Metagenomics Thank you");
        return new ModelAndView("/feedbackSuccess", model);
    }

    @RequestMapping(value = "**/doFeedback", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, String> doProcessFeedback(@RequestParam String emailAddress, @RequestParam String emailSubject,
                                          @RequestParam String emailMessage, @RequestParam String leaveIt,
                                          HttpServletResponse response) {
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

    private void populateModel(final ModelMap model, final String pageTitle) {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, pageTitle, getBreadcrumbs(null), propertyContainer);
        final ViewModel defaultViewModel = builder.getModel();
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
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
        model.put("message", message);
        //Add logged in user to Velocity model
        if (sessionManager != null && sessionManager.getSessionBean() != null) {
            model.put("submitter", sessionManager.getSessionBean().getSubmitter());
        }
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "feedback-email.vm", model);
    }

    @Override
    protected String getModelViewName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected List<Breadcrumb> getBreadcrumbs(SecureEntity obj) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
