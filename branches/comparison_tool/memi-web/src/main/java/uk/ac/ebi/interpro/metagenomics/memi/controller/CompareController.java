package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.ComparisonForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    @Resource
    protected Map<String, DownloadableFileDefinition> fileDefinitionsMap;

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
                        List<Study> studyList = studyDAO.retrieveOrderedPublicStudies("studyName",false);
                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                        model.addAttribute("studies",studyList);
                        model.addAttribute("comparisonForm",new ComparisonForm());
                    }
                });
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doPostComparisonToolForm(@Valid @ModelAttribute("comparisonForm") final ComparisonForm comparisonForm,
                                                 BindingResult result,
                                                 ModelMap model) {
        if (result.hasErrors()) {
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
                            List<Study> studyList = studyDAO.retrieveOrderedPublicStudies("studyName",false);
                            model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                            model.addAttribute("studies",studyList);
                            model.addAttribute("comparisonForm",comparisonForm);
                        }
                    });
        }
        String usedData = comparisonForm.getUsedData();
        String visualisation = comparisonForm.getUsedVis();
        //TODO: 1. Retrieve input files for abundance tables creation
        //TODO: We need to implement different cases depending on the data request (GO, GO_SLIM, IPR etc.)
        List<String> inputFilePaths = new ArrayList<String>();
        for(Long sampleId : comparisonForm.getSamples())
        {
            final EmgFile emgFile = getEmgFile(sampleId);
            if (emgFile != null) {
                DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_SLIM_FILE.name());
                File fileObject = FileObjectBuilder.createFileObject(emgFile, propertyContainer, fileDefinition);
                String absoluteFilePath = fileObject.getAbsolutePath();
                inputFilePaths.add(absoluteFilePath);
            }
        }

        //TODO: 2. Launch R script (abundance tables creation script)

        //TODO: 3. Check status of R script (RUNNING or FINISHED)

        //TODO: 4. Consume abundance file and render the page

        return null;
    }

        @RequestMapping(value = "/samples")
    public ModelAndView getSamplesByID(
            @RequestParam(value = "studyId", required = true) final long studyId
    ){
        ModelAndView mav = new ModelAndView("/compare-samples");
        List<Sample> sampleListForId = sampleDAO.retrievePublicSamplesByStudyId(studyId);
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