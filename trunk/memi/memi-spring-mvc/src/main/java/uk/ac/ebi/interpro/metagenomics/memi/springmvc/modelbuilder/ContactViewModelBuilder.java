package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ContactViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.List;

public class ContactViewModelBuilder extends AbstractViewModelBuilder<ContactViewModel> {

    private final static Log log = LogFactory.getLog(ContactViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    public ContactViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
    }

    @Override
    public ContactViewModel getModel() {
        log.info("Building instance of " + ContactViewModel.class + "...");
        return new ContactViewModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer);
    }
}