package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.NewsDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.HomePageModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Represents the controller for the MG portal home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping(value = "/index")
public class HomePageController extends LoginController implements IMGController {

    private final Log log = LogFactory.getLog(HomePageController.class);

    /**
     * View name of this controller which is used several times.
     */
    private final String VIEW_NAME = "index";

    //Data access objects
    @Resource
    private EmgStudyDAO emgStudyDAO;

    @Resource
    private NewsDAO newsDAO;

    //Other injections
    @Resource
    private SessionManager sessionManager;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model) {
        //build and add the page model
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView processLoginSubmit(@ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm, BindingResult result,
                                           ModelMap model, SessionStatus status) {
        //process login        
        super.processLogin(loginForm, result, model, status);
        //create model and view
        populateModel(model);
        return new ModelAndView(VIEW_NAME, model);
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model) {
        final HomePageModel hpModel = MGModelFactory.getHomePageModel(sessionManager, emgStudyDAO, newsDAO);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, hpModel);
    }
}