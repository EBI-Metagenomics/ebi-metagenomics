package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Represents a error controller, which currently holds request mappings
 * for error pages that need no separate controller class.
 * <p>
 * As all pages share the menu including menu function like e.g. login, they need specific attributes for successful rendering (e.g. LoginForm). The 404 error request is specified in the web.xml.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class ErrorController {
    private static final Log log = LogFactory.getLog(ErrorController.class);

    @Resource
    private UserManager userManager;

    @Autowired
    private EBISearchForm ebiSearchForm;

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public ModelAndView doGetPageNotFoundMAV(ModelMap model) {
        log.warn("This method is called twice times at the moment. If you don't find any Spring related " +
                "log to no such request mapping found, please do not note this warning. HTTP status code " +
                "404 responded. Please have a look to the Spring log to see which wrong URL was " +
                "requested!");
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(userManager, ebiSearchForm,
                "404 Document Not Found", new ArrayList<Breadcrumb>(), null);
        final ViewModel viewModel = builder.getModel();
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, viewModel);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(EBISearchForm.MODEL_ATTR_NAME, ebiSearchForm);
        return new ModelAndView("/errors/404", model);
    }
}