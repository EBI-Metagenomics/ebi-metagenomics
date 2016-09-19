package uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects;

/**
 * Created by maxim on 07/07/16.
 */
public class StudyStatisticsVO {
    private long numOfPublicStudies;

    private long numOfPrivateStudies;

    public StudyStatisticsVO() {

    }

    public void setNumOfPublicStudies(long numOfPublicStudies) {
        this.numOfPublicStudies = numOfPublicStudies;
    }

    public void setNumOfPrivateStudies(long numOfPrivateStudies) {
        this.numOfPrivateStudies = numOfPrivateStudies;
    }

    public long getNumOfPublicStudies() {
        return numOfPublicStudies;
    }

    public long getNumOfPrivateStudies() {
        return numOfPrivateStudies;
    }
}