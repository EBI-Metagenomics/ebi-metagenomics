package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles registration form requests..
 *
 * @author Maxim Scheremetjew
 */
@Controller
@RequestMapping("/submission")
public class SubmissionController extends AbstractController {

    private final static Log log = LogFactory.getLog(SubmissionController.class);

    @Resource
    private SubmissionContactDAO submissionContactDAO;

    @RequestMapping
    public ModelAndView doGet(final ModelMap modelMap) {
        // put your initial command
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(userManager, getEbiSearchForm(),
                "Submit data", getBreadcrumbs(null), propertyContainer);
        final ViewModel submitDataModel = builder.getModel();
        submitDataModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SUBMIT_VIEW);
        //Add submitter details (e.g. registration info) to the model if user is logged in
        //Check if user is logged in
        Submitter submitter = submitDataModel.getSubmitter();
        if (submitter != null) {//If logged in
            final String submitterAccountId = submitter.getSubmissionAccountId();
            //Retrieve submitter details for the private area
            Submitter submitterDetails = submissionContactDAO.getSubmitterBySubmissionAccountId(submitterAccountId);
            modelMap.addAttribute("submitterDetails", submitterDetails);
        }//Otherwise do nothing

        //return new ModelAndView("submission-check/intro", modelMap);
        return buildModelAndView(
                "submission-check/intro",
                modelMap,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        modelMap.addAttribute(ViewModel.MODEL_ATTR_NAME, submitDataModel);
                    }
                }
        );
    }

    @Override
    protected String getModelViewName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Submit data", "Submit new data", "submission"));
        return result;
    }
}