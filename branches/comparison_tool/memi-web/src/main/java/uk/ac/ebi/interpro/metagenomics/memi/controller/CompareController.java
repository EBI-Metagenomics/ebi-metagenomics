package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.ComparisonForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the controller for the MG portal home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller(value = "compareController")
@RequestMapping('/' + CompareController.VIEW_NAME)
public class CompareController extends AbstractController implements IController {

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "compare";


    @Resource
    private SampleDAO sampleDAO;
    @Resource
    private StudyDAO studyDAO;

    @Override
    public ModelAndView doGet(ModelMap model) {
        return buildModelAndView(
                getModelViewName(),
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Compare samples of same project", getBreadcrumbs(null), propertyContainer);
                        final ViewModel defaultViewModel = builder.getModel();
                        defaultViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_COMPARE_VIEW);

                        // Retrieving list of public studies and samples + add attributes
                        //List<Study> studyList = studyDAO.retrieveOrderedPublicStudies("STUDY_NAME",true);
                        List<Study> studyList = studyDAO.retrievePublicStudies();
                        List<Sample> sampleList = sampleDAO.retrieveAllPublicSamples();
                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                        model.addAttribute("studies",studyList);
                        //model.addAttribute("samples",sampleList);
                        model.addAttribute("comparisonForm",new ComparisonForm());
                    }
                });
    }

    @RequestMapping(value = "/samples")
    public ModelAndView getSamplesByID(
            @RequestParam(value = "studyId", required = true) final long studyId
    ){
        ModelAndView mav = new ModelAndView("/compare-samples");
        List<Sample> sampleListForId = sampleDAO.retrieveAllSamplesByStudyId(studyId);
        mav.addObject("samples",sampleListForId);
        return mav;
    }

    @RequestMapping(value = "/studies")
    public ModelAndView getStudyDescription(
            @RequestParam(value = "studyId", required = true) final String studyId
    ){
        ModelAndView mav = new ModelAndView("/compare-studies");
        Study currentStudy = studyDAO.readByStringId(studyId);
        mav.addObject("study",currentStudy);
        return mav;
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Comparison tool", "Compare samples of same project", VIEW_NAME));
        return result;
    }
}