package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;


import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Hibernate generated study table.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Entity
@Table(name = "STUDY")
public class Study implements SecureEntity {

    @Id
    @Column(name = "STUDY_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STUDY_SEQ")
    @SequenceGenerator(
            name = "STUDY_SEQ",
            sequenceName = "STUDY_SEQ")
//            allocationSize = 1
    private long id;

    @Column(name = "EXT_STUDY_ID", length = 18, nullable = false)
    private String studyId;

    @Column(name = "STUDY_NAME")
    private String studyName;

    /**
     * NCBI BioProject ID
     */
    @Column(name = "NCBI_PROJECT_ID")
    private Long ncbiProjectId;

    //Deprecated - Replaced by  submissionAccountId
//    @Column(name = "SUBMITTER_ID")
//    private Long submitterId;

    @Column(name = "SUBMISSION_ACCOUNT_ID")
    private String submissionAccountId;

    @Column(name = "STUDY_Status", length = 30)
    @Enumerated(EnumType.STRING)
    private StudyStatus studyStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "PUBLIC_RELEASE_DATE")
    private Date publicReleaseDate;

    /**
     * Date when we received the last meta data or a substantial update.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_UPDATE", columnDefinition = "DATE DEFAULT CURRENT_DATE", nullable = false)
    private Date lastMetadataReceived;

    /**
     * Associated publication.
     */
    @ManyToMany
    private Set<Publication> publications;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "study")
    @Column(name = "Study_ID")
    private Set<Sample> samples;

    @Column(name = "CENTRE_NAME")
    private String centreName;

    @Column(name = "STUDY_ABSTRACT")
    @Lob
    private String studyAbstract;

    @Column(name = "EXPERIMENTAL_FACTOR")
    private String experimentalFactor;

    /**
     * Default value is private.
     */
    @Column(name = "IS_PUBLIC")
    private boolean isPublic;

    /**
     * Holds an optional URL to the study's website.
     */
    @Column(name = "STUDY_LINKOUT")
    private String studyPageURL;

    /**
     * Specifies the origination of the study.<br>
     * Submitted: Somebody submitted his Metagenomics project to us<br>
     * Mirrored: We integrated a Metagenomics project from an external service provider for instance MG-Rast<br>
     * Harvested: Nucleotid sequences submitted to the ENA.
     */
    @Column(name = "DATA_ORIGINATION", length = 20)
    @Enumerated(EnumType.STRING)
    private DataOrigination dataOrigination;

    @Column(name = "AUTHOR_NAME", length = 100)
    private String authorName;

    @Column(name = "AUTHOR_EMAIL", length = 100)
    private String authorEmailAddress;

    @Transient
    private Long sampleSize;

    /**
     * Submitted - Directly submitted to us (EBI Metagenomics).
     * <p/>
     * Harvested - Study already existed in ENA/SRA.
     * <p/>
     * Mirrored - External source e.g. MGRast.
     */
    public enum DataOrigination {
        SUBMITTED, HARVESTED, MIRRORED;
    }

    public Study() {
        publications = new HashSet<Publication>();
        samples = new HashSet<Sample>();
        this.isPublic = false;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public Long getNcbiProjectId() {
        return ncbiProjectId;
    }

    public void setNcbiProjectId(Long ncbiProjectId) {
        this.ncbiProjectId = ncbiProjectId;
    }

//    public Long getSubmitterId() {
//        return submitterId;
//    }
//
//    public void setSubmitterId(Long submitterId) {
//        this.submitterId = submitterId;
//    }


    public String getSubmissionAccountId() {
        return submissionAccountId;
    }

    public void setSubmissionAccountId(String submissionAccountId) {
        this.submissionAccountId = submissionAccountId;
    }

    public String getStudyStatusAsString() {
        return getStudyStatus().toString();
    }

    public void setStudyStatusAsString(String enumType) {
        setStudyStatus(StudyStatus.valueOf(enumType));
    }

    public StudyStatus getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(StudyStatus studyStatus) {
        this.studyStatus = studyStatus;
    }

    public Date getPublicReleaseDate() {
        return publicReleaseDate;
    }

    public void setPublicReleaseDate(Date publicReleaseDate) {
        this.publicReleaseDate = publicReleaseDate;
    }

    public Set<Publication> getPublications() {
        return publications;
    }

    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }

    public void addPublication(Publication publication) {
        if (publication != null) {
            if (publications == null) {
                publications = new HashSet<Publication>();
            }
            publications.add(publication);
        }
    }

    public Set<Sample> getSamples() {
        return samples;
    }

    public void setSamples(Set<Sample> samples) {
        this.samples = samples;
    }

    public void addSample(Sample sample) {
        if (sample != null) {
            if (samples == null) {
                samples = new HashSet<Sample>();
            }
            samples.add(sample);
        }
    }

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }

    public String getStudyAbstract() {
        return studyAbstract;
    }

    @Transient
    public String getShortStudyAbstract() {
        return getShortStudyAbstract(100);
    }

    @Transient
    protected String getShortStudyAbstract(int maxLength) {
        if (studyAbstract != null && studyAbstract.length() > maxLength) {
            String shortAbstract = studyAbstract.substring(0, maxLength);
            int index = shortAbstract.lastIndexOf(' ');
            return shortAbstract.substring(0, index);
        }
        return studyAbstract;
    }

    public void setStudyAbstract(String studyAbstract) {
        this.studyAbstract = studyAbstract;
    }

    public String getExperimentalFactor() {
        return experimentalFactor;
    }

    public void setExperimentalFactor(String experimentalFactor) {
        this.experimentalFactor = experimentalFactor;
    }

    @Override
    @Transient
    public String getSecureEntityId() {
        return getStudyId();
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getPrivacy() {
        if (isPublic) {
            return "public";
        }
        return "private";
    }

    public String getStudyPageURL() {
        return studyPageURL;
    }

    public void setStudyPageURL(String studyPageURL) {
        this.studyPageURL = studyPageURL;
    }

    public Date getLastMetadataReceived() {
        return lastMetadataReceived;
    }

    public String getFormattedLastReceived() {
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(lastMetadataReceived);
    }

    public void setLastMetadataReceived(Date lastMetadataReceived) {
        this.lastMetadataReceived = lastMetadataReceived;
    }

    public DataOrigination getDataOrigination() {
        return dataOrigination;
    }

    public void setDataOrigination(DataOrigination dataOrigination) {
        this.dataOrigination = dataOrigination;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmailAddress() {
        return authorEmailAddress;
    }

    public void setAuthorEmailAddress(String authorEmailAddress) {
        this.authorEmailAddress = authorEmailAddress;
    }

    public Long getSampleSize() {
        return sampleSize;
    }

    public void setSampleSize(Long sampleSize) {
        this.sampleSize = sampleSize;
    }

    /**
     * Please note this method is auto-generated by IntelliJ.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Study study = (Study) o;

        if (id != study.id) return false;
        if (isPublic != study.isPublic) return false;
        if (ncbiProjectId.equals(study.ncbiProjectId)) return false;
        if (submissionAccountId.equals(study.submissionAccountId)) return false;
        if (!centreName.equals(study.centreName)) return false;
        if (!experimentalFactor.equals(study.experimentalFactor)) return false;
        if (!lastMetadataReceived.equals(study.lastMetadataReceived)) return false;
        if (!publicReleaseDate.equals(study.publicReleaseDate)) return false;
        if (!studyAbstract.equals(study.studyAbstract)) return false;
        if (studyId != null ? !studyId.equals(study.studyId) : study.studyId != null) return false;
        if (!studyName.equals(study.studyName)) return false;
        if (!studyPageURL.equals(study.studyPageURL)) return false;
        if (studyStatus != study.studyStatus) return false;

        return true;
    }

    /**
     * Please note this method is auto-generated by IntelliJ.
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (studyId == null ? 0 : studyId.hashCode());
        result = 31 * result + (studyName == null ? 0 : studyName.hashCode());
        result = 31 * result + (ncbiProjectId == null ? 0 : ncbiProjectId.hashCode());
        result = 31 * result + (submissionAccountId == null ? 0 : submissionAccountId.hashCode());
        result = 31 * result + (studyStatus == null ? 0 : studyStatus.hashCode());
        result = 31 * result + (publicReleaseDate == null ? 0 : publicReleaseDate.hashCode());
        result = 31 * result + (lastMetadataReceived == null ? 0 : lastMetadataReceived.hashCode());
        result = 31 * result + (centreName == null ? 0 : centreName.hashCode());
        result = 31 * result + (studyAbstract == null ? 0 : studyAbstract.hashCode());
        result = 31 * result + (experimentalFactor == null ? 0 : experimentalFactor.hashCode());
        result = 31 * result + (isPublic ? 1 : 0);
        result = 31 * result + (studyPageURL == null ? 0 : studyPageURL.hashCode());
        return result;
    }

    public enum StudyStatus {
        IN_PROGRESS("In progress"),
        FINISHED("Finished"),
        UNDEFINED("Undefined");

        private String status;

        private StudyStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }
    }
}
