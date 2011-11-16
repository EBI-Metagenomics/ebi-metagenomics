package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

/**
 * Represents a form for submissions.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class FeedbackForm {
    public final static String MODEL_ATTR_NAME = "feedbackForm";

    @NotEmpty(message = "{form.feedback.emailAddress.email}")
    @Email(message = "{form.feedback.emailAddress.email}")
    private String emailAddress;

    @NotEmpty(message = "{form.feedback.emailSubject.notEmpty}")
    @Size(min = 3, max = 100, message = "{form.feedback.emailSubject.size}")
    private String emailSubject;

    @NotEmpty(message = "{form.feedback.emailMessage.notEmpty}")
    @Size(min = 3, message = "{form.feedback.emailMessage.size}")
    private String emailMessage;

    private String leaveIt; // Secret HoneyPot field to detect robots - should be left blank by humans!

    public FeedbackForm() {

    }

    public FeedbackForm(String emailAddress, String emailSubject, String emailMessage, String leaveIt) {
        this.emailAddress = emailAddress;
        this.emailSubject = emailSubject;
        this.emailMessage = emailMessage;
        this.leaveIt = leaveIt;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    public String getLeaveIt() {
        return leaveIt;
    }

    public void setLeaveIt(String leaveIt) {
        this.leaveIt = leaveIt;
    }
}