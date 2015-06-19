package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SRARegistrationViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
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
@RequestMapping("/register")
public class NewRegistrationController extends AbstractController implements IController {

    private final static Log log = LogFactory.getLog(NewRegistrationController.class);

    @RequestMapping
    public ModelAndView doGet(final ModelMap modelMap) {
        // put your initial command
        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        // populate the model Map as needed
        return new ModelAndView("user-registration/registIntro", modelMap);
    }

    @Override
    protected String getModelViewName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public static final String VIEW_NAME = "submit";
    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Submit data", "Submit new data", VIEW_NAME));
        return result;
    }
}