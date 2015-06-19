package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: maxim
 * Date: 6/18/15
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationForm {

    /* Boolean flag to track if submitters do have an ENA Webin account */
    @NotNull(message = "Please choose one of the following options.")
    Boolean doesAccountExist;

    @NotEmpty(message = "Please enter a valid ENA Webin account user name")
    @Length(min = 3, message = "Your user name must be at least {min} characters long")
    String userName;

    String password;

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

    public Boolean getDoesAccountExist() {
        return doesAccountExist;
    }

    public void setDoesAccountExist(Boolean doesAccountExist) {
        this.doesAccountExist = doesAccountExist;
    }
}