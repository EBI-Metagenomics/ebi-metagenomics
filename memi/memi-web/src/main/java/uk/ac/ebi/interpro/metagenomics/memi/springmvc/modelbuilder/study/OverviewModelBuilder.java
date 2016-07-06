package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.PublicationComparator;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.OverviewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.AbstractViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model builder class for StudyViewModel. See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study.OverviewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class OverviewModelBuilder extends AbstractViewModelBuilder<OverviewModel> {

    private final static Log log = LogFactory.getLog(OverviewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private Study study;

    private RunDAO runDAO;

    private List<Publication> relatedLinks;

    private List<Publication> relatedPublications;


    public OverviewModelBuilder(UserManager sessionMgr, EBISearchForm ebiSearchForm, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                                Study study, RunDAO runDAO) {
        super(sessionMgr, ebiSearchForm);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.study = study;
        this.runDAO = runDAO;
        this.relatedLinks = new ArrayList<Publication>();
        this.relatedPublications = new ArrayList<Publication>();
    }

    @Override
    public OverviewModel getModel() {
        if (log.isInfoEnabled()) {
            log.info("Building instance of " + OverviewModel.class + "...");
        }
        Submitter submitter = getSessionSubmitter(sessionMgr);
        EBISearchForm ebiSearchForm = getEbiSearchForm();
        List<QueryRunsForProjectResult> runs = getRunsForStudyViewModel(submitter);
        buildPublicationLists();
        boolean isGoogleMapDataAvailable = isGoogleMapDataAvailable();
        return new OverviewModel(submitter, ebiSearchForm, study, runs, pageTitle,
                breadcrumbs, propertyContainer, relatedPublications, relatedLinks,isGoogleMapDataAvailable);
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
            if (pub.getPubType().equals("WEBSITE_LINK")) {
                relatedLinks.add(pub);
            } else {
                relatedPublications.add(pub);
            }
        }
        //Sorting lists
        Collections.sort(relatedPublications, new PublicationComparator());
        Collections.sort(relatedLinks, new PublicationComparator());
    }

    public boolean isGoogleMapDataAvailable() {
        // Check JSON file does exist
        final String studyResultDirectory = study.getResultDirectory();
        final String rootPath = propertyContainer.getPathToAnalysisDirectory();
        final String fileName = "google-map-sample-data.json";
        final String resultDirectoryAbsolute = rootPath + studyResultDirectory + File.separator + fileName;
        final File googleDataFile = new File(resultDirectoryAbsolute);
        if (googleDataFile.exists()) {
            return true;
        }
        return false;
    }
}