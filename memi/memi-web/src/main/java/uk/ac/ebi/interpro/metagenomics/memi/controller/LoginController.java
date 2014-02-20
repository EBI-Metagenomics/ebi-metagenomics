package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import uk.ac.ebi.ena.account.admin.client.AuthenticationClient;
import uk.ac.ebi.ena.authentication.model.AuthResult;
import uk.ac.ebi.interpro.metagenomics.memi.authentication.AuthenticationService;
import uk.ac.ebi.interpro.metagenomics.memi.authentication.MGPortalAuthResult;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;

/**
 * Represents the login controller for the login component on the right hand side, which is display on each page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public abstract class LoginController extends AbstractController {
    private final Log log = LogFactory.getLog(LoginController.class);

    @Resource
    private AuthenticationService authenticationService;

    public void processLogin(final LoginForm loginForm, final BindingResult bindingResult, final SessionStatus status) {
        //Perform some simple sanity checks
        if (bindingResult == null) {
            log.error("Application error! Please check why the binding result is NULL at that stage.");
            return;
        }
        if (loginForm == null) {
            bindingResult.addError(new FieldError("loginForm", "userName", "Application error! Please email us so we can keep fix the issue."));
            log.error("Application error! Please check why the login form is NULL at that stage.");
            return;
        }
        //sanity checks done
        if (bindingResult.hasErrors()) {
            return;
        }
        //Define error messages
//        final String applicationErrorMessage = "Application error! We are sorry for any inconvenience.";
//        final String loginValidationErrorMessage = "Login failed. Incorrect combination of user name and password.";
        //ensure that the user ref is cleared before process the login, in case anything unexpected happened
        sessionManager.getSessionBean().removeSubmitter();

        MGPortalAuthResult authResult = authenticationService.login(loginForm.getUserName(), loginForm.getPassword());
        if (authResult.failed()) {
            bindingResult.addError(new FieldError("loginForm", "userName", loginForm.getUserName(), false, null, null, ""));
            bindingResult.addError(new FieldError("loginForm", "password", loginForm.getPassword(), false, null, null, authResult.getErrorMessage()));
        } else {
            sessionManager.getSessionBean().setSubmitter(authResult.getSubmitter());
        }

        //clear the command object from the session
        if (status != null) {
            status.setComplete();
        }
    }
}