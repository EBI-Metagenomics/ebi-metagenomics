package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.MGPortalURLCollection;
import uk.ac.ebi.interpro.metagenomics.memi.controller.ModelProcessingStrategy;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.PipelineReleaseDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.StudyViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study.StudyViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents the controller for the study (project) overview page. The use of terms project for study has a historical background.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class StudyViewController extends AbstractStudyViewController {

    private final static Log log = LogFactory.getLog(StudyViewController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "project";

    @Resource
    private PipelineReleaseDAO pipelineReleaseDAO;

    @Resource
    private BiomeDAO biomeDAO;

    @RequestMapping(value = MGPortalURLCollection.PROJECT)
    public ModelAndView doGetStudy(@PathVariable final String studyId,
                                   final ModelMap model) {

        return checkAccessAndBuildModel(new ModelProcessingStrategy<Study>() {
            @Override
            public void processModel(ModelMap model, Study study) {
                populateModel(model, study);
            }
        }, model, getSecuredEntity(studyId), getModelViewName());
    }

    /**
     * Temporary re-directions.
     */
    @RequestMapping(value = "/project/{studyId}")
    public String doGetStudy(@PathVariable final String studyId) {
        return "redirect:/projects/" + studyId;
    }

    //TODO: Add security layer for private studies
    @RequestMapping(value = MGPortalURLCollection.PROJECT_MAP_DATA)
    public void getSampleData(@PathVariable final String studyId, HttpServletResponse response)
            throws IOException {
        Study study = getSecuredEntity(studyId);

        final String studyResultDirectory = study.getResultDirectory();
        final String rootPath = propertyContainer.getPathToAnalysisDirectory();
        final String fileName = "google-map-sample-data.json";
        final String resultDirectoryAbsolute = rootPath + studyResultDirectory + File.separator + fileName;
        final File googleDataFile = new File(resultDirectoryAbsolute);

        ServletOutputStream servletOutStream = null;
        InputStream fileInputStream = null;
        try {
            servletOutStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            fileInputStream = new FileInputStream(googleDataFile);
            int contentLengthCounter = 0;
            int read;
            while ((read = fileInputStream.read(buffer)) != -1) {
                servletOutStream.write(buffer, 0, read);
                contentLengthCounter += read;
            }
            response.setContentLength(contentLengthCounter);
            response.setContentType("text/plain; charset=utf-8");
            fileInputStream.close();
            servletOutStream.flush();
            servletOutStream.close();
        } catch (IOException e) {
            if (servletOutStream != null) {
                servletOutStream.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            throw e;
        }
    }

    // TODO Bring this back? Not for now, and maybe we'll go to datatables anyway which will provide this export functionality
//    @RequestMapping(value = MGPortalURLCollection.PROJECT_EXPORT)
//    public ModelAndView doExportSamples(@PathVariable final String studyId,
//                                        final ModelMap model,
//                                        final HttpServletResponse response) throws Exception {
//        return checkAccessAndBuildModel(new ModelProcessingStrategy<Study>() {
//            @Override
//            public void processModel(ModelMap model, Study study) {
//                populateModel(model, study);
//
//                List<QueryRunsForProjectResult> runs = ((StudyViewModel) model.get(StudyViewModel.MODEL_ATTR_NAME)).getRuns();
//                if (runs != null && runs.size() > 0) {
//                    Set<String> samplesIDs = MemiTools.getSampleIds(runs);
//                    Class clazz = MemiTools.getTypeOfGenericSet(runs);
//                    if (downloadService != null) {
//                        boolean isDialogOpen = downloadService.openDownloadDialog(response, clazz, samplesIDs);
//                        model.addAttribute("isDialogOpen", isDialogOpen);
//                    } else {
//                        log.warn("Could not open download dialog to export samples. Download service is null!");
//                    }
//                } else {
//                    log.info("There are no samples to be exported!");
//                }
//            }
//        }, model, getSecuredEntity(studyId), getModelViewName());
//    }

    /**
     * Creates a {@link StudyViewModel} and adds it to the specified model map.
     */

    private void populateModel(final ModelMap model, final Study study) {
        //  Assign biome CSS class to the study
        MemiTools.assignBiomeIconCSSClass(study, biomeDAO);
        MemiTools.assignBiomeIconTitle(study, biomeDAO);
        String pageTitle = "Project: " + study.getStudyName() + "";
        final ViewModelBuilder<StudyViewModel> builder = new StudyViewModelBuilder(sessionManager,
                pageTitle, getBreadcrumbs(study), propertyContainer, study, pipelineReleaseDAO);
        final StudyViewModel studyModel = builder.getModel();
        studyModel.changeToHighlightedClass(ViewModel.TAB_CLASS_PROJECTS_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(StudyViewModel.MODEL_ATTR_NAME, studyModel);
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

}