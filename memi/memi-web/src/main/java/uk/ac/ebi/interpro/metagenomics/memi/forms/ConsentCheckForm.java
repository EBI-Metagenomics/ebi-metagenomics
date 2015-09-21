package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: maxim
 * Date: 6/18/15
 * Time: 3:10 PM
 * To change this template use File | Settings | File Templates.
 */
@GroupSequence({
        //Another important point is to always add the class being annotated as a member of the group defined by@GroupSequence, as was done with the AdvancedSequenceSearchFormBean class. If this condition is not met, a GroupDefinitionException will be thrown.
        ConsentCheckForm.class,
        ConsentCheckForm.NotEmptyCheck.class,
        ConsentCheckForm.LengthCheck.class,
        ConsentCheckForm.NotNullCheck.class,
        ConsentCheckForm.EmailCheck.class
})
public class ConsentCheckForm {

    /* Boolean flag to track if submitters do have an ENA Webin account */
    @NotNull(message = "Please choose one of the following options.", groups = NotNullCheck.class)
    Boolean doesAccountExist;

    @AssertTrue(message = "Please check this box if you want your data to be analysed by the EBI metagenomics team.")
    Boolean consentCheck = true;

    @NotEmpty(message = "Please enter a valid ENA Webin account username", groups = NotEmptyCheck.class)
    @Length(min = 7, message = "Your username must be at least {min} characters long", groups = LengthCheck.class)
    String userName;

    @NotEmpty(message = "Please enter a valid email address", groups = NotEmptyCheck.class)
    @Length(min = 6, message = "Your email address must be at least {min} characters long", groups = LengthCheck.class)
    @Email(groups = EmailCheck.class)
    String email;

    @NotEmpty(message = "Please enter your password", groups = NotEmptyCheck.class)
    private String password;

    private boolean isNewUser;

    private Submitter submitter;

    public ConsentCheckForm() {
        this(false);
    }

    public ConsentCheckForm(boolean newUser) {
        isNewUser = newUser;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /* Necessary for group validation  */
    public interface LengthCheck {
    }

    /* Necessary for group validation  */
    public interface NotEmptyCheck {
    }

    /* Necessary for group validation  */
    public interface NotNullCheck {
    }

    /* Necessary for group validation  */
    public interface EmailCheck {
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public void setNewUser(boolean newUser) {
        isNewUser = newUser;
    }

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }
}