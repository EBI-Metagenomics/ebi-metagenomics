package uk.ac.ebi.interpro.metagenomics.memi.forms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 18/03/2016.
 */
public class EBISearchForm {

    public final static String MODEL_ATTR_NAME = "ebiSearchForm";

    String searchText;

    List<String> facets;

    int page;

    int resultsPerPage;

    String sortField;

    Boolean ascendingOrder;

    String errorText;

    static final int DEFAULT_RESULTS_PER_PAGE = 20;

    public EBISearchForm() {
        page = 0;
        resultsPerPage = DEFAULT_RESULTS_PER_PAGE;
        facets = new ArrayList<String>();
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<String> getFacets() {
        return facets;
    }

    public void setFacets(List<String> facets) {
        this.facets = facets;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public Boolean getAscendingOrder() {
        return ascendingOrder;
    }

    public void setAscendingOrder(Boolean ascendingOrder) {
        this.ascendingOrder = ascendingOrder;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
}
