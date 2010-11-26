package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SubmissionForm;

import javax.validation.Valid;

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
//            TODO: Add message notification service

        } else {
            return "errorPage";
        }
        return "submissionSuccessPage";
    }
}