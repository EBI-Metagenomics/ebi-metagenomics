package uk.ac.ebi.interpro.metagenomics.memi.springmvc.session;

import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import java.io.Serializable;

/**
 * Represents a simple POJO which holds the current session user(submitter).
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class SessionBean implements Serializable {

    private Submitter submitter;

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    public void removeSubmitter() {
        this.submitter = null;
    }
}