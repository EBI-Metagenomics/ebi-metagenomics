package uk.ac.ebi.interpro.metagenomics.memi.controller.results;


import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.controller.SecuredAbstractController;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.AnalysisJobDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.temp.SampleAnnotationDAO;
import uk.ac.ebi.interpro.metagenomics.memi.exceptionHandling.EntryNotFoundException;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FunctionalAnalysisFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.ResultFileDefinitionImpl;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.ResultViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results.ResultViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Abstract controller class which is inherited by several result view controllers.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public abstract class AbstractResultViewController extends SecuredAbstractController<Run> {

    @Resource
    protected SampleDAO sampleDAO;

    @Resource
    protected SampleAnnotationDAO sampleAnnotationDAO;

    @Resource
    protected AnalysisJobDAO analysisJobDAO;

    @Resource
    protected RunDAO runDAO;

    @Resource
    private MemiDownloadService downloadService;

    @Resource(name = "qualityControlFileDefinitions")
    protected List<ResultFileDefinitionImpl> qualityControlFileDefinitions;

    @Resource(name = "functionalAnalysisFileDefinitions")
    protected List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions;

    @Resource(name = "taxonomicAnalysisFileDefinitions")
    protected List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions;

    @Resource
    protected Map<String, DownloadableFileDefinition> fileDefinitionsMap;

    @Resource
    protected Map<String, DownloadableFileDefinition> chunkedResultFilesMap;

    protected Run getSecuredEntity(final String projectId,
                                   final String sampleId,
                                   final String runId,
                                   String version) {
//        TODO: Implement
        return runDAO.readByRunIdDeep(projectId, sampleId, runId, version);
    }


    protected List<Breadcrumb> getBreadcrumbs(SecureEntity secureEntity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (secureEntity != null && secureEntity instanceof Run) {
            Run run = (Run) secureEntity;
            String runURL = "projects/" + run.getExternalProjectId() + "/samples/" + run.getExternalSampleId() + "/runs/" + run.getExternalRunId() + "/results/versions/" + run.getReleaseVersion();
            String sampleURL = "projects/" + run.getExternalProjectId() + "/samples/" + run.getExternalSampleId();
            String projectURL = "projects/" + run.getExternalProjectId();
            result.add(new Breadcrumb("Project: " + run.getExternalProjectId(), "View project " + run.getExternalProjectId(), projectURL));
            result.add(new Breadcrumb("Sample: " + run.getExternalSampleId(), "View sample " + run.getExternalSampleId(), sampleURL));
            result.add(new Breadcrumb("Run: " + run.getExternalRunId(), "View run " + run.getExternalRunId(), runURL));
        }
        return result;
    }

    protected void openDownloadDialog(final HttpServletResponse response,
                                      final HttpServletRequest request,
                                      final AnalysisJob analysisJob,
                                      final String fileNameEnd,
                                      final File fileObject) {
        if (downloadService != null) {
            //white spaces are replaced by underscores
            final String fileNameForDownload = getFileName(analysisJob, fileNameEnd);
            downloadService.openDownloadDialog(response, request, fileObject, fileNameForDownload, false);
        }
    }

    private String getFileName(final AnalysisJob analysisJob,
                               final String fileNameEnd) {
        return analysisJob.getInputFileName().replace(" ", "_") + fileNameEnd;
    }

    /**
     * Populates the abstract result view model.
     */
    protected void populateModel(final ModelMap model, final Run run, String pageTitle) {

        AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), run.getReleaseVersion(), "completed");
        if (analysisJob == null) {
            throw new EntryNotFoundException();
        }

        final ViewModelBuilder<ResultViewModel> builder = new ResultViewModelBuilder(
                userManager,
                getEbiSearchForm(),
                analysisJob.getSample(),
                run,
                pageTitle,
                getBreadcrumbs(run),
                analysisJob,
                //TODO: Refactor
//                MemiTools.getArchivedSeqs(fileInfoDAO, sample),
                new ArrayList<String>(),
                propertyContainer,
                qualityControlFileDefinitions,
                functionalAnalysisFileDefinitions,
                taxonomicAnalysisFileDefinitions);
        final ResultViewModel resultModel = builder.getModel();

        resultModel.changeToHighlightedClass(ViewModel.DEFAULT_CLASS);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, resultModel);
    }
}