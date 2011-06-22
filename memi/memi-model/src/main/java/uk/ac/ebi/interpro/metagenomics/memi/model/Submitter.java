package uk.ac.ebi.interpro.metagenomics.memi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Should represents the submitter object from the ENA database,
 * but at the moment it is much simpler version.
 * TODO: Should be exactly mapped with the ENA submitter object
 * TODO: Add JPA and Hibernate annotations
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class Submitter implements Serializable {

    public final static String TABLE_NAME = "spin2006.submitter";

    private long submitterId;

    private String password;

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


    public Submitter() {
    }

    public Submitter(String firstName, String surname, String emailAddress, String password) {
        this();
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.password = password;
    }


    public long getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(long submitterId) {
        this.submitterId = submitterId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}