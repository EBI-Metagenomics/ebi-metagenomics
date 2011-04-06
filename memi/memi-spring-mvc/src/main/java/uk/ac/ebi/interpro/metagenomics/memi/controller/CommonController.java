package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
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

    public static final String EXCEPTION_PAGE_VIEW_NAME = "exception";

    public static final String NO_SUCH_REQUEST_PAGE_VIEW_NAME = "404";

    public static final String ACCESS_DENIED_VIEW_NAME = "lostInSpace";

    @Resource
    private SessionManager sessionManager;

    @RequestMapping(value = "/installationSite", method = RequestMethod.GET)
    public void installationSiteHandler() {
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView testHandler() {
        MGModel mgModel = MGModelFactory.getMGModel(sessionManager);
        ModelMap model = new ModelMap();
        model.addAttribute(MGModel.MODEL_ATTR_NAME, mgModel);
        return new ModelAndView("/errors/" + CommonController.NO_SUCH_REQUEST_PAGE_VIEW_NAME, model);
    }

    @RequestMapping(value = "/test")
    public ModelAndView testHandler(ModelMap model) {
        model.addAttribute("emgFile", new EmgFile("img", "chart.png"));
        return new ModelAndView("test", model);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView indexHandler() {
        sessionManager.getSessionBean().removeSubmitter();
        return new ModelAndView("redirect:" + HomePageController.REQUEST_MAPPING_VALUE);
    }
}