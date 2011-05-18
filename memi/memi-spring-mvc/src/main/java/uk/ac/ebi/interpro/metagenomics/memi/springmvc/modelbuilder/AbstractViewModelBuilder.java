package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

/**
 * Represents an abstract instance of a view model builder. Each extended view model builder needs to implement the getModel() method.
 * The model builder is usually used to build the view model of controller class.<br>
 * Example of how to use:<br>
 * ContactPageViewModelBuilder builder = new ContactPageViewModelBuilder(sessionManager, "Metagenomics Contact", getBreadcrumbs(null), propertyContainer);
 * final ContactViewModel contactPageViewModel = builder.getModel();
 * ModelMap model = new ModelMap();
 * model.addAttribute(ViewModel.MODEL_ATTR_NAME, contactPageViewModel);
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public abstract class AbstractViewModelBuilder<T extends ViewModel> {

    protected abstract T getModel();

    protected Submitter getSessionSubmitter(SessionManager sessionMgr) {
        if (sessionMgr != null && sessionMgr.getSessionBean() != null) {
            return sessionMgr.getSessionBean().getSubmitter();
        }
        return null;
    }
}