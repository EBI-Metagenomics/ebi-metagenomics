package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 21/03/2016.
 */
public class EBISampleSearchEntry {

    String identifier;
    URL url;
    String description;
    String project;
    String taxonomy;
    List<String> biomes = new ArrayList<String>();
    List<String> experimentTypes = new ArrayList<String>();

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public List<String> getBiomes() {
        return biomes;
    }

    public void setBiomes(List<String> biomes) {
        this.biomes = biomes;
    }

    public List<String> getExperimentTypes() {
        return experimentTypes;
    }

    public void setExperimentTypes(List<String> experimentTypes) {
        this.experimentTypes = experimentTypes;
    }
}
