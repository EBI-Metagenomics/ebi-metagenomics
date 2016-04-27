package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents the model for the home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public final class HomePageViewModel extends ViewModel {

    /**
     * Maps studies and their number of samples.
     */
    private List<Study> studies;

//    private List<Sample> publicSamples;

    /**
     * Maps studies and their number of samples.
     */
    private Map<Study, Long> myStudiesMap;

    /**
     * Maps biomes with biome counts
     */
    private Map<String, Long> biomeMap;

    private List<Sample> mySamples;

    /**
     * The number of latest project and samples to show on the home page.
     */
    private final int maxRowNumberOfLatestItems;

    private Long publicSamplesCount;

    private Long privateSamplesCount;

    private Long publicStudiesCount;

    private Long privateStudiesCount;

    private Long mySamplesCount;

    private Long myStudiesCount;

    private int publicRunCount;

    private int privateRunCount;

    private Map<String, Long> experimentCountMap;

    private Long numOfDataSets;

    private Map<String, Long> studyToSampleCountMap;

    private Map<String, Long> studyToRunCountMap;

    public HomePageViewModel(final Submitter submitter,
                             final String pageTitle,
                             final List<Breadcrumb> breadcrumbs,
                             final MemiPropertyContainer propertyContainer,
                             final int maxRowNumberOfLatestItems,
                             final Long publicSamplesCount,
                             final Long privateSamplesCount,
                             final Long publicStudiesCount,
                             final Long privateStudiesCount,
                             final List<Study> studies,
                             final int publicRunCount,
                             final int privateRunCount,
                             final Map<String, Long> biomeCountMap,
                             final Map<String, Long> experimentCountMap,
                             final Long numOfDataSets,
                             final Map<String, Long> studyToSampleCountMap,
                             final Map<String, Long> studyToRunCountMap) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
//        this.publicSamples = publicSamples;
        this.myStudiesMap = new TreeMap<Study, Long>();
        this.mySamples = new ArrayList<Sample>();
        this.maxRowNumberOfLatestItems = maxRowNumberOfLatestItems;
        this.privateSamplesCount = privateSamplesCount;
        this.publicSamplesCount = publicSamplesCount;
        this.publicStudiesCount = publicStudiesCount;
        this.privateStudiesCount = privateStudiesCount;
        this.studies = studies;
        this.publicRunCount = publicRunCount;
        this.privateRunCount = privateRunCount;
        this.biomeMap = biomeCountMap;
        this.experimentCountMap = experimentCountMap;
        this.numOfDataSets = numOfDataSets;
        this.studyToSampleCountMap = studyToSampleCountMap;
        this.studyToRunCountMap = studyToRunCountMap;

    }

    public HomePageViewModel(final Submitter submitter,
//                             final Map<Study, Long> myStudiesMap,
                             final List<Study> studies,
                             final List<Sample> mySamples,
                             final String pageTitle,
                             final List<Breadcrumb> breadcrumbs,
                             final MemiPropertyContainer propertyContainer,
                             final int maxRowNumberOfLatestItems,
                             final Long mySamplesCount,
                             final Long myStudiesCount,
                             final Long publicSamplesCount,
                             final Long privateSamplesCount,
                             final Long publicStudiesCount,
                             final Long privateStudiesCount,
                             final int publicRunCount,
                             final int privateRunCount,
                             final Map<String, Long> studyToSampleCountMap,
                             final Map<String, Long> studyToRunCountMap) {
        this(submitter, pageTitle, breadcrumbs, propertyContainer, maxRowNumberOfLatestItems,
                publicSamplesCount, privateSamplesCount, publicStudiesCount, privateStudiesCount,studies, publicRunCount, privateRunCount,
                null, null, null, studyToSampleCountMap, studyToRunCountMap);
//        this.myStudiesMap = myStudiesMap;
        this.mySamples = mySamples;
        this.mySamplesCount = mySamplesCount;
        this.myStudiesCount = myStudiesCount;
    }

    public List<Study> getStudies() {
        return studies;
    }

    public Map<Study, Long> getMyStudiesMap() {
        return myStudiesMap;
    }

    public void setMyStudiesMap(Map<Study, Long> myStudiesMap) {
        this.myStudiesMap = myStudiesMap;
    }

//    public List<Sample> getPublicSamples() {
//        return publicSamples;
//    }

//    public void setPublicSamples(List<Sample> publicSamples) {
//        this.publicSamples = publicSamples;
//    }


    public List<Sample> getMySamples() {
        return mySamples;
    }

    public void setMySamples(List<Sample> mySamples) {
        this.mySamples = mySamples;
    }

    public int getMaxRowNumberOfLatestItems() {
        return maxRowNumberOfLatestItems;
    }

    public Long getPublicSamplesCount() {
        return publicSamplesCount;
    }

    public Long getPrivateSamplesCount() {
        return privateSamplesCount;
    }

    public Long getPublicStudiesCount() {
        return publicStudiesCount;
    }

    public Long getPrivateStudiesCount() {
        return privateStudiesCount;
    }

    public Long getMySamplesCount() {
        return mySamplesCount;
    }

    public Long getMyStudiesCount() {
        return myStudiesCount;
    }

    public int getPublicRunCount() {
        return publicRunCount;
    }

    public int getPrivateRunCount() {
        return privateRunCount;
    }

    public Map<String, Long> getBiomeMap() {
        return biomeMap;
    }

    public void setBiomeMap(Map<String, Long> biomeMap) {
        this.biomeMap = biomeMap;
    }

    public Map<String, Long> getExperimentCountMap() {
        return experimentCountMap;
    }

    public Long getNumOfDataSets() {
        return numOfDataSets;
    }

    public Map<String, Long> getStudyToSampleCountMap() {
        return studyToSampleCountMap;
    }

    public Map<String, Long> getStudyToRunCountMap() {
        return studyToRunCountMap;
    }
}