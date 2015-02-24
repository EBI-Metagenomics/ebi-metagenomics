package uk.ac.ebi.interpro.metagenomics.memi.model;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;

/**
 * //TODO: Add description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.3-SNAPSHOT
 */
public class Run implements SecureEntity {

    private String externalRunId;

    private String externalProjectId;

    private String externalSampleId;

    private Long sampleId;

    private boolean isPublic;

    private String submissionAccountId;

    private String latestAnalysisJobPipelineVersion;

    public String getExternalRunId() {
        return externalRunId;
    }

    public void setExternalRunId(String externalRunId) {
        this.externalRunId = externalRunId;
    }

    public String getSecureEntityId() {
        return getExternalRunId();
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getSubmissionAccountId() {
        return submissionAccountId;
    }

    public void setSubmissionAccountId(String submissionAccountId) {
        this.submissionAccountId = submissionAccountId;
    }

    public String getExternalProjectId() {
        return externalProjectId;
    }

    public void setExternalProjectId(String externalProjectId) {
        this.externalProjectId = externalProjectId;
    }

    public String getExternalSampleId() {
        return externalSampleId;
    }

    public void setExternalSampleId(String externalSampleId) {
        this.externalSampleId = externalSampleId;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public String getLatestAnalysisJobPipelineVersion() {
        return latestAnalysisJobPipelineVersion;
    }

    public void setLatestAnalysisJobPipelineVersion(String latestAnalysisJobPipelineVersion) {
        this.latestAnalysisJobPipelineVersion = latestAnalysisJobPipelineVersion;
    }
}