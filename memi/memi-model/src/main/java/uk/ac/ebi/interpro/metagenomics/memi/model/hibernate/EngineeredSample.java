package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;


import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Represents an engineered sample object, which extends abstract {@link uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample} class.
 * An engineered sample, is a sample which is taken from an engineered process (e.g. it could be a food sample or a sample taken
 * from a plastic production process).
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Entity
public class EngineeredSample extends Sample {

    @Column(name = "ENVIRONMENT_BIOME")
    private String environmentalBiome;

    @Column(name = "ENVIRONMENT_FEATURE")
    private String environmentalFeature;

    @Column(name = "ENVIRONMENT_MATERIAL")
    private String environmentalMaterial;

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
//        return SampleType.ENGINEERED;
//    }
}