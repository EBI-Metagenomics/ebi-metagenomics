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
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.validation.Valid;

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
@RequestMapping(value = "/login")
public class LoginPageController extends LoginController implements IMGController {

    /**
     * View name of this controller which is used several times.
     */
    private final String VIEW_NAME = "login";

    @Resource
    private SessionManager sessionManager;

    @Override
    public ModelAndView doGet(ModelMap model) {
        model.addAttribute("loginForm", new LoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    @RequestMapping(params = "login", method = RequestMethod.POST)
    public ModelAndView doProcessLogin(@ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm, BindingResult result, ModelMap model, SessionStatus status) {
        //process login
        super.processLogin(loginForm, result, model, status);
        Submitter submitter = sessionManager.getSessionBean().getSubmitter();
        if (submitter != null) {
            return new ModelAndView("redirect:submissionForm", model);
        }
        //create model and view
        return new ModelAndView(VIEW_NAME, model);
    }

    @RequestMapping(params = "cancel", method = RequestMethod.POST)
    public ModelAndView doCancelLoginProcess(@ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm, BindingResult result, ModelMap model, SessionStatus status) {
        //create model and view
        return new ModelAndView("redirect:index", model);
    }
}
