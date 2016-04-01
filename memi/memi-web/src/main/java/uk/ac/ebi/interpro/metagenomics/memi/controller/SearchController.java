package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.EBISearchTool;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISampleSearchResults;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SearchViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SearchViewModelBuilder;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maq on 17/03/2016.
 */
@Controller
@RequestMapping(value = "/" + SearchController.VIEW_NAME)
public class SearchController extends AbstractController implements IController {
    private final Log log = LogFactory.getLog(SearchController.class);

    public static final String VIEW_NAME = "search";

    public static final String VIEW_SEARCH = "doEbiSearch";

    public EBISearchTool ebiSearchTool;

    public SearchController() {
        ebiSearchTool = new EBISearchTool();
    }

    @Override
    public ModelAndView doGet(final ModelMap model) {
        log.info("Requesting doGet of SearchController...");
        //build and add the page model
        populateModel(model, new EBISearchForm(), null, new LoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    @RequestMapping(value = "/" + SearchController.VIEW_SEARCH)
    public ModelAndView doEbiSearch(@Valid @ModelAttribute(EBISearchForm.MODEL_ATTR_NAME) final EBISearchForm ebiSearchForm,
                                    BindingResult result,
                                    ModelMap model) {
        log.info("Requesting doEbiSearch of SearchController...");

        EBISampleSearchResults sampleSearchResults = ebiSearchTool.searchSamples(ebiSearchForm);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        populateModel(model, ebiSearchForm, sampleSearchResults, new LoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(final ModelMap model,
                               EBISearchForm ebiSearchForm,
                               EBISampleSearchResults sampleSearchResults,
                               LoginForm loginForm
    ) {
        log.info("Building model of InfoController...");
        final SearchViewModelBuilder<ViewModel> builder = new SearchViewModelBuilder(
                sessionManager,
                "Search EBI metagenomics",
                getBreadcrumbs(null),
                propertyContainer,
                ebiSearchForm,
                sampleSearchResults);
        final ViewModel searchModel = builder.getModel();
        searchModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SEARCH_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, searchModel);
        model.addAttribute(EBISearchForm.MODEL_ATTR_NAME, ((SearchViewModel) searchModel).getEbiSearchForm());
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, loginForm);
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Search", "Search the Metagenomics portal", VIEW_NAME));
        return result;
    }
}
