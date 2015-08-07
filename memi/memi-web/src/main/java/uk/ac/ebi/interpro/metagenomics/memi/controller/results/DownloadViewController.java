package uk.ac.ebi.interpro.metagenomics.memi.controller.results;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.MGPortalURLCollection;
import uk.ac.ebi.interpro.metagenomics.memi.controller.ModelProcessingStrategy;
import uk.ac.ebi.interpro.metagenomics.memi.exceptionHandling.EntryNotFoundException;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.DownloadViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results.DownloadViewModelBuilder;

/**
 * The controller for the analysis results page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Controller
public class DownloadViewController extends AbstractResultViewController {
    private static final Log log = LogFactory.getLog(DownloadViewController.class);

    protected String getModelViewName() {
        return "tabs/results/mainNavigation/download";
    }

    private ModelProcessingStrategy<Run> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Run>() {
            @Override
            public void processModel(ModelMap model, Run run) {
                log.info("Building download view model...");
                populateModel(model, run);
            }
        };
    }


    /**
     * Request method for the download tab on the sample view page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_DOWNLOADS)
    public ModelAndView ajaxLoadDownloadTab(@PathVariable final String projectId,
                                            @PathVariable final String sampleId,
                                            @PathVariable final String runId,
                                            @PathVariable final String releaseVersion,
                                            final ModelMap model) {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), getModelViewName());
    }

    /**
     * Creates the analysis page model and adds it to the specified model map.
     */
    protected void populateModel(final ModelMap model, final Run run) {
        String pageTitle = "Download view: " + run.getExternalRunId() + "";

        AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), run.getReleaseVersion(), "completed");
        if (analysisJob == null) {
            throw new EntryNotFoundException();
        }

        final ViewModelBuilder<DownloadViewModel> builder = new DownloadViewModelBuilder(
                sessionManager,
                pageTitle,
                getBreadcrumbs(run),
                propertyContainer,
                run,
                fileDefinitionsMap,
                analysisJob);
        final DownloadViewModel downloadViewModel = builder.getModel();

        downloadViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, downloadViewModel);
    }
}