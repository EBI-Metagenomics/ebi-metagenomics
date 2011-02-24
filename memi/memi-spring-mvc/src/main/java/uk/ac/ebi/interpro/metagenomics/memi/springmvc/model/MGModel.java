package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

/**
 * Represents the root object of the entire Metagenomics model.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MGModel {

    private Submitter submitter;

    private LoginForm loginForm;

    private final String baseURL = "";

    public final static String MODEL_ATTR_NAME = "mgModel";

    MGModel(Submitter submitter) {
        this.submitter = submitter;
        this.loginForm = new LoginForm();
    }

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    public LoginForm getLoginForm() {
        return loginForm;
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }
}
