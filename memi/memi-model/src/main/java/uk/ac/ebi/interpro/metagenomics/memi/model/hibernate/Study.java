package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Entity
public class Study {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "STUDY_ID", length = 15)
    private String studyId;

    @Column(name = "STUDY_NAME")
    private String studyName;

    @Column(name = "NCBI_PROJECT_ID")
    private long ncbiProjectId;

    @Column(name = "SUBMITTER_ID")
    private long submitterId;

    @Transient
    private StudyType studyType;

    //Only used internal, for saving the enum into the database
    @Column(name = "STUDY_TYPE", length = 30)
    private String studyTypeAsString;

    @Transient
    private StudyStatus studyStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "PUBLIC_RELEASE_DATE")
    private Date publicReleaseDate;

    /**
     * Associated publication.
     */
    @ManyToMany
    private Set<Publication> publications;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "Study_ID")
    private Set<Sample> samples;

    @Column(name = "CENTRE_NAME")
    private String centreName;

    @Column(name = "STUDY_ABSTRACT")
    @Lob
    private String studyAbstract;

    @Column(name = "EXPERIMENTAL_FACTOR")
    private String experimentalFactor;

    public Study() {
        publications = new HashSet<Publication>();
        samples = new HashSet<Sample>();
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

    public long getNcbiProjectId() {
        return ncbiProjectId;
    }

    public void setNcbiProjectId(long ncbiProjectId) {
        this.ncbiProjectId = ncbiProjectId;
    }

    public long getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(long submitterId) {
        this.submitterId = submitterId;
    }

    public StudyType getStudyType() {
        return studyType;
    }

    public void setStudyType(StudyType studyType) {
        this.studyType = studyType;
    }

    public String getStudyTypeAsString() {
        return getStudyType().toString();
    }

    public void setStudyTypeAsString(String enumType) {
        this.setStudyType(StudyType.valueOf(enumType));
    }

    @Column(name = "STUDY_STATUS")
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

    public void setStudyAbstract(String studyAbstract) {
        this.studyAbstract = studyAbstract;
    }

    public String getExperimentalFactor() {
        return experimentalFactor;
    }

    public void setExperimentalFactor(String experimentalFactor) {
        this.experimentalFactor = experimentalFactor;
    }

    public enum StudyType {
        ENVIRONMENTAL, HOST_ASSOCIATED;
    }

    public enum StudyStatus {
        SUBMITTED, IN_PROGRESS, FINISHED;
    }
}