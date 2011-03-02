package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Represents a common controller, which currently holds request mappings
 * for simple pages2 that need no own controller class.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class CommonController {

    public static final String ERROR_PAGE_VIEW_NAME = "exception";

    public static final String ACCESS_DENIED_VIEW_NAME = "lostInSpace";

    @Resource
    private SessionManager sessionManager;

    @RequestMapping("/installationSite")
    public void installationSiteHandler() {
    }

    @RequestMapping("/about")
    public void aboutHandler() {
    }

    @RequestMapping("/help")
    public ModelAndView helpHandler(ModelMap model) {
        model.addAttribute(MGModel.MODEL_ATTR_NAME, MGModelFactory.getMGModel(sessionManager));
        return new ModelAndView("help", model);
    }

    @RequestMapping("/logout")
    public ModelAndView indexHandler() {
        sessionManager.getSessionBean().removeSubmitter();
        return new ModelAndView("redirect:index");
    }
}
