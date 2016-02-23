package uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects;

/**
 * Simple value object class.
 * <p/>
 * Created by maxim on 23/02/16.
 */
public class ProjectSampleRunMappingVO {

    private String studyId;

    private String sampleId;

    private String runId;

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    @Override
    public String toString() {
        final char DELIMITER = ',';
        return studyId + DELIMITER + sampleId + DELIMITER + runId;
    }
}