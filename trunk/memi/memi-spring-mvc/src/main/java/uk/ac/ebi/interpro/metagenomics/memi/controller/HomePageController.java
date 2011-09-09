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
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.feed.RomeClient;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.HomePageViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.StudyViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the controller for the MG portal home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller(value = "homePageController")
@RequestMapping(value = "/")
public class HomePageController extends LoginController implements IMGController {

    private final Log log = LogFactory.getLog(HomePageController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "index";

    public static final String REQUEST_MAPPING_VALUE = "";

    public static final String REDIRECT_VALUE = "/" + REQUEST_MAPPING_VALUE;

    //Data access objects
    @Resource
    private HibernateStudyDAO studyDAO;

    @Resource
    private HibernateSampleDAO sampleDAO;

    @Resource
    RomeClient rssClient;

    @Override
    public ModelAndView doGet(ModelMap model) {
        log.info("Requesting doGet of " + HomePageController.class);
        //build and add the page model
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewModel) model.get(ViewModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }


    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doProcessLogin(@ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm, BindingResult result,
                                       ModelMap model, SessionStatus status) {
        log.info("Requesting doProcessLogin of " + HomePageController.class + "...");
        //process login
        super.processLogin(loginForm, result, model, status);
        //create model and view
        populateModel(model);
        return new ModelAndView(VIEW_NAME, model);
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     *
     * @param model Model map to populate
     */
    private void populateModel(ModelMap model) {
        log.info("Building model of " + HomePageController.class + "...");
        final ViewModelBuilder<HomePageViewModel> builder = new HomePageViewModelBuilder(sessionManager, "Metagenomics Home",
                getBreadcrumbs(null), propertyContainer, studyDAO, sampleDAO, rssClient);
        final HomePageViewModel hpModel = builder.getModel();
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, hpModel);
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Home", "Home page", ""));
        return result;
    }
}
