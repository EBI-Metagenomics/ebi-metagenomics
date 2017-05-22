package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.CompareToolStudyVO;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.CompareViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.util.List;

/**
 * Model builder class for StudiesViewModel. See {@link ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class CompareViewModelBuilder extends AbstractViewModelBuilder<CompareViewModel> {

    private final static Log log = LogFactory.getLog(CompareViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private StudyDAO studyDAO;


    public CompareViewModelBuilder(final UserManager sessionMgr, final EBISearchForm ebiSearchForm,
                                       final String pageTitle, final List<Breadcrumb> breadcrumbs,
                                       final MemiPropertyContainer propertyContainer, final StudyDAO studyDAO) {
        super(sessionMgr, ebiSearchForm);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.studyDAO = studyDAO;
    }


    public CompareViewModel getModel() {
        log.info("Building instance of " + CompareViewModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        EBISearchForm ebiSearchForm = getEbiSearchForm();
        String submissionAccountId = (submitter != null ? submitter.getSubmissionAccountId() : null);

        List<CompareToolStudyVO> filteredStudies = studyDAO.retrieveNonAmpliconStudies(submissionAccountId);

        return new CompareViewModel(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer, filteredStudies);
    }
}