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
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.FunctionalViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results.FunctionalViewModelBuilder;

/**
 * The controller for the analysis results page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Controller
public class FunctionalViewController extends AbstractResultViewController {
    private static final Log log = LogFactory.getLog(FunctionalViewController.class);

    protected String getModelViewName() {
        return "tabs/results/mainNavigation/functional";
    }

    private ModelProcessingStrategy<Run> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Run>() {
            @Override
            public void processModel(ModelMap model, Run run) {
                log.info("Building functional results view model...");
                populateModel(model, run);
            }
        };
    }


    /**
     * Request method for the functional analysis tab on the sample view page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL)
    public ModelAndView ajaxLoadFunctionalTab(@PathVariable final String projectId,
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
        String pageTitle = "Functional results: " + run.getExternalRunId() + "";

        AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), run.getReleaseVersion(), "completed");
        if (analysisJob == null) {
            throw new EntryNotFoundException();
        }

        final ViewModelBuilder<FunctionalViewModel> builder = new FunctionalViewModelBuilder(
                userManager,
                getEbiSearchForm(),
                pageTitle,
                getBreadcrumbs(run),
                propertyContainer,
                run,
                qualityControlFileDefinitions,
                functionalAnalysisFileDefinitions,
                taxonomicAnalysisFileDefinitions,
                analysisJob);
        final FunctionalViewModel functionalViewModel = builder.getModel();

        functionalViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, functionalViewModel);
    }
}