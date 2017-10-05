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
    @Column(name = "SAMPLE_ID", columnDefinition = "INT(11)")
    private long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "STUDY_SAMPLE",
            joinColumns = {@JoinColumn(name = "SAMPLE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "STUDY_ID")}
    )
    private Set<Study> studies = new HashSet<Study>(0);

    @Column(name = "EXT_SAMPLE_ID", nullable = false, length = 15)
    private String sampleId;

    @Column(name = "SAMPLE_ALIAS", length = 255)
    private String sampleAlias;

    @Column(name = "SAMPLE_NAME", length = 255)
    private String sampleName;

    @Column(name = "SAMPLE_DESC")
    @Lob
    private String sampleDescription;

    @Column(name = "GEO_LOC_NAME", length = 255)
    private String geoLocName;

    @Temporal(TemporalType.DATE)
    @Column(name = "COLLECTION_DATE", columnDefinition = "DATETIME")
    private Date collectionDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "METADATA_RECEIVED", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date metadataReceived;

    @Temporal(TemporalType.DATE)
    @Column(name = "SEQUENCEDATA_RECEIVED", columnDefinition = "DATETIME")
    private Date sequenceDataReceived;

    @Temporal(TemporalType.DATE)
    @Column(name = "SEQUENCEDATA_ARCHIVED", columnDefinition = "DATETIME")
    private Date sequenceDataArchived;

    @Temporal(TemporalType.DATE)
    @Column(name = "ANALYSIS_COMPLETED", columnDefinition = "DATETIME")
    private Date analysisCompleted;

    /**
     * Date when we received the last meta data or a substantial update.
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "LAST_UPDATE", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private Date lastMetadataReceived;

    /**
     * Single samples of public study could be private. Default value is private.
     */
    @Column(name = "IS_PUBLIC", columnDefinition = "TINYINT(4)")
    private Integer isPublic;

    @Column(name = "SUBMISSION_ACCOUNT_ID", length = 15)
    private String submissionAccountId;

    @Column(name = "LATITUDE", precision = 7, scale = 4)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 7, scale = 4)
    private BigDecimal longitude;

    @Column(name = "ENVIRONMENT_BIOME", length = 255)
    private String environmentalBiome;

    @Column(name = "ENVIRONMENT_FEATURE", length = 255)
    private String environmentalFeature;

    @Column(name = "ENVIRONMENT_MATERIAL", length = 255)
    private String environmentalMaterial;

    /**
     * Host taxonomy ID (e.g. 9606 for Homo sapiens)
     */
    @Column(name = "HOST_TAX_ID")
    private Integer hostTaxonomyId;

    /**
     * Name of the species for instance Homo sapiens
     */
    @Column(name = "SPECIES", nullable = true, length = 255)
    private String species;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BIOME_ID", nullable = true)
    private Biome biome;

    @Transient
    private String biomeIconCSSClass;

    @Transient
    private String biomeIconTitle;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sample")
    @Column(name = "SAMPLE_ID")
    private Set<AnalysisJob> analysisJobs;

    /**
     * Associated publication.
     */
    @ManyToMany
    @JoinTable(
            name = "SAMPLE_PUBLICATION",
            joinColumns = {@JoinColumn(name = "PUB_ID")},
            inverseJoinColumns = {@JoinColumn(name = "SAMPLE_ID")}
    )
    private Set<Publication> publications;


    public Sample() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
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
        return (isPublic == 1 ? true : false);
    }

    public Integer isPublicInt() {
        return isPublic;
    }

    public void setPublic(Integer isPublicInt) {
        isPublic = isPublicInt;
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

    public Integer getHostTaxonomyId() {
        return hostTaxonomyId;
    }

    public void setHostTaxonomyId(Integer hostTaxonomyId) {
        this.hostTaxonomyId = hostTaxonomyId;
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

    public Set<AnalysisJob> getAnalysisJobs() {
        return analysisJobs;
    }

    public void setAnalysisJobs(Set<AnalysisJob> analysisJobs) {
        this.analysisJobs = analysisJobs;
    }

    @Override
    @Transient
    public String getSecureEntityId() {
        return getSampleId();
    }
}
