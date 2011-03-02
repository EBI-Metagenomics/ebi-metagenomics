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
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Represents a controller for the info page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping(value = "/" + InfoController.VIEW_NAME)
public class InfoController extends LoginController implements IMGController {
    private final Log log = LogFactory.getLog(InfoController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "info";

    @Resource
    private SessionManager sessionManager;

    @Override
    public ModelAndView doGet(ModelMap model) {
        log.info("Requesting info controller...");
        //build and add the page model
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doProcessLogin(@ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result,
                                       ModelMap model, SessionStatus status) {
        //process login
        super.processLogin(loginForm, result, model, status);
        //create model and view
        populateModel(model);
        return new ModelAndView(VIEW_NAME, model);
    }

    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model) {
        final MGModel hpModel = MGModelFactory.getMGModel(sessionManager);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, hpModel);
    }
}