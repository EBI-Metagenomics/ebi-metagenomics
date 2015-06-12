package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Represents a customized login form.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public final class LoginForm {

    public final static String MODEL_ATTR_NAME = "loginForm";

    @NotEmpty(message = "{form.login.userName.notEmpty}")
    private String userName;

    @NotEmpty(message = "{form.login.password.notEmpty}")
    private String password;

    public LoginForm() {
    }

    public LoginForm(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
