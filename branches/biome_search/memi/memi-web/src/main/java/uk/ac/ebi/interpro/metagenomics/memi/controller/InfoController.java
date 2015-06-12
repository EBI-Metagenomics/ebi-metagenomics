package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a controller for the info page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping(value = "/" + InfoController.VIEW_NAME)
public class InfoController extends AbstractController implements IController {
    private final Log log = LogFactory.getLog(InfoController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "about";

    @Override
    public ModelAndView doGet(final ModelMap model) {
        log.info("Requesting doGet of InfoController...");
        //build and add the page model
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewModel) model.get(ViewModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(final ModelMap model) {
        log.info("Building model of InfoController...");
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "About EBI metagenomics", getBreadcrumbs(null), propertyContainer);
        final ViewModel infoModel = builder.getModel();
        infoModel.changeToHighlightedClass(ViewModel.TAB_CLASS_ABOUT_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, infoModel);
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("About", "About the Metagenomics portal", VIEW_NAME));
        return result;
    }
}
