package uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects;

/**
 * Created by maxim on 07/07/16.
 */
public class RunStatisticsVO {
    private long numOfPublicRuns;

    private long numOfPrivateRuns;

    public RunStatisticsVO() {

    }

    public void setNumOfPublicRuns(long numOfPublicRuns) {
        this.numOfPublicRuns = numOfPublicRuns;
    }

    public void setNumOfPrivateRuns(long numOfPrivateRuns) {
        this.numOfPrivateRuns = numOfPrivateRuns;
    }

    public long getNumOfPublicRuns() {
        return numOfPublicRuns;
    }

    public long getNumOfPrivateRuns() {
        return numOfPrivateRuns;
    }
}
