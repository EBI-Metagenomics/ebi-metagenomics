package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SubmissionForm;
import uk.ac.ebi.interpro.metagenomics.memi.services.NotificationService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the controller for the submission forms.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/submissionForm")
public class SubmissionController {

    @Resource(name = "emailNotificationService")
    private NotificationService emailService;

    @Resource(name = "velocityEngine")
    private VelocityEngine velocityEngine;

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) {
        SubmissionForm subForm = new SubmissionForm();
        model.put("subForm", subForm);
        return "submissionForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("subForm") @Valid SubmissionForm subForm, BindingResult result,
                                ModelMap model, SessionStatus status) {
        if (result.hasErrors())
            return "submissionForm";
        subForm = (SubmissionForm) model.get("subForm");
        if (subForm != null) {
            String msg = buildMsg(subForm);
            emailService.sendNotification(msg);
            status.setComplete();
        } else {
            return "errorPage";
        }
        return "submissionSuccessPage";
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
                velocityEngine, "WEB-INF/templates/submission-confirmation.vm", model);
    }
}