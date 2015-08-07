package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.MGPortalURLCollection;
import uk.ac.ebi.interpro.metagenomics.memi.controller.ModelProcessingStrategy;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.AnalysisJobDAO;
import uk.ac.ebi.interpro.metagenomics.memi.exceptionHandling.EntryNotFoundException;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.DownloadViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study.StudyDownloadViewModelBuilder;

import javax.annotation.Resource;

/**
 * Download tab on the project page.
 */
@Controller
public class DownloadStudyController extends AbstractStudyViewController {

    private static final Log log = LogFactory.getLog(DownloadStudyController.class);

    @Resource
    protected AnalysisJobDAO analysisJobDAO;

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
                study);

        final DownloadViewModel downloadViewModel = builder.getModel();
        downloadViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_PROJECTS_VIEW); // Not really needed as this is within an AJAX tab anyway?
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm()); // Not really needed as this is within an AJAX tab anyway?
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, downloadViewModel);

    }
}
