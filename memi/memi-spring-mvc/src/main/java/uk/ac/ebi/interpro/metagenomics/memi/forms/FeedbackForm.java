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

    @NotEmpty(message = "{form.feedback.sender.email}")
    @Email(message = "{form.feedback.sender.email}")
    private String sender;

    @NotEmpty(message = "{form.feedback.emailSubject.notEmpty}")
    @Size(min = 3, max = 100, message = "{form.feedback.emailSubject.size}")
    private String emailSubject;

    @NotEmpty(message = "{form.feedback.message.notEmpty}")
    @Size(min = 3, message = "{form.feedback.message.size}")
    private String message;

    private String leaveIt; // Secret HoneyPot field to detect robots - should be left blank by humans!

    public FeedbackForm(String sender, String emailSubject, String message, String leaveIt) {
        this.sender = sender;
        this.emailSubject = emailSubject;
        this.message = message;
        this.leaveIt = leaveIt;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLeaveIt() {
        return leaveIt;
    }

    public void setLeaveIt(String leaveIt) {
        this.leaveIt = leaveIt;
    }
}