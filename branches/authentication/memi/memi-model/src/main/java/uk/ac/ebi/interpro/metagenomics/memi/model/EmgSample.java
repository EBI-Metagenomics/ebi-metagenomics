package uk.ac.ebi.interpro.metagenomics.memi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.*;

/**
 * Represents an EBI Metagenomics sample object.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Table(name = "EMG_SAMPLE")
@Entity
public class EmgSample {

    /**
     * Maps all properties of this object. Useful if you like to iterate over all properties (used in the studyOverview JSP).
     * Property values are added by setter.
     */
    private SortedMap<String, Object> propertyMap;

    private final String SAMPLE_ID = "SampleId";

    private final String STUDY_ID = "StudyId";

    private final String SAMPLE_TITLE = "SampleTitle";

    private final String SAMPLE_DESC = "SampleDescription";

    private final String SAMPLE_CLASSIFICATION = "SampleClassification";

    private final String GEO_LOC_NAME = "GeoLocName";

    private final String LAT_LON = "LatLon";

    private final String COLLECTION_DATE = "CollectionDate";

    private final String BIOME = "Biome";


    public EmgSample() {
        propertyMap = new TreeMap<String, Object>();
    }

    public EmgSample(String sampleId, String sampleTitle) {
        this();
        propertyMap.put(SAMPLE_ID, sampleId);
        propertyMap.put(SAMPLE_TITLE, sampleTitle);
    }

    @Transient
    public Map<String, Object> getPropertyMap() {
        return propertyMap;
    }

    @javax.persistence.Column(name = "SAMPLE_ID", nullable = false, insertable = true, updatable = true, length = 9, precision = 0)
    @javax.persistence.Basic
    @Id
    public String getSampleId() {
        return (String) propertyMap.get(SAMPLE_ID);
    }

    public void setSampleId(String sampleId) {
        propertyMap.put(SAMPLE_ID, sampleId);
    }

    @javax.persistence.Column(name = "STUDY_ID", nullable = false, insertable = true, updatable = true, length = 9, precision = 0)
    @javax.persistence.Basic
    public String getStudyId() {
        return (String) propertyMap.get(STUDY_ID);
    }

    public void setStudyId(String studyId) {
        propertyMap.put(STUDY_ID, studyId);
    }

    @javax.persistence.Column(name = "SAMPLE_TITLE", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @javax.persistence.Basic
    public String getSampleTitle() {
        return (String) propertyMap.get(SAMPLE_TITLE);
    }

    public void setSampleTitle(String sampleTitle) {
        propertyMap.put(SAMPLE_TITLE, sampleTitle);
    }

    @javax.persistence.Column(name = "SAMPLE_DESCRIPTION", nullable = false, insertable = true, updatable = true, length = 4000, precision = 0)
    @javax.persistence.Lob
    @javax.persistence.Basic
    public String getSampleDescription() {
        return (String) propertyMap.get(SAMPLE_DESC);
    }

    public void setSampleDescription(String sampleDescription) {
        propertyMap.put(SAMPLE_DESC, sampleDescription);
    }

    @javax.persistence.Column(name = "SAMPLE_CLASSIFICATION", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @javax.persistence.Basic
    public String getSampleClassification() {
        return (String) propertyMap.get(SAMPLE_CLASSIFICATION);
    }

    public void setSampleClassification(String sampleClassification) {
        propertyMap.put(SAMPLE_CLASSIFICATION, sampleClassification);
    }

    @javax.persistence.Column(name = "GEO_LOC_NAME", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
    @javax.persistence.Basic
    public String getGeoLocName() {
        return (String) propertyMap.get(GEO_LOC_NAME);
    }

    public void setGeoLocName(String geoLocName) {
        propertyMap.put(GEO_LOC_NAME, geoLocName);
    }

    @javax.persistence.Column(name = "LAT_LON", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getLatLon() {
        return (String) propertyMap.get(LAT_LON);
    }

    public void setLatLon(String latLon) {
        propertyMap.put(LAT_LON, latLon);
    }

    @javax.persistence.Column(name = "COLLECTION_DATE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getCollectionDate() {
        String result = (String) propertyMap.get(COLLECTION_DATE);
        if (result != null) {
            int i = result.indexOf(" ");
            if (i > 1) {
                result = result.substring(0, i);
            }
        }
        return result;
    }

    public void setCollectionDate(String collectionDate) {
        propertyMap.put(COLLECTION_DATE, collectionDate);
    }

    @javax.persistence.Column(name = "BIOME", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @javax.persistence.Basic
    public String getBiome() {
        return (String) propertyMap.get(BIOME);
    }

    public void setBiome(String biome) {
        propertyMap.put(BIOME, biome);
    }

    private final String FEATURE = "Feature";

    @javax.persistence.Column(name = "FEATURE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getFeature() {
        return (String) propertyMap.get(FEATURE);
    }

    public void setFeature(String feature) {
        propertyMap.put(FEATURE, feature);
    }

    private final String MATERIAL = "Material";

    @javax.persistence.Column(name = "MATERIAL", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getMaterial() {
        return (String) propertyMap.get(MATERIAL);
    }

    public void setMaterial(String material) {
        propertyMap.put(MATERIAL, material);
    }

    private final String ALT = "Alt";

    @javax.persistence.Column(name = "ALT", nullable = true, insertable = true, updatable = true, length = 22, precision = 0)
    @javax.persistence.Basic
    public Integer getAlt() {
        return (Integer) propertyMap.get(ALT);
    }

    public void setAlt(Integer alt) {
        propertyMap.put(ALT, alt);
    }

    private final String DEPTH = "Depth";

    @javax.persistence.Column(name = "DEPTH", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getDepth() {
        return (String) propertyMap.get(DEPTH);
    }

    public void setDepth(String depth) {
        propertyMap.put(DEPTH, depth);
    }

    private final String ELEVATION = "Elevation";

    @javax.persistence.Column(name = "ELEVATION", nullable = true, insertable = true, updatable = true, length = 22, precision = 0)
    @javax.persistence.Basic
    public Integer getElevation() {
        return (Integer) propertyMap.get(ELEVATION);
    }

    public void setElevation(Integer elevation) {
        propertyMap.put(ELEVATION, elevation);
    }

    private final String HOST_TAXID = "HostTaxid";

    @javax.persistence.Column(name = "HOST_TAXID", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getHostTaxid() {
        return (String) propertyMap.get(HOST_TAXID);
    }

    public void setHostTaxid(String hostTaxid) {
        propertyMap.put(HOST_TAXID, hostTaxid);
    }

    private final String HOST_SUBJECT_ID = "HostSubjectId";

    @javax.persistence.Column(name = "HOST_SUBJECT_ID", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getHostSubjectId() {
        return (String) propertyMap.get(HOST_SUBJECT_ID);
    }

    public void setHostSubjectId(String hostSubjectId) {
        propertyMap.put(HOST_SUBJECT_ID, hostSubjectId);
    }

    private final String SAMP_STORE_TEMP = "SampleStoreTemperature";

    @javax.persistence.Column(name = "SAMP_STORE_TEMP", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getSampStoreTemp() {
        return (String) propertyMap.get(SAMP_STORE_TEMP);
    }

    public void setSampStoreTemp(String sampStoreTemp) {
        propertyMap.put(SAMP_STORE_TEMP, sampStoreTemp);
    }

    private final String SAMPLE_STORE_LOCATION = "SampleStoreLocation";

    @javax.persistence.Column(name = "SAMPLE_STORE_LOCATION", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getSampleStoreLocation() {
        return (String) propertyMap.get(SAMPLE_STORE_LOCATION);
    }

    public void setSampleStoreLocation(String sampleStoreLocation) {
        propertyMap.put(SAMPLE_STORE_LOCATION, sampleStoreLocation);
    }

    private final String SAMPLE_STORE_DURATION = "SampleStoreDuration";

    @javax.persistence.Column(name = "SAMPLE_STORE_DURATION", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getSampleStoreDuration() {
        return (String) propertyMap.get(SAMPLE_STORE_DURATION);
    }

    public void setSampleStoreDuration(String sampleStoreDuration) {
        propertyMap.put(SAMPLE_STORE_DURATION, sampleStoreDuration);
    }

    private final String SEQUENCING_METHOD = "SequencingMethod";

    @javax.persistence.Column(name = "SEQUENCING_METHOD", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getSequencingMethod() {
        return (String) propertyMap.get(SEQUENCING_METHOD);
    }

    public void setSequencingMethod(String sequencingMethod) {
        propertyMap.put(SEQUENCING_METHOD, sequencingMethod);
    }

    private final String RELEVANT_ELECTRONIC_RESOURCES = "RelevantElectronicResources";

    @javax.persistence.Column(name = "RELEVANT_ELECTRONIC_RESOURCES", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @javax.persistence.Basic
    public String getRelevantElectronicResources() {
        return (String) propertyMap.get(RELEVANT_ELECTRONIC_RESOURCES);
    }

    public void setRelevantElectronicResources(String relevantElectronicResources) {
        propertyMap.put(RELEVANT_ELECTRONIC_RESOURCES, relevantElectronicResources);
    }

    private final String COLLECTION_DEVICE_METHOD = "CollectionDeviceMethod";

    @javax.persistence.Column(name = "COLLECTION_DEVICE_METHOD", nullable = true, insertable = true, updatable = true, length = 100, precision = 0)
    @javax.persistence.Basic
    public String getCollectionDeviceMethod() {
        return (String) propertyMap.get(COLLECTION_DEVICE_METHOD);
    }

    public void setCollectionDeviceMethod(String collectionDeviceMethod) {
        propertyMap.put(COLLECTION_DEVICE_METHOD, collectionDeviceMethod);
    }

    private final String AMOUNT_OR_SIZE = "AmountOrSize";

    @javax.persistence.Column(name = "AMOUNT_OR_SIZE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getAmountOrSize() {
        return (String) propertyMap.get(AMOUNT_OR_SIZE);
    }

    public void setAmountOrSize(String amountOrSize) {
        propertyMap.put(AMOUNT_OR_SIZE, amountOrSize);
    }

    private final String SAMPLE_MATERIAL_PROCESSING = "SampleMaterialProcessing";

    @javax.persistence.Column(name = "SAMPLE_MATERIAL_PROCESSING", nullable = true, insertable = true, updatable = true, length = 150, precision = 0)
    @javax.persistence.Basic
    public String getSampleMaterialProcessing() {
        return (String) propertyMap.get(SAMPLE_MATERIAL_PROCESSING);
    }

    public void setSampleMaterialProcessing(String sampleMaterialProcessing) {
        propertyMap.put(SAMPLE_MATERIAL_PROCESSING, sampleMaterialProcessing);
    }

    private final String NUCLEIC_ACID_EXTRACTION = "NucleicAcidExtraction";

    @javax.persistence.Column(name = "NUCLEIC_ACID_EXTRACTION", nullable = true, insertable = true, updatable = true, length = 150, precision = 0)
    @javax.persistence.Basic
    public String getNucleicAcidExtraction() {
        return (String) propertyMap.get(NUCLEIC_ACID_EXTRACTION);
    }

    public void setNucleicAcidExtraction(String nucleicAcidExtraction) {
        propertyMap.put(NUCLEIC_ACID_EXTRACTION, nucleicAcidExtraction);
    }

    private final String NUCLEIC_ACID_AMPLIFICATION = "NucleicAcidAmplification";

    @javax.persistence.Column(name = "NUCLEIC_ACID_AMPLIFICATION", nullable = true, insertable = true, updatable = true, length = 150, precision = 0)
    @javax.persistence.Basic
    public String getNucleicAcidAmplification() {
        return (String) propertyMap.get(NUCLEIC_ACID_AMPLIFICATION);
    }

    public void setNucleicAcidAmplification(String nucleicAcidAmplification) {
        propertyMap.put(NUCLEIC_ACID_AMPLIFICATION, nucleicAcidAmplification);
    }

    private final String TOTAL_DEPTH_OF_WATER = "TotalDepthOfWater";

    @javax.persistence.Column(name = "TOTAL_DEPTH_OF_WATER", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getTotalDepthOfWater() {
        return (String) propertyMap.get(TOTAL_DEPTH_OF_WATER);
    }

    public void setTotalDepthOfWater(String totalDepthOfWater) {
        propertyMap.put(TOTAL_DEPTH_OF_WATER, totalDepthOfWater);
    }

    private final String TEMPERATURE = "Temperature";

    @javax.persistence.Column(name = "TEMPERATURE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getTemperature() {
        return (String) propertyMap.get(TEMPERATURE);
    }

    public void setTemperature(String temperature) {
        propertyMap.put(TEMPERATURE, temperature);
    }

    private final String SILICATE = "Silicate";

    @javax.persistence.Column(name = "SILICATE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getSilicate() {
        return (String) propertyMap.get(SILICATE);
    }

    public void setSilicate(String silicate) {
        propertyMap.put(SILICATE, silicate);
    }

    private final String SALINITY = "Salinity";

    @javax.persistence.Column(name = "SALINITY", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getSalinity() {
        return (String) propertyMap.get(SALINITY);
    }

    public void setSalinity(String salinity) {
        propertyMap.put(SALINITY, salinity);
    }

    private final String PHOSPHATE = "Phosphate";

    @javax.persistence.Column(name = "PHOSPHATE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getPhosphate() {
        return (String) propertyMap.get(PHOSPHATE);
    }

    public void setPhosphate(String phosphate) {
        propertyMap.put(PHOSPHATE, phosphate);
    }

    private final String PH = "PH";

    @javax.persistence.Column(name = "PH", nullable = true, insertable = true, updatable = true, length = 3, precision = 2)
    @javax.persistence.Basic
    public BigDecimal getPh() {
        return (BigDecimal) propertyMap.get(PH);
    }

    public void setPh(BigDecimal ph) {
        propertyMap.put(PH, ph);
    }

    private final String OXYGENATION_STATUS_OF_SAMPLE = "OxygenationStatusOfSample";

    @javax.persistence.Column(name = "OXYGENATION_STATUS_OF_SAMPLE", nullable = true, insertable = true, updatable = true, length = 150, precision = 0)
    @javax.persistence.Basic
    public String getOxygenationStatusOfSample() {
        return (String) propertyMap.get(OXYGENATION_STATUS_OF_SAMPLE);
    }

    public void setOxygenationStatusOfSample(String oxygenationStatusOfSample) {
        propertyMap.put(OXYGENATION_STATUS_OF_SAMPLE, oxygenationStatusOfSample);
    }

    private final String ORGANIC_NITROGEN = "OrganicNitrogen";

    @javax.persistence.Column(name = "ORGANIC_NITROGEN", nullable = true, insertable = true, updatable = true, length = 10, precision = 3)
    @javax.persistence.Basic
    public BigDecimal getOrganicNitrogen() {
        return (BigDecimal) propertyMap.get(ORGANIC_NITROGEN);
    }

    public void setOrganicNitrogen(BigDecimal organicNitrogen) {
        propertyMap.put(ORGANIC_NITROGEN, organicNitrogen);
    }

    private final String ORGANIC_CARBON = "OrganicCarbon";

    @javax.persistence.Column(name = "ORGANIC_CARBON", nullable = true, insertable = true, updatable = true, length = 10, precision = 3)
    @javax.persistence.Basic
    public BigDecimal getOrganicCarbon() {
        return (BigDecimal) propertyMap.get(ORGANIC_CARBON);
    }

    public void setOrganicCarbon(BigDecimal organicCarbon) {
        propertyMap.put(ORGANIC_CARBON, organicCarbon);
    }

    private final String NITRATE = "Nitrate";

    @javax.persistence.Column(name = "NITRATE", nullable = true, insertable = true, updatable = true, length = 10, precision = 3)
    @javax.persistence.Basic
    public BigDecimal getNitrate() {
        return (BigDecimal) propertyMap.get(NITRATE);
    }

    public void setNitrate(BigDecimal nitrate) {
        propertyMap.put(NITRATE, nitrate);
    }


    private final String CHLOROPHYLL = "Chlorophyll";

    @javax.persistence.Column(name = "CHLOROPHYLL", nullable = true, insertable = true, updatable = true, length = 10, precision = 3)
    @javax.persistence.Basic
    public BigDecimal getChlorophyll() {
        return (BigDecimal) propertyMap.get(CHLOROPHYLL);
    }

    public void setChlorophyll(BigDecimal chlorophyll) {
        propertyMap.put(CHLOROPHYLL, chlorophyll);
    }


    private final String AMMONIUM = "Ammonium";

    @javax.persistence.Column(name = "AMMONIUM", nullable = true, insertable = true, updatable = true, length = 10, precision = 3)
    @javax.persistence.Basic
    public BigDecimal getAmmonium() {
        return (BigDecimal) propertyMap.get(AMMONIUM);
    }

    public void setAmmonium(BigDecimal ammonium) {
        propertyMap.put(AMMONIUM, ammonium);
    }


    private final String HOST_SEX = "HostSex";

    @javax.persistence.Column(name = "HOST_SEX", nullable = true, insertable = true, updatable = true, length = 6, precision = 0)
    @javax.persistence.Basic
    public String getHostSex() {
        return (String) propertyMap.get(HOST_SEX);
    }

    public void setHostSex(String hostSex) {
        propertyMap.put(HOST_SEX, hostSex);
    }

    private final String HEIGHT = "Height";

    @javax.persistence.Column(name = "HEIGHT", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getHeight() {
        return (String) propertyMap.get(HEIGHT);
    }


    public void setHeight(String height) {
        propertyMap.put(HEIGHT, height);
    }

    private final String DIET = "Diet";

    @javax.persistence.Column(name = "DIET", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getDiet() {
        return (String) propertyMap.get(DIET);
    }

    public void setDiet(String diet) {
        propertyMap.put(DIET, diet);
    }

    private final String LAST_MEAL = "LastMeal";

    @javax.persistence.Column(name = "LAST_MEAL", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getLastMeal() {
        return (String) propertyMap.get(LAST_MEAL);
    }

    public void setLastMeal(String lastMeal) {
        propertyMap.put(LAST_MEAL, lastMeal);
    }

    private final String FAMILY_RELATIONSHIP = "FamilyRelationship";

    @javax.persistence.Column(name = "FAMILY_RELATIONSHIP", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getFamilyRelationship() {
        return (String) propertyMap.get(FAMILY_RELATIONSHIP);
    }

    public void setFamilyRelationship(String familyRelationship) {
        propertyMap.put(FAMILY_RELATIONSHIP, familyRelationship);
    }

    private final String GENOTYPE = "Genotype";

    @javax.persistence.Column(name = "GENOTYPE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getGenotype() {
        return (String) propertyMap.get(GENOTYPE);
    }

    public void setGenotype(String genotype) {
        propertyMap.put(GENOTYPE, genotype);
    }

    private final String PHENOTYPE = "Phenotype";

    @javax.persistence.Column(name = "PHENOTYPE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getPhenotype() {
        return (String) propertyMap.get(PHENOTYPE);
    }

    public void setPhenotype(String phenotype) {
        propertyMap.put(PHENOTYPE, phenotype);
    }


    private final String HOST_BODY_TEMPERATURE = "HostBodyTemperature";

    @javax.persistence.Column(name = "HOST_BODY_TEMPERATURE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getHostBodyTemperature() {
        return (String) propertyMap.get(HOST_BODY_TEMPERATURE);
    }

    public void setHostBodyTemperature(String hostBodyTemperature) {
        propertyMap.put(HOST_BODY_TEMPERATURE, hostBodyTemperature);
    }

    private final String BODY_MASS_INDEX = "BodyMassIndex";

    @javax.persistence.Column(name = "BODY_MASS_INDEX", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getBodyMassIndex() {
        return (String) propertyMap.get(BODY_MASS_INDEX);
    }

    public void setBodyMassIndex(String bodyMassIndex) {
        propertyMap.put(BODY_MASS_INDEX, bodyMassIndex);
    }

    private final String IHMC_ETHNICITY = "IhmcEthnicity";

    @javax.persistence.Column(name = "IHMC_ETHNICITY", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getIhmcEthnicity() {
        return (String) propertyMap.get(IHMC_ETHNICITY);
    }

    public void setIhmcEthnicity(String ihmcEthnicity) {
        propertyMap.put(IHMC_ETHNICITY, ihmcEthnicity);
    }

    private final String OCCUPATION = "Occupation";

    @javax.persistence.Column(name = "OCCUPATION", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getOccupation() {
        return (String) propertyMap.get(OCCUPATION);
    }

    public void setOccupation(String occupation) {
        propertyMap.put(OCCUPATION, occupation);
    }

    private final String MEDICAL_HISTORY_PERFORMED = "MedicalHistoryPerformed";

    @javax.persistence.Column(name = "MEDICAL_HISTORY_PERFORMED", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getMedicalHistoryPerformed() {
        return (String) propertyMap.get(MEDICAL_HISTORY_PERFORMED);
    }

    public void setMedicalHistoryPerformed(String medicalHistoryPerformed) {
        propertyMap.put(MEDICAL_HISTORY_PERFORMED, medicalHistoryPerformed);
    }

    private final String PULSE = "Pulse";

    @javax.persistence.Column(name = "PULSE", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getPulse() {
        return (String) propertyMap.get(PULSE);
    }

    public void setPulse(String pulse) {
        propertyMap.put(PULSE, pulse);
    }

    private final String PERTURBATION = "Perturbation";

    @javax.persistence.Column(name = "PERTURBATION", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getPerturbation() {
        return (String) propertyMap.get(PERTURBATION);
    }

    public void setPerturbation(String perturbation) {
        propertyMap.put(PERTURBATION, perturbation);
    }

    private final String SAMPLE_SALINITY = "SampleSalinity";

    @javax.persistence.Column(name = "SAMPLE_SALINITY", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getSampleSalinity() {
        return (String) propertyMap.get(SAMPLE_SALINITY);
    }

    public void setSampleSalinity(String sampleSalinity) {
        propertyMap.put(SAMPLE_SALINITY, sampleSalinity);
    }

    private final String MISC_PARAM_1 = "MiscParam1";

    @javax.persistence.Column(name = "MISC_PARAM_1", nullable = true, insertable = true, updatable = true, length = 30, precision = 0)
    @javax.persistence.Basic
    public String getMiscParam1() {
        return (String) propertyMap.get(MISC_PARAM_1);
    }

    public void setMiscParam1(String miscParam1) {
        propertyMap.put(MISC_PARAM_1, miscParam1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmgSample emgSample = (EmgSample) o;

        if (this.getAlt() != emgSample.getAlt()) return false;
        if (this.getElevation() != emgSample.getElevation()) return false;
        if (this.getAmmonium() != null ? !this.getAmmonium().equals(emgSample.getAmmonium()) : emgSample.getAmmonium() != null)
            return false;
        if (this.getAmountOrSize() != null ? !this.getAmountOrSize().equals(emgSample.getAmountOrSize()) : emgSample.getAmountOrSize() != null)
            return false;
        if (this.getBiome() != null ? !this.getBiome().equals(emgSample.getBiome()) : emgSample.getBiome() != null)
            return false;
        if (this.getBodyMassIndex() != null ? !this.getBodyMassIndex().equals(emgSample.getBodyMassIndex()) : emgSample.getBodyMassIndex() != null)
            return false;
        if (this.getChlorophyll() != null ? !this.getChlorophyll().equals(emgSample.getChlorophyll()) : emgSample.getChlorophyll() != null)
            return false;
        if (this.getCollectionDate() != null ? !this.getCollectionDate().equals(emgSample.getCollectionDate()) : emgSample.getCollectionDate() != null)
            return false;
        if (this.getCollectionDeviceMethod() != null ? !this.getCollectionDeviceMethod().equals(emgSample.getCollectionDeviceMethod()) : emgSample.getCollectionDeviceMethod() != null)
            return false;
        if (this.getDepth() != null ? !this.getDepth().equals(emgSample.getDepth()) : emgSample.getDepth() != null)
            return false;
        if (this.getDiet() != null ? !this.getDiet().equals(emgSample.getDiet()) : emgSample.getDiet() != null)
            return false;
        if (this.getFamilyRelationship() != null ? !this.getFamilyRelationship().equals(emgSample.getFamilyRelationship()) : emgSample.getFamilyRelationship() != null)
            return false;
        if (this.getFeature() != null ? !this.getFeature().equals(emgSample.getFeature()) : emgSample.getFeature() != null)
            return false;
        if (this.getGenotype() != null ? !this.getGenotype().equals(emgSample.getGenotype()) : emgSample.getGenotype() != null)
            return false;
        if (this.getGeoLocName() != null ? !this.getGeoLocName().equals(emgSample.getGeoLocName()) : emgSample.getGeoLocName() != null)
            return false;
        if (this.getHeight() != null ? !this.getHeight().equals(emgSample.getHeight()) : emgSample.getHeight() != null)
            return false;
        if (this.getHostBodyTemperature() != null ? !this.getHostBodyTemperature().equals(emgSample.getHostBodyTemperature()) : emgSample.getHostBodyTemperature() != null)
            return false;
        if (this.getHostSex() != null ? !this.getHostSex().equals(emgSample.getHostSex()) : emgSample.getHostSex() != null)
            return false;
        if (this.getHostSubjectId() != null ? !this.getHostSubjectId().equals(emgSample.getHostSubjectId()) : emgSample.getHostSubjectId() != null)
            return false;
        if (this.getHostTaxid() != null ? !this.getHostTaxid().equals(emgSample.getHostTaxid()) : emgSample.getHostTaxid() != null)
            return false;
        if (this.getIhmcEthnicity() != null ? !this.getIhmcEthnicity().equals(emgSample.getIhmcEthnicity()) : emgSample.getIhmcEthnicity() != null)
            return false;
        if (this.getLastMeal() != null ? !this.getLastMeal().equals(emgSample.getLastMeal()) : emgSample.getLastMeal() != null)
            return false;
        if (this.getLatLon() != null ? !this.getLatLon().equals(emgSample.getLatLon()) : emgSample.getLatLon() != null)
            return false;
        if (this.getMaterial() != null ? !this.getMaterial().equals(emgSample.getMaterial()) : emgSample.getMaterial() != null)
            return false;
        if (this.getMedicalHistoryPerformed() != null ? !this.getMedicalHistoryPerformed().equals(emgSample.getMedicalHistoryPerformed()) : emgSample.getMedicalHistoryPerformed() != null)
            return false;
        if (this.getMiscParam1() != null ? !this.getMiscParam1().equals(emgSample.getMiscParam1()) : emgSample.getMiscParam1() != null)
            return false;
        if (this.getNitrate() != null ? !this.getNitrate().equals(emgSample.getNitrate()) : emgSample.getNitrate() != null)
            return false;
        if (this.getNucleicAcidAmplification() != null ? !this.getNucleicAcidAmplification().equals(emgSample.getNucleicAcidAmplification()) : emgSample.getNucleicAcidAmplification() != null)
            return false;
        if (this.getNucleicAcidExtraction() != null ? !this.getNucleicAcidExtraction().equals(emgSample.getNucleicAcidExtraction()) : emgSample.getNucleicAcidExtraction() != null)
            return false;
        if (this.getOccupation() != null ? !this.getOccupation().equals(emgSample.getOccupation()) : emgSample.getOccupation() != null)
            return false;
        if (this.getOrganicCarbon() != null ? !this.getOrganicCarbon().equals(emgSample.getOrganicCarbon()) : emgSample.getOrganicCarbon() != null)
            return false;
        if (this.getOrganicNitrogen() != null ? !this.getOrganicNitrogen().equals(emgSample.getOrganicNitrogen()) : emgSample.getOrganicNitrogen() != null)
            return false;
        if (this.getOxygenationStatusOfSample() != null ? !this.getOxygenationStatusOfSample().equals(emgSample.getOxygenationStatusOfSample()) : emgSample.getOxygenationStatusOfSample() != null)
            return false;
        if (this.getPerturbation() != null ? !this.getPerturbation().equals(emgSample.getPerturbation()) : emgSample.getPerturbation() != null)
            return false;
        if (this.getPh() != null ? !this.getPh().equals(emgSample.getPh()) : emgSample.getPh() != null) return false;
        if (this.getPhenotype() != null ? !this.getPhenotype().equals(emgSample.getPhenotype()) : emgSample.getPhenotype() != null)
            return false;
        if (this.getPhosphate() != null ? !this.getPhosphate().equals(emgSample.getPhosphate()) : emgSample.getPhosphate() != null)
            return false;
        if (this.getPulse() != null ? !this.getPulse().equals(emgSample.getPulse()) : emgSample.getPulse() != null)
            return false;
        if (this.getRelevantElectronicResources() != null ? !this.getRelevantElectronicResources().equals(emgSample.getRelevantElectronicResources()) : emgSample.getRelevantElectronicResources() != null)
            return false;
        if (this.getSalinity() != null ? !this.getSalinity().equals(emgSample.getSalinity()) : emgSample.getSalinity() != null)
            return false;
        if (this.getSampStoreTemp() != null ? !this.getSampStoreTemp().equals(emgSample.getSampStoreTemp()) : emgSample.getSampStoreTemp() != null)
            return false;
        if (this.getSampleClassification() != null ? !this.getSampleClassification().equals(emgSample.getSampleClassification()) : emgSample.getSampleClassification() != null)
            return false;
        if (this.getSampleDescription() != null ? !this.getSampleDescription().equals(emgSample.getSampleDescription()) : emgSample.getSampleDescription() != null)
            return false;
        if (this.getSampleId() != null ? !this.getSampleId().equals(emgSample.getSampleId()) : emgSample.getSampleId() != null)
            return false;
        if (this.getSampleMaterialProcessing() != null ? !this.getSampleMaterialProcessing().equals(emgSample.getSampleMaterialProcessing()) : emgSample.getSampleMaterialProcessing() != null)
            return false;
        if (this.getSampleSalinity() != null ? !this.getSampleSalinity().equals(emgSample.getSampleSalinity()) : emgSample.getSampleSalinity() != null)
            return false;
        if (this.getSampleStoreDuration() != null ? !this.getSampleStoreDuration().equals(emgSample.getSampleStoreDuration()) : emgSample.getSampleStoreDuration() != null)
            return false;
        if (this.getSampleStoreLocation() != null ? !this.getSampleStoreLocation().equals(emgSample.getSampleStoreLocation()) : emgSample.getSampleStoreLocation() != null)
            return false;
        if (this.getSampleTitle() != null ? !this.getSampleTitle().equals(emgSample.getSampleTitle()) : emgSample.getSampleTitle() != null)
            return false;
        if (this.getSequencingMethod() != null ? !this.getSequencingMethod().equals(emgSample.getSequencingMethod()) : emgSample.getSequencingMethod() != null)
            return false;
        if (this.getSilicate() != null ? !this.getSilicate().equals(emgSample.getSilicate()) : emgSample.getSilicate() != null)
            return false;
        if (this.getStudyId() != null ? !this.getStudyId().equals(emgSample.getStudyId()) : emgSample.getStudyId() != null)
            return false;
        if (this.getTemperature() != null ? !this.getTemperature().equals(emgSample.getTemperature()) : emgSample.getTemperature() != null)
            return false;
        if (this.getTotalDepthOfWater() != null ? !this.getTotalDepthOfWater().equals(emgSample.getTotalDepthOfWater()) : emgSample.getTotalDepthOfWater() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getSampleId() != null ? getSampleId().hashCode() : 0;
        result = 31 * result + (getStudyId() != null ? getStudyId().hashCode() : 0);
        result = 31 * result + (getSampleTitle() != null ? getSampleTitle().hashCode() : 0);
        result = 31 * result + (getSampleDescription() != null ? getSampleDescription().hashCode() : 0);
        result = 31 * result + (getSampleClassification() != null ? getSampleClassification().hashCode() : 0);
        result = 31 * result + (getGeoLocName() != null ? getGeoLocName().hashCode() : 0);
        result = 31 * result + (getLatLon() != null ? getLatLon().hashCode() : 0);
        result = 31 * result + (getCollectionDate() != null ? getCollectionDate().hashCode() : 0);
        result = 31 * result + (getBiome() != null ? getBiome().hashCode() : 0);
        result = 31 * result + (getFeature() != null ? getFeature().hashCode() : 0);
        result = 31 * result + (getMaterial() != null ? getMaterial().hashCode() : 0);
        result = 31 * result + getAlt();
        result = 31 * result + (getDepth() != null ? getDepth().hashCode() : 0);
        result = 31 * result + getElevation();
        result = 31 * result + (getHostTaxid() != null ? getHostTaxid().hashCode() : 0);
        result = 31 * result + (getHostSubjectId() != null ? getHostSubjectId().hashCode() : 0);
        result = 31 * result + (getSampStoreTemp() != null ? getSampStoreTemp().hashCode() : 0);
        result = 31 * result + (getSampleStoreLocation() != null ? getSampleStoreLocation().hashCode() : 0);
        result = 31 * result + (getSampleStoreDuration() != null ? getSampleStoreDuration().hashCode() : 0);
        result = 31 * result + (getSequencingMethod() != null ? getSequencingMethod().hashCode() : 0);
        result = 31 * result + (getRelevantElectronicResources() != null ? getRelevantElectronicResources().hashCode() : 0);
        result = 31 * result + (getCollectionDeviceMethod() != null ? getCollectionDeviceMethod().hashCode() : 0);
        result = 31 * result + (getAmountOrSize() != null ? getAmountOrSize().hashCode() : 0);
        result = 31 * result + (getSampleMaterialProcessing() != null ? getSampleMaterialProcessing().hashCode() : 0);
        result = 31 * result + (getNucleicAcidExtraction() != null ? getNucleicAcidExtraction().hashCode() : 0);
        result = 31 * result + (getNucleicAcidAmplification() != null ? getNucleicAcidAmplification().hashCode() : 0);
        result = 31 * result + (getTotalDepthOfWater() != null ? getTotalDepthOfWater().hashCode() : 0);
        result = 31 * result + (getTemperature() != null ? getTemperature().hashCode() : 0);
        result = 31 * result + (getSilicate() != null ? getSilicate().hashCode() : 0);
        result = 31 * result + (getSalinity() != null ? getSalinity().hashCode() : 0);
        result = 31 * result + (getPhosphate() != null ? getPhosphate().hashCode() : 0);
        result = 31 * result + (getPh() != null ? getPh().hashCode() : 0);
        result = 31 * result + (getOxygenationStatusOfSample() != null ? getOxygenationStatusOfSample().hashCode() : 0);
        result = 31 * result + (getOrganicNitrogen() != null ? getOrganicNitrogen().hashCode() : 0);
        result = 31 * result + (getOrganicCarbon() != null ? getOrganicCarbon().hashCode() : 0);
        result = 31 * result + (getNitrate() != null ? getNitrate().hashCode() : 0);
        result = 31 * result + (getChlorophyll() != null ? getChlorophyll().hashCode() : 0);
        result = 31 * result + (getAmmonium() != null ? getAmmonium().hashCode() : 0);
        result = 31 * result + (getHostSex() != null ? getHostSex().hashCode() : 0);
        result = 31 * result + (getHeight() != null ? getHeight().hashCode() : 0);
        result = 31 * result + (getHeight() != null ? getDiet().hashCode() : 0);
        result = 31 * result + (getLastMeal() != null ? getLastMeal().hashCode() : 0);
        result = 31 * result + (getFamilyRelationship() != null ? getFamilyRelationship().hashCode() : 0);
        result = 31 * result + (getGenotype() != null ? getGenotype().hashCode() : 0);
        result = 31 * result + (getPhenotype() != null ? getPhenotype().hashCode() : 0);
        result = 31 * result + (getHostBodyTemperature() != null ? getHostBodyTemperature().hashCode() : 0);
        result = 31 * result + (getBodyMassIndex() != null ? getBodyMassIndex().hashCode() : 0);
        result = 31 * result + (getIhmcEthnicity() != null ? getIhmcEthnicity().hashCode() : 0);
        result = 31 * result + (getOccupation() != null ? getOccupation().hashCode() : 0);
        result = 31 * result + (getMedicalHistoryPerformed() != null ? getMedicalHistoryPerformed().hashCode() : 0);
        result = 31 * result + (getPulse() != null ? getPulse().hashCode() : 0);
        result = 31 * result + (getPerturbation() != null ? getPerturbation().hashCode() : 0);
        result = 31 * result + (getSampleSalinity() != null ? getSampleSalinity().hashCode() : 0);
        result = 31 * result + (getMiscParam1() != null ? getMiscParam1().hashCode() : 0);
        return result;
    }
}