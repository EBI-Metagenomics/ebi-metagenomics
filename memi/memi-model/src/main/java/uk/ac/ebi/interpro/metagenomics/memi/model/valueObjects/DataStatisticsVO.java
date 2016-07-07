package uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects;

/**
 * Created by maxim on 07/07/16.
 */
public class DataStatisticsVO {
    private long numOfPublicStudies;

    private long numOfPublicSamples;

    private long numOfPublicRuns;

    private long numOfPrivateStudies;

    private long numOfPrivateSamples;

    private long numOfPrivateRuns;

    public DataStatisticsVO() {

    }

    public void setNumOfPublicStudies(long numOfPublicStudies) {
        this.numOfPublicStudies = numOfPublicStudies;
    }

    public void setNumOfPublicSamples(long numOfPublicSamples) {
        this.numOfPublicSamples = numOfPublicSamples;
    }

    public void setNumOfPublicRuns(long numOfPublicRuns) {
        this.numOfPublicRuns = numOfPublicRuns;
    }

    public void setNumOfPrivateStudies(long numOfPrivateStudies) {
        this.numOfPrivateStudies = numOfPrivateStudies;
    }

    public void setNumOfPrivateSamples(long numOfPrivateSamples) {
        this.numOfPrivateSamples = numOfPrivateSamples;
    }

    public void setNumOfPrivateRuns(long numOfPrivateRuns) {
        this.numOfPrivateRuns = numOfPrivateRuns;
    }

    public long getNumOfPublicStudies() {
        return numOfPublicStudies;
    }

    public long getNumOfPublicSamples() {
        return numOfPublicSamples;
    }

    public long getNumOfPublicRuns() {
        return numOfPublicRuns;
    }

    public long getNumOfPrivateStudies() {
        return numOfPrivateStudies;
    }

    public long getNumOfPrivateSamples() {
        return numOfPrivateSamples;
    }

    public long getNumOfPrivateRuns() {
        return numOfPrivateRuns;
    }
}
