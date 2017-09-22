package uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects;

import uk.ac.ebi.interpro.metagenomics.memi.model.IAnalysisJob;

/**
 * Simple value object class.
 * <p>
 * Created by maxim on 21/01/16.
 */
public class AnalysisJobVO implements IAnalysisJob {

    private String resultDirectory;

    private String inputFileName;

    private long jobId;

    private String externalRunIDs;

    private String sampleName;

    private String sampleAlias;

    private String experimentType;

    private String releaseVersion;

    public String getResultDirectory() {
        return resultDirectory;
    }

    public void setResultDirectory(String resultDirectory) {
        this.resultDirectory = resultDirectory;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getExternalRunIDs() {
        return externalRunIDs;
    }

    public void setExternalRunIDs(String externalRunIDs) {
        this.externalRunIDs = externalRunIDs;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleAlias() {
        return sampleAlias;
    }

    public void setSampleAlias(String sampleAlias) {
        this.sampleAlias = sampleAlias;
    }

    public String getExperimentType() {
        return experimentType;
    }

    public void setExperimentType(String experimentType) {
        this.experimentType = experimentType;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }
}