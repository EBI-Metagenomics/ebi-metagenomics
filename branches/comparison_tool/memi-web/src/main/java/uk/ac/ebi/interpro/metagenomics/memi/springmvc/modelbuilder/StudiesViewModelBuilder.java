package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.StudiesViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewPagination;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Model builder class for StudiesViewModel. See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class StudiesViewModelBuilder extends AbstractViewModelBuilder<StudiesViewModel> {

    private final static Log log = LogFactory.getLog(StudiesViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private List<String> tableHeaderNames;

    private SampleDAO sampleDAO;

    private StudyDAO studyDAO;

    private StudyFilter filter;

    private int startPosition;

    private final static int PAGE_SIZE = 10;

    private boolean doPagination;

    public StudiesViewModelBuilder(final SessionManager sessionMgr, final String pageTitle, final List<Breadcrumb> breadcrumbs,
                                   final MemiPropertyContainer propertyContainer, final List<String> tableHeaderNames,
                                   final SampleDAO sampleDAO,
                                   final StudyDAO studyDAO,
                                   final StudyFilter filter,
                                   final int startPosition,
                                   final boolean doPagination) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.tableHeaderNames = tableHeaderNames;
        this.sampleDAO = sampleDAO;
        this.studyDAO = studyDAO;
        this.filter = filter;
        this.startPosition = startPosition;
        this.doPagination = doPagination;
    }

    public StudiesViewModel getModel() {
        log.info("Building instance of " + StudiesViewModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        String submissionAccountId = (submitter != null ? submitter.getSubmissionAccountId() : null);

        //Get filtered studies
        List<Criterion> filterCriteria = buildFilterCriteria(filter, submissionAccountId);
        List<Study> filteredStudies = getFilteredStudies(filterCriteria, doPagination);
        long filteredStudiesCount = studyDAO.countByCriteria(filterCriteria);
        ViewPagination pagination = new ViewPagination(startPosition, filteredStudiesCount, PAGE_SIZE);

        //studies are sorted by study name at the moment
//        Map<Study, Long> sortedStudyMap = getStudySampleSizeMap(filteredStudies, sampleDAO, new ViewStudiesComparator());

        attachSampleSize(filteredStudies);
        return new StudiesViewModel(submitter, filteredStudies, null, pageTitle, breadcrumbs, propertyContainer, tableHeaderNames, pagination, filter);
    }

    private void attachSampleSize(List<Study> filteredStudies) {
        for (Study study : filteredStudies) {
            if (sampleDAO != null) {
                long sampleSize = sampleDAO.retrieveSampleSizeByStudyId(study.getId());
                study.setSampleSize(new Long(sampleSize));
            }
        }
    }

    private List<Study> getFilteredStudies(final List<Criterion> criteria,
                                           final boolean doPagination) {
        List<Study> result;
        if (doPagination) {
            result = studyDAO.retrieveFilteredStudies(criteria, startPosition, PAGE_SIZE, false, "studyName");
        } else {
            result = studyDAO.retrieveFilteredStudies(criteria, false, "studyName");
        }
        return (result == null ? new ArrayList<Study>() : result);
    }

    /**
     * Builds a list of criteria for the specified study filter. These criteria can be used for
     * a Hibernate query.
     */
    private static List<Criterion> buildFilterCriteria(final StudyFilter filter, final String submissionAccountId) {
        String searchText = filter.getSearchTerm();
        Study.StudyStatus studyStatus = filter.getStudyStatus();

        List<Criterion> crits = new ArrayList<Criterion>();
        //add search term criterion
        if (searchText != null && searchText.trim().length() > 0) {
            crits.add(Restrictions.or(Restrictions.ilike("studyId", searchText, MatchMode.ANYWHERE), Restrictions.ilike("studyName", searchText, MatchMode.ANYWHERE)));
        }
        //add study status criterion
        if (studyStatus != null) {
            crits.add(Restrictions.eq("studyStatus", studyStatus));
        }
        //add is public and submitter identifier criteria
        if (submissionAccountId !=null) {
            //Set DEFAULT visibility if not defined
            StudyFilter.StudyVisibility visibility = (filter.getStudyVisibility() == null ? StudyFilter.StudyVisibility.MY_PROJECTS: filter.getStudyVisibility());
            //SELECT * FROM HB_STUDY where submitter_id=?;
            if (visibility.equals(StudyFilter.StudyVisibility.MY_PROJECTS)) {
                crits.add(Restrictions.eq("submissionAccountId", submissionAccountId));
            }
            //select * from hb_study where submitter_id=? and is_public=1;
            else if (visibility.equals(StudyFilter.StudyVisibility.MY_PUBLISHED_PROJECTS)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", true), Restrictions.eq("submissionAccountId", submissionAccountId)));
            }
            //select * from hb_study where submitter_id=? and is_public=0;
            else if (visibility.equals(StudyFilter.StudyVisibility.MY_PREPUBLISHED_PROJECTS)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submissionAccountId", submissionAccountId)));
            }
            //select * from hb_study where is_public=1;
            else if (visibility.equals(StudyFilter.StudyVisibility.ALL_PUBLISHED_PROJECTS)) {
                crits.add(Restrictions.eq("isPublic", true));
            }
            //select * from hb_study where is_public=1 or submitter_id=? and is_public=0;
            else if (visibility.equals(StudyFilter.StudyVisibility.ALL_PROJECTS)) {
                crits.add(Restrictions.or(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submissionAccountId", submissionAccountId)), Restrictions.eq("isPublic", true)));
            }
        } else {
            crits.add(Restrictions.eq("isPublic", true));
        }

        return crits;
    }
}