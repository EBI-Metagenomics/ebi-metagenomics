package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SearchViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISampleSearchResults;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.List;

/**
 * Created by maq on 17/03/2016.
 */
public class SearchViewModelBuilder<ViewModel extends uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel> extends AbstractViewModelBuilder<ViewModel> {
    private final static Log log = LogFactory.getLog(SearchViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private EBISearchForm search;

    private EBISampleSearchResults sampleSearchResults;

    public SearchViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer, EBISearchForm search, EBISampleSearchResults sampleSearchResults) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.search = search;
        this.sampleSearchResults = sampleSearchResults;
    }

    @Override
    public ViewModel getModel() {
        log.info("Building instance of " + SearchViewModel.class + "...");
        return (ViewModel) new SearchViewModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer, search, sampleSearchResults);
    }
}