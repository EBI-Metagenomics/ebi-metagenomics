package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

/**
 * Represents an abstract instance of a view model builder. Each extended view model builder needs to implement the getModel() method.
 * The model builder is usually used to build the view model of controller class.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public abstract class AbstractViewModelBuilder<E extends ViewModel> implements ViewModelBuilder<E> {

    protected SessionManager sessionMgr;

    protected AbstractViewModelBuilder(SessionManager sessionMgr) {
        this.sessionMgr = sessionMgr;
    }

    protected Submitter getSessionSubmitter(SessionManager sessionMgr) {
        if (sessionMgr != null && sessionMgr.getSessionBean() != null) {
            return sessionMgr.getSessionBean().getSubmitter();
        }
        return null;
    }

    protected EBISearchForm getEbiSearchForm(SessionManager sessionMgr) {
        if (sessionMgr != null) {
            /*
            if (sessionMgr.getEbiSearchForm() == null) {
                EBISearchForm ebiSearchForm = new EBISearchForm();
                sessionMgr.setEbiSearchForm(ebiSearchForm);
            }
            */
            return sessionMgr.getEbiSearchForm();
        }
        return null;
    }
}