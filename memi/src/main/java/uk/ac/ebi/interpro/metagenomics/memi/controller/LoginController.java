package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Represents the controller for the login page. For further information on how to implement
 * a simple form controller please take a look at (annotated-based implementation):<br>
 * http://www.mkyong.com/spring-mvc/spring-mvc-form-handling-annotation-example/
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/loginForm")
public class LoginController {

    @Resource(name = "submitterDAO")
    private SubmitterDAO submitterDAO;

    private final Log log = LogFactory.getLog(LoginController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(ModelMap model) {
        LoginForm loginForm = new LoginForm();
        model.put("loginForm", loginForm);
        return "loginForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result,
                                ModelMap model, SessionStatus status) {
        if (result.hasErrors())
            return "loginForm";
        loginForm = (LoginForm) model.get("loginForm");
        if (loginForm != null) {
            String emailAddress = loginForm.getEmailAddress();
            if (!submitterDAO.isDatabaseAlive()) {
                result.addError(new FieldError("loginForm", "emailAddress", "Database is down! We are sorry for that."));
                return "loginForm";
            }
            Submitter submitter = submitterDAO.getSubmitterByEmailAddress(emailAddress);
            if (submitter != null) {
                if (!submitter.getPassword().equals(loginForm.getPassword())) {
                    result.addError(new FieldError("loginForm", "emailAddress", "Incorrect login data!"));
                    return "loginForm";
                }
            } else {
                log.warn("Could not find any submitter for the specified email address: " + emailAddress);
                result.addError(new FieldError("loginForm", "emailAddress", "Incorrect login data!"));
                return "loginForm";
            }
        } else {
            return "errorPage";
        }
        //clear the command object from the session
        status.setComplete();
        return "loginSuccessPage";
    }
}