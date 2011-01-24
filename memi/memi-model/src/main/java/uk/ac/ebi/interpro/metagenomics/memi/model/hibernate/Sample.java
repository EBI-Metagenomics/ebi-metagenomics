package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Entity
@Table(name = "HB_SAMPLE", schema = "EMG_USER")
public abstract class Sample {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Study study;

    @Column(name = "SAMPLE_ID")
    private String sampleId;

    @Column(name = "SAMPLE_TITLE")
    private String sampleTitle;

    @Column(name = "SAMPLE_DESC")
    @Lob
    private String sampleDescription;

    @Column(name = "SAMPLE_CLASSIFICATION")
    private String sampleClassification;

    @Column(name = "GEO_LOC_NAME")
    private String geoLocName;

    @Column(name = "COLLECTION_DATE")
    private Date collectionDate;

    @Column(name = "HABITAT_TYPE")
    private String habitatType;


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

    public String getSampleTitle() {
        return sampleTitle;
    }

    public void setSampleTitle(String sampleTitle) {
        this.sampleTitle = sampleTitle;
    }

    public String getSampleDescription() {
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

    public String getHabitatType() {
        return habitatType;
    }

    public void setHabitatType(String habitatType) {
        this.habitatType = habitatType;
    }
}