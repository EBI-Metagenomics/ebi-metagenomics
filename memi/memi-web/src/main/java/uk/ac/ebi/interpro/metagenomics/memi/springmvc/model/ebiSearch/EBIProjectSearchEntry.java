package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 06/05/2016.
 */
public class EBIProjectSearchEntry implements EBISearchEntry {

    String identifier;
    URL url;
    String name;
    String description;
    String centreName;

    List<String> samples = new ArrayList<String>();
    List<String> biomes = new ArrayList<String>();

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

    public List<String> getSamples() {
        return samples;
    }

    public void setSamples(List<String> samples) {
        this.samples = samples;
    }

    public List<String> getBiomes() {
        return biomes;
    }

    public void setBiomes(List<String> biomes) {
        this.biomes = biomes;
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }
}
