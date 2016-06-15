package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SearchViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISampleSearchResults;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISearchResults;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.util.List;

/**
 * Created by maq on 17/03/2016.
 */
public class SearchViewModelBuilder<ViewModel extends uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel> extends AbstractViewModelBuilder<ViewModel> {
    private final static Log log = LogFactory.getLog(SearchViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    String searchResults;

    public SearchViewModelBuilder(UserManager sessionMgr, EBISearchForm ebiSearchForm, String pageTitle,
                                  List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                                  String searchResults) {
        super(sessionMgr, ebiSearchForm);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.searchResults = searchResults;
    }

    @Override
    public ViewModel getModel() {
        log.info("Building instance of " + SearchViewModel.class + "...");
        return (ViewModel) new SearchViewModel(
                getSessionSubmitter(sessionMgr),
                getEbiSearchForm(),
                pageTitle,
                breadcrumbs,
                propertyContainer,
                searchResults);
    }
}