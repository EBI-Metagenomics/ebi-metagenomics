package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import java.util.List;

/**
 * Represents the root object of the entire Metagenomics model.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MGModel {

    private Submitter submitter;

    private List<EmgStudy> studies;

    private LoginForm loginForm;

    public final static String MODEL_ATTR_NAME = "mgModel";

    public MGModel(Submitter submitter, List<EmgStudy> studies) {
        this.submitter = submitter;
        this.studies = studies;
        this.loginForm = new LoginForm();
    }

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    public List<EmgStudy> getStudies() {
        return studies;
    }

    public void setStudies(List<EmgStudy> studies) {
        this.studies = studies;
    }

    public LoginForm getLoginForm() {
        return loginForm;
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }
}