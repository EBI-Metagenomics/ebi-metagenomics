package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
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
    private static final Log log = LogFactory.getLog(CommonController.class);

    public static final String EXCEPTION_PAGE_VIEW_NAME = "exception";

    public static final String NO_SUCH_REQUEST_PAGE_VIEW_NAME = "404";

    public static final String ACCESS_DENIED_VIEW_NAME = "lostInSpace";

    @Resource
    private SessionManager sessionManager;

    @RequestMapping(value = "/installationSite", method = RequestMethod.GET)
    public void installationSiteHandler() {
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView pageNotFoundHandler(ModelMap model) {
        log.warn("This method is called twice times at the moment. If you don't find any Spring related " +
                "log to no such request mapping found, please do not note this warning. HTTP status code " +
                "404 responded. Please have a look to the Spring log to see which wrong URL was " +
                "requested!");
        ViewModel viewModel = MGModelFactory.getMGModel(sessionManager);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, viewModel);
        return new ModelAndView("/errors/" + CommonController.NO_SUCH_REQUEST_PAGE_VIEW_NAME, model);
    }

    @RequestMapping(value = "/test")
    public ModelAndView testHandler(ModelMap model) {
        model.addAttribute("emgFile", new EmgFile("img", "chart.png"));
        return new ModelAndView("test", model);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView indexHandler() {
        if (sessionManager.getSessionBean() != null) {
            Submitter submitter = sessionManager.getSessionBean().getSubmitter();
            log.info("Submitter with email address " + submitter.getEmailAddress() + " tries to logout...");
            sessionManager.getSessionBean().removeSubmitter();
        }
        return new ModelAndView("redirect:" + HomePageController.REQUEST_MAPPING_VALUE);
    }
}