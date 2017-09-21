package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.StudiesViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewPagination;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Model builder class for StudiesViewModel. See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class StudiesViewModelBuilder extends AbstractBiomeViewModelBuilder<StudiesViewModel> {

    private final static Log log = LogFactory.getLog(StudiesViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private List<String> tableHeaderNames;

    private SampleDAO sampleDAO;

    private StudyDAO studyDAO;

    private BiomeDAO biomeDAO;

    private StudyFilter filter;

    private int startPosition;

    private final static int PAGE_SIZE = 10;

    private boolean doPagination;

    public StudiesViewModelBuilder(final UserManager sessionMgr, final EBISearchForm ebiSearchForm,
                                   final String pageTitle, final List<Breadcrumb> breadcrumbs,
                                   final MemiPropertyContainer propertyContainer, final List<String> tableHeaderNames,
                                   final SampleDAO sampleDAO,
                                   final StudyDAO studyDAO,
                                   final BiomeDAO biomeDAO,
                                   final StudyFilter filter,
                                   final int startPosition,
                                   final boolean doPagination) {
        super(sessionMgr, ebiSearchForm);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.tableHeaderNames = tableHeaderNames;
        this.sampleDAO = sampleDAO;
        this.studyDAO = studyDAO;
        this.biomeDAO = biomeDAO;
        this.filter = filter;
        this.startPosition = startPosition;
        this.doPagination = doPagination;
    }

    public StudiesViewModel getModel() {
        log.info("Building instance of " + StudiesViewModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        EBISearchForm ebiSearchForm = getEbiSearchForm();
        String submissionAccountId = (submitter != null ? submitter.getSubmissionAccountId() : null);

        //Get filtered studies
        List<Criterion> filterCriteria = StudiesViewHelper.buildFilterCriteria(filter, submissionAccountId, biomeDAO);
        List<Study> filteredStudies = getFilteredStudies(filterCriteria, doPagination);
        long filteredStudiesCount = studyDAO.countByCriteria(filterCriteria);
        ViewPagination pagination = new ViewPagination(startPosition, filteredStudiesCount, PAGE_SIZE);

        StudiesViewHelper.attachSampleSize(filteredStudies, sampleDAO);
        return new StudiesViewModel(submitter, ebiSearchForm, filteredStudies, null, pageTitle, breadcrumbs, propertyContainer, tableHeaderNames, pagination, filter);
    }

    private List<Study> getFilteredStudies(final List<Criterion> criteria,
                                           final boolean doPagination) {
        List<Study> result;
        if (doPagination) {
            result = studyDAO.retrieveFilteredStudies(criteria, startPosition, PAGE_SIZE, false, "studyName");
        } else {
            result = studyDAO.retrieveFilteredStudies(criteria, false, "studyName");
        }
        if (result != null && !result.isEmpty()) {
            for (Study study : result) {
                MemiTools.assignBiomeIconCSSClass(study, biomeDAO);
                MemiTools.assignBiomeIconTitle(study, biomeDAO);
            }
            return result;
        } else {
            return new ArrayList<Study>();
        }
    }
}