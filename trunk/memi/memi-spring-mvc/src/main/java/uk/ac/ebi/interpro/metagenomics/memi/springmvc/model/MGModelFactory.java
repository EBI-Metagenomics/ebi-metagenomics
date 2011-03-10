package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.feed.RomeClient;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.IOException;
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

    public static HomePageModel getHomePageModel(SessionManager sessionMgr, HibernateStudyDAO studyDAO, HibernateSampleDAO sampleDAO, RomeClient romeClient, List<Breadcrumb> breadcrumbs) {
        log.info("Building instance of " + HomePageModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        String rssUrl = null;
        try {
            rssUrl = romeClient.getFeed().getURL().toString();
        } catch (IOException e) {
            rssUrl = "unknown"; // TODO: Put URL default here
            log.warn("Could not get feed URL from Rome client!", e);
        }
        if (submitter == null) {
            List<Study> studies = getOrderedPublicStudies(studyDAO);
            SortedMap<Study, Long> publicStudiesMap = getStudySampleSizeMap(studies, sampleDAO);
//            TODO: Check the order of the studies (should be solved in Java not with Hibernate)
            return new HomePageModel(submitter, publicStudiesMap, getOrderedPublicSamples(sampleDAO), rssUrl, romeClient.getEntries(), breadcrumbs);
        } else {
            List<Study> myStudies = getOrderedStudiesBySubmitter(submitter.getSubmitterId(), studyDAO);
            SortedMap<Study, Long> myStudiesMap = getStudySampleSizeMap(myStudies, sampleDAO);
            List<Sample> mySamples = getOrderedSamplesBySubmitter(submitter.getSubmitterId(), sampleDAO);
            List<Study> publicStudies = getOrderedPublicStudiesWithoutSubId(submitter.getSubmitterId(), studyDAO);
            SortedMap<Study, Long> publicStudiesMap = getStudySampleSizeMap(publicStudies, sampleDAO);
            List<Sample> publicSamples = getOrderedPublicSamplesWithoutSubId(submitter.getSubmitterId(), sampleDAO);
            return new HomePageModel(submitter, publicStudiesMap, publicSamples, rssUrl, romeClient.getEntries(), myStudiesMap, mySamples, breadcrumbs);
        }
    }

    public static MGModel getMGModel(SessionManager sessionMgr) {
        return getMGModel(sessionMgr, new ArrayList<Breadcrumb>());
    }

    public static MGModel getMGModel(SessionManager sessionMgr, List<Breadcrumb> breadcrumbs) {
        log.info("Building instance of " + MGModel.class + "...");
        return new MGModel(getSessionSubmitter(sessionMgr), breadcrumbs);
    }



    public static SubmissionModel getSubmissionModel(SessionManager sessionMgr, List<Breadcrumb> breadcrumbs) {
        log.info("Building instance of " + SubmissionModel.class + "...");
        return new SubmissionModel(getSessionSubmitter(sessionMgr), breadcrumbs);
    }

    public static AnalysisStatsModel getAnalysisStatsModel(SessionManager sessionManager, Sample sample, String classPathToStatsFile, List<Breadcrumb> breadcrumbs) {
        log.info("Building instance of " + AnalysisStatsModel.class + "...");
        return new AnalysisStatsModel(getSessionSubmitter(sessionManager), sample, classPathToStatsFile, breadcrumbs);
    }

    public static StudyViewModel getStudyViewModel(SessionManager sessionManager, Study study, List<Sample> samples, List<Breadcrumb> breadcrumbs) {
        log.info("Building instance of " + StudyViewModel.class + "...");
        return new StudyViewModel(getSessionSubmitter(sessionManager), study, samples, breadcrumbs);
    }

    public static SampleViewModel getSampleViewModel(SessionManager sessionManager, Sample sample, List<String> archivedSeqs, List<Breadcrumb> breadcrumbs) {
        log.info("Building instance of " + SampleViewModel.class + "...");
        return new SampleViewModel(getSessionSubmitter(sessionManager), sample, archivedSeqs, breadcrumbs);
    }

    public static ViewStudiesModel getViewStudiesPageModel(SessionManager sessionMgr, HibernateStudyDAO studyDAO, HibernateSampleDAO sampleDAO, StudyFilter filter, List<Breadcrumb> breadcrumbs) {
        log.info("Building instance of " + ViewStudiesModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        long submitterId = (submitter != null ? submitter.getSubmitterId() : -1L);
        List<Study> studies = getFilteredStudies(studyDAO, filter, submitterId);
        return new ViewStudiesModel(submitter, getStudySampleSizeMap(studies, sampleDAO), breadcrumbs);
    }

    public static ViewSamplesModel getViewSamplesPageModel(SessionManager sessionMgr, HibernateSampleDAO sampleDAO, SampleFilter filter, List<Breadcrumb> breadcrumbs) {
        log.info("Building instance of " + ViewSamplesModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        long submitterId = (submitter != null ? submitter.getSubmitterId() : -1L);
        return new ViewSamplesModel(submitter, getFilteredSamples(sampleDAO, filter, submitterId), breadcrumbs);
    }

    public static ContactModel getContactModel(SessionManager sessionMgr, List<Breadcrumb> breadcrumbs) {
        return new ContactModel(getSessionSubmitter(sessionMgr), breadcrumbs);
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
            crits.add(Restrictions.or(Restrictions.like("studyId", searchText, MatchMode.ANYWHERE), Restrictions.like("studyName", searchText, MatchMode.ANYWHERE)));
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
            crits.add(Restrictions.or(Restrictions.like("sampleId", searchText, MatchMode.ANYWHERE), Restrictions.like("sampleTitle", searchText, MatchMode.ANYWHERE)));
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
}


