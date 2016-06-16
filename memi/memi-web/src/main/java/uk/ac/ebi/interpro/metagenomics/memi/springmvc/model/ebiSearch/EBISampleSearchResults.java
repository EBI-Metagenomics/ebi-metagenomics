package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 21/03/2016.
 */
public class EBISampleSearchResults implements ISearchResults{

    int numberOfHits;
    int page = 1;
    int maxPage;
    int resultsPerPage = EBISearchResults.DEFAULT_RESULTS_PER_PAGE;

    List<EBISampleSearchEntry> entries = new ArrayList<EBISampleSearchEntry>();

    List<EBISearchFacet> facets = new ArrayList<EBISearchFacet>();

    List<String> checkedFacets = new ArrayList<>();

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public void setNumberOfHits(int numberOfHits) {
        this.numberOfHits = numberOfHits;
    }

    public List<EBISampleSearchEntry> getEntries() {
        return entries;
    }

    public List<EBISearchFacet> getFacets() { return facets; }

    public List<String> getCheckedFacets() { return checkedFacets; }

    public void setCheckedFacets(List<String> checkedFacets) { this.checkedFacets = checkedFacets; }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    @Override
    public int getResultsPerPage() {
        return resultsPerPage;
    }

    @Override
    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }
}
