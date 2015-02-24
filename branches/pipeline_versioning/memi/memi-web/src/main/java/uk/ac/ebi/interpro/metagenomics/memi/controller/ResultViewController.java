package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * The controller for the analysis results page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Controller
//@RequestMapping("/projects/{projectId}/samples/{sampleId}/runs/{runId}")
public class ResultViewController extends AbstractResultViewController {
    private static final Log log = LogFactory.getLog(ResultViewController.class);

    protected String getModelViewName() {
        return "results";
    }

    @Resource
    private MemiDownloadService downloadService;

    /**
     * @param projectId External project identifier (e.g. in ENA, for instance ERP000001)
     * @param sampleId  External sample identifier (e.g. in ENA, for instance ERS580795)
     * @param runId     External run identifier (e.g. in ENA, for instance ERR000001)
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS)
    public ModelAndView doGetSample(@PathVariable final String projectId,
                                    @PathVariable final String sampleId,
                                    @PathVariable final String runId,
                                    final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId), "results");
    }

    private ModelProcessingStrategy<Run> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Run>() {
            @Override
            public void processModel(ModelMap model, Run run) {
                log.info("Building sample view model...");
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
                                            final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/mainNavigation/download");
    }

    /**
     * Request method for the quality control tab on the sample view page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_QUALITY_CONTROL)
    public ModelAndView ajaxLoadQualityControlTab(@PathVariable final String projectId,
                                                  @PathVariable final String sampleId,
                                                  @PathVariable final String runId,
                                                  @PathVariable final String releaseVersion,
                                                  final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/mainNavigation/qualityControl");
    }

    /**
     * Request method for the taxonomic analysis tab on the sample view page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC)
    public ModelAndView ajaxLoadTaxonomyTab(@PathVariable final String projectId,
                                            @PathVariable final String sampleId,
                                            @PathVariable final String runId,
                                            @PathVariable final String releaseVersion,
                                            final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/mainNavigation/taxonomic");
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
                                              final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/mainNavigation/functional");
    }

    /**
     * Request method for the overview tab on the sample view page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_OVERVIEW)
    public ModelAndView ajaxLoadOverviewTab(@PathVariable final String projectId,
                                            @PathVariable final String sampleId,
                                            @PathVariable final String runId,
                                            @PathVariable final String releaseVersion,
                                            final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/mainNavigation/overview");
    }

    /**
     * Request method for the GO bar chart tab on the functional analysis tab.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL_GO_BAR_CHART)
    public ModelAndView ajaxLoadGoBarChartTab(@PathVariable final String projectId,
                                              @PathVariable final String sampleId,
                                              @PathVariable final String runId,
                                              @PathVariable final String releaseVersion,
                                              final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/functionalAnalysis/goBarChartView");
    }

    /**
     * Request method for the GO pie chart tab on the functional analysis tab.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_FUNCTIONAL_GO_PIE_CHART)
    public ModelAndView ajaxLoadGoPieChartTab(@PathVariable final String projectId,
                                              @PathVariable final String sampleId,
                                              @PathVariable final String runId,
                                              @PathVariable final String releaseVersion,
                                              final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/functionalAnalysis/goPieChartView");
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_KRONA_VIEW)
    public ModelAndView ajaxLoadKronaChartView(@PathVariable final String projectId,
                                               @PathVariable final String sampleId,
                                               @PathVariable final String runId,
                                               @PathVariable final String releaseVersion,
                                               final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/taxonomicAnalysis/kronaChartView");
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_PIE_CHART)
    public ModelAndView ajaxLoadTaxPieChartView(@PathVariable final String projectId,
                                                @PathVariable final String sampleId,
                                                @PathVariable final String runId,
                                                @PathVariable final String releaseVersion,
                                                final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/taxonomicAnalysis/taxPieChartView");
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_BAR_CHART)
    public ModelAndView ajaxLoadTaxBarChartView(@PathVariable final String projectId,
                                                @PathVariable final String sampleId,
                                                @PathVariable final String runId,
                                                @PathVariable final String releaseVersion,
                                                final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/taxonomicAnalysis/taxBarChartView");
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_COLUMN_CHART)
    public ModelAndView ajaxLoadTaxColumnChartView(@PathVariable final String projectId,
                                                   @PathVariable final String sampleId,
                                                   @PathVariable final String runId,
                                                   @PathVariable final String releaseVersion,
                                                   final ModelMap model) throws IOException {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId, runId, releaseVersion), "tabs/taxonomicAnalysis/taxColumnChartView");
    }

    @RequestMapping(value = "/accessDenied")
    public ModelAndView doGetAccessDeniedPage(@PathVariable final String sampleId) {
        return buildAccessDeniedModelAndView(sampleId);
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_GO_SLIM)
    public ModelAndView doExportGOSlimFile(@PathVariable final String projectId,
                                           @PathVariable final String sampleId,
                                           @PathVariable final String runId,
                                           @PathVariable final String releaseVersion,
                                           final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_SLIM_FILE.name());
        return handleExport(getSecuredEntity(projectId, sampleId, runId, releaseVersion), releaseVersion, response, request, fileDefinition);
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_GO)
    public ModelAndView doExportGOFile(@PathVariable final String projectId,
                                       @PathVariable final String sampleId,
                                       @PathVariable final String runId,
                                       @PathVariable final String releaseVersion,
                                       final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_COMPLETE_FILE.name());
        return handleExport(getSecuredEntity(projectId, sampleId, runId, releaseVersion), releaseVersion, response, request, fileDefinition);
    }


    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_FILE_EXPORT_I5_TSV)
    public ModelAndView doExportI5File(@PathVariable final String projectId,
                                       @PathVariable final String sampleId,
                                       @PathVariable final String runId,
                                       @PathVariable final String releaseVersion,
                                       final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.INTERPROSCAN_RESULT_FILE.name());
        return handleExport(getSecuredEntity(projectId, sampleId, runId, releaseVersion), releaseVersion, response, request, fileDefinition);
    }

//    @RequestMapping(value = "/doExportIPRFile", method = RequestMethod.GET)
//    public ModelAndView doExportIPRFile(@PathVariable final String sampleId,
//                                        final HttpServletResponse response, final HttpServletRequest request) {
//        return handleExport(sampleId, response, request, EmgFile.ResultFileType.IPR, "_InterPro_sum.csv");
//    }

    @RequestMapping(value = "/doExportIPRhitsFile", method = RequestMethod.GET)
    public ModelAndView doExportIPRhitsFile(@PathVariable final String projectId,
                                            @PathVariable final String sampleId,
                                            @PathVariable final String runId,
                                            @PathVariable final String releaseVersion,
                                            final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.PREDICTED_CDS_WITH_INTERPRO_MATCHES_FILE.name());
        return handleExport(getSecuredEntity(projectId, sampleId, runId, releaseVersion), releaseVersion, response, request, fileDefinition);
    }

    /**
     * @param response
     * @param request
     * @return
     */
    private ModelAndView handleExport(final Run run,
                                      final String releaseVersion,
                                      final HttpServletResponse response,
                                      final HttpServletRequest request, final DownloadableFileDefinition fileDefinition) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Run>() {
            @Override
            public void processModel(ModelMap model, Run run) {
                log.info("Open download dialog...");
                AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), releaseVersion, "completed");
                if (analysisJob != null) {
                    File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
                    openDownloadDialog(response, request, analysisJob, fileDefinition.getDownloadName(), fileObject);
                }
            }
        }, null, run, getModelViewName());
    }


    /**
     * Creates the analysis page model and adds it to the specified model map.
     */
    protected void populateModel(final ModelMap model, final Run run) {
        String pageTitle = "Analysis results: " + run.getExternalRunId() + "";
        populateModel(model, run, pageTitle);
    }
}