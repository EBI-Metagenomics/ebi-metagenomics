package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 21/03/2016.
 */
public class EBISampleSearchEntry implements EBISearchEntry{

    String identifier;
    URL url;
    String name;
    String description;
    String project;
    String taxonomy;
    List<String> biomes = new ArrayList<String>();
    List<String> experimentTypes = new ArrayList<String>();

    public final static String OLD_NAME = "displayName";

    public final static String NAME = "name";
    public final static String DESCRIPTION = "description";
    public final static String CENTRE = "centre_name";
    public final static String TAXONOMY = "TAXONOMY";
    public final static String BIOME = "biome";
    public final static String EXPERIMENT = "experiment_type";
    public final static String PROJECT = "METAGENOMICS_PROJECT";
    public final static String OLD_PROJECT = "project";

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
