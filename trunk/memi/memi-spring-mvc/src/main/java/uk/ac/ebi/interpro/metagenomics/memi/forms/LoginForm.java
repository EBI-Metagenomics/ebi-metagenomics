package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Represents a customized login form.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class LoginForm {

    public final static String MODEL_ATTR_NAME = "loginForm";

    @NotEmpty(message = "{form.login.emailAddress.email}")
    @Email(message = "{form.login.emailAddress.email}")
    private String emailAddress;

    @NotEmpty(message = "{form.login.password.notEmpty}")
    private String password;

    public LoginForm() {
    }

    public LoginForm(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
