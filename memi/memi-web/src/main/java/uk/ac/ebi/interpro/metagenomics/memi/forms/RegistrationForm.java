package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;
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

    @AssertTrue(message = "Please check this box if you want your data to be analysed by the EBI metagenomics team.")
    Boolean consentCheck=true;

    @NotEmpty(message = "Please enter a valid ENA Webin account username")
    @Length(min = 3, message = "Your username must be at least {min} characters long")
    String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getDoesAccountExist() {
        return doesAccountExist;
    }

    public void setDoesAccountExist(Boolean doesAccountExist) {
        this.doesAccountExist = doesAccountExist;
    }

    public Boolean getConsentCheck() {
        return consentCheck;
    }

    public void setConsentCheck(Boolean consentCheck) {
        this.consentCheck = consentCheck;
    }
}