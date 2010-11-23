package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO;

import javax.annotation.Resource;

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
    public String showLoginForm(ModelMap model) {
        return "submissionForm";
    }
}
