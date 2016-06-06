package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISampleSearchResults;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISearchResults;

import java.util.List;

/**
 * Created by maq on 17/03/2016.
 */
public class SearchViewModel extends ViewModel {

    EBISearchResults searchResults;

    /**
     * Please notice to use this name for all the different model types. Otherwise the main menu would not work
     * fine.
     */
    public final static String MODEL_ATTR_NAME = "model";

    public SearchViewModel(Submitter submitter,
                           EBISearchForm ebiSearchForm,
                           String pageTitle,
                           List<Breadcrumb> breadcrumbs,
                           MemiPropertyContainer propertyContainer,
                           EBISearchResults searchResults) {
        super(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer);
        this.searchResults = searchResults;
    }

    public EBISearchResults getSearchResults() { return searchResults; }

    public void setSearchResults(EBISearchResults searchResults) { this.searchResults = searchResults; }
}
