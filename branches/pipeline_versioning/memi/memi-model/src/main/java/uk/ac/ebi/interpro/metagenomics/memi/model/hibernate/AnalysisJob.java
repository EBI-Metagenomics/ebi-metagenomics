package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
public class AnalysisJob implements Comparator<AnalysisJob>, SecureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANALYSIS_JOB_SEQUENCE")
    @Column(name = "JOB_ID")
    @SequenceGenerator(
            name = "ANALYSIS_JOB_SEQUENCE",
            sequenceName = "ANALYSIS_JOB_SEQ")
    private long jobId;

    @Column(name = "JOB_OPERATOR", length = 15, nullable = false)
    private String jobOperator;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @Column(name = "RE_RUN_COUNT", precision = 2, scale = 0)
    private int reRunCount;

    @Column(name = "INPUT_FILE_NAME", length = 50, nullable = false)
    private String inputFileName;

    @Column(name = "RESULT_DIRECTORY", length = 100, nullable = false)
    private String resultDirectory;

    @Column(name = "EXTERNAL_RUN_IDS", length = 100)
    private String externalRunIDs;

    @ManyToOne
    @JoinColumn(name = "SAMPLE_ID")
    private Sample sample;

    @Column(name = "IS_PRODUCTION_RUN")
    private boolean isProductionRun;

    public AnalysisJob() {
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
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(completeTime.getTime());
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
        result = 31 * result + externalRunIDs.hashCode();
        result = 31 * result + (sample != null ? sample.hashCode() : 0);
        //TODO: Implement isProductionRun
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
                .append(getSample(), aj.getSample())
                .append(isProductionRun(), aj.isProductionRun())
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
    public String getSubmissionAccountId() {
        return (sample != null ? sample.getSubmissionAccountId() : "n/a");
    }
}