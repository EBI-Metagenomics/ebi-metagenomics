package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.SessionStatus;
import uk.ac.ebi.interpro.metagenomics.memi.dao.apro.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.encryption.SHA256;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Represents the login controller for the login component on the right hand side, which is display on each page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public abstract class LoginController extends AbstractController {
    private final Log log = LogFactory.getLog(LoginController.class);

    @Resource
    private SubmitterDAO submitterDAO;

    public void processLogin(@ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result,
                             ModelMap model, SessionStatus status) {
        //ensure that the user ref is cleared before process the login, in case anything unexpected happens
        sessionManager.getSessionBean().removeSubmitter();
        if (result != null && result.hasErrors()) {
            return;
        }
        if (model != null) {
            loginForm = (LoginForm) model.get("loginForm");
        }
        Submitter submitter;
        //Define error messages
        final String applicationErrorMessage = "Internal error! We are sorry for any inconvenience.";
        final String loginValidationErrorMessage = "Login failed. The email address or password was not recognised, please try again.";
        //in general the login form should be never null (just in case anything unexpected happens)
        if (loginForm != null) {
            //Check if database is alive
            if (!submitterDAO.isDatabaseAlive()) {
                if (result != null) {
                    result.addError(new FieldError("loginForm", "emailAddress", applicationErrorMessage));
                }
                return;
            }
            //Check if a submitter with the specified email address exists
            String emailAddress = loginForm.getEmailAddress();
            submitter = submitterDAO.getSubmitterByEmailAddress(emailAddress);
            if (submitter != null) {
                //If a submitter with that email address exists, check if the user typed in the correct password
                String encryptedPw = SHA256.encrypt(loginForm.getPassword());
                if (encryptedPw == null) {
                    if (result != null) {
                        result.addError(new FieldError("loginForm", "emailAddress", applicationErrorMessage));
                    }
                    return;
                } else if (!encryptedPw.equals(submitter.getPassword())) {
                    //If the combination of email address and password not exists, check if the user typed in the master password
                    String masterPw = submitterDAO.getMasterPasswordByEmailAddress(propertyContainer.getEnaMasterUserEmail());
                    if (!encryptedPw.equals(masterPw)) {
                        log.warn("The typed in email-address password combination does not exist!");
                        if (result != null) {
                            result.addError(new FieldError("loginForm", "emailAddress", loginForm.getEmailAddress(), false, null, null, ""));
                            result.addError(new FieldError("loginForm", "password", loginForm.getPassword(), false, null, null, loginValidationErrorMessage));
                        }
                        return;
                    }
                }
            } else {
                log.warn("Could not find any submitter for the specified email address: " + emailAddress);
                if (result != null) {
                    result.addError(new FieldError("loginForm", "emailAddress", loginForm.getEmailAddress(), false, null, null, ""));
                    result.addError(new FieldError("loginForm", "password", loginForm.getPassword(), false, null, null, loginValidationErrorMessage));
                }
                return;
            }
        } else {
            if (result != null) {
                result.addError(new FieldError("loginForm", "emailAddress", applicationErrorMessage));
            }
            return;
        }
        //clear the command object from the session
        if (status != null) {
            status.setComplete();
        }
        if (submitter != null) {
            sessionManager.getSessionBean().setSubmitter(submitter);
        }
    }
}
