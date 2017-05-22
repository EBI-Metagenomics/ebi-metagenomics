package uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects;

/**
 * Created by maxim on 07/07/16.
 */
public class CompareToolStudyVO {
    private long studyId;

    private String externalStudyId;

    private String studyName;

    private int isPublic;

    public CompareToolStudyVO(long studyId, String externalStudyId, String studyName, Integer isPublic) {
        this.studyId = studyId;
        this.externalStudyId = externalStudyId;
        this.studyName = studyName;
        this.isPublic = isPublic;
    }

    public long getStudyId() {
        return studyId;
    }

    public String getExternalStudyId() {
        return externalStudyId;
    }

    public String getStudyName() {
        return studyName;
    }

    public int getIsPublic() {
        return isPublic;
    }
}