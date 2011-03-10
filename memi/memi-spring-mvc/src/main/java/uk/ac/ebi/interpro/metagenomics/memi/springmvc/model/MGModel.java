package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
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

    private LoginForm loginForm;

    private final String baseURL = "";

    private List<Breadcrumb> breadcrumbs;

    /**
     * Please notice to use this name for all the different model types. Otherwise the main menu would not work
     * fine.
     */
    public final static String MODEL_ATTR_NAME = "model";


    MGModel(Submitter submitter, List<Breadcrumb> breadcrumbs) {
        this.submitter = submitter;
        this.breadcrumbs = breadcrumbs;
        this.loginForm = new LoginForm();
    }

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    public List<Breadcrumb> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<Breadcrumb> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public LoginForm getLoginForm() {
        return loginForm;
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }
}
