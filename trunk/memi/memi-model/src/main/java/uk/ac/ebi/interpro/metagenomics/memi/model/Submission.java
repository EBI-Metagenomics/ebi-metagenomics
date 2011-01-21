package uk.ac.ebi.interpro.metagenomics.memi.model;

import java.util.Date;

/**
 * Represents a submission for hibernate Metagenomics studies.
 * TODO: Implement the JUni test for this class
 * TODO: Should be equal to the submitter object of the ENA database
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class Submission {

    private long submissionId;

    private long submitterId;

    /* Date of submission */
    private Date submitted;

    /* Submission message */
    private String submissionMsg;

    /* Description of the study data */
    private String dataDesc;

// Contact details of the submitter

    private String submitterName;

    /* Contact address of the submitter */
    private String emailAddress;


    public String getSubmissionMsg() {
        return submissionMsg;
    }

    public void setSubmissionMsg(String submissionMsg) {
        this.submissionMsg = submissionMsg;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    public long getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(long submitterId) {
        this.submitterId = submitterId;
    }

    public Date getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Date submitted) {
        this.submitted = submitted;
    }
}