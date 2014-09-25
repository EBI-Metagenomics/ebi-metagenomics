package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.ISampleStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.StudyViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.StudyViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents the controller for the study (project) overview page. The use of terms project for study has a historical background.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/" + StudyViewController.VIEW_NAME + "/{studyId}")
public class StudyViewController extends SecuredAbstractController<Study> {

    private final static Log log = LogFactory.getLog(StudyViewController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "project";

    @Resource
    private StudyDAO studyDAO;

    @Resource
    private SampleDAO sampleDAO;

    @Resource
    private SubmissionContactDAO submissionContactDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    //GET Methods

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGetStudy(final ModelMap model, @PathVariable final String studyId) {
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Study>() {
            @Override
            public void processModel(ModelMap model, Study study) {
                populateModel(model, study);
            }
        }, model, studyId, getModelViewName());
    }

    @RequestMapping(value = "/doExport", method = RequestMethod.GET)
    public ModelAndView doExportSamples(final ModelMap model, final HttpServletResponse response, @PathVariable final String studyId) throws Exception {
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Study>() {
            @Override
            public void processModel(ModelMap model, Study study) {
                populateModel(model, study);

                List<Sample> samples = ((StudyViewModel) model.get(StudyViewModel.MODEL_ATTR_NAME)).getSamples();
                if (samples != null && samples.size() > 0) {
                    Set<String> samplesIDs = MemiTools.getSampleIds(samples);
                    Class clazz = MemiTools.getTypeOfGenericSet(samples);
                    if (downloadService != null) {
                        boolean isDialogOpen = downloadService.openDownloadDialog(response, clazz, samplesIDs);
                        model.addAttribute("isDialogOpen", isDialogOpen);
                    } else {
                        log.warn("Could not open download dialog to export samples. Download service is null!");
                    }
                } else {
                    log.info("There are no samples to be exported!");
                }
            }
        }, model, studyId, getModelViewName());
    }

    /**
     * Creates a {@link StudyViewModel} and adds it to the specified model map.
     */

    private void populateModel(final ModelMap model, final Study study) {
//        List<Sample> samples = sampleDAO.retrieveSamplesByStudyId(study.getId());
        String pageTitle = "Project overview: " + study.getStudyName() + "";
        final ViewModelBuilder<StudyViewModel> builder = new StudyViewModelBuilder(sessionManager,
                pageTitle, getBreadcrumbs(study), propertyContainer, study, sampleDAO,submissionContactDAO);
        final StudyViewModel studyModel = builder.getModel();
        studyModel.changeToHighlightedClass(ViewModel.TAB_CLASS_PROJECTS_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(StudyViewModel.MODEL_ATTR_NAME, studyModel);
    }

    ISampleStudyDAO<Study> getDAO() {
        return studyDAO;
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (entity != null && entity instanceof Study) {
            result.add(new Breadcrumb("Project: " + ((Study) entity).getStudyName(), "View project " + ((Study) entity).getStudyName(), VIEW_NAME + '/' + ((Study) entity).getStudyId()));
        }
        return result;
    }
}
