package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import au.com.bytecode.opencsv.CSVReader;
import com.sun.syndication.feed.synd.SyndEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
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

import java.io.*;
import java.util.*;

/**
 * Represents a Metagenomics model factory. Use this factory if you want to create a {@link MGModel}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MGModelFactory {
    private final static Log log = LogFactory.getLog(MGModelFactory.class);

    //Final variables
    /**
     * The number studies, which should be shown on the portal home page.
     */
    private final static int maxRowNumber = 4;

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
        } catch (Exception e) {
            log.warn("Could not get RSS entries", e);
        }
        if (submitter == null) {
            List<Study> studies = getOrderedPublicStudies(studyDAO);
            SortedMap<Study, Long> publicStudiesMap = getStudySampleSizeMap(studies, sampleDAO);
//            TODO: Check the order of the studies (should be solved in Java not with Hibernate)
            return new HomePageModel(submitter, publicStudiesMap, getOrderedPublicSamples(sampleDAO),
                    rssUrl, rssEntries, pageTitle, breadcrumbs, propertyContainer);
        } else {
            List<Study> myStudies = getOrderedStudiesBySubmitter(submitter.getSubmitterId(), studyDAO);
            SortedMap<Study, Long> myStudiesMap = getStudySampleSizeMap(myStudies, sampleDAO);
            List<Sample> mySamples = getOrderedSamplesBySubmitter(submitter.getSubmitterId(), sampleDAO);
            List<Study> publicStudies = getOrderedPublicStudiesWithoutSubId(submitter.getSubmitterId(), studyDAO);
            SortedMap<Study, Long> publicStudiesMap = getStudySampleSizeMap(publicStudies, sampleDAO);
            List<Sample> publicSamples = getOrderedPublicSamplesWithoutSubId(submitter.getSubmitterId(), sampleDAO);
            return new HomePageModel(submitter, publicStudiesMap, publicSamples, rssUrl, rssEntries,
                    myStudiesMap, mySamples, pageTitle, breadcrumbs, propertyContainer);
        }
    }

    public static MGModel getMGModel(SessionManager sessionMgr) {
        return getMGModel(sessionMgr, "EBI Metagenomics Portal", new ArrayList<Breadcrumb>(), null);
    }

    public static MGModel getMGModel(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs,
                                     MemiPropertyContainer propertyContainer) {
        log.info("Building instance of " + MGModel.class + "...");
        return new MGModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer);
    }


    public static SubmissionModel getSubmissionModel(SessionManager sessionMgr, String pageTitle,
                                                     List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer) {
        log.info("Building instance of " + SubmissionModel.class + "...");
        return new SubmissionModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer);
    }

    public static AnalysisStatsModel getAnalysisStatsModel(SessionManager sessionManager, Sample sample,
                                                           String pageTitle, List<Breadcrumb> breadcrumbs,
                                                           EmgFile emgFile, List<String> archivedSequences,
                                                           MemiPropertyContainer propertyContainer) {
        log.info("Building instance of " + AnalysisStatsModel.class + "...");
        Map<Class, List<AbstractGOTerm>> goData = loadGODataFromCSV(propertyContainer.getPathToAnalysisDirectory(),
                emgFile);
        return new AnalysisStatsModel(getSessionSubmitter(sessionManager), pageTitle, breadcrumbs, sample,
                getBarChartURL(propertyContainer.getPathToAnalysisDirectory(), emgFile),
                getPieChartURL(BiologicalProcessGOTerm.class, goData),
                getPieChartURL(CellularComponentGOTerm.class, goData),
                getPieChartURL(MolecularFunctionGOTerm.class, goData),
                null,
                goData.get(BiologicalProcessGOTerm.class), emgFile, archivedSequences, propertyContainer);
    }

    public static StudyViewModel getStudyViewModel(SessionManager sessionManager, Study study,
                                                   List<Sample> samples, String pageTitle,
                                                   List<Breadcrumb> breadcrumbs,
                                                   MemiPropertyContainer propertyContainer) {
        log.info("Building instance of " + StudyViewModel.class + "...");
        return new StudyViewModel(getSessionSubmitter(sessionManager), study, samples, pageTitle,
                breadcrumbs, propertyContainer);
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
                                                           MemiPropertyContainer propertyContainer) {
        log.info("Building instance of " + ViewStudiesModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        long submitterId = (submitter != null ? submitter.getSubmitterId() : -1L);
        List<Study> studies = getFilteredStudies(studyDAO, filter, submitterId);
        return new ViewStudiesModel(submitter, getStudySampleSizeMap(studies, sampleDAO), pageTitle,
                breadcrumbs, propertyContainer);
    }

    public static ViewSamplesModel getViewSamplesPageModel(SessionManager sessionMgr, HibernateSampleDAO sampleDAO,
                                                           SampleFilter filter, String pageTitle,
                                                           List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer) {
        log.info("Building instance of " + ViewSamplesModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        long submitterId = (submitter != null ? submitter.getSubmitterId() : -1L);
        return new ViewSamplesModel(submitter, getFilteredSamples(sampleDAO, filter, submitterId),
                pageTitle, breadcrumbs, propertyContainer);
    }

    public static ContactModel getContactModel(SessionManager sessionMgr, String pageTitle,
                                               List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer) {
        return new ContactModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer);
    }


    /**
     * Returns a list of public studies limited by a specified number of rows and order by received date.
     */
    private static List<Study> getOrderedPublicStudies(HibernateStudyDAO studyDAO) {
        List<Study> studies = null;
        if (studyDAO != null) {
            studies = studyDAO.retrieveOrderedPublicStudies("lastMetadataReceived", true);
        }
        if (studies == null) {
            studies = new ArrayList<Study>();
        } else {
            if (studies.size() >= maxRowNumber) {
                studies = studies.subList(0, maxRowNumber);
            }
        }
        return studies;
    }

    /**
     * Returns a list of studies for the specified submitter, limited by a specified number of rows and order by received date.
     */
    private static List<Study> getOrderedStudiesBySubmitter(long submitterId, HibernateStudyDAO studyDAO) {
        List<Study> studies = null;
        if (studyDAO != null) {
            studies = studyDAO.retrieveOrderedStudiesBySubmitter(submitterId, "lastMetadataReceived", true);
        }
        if (studies == null) {
            studies = new ArrayList<Study>();
        } else {
            if (studies.size() >= maxRowNumber) {
                studies = studies.subList(0, maxRowNumber);
            }
        }
        return studies;
    }

    private static List<Study> getOrderedPublicStudiesWithoutSubId(long submitterId, HibernateStudyDAO studyDAO) {
        List<Study> studies = null;
        if (studyDAO != null) {
            studies = studyDAO.retrieveOrderedPublicStudiesWithoutSubId(submitterId, "lastMetadataReceived", true);
        }
        if (studies == null) {
            studies = new ArrayList<Study>();
        } else {
            if (studies.size() >= maxRowNumber) {
                studies = studies.subList(0, maxRowNumber);
            }
        }
        return studies;
    }

    /**
     * Returns a list of public sample limited by a specified number of rows and order by received date.
     */
    public static List<Sample> getOrderedPublicSamples(HibernateSampleDAO sampleDAO) {
        List<Sample> samples = null;
        if (sampleDAO != null) {
            samples = sampleDAO.retrieveOrderedPublicSamples("metadataReceived", true);
        }
        if (samples == null) {
            samples = new ArrayList<Sample>();
        } else {
            if (samples.size() >= maxRowNumber) {
                samples = samples.subList(0, maxRowNumber);
            }
        }
        return samples;
    }


    /**
     * Returns a list of studies for the specified submitter, limited by a specified number of rows and order by received date.
     */
    private static List<Sample> getOrderedSamplesBySubmitter(long submitterId, HibernateSampleDAO sampleDAO) {
        List<Sample> samples = null;
        if (sampleDAO != null) {
            samples = sampleDAO.retrieveOrderedSamplesBySubmitter(submitterId, "metadataReceived", true);
        }
        if (samples == null) {
            samples = new ArrayList<Sample>();
        } else {
            if (samples.size() >= maxRowNumber) {
                samples = samples.subList(0, maxRowNumber);
            }
        }
        return samples;
    }

    private static List<Sample> getOrderedPublicSamplesWithoutSubId(long submitterId, HibernateSampleDAO sampleDAO) {
        List<Sample> samples = null;
        if (sampleDAO != null) {
            samples = sampleDAO.retrieveOrderedPublicSamplesWithoutSubId(submitterId, "metadataReceived", true);
        }
        if (samples == null) {
            samples = new ArrayList<Sample>();
        } else {
            if (samples.size() >= maxRowNumber) {
                samples = samples.subList(0, maxRowNumber);
            }
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

    private static SortedMap<Study, Long> getStudySampleSizeMap(List<Study> studies, HibernateSampleDAO sampleDAO) {
        SortedMap<Study, Long> result = new TreeMap<Study, Long>(new StudyComparator());
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

    static class StudyComparator implements Comparator<Study> {

        @Override
        public int compare(Study o1, Study o2) {
            Date lastUpdateO1 = o1.getLastMetadataReceived();
            Date lastUpdateO2 = o2.getLastMetadataReceived();
            if (lastUpdateO1 != null && lastUpdateO2 != null)
                return -(lastUpdateO1.compareTo(lastUpdateO2));
            return 0;
        }
    }

    private static String getPieChartURL(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {

        List<Float> data = getGOData(clazz, goData);
        List<String> labels = getGOLabels(clazz, goData);
        Properties props = new Properties();
        props.put(GoogleChartFactory.CHART_MARGIN, "270,270");
        props.put(GoogleChartFactory.CHART_SIZE, "740x180");
        if (clazz.equals(BiologicalProcessGOTerm.class)) {
            props.put(GoogleChartFactory.CHART_COLOUR, "FFFF10,FF0000");
        } else if (clazz.equals(CellularComponentGOTerm.class)) {
            props.put(GoogleChartFactory.CHART_COLOUR, "FFFF10,00FF00");
        } else {
            props.put(GoogleChartFactory.CHART_COLOUR, "FFFF10,0000FF");
        }

        return GoogleChartFactory.buildPieChartURL(props, data, labels);
    }


    private static List<Float> getGOData(Class clazz, Map<Class, List<AbstractGOTerm>> goData) {
        List<Float> result = new ArrayList<Float>();
        List<AbstractGOTerm> goTerms = null;
        if (clazz.equals(BiologicalProcessGOTerm.class)) {
            goTerms = goData.get(BiologicalProcessGOTerm.class);
        } else if (clazz.equals(CellularComponentGOTerm.class)) {
            goTerms = goData.get(CellularComponentGOTerm.class);
        } else {
            goTerms = goData.get(MolecularFunctionGOTerm.class);
        }
        if (goTerms != null) {
            float totalNumberOfMatches = 0;
            //the first iteration is to get the total number of GO term matches
            for (AbstractGOTerm term : goTerms) {
                totalNumberOfMatches += (float) term.getNumberOfMatches();
            }
            //the second iteration is to calculate the pie chart data
            for (AbstractGOTerm term : goTerms) {
                result.add(((float) term.getNumberOfMatches()) / totalNumberOfMatches);

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

        String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
        File file = new File(classPathToStatsFile + directoryName + '/' + directoryName + "_summary.go_slim");
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(file), '\t');
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (reader != null) {
            List<String[]> rows = null;
            try {
                rows = reader.readAll();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if (rows != null) {
                for (String[] row : rows) {
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

                }
            }
        }
        return result;
    }


    private static String getBarChartURL(String classPathToStatsFile, EmgFile emgFile) {
        List<Float> chartData = loadStatsFromCSV(classPathToStatsFile, emgFile);
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

    private static List<Float> loadStatsFromCSV(String classPathToStatsFile, EmgFile emgFile) {
        List<Float> result = new ArrayList<Float>();
        String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
        File file = new File(classPathToStatsFile + directoryName + '/' + directoryName + "_summary");
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(file), '\t');
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (reader != null) {
            List<String[]> rows = null;
            try {
                rows = reader.readAll();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if (rows != null) {
                float numSubmittedSeqs = getValueOfRow(rows, 0);
                float numSeqsOfProcessedSeqs = getValueOfRow(rows, 3);
                float numSeqsWithPredicatedCDS = getValueOfRow(rows, 4);
                float numSeqsWithInterProScanMatch = getValueOfRow(rows, 5);
                result.add(numSubmittedSeqs);
                result.add(numSeqsOfProcessedSeqs);
                result.add(numSeqsWithPredicatedCDS);
                result.add(numSeqsWithInterProScanMatch);
            }
        }
        return result;
    }

    private static float getValueOfRow(List<String[]> rows, int i) {
        String[] row = rows.get(i);
        if (row.length > 1) {
            return Float.parseFloat(row[1]);
        }
        return -1f;
    }
}


