package uk.ac.ebi.interpro.metagenomics.memi.controller.results;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.MGPortalURLCollection;
import uk.ac.ebi.interpro.metagenomics.memi.controller.ModelProcessingStrategy;
import uk.ac.ebi.interpro.metagenomics.memi.exceptionHandling.EntryNotFoundException;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.StatsViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results.StatsViewModelBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * The controller for the analysis results stats view.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Controller
public class StatsViewController extends AbstractResultViewController {
    private static final Log log = LogFactory.getLog(StatsViewController.class);

    protected String getModelViewName() {
        return "tabs/results/mainNavigation/stats";
    }

    private ModelProcessingStrategy<Run> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Run>() {
            @Override
            public void processModel(ModelMap model, Run run) {
                log.info("Building stats view model...");
                populateModel(model, run);
            }
        };
    }

    /**
     * Request method for the stats tab on the result view page.
     *
     * @throws IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_STATS)
    public ModelAndView ajaxLoadStatsTab(@PathVariable final String projectId,
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
        String pageTitle = "Stats view: " + run.getExternalRunId() + "";

        AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), run.getReleaseVersion(), "completed");

        if (analysisJob == null) {
            throw new EntryNotFoundException();
        }

        final ViewModelBuilder<StatsViewModel> builder = new StatsViewModelBuilder(
                userManager,
                getEbiSearchForm(),
                pageTitle,
                getBreadcrumbs(run),
                propertyContainer,
                qualityControlFileDefinitions,
                functionalAnalysisFileDefinitions,
                taxonomicAnalysisFileDefinitions,
                analysisJob);
        final StatsViewModel statsViewModel = builder.getModel();

        statsViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, statsViewModel);
        model.addAttribute("projectId", run.getExternalProjectId());
        model.addAttribute("sampleId", run.getExternalSampleId());
        model.addAttribute("runId", run.getExternalRunId());
        model.addAttribute("versionId", run.getReleaseVersion());
    }

    private String getAbsResultDirPath(final Study study) {
        final String rootPath = propertyContainer.getPathToAnalysisDirectory();
        return rootPath + study.getResultDirectory();
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_STATS_FILES,
            produces = "image/svg+xml")
    public
    @ResponseBody
    byte[] heatMap(@PathVariable final String projectId,
                   @PathVariable final String sampleId,
                   @PathVariable final String runId,
                   @PathVariable final String releaseVersion,
                   @PathVariable final String svgFile,
                   final HttpServletResponse response) throws IOException {
        final Run run = getSecuredEntity(projectId, sampleId, runId, releaseVersion);
        if (isAccessible(run)) {
            //Get directory name + root path
            AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(runId, releaseVersion, "completed");
            if (analysisJob == null) {
                throw new EntryNotFoundException();
            }
            final String resultDirectory = analysisJob.getResultDirectory();
            final String rootPath = propertyContainer.getPathToAnalysisDirectory();
            final String resultDirectoryAbsolute = rootPath + resultDirectory;
            String filename = resultDirectoryAbsolute + File.separator;
            if (svgFile.equalsIgnoreCase("heatmap")) {
                filename += "charts/fold-change.svg";
            } else if (svgFile.equalsIgnoreCase("tad-plots")) {
                filename += "charts/tad-plots.svg";
            } else {
                throw new EntryNotFoundException();
            }
            File file = new File(filename);
            if (file.exists()) {
                return IOUtils.toByteArray(new FileInputStream(filename));
            } else {
                throw new EntryNotFoundException();
            }

        } else {//access denied
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendRedirect("/metagenomics/accessDenied");
            return new byte[0];
        }
    }
}