package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;


import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Represents an environmental sample object, which extends abstract {@link Sample} class.
 * Replaces {@link uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample} object.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Entity
public class EnvironmentSample extends Sample {

    //TODO: Needs to be removed in the future. Replaced by latitude and longitude attributes.
    @Column(name = "LAT_LON")
    private String latLon;

    @Column(name = "ENVIRONMENT_BIOME")
    private String environmentalBiome;

    @Column(name = "ENVIRONMENT_FEATURE")
    private String environmentalFeature;

    @Column(name = "ENVIRONMENT_MATERIAL")
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

    public Class<? extends Sample> getClazz() {
        return this.getClass();
    }

//    public SampleType getSampleType() {
//        return Sample.SampleType.ENVIRONMENTAL;
//    }
}