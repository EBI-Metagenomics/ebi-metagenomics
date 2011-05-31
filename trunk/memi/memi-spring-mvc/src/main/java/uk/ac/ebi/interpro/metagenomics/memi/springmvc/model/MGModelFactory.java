package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import au.com.bytecode.opencsv.CSVReader;
import com.sun.syndication.feed.synd.SyndEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.basic.comparators.HomePageSamplesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.basic.comparators.HomePageStudiesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.basic.comparators.ViewSamplesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.basic.comparators.ViewStudiesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.feed.RomeClient;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.googlechart.GoogleChartFactory;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Represents a Metagenomics model factory. Use this factory if you want to create a {@link ViewModel}.
 * TODO: Please note, that this factory class is deprecated and needs to be replace by {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.AbstractViewModelBuilder}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Deprecated
public class MGModelFactory {
    private final static Log log = LogFactory.getLog(MGModelFactory.class);

    /**
     * The number of RSS news items to show on the portal home page.
     */
    private final static int maxRssRowNumber = 3;

    private MGModelFactory() {

    }

    public static HomePageModel getHomePageModel(SessionManager sessionMgr, HibernateStudyDAO studyDAO,
                                                 HibernateSampleDAO sampleDAO, RomeClient romeClient,
                                                 String pageTitle, List<Breadcrumb> breadcrumbs,
                                                 MemiPropertyContainer propertyContainer) {
        log.info("Building instance of " + HomePageModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        // Get RSS URL
        // TODO: Replace local cached feed with Twitter feed when fixed @Cacheable problem
        String rssUrl = "http://twitter.com/statuses/user_timeline/257482404.rss";
//        try {
//            rssUrl = "http://twitter.com/statuses/user_timeline/257482404.rss";
//        }
//        catch (IOException e) {
//            rssUrl = "unknown"; // TODO: Put URL default here
//            log.warn("Could not get RSS feed URL", e);
//        }
        // Get RSS entries
        List<SyndEntry> rssEntries = Collections.emptyList();
        try {
            rssEntries = romeClient.getEntries();
            if (rssEntries.size() > maxRssRowNumber) {
                // Limit number of rss entries
                rssEntries = rssEntries.subList(0, maxRssRowNumber);
            }
        } catch (Exception e) {
            log.warn("Could not get RSS entries", e);
        }
//        If case: if nobody is logged in
        if (submitter == null) {
            //retrieve public studies and order them by last meta data received
            List<Study> studies = getPublicStudies(studyDAO);
            Map<Study, Long> publicStudiesMap = getStudySampleSizeMap(studies, sampleDAO, new HomePageStudiesComparator());
            //retrieve public samples and order them by last meta data received
            List<Sample> samples = getPublicSamples(sampleDAO);
            Collections.sort(samples, new HomePageSamplesComparator());
            samples = samples.subList(0, getToIndex(samples));

            return new HomePageModel(submitter, publicStudiesMap, samples,
                    rssUrl, rssEntries, pageTitle, breadcrumbs, propertyContainer);
        }
//        Else case: if somebody is logged in
        else {
            //retrieve private studies and order them by last meta data received
            List<Study> myStudies = getStudiesBySubmitter(submitter.getSubmitterId(), studyDAO);
            Map<Study, Long> myStudiesMap = getStudySampleSizeMap(myStudies, sampleDAO, new HomePageStudiesComparator());

            //retrieve private samples and order them last meta data received
            List<Sample> mySamples = getSamplesBySubmitter(submitter.getSubmitterId(), sampleDAO);
            Collections.sort(mySamples, new HomePageSamplesComparator());
            mySamples = mySamples.subList(0, getToIndex(mySamples));

            //retrieve public studies and order them last meta data received
            List<Study> publicStudies = getPublicStudiesWithoutSubId(submitter.getSubmitterId(), studyDAO);
            Map<Study, Long> publicStudiesMap = getStudySampleSizeMap(publicStudies, sampleDAO, new HomePageStudiesComparator());

            //retrieve public samples and order them last meta data received
            List<Sample> publicSamples = getOrderedPublicSamplesWithoutSubId(submitter.getSubmitterId(), sampleDAO);
            Collections.sort(publicSamples, new HomePageSamplesComparator());
            publicSamples = publicSamples.subList(0, getToIndex(publicSamples));

            return new HomePageModel(submitter, publicStudiesMap, publicSamples, rssUrl, rssEntries,
                    myStudiesMap, mySamples, pageTitle, breadcrumbs, propertyContainer);
        }
    }

    private static int getToIndex(Collection<Sample> collection) {
        return ((collection.size() > 5) ? 5 : collection.size());
    }

    public static ViewModel getMGModel(SessionManager sessionMgr) {
        return getMGModel(sessionMgr, "EBI Metagenomics Portal", new ArrayList<Breadcrumb>(), null);
    }

    public static ViewModel getMGModel(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs,
                                       MemiPropertyContainer propertyContainer) {
        log.info("Building instance of " + ViewModel.class + "...");
        return new ViewModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer);
    }


