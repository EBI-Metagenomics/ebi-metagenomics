package uk.ac.ebi.interpro.metagenomics.memi.model;


import org.hibernate.annotations.Entity;

import javax.persistence.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents an EBI Metagenomics study object.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Table(name = "EMG_STUDY", schema = "EMG_USER")
@Entity
public class EmgStudy {

    /**
     * Maps all properties of this object. Useful if you like to iterate over all properties (used in the studyOverview JSP).
     * Property values are added by setter.
     */
    private SortedMap<String, Object> propertyMap;

    private final String STUDY_ID = "StudyId";

    private final String NCBI_PROJECT_ID = "NCBIProjectId";

    private final String STUDY_NAME = "StudyName";

    private final String SUBMITTER_ID = "SubmitterId";

    private final String SUBMITTER_NAME = "SubmitterName";

    private final String CENTRE_NAME = "CentreName";

    private final String STUDY_TYPE = "StudyType";

    private final String ASSOCIATED_PUBLICATIONS = "AssociatedPublications";

    private final String STUDY_ABSTRACT = "StudyAbstract";

    private final String EXPERIMENTAL_FACTOR = "ExperimentalFactor";

    private final String NUMBER_SAMPLES = "NumberSamples";

    private final String NUMBER_OF_SAMPLES_USED = "NumberOfSamplesUsed";

    private final String PUBLIC_RELEASE_DATE = "PublicReleaseDate";

    private final String STUDY_CONTACT_NAME = "StudyContactName";

    private final String STUDY_CONTACT_EMAIL = "StudyContactEmail";

    private final String STUDY_STATUS = "StudyStatus";


    public EmgStudy() {
        propertyMap = new TreeMap<String, Object>();
    }

    public EmgStudy(String studyId, String studyName, String studyType, StudyStatus studyStatus) {
        this();
        propertyMap.put(STUDY_ID, studyId);
        propertyMap.put(STUDY_NAME, studyName);
        propertyMap.put(STUDY_TYPE, studyType);
        propertyMap.put(STUDY_STATUS, studyStatus);
    }

    public Map<String, Object> getProperties() {
        return propertyMap;
    }

    @Column(name = "STUDY_ID", nullable = false, insertable = true, updatable = true, length = 9, precision = 0)
    @Basic
    @Id
    public String getStudyId() {
        return (String) propertyMap.get(STUDY_ID);
    }

    public void setStudyId(String studyId) {
        propertyMap.put(STUDY_ID, studyId);
    }

