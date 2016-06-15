package uk.ac.ebi.interpro.metagenomics.memi.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 18/03/2016.
 */
public class EBISearchForm implements Serializable {

    public final static String MODEL_ATTR_NAME = "ebiSearchForm";

    String searchText;

    String previousSearchText;

    List<String> facets;

    int page;

    int maxPage;

    int resultsPerPage;

    String searchType;

    String sortField;

    Boolean ascendingOrder;

    String errorText;

    static final int DEFAULT_RESULTS_PER_PAGE = 20;

    public EBISearchForm() {
        page = 1;
        maxPage = 1;
        resultsPerPage = DEFAULT_RESULTS_PER_PAGE;
        facets = new ArrayList<String>();
        searchType = "projects";
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getPreviousSearchText() {
        previousSearchText = getSearchText();
        return previousSearchText;
    }

    public void setPreviousSearchText(String previousSearchText) {
        this.previousSearchText = previousSearchText;
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

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
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
