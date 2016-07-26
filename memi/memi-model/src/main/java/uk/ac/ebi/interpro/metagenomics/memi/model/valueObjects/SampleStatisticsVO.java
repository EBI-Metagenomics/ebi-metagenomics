package uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects;

/**
 * Created by maxim on 07/07/16.
 */
public class SampleStatisticsVO {
    private long numOfPublicSamples;
    private long numOfPrivateSamples;

    public SampleStatisticsVO() {

    }

    public void setNumOfPublicSamples(long numOfPublicSamples) {
        this.numOfPublicSamples = numOfPublicSamples;
    }

    public void setNumOfPrivateSamples(long numOfPrivateSamples) {
        this.numOfPrivateSamples = numOfPrivateSamples;
    }

    public long getNumOfPublicSamples() {
        return numOfPublicSamples;
    }

    public long getNumOfPrivateSamples() {
        return numOfPrivateSamples;
    }
}
