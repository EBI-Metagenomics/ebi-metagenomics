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
public class ContactForm {
    public final static String MODEL_ATTR_NAME = "contactForm";

    @NotEmpty(message = "{form.contact.sender.email}")
    @Email(message = "{form.contact.sender.email}")
    private String sender;

    @NotEmpty( message = "{form.contact.emailSubject.notEmpty}")
    @Size(min = 3, max = 100, message = "{form.contact.emailSubject.size}")
    private String emailSubject;

    @NotEmpty(message = "{form.contact.message.notEmpty}")
    @Size(min = 3, message = "{form.contact.message.size}")
    private String message;

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

}
