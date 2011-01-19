package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.SubmissionForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import java.util.List;

/**
 * Represents the model for the submission page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class SubmissionModel extends MGModel {

    private SubmissionForm subForm;

    public SubmissionModel(Submitter submitter, List<EmgStudy> studies) {
        super(submitter, studies);
        this.subForm = new SubmissionForm();
    }

    public SubmissionForm getSubForm() {
        return subForm;
    }

    public void setSubForm(SubmissionForm subForm) {
        this.subForm = subForm;
    }
}
