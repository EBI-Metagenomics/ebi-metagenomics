package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;


import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;

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

    @Column(name = "LATITUDE", precision = 7, scale = 4)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 7, scale = 4)
    private BigDecimal longitude;

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

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Class<? extends Sample> getClazz() {
        return this.getClass();
    }

    public SampleType getSampleType() {
        return Sample.SampleType.ENVIRONMENTAL;
    }
}