    @javax.persistence.Column(name = "NCBI_PROJECT", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @Basic
    public String getNcbiProject() {
        return (String) propertyMap.get(NCBI_PROJECT_ID);
    }

    public void setNcbiProject(String ncbiProject) {
        propertyMap.put(NCBI_PROJECT_ID, ncbiProject);
    }

    @javax.persistence.Column(name = "STUDY_NAME", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getStudyName() {
        return (String) propertyMap.get(STUDY_NAME);
    }

    public void setStudyName(String studyName) {
        propertyMap.put(STUDY_NAME, studyName);
    }

    @javax.persistence.Column(name = "SUBMITTER_ID", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @Basic
    public String getSubmitterId() {
        return (String) propertyMap.get(SUBMITTER_ID);
    }

    public void setSubmitterId(String submitterId) {
        propertyMap.put(SUBMITTER_ID, submitterId);
    }

    @javax.persistence.Column(name = "SUBMITTER_NAME", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @Basic
    public String getSubmitterName() {
        return (String) propertyMap.get(SUBMITTER_NAME);
    }

    public void setSubmitterName(String submitterName) {
        propertyMap.put(SUBMITTER_NAME, submitterName);
    }

    @javax.persistence.Column(name = "CENTRE_NAME", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @Basic
    public String getCentreName() {
        return (String) propertyMap.get(CENTRE_NAME);
    }

    public void setCentreName(String centreName) {
        propertyMap.put(CENTRE_NAME, centreName);
    }

    @javax.persistence.Column(name = "STUDY_TYPE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @Basic
    public String getStudyType() {
        return (String) propertyMap.get(STUDY_TYPE);
    }

    public void setStudyType(String studyType) {
        propertyMap.put(STUDY_TYPE, studyType);
    }

    @javax.persistence.Column(name = "ASSOCIATED_PUBLICATIONS", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    public String getAssociatedPublications() {
        return (String) propertyMap.get(ASSOCIATED_PUBLICATIONS);
    }

    public void setAssociatedPublications(String associatedPublications) {
        propertyMap.put(ASSOCIATED_PUBLICATIONS, associatedPublications);
    }

    @javax.persistence.Column(name = "STUDY_ABSTRACT", nullable = true, insertable = true, updatable = true, length = 4000, precision = 0)
    @Lob
    @Basic
    public String getStudyAbstract() {
        return (String) propertyMap.get(STUDY_ABSTRACT);
    }

    public void setStudyAbstract(String studyAbstract) {
        propertyMap.put(STUDY_ABSTRACT, studyAbstract);
    }

    @javax.persistence.Column(name = "EXPERIMENTAL_FACTOR", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @Basic
    public String getExperimentalFactor() {
        return (String) propertyMap.get(EXPERIMENTAL_FACTOR);
    }

    public void setExperimentalFactor(String experimentalFactor) {
        propertyMap.put(EXPERIMENTAL_FACTOR, experimentalFactor);
    }

    @javax.persistence.Column(name = "NUMBER_SAMPLES", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @Basic
    public String getNumberSamples() {
        return (String) propertyMap.get(NUMBER_SAMPLES);
    }

    public void setNumberSamples(String numberSamples) {
        propertyMap.put(NUMBER_SAMPLES, numberSamples);
    }

    @javax.persistence.Column(name = "NUMBER_OF_SAMPLES_USED", nullable = true, insertable = true, updatable = true, length = 22, precision = 0)
    @Basic
    public Integer getNumberOfSamplesUsed() {
        return (Integer) propertyMap.get(NUMBER_OF_SAMPLES_USED);
    }

    public void setNumberOfSamplesUsed(Integer numberOfSamplesUsed) {
        propertyMap.put(NUMBER_OF_SAMPLES_USED, numberOfSamplesUsed);
    }

    @javax.persistence.Column(name = "PUBLIC_RELEASE_DATE", nullable = true, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Date getPublicReleaseDate() {
        return (Date) propertyMap.get(PUBLIC_RELEASE_DATE);
    }

    @Transient
    public String getFormattedReleaseDate() {
        Format formatter = new SimpleDateFormat("dd.MMM.yyyy");
        Date result = (Date) propertyMap.get(PUBLIC_RELEASE_DATE);
        if (result != null) {
            return formatter.format(result);
        }
        return formatter.format(new Date());
    }

    public void setPublicReleaseDate(Date publicReleaseDate) {
        propertyMap.put(PUBLIC_RELEASE_DATE, publicReleaseDate);
    }

    @javax.persistence.Column(name = "STUDY_CONTACT_NAME", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @Basic
    public String getStudyContactName() {
        return (String) propertyMap.get(STUDY_CONTACT_NAME);
    }

    public void setStudyContactName(String studyContactName) {
        propertyMap.put(STUDY_CONTACT_NAME, studyContactName);
    }

    @javax.persistence.Column(name = "STUDY_CONTACT_EMAIL", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @Basic
    public String getStudyContactEmail() {
        return (String) propertyMap.get(STUDY_CONTACT_EMAIL);
    }

    public void setStudyContactEmail(String studyContactEmail) {
        propertyMap.put(STUDY_CONTACT_EMAIL, studyContactEmail);
    }

    @Transient
    public StudyStatus getStudyStatus() {
        return (StudyStatus) propertyMap.get(STUDY_STATUS);
    }

    public void setStudyStatus(StudyStatus studyStatus) {
        propertyMap.put(STUDY_STATUS, studyStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmgStudy emgStudy = (EmgStudy) o;

        if (this.getNumberOfSamplesUsed() != emgStudy.getNumberOfSamplesUsed()) return false;
        if (this.getAssociatedPublications() != null ? !this.getAssociatedPublications().equals(emgStudy.getAssociatedPublications()) : emgStudy.getAssociatedPublications() != null)
            return false;
        if (this.getCentreName() != null ? !this.getCentreName().equals(emgStudy.getCentreName()) : emgStudy.getCentreName() != null)
            return false;
        if (this.getExperimentalFactor() != null ? !this.getExperimentalFactor().equals(emgStudy.getExperimentalFactor()) : emgStudy.getExperimentalFactor() != null)
            return false;
        if (this.getNcbiProject() != null ? !this.getNcbiProject().equals(emgStudy.getNcbiProject()) : emgStudy.getNcbiProject() != null)
            return false;
        if (this.getNumberSamples() != null ? !this.getNumberSamples().equals(emgStudy.getNumberSamples()) : emgStudy.getNumberSamples() != null)
            return false;
        if (this.getPublicReleaseDate() != null ? !this.getPublicReleaseDate().equals(emgStudy.getPublicReleaseDate()) : emgStudy.getPublicReleaseDate() != null)
            return false;
        if (this.getStudyAbstract() != null ? !this.getStudyAbstract().equals(emgStudy.getStudyAbstract()) : emgStudy.getStudyAbstract() != null)
            return false;
        if (this.getStudyContactEmail() != null ? !this.getStudyContactEmail().equals(emgStudy.getStudyContactEmail()) : emgStudy.getStudyContactEmail() != null)
            return false;
        if (this.getStudyContactName() != null ? !this.getStudyContactName().equals(emgStudy.getStudyContactName()) : emgStudy.getStudyContactName() != null)
            return false;
        if (this.getStudyId() != null ? !this.getStudyId().equals(emgStudy.getStudyId()) : emgStudy.getStudyId() != null)
            return false;
        if (this.getStudyName() != null ? !this.getStudyName().equals(emgStudy.getStudyName()) : emgStudy.getStudyName() != null)
            return false;
        if (this.getStudyType() != null ? !this.getStudyType().equals(emgStudy.getStudyType()) : emgStudy.getStudyType() != null)
            return false;
        if (this.getSubmitterId() != null ? !this.getStudyType().equals(emgStudy.getStudyType()) : emgStudy.getStudyType() != null)
            return false;
        if (this.getSubmitterName() != null ? !this.getSubmitterName().equals(emgStudy.getSubmitterName()) : emgStudy.getSubmitterName() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getStudyId() != null ? getStudyId().hashCode() : 0;
        result = 31 * result + (getNcbiProject() != null ? getNcbiProject().hashCode() : 0);
        result = 31 * result + (getStudyName() != null ? getStudyName().hashCode() : 0);
        result = 31 * result + (getSubmitterId() != null ? getSubmitterId().hashCode() : 0);
        result = 31 * result + (getSubmitterName() != null ? getSubmitterName().hashCode() : 0);
        result = 31 * result + (getCentreName() != null ? getCentreName().hashCode() : 0);
        result = 31 * result + (getStudyType() != null ? getStudyType().hashCode() : 0);
        result = 31 * result + (getAssociatedPublications() != null ? getAssociatedPublications().hashCode() : 0);
        result = 31 * result + (getStudyAbstract() != null ? getStudyAbstract().hashCode() : 0);
        result = 31 * result + (getExperimentalFactor() != null ? getExperimentalFactor().hashCode() : 0);
        result = 31 * result + (getNumberSamples() != null ? getNumberSamples().hashCode() : 0);
        result = 31 * result + getNumberOfSamplesUsed();
        result = 31 * result + (getPublicReleaseDate() != null ? getPublicReleaseDate().hashCode() : 0);
        result = 31 * result + (getStudyContactName() != null ? getStudyContactName().hashCode() : 0);
        result = 31 * result + (getStudyContactEmail() != null ? getStudyContactEmail().hashCode() : 0);
        return result;
    }


    public enum StudyStatus {
        QUEUED(1, "Queued"), IN_PROCESS(2, "In process"), FINISHED(3, "Finished");

        private int studyStatusId;

        private String studyStatusName;

        private StudyStatus(int studyStatusId, String studyStatusName) {
            this.studyStatusId = studyStatusId;
            this.studyStatusName = studyStatusName;
        }

        public int getStudyStatusId() {
            return studyStatusId;
        }

        public void setStudyStatusId(int studyStatusId) {
            this.studyStatusId = studyStatusId;
        }

        public String getStudyStatusName() {
            return studyStatusName;
        }

        public void setStudyStatusName(String studyStatusName) {
            this.studyStatusName = studyStatusName;
        }
    }

    public enum StudyType {
        ENVIRONMENTAL(1, "Environmental"), HOST_ASSOCIATED(2, "Host associated"), UNDEFINED(3, "Undefined");

        private int studyTypeId;

        private String studyTypeName;

        private StudyType(int studyTypeId, String studyTypeName) {
            this.studyTypeId = studyTypeId;
            this.studyTypeName = studyTypeName;
        }


        public int getStudyTypeId() {
            return studyTypeId;
        }

        public void setStudyTypeId(int studyTypeId) {
            this.studyTypeId = studyTypeId;
        }

        public String getStudyTypeName() {
            return studyTypeName;
        }

        public void setStudyTypeName(String studyTypeName) {
            this.studyTypeName = studyTypeName;
        }
    }
}
