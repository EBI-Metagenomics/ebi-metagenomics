package uk.ac.ebi.interpro.metagenomics.memi.basic;

/**
 * Represents a simple object to save all ENA related submission URLs.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ENASubmissionURL {
    private String registrationPageName;

    private String editPrefsPageName;

    private String forgottenPasswordPageName;

    private String mainURL;

    public String getRegistrationLink() {
        return mainURL + registrationPageName;
    }

    public String getEditPrefsLink() {
        return mainURL + editPrefsPageName;
    }

    /**
     * Forgotten password link
     */
    public String getForgottenPwdLink() {
        return mainURL + forgottenPasswordPageName;
    }

    public void setRegistrationPageName(String registrationPageName) {
        this.registrationPageName = registrationPageName;
    }

    public void setEditPrefsPageName(String editPrefsPageName) {
        this.editPrefsPageName = editPrefsPageName;
    }

    public void setForgottenPasswordPageName(String forgottenPasswordPageName) {
        this.forgottenPasswordPageName = forgottenPasswordPageName;
    }

    public void setMainURL(String mainURL) {
        this.mainURL = mainURL;
    }
}