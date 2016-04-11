package uk.ac.ebi.interpro.metagenomics.memi.springmvc.session;

import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;

/**
 * Represents a proper session bean manager for the entire Metagenomics project.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class SessionManager {
    private SessionBean sessionBean;

    private EBISearchForm ebiSearchForm;

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public EBISearchForm getEbiSearchForm() {
        return ebiSearchForm;
    }

    public void setEbiSearchForm(EBISearchForm ebiSearchForm) {
        this.ebiSearchForm = ebiSearchForm;
    }
}