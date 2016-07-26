package uk.ac.ebi.interpro.metagenomics.memi.dao.extensions;

/**
 * Holds the results of the database query.
 */
public class QueryRunsForProjectResult {

    // aj.sample_id, sa.ext_sample_id, tmp.ct, aj.external_run_ids, aj.experiment_type, sa.submission_account_id, sa.is_public, r.release_version

    private long sampleId;
    private String externalSampleId;
    private String sampleName;
    private String sampleDescription;
    private int runCount;
    private String externalRunIds;
    private String experimentType;
    private String submissionAccountId;
    private int isPublic;
    private String releaseVersion;
    private String analysisStatus;
    private String instrumentModel;

    public long getSampleId() {
        return sampleId;
    }

    public void setSampleId(long sampleId) {
        this.sampleId = sampleId;
    }

    public String getExternalSampleId() {
        return externalSampleId;
    }

    public void setExternalSampleId(String externalSampleId) {
        this.externalSampleId = externalSampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleDescription() {
        return sampleDescription;
    }

    public void setSampleDescription(String sampleDescription) {
        this.sampleDescription = sampleDescription;
    }

    public int getRunCount() {
        return runCount;
    }

    public void setRunCount(int runCount) {
        this.runCount = runCount;
    }

    public String getExternalRunIds() {
        return externalRunIds;
    }

    public void setExternalRunIds(String externalRunIds) {
        this.externalRunIds = externalRunIds;
    }

    public String getExperimentType() {
        return experimentType;
    }

    public void setExperimentType(String experimentType) {
        this.experimentType = experimentType;
    }

    public String getSubmissionAccountId() {
        return submissionAccountId;
    }

    public void setSubmissionAccountId(String submissionAccountId) {
        this.submissionAccountId = submissionAccountId;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public String getAnalysisStatus() {
        return analysisStatus;
    }

    public void setAnalysisStatus(String analysisStatus) {
        this.analysisStatus = analysisStatus;
    }

    public boolean isPublic() {
        if (isPublic == 1) {
            return true;
        }
        return false;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public String getInstrumentModel() {
        return instrumentModel;
    }

    public void setInstrumentModel(String instrumentModel) {
        this.instrumentModel = instrumentModel;
    }
}