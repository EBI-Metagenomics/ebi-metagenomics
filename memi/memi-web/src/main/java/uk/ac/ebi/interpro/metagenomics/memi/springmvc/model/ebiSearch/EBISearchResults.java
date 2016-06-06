package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 03/06/2016.
 */
public class EBISearchResults {

    public enum SearchType {
        RUN, SAMPLE, PROJECT
    }

    //int numberOfSamples;
    //int numberOfProjects;
    //int numberOfRuns;
    int numberOfHits;

    public SearchType searchType;

    List<EBISampleSearchEntry> samples = new ArrayList<>();
    List<EBIProjectSearchEntry> projects = new ArrayList<>();
    List<EBIRunSearchEntry> runs = new ArrayList<>();
    List<EBISearchFacet> facets = new ArrayList<>();

    /*
    public int getNumberOfSamples() { return numberOfSamples; }

    public void setNumberOfSamples(int numberOfSamples) { this.numberOfSamples = numberOfSamples; }

    public int getNumberOfProjects() { return numberOfProjects; }

    public void setNumberOfProjects(int numberOfProjects) { this.numberOfProjects = numberOfProjects; }

    public int getNumberOfRuns() { return numberOfRuns; }

    public void setNumberOfRuns(int numberOfRuns) { this.numberOfRuns = numberOfRuns; }
    */
    public int getNumberOfHits() { return numberOfHits; }

    public void setNumberOfHits(int numberOfHits) { this.numberOfHits = numberOfHits; }

    public SearchType getSearchType() { return searchType; }

    public void setSearchType(SearchType searchType) { this.searchType = searchType; }

    public List<EBISampleSearchEntry> getSamples() { return samples; }

    public void setSamples(List<EBISampleSearchEntry> samples) { this.samples = samples; }

    public List<EBIProjectSearchEntry> getProjects() { return projects; }

    public void setProjects(List<EBIProjectSearchEntry> projects) { this.projects = projects; }

    public List<EBIRunSearchEntry> getRuns() { return runs; }

    public void setRuns(List<EBIRunSearchEntry> runs) { this.runs = runs; }

    public List<EBISearchFacet> getFacets() { return facets; }

    public void setFacets(List<EBISearchFacet> facets) { this.facets = facets; }
}
