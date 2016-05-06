package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 06/05/2016.
 */
public class EBIProjectSearchResults {

    int numberOfHits;

    List<EBIProjectSearchEntry> entries = new ArrayList<>();

    List<EBISearchFacet> facets = new ArrayList<>();

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
}