    public static AnalysisStatsModel getAnalysisStatsModel(SessionManager sessionManager, Sample sample,
                                                           String pageTitle, List<Breadcrumb> breadcrumbs,
                                                           EmgFile emgFile, List<String> archivedSequences,
                                                           MemiPropertyContainer propertyContainer,
                                                           boolean isReturnSizeLimit) {
        log.info("Building instance of " + AnalysisStatsModel.class + "...");
        if (emgFile != null) {
            Map<Class, List<AbstractGOTerm>> goData = loadGODataFromCSV(propertyContainer.getPathToAnalysisDirectory(),
                    emgFile);
            return new AnalysisStatsModel(getSessionSubmitter(sessionManager), pageTitle, breadcrumbs, sample,
                    getBarChartURL(propertyContainer.getPathToAnalysisDirectory(), emgFile),
                    getHBarChartURL(BiologicalProcessGOTerm.class, goData),
                    getHBarChartURL(CellularComponentGOTerm.class, goData),
                    getHBarChartURL(MolecularFunctionGOTerm.class, goData),
                    null,
                    goData.get(BiologicalProcessGOTerm.class), emgFile, archivedSequences, propertyContainer,
                    getListOfInterProEntries(propertyContainer.getPathToAnalysisDirectory(), emgFile, isReturnSizeLimit));
        } else {
            return new AnalysisStatsModel(getSessionSubmitter(sessionManager), pageTitle, breadcrumbs, sample,
                    emgFile, archivedSequences, propertyContainer);
        }
    }

    public static SampleViewModel getSampleViewModel(SessionManager sessionManager, Sample sample,
                                                     List<String> archivedSeqs, String pageTitle,
                                                     List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer) {
        log.info("Building instance of " + SampleViewModel.class + "...");
        return new SampleViewModel(getSessionSubmitter(sessionManager), sample, archivedSeqs, pageTitle,
                breadcrumbs, propertyContainer);
    }

