package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.CountryDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.RegistrationForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SRARegistrationForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SRARegistrationModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SRARegistrationViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles registration form requests..
 *
 * @author Maxim Scheremetjew
 */
@Controller
@RequestMapping("/submit")
public class NewRegistrationController extends AbstractController {

    private final static Log log = LogFactory.getLog(NewRegistrationController.class);

    @RequestMapping
    public ModelAndView doGet(final @ModelAttribute("registrationForm") @Valid RegistrationForm form,
                              final Errors errors,
                              final ModelMap modelMap) {
        // put your initial command
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Submit data", getBreadcrumbs(null), propertyContainer);
        final ViewModel submitDataModel = builder.getModel();
        submitDataModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SUBMIT_VIEW);
        modelMap.addAttribute(ViewModel.MODEL_ATTR_NAME, submitDataModel);

        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        modelMap.addAttribute("registrationForm", new RegistrationForm());
        modelMap.addAttribute("displayUsernameBox", "none");
        // populate the model Map as needed
        return new ModelAndView("user-registration/registIntro", modelMap);
    }

    @Override
    protected String getModelViewName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Submit data", "Submit new data", "submit"));
        return result;
    }
}