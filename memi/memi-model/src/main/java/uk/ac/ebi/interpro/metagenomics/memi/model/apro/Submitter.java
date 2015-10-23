package uk.ac.ebi.interpro.metagenomics.memi.model.apro;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a logged in user object.
 * TODO: Add JPA and Hibernate annotations - Why? The submitter is requested by plain Jdbc.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class Submitter implements Serializable {

//    private long submitterId;

    private String firstName;

    private String middleInitials;

    private String surname;

    private String country;

    private String telNO;

    private String fax;

    private String emailAddress;

    private String address;

    private Date auditTime;

    private String auditUser;

    private char active;

    private String loginName;

    private String sessionId;

    private String submissionAccountId;

    private boolean mainContact;

    /* Represents role_metagenome_analysis column in the erapro submission_account table. */
    boolean consentGiven;

    /* Represents role_metagenome_submitter column in the erapro submission_account table. */
    private boolean registered;

    private String laboratoryName;

    private String centreName;


    public Submitter() {
    }

    public Submitter(String loginName, String sessionId, String submissionAccountId) {
        this.loginName = loginName;
        this.sessionId = sessionId;
        this.submissionAccountId = submissionAccountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMiddleInitials() {
        return middleInitials;
    }

    public void setMiddleInitials(String middleInitials) {
        this.middleInitials = middleInitials;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelNO() {
        return telNO;
    }

    public void setTelNO(String telNO) {
        this.telNO = telNO;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public char getActive() {
        return active;
    }

    public void setActive(char active) {
        this.active = active;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSubmissionAccountId() {
        return submissionAccountId;
    }

    public void setSubmissionAccountId(String submissionAccountId) {
        this.submissionAccountId = submissionAccountId;
    }

    public boolean isConsentGiven() {
        return consentGiven;
    }

    public void setConsentGiven(boolean consentGiven) {
        this.consentGiven = consentGiven;
    }

    public boolean isMainContact() {
        return mainContact;
    }

    public void setMainContact(boolean mainContact) {
        this.mainContact = mainContact;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }
}