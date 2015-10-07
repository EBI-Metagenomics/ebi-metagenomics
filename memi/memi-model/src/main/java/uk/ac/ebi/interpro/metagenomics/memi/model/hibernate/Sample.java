package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
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
@Table(name = "SAMPLE")
public class Sample implements SecureEntity, BiomeEntity {

    @Id
    @Column(name = "SAMPLE_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAMPLE_SEQ")
    @SequenceGenerator(
            name = "SAMPLE_SEQ",
            sequenceName = "SAMPLE_SEQ")
    private long id;

    @ManyToOne
    @JoinColumn(name = "STUDY_ID", nullable = true)
    private Study study;

    @Column(name = "EXT_SAMPLE_ID", nullable = false, length = 15)
    private String sampleId;

    @Column(name = "SAMPLE_ALIAS")
    private String sampleAlias;

    @Column(name = "SAMPLE_NAME")
    private String sampleName;

    @Column(name = "SAMPLE_DESC")
    @Lob
    private String sampleDescription;

//    @Column(name = "SAMPLE_CLASSIFICATION")
//    private String sampleClassification;

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
     * Date when we received the last meta data or a substantial update.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_UPDATE", columnDefinition = "DATE DEFAULT CURRENT_DATE", nullable = false)
    private Date lastMetadataReceived;

    /**
     * Single samples of public study could be private. Default value is private.
     */
    @Column(name = "IS_PUBLIC")
    private boolean isPublic;

    @Column(name = "SUBMISSION_ACCOUNT_ID")
    private String submissionAccountId;

    @Column(name = "MISC")
    @Lob
    private String miscellaneous;

    @Column(name = "LATITUDE", precision = 7, scale = 4)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 7, scale = 4)
    private BigDecimal longitude;

    @Column(name = "ENVIRONMENT_BIOME")
    private String environmentalBiome;

    @Column(name = "ENVIRONMENT_FEATURE")
    private String environmentalFeature;

    @Column(name = "ENVIRONMENT_MATERIAL")
    private String environmentalMaterial;

    @Column(name = "PHENOTYPE")
    private String phenotype;

    /**
     * Host taxonomy ID (e.g. 9606 for Homo sapiens)
     */
    @Column(name = "HOST_TAX_ID")
    private Integer hostTaxonomyId;


    /**
     * Name of the species for instance Homo sapiens
     */
    @Column(name = "SPECIES", nullable = true)
    private String species;

    @Column(name = "HOST_SEX", length = 30)
    @Enumerated(EnumType.STRING)
    private HostSex hostSex;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BIOME_ID", nullable = true)
    private Biome biome;

    @Transient
    private String biomeIconCSSClass;

    @Transient
    private String biomeIconTitle;

    /**
     * Associated publication.
     */
    @ManyToMany
    private Set<Publication> publications;

    public Sample() {
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

//    public String getSampleClassification() {
//        return sampleClassification;
//    }

//    public void setSampleClassification(String sampleClassification) {
//        this.sampleClassification = sampleClassification;
//    }

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

    public String getSubmissionAccountId() {
        return submissionAccountId;
    }

    public void setSubmissionAccountId(String submissionAccountId) {
        this.submissionAccountId = submissionAccountId;
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

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Date getLastMetadataReceived() {
        return lastMetadataReceived;
    }

    public void setLastMetadataReceived(Date lastMetadataReceived) {
        this.lastMetadataReceived = lastMetadataReceived;
    }

    public String getEnvironmentalBiome() {
        return environmentalBiome;
    }

    public void setEnvironmentalBiome(String environmentalBiome) {
        this.environmentalBiome = environmentalBiome;
    }

    public String getEnvironmentalFeature() {
        return environmentalFeature;
    }

    public void setEnvironmentalFeature(String environmentalFeature) {
        this.environmentalFeature = environmentalFeature;
    }

    public String getEnvironmentalMaterial() {
        return environmentalMaterial;
    }

    public void setEnvironmentalMaterial(String environmentalMaterial) {
        this.environmentalMaterial = environmentalMaterial;
    }

    public String getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(String phenotype) {
        this.phenotype = phenotype;
    }

    public Integer getHostTaxonomyId() {
        return hostTaxonomyId;
    }

    public void setHostTaxonomyId(Integer hostTaxonomyId) {
        this.hostTaxonomyId = hostTaxonomyId;
    }

    public HostSex getHostSex() {
        return hostSex;
    }

    public void setHostSex(HostSex hostSex) {
        this.hostSex = hostSex;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Biome getBiome() {
        return biome;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    public void setBiomeIconCSSClass(String biomeIconCSSClass) {
        this.biomeIconCSSClass = biomeIconCSSClass;
    }

    public String getBiomeIconCSSClass() {
        return biomeIconCSSClass;
    }

    public String getBiomeIconTitle() {
        return biomeIconTitle;
    }

    public void setBiomeIconTitle(String biomeIconTitle) {
        this.biomeIconTitle = biomeIconTitle;
    }

//    @Transient
//    public abstract Class<? extends Sample> getClazz();

//    @Transient
//    public abstract SampleType getSampleType();

//    @Transient
//    public String getSampleTypeAsString() {
//        return getSampleType().toString();
//    }

    @Override
    @Transient
    public String getSecureEntityId() {
        return getSampleId();
    }


//    public enum SampleType {
//        ENVIRONMENTAL(EnvironmentSample.class, "Environmental"),
//        HOST_ASSOCIATED(HostSample.class, "Host associated"),
//        ENGINEERED(EngineeredSample.class, "Man-made"),
//        UNDEFINED(UndefinedSample.class, "Undefined");
//
//        private Class<? extends Sample> clazz;
//
//        private String type;
//
//        private SampleType(Class<? extends Sample> clazz, String type) {
//            this.clazz = clazz;
//            this.type = type;
//        }
//
//        public Class<? extends Sample> getClazz() {
//            return clazz;
//        }
//
//        @Override
//        public String toString() {
//            return type;
//        }
//
//        public String getUpperCaseString() {
//            String result = type.replace(" ", "_");
//            return result.toUpperCase();
//        }
//    }

    public enum HostSex {
        FEMALE, MALE;
    }
}
