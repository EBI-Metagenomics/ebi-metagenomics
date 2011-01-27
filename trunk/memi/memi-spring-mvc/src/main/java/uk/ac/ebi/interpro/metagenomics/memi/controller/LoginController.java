package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.SessionStatus;
import uk.ac.ebi.interpro.metagenomics.memi.encryption.SHA256;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Represents the controller for the MG portal home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
//public abstract class LoginController<T extends MGModel> {
public abstract class LoginController implements ILoginController {
    private final Log log = LogFactory.getLog(LoginController.class);

    @Resource
    private SubmitterDAO submitterDAO;

    @Resource
    private SessionManager sessionManager;


    public void processLogin(@ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result,
                             ModelMap model, SessionStatus status) {
        //ensure that the user ref is cleared before process the login, in case anything unexpected happens
        sessionManager.getSessionBean().removeSubmitter();
        if (result.hasErrors()) {
            return;
        }
        loginForm = (LoginForm) model.get("loginForm");
        Submitter submitter;
        //in general the login form should be never null (just in case anything unexpected happens)
        if (loginForm != null) {
            String emailAddress = loginForm.getEmailAddress();
            if (!submitterDAO.isDatabaseAlive()) {
                result.addError(new FieldError("loginForm", "emailAddress", "Internal error! We are sorry for any inconvenience."));
                return;
            }
            submitter = submitterDAO.getSubmitterByEmailAddress(emailAddress);
            if (submitter != null) {
                String encryptedPw = SHA256.encrypt(loginForm.getPassword());
                if (encryptedPw == null || !encryptedPw.equals(submitter.getPassword())) {
                    result.addError(new FieldError("loginForm", "emailAddress", "Incorrect login data!"));
                    return;
                }
            } else {
                log.warn("Could not find any submitter for the specified email address: " + emailAddress);
                result.addError(new FieldError("loginForm", "emailAddress", "Incorrect login data!"));
                return;
            }
        } else {
            result.addError(new FieldError("loginForm", "emailAddress", "Internal error! We are sorry for any inconvenience."));
            return;
        }
        //clear the command object from the session
        status.setComplete();
        if (submitter != null) {
            sessionManager.getSessionBean().setSubmitter(submitter);
        }
    }
}