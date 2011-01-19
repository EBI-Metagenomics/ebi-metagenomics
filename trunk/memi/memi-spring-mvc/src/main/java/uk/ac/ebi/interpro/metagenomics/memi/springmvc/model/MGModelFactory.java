package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import org.springframework.web.bind.annotation.ModelAttribute;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.NewsDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.model.News;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

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
    private final static int rowNumberForStudies = 10;

    private MGModelFactory() {

    }

    public static HomePageModel getHomePageModel(SessionManager sessionMgr, EmgStudyDAO emgStudyDAO, NewsDAO newsDAO) {
        return new HomePageModel(getSessionSubmitter(sessionMgr), getLimitedPublicStudiesFromDB(emgStudyDAO), getNewsListFromDB(newsDAO));
    }

    public static MGModel getMGModel(SessionManager sessionMgr, EmgStudyDAO emgStudyDAO) {
        return new MGModel(getSessionSubmitter(sessionMgr), getLimitedPublicStudiesFromDB(emgStudyDAO));
    }

    public static SubmissionModel getSubmissionModel(SessionManager sessionMgr, EmgStudyDAO emgStudyDAO) {
        return new SubmissionModel(getSessionSubmitter(sessionMgr), getLimitedPublicStudiesFromDB(emgStudyDAO));
    }

    public static ListStudiesPageModel getListStudiesPageModel(SessionManager sessionMgr, EmgStudyDAO emgStudyDAO) {
        return new ListStudiesPageModel(getSessionSubmitter(sessionMgr), getAllPublicStudiesFromDB(emgStudyDAO));
    }

    /**
     * Returns a list of public studies limited by a specified number of rows.
     */
    public static List<EmgStudy> getLimitedPublicStudiesFromDB(EmgStudyDAO emgStudyDAO) {
        List<EmgStudy> studies = null;
        if (emgStudyDAO != null) {
            studies = emgStudyDAO.retrieveStudiesLimitedByRows(rowNumberForStudies);
        }
        if (studies == null) {
            studies = new ArrayList<EmgStudy>();
        }
        return studies;
    }

    /**
     * Returns a list of all public studies with the MG database.
     */
    public static List<EmgStudy> getAllPublicStudiesFromDB(EmgStudyDAO emgStudyDAO) {
        List<EmgStudy> result = emgStudyDAO.retrieveAll();
        if (result == null) {
            result = new ArrayList<EmgStudy>();
        }
        return result;
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