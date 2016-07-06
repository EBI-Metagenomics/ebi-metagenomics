package uk.ac.ebi.interpro.metagenomics.memi.springmvc.session;

import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;

/**
 * Represents a proper session bean manager for the entire Metagenomics project.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class UserManager {
    private UserAuthentication userAuthentication;

    public UserAuthentication getUserAuthentication() {
        return userAuthentication;
    }

    public void setUserAuthentication(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;
    }
}