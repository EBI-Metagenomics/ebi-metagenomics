package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.HomePageSamplesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.HomePageStudiesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.HomePageViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.*;

/**
 * Model builder class for StudyViewModel. See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class HomePageViewModelBuilder extends AbstractBiomeViewModelBuilder<HomePageViewModel> {

    private final static Log log = LogFactory.getLog(HomePageViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private StudyDAO studyDAO;

    private SampleDAO sampleDAO;

    private BiomeDAO biomeDAO;

    private RunDAO runDAO;

    /**
     * The number of latest project and samples to show on the home page. Used within this builder class, but also within the Java Server Page.
     */
    private final int maxRowNumberOfLatestItems = 5;


    public HomePageViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                                    StudyDAO studyDAO, SampleDAO sampleDAO, RunDAO runDAO, BiomeDAO biomeDAO) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.studyDAO = studyDAO;
        this.sampleDAO = sampleDAO;
        this.runDAO = runDAO;
        this.biomeDAO = biomeDAO;
    }

    public HomePageViewModel getModel() {
        log.info("Building instance of " + HomePageViewModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        final Long publicSamplesCount = sampleDAO.countAllPublic();
        final Long privateSamplesCount = sampleDAO.countAllPrivate();
        final Long publicStudiesCount = studyDAO.countAllPublic();
        final Long privateStudiesCount = studyDAO.countAllPrivate();
        final int publicRunCount = runDAO.countAllPublic();
        final int privateRunCount = runDAO.countAllPrivate();
        final Map<String, Integer> experimentCountMap = runDAO.retrieveRunCountsGroupedByExperimentType(3);
        final Long numOfSubmitters = studyDAO.countDistinctSubmissionAccounts();
        final Long numOfSubmissions = studyDAO.countDistinct();
        // If case: if nobody is logged in
        if (submitter == null) {
            // Retrieve public studies and order them by last meta data received
            List<Study> studies = getOrderedPublicStudies();
            attachSampleSize(studies);
            // Retrieve public samples and order them by last meta data received
            List<Sample> samples = getPublicSamples();
            Collections.sort(samples, new HomePageSamplesComparator());
            samples = samples.subList(0, getToIndex(samples));

            Map<String, Long> biomeCountMap = buildBiomeCountMap();
            return new HomePageViewModel(submitter, samples, pageTitle, breadcrumbs, propertyContainer, maxRowNumberOfLatestItems, publicSamplesCount,
                    privateSamplesCount, publicStudiesCount, privateStudiesCount, studies, publicRunCount, privateRunCount, biomeCountMap, experimentCountMap, numOfSubmitters,
                    numOfSubmissions);
        }
        //  Else case: if somebody is logged in
        else {
            //retrieve private studies and order them by last meta data received
            List<Study> myStudies = getStudiesBySubmitter(submitter.getSubmissionAccountId(), studyDAO);
            Map<Study, Long> myStudiesMap = getStudySampleSizeMap(myStudies, sampleDAO, new HomePageStudiesComparator());

            //retrieve private samples and order them last meta data received
            List<Sample> mySamples = getSamplesBySubmitter(submitter.getSubmissionAccountId(), sampleDAO);
            Collections.sort(mySamples, new HomePageSamplesComparator());
            mySamples = mySamples.subList(0, getToIndex(mySamples));

            final Long mySamplesCount = (mySamples != null ? new Long(mySamples.size()) : new Long(0));
            final Long myStudiesCount = (myStudies != null ? new Long(myStudies.size()) : new Long(0));

            return new HomePageViewModel(submitter, myStudiesMap, mySamples, pageTitle, breadcrumbs, propertyContainer, maxRowNumberOfLatestItems,
                    mySamplesCount, myStudiesCount, publicSamplesCount, privateSamplesCount, publicStudiesCount, privateStudiesCount, publicRunCount, privateRunCount);
        }
    }

    private Map<String, Long> buildBiomeCountMap() {
        final Map<String, Long> biomesCountMap = new HashMap<String, Long>();
        //Add number of soil biomes
        long studyCount = super.countStudiesFilteredByBiomes(studyDAO, biomeDAO, StudyFilter.Biome.SOIL.getLineages());
        biomesCountMap.put(StudyFilter.Biome.SOIL.toString(), studyCount);
        //Add number of marine biomes
        studyCount = super.countStudiesFilteredByBiomes(studyDAO, biomeDAO, StudyFilter.Biome.MARINE.getLineages());
        biomesCountMap.put(StudyFilter.Biome.MARINE.toString(), studyCount);
        //Add number of forest biomes
        studyCount = super.countStudiesFilteredByBiomes(studyDAO, biomeDAO, StudyFilter.Biome.FOREST_SOIL.getLineages());
        biomesCountMap.put(StudyFilter.Biome.FOREST_SOIL.toString(), studyCount);
        //Add number of freshwater biomes
        studyCount = super.countStudiesFilteredByBiomes(studyDAO, biomeDAO, StudyFilter.Biome.FRESHWATER.getLineages());
        biomesCountMap.put(StudyFilter.Biome.FRESHWATER.toString(), studyCount);
        //Add number of grassland biomes
        studyCount = super.countStudiesFilteredByBiomes(studyDAO, biomeDAO, StudyFilter.Biome.GRASSLAND.getLineages());
        biomesCountMap.put(StudyFilter.Biome.GRASSLAND.toString(), studyCount);
        //Add number of human gut biomes
        studyCount = super.countStudiesFilteredByBiomes(studyDAO, biomeDAO, StudyFilter.Biome.HUMAN_GUT.getLineages());
        biomesCountMap.put(StudyFilter.Biome.HUMAN_GUT.toString(), studyCount);
        //Add number of engineered biomes
        studyCount = super.countStudiesFilteredByBiomes(studyDAO, biomeDAO, StudyFilter.Biome.ENGINEERED.getLineages());
        biomesCountMap.put(StudyFilter.Biome.ENGINEERED.toString(), studyCount);
        //Add number of air biomes
        studyCount = super.countStudiesFilteredByBiomes(studyDAO, biomeDAO, StudyFilter.Biome.AIR.getLineages());
        biomesCountMap.put(StudyFilter.Biome.AIR.toString(), studyCount);
        //Add number of wastewater biomes
        studyCount = super.countStudiesFilteredByBiomes(studyDAO, biomeDAO, StudyFilter.Biome.WASTEWATER.getLineages());
        biomesCountMap.put(StudyFilter.Biome.WASTEWATER.toString(), studyCount);
        return biomesCountMap;
    }

    private void attachSampleSize(List<Study> studies) {
        if (sampleDAO != null) {
            for (Study study : studies) {
                long sampleSize = sampleDAO.retrieveSampleSizeByStudyId(study.getId());
                study.setSampleSize(new Long(sampleSize));
            }
        }
    }

    /**
     * Returns a list of public studies limited by a specified number of rows and order by meta data received date.
     */
    private List<Study> getOrderedPublicStudies() {
        List<Study> studies = null;
        if (studyDAO != null) {
            studies = studyDAO.retrieveOrderedPublicStudies("lastMetadataReceived", true);
        }
        if (studies != null && !studies.isEmpty()) {
            for (Study study : studies) {
                MemiTools.assignBiomeIconCSSClass(study, biomeDAO);
            }
            return studies;
        } else {
            return new ArrayList<Study>();
        }
    }

    private Map<Study, Long> getStudySampleSizeMap(List<Study> studies, SampleDAO sampleDAO, Comparator<Study> comparator) {
        Map<Study, Long> result = new TreeMap<Study, Long>(comparator);
        for (Study study : studies) {
            if (sampleDAO != null) {
                result.put(study, sampleDAO.retrieveSampleSizeByStudyId(study.getId()));
            }
        }
        return result;
    }

    /**
     * Returns a list of public sample limited by a specified number of rows and order by received date.
     */
    public List<Sample> getPublicSamples() {
        List<Sample> samples = new ArrayList<Sample>();
        if (sampleDAO != null) {
            samples = sampleDAO.retrieveAllPublicSamples();
        }
        return samples;
    }

    private int getToIndex(Collection<Sample> collection) {
        return ((collection.size() > 5) ? 5 : collection.size());
    }

    /**
     * Returns a list of studies for the specified submitter.
     */
    private List<Study> getStudiesBySubmitter(String submissionAccountId, StudyDAO studyDAO) {
        List<Study> studies = new ArrayList<Study>();
        if (studyDAO != null) {
            studies = studyDAO.retrieveStudiesBySubmitter(submissionAccountId);
        }
        return studies;
    }

    /**
     * Returns a list of studies for the specified submitter, limited by a specified number of rows.
     */
    private List<Sample> getSamplesBySubmitter(String submissionAccountId, SampleDAO sampleDAO) {
        List<Sample> samples = new ArrayList<Sample>();
        if (sampleDAO != null) {
            samples = sampleDAO.retrieveSamplesBySubmitter(submissionAccountId);
        }
        return samples;
    }

    private List<Study> getPublicStudiesWithoutSubId(long submitterId, StudyDAO studyDAO) {
        List<Study> studies = new ArrayList<Study>();
        if (studyDAO != null) {
            studies = studyDAO.retrievePublicStudiesWithoutSubId(submitterId);
        }
        return studies;
    }

    private List<Sample> getOrderedPublicSamplesWithoutSubId(long submitterId, SampleDAO sampleDAO) {
        List<Sample> samples = new ArrayList<Sample>();
        if (sampleDAO != null) {
            samples = sampleDAO.retrievePublicSamplesWithoutSubId(submitterId);
        }
        return samples;
    }

}