    public static ViewStudiesModel getViewStudiesPageModel(SessionManager sessionMgr, HibernateStudyDAO studyDAO,
                                                           HibernateSampleDAO sampleDAO, StudyFilter filter,
                                                           String pageTitle, List<Breadcrumb> breadcrumbs,
                                                           MemiPropertyContainer propertyContainer,
                                                           List<String> tableHeaderNames) {
        log.info("Building instance of " + ViewStudiesModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        long submitterId = (submitter != null ? submitter.getSubmitterId() : -1L);
        List<Study> studies = getFilteredStudies(studyDAO, filter, submitterId);
        //studies are sorted by study name at the moment
        Map<Study, Long> sortedStudyMap = getStudySampleSizeMap(studies, sampleDAO, new ViewStudiesComparator());
        return new ViewStudiesModel(submitter, sortedStudyMap, pageTitle,
                breadcrumbs, propertyContainer, tableHeaderNames);
    }

    public static ViewSamplesModel getViewSamplesPageModel(SessionManager sessionMgr, HibernateSampleDAO sampleDAO,
                                                           SampleFilter filter, String pageTitle,
                                                           List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                                                           List<String> tableHeaderNames) {
        log.info("Building instance of " + ViewSamplesModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        long submitterId = (submitter != null ? submitter.getSubmitterId() : -1L);
        List<Sample> filteredSamples = getFilteredSamples(sampleDAO, filter, submitterId);
        Set<Sample> sortedSamples = new TreeSet<Sample>(new ViewSamplesComparator());
        sortedSamples.addAll(filteredSamples);
        return new ViewSamplesModel(submitter, sortedSamples, pageTitle, breadcrumbs, propertyContainer, tableHeaderNames);
    }

    /**
     * Returns a list of public studies limited by a specified number of rows and order by meta data received date.
     */
    private static List<Study> getPublicStudies(HibernateStudyDAO studyDAO) {
        List<Study> studies = new ArrayList<Study>();
        if (studyDAO != null) {
            studies = studyDAO.retrievePublicStudies();
        }
        return studies;
    }

    /**
     * Returns a list of studies for the specified submitter.
     */
    private static List<Study> getStudiesBySubmitter(long submitterId, HibernateStudyDAO studyDAO) {
        List<Study> studies = new ArrayList<Study>();
        if (studyDAO != null) {
            studies = studyDAO.retrieveStudiesBySubmitter(submitterId);
        }
        return studies;
    }

    private static List<Study> getPublicStudiesWithoutSubId(long submitterId, HibernateStudyDAO studyDAO) {
        List<Study> studies = new ArrayList<Study>();
        if (studyDAO != null) {
            studies = studyDAO.retrievePublicStudiesWithoutSubId(submitterId);
        }
        return studies;
    }

    /**
     * Returns a list of public sample limited by a specified number of rows and order by received date.
     */
    public static List<Sample> getPublicSamples(HibernateSampleDAO sampleDAO) {
        List<Sample> samples = new ArrayList<Sample>();
        if (sampleDAO != null) {
            samples = sampleDAO.retrieveAllPublicSamples();
        }
        return samples;
    }


    /**
     * Returns a list of studies for the specified submitter, limited by a specified number of rows.
     */
    private static List<Sample> getSamplesBySubmitter(long submitterId, HibernateSampleDAO sampleDAO) {
        List<Sample> samples = new ArrayList<Sample>();
        if (sampleDAO != null) {
            samples = sampleDAO.retrieveSamplesBySubmitter(submitterId);
        }
        return samples;
    }

    private static List<Sample> getOrderedPublicSamplesWithoutSubId(long submitterId, HibernateSampleDAO sampleDAO) {
        List<Sample> samples = new ArrayList<Sample>();
        if (sampleDAO != null) {
            samples = sampleDAO.retrievePublicSamplesWithoutSubId(submitterId);
        }
        return samples;
    }

    private static List<Study> getFilteredStudies(HibernateStudyDAO studyDAO, StudyFilter filter, long submitterId) {
        List<Study> result = studyDAO.retrieveFilteredStudies(buildFilterCriteria(filter, submitterId));
        if (result == null) {
            result = new ArrayList<Study>();
        }
        return result;
    }

    private static Map<Study, Long> getStudySampleSizeMap(List<Study> studies, HibernateSampleDAO sampleDAO, Comparator<Study> comparator) {
        Map<Study, Long> result = new TreeMap<Study, Long>(comparator);
        for (Study study : studies) {
            if (sampleDAO != null) {
                result.put(study, sampleDAO.retrieveSampleSizeByStudyId(study.getId()));
            }
        }
        return result;
    }

    private static List<Sample> getFilteredSamples(HibernateSampleDAO sampleDAO, SampleFilter filter, long submitterId) {
        List<Sample> result = sampleDAO.retrieveFilteredSamples(buildFilterCriteria(filter, submitterId), getSampleClass(filter.getSampleType()));
        if (result == null) {
            result = new ArrayList<Sample>();
        }
        return result;
    }

    private static Class<? extends Sample> getSampleClass(Sample.SampleType type) {
        if (type != null) {
            return type.getClazz();
        }
        // Without knowing the type, return the superclass.
        return Sample.class;
    }

    /**
     * Builds a list of criteria for the specified study filter. These criteria can be used for
     * a Hibernate query.
     */
    private static List<Criterion> buildFilterCriteria(StudyFilter filter, long submitterId) {
        String searchText = filter.getSearchTerm();
        Study.StudyStatus studyStatus = filter.getStudyStatus();
        StudyFilter.StudyVisibility visibility = filter.getStudyVisibility();

        List<Criterion> crits = new ArrayList<Criterion>();
        //add search term criterion
        if (searchText != null && searchText.trim().length() > 0) {
            crits.add(Restrictions.or(Restrictions.ilike("studyId", searchText, MatchMode.ANYWHERE), Restrictions.ilike("studyName", searchText, MatchMode.ANYWHERE)));
        }
        //add study status criterion
        if (studyStatus != null) {
            crits.add(Restrictions.eq("studyStatus", studyStatus));
        }
        //add is public criterion
        if (submitterId > -1) {
            //SELECT * FROM HB_STUDY where submitter_id=?;
            if (visibility.equals(StudyFilter.StudyVisibility.MY_PROJECTS)) {
                crits.add(Restrictions.eq("submitterId", submitterId));
            }
            //select * from hb_study where submitter_id=? and is_public=1;
            else if (visibility.equals(StudyFilter.StudyVisibility.MY_PUBLISHED_PROJECTS)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", true), Restrictions.eq("submitterId", submitterId)));
            }
            //select * from hb_study where submitter_id=? and is_public=0;
            else if (visibility.equals(StudyFilter.StudyVisibility.MY_PREPUBLISHED_PROJECTS)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submitterId", submitterId)));
            }
            //select * from hb_study where is_public=1;
            else if (visibility.equals(StudyFilter.StudyVisibility.ALL_PUBLISHED_PROJECTS)) {
                crits.add(Restrictions.eq("isPublic", true));
            }
            //select * from hb_study where is_public=1 or submitter_id=? and is_public=0;
            else if (visibility.equals(StudyFilter.StudyVisibility.ALL_PROJECTS)) {
                crits.add(Restrictions.or(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submitterId", submitterId)), Restrictions.eq("isPublic", true)));
            }
        } else {
            crits.add(Restrictions.eq("isPublic", true));
        }

        return crits;
    }

    /**
     * Builds a list of criteria for the specified {@link uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter}}.
     * These criteria can be used for a Hibernate query.
     */
    private static List<Criterion> buildFilterCriteria(SampleFilter filter, long submitterId) {
        String searchText = filter.getSearchTerm();
        SampleFilter.SampleVisibility visibility = filter.getSampleVisibility();

        List<Criterion> crits = new ArrayList<Criterion>();
        //add search term criterion
        if (searchText != null && searchText.trim().length() > 0) {
            crits.add(Restrictions.or(Restrictions.ilike("sampleId", searchText, MatchMode.ANYWHERE), Restrictions.ilike("sampleName", searchText, MatchMode.ANYWHERE)));
        }
        //add is public criterion
        if (submitterId > -1) {
            //SELECT * FROM HB_STUDY where submitter_id=?;
            if (visibility.equals(SampleFilter.SampleVisibility.MY_SAMPLES)) {
                crits.add(Restrictions.eq("submitterId", submitterId));
            }
            //select * from hb_study where submitter_id=? and is_public=1;
            else if (visibility.equals(SampleFilter.SampleVisibility.MY_PUBLISHED_SAMPLES)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", true), Restrictions.eq("submitterId", submitterId)));
            }
            //select * from hb_study where submitter_id=? and is_public=0;
            else if (visibility.equals(SampleFilter.SampleVisibility.MY_PREPUBLISHED_SAMPLES)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submitterId", submitterId)));
            }
            //select * from hb_study where is_public=1;
            else if (visibility.equals(SampleFilter.SampleVisibility.ALL_PUBLISHED_SAMPLES)) {
                crits.add(Restrictions.eq("isPublic", true));
            }
            //select * from hb_study where is_public=1 or submitter_id=? and is_public=0;
            else if (visibility.equals(SampleFilter.SampleVisibility.ALL_SAMPLES)) {
                crits.add(Restrictions.or(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submitterId", submitterId)), Restrictions.eq("isPublic", true)));
            }
        } else {
            crits.add(Restrictions.eq("isPublic", true));
        }
        return crits;
    }

    private static Submitter getSessionSubmitter(SessionManager sessionMgr) {
        if (sessionMgr != null && sessionMgr.getSessionBean() != null) {
            return sessionMgr.getSessionBean().getSubmitter();
        }
        return null;
    }


    private static String getHBarChartURL(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {

        List<Integer> data = getGOData(clazz, goData);
        List<String> labels = getGOLabels(clazz, goData);
        Properties props = new Properties();

        props.put(GoogleChartFactory.CHART_MARGIN, "0,40,0,0");
        props.put(GoogleChartFactory.CHART_SIZE, "300x440");
        props.put(GoogleChartFactory.CHART_COLOUR, "ff0a00,ff4700,ff4700,ffb444,ffb444,ffd088,ffd088," +
                "ffebcc,b8b082,b8b082,b8b082,b8b082,b8b082,b8b082,b8b082,b8b082");
        props.put("chxt", "x");
        props.put("chxs", "0,ffffff,0,0,_");

        props.put("chds", "0,600");
        props.put("chxr", "0,0,600");
        props.put("chxs", "0,ffffff,0,0,_");

        return GoogleChartFactory.buildHorizontalBarChartURL(props, data, labels);
    }


    private static List<Integer> getGOData(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {
        List<Integer> result = new ArrayList<Integer>();
        List<AbstractGOTerm> goTerms = null;
        if (clazz.equals(BiologicalProcessGOTerm.class)) {
            goTerms = goData.get(BiologicalProcessGOTerm.class);
        } else if (clazz.equals(CellularComponentGOTerm.class)) {
            goTerms = goData.get(CellularComponentGOTerm.class);
        } else {
            goTerms = goData.get(MolecularFunctionGOTerm.class);
        }
        if (goTerms != null) {
            //the second iteration is to calculate the pie chart data
            for (AbstractGOTerm term : goTerms) {
                result.add(term.getNumberOfMatches());
            }
        }
        return result;
    }

    private static List<String> getGOLabels(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {
        List<String> result = new ArrayList<String>();
        List<AbstractGOTerm> goTerms = null;
        if (clazz.equals(BiologicalProcessGOTerm.class)) {
            goTerms = goData.get(BiologicalProcessGOTerm.class);
        } else if (clazz.equals(CellularComponentGOTerm.class)) {
            goTerms = goData.get(CellularComponentGOTerm.class);
        } else {
            goTerms = goData.get(MolecularFunctionGOTerm.class);
        }
        if (goTerms != null) {
            //the second iteration is to calculate the pie chart data
            for (AbstractGOTerm term : goTerms) {
                result.add(term.toString());
            }
        }
        return result;
    }

    private static Map<Class, List<AbstractGOTerm>> loadGODataFromCSV(String classPathToStatsFile, EmgFile emgFile) {
        Map<Class, List<AbstractGOTerm>> result = new Hashtable<Class, List<AbstractGOTerm>>();
        result.put(BiologicalProcessGOTerm.class, new ArrayList<AbstractGOTerm>());
        result.put(CellularComponentGOTerm.class, new ArrayList<AbstractGOTerm>());
        result.put(MolecularFunctionGOTerm.class, new ArrayList<AbstractGOTerm>());

        log.info("Processing GO slim file...");
        List<String[]> rows = getRawData(classPathToStatsFile, emgFile, "_summary.go_slim", ',');

        if (rows != null) {
            for (String[] row : rows) {
                if (row.length == 4) {
                    String ontology = row[2];
                    if (ontology != null && ontology.trim().length() > 0) {
                        String accession = row[0];
                        String synonym = row[1];
                        int numberOfMatches = Integer.parseInt(row[3]);
                        if (ontology.equals("biological_process")) {
                            BiologicalProcessGOTerm instance = new BiologicalProcessGOTerm(accession,
                                    synonym, numberOfMatches);
                            result.get(BiologicalProcessGOTerm.class).add(instance);
                        } else if (ontology.equals("cellular_component")) {
                            CellularComponentGOTerm instance = new CellularComponentGOTerm(accession,
                                    synonym, numberOfMatches);
                            result.get(CellularComponentGOTerm.class).add(instance);
                        } else {
                            MolecularFunctionGOTerm instance = new MolecularFunctionGOTerm(accession,
                                    synonym, numberOfMatches);
                            result.get(MolecularFunctionGOTerm.class).add(instance);
                        }
                    }
                } else {
                    log.warn("Row size is not the expected one.");
                }
            }
        } else {
            log.warn("Didn't get any data from GO term file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return result;
    }


    private static String getBarChartURL(String classPathToStatsFile, EmgFile emgFile) {
        List<Integer> chartData = loadStatsFromCSV(classPathToStatsFile, emgFile);
        if (chartData != null && chartData.size() > 0) {
            Properties props = new Properties();
            props.put(GoogleChartFactory.CHART_TYPE, "bhs");
            props.put(GoogleChartFactory.CHART_SIZE, "450x200");
            props.put(GoogleChartFactory.CHART_AXES, "y");
            props.put(GoogleChartFactory.CHART_LEGEND_TEXT, "total number of submitted reads|total number of processed reads|reads with at least 1 pCDS|reads with at least 1 InterPro match");
            props.put(GoogleChartFactory.CHART_COLOUR, "FF0000|00FF00|0000FF|FFFF10");
            props.put(GoogleChartFactory.CHART_MARKER, "N,000000,0,-1,11");
            props.put(GoogleChartFactory.CHART_DATA_SCALE, "0," + chartData.get(0));
            return GoogleChartFactory.buildChartURL(props, chartData);
        }
        return null;
    }

    private static List<Integer> loadStatsFromCSV(String classPathToStatsFile, EmgFile emgFile) {
        List<Integer> result = new ArrayList<Integer>();

        log.info("Processing summary file...");
        List<String[]> rows = getRawData(classPathToStatsFile, emgFile, "_summary", ',');

        if (rows != null && rows.size() >= 6) {
            int numSubmittedSeqs = getValueOfRow(rows, 0);
            int numSeqsOfProcessedSeqs = getValueOfRow(rows, 3);
            int numSeqsWithPredicatedCDS = getValueOfRow(rows, 4);
            int numSeqsWithInterProScanMatch = getValueOfRow(rows, 5);
            result.add(numSubmittedSeqs);
            result.add(numSeqsOfProcessedSeqs);
            result.add(numSeqsWithPredicatedCDS);
            result.add(numSeqsWithInterProScanMatch);
        } else {
            log.warn("Didn't get any data from summary file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return result;
    }

    private static int getValueOfRow(List<String[]> rows, int i) {
        String[] row = rows.get(i);
        if (row.length > 1) {
            return Integer.parseInt(row[1]);
        }
        return -1;
    }

    private static List<InterProEntry> getListOfInterProEntries(String pathToAnalysisDirectory, EmgFile emgFile, boolean isReturnSizeLimit) {
        return loadInterProMatchesFromCSV(pathToAnalysisDirectory, emgFile, isReturnSizeLimit);
    }


    /**
     * Loads InterPro matches from the Python pipeline result file with file extension '_summary.ipr'.
     * Please notice that the size of the returned list could be limited to 5 items.
     * TODO: Size limitation is a temporary solution
     *
     * @param classPathToStatsFile
     * @param emgFile
     * @param isReturnSizeLimit    Specifies if the size of the returned list is limited to 5.
     * @return
     */
    private static List<InterProEntry> loadInterProMatchesFromCSV(String classPathToStatsFile, EmgFile emgFile, boolean isReturnSizeLimit) {
        List<InterProEntry> result = new ArrayList<InterProEntry>();
        log.info("Processing interpro result summary file...");
        List<String[]> rows = getRawData(classPathToStatsFile, emgFile, "_summary.ipr", ',');

        if (rows != null) {
            //return size limitation the
            if (isReturnSizeLimit) {
                rows = rows.subList(0, 5);
            }
            for (String[] row : rows) {
                if (row.length == 3) {
                    String entryID = row[0];
                    String entryDesc = row[1];
                    int numOfEntryHits = Integer.parseInt(row[2]);
                    if (entryID != null && entryID.trim().length() > 0) {
                        result.add(new InterProEntry(entryID, entryDesc, numOfEntryHits));
                    }
                } else {
                    log.warn("Row size is not the expected one.");
                }
            }
        } else {
            log.warn("Didn't get any data from interpro result summary file. There might be some fundamental change to this file" +
                    "(maybe in the near past), which affects this parsing process!");
        }
        return result;
    }

    /**
     * Reads raw data from the specified file by using a CSV reader. Possible to specify different delimiters.
     *
     * @param classPathToStatsFile
     * @param emgFile
     * @return
     */
    private static List<String[]> getRawData(String classPathToStatsFile, EmgFile emgFile, String fileExtension, char delimiter) {
        List<String[]> rows = null;

        String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
        File file = new File(classPathToStatsFile + directoryName + '/' + directoryName + fileExtension);
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(file), delimiter);
        } catch (FileNotFoundException e) {
            log.warn("Could not find the following file " + file.getAbsolutePath(), e);
        }
        if (reader != null) {
            try {
                rows = reader.readAll();
            } catch (IOException e) {
                log.warn("Could not get rows from CSV file!", e);
            }
        }
        return rows;
    }
}