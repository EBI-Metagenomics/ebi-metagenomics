package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 03/06/2016.
 */
public class EBIRunSearchEntry implements EBISearchEntry {
    String identifier;
    URL url;
    String experimentType;
    List<String> organisms = new ArrayList<>();
    List<String> goTerms = new ArrayList<>();
    List<String> interproDomains = new ArrayList<>();

    public String getIdentifier() { return identifier; }

    public void setIdentifier(String identifier) { this.identifier = identifier; }

    public URL getUrl() { return url; }

    public void setUrl(URL url) { this.url = url; }

    public String getExperimentType() { return experimentType; }

    public void setExperimentType(String experimentType) { this.experimentType = experimentType; }

    public List<String> getOrganisms() { return organisms; }

    public void setOrganisms(List<String> organisms) { this.organisms = organisms; }

    public List<String> getGoTerms() { return goTerms; }

    public void setGoTerms(List<String> goTerms) { this.goTerms = goTerms; }

    public List<String> getInterproDomains() { return interproDomains; }

    public void setInterproDomains(List<String> interproDomains) { this.interproDomains = interproDomains; }
}
