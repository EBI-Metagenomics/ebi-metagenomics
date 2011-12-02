package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller handles the login page in contrast to the login component
 * (please notice, that this is not the controller for the login component on the right hand side).
 * This controller is used if somebody who is not logged in wants to submit data.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping(value = "/" + LoginPageController.VIEW_NAME)
public class LoginPageController extends LoginController {

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "login";

//    public ModelAndView doGet(ModelMap model) {
//        populateModel(model);
//        model.addAttribute("loginForm", new LoginForm());
//        return getModelAndView(model);
//    }


    /**
     * Handles /login?display=true requests.
     *
     * @param display Short term for 'display submission block'. If TRUE the submission block is shown on the page,
     *                otherwise it is invisible.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(@RequestParam(required = true) final boolean display, ModelMap model) {
        populateModel(model, display);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return getModelAndView(model, display);
    }

    @RequestMapping(params = "login", method = RequestMethod.POST)
    public ModelAndView doProcessLogin(
            @ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm,
            BindingResult result, ModelMap model, SessionStatus status,
            HttpServletRequest request) {
        boolean display = Boolean.parseBoolean(request.getParameter("display"));
        //process login
        super.processLogin(loginForm, result, model, status);
        populateModel(model, display);
        return getModelAndView(model, display);
    }

    /**
     * Creates the home page model and adds it to the specified model map.
     */
    private void populateModel(final ModelMap model, final boolean display) {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Login, start submission process - EBI metagenomics", getBreadcrumbs(null), propertyContainer);
        final ViewModel viewModel = builder.getModel();
        //We only do highlighting the submission tab if the login is part of the submission process (clicked on submit data tab)
        if (display) {
            viewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SUBMIT_VIEW);
        }
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, viewModel);
    }

    @RequestMapping(params = "cancel", method = RequestMethod.POST)
    public ModelAndView doCancelLoginProcess(@ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm, ModelMap model) {
        //create model and view
        return new ModelAndView("redirect:" + HomePageController.REDIRECT_VALUE, model);
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Login", "Login to the metagenomics portal", VIEW_NAME));
        return result;
    }

    private ModelAndView getModelAndView(final ModelMap model, final boolean display) {
        Submitter submitter = sessionManager.getSessionBean().getSubmitter();
        if (submitter != null) {
            if (display) {
                return new ModelAndView("redirect:submit", model);
            } else {
                return new ModelAndView("redirect:", model);
            }
        }
        //display submission block
        model.addAttribute("displaySubBlock", display);
        //create model and view
        return new ModelAndView(VIEW_NAME, model);
    }
}