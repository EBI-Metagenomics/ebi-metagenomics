package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.PublicationComparator;
import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PublicationType;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.StudyViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.AbstractViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/**
 * Model builder class for StudyViewModel. See {@link StudyViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class StudyViewModelBuilder extends AbstractViewModelBuilder<StudyViewModel> {

    private final static Log log = LogFactory.getLog(StudyViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private Study study;

    private RunDAO runDAO;

    private List<Publication> relatedLinks;

    private List<Publication> relatedPublications;


    public StudyViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                                 Study study, RunDAO runDAO) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.study = study;
        this.runDAO = runDAO;
        this.relatedLinks = new ArrayList<Publication>();
        this.relatedPublications = new ArrayList<Publication>();
    }

    @Override
    public StudyViewModel getModel() {
        if (log.isInfoEnabled()) {
            log.info("Building instance of " + StudyViewModel.class + "...");
        }
        Submitter submitter = getSessionSubmitter(sessionMgr);
        List<QueryRunsForProjectResult> runs = getRunsForStudyViewModel(submitter);
        buildPublicationLists();
        return new StudyViewModel(submitter, study, runs, pageTitle,
                breadcrumbs, propertyContainer, relatedPublications, relatedLinks);
    }

    private List<QueryRunsForProjectResult> getRunsForStudyViewModel(Submitter submitter) {
        long studyId = study.getId();
        if (submitter == null) {
            return runDAO.retrieveRunsByProjectId(studyId, true);
        } else {
            //Check if submitter is study owner
            if (submitter.getSubmissionAccountId().equalsIgnoreCase(study.getSubmissionAccountId())) {
                return runDAO.retrieveRunsByProjectId(studyId, false);
            } else {
                return runDAO.retrieveRunsByProjectId(studyId, true);
            }
        }
    }

    /**
     * Divides the set of publications into 2 different types of publication sets.
     */
    private void buildPublicationLists() {
        for (Publication pub : study.getPublications()) {
            if (pub.getPubType().equals(PublicationType.PUBLICATION)) {
                relatedPublications.add(pub);
            } else if (pub.getPubType().equals(PublicationType.WEBSITE_LINK)) {
                relatedLinks.add(pub);
            }
        }
        //Sorting lists
        Collections.sort(relatedPublications, new PublicationComparator());
        Collections.sort(relatedLinks, new PublicationComparator());
    }
}