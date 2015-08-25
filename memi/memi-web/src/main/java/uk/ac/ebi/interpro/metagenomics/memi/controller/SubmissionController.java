package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.forms.ConsentCheckForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.validation.Valid;
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

    @RequestMapping
    public ModelAndView doGet(final ModelMap modelMap) {
        // put your initial command
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Submit data", getBreadcrumbs(null), propertyContainer);
        final ViewModel submitDataModel = builder.getModel();
        submitDataModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SUBMIT_VIEW);
        modelMap.addAttribute(ViewModel.MODEL_ATTR_NAME, submitDataModel);

        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return new ModelAndView("submission-check/intro", modelMap);
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