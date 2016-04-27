package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

/**
 * Represents an abstract instance of a view model builder. Each extended view model builder needs to implement the getModel() method.
 * The model builder is usually used to build the view model of controller class.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public abstract class AbstractViewModelBuilder<E extends ViewModel> implements ViewModelBuilder<E> {

    protected UserManager sessionMgr;

    private EBISearchForm ebiSearchForm;

    protected AbstractViewModelBuilder(UserManager sessionMgr, EBISearchForm ebiSearchForm) {
        this.sessionMgr = sessionMgr;
        this.ebiSearchForm = ebiSearchForm;
    }

    protected Submitter getSessionSubmitter(UserManager sessionMgr) {
        if (sessionMgr != null && sessionMgr.getUserAuthentication() != null) {
            return sessionMgr.getUserAuthentication().getSubmitter();
        }
        return null;
    }

    public EBISearchForm getEbiSearchForm() {
        return ebiSearchForm;
    }
}