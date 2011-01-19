package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Represents a customized login form.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class LoginForm {

    public final static String MODEL_ATTR_NAME = "loginForm";

    @NotEmpty
//    @Size(min = 1, max = 30)
//    @Email
//    TODO: Reactivate email annotation (deactivate because the test user has an invalid email address)
    private String emailAddress;

    @NotEmpty
//    @Size(min = 1, max = 12)
    private String password;

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