package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
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

    //TODO: What is the base URL for?
    private final String baseURL = "";

    private List<Breadcrumb> breadcrumbs;

    private String pageTitle;

    private final MemiPropertyContainer propertyContainer;

    /**
     * Please notice to use this name for all the different model types. Otherwise the main menu would not work
     * fine.
     */
    public final static String MODEL_ATTR_NAME = "model";


    MGModel(Submitter submitter, String pageTitle, List<Breadcrumb> breadcrumbs,
            MemiPropertyContainer propertyContainer) {
        this.submitter = submitter;
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.loginForm = new LoginForm();
        this.propertyContainer = propertyContainer;
    }

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
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

    public MemiPropertyContainer getPropertyContainer() {
        return propertyContainer;
    }
}
