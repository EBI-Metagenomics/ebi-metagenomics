package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SubmissionModel;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
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
public class SubmissionController extends CheckLoginController implements IMGController {

    private final static Log log = LogFactory.getLog(SubmissionController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "submit";

    public static final String SUCCESS_VIEW_NAME = "submitSuccess";

    @Resource(name = "emailNotificationServiceSubmit")
    private INotificationService emailService;

    @Resource(name = "velocityEngine")
    private VelocityEngine velocityEngine;

    @Override
    public ModelAndView doGet(ModelMap model) {
        if (isUserAssociatedToSession()) {
            //build and add the page model
            populateModel(model);
            model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((SubmissionModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
            model.addAttribute(SubmissionForm.MODEL_ATTR_NAME, ((SubmissionModel) model.get(MGModel.MODEL_ATTR_NAME)).getSubForm());
            return new ModelAndView(VIEW_NAME, model);
        } else {
            return new ModelAndView("redirect:" + LoginPageController.VIEW_NAME);
        }
    }

    @RequestMapping(params = "submit", method = RequestMethod.POST)
    public ModelAndView doPost(@ModelAttribute("subForm") @Valid SubmissionForm subForm, BindingResult result,
                               ModelMap model, SessionStatus status) {
        if (isUserAssociatedToSession()) {
            populateModel(model);
            if (subForm != null && !validateReleaseDate(subForm.getReleaseDate())) {
                result.addError(new FieldError("subForm", "releaseDate", "Data cannot be held private for more than 2 years"));
            }
            if (result.hasErrors()) {
                log.info("Submission form still has validation errors!");
                model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((SubmissionModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
                return new ModelAndView(VIEW_NAME, model);
            }
            if (subForm != null) {
                String msg = buildMsg(subForm);
                String sender = sessionManager.getSessionBean().getSubmitter().getEmailAddress();
                ((EmailNotificationService) emailService).setSender(sender);
                ((EmailNotificationService) emailService).setEmailSubject("EMG-SUB: " + subForm.getSubTitle());
                emailService.sendNotification(msg);
                log.info("Sent an email with hibernate submission details: " + msg);
                status.setComplete();
            } else {
                return new ModelAndView(CommonController.ERROR_PAGE_VIEW_NAME);
            }
            return new ModelAndView(SUCCESS_VIEW_NAME);
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
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model) {
        final SubmissionModel subModel = MGModelFactory.getSubmissionModel(sessionManager,
                "Metagenomics Submit", getBreadcrumbs(null), propertyContainer);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, subModel);
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

        //Should a release date warning be added to the email?
        String releaseDate = subForm.getReleaseDate();
        model.put("dateWarning", !validateReleaseDate(releaseDate));

        //Add logged in user to Velocity model
        if (sessionManager != null && sessionManager.getSessionBean() != null) {
            model.put("submitter", sessionManager.getSessionBean().getSubmitter());
        }
        return VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, "WEB-INF/velocity_templates/submission-confirmation.vm", model);
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Submit", "Submit new data", VIEW_NAME));
        return result;
    }

    /**
     * Check that the release date entered is not more than 2 years from the current time.
     *
     * @param releaseDate String in format "MM/dd/yyy"
     * @return False if validation failed, otherwise true
     */
    private boolean validateReleaseDate(String releaseDate) {
        if (releaseDate.equals(null)) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        long now = System.currentTimeMillis();
        calendar.setTimeInMillis(now);
        calendar.add(Calendar.YEAR, 2);
        long limit = calendar.getTimeInMillis();


        DateFormat df = new SimpleDateFormat("MM/dd/yyy");
        Date dateEntered = null;
        try {
            dateEntered = df.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        long releaseTimeInMillis = dateEntered.getTime();
        if (releaseTimeInMillis > limit) {
            return false;
        }

        return true;
    }
}
