package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.model.IAnalysisJob;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;


/**
 * Represents an analysis job object (submitted by the MGPipeline).
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.3-SNAPSHOT
 */
@Entity
@Table(name = "ANALYSIS_JOB")
public class AnalysisJob implements Comparator<AnalysisJob>, SecureEntity, IAnalysisJob {

    @Id
    @Column(name = "JOB_ID", columnDefinition = "BIGINT(20)")
    private long jobId;

    @Column(name = "JOB_OPERATOR", length = 15, nullable = false)
    private String jobOperator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PIPELINE_ID", nullable = false)
    private PipelineRelease pipelineRelease;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SUBMIT_TIME", nullable = false)
    private Calendar submitTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "COMPLETE_TIME")
    private Calendar completeTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ANALYSIS_STATUS_ID", nullable = false)
    private AnalysisStatus analysisStatus;

    @Column(name = "RE_RUN_COUNT", columnDefinition = "TINYINT(4)")
    private int reRunCount;

    @Column(name = "INPUT_FILE_NAME", length = 50, nullable = false)
    private String inputFileName;

    @Column(name = "RESULT_DIRECTORY", length = 100, nullable = false)
    private String resultDirectory;

    @Column(name = "EXTERNAL_RUN_IDS", length = 100)
    private String externalRunIDs;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "EXPERIMENT_TYPE_ID", nullable = false)
    private ExperimentType experimentType;

