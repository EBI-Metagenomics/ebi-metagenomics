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
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.StudyViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.StudyViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;

/**
 * The controller for the analysis results page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Controller
public class OverviewStudyViewController extends AbstractStudyViewController{
    private static final Log log = LogFactory.getLog(OverviewStudyViewController.class);

    @Resource
    private RunDAO runDAO;

    protected String getModelViewName() {
        return "tabs/study/overview";
    }


    private ModelProcessingStrategy<Study> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Study>() {
            @Override
            public void processModel(ModelMap model, Study study) {
                log.info("Building overview model...");
                populateModel(model, study);
            }
        };
    }


    /**
     * Request method for the overview tab on the project page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_OVERVIEW)
    public ModelAndView ajaxLoadOverviewTab(@PathVariable final String studyId,
                                            final ModelMap model) {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(studyId), getModelViewName());
    }

    /**
     * Creates the analysis page model and adds it to the specified model map.
     */
    protected void populateModel(final ModelMap model, final Study study) {
        String pageTitle = "Project overview: " + study.getStudyName() + "";
        final ViewModelBuilder<StudyViewModel> builder = new StudyViewModelBuilder(sessionManager,
                pageTitle, getBreadcrumbs(study), propertyContainer, study, runDAO);
        final StudyViewModel studyModel = builder.getModel();
        studyModel.changeToHighlightedClass(ViewModel.TAB_CLASS_PROJECTS_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(StudyViewModel.MODEL_ATTR_NAME, studyModel);
    }
}