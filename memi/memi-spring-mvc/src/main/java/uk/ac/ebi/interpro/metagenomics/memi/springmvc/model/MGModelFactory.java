package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.NewsDAO;
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

    public static ListStudiesModel getListStudiesPageModel(SessionManager sessionMgr, HibernateStudyDAO studyDAO) {
        return new ListStudiesModel(getSessionSubmitter(sessionMgr), getAllPublicStudiesFromDB(studyDAO));
    }

    public static ListStudiesModel getListStudiesPageModel(SessionManager sessionMgr, HibernateStudyDAO studyDAO, StudyFilter filter) {
        return new ListStudiesModel(getSessionSubmitter(sessionMgr), getFilteredStudiesFrom(studyDAO, filter));
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


    /**
     * Returns a list of all public studies with the MG database.
     */
    public static List<Study> getAllPublicStudiesFromDB(HibernateStudyDAO studyDAO) {
        List<Study> result = studyDAO.retrieveAll();
        if (result == null) {
            result = new ArrayList<Study>();
        }
        return result;
    }

    public static List<Study> getFilteredStudiesFrom(HibernateStudyDAO studyDAO, StudyFilter filter) {
        List<Study> result = studyDAO.retrieveFilteredStudies(buildFilterCriteria(filter));
        if (result == null) {
            result = new ArrayList<Study>();
        }
        return result;
    }

    private static List<Criterion> buildFilterCriteria(StudyFilter filter) {
        String searchText = filter.getSearchTerm();
        Study.StudyType type = filter.getStudyType();
        Study.StudyStatus studyStatus = filter.getStudyStatus();
        StudyFilter.StudyVisibility visibility = filter.getStudyVisibility();

        List<Criterion> crits = new ArrayList<Criterion>();
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
        if (!visibility.equals(StudyFilter.StudyVisibility.ALL)) {
            if (visibility.equals(StudyFilter.StudyVisibility.PRIVATE)) {
                crits.add(Restrictions.eq("isPublic", false));
            } else if (visibility.equals(StudyFilter.StudyVisibility.PUBLIC)) {
                crits.add(Restrictions.eq("isPublic", true));
            }
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