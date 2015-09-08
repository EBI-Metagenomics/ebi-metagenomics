package uk.ac.ebi.interpro.metagenomics.memi.model;

/**
 * Represents an experiment you can run on a sample.
 *
 * TODO: Will be mapped to an Oracle table eventually.
 *
 * @author Maxim Scheremetjew - EMBL-EBI
 */
public enum ExperimentTypeE {

    METAGENOMICS("metagenomic"), AMPLICON("amplicon"), METATRANSCRIPTOMIC("metatranscriptomic"), ASSEMBLY("assembly");

    private String experimentType;

    private ExperimentTypeE(String experimentType) {
        this.experimentType = experimentType;
    }

    public String getExperimentType() {
        return experimentType;
    }
}
