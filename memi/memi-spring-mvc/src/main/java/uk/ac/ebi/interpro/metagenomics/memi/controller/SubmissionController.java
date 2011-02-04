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
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SubmissionModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the controller for the submission forms.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/submissionForm")
public class SubmissionController implements IMGController {

    private final Log log = LogFactory.getLog(SubmissionController.class);

    /**
     * View name of this controller which is used several times.
     */
    private final String VIEW_NAME = "submissionForm";

    @Resource(name = "emailNotificationService")
    private INotificationService emailService;

    @Resource(name = "velocityEngine")
    private VelocityEngine velocityEngine;

    @Resource
    private SessionManager sessionManager;

    @Override
    public ModelAndView doGet(ModelMap model) {
        //build and add the page model
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((SubmissionModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        model.addAttribute(SubmissionForm.MODEL_ATTR_NAME, ((SubmissionModel) model.get(MGModel.MODEL_ATTR_NAME)).getSubForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doPost(@ModelAttribute("subForm") @Valid SubmissionForm subForm, BindingResult result,
                               ModelMap model, SessionStatus status) {
        populateModel(model);
        if (result.hasErrors()) {
            log.info("Submission form still has validation errors!");
            model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((SubmissionModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
            return new ModelAndView(VIEW_NAME, model);
        }
        subForm = (SubmissionForm) model.get("subForm");
        if (subForm != null) {
            String msg = buildMsg(subForm);
            emailService.sendNotification(msg);
            log.info("Sent an email with hibernate submission details: " + msg);
            status.setComplete();
        } else {
            return new ModelAndView("errorPage");
        }
        return new ModelAndView("submissionSuccess");
    }

    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model) {
        final SubmissionModel subModel = MGModelFactory.getSubmissionModel(sessionManager);
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
        model.put("subForm", subForm);
        return VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, "WEB-INF/velocity_templates/submission-confirmation.vm", model);
    }
}