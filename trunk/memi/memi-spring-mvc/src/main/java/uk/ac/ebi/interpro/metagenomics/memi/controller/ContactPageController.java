package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the controller for the MG portal home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller(value = "contactPageController")
@RequestMapping('/' + ContactPageController.VIEW_NAME)
public class ContactPageController extends AbstractController implements IMGController {

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "contact";

    @Override
    public ModelAndView doGet(ModelMap model) {
        //build and add the default page model
        populateModel(model);
        return new ModelAndView(VIEW_NAME, model);
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     *
     * @param model The model map to populate
     */
    private void populateModel(final ModelMap model) {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Metagenomics Contact", getBreadcrumbs(null), propertyContainer);
        final ViewModel defaultViewModel = builder.getModel();
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Contact", "Contact us", VIEW_NAME));
        return result;
    }
}