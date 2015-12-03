package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.PipelineReleaseDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineRelease;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.StudyViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.AbstractViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.File;
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

    private PipelineReleaseDAO pipelineReleaseDAO;

    public StudyViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                                 Study study, PipelineReleaseDAO pipelineReleaseDAO) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.study = study;
        this.pipelineReleaseDAO = pipelineReleaseDAO;
    }

    @Override
    public StudyViewModel getModel() {
        if (log.isInfoEnabled()) {
            log.info("Building instance of " + StudyViewModel.class + "...");
        }
        Submitter submitter = getSessionSubmitter(sessionMgr);
        String tabDisabledOption = getTabDisabledOption();
        boolean isGoogleMapDataAvailable = isGoogleMapDataAvailable();
        return new StudyViewModel(submitter, study, pageTitle,
                breadcrumbs, propertyContainer, tabDisabledOption, isGoogleMapDataAvailable);
    }

    /**
     * Checks for all pipeline release if results do exist. If no results exist then disable the study summary tab.
     */
    public String getTabDisabledOption() {
        final String disableOption = "disabled: [1]";

        if (study == null || study.getResultDirectory() == null || study.getResultDirectory().isEmpty()) {
            return disableOption; // No data to show, disable tab with index 1
        } else {
            // Check if study result directory does exist and is not empty (suppress tab if necessary)
            final String studyResultDirectory = study.getResultDirectory();
            final String rootPath = propertyContainer.getPathToAnalysisDirectory();
            final String resultDirectoryAbsolute = rootPath + studyResultDirectory;
            //Get all pipeline releases and iterate over them
            List<PipelineRelease> pipelines = pipelineReleaseDAO.retrieveAll();
            boolean hasResults = false;
            for (PipelineRelease pipeline : pipelines) {
                final File results = new File(resultDirectoryAbsolute + File.separator + "version_" + pipeline.getReleaseVersion() + File.separator + "project-summary");
                if (results.exists() && results.isDirectory() && results.list().length > 0) {
                    // We have data, no tabs to be disabled
                    hasResults = true;
                    //leave the loop, we proved that:
                    //1. The result dir does exist and is not empty
                    break;
                }
            }
            if (hasResults) {
                return ""; // No data to show, disable tab with index 1
            } else {
                return disableOption; // We have data, no tabs to be disabled
            }
        }
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
