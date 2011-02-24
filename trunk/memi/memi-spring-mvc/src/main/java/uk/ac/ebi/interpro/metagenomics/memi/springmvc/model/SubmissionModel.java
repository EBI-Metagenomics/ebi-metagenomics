package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.SubmissionForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

/**
 * Represents the model for the submission page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class SubmissionModel extends MGModel {

    private SubmissionForm subForm;

    SubmissionModel(Submitter submitter) {
        super(submitter);
        this.subForm = new SubmissionForm();
    }

    public SubmissionForm getSubForm() {
        return subForm;
    }

    public void setSubForm(SubmissionForm subForm) {
        this.subForm = subForm;
    }
}
