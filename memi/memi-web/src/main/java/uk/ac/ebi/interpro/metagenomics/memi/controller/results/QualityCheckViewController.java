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
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.QualityCheckViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results.QualityCheckViewModelBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * The controller for the analysis results page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Controller
public class QualityCheckViewController extends AbstractResultViewController {
    private static final Log log = LogFactory.getLog(QualityCheckViewController.class);

    protected String getModelViewName() {
        return "tabs/results/mainNavigation/qualityControl";
    }

    private ModelProcessingStrategy<Run> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Run>() {
            @Override
            public void processModel(ModelMap model, Run run) {
                log.info("Building quality check view model...");
                populateModel(model, run);
            }
        };
    }

    /**
     * Request method for the quality control tab on the result view page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_QUALITY_CONTROL)
    public ModelAndView ajaxLoadQualityControlTab(@PathVariable final String projectId,
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
        String pageTitle = "Quality check view: " + run.getExternalRunId() + "";

        AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), run.getReleaseVersion(), "completed");

        if (analysisJob == null) {
            throw new EntryNotFoundException();
        }

        final ViewModelBuilder<QualityCheckViewModel> builder = new QualityCheckViewModelBuilder(
                userManager,
                getEbiSearchForm(),
                pageTitle,
                getBreadcrumbs(run),
                propertyContainer,
                qualityControlFileDefinitions,
                functionalAnalysisFileDefinitions,
                taxonomicAnalysisFileDefinitions,
                analysisJob);
        final QualityCheckViewModel qualityCheckViewModel = builder.getModel();

        qualityCheckViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, qualityCheckViewModel);
        model.addAttribute("projectId", run.getExternalProjectId());
        model.addAttribute("sampleId", run.getExternalSampleId());
        model.addAttribute("runId", run.getExternalRunId());
        model.addAttribute("versionId", run.getReleaseVersion());
    }


    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_QC_TYPE)
    public void handleQCResultDownloads(@PathVariable final String projectId,
                                              @PathVariable final String sampleId,
                                              @PathVariable final String runId,
                                              @PathVariable final String releaseVersion,
                                              @PathVariable final String resultType,
                                              final HttpServletResponse response,
                                              final HttpServletRequest request) throws IOException {
        FileDefinitionId fileDefinitionId = FileDefinitionId.DEFAULT;
        if (resultType.equalsIgnoreCase("summary")) {
            fileDefinitionId = FileDefinitionId.QC_SUMMARY;
        } else if (resultType.equalsIgnoreCase("stats")) {
            fileDefinitionId = FileDefinitionId.QC_STATS;
        }else if (resultType.equalsIgnoreCase("base")) {
            fileDefinitionId = FileDefinitionId.QC_BASE;
        }else if (resultType.equalsIgnoreCase("base.sub-set")) {
            fileDefinitionId = FileDefinitionId.QC_BASE_SUBSET;
        }else if (resultType.equalsIgnoreCase("length")) {
            fileDefinitionId = FileDefinitionId.QC_LENGTH;
        }else if (resultType.equalsIgnoreCase("length.sub-set")) {
            fileDefinitionId = FileDefinitionId.QC_LENGTH_SUBSET;
        }else if (resultType.equalsIgnoreCase("length.bin")) {
            fileDefinitionId = FileDefinitionId.QC_LENGTH_BIN;
        }else if (resultType.equalsIgnoreCase("length.bin.sub-set")) {
            fileDefinitionId = FileDefinitionId.QC_LENGTH_BIN_SUBSET;
        }else if (resultType.equalsIgnoreCase("gc_bin")) {
            fileDefinitionId = FileDefinitionId.QC_GC_BIN;
        }else if (resultType.equalsIgnoreCase("gc_bin.sub-set")) {
            fileDefinitionId = FileDefinitionId.QC_GC_BIN_SUBSET;
        }else if (resultType.equalsIgnoreCase("gc")) {
            fileDefinitionId = FileDefinitionId.QC_GC;
        }else if (resultType.equalsIgnoreCase("gc.sub-set")) {
            fileDefinitionId = FileDefinitionId.QC_GC_SUBSET;
        }else {
            log.warn("Result type: " + resultType + " not found!");
        }
        doHandleQCExports(projectId, sampleId, runId, releaseVersion, response, request, fileDefinitionId);
    }
    protected void doHandleQCExports(@PathVariable final String projectId,
                                           @PathVariable final String sampleId,
                                           @PathVariable final String runId,
                                           @PathVariable final String releaseVersion,
                                           final HttpServletResponse response,
                                           final HttpServletRequest request,
                                           final FileDefinitionId fileDefinitionId) throws IOException {
        response.setContentType("text/tab-separated-values;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setLocale(Locale.ENGLISH);

        // Please note: The actual security check will be done further down
        Run run = getSecuredEntity(projectId, sampleId, runId, releaseVersion);

        if (run != null && fileDefinitionId != null) {
            // Perform security check
            if (isAccessible(run)) {
                AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), releaseVersion, "completed");
                if (analysisJob != null) {
                    DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(fileDefinitionId.name());
                    if (fileDefinition != null) {
                        File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
                        if (!fileObject.exists())
                            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        else
                            try {
                                openDownloadDialog(response, request, analysisJob, fileDefinition.getDownloadName(), fileObject);
                            } catch (IndexOutOfBoundsException e) {
                                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                            }
                    } else {//analysis job is NULL
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                } else {//access denied
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
                }
            } else {//run is NULL
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
    }

}