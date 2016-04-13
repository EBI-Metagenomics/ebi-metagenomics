package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.MGPortalURLCollection;
import uk.ac.ebi.interpro.metagenomics.memi.controller.ModelProcessingStrategy;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.AnalysisJobDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.PipelineReleaseDAO;
import uk.ac.ebi.interpro.metagenomics.memi.exceptionHandling.EntryNotFoundException;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.DownloadViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study.StudyDownloadViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * Download tab on the project page.
 */
@Controller
public class DownloadStudyController extends AbstractStudyViewController {

    private static final Log log = LogFactory.getLog(DownloadStudyController.class);

    @Resource
    protected AnalysisJobDAO analysisJobDAO;

    @Resource
    private PipelineReleaseDAO pipelineReleaseDAO;

    @Resource
    private MemiDownloadService downloadService;

    @Resource
    protected Map<String, DownloadableFileDefinition> fileDefinitionsMap;


    protected String getModelViewName() {
        return "tabs/study/download";
    }

    private ModelProcessingStrategy<Study> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Study>() {
            @Override
            public void processModel(ModelMap model, Study study) {
                log.info("Building download view model...");
                populateModel(model, study);
            }
        };
    }

    /**
     * Request method for the download tab on the sample view page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_DOWNLOAD)
    public ModelAndView ajaxLoadDownloadTab(@PathVariable final String studyId,
                                            final ModelMap model) {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(studyId), getModelViewName());
    }

    protected void populateModel(final ModelMap model, final Study study) {

        if (study == null) {
            throw new EntryNotFoundException();
        }

        final String pageTitle = "Download view: " + study.getStudyId();
        final ViewModelBuilder<DownloadViewModel> builder = new StudyDownloadViewModelBuilder(
                sessionManager,
                pageTitle, // Not really needed as this is within an AJAX tab anyway?
                getBreadcrumbs(study), // Not really needed as this is within an AJAX tab anyway?
                propertyContainer,
                fileDefinitionsMap,
                study,
                pipelineReleaseDAO);

        final DownloadViewModel downloadViewModel = builder.getModel();
        downloadViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_PROJECTS_VIEW); // Not really needed as this is within an AJAX tab anyway?
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, downloadViewModel);
    }

    /**
     * Perform the project summary file download.
     *
     * @param studyId
     * @param releaseVersion
     * @param exportValue
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SUMMARY_EXPORT)
    public void doHandleSummaryExports(@PathVariable final String studyId,
                                        @PathVariable final String releaseVersion,
                                        @RequestParam(required = true, value = "exportValue") final String exportValue,
                                        final HttpServletResponse response,
                                        final HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setLocale(Locale.ENGLISH);

        Study study = getSecuredEntity(studyId);

        if (study != null) {
            if (isAccessible(study)) {
                File file = getDownloadFile(study, releaseVersion, exportValue);
                if (file != null) {
                    final String filename = studyId + "_" + exportValue + "_v" + releaseVersion + ".tsv";
                    downloadService.openDownloadDialog(response, request, file, filename ,false);
                } else {//analysis job is NULL
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {//access denied
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect("/metagenomics/projects/" + studyId + "/accessDenied");
            }
        } else {//run is NULL
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    private File getDownloadFile(final Study study, final String version, final String exportValue) {
        final String rootPath = propertyContainer.getPathToAnalysisDirectory();
        final String resultDirectoryAbsolute = rootPath + study.getResultDirectory();

        final String filename = resultDirectoryAbsolute + File.separator + "version_" + version + File.separator + "project-summary" + File.separator + exportValue + "_v" + version + ".tsv";
        final File file = new File(filename);
        return file;
    }


}
