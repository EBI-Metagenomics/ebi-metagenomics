package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.util.List;

/**
 * Created by maq on 16/06/2016.
 */
public interface ISearchResults {

    List<EBISearchFacet> getFacets();
    List<String> getCheckedFacets();
    int getPage();
    void setPage(int page);
    int getMaxPage();
    void setMaxPage(int maxPage);
    int getResultsPerPage();
    void setResultsPerPage(int resultsPerPage);
}
