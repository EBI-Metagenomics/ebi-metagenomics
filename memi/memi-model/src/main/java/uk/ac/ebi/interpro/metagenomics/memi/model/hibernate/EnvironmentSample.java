package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;


import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Entity
public class EnvironmentSample extends Sample {

    @Column(name = "LAT_LON")
    private String latLon;

    @Column(name = "Environment_BIOME")
    private String environmentalBiome;

    @Column(name = "Environment_FEATURE")
    private String environmentalFeature;

    @Column(name = "Environment_MATERIAL")
    private String environmentalMaterial;

    public String getLatLon() {
        return latLon;
    }

    public void setLatLon(String latLon) {
        this.latLon = latLon;
    }

    public String getEnvironmentalBiome() {
        return environmentalBiome;
    }

    public void setEnvironmentalBiome(String environmentalBiome) {
        this.environmentalBiome = environmentalBiome;
    }

    public String getEnvironmentalFeature() {
        return environmentalFeature;
    }

    public void setEnvironmentalFeature(String environmentalFeature) {
        this.environmentalFeature = environmentalFeature;
    }

    public String getEnvironmentalMaterial() {
        return environmentalMaterial;
    }

    public void setEnvironmentalMaterial(String environmentalMaterial) {
        this.environmentalMaterial = environmentalMaterial;
    }
}