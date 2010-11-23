package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

/**
 * Represents the controller for the login page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/loginForm")
public class LoginController {

    @Resource(name="submitterDAO")
    private SubmitterDAO submitterDAO;

    @RequestMapping(method = RequestMethod.GET)
    @SuppressWarnings(value = "unchecked")
    public String showForm(Map model) {
        LoginForm loginForm = new LoginForm();
        model.put("loginForm", loginForm);
        return "loginForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    @SuppressWarnings(value = "unchecked")
    public String processForm(@Valid LoginForm loginForm, BindingResult result,
                              Map model) {
        if (result.hasErrors())
            return "loginForm";
        loginForm = (LoginForm) model.get("loginForm");
        if (loginForm != null) {
            String emailAddress = loginForm.getEmailAddress();
            Submitter submitter = submitterDAO.getSubmitterByEmailAddress(emailAddress);
            if (submitter != null) {
                if (!submitter.getPassword().equals(loginForm.getPassword())) {
                    result.addError(new FieldError("loginForm", "emailAddress", "Email address and password do not belong together!"));
                    return "loginForm";
                }
            } else {
                result.addError(new FieldError("loginForm", "emailAddress", "Email address does not exist!"));
                return "loginForm";
            }
        }
        model.put("loginForm", loginForm);
        return "loginSuccessPage";
    }


//    @RequestMapping(method = RequestMethod.POST)
//    public String onSubmit(@ModelAttribute("user") User user) {
//        userService.add(user);
//        return "redirect:userSuccess.htm";
//    }
}
