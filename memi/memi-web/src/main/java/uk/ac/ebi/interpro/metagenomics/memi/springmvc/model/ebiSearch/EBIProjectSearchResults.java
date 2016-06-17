package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 06/05/2016.
 */
public class EBIProjectSearchResults implements ISearchResults{

    int numberOfHits;
    int page = 1;
    int maxPage;
    int resultsPerPage = EBISearchResults.DEFAULT_RESULTS_PER_PAGE;

    List<EBIProjectSearchEntry> entries = new ArrayList<>();

    List<EBISearchFacet> facets = new ArrayList<>();

    List<String> checkedFacets = new ArrayList<>();

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public void setNumberOfHits(int numberOfHits) {
        this.numberOfHits = numberOfHits;
    }

    public List<EBIProjectSearchEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<EBIProjectSearchEntry> entries) {
        this.entries = entries;
    }

    public List<EBISearchFacet> getFacets() {
        return facets;
    }

    public void setFacets(List<EBISearchFacet> facets) {
        this.facets = facets;
    }

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
