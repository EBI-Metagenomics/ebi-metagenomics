package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.ebeyesearch.*;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.search.TextSearch;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Perform a search.
 *
 * @author Matthew Fraser, EMBL-EBI, InterPro
 * @version $Id: SearchController.java,v 1.19 2013/03/19 14:58:23 matthew Exp $
 * @since 1.0-SNAPSHOT
 */
@Controller
public class SearchController extends AbstractController implements IController {

    private static final Logger LOGGER = Logger.getLogger(SearchController.class.getName());

    TextSearch textSearch;

    @RequestMapping("/search")
    public ModelAndView restfulSearch(@RequestParam(value = "q", required = true) String query,
                                      @RequestParam(value = "start", required = false, defaultValue = "0") final int startIndex,
                                      final ModelMap model)
            throws IOException {
        //Set default domain
        Domain domain = Domain.SRA_SAMPLE;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Perform search with query: " + query);
        }

        //e.g. PRJNA168081, PRJEB3401
        if (query.startsWith("SRP") || query.startsWith("DRP") || query.startsWith("ERP") || query.startsWith("PRJ")) {
            domain = Domain.SRA_STUDY;
        }

        if (!domain.equals(Domain.SRA_STUDY) && !query.startsWith("SRS") && !query.startsWith("ERS") && !query.startsWith("TAXONOMY:(")) {
            query = '(' + query + " AND TAXONOMY:(408170 OR 408171 OR 410656 OR 410661 OR 410662 OR 433724 OR 433733 OR 444079 OR 447426 OR 483425 OR 496922 OR 496923 OR 496924 OR 506599 OR 506600 OR 539655 OR 540485 OR 646099 OR 662107 OR 703336 OR 749906 OR 910258 OR 1006967 OR 1009879 OR 1041057 OR 1070528 OR 1076140 OR 1077527 OR 1077528 OR 1077529 OR 1115523 OR 1118232 OR 1130098 OR 1131769 OR 1163772 OR 1176744 OR 1202446 OR 1204294 OR 1213622 OR 1218275 OR 1227552 OR 1234904 OR 1284369 OR 1297858 OR 1300146 OR 1335591 OR 1338477))";
        }

//        super.userSessionData.setCurrentQuery(query);
        final String ES_SEARCH_WEBSERVICE_URL = "http://www.ebi.ac.uk/ebisearch/service.ebi";
        InterProSearchFormBean searchFormBean = null;
        String errorMessage = null;
        try {
            TextSearch textSearch = new TextSearch(ES_SEARCH_WEBSERVICE_URL);
            searchFormBean = textSearch.search(query, startIndex, 10, true, domain.getDatabaseName());
        } catch (Exception ex) {
            errorMessage = ex.getLocalizedMessage();
            LOGGER.error("Text search threw exception: " + errorMessage);
        }

        if (searchFormBean == null) {
            // An error occurred when performing a text search, perhaps the external services web service failed?
            // Construct a basic (empty) results page
            searchFormBean = new InterProSearchFormBean(query, // User entered query
                    null, // Query without facets
                    0, // Result count
                    0, // Count without facets
                    0, // Current page number
                    new ArrayList<InterProSearchResult>(), // No results
                    new ArrayList<LinkInfoBean>(), // No pagination links
                    new ArrayList<Facet>(), // No facets
                    errorMessage); // Error message if an exception was thrown
            LOGGER.error("Text search failed, check logs, check query: " + query +
                    ", check the ES search web service is OK: " + ES_SEARCH_WEBSERVICE_URL);
        }
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewModel) model.get(ViewModel.MODEL_ATTR_NAME)).getLoginForm());
        //Finally build the model and view
        ModelAndView mav = new ModelAndView("searchResult", model);
        mav.addObject("search", searchFormBean);
        mav.addObject("domain", domain.getDatabaseName());
        return mav;
    }

    private void populateModel(final ModelMap model) {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Search result EBI metagenomics", getBreadcrumbs(null), propertyContainer);
        final ViewModel searchResultModel = builder.getModel();
        searchResultModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, searchResultModel);
    }

    @Override
    protected String getModelViewName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected List<Breadcrumb> getBreadcrumbs(SecureEntity obj) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ModelAndView doGet(ModelMap model) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