//    @Column(name = "EXPERIMENT_TYPE", length = 30, nullable = false)
//    private String experimentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SAMPLE_ID")
    private Sample sample;

    @Column(name = "IS_PRODUCTION_RUN")
    private boolean isProductionRun;

    @Column(name = "SECONDARY_ACCESSION", columnDefinition = "VARCHAR(20)")
    private String secondaryAccession;

    @Column(name = "STUDY_ID", columnDefinition = "INT(11)", nullable = false)
    private Long studyId;

    @Column(name = "RUN_STATUS_ID", columnDefinition = "TINYINT(4)")
    private int runStatusId;


    public AnalysisJob() {
    }

    public AnalysisJob(String jobOperator, PipelineRelease pipelineRelease, Calendar submitTime,
                       AnalysisStatus analysisStatus, String inputFileName, String resultDirectory,
                       ExperimentType experimentType) {
        this.jobOperator = jobOperator;
        this.pipelineRelease = pipelineRelease;
        this.submitTime = submitTime;
        this.analysisStatus = analysisStatus;
        this.inputFileName = inputFileName;
        this.resultDirectory = resultDirectory;
        this.experimentType = experimentType;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getJobOperator() {
        return jobOperator;
    }

    public void setJobOperator(String jobOperator) {
        this.jobOperator = jobOperator;
    }

    public PipelineRelease getPipelineRelease() {
        return pipelineRelease;
    }

    public void setPipelineRelease(PipelineRelease pipelineRelease) {
        this.pipelineRelease = pipelineRelease;
    }

    public Calendar getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Calendar submitTime) {
        this.submitTime = submitTime;
    }

    public String getCompleteTime() {
        if (this.completeTime != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(this.completeTime.getTime());
        } else {
            return "N/A";
        }
    }

    public void setCompleteTime(Calendar completeTime) {
        this.completeTime = completeTime;
    }

    public AnalysisStatus getAnalysisStatus() {
        return analysisStatus;
    }

    public void setAnalysisStatus(AnalysisStatus analysisStatus) {
        this.analysisStatus = analysisStatus;
    }

    public int getReRunCount() {
        return reRunCount;
    }

    public void setReRunCount(int reRunCount) {
        this.reRunCount = reRunCount;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public String getResultDirectory() {
        return resultDirectory;
    }

    public void setResultDirectory(String resultDirectory) {
        this.resultDirectory = resultDirectory;
    }

    public String getExternalRunIDs() {
        return externalRunIDs;
    }

    public void setExternalRunIDs(String externalRunIDs) {
        this.externalRunIDs = externalRunIDs;
    }

    public ExperimentType getExperimentType() {
        return experimentType;
    }

    public void setExperimentType(ExperimentType experimentType) {
        this.experimentType = experimentType;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public boolean isProductionRun() {
        return isProductionRun;
    }

    public void setProductionRun(boolean productionRun) {
        isProductionRun = productionRun;
    }

    public Long getStudyId() {
        return studyId;
    }

    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }

    public String getSecondaryAccession() {
        return secondaryAccession;
    }

    public void setSecondaryAccession(String secondaryAccession) {
        this.secondaryAccession = secondaryAccession;
    }


    public int getRunStatusId() {
        return runStatusId;
    }

    public void setRunStatusId(int runStatusId) {
        this.runStatusId = runStatusId;
    }

    @Override
    public int hashCode() {
        int result = (int) (jobId ^ (jobId >>> 32));
        result = 31 * result + jobOperator.hashCode();
        result = 31 * result + pipelineRelease.hashCode();
        result = 31 * result + submitTime.hashCode();
        result = 31 * result + (completeTime != null ? completeTime.hashCode() : 0);
        result = 31 * result + analysisStatus.hashCode();
        result = 31 * result + reRunCount;
        result = 31 * result + inputFileName.hashCode();
        result = 31 * result + resultDirectory.hashCode();
        result = 31 * result + (externalRunIDs != null ? externalRunIDs.hashCode() : 0);
        result = 31 * result + experimentType.hashCode();
        result = 31 * result + (sample != null ? sample.hashCode() : 0);
        result = 31 * result + Long.hashCode(studyId);
        result = 31 * result + (secondaryAccession != null ? secondaryAccession.hashCode() : 0);
        result = 31 * result + Integer.hashCode(runStatusId);

//        result = 31 * result + Boolean.isProductionRun;
        return result;
    }

    //TODO: Implement
    public int compare(AnalysisJob o1, AnalysisJob o2) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        AnalysisJob aj = (AnalysisJob) obj;
        return new EqualsBuilder()
                .append(getJobId(), aj.getJobId())
                .append(getJobOperator(), aj.getJobOperator())
                .append(getPipelineRelease(), aj.getPipelineRelease())
                .append(getSubmitTime(), aj.getSubmitTime())
                .append(getCompleteTime(), aj.getCompleteTime())
                .append(getAnalysisStatus(), aj.getAnalysisStatus())
                .append(getReRunCount(), aj.getReRunCount())
                .append(getInputFileName(), aj.getInputFileName())
                .append(getResultDirectory(), aj.getResultDirectory())
                .append(getExternalRunIDs(), aj.getExternalRunIDs())
                .append(getExperimentType(), aj.getExperimentType())
                .append(getSample(), aj.getSample())
                .append(isProductionRun(), aj.isProductionRun())
                .append(getSecondaryAccession(), aj.getSecondaryAccession())
                .append(getStudyId(), aj.getStudyId())
                .append(getRunStatusId(), aj.getRunStatusId())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("jobId", getJobId()).
                append("jobOperator", getJobOperator()).
                append("releaseVersion", getPipelineRelease().getReleaseVersion()).
                append("analysisStatus", getAnalysisStatus().getAnalysisStatus()).
                append("submitTime", getSubmitTime()).
                append("completeTime", getCompleteTime()).
                //TODO: Add more
                        toString();
    }

    @Transient
    public String getSecureEntityId() {
        return getExternalRunIDs();
    }

    @Transient
    public boolean isPublic() {
        return (sample != null ? sample.isPublic() : false);
    }

    @Transient
    public Integer isPublicInt() {
        return (sample != null ? sample.isPublicInt() : 0);
    }

    @Transient
    public String getSubmissionAccountId() {
        return (sample != null ? sample.getSubmissionAccountId() : "n/a");
    }

    public enum AnalysisJobStatus {
        COMPLETED("completed");

        private String status;

        private AnalysisJobStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}