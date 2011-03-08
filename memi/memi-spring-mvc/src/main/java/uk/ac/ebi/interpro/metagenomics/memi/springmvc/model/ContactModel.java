package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.ContactForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

/**
 * Represents the model for the contact page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class ContactModel extends MGModel {

    private ContactForm contactForm;

    ContactModel(Submitter submitter) {
        super(submitter);
        this.contactForm = new ContactForm();
    }

    public ContactForm getContactForm() {
        return contactForm;
    }

    public void setContactForm(ContactForm contactForm) {
        this.contactForm = contactForm;
    }
}
