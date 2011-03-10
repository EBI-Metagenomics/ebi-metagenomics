package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;
import javax.annotation.Resource;

/**
 * Represents a common controller, which currently holds request mappings
 * for simple pages that need no own controller class.
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

    @RequestMapping("/logout")
    public ModelAndView indexHandler() {
        sessionManager.getSessionBean().removeSubmitter();
        return new ModelAndView("redirect:" + HomePageController.REDIRECT_VALUE);
    }
}
