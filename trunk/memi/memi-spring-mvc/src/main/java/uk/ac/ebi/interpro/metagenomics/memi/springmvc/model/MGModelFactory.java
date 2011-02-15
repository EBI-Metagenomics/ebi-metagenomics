package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.NewsDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.News;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Metagenomics model factory. Use this factory if you want to create a {@link MGModel}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MGModelFactory {
    //Final variables
    /**
     * The number studies, which should be shown on the portal home page.
     */
    private final static int maxRowNumber = 5;

    private MGModelFactory() {

    }

    public static HomePageModel getHomePageModel(SessionManager sessionMgr, HibernateStudyDAO studyDAO, HibernateSampleDAO sampleDAO, NewsDAO newsDAO) {
        Submitter submitter = getSessionSubmitter(sessionMgr);
        if (submitter == null) {
            return new HomePageModel(submitter, getOrderedPublicStudies(studyDAO), getOrderedPublicSamples(sampleDAO), getNewsListFromDB(newsDAO));
        } else {
            List<Study> myStudies = getOrderedStudiesBySubmitter(submitter.getSubmitterId(), studyDAO);
            List<Sample> mySamples = getOrderedSamplesBySubmitter(submitter.getSubmitterId(), sampleDAO);
            List<Study> publicStudies = getOrderedPublicStudiesWithoutSubId(submitter.getSubmitterId(), studyDAO);
            List<Sample> publicSamples = getOrderedPublicSamplesWithoutSubId(submitter.getSubmitterId(), sampleDAO);
            return new HomePageModel(submitter, publicStudies, publicSamples, getNewsListFromDB(newsDAO), myStudies, mySamples);
        }
    }

    public static MGModel getMGModel(SessionManager sessionMgr) {
        return new MGModel(getSessionSubmitter(sessionMgr));
    }

    public static SubmissionModel getSubmissionModel(SessionManager sessionMgr) {
        return new SubmissionModel(getSessionSubmitter(sessionMgr));
    }

    public static ViewStudiesModel getViewStudiesPageModel(SessionManager sessionMgr, HibernateStudyDAO studyDAO, StudyFilter filter) {
        Submitter submitter = getSessionSubmitter(sessionMgr);
        long submitterId = (submitter != null ? submitter.getSubmitterId() : -1L);
        return new ViewStudiesModel(submitter, getFilteredStudies(studyDAO, filter, submitterId));
    }

    public static ViewSamplesModel getViewSamplesPageModel(SessionManager sessionMgr, HibernateSampleDAO sampleDAO, SampleFilter filter) {
        Submitter submitter = getSessionSubmitter(sessionMgr);
        long submitterId = (submitter != null ? submitter.getSubmitterId() : -1L);
        return new ViewSamplesModel(submitter, getFilteredSamples(sampleDAO, filter, submitterId));
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

    private static List<Sample> getFilteredSamples(HibernateSampleDAO sampleDAO, SampleFilter filter, long submitterId) {
        List<Sample> result = sampleDAO.retrieveFilteredSamples(buildFilterCriteria(filter, submitterId));
        if (result == null) {
            result = new ArrayList<Sample>();
        }
        return result;
    }

    /**
     * Builds a list of criteria for the specified study filter. These criteria can be used for
     * a Hibernate query.
     */
    private static List<Criterion> buildFilterCriteria(StudyFilter filter, long submitterId) {
        String searchText = filter.getSearchTerm();
        Study.StudyType type = filter.getStudyType();
        Study.StudyStatus studyStatus = filter.getStudyStatus();
        StudyFilter.StudyVisibility visibility = filter.getStudyVisibility();

        List<Criterion> crits = new ArrayList<Criterion>();
        //add search term criterion
        if (searchText != null && searchText.trim().length() > 0) {
            crits.add(Restrictions.or(Restrictions.like("studyId", searchText, MatchMode.ANYWHERE), Restrictions.like("studyName", searchText, MatchMode.ANYWHERE)));
        }
        //add study type criterion
        if (type != null) {
            crits.add(Restrictions.eq("studyType", type));
        }
        //add study status criterion
        if (studyStatus != null) {
            crits.add(Restrictions.eq("studyStatus", studyStatus));
        }
        //add is public criterion
        if (submitterId > -1) {
            //SELECT * FROM HB_STUDY where submitter_id=?;
            if (visibility.equals(StudyFilter.StudyVisibility.MY_STUDIES)) {
                crits.add(Restrictions.eq("submitterId", submitterId));
            }
            //select * from hb_study where submitter_id=? and is_public=1;
            else if (visibility.equals(StudyFilter.StudyVisibility.MY_PUBLISHED_STUDIES)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", true), Restrictions.eq("submitterId", submitterId)));
            }
            //select * from hb_study where submitter_id=? and is_public=0;
            else if (visibility.equals(StudyFilter.StudyVisibility.MY_PREPUBLISHED_STUDIES)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submitterId", submitterId)));
            }
            //select * from hb_study where is_public=1;
            else if (visibility.equals(StudyFilter.StudyVisibility.ALL_PUBLISHED_STUDIES)) {
                crits.add(Restrictions.eq("isPublic", true));
            }
            //select * from hb_study where is_public=1 or submitter_id=? and is_public=0;
            else if (visibility.equals(StudyFilter.StudyVisibility.ALL_STUDIES)) {
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


    public static List<News> getNewsListFromDB(NewsDAO newsDAO) {
        List<News> newsList = newsDAO.getLatestNews();
        if (newsList == null) {
            newsList = new ArrayList<News>();
        }
        return newsList;
    }

    private static Submitter getSessionSubmitter(SessionManager sessionMgr) {
        return sessionMgr.getSessionBean().getSubmitter();
    }
}