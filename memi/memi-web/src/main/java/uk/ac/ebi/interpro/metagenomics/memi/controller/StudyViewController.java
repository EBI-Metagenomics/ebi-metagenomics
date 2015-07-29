package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.StudyViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.StudyViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the controller for the study (project) overview page. The use of terms project for study has a historical background.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class StudyViewController extends SecuredAbstractController<Study> {

    private final static Log log = LogFactory.getLog(StudyViewController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "project";

    @Resource
    private StudyDAO studyDAO;

    @Resource
    private RunDAO runDAO;

    @Resource
    private SubmissionContactDAO submissionContactDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    private Study getSecuredEntity(final String projectId) {
        return studyDAO.readByStringId(projectId);
    }

    //GET Methods

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
        String pageTitle = "Project overview: " + study.getStudyName() + "";
        final ViewModelBuilder<StudyViewModel> builder = new StudyViewModelBuilder(sessionManager,
                pageTitle, getBreadcrumbs(study), propertyContainer, study, runDAO);
        final StudyViewModel studyModel = builder.getModel();
        studyModel.changeToHighlightedClass(ViewModel.TAB_CLASS_PROJECTS_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(StudyViewModel.MODEL_ATTR_NAME, studyModel);
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (entity != null && entity instanceof Study) {
            result.add(new Breadcrumb("Project: " + ((Study) entity).getStudyName(), "View project " + ((Study) entity).getStudyName(), "projects/" + ((Study) entity).getStudyId()));
        }
        return result;
    }
}
