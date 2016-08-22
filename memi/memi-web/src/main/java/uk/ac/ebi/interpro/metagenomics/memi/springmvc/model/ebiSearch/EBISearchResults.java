package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 03/06/2016.
 */
public class EBISearchResults {

    static final int DEFAULT_RESULTS_PER_PAGE = 20;

    String[] dataTypes = {"projects", "samples", "runs"};
    EBISampleSearchResults samples;
    EBIProjectSearchResults projects;
    EBIRunSearchResults runs;
    String searchText;
    String searchType;
    int resultsPerPage;
    String error;

    public EBISearchResults() {
        samples = new EBISampleSearchResults();
        projects = new EBIProjectSearchResults();
        runs = new EBIRunSearchResults();
        searchText = "";
        resultsPerPage = DEFAULT_RESULTS_PER_PAGE;
    }

    public String[] getDataTypes() {
        return dataTypes;
    }

    public EBISampleSearchResults getSamples() {
        return samples;
    }

    public void setSamples(EBISampleSearchResults samples) {
        this.samples = samples;
    }

    public EBIProjectSearchResults getProjects() {
        return projects;
    }

    public void setProjects(EBIProjectSearchResults projects) {
        this.projects = projects;
    }

    public EBIRunSearchResults getRuns() {
        return runs;
    }

    public void setRuns(EBIRunSearchResults runs) {
        this.runs = runs;
    }

    public String getSearchText() { return searchText; }

    public void setSearchText(String searchText) { this.searchText = searchText; }

    public String getSearchType() { return searchType; }

    public void setSearchType(String searchType) { this.searchType = searchType; }

    public int getResultsPerPage() { return resultsPerPage; }

    public void setResultsPerPage(int resultsPerPage) { this.resultsPerPage = resultsPerPage; }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
