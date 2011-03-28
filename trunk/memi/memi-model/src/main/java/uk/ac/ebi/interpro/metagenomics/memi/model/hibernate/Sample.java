package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Hibernate generated sample table
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Entity
@Table(name = "HB_SAMPLE")
@SequenceGenerator(
        name = "SAMPLE_SEQ",
        sequenceName = "SAMPLE_SEQ",
        allocationSize = 1
)
public abstract class Sample implements SecureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAMPLE_SEQ")
    private long id;

    @ManyToOne
    @JoinColumn(name = "STUDY_ID", nullable = true)
    private Study study;

    @Column(name = "SAMPLE_ID", nullable = false, length = 15)
    private String sampleId;

    @Column(name = "SAMPLE_ALIAS")
    private String sampleAlias;

    @Column(name = "SAMPLE_NAME")
    private String sampleName;

    @Column(name = "SAMPLE_DESC")
    @Lob
    private String sampleDescription;

    @Column(name = "SAMPLE_CLASSIFICATION")
    private String sampleClassification;

    @Column(name = "GEO_LOC_NAME")
    private String geoLocName;

    @Temporal(TemporalType.DATE)
    @Column(name = "COLLECTION_DATE")
    private Date collectionDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "METADATA_RECEIVED")
    private Date metadataReceived;

    @Temporal(TemporalType.DATE)
    @Column(name = "SEQUENCEDATA_RECEIVED")
    private Date sequenceDataReceived;

    @Temporal(TemporalType.DATE)
    @Column(name = "SEQUENCEDATA_ARCHIVED")
    private Date sequenceDataArchived;

    @Temporal(TemporalType.DATE)
    @Column(name = "ANALYSIS_COMPLETED")
    private Date analysisCompleted;

    /**
     * Single samples of public study could be private. Default value is private.
     */
    @Column(name = "IS_PUBLIC")
    private boolean isPublic;

    @Column(name = "SUBMITTER_ID")
    private long submitterId;

    @Column(name = "MISC")
    @Lob
    private String miscellaneous;

    /**
     * Associated publication. Deactivated lazy loading.
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Publication> publications;

    protected Sample() {
        isPublic = false;
        publications = new HashSet<Publication>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleDescription() {
        return sampleDescription;
    }

    @Transient
    public String getShortSampleDescription() {
        return getShortSampleDescription(100);
    }

    @Transient
    protected String getShortSampleDescription(int maxLength) {
        if (sampleDescription != null && sampleDescription.length() > maxLength) {
            String shortDescription = sampleDescription.substring(0, maxLength);
            int index = shortDescription.lastIndexOf(' ');
            return shortDescription.substring(0, index);
        }
        return sampleDescription;
    }

    public void setSampleDescription(String sampleDescription) {
        this.sampleDescription = sampleDescription;
    }

    public String getSampleClassification() {
        return sampleClassification;
    }

    public void setSampleClassification(String sampleClassification) {
        this.sampleClassification = sampleClassification;
    }

    public String getGeoLocName() {
        return geoLocName;
    }

    public void setGeoLocName(String geoLocName) {
        this.geoLocName = geoLocName;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Date getMetadataReceived() {
        return metadataReceived;
    }

    public void setMetadataReceived(Date metadataReceived) {
        this.metadataReceived = metadataReceived;
    }

    public Date getSequenceDataReceived() {
        return sequenceDataReceived;
    }

    public void setSequenceDataReceived(Date sequenceDataReceived) {
        this.sequenceDataReceived = sequenceDataReceived;
    }

    public Date getSequenceDataArchived() {
        return sequenceDataArchived;
    }

    public void setSequenceDataArchived(Date sequenceDataArchived) {
        this.sequenceDataArchived = sequenceDataArchived;
    }

    public Date getAnalysisCompleted() {
        return analysisCompleted;
    }

    public void setAnalysisCompleted(Date analysisCompleted) {
        this.analysisCompleted = analysisCompleted;
    }

    public long getSubmitterId() {
        return submitterId;
    }

    public void setSubmitterId(long submitterId) {
        this.submitterId = submitterId;
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

    public String getMiscellaneous() {
        return miscellaneous;
    }

    public void setMiscellaneous(String miscellaneous) {
        this.miscellaneous = miscellaneous;
    }

    public String getSampleAlias() {
        return sampleAlias;
    }

    public void setSampleAlias(String sampleAlias) {
        this.sampleAlias = sampleAlias;
    }

    @Transient
    public abstract Class<? extends Sample> getClazz();

    @Transient
    public abstract SampleType getSampleType();

    @Transient
    public String getSampleTypeAsString() {
        return getSampleType().toString();
    }


    public enum SampleType {
        ENVIRONMENTAL(EnvironmentSample.class, "Environmental"),
        HOST_ASSOCIATED(HostSample.class, "Host associated"),
        UNDEFINED(UndefinedSample.class, "Undefined");

        private Class<? extends Sample> clazz;

        private String type;

        private SampleType(Class<? extends Sample> clazz, String type) {
            this.clazz = clazz;
            this.type = type;
        }

        public Class<? extends Sample> getClazz() {
            return clazz;
        }

        @Override
        public String toString() {
            return type;
        }
    }
}
