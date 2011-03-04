package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;


import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
@Table(name = "HB_STUDY")
@SequenceGenerator(
        name = "STUDY_SEQ",
        sequenceName = "STUDY_SEQ",
        allocationSize = 1
)
public class Study implements SecureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STUDY_SEQ")
    private long id;

    @Column(name = "STUDY_ID", length = 18)
    private String studyId;

    @Column(name = "STUDY_NAME")
    private String studyName;

    /**
     * NCBI BioProject ID
     */
    @Column(name = "NCBI_PROJECT_ID")
    private long ncbiProjectId;

    @Column(name = "SUBMITTER_ID")
    private long submitterId;

    @Column(name = "STUDY_TYPE")
    private StudyType studyType;

    @Column(name = "STUDY_Status")
    private StudyStatus studyStatus;

    @Temporal(TemporalType.DATE)
    @Column(name = "PUBLIC_RELEASE_DATE")
    private Date publicReleaseDate;

    /**
     * Date of the last received metadata.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_UPDATE")
    private Date lastMetadataReceived;

    /**
     * Associated publication. Deactivated lazy loading.
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Publication> publications;

    //    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "study")
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
    public String getShortStudyAbstract()
    {
        if(studyAbstract!=null && studyAbstract.length()>15)
            return studyAbstract.substring(0,15);
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
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(lastMetadataReceived);
    }

    public void setLastMetadataReceived(Date lastMetadataReceived) {
        this.lastMetadataReceived = lastMetadataReceived;
    }

    public enum StudyType {
        ENVIRONMENTAL(EnvironmentSample.class),
        HOST_ASSOCIATED(HostSample.class),
        UNDEFINED(UndefinedSample.class);

        private Class<? extends Sample> clazz;

        private StudyType(Class<? extends Sample> clazz) {
            this.clazz = clazz;
        }

        public Class<? extends Sample> getClazz() {
            return clazz;
        }
    }

    public enum StudyStatus {
        QUEUED, IN_PROGRESS, FINISHED, UNDEFINED;
    }
}
