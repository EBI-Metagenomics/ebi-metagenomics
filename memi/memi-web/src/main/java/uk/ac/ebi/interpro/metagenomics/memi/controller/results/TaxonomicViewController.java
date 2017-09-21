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
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.TaxonomicViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results.TaxonomicViewModelBuilder;

/**
 * The controller for the analysis results page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Controller
public class TaxonomicViewController extends AbstractResultViewController {
    private static final Log log = LogFactory.getLog(TaxonomicViewController.class);

    protected String getModelViewName() {
        return null;
    }

    private ModelProcessingStrategy<Run> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Run>() {
            @Override
            public void processModel(ModelMap model, Run run) {
                log.info("Building taxonomic results view model...");
                populateModel(model, run);
            }
        };
    }


    /**
     * Request method for the taxonomic analysis tab on the sample view page.
     *
     * @throws java.io.IOException
     */

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_LSU)
    public ModelAndView ajaxLoadTaxonomyLSUTab(@PathVariable final String projectId,
                                               @PathVariable final String sampleId,
                                               @PathVariable final String runId,
                                               @PathVariable final String releaseVersion,
                                               final ModelMap model) {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/results/mainNavigation/taxonomiclsu");
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_SSU)
    public ModelAndView ajaxLoadTaxonomySSUTab(@PathVariable final String projectId,
                                               @PathVariable final String sampleId,
                                               @PathVariable final String runId,
                                               @PathVariable final String releaseVersion,
                                               final ModelMap model) {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/results/mainNavigation/taxonomicssu");
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC)
    public ModelAndView ajaxLoadTaxonomyTab(@PathVariable final String projectId,
                                            @PathVariable final String sampleId,
                                            @PathVariable final String runId,
                                            @PathVariable final String releaseVersion,
                                            final ModelMap model) {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/results/mainNavigation/taxonomic");
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_PIE_CHART)
    public ModelAndView ajaxLoadTaxPieChartView(@PathVariable final String projectId,
                                                @PathVariable final String sampleId,
                                                @PathVariable final String runId,
                                                @PathVariable final String releaseVersion,
                                                @PathVariable final String rRNAType,
                                                final ModelMap model) {
        String viewName = "tabs/results/taxonomicAnalysis/ssu/taxPieChartView";
        if (rRNAType.equalsIgnoreCase("LSU")) {
            viewName = "tabs/results/taxonomicAnalysis/lsu/taxPieChartView";
        }
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), viewName);
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_KRONA_VIEW)
    public ModelAndView ajaxLoadKronaChartView(@PathVariable final String projectId,
                                               @PathVariable final String sampleId,
                                               @PathVariable final String runId,
                                               @PathVariable final String releaseVersion,
                                               @PathVariable final String rRNAType,
                                               final ModelMap model) {
        String viewName = "tabs/results/taxonomicAnalysis/ssu/kronaChartView";
        if (rRNAType.equalsIgnoreCase("LSU")) {
            viewName = "tabs/results/taxonomicAnalysis/lsu/kronaChartView";
        }
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), viewName);
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_BAR_CHART)
    public ModelAndView ajaxLoadTaxBarChartView(@PathVariable final String projectId,
                                                @PathVariable final String sampleId,
                                                @PathVariable final String runId,
                                                @PathVariable final String releaseVersion,
                                                @PathVariable final String rRNAType,
                                                final ModelMap model) {
        String viewName = "tabs/results/taxonomicAnalysis/ssu/taxBarChartView";
        if (rRNAType.equalsIgnoreCase("LSU")) {
            viewName = "tabs/results/taxonomicAnalysis/lsu/taxBarChartView";
        }
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), viewName);
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_COLUMN_CHART)
    public ModelAndView ajaxLoadTaxColumnChartView(@PathVariable final String projectId,
                                                   @PathVariable final String sampleId,
                                                   @PathVariable final String runId,
                                                   @PathVariable final String releaseVersion,
                                                   @PathVariable final String rRNAType,
                                                   final ModelMap model) {
        String viewName = "tabs/results/taxonomicAnalysis/ssu/taxColumnChartView";
        if (rRNAType.equalsIgnoreCase("LSU")) {
            viewName = "tabs/results/taxonomicAnalysis/lsu/taxColumnChartView";
        }
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), viewName);
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

        final ViewModelBuilder<TaxonomicViewModel> builder = new TaxonomicViewModelBuilder(
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
        final TaxonomicViewModel taxonomicViewModel = builder.getModel();

        taxonomicViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, taxonomicViewModel);
    }
}