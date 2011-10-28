package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.ContactForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import java.util.List;

/**
 * Represents the model for the contact page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class ContactViewModel extends ViewModel {

    private ContactForm contactForm;

    public ContactViewModel(Submitter submitter, String pageTitle, List<Breadcrumb> breadcrumbs,
                            MemiPropertyContainer propertyContainer) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.contactForm = new ContactForm();
    }

    public ContactForm getContactForm() {
        return contactForm;
    }

    public void setContactForm(ContactForm contactForm) {
        this.contactForm = contactForm;
    }
}
