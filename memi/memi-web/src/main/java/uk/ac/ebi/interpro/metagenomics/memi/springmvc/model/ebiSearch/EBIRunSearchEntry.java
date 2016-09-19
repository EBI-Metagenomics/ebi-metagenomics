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
    String pipelineVersion;
    String sample;
    String project;

    public final static String EXPERIMENT_TYPE = "experiment_type";
    public final static String PIPELINE_VERSION = "pipeline_version";
    public final static String SAMPLE = "METAGENOMICS_SAMPLE";
    public final static String PROJECT = "METAGENOMICS_PROJECT";

    /*
    List<String> organisms = new ArrayList<>();
    List<String> goTerms = new ArrayList<>();
    List<String> interproDomains = new ArrayList<>();
    */

    public String getIdentifier() { return identifier; }

    public void setIdentifier(String identifier) { this.identifier = identifier; }

    public URL getUrl() { return url; }

    public void setUrl(URL url) { this.url = url; }

    public String getExperimentType() { return experimentType; }

    public void setExperimentType(String experimentType) { this.experimentType = experimentType; }

    public String getPipelineVersion() {
        return pipelineVersion;
    }

    public void setPipelineVersion(String pipelineVersion) {
        this.pipelineVersion = pipelineVersion;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    /*
    public List<String> getOrganisms() { return organisms; }

    public void setOrganisms(List<String> organisms) { this.organisms = organisms; }

    public List<String> getGoTerms() { return goTerms; }

    public void setGoTerms(List<String> goTerms) { this.goTerms = goTerms; }

    public List<String> getInterproDomains() { return interproDomains; }

    public void setInterproDomains(List<String> interproDomains) { this.interproDomains = interproDomains; }
    */
}
