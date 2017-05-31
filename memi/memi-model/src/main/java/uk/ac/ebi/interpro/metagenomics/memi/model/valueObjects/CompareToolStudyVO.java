package uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects;

/**
 * Created by maxim on 07/07/16.
 */
public class CompareToolStudyVO {
    private long studyId;

    private String externalStudyId;

    private String studyName;

    private int isPublic;

    private int runCount;

    public long getStudyId() {
        return studyId;
    }

    public void setStudyId(long studyId) {
        this.studyId = studyId;
    }

    public String getExternalStudyId() {
        return externalStudyId;
    }

    public void setExternalStudyId(String externalStudyId) {
        this.externalStudyId = externalStudyId;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public int getRunCount() {
        return runCount;
    }

    public void setRunCount(int runCount) {
        this.runCount = runCount;
    }
}