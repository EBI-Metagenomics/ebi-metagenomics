package uk.ac.ebi.interpro.metagenomics.memi.controller;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ebiSearch.EBISearchResults;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SearchViewModelBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by maq on 17/03/2016.
 */
@Controller
@RequestMapping(value = "/" + SearchController.VIEW_NAME)
public class SearchController extends AbstractController {
    private final Log log = LogFactory.getLog(SearchController.class);

    public static final String VIEW_NAME = "search";

    public static final String VIEW_SEARCH = "doEbiSearch";
    public static final String VIEW_AJAX = "doAjaxSearch";

    private static final String SEARCH_TERM = "searchText";

    public SearchController() {}

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doEbiSearch(ModelMap model, HttpServletRequest request,
                                    HttpServletResponse response) {

        return buildModelAndView(
                getModelViewName(),
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        log.info("Building model of " + this.getClass() + "...");
                        final SearchViewModelBuilder<ViewModel> builder = new SearchViewModelBuilder(
                                userManager,
                                null,
                                "Search EBI metagenomics",
                                getBreadcrumbs(null),
                                propertyContainer,
                                null
                        );
                        final ViewModel searchModel = builder.getModel();
                        searchModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SEARCH_VIEW);
                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, searchModel);
                    }
                }
        );
    }

    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(final ModelMap model,
                               EBISearchForm ebiSearchForm,
                               String searchResults
    ) {
        log.info("Building model of " + this.getClass() + "...");
        final SearchViewModelBuilder<ViewModel> builder = new SearchViewModelBuilder(
                userManager,
                getEbiSearchForm(),
                "Search EBI metagenomics",
                getBreadcrumbs(null),
                propertyContainer,
                searchResults
        );
        final ViewModel searchModel = builder.getModel();
        searchModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SEARCH_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, searchModel);
        model.addAttribute(EBISearchForm.MODEL_ATTR_NAME, ebiSearchForm);
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
