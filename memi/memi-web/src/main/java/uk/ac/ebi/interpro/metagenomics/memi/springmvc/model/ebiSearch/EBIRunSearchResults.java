package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 09/06/2016.
 */
public class EBIRunSearchResults {

    int numberOfHits;

    List<EBISampleSearchEntry> entries = new ArrayList<EBISampleSearchEntry>();

    List<EBISearchFacet> facets = new ArrayList<EBISearchFacet>();

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public void setNumberOfHits(int numberOfHits) {
        this.numberOfHits = numberOfHits;
    }

    public List<EBISampleSearchEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<EBISampleSearchEntry> entries) {
        this.entries = entries;
    }

    public List<EBISearchFacet> getFacets() {
        return facets;
    }

    public void setFacets(List<EBISearchFacet> facets) {
        this.facets = facets;
    }
}
