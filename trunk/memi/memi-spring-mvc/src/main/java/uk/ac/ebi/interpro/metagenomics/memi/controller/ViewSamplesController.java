package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.basic.SampleVisibilityEditor;
import uk.ac.ebi.interpro.metagenomics.memi.basic.SampleTypeEditor;
import uk.ac.ebi.interpro.metagenomics.memi.basic.VelocityTemplateWriter;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewSamplesModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;
import uk.ac.ebi.interpro.metagenomics.memi.tools.MemiTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the controller for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/" + ViewSamplesController.VIEW_NAME)
public class ViewSamplesController implements IMGController {

    private final Log log = LogFactory.getLog(ViewSamplesController.class);

    /* The maximum allowed number of characters per column within the study list table*/
    private final int MAX_CHARS_PER_COLUMN = 35;

    /**
     * View name of this controller which is used several times.
     */
    public final static String VIEW_NAME = "samples";

    private final String VELOCITY_TEMPLATE_LOCATION_PATH = "WEB-INF/velocity_templates/exportSamples.vm";

    private final String DOWNLOAD_FILE_NAME = "mg_samples.csv";

    @Resource
    private HibernateSampleDAO sampleDAO;

    @Resource
    private SessionManager sessionManager;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @Override
    public ModelAndView doGet(ModelMap model) {
        log.info("Requesting doGet...");
        //build and add the page model
        populateModel(model, new SampleFilter());
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewSamplesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        model.addAttribute(SampleFilter.MODEL_ATTR_NAME, ((ViewSamplesModel) model.get(MGModel.MODEL_ATTR_NAME)).getSampleFilter());
        return new ModelAndView(VIEW_NAME, model);
    }


    /**
     * Handles the export of the samples table.
     */
    @RequestMapping(value = "doExportTable", method = RequestMethod.GET)
    public ModelAndView doExportSampleTable(HttpServletRequest request, HttpServletResponse response, @ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter) {
        log.info("Requesting exportSamples (GET method)...");

        ModelMap model = new ModelMap();
        processRequestParams(request, filter);
        populateModel(model, filter);
        List<Sample> samples = ((ViewSamplesModel) model.get(MGModel.MODEL_ATTR_NAME)).getSamples();

        if (samples != null && samples.size() > 0) {
            //Create velocity spring_model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            velocityModel.put("sampleProperties", getSampleProperties());
            velocityModel.put("samples", samples);
            velocityModel.put("columnLength", MAX_CHARS_PER_COLUMN);
            //Create file content
            String fileContent = VelocityTemplateWriter.createFileContent(velocityEngine, VELOCITY_TEMPLATE_LOCATION_PATH, velocityModel);
            File file = MemiFileWriter.writeCSVFile(fileContent);
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, file, DOWNLOAD_FILE_NAME, true);
            }
        }
        return new ModelAndView(VIEW_NAME, model);
    }

    /**
     * Handles the export of a more detailed export file.
     */
    @RequestMapping(value = "doExportDetails", method = RequestMethod.GET)
    public ModelAndView doExportDetails(HttpServletRequest request, HttpServletResponse response, @ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter) {
        log.info("Requesting exportSamples (GET method)...");

        ModelMap model = new ModelMap();
        processRequestParams(request, filter);
        populateModel(model, filter);
        List<Sample> samples = ((ViewSamplesModel) model.get(MGModel.MODEL_ATTR_NAME)).getSamples();
        if (samples != null && samples.size() > 0) {
            String[] samplesIDs = MemiTools.getSampleIds(samples);
            if (downloadService != null) {
                boolean isDialogOpen = downloadService.openDownloadDialog(response, samples.get(0).getClazz(), samplesIDs);
                model.addAttribute("isDialogOpen", isDialogOpen);
            } else {
                log.warn("Could not open download dialog to export samples. Download service is null!");
            }
        } else {
            log.info("There are no samples to be exported!");
        }
        return new ModelAndView(VIEW_NAME, model);
    }

//    @Override
//    @RequestMapping(method = RequestMethod.POST)
//    public ModelAndView doProcessLogin(@ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm, BindingResult result,
//                                       ModelMap model, SessionStatus status) {
//        log.info("Requesting doProcessLogin (POST method)...");
//        //process login
//        super.processLogin(loginForm, result, model, status);
//        //create model and view
//        populateModel(model, new SampleFilter());
//        model.addAttribute(SampleFilter.MODEL_ATTR_NAME, ((ViewSamplesModel) model.get(MGModel.MODEL_ATTR_NAME)).getSampleFilter());
//        return new ModelAndView(VIEW_NAME, model);
//    }

    @RequestMapping(params = "search", value = "doSearch", method = RequestMethod.GET)
    public ModelAndView doSearch(HttpServletRequest request, @ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter, ModelMap model) {
        log.info("Requesting doSearch (POST method)...");

        processRequestParams(request, filter);
        populateModel(model, filter);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewSamplesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    /**
     * Values are set to an empty string for the search term and a public visibility.
     */
    @RequestMapping(params = "clear", value = "doSearch", method = RequestMethod.GET)
    public ModelAndView doClearSearch(ModelMap model, @ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter) {
        log.info("Requesting doClear (POST method)...");

        filter.setSearchTerm("");
        filter.setSampleVisibility(SampleFilter.SampleVisibility.ALL_PUBLISHED_SAMPLES);
        filter.setSampleType(null);
        populateModel(model, new SampleFilter());
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewSamplesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    private void processRequestParams(HttpServletRequest request, SampleFilter filter) {
        //Get URL parameter of GET request
        String sampleVisibility = request.getParameter("sampleVisibility");
        String searchTerm = request.getParameter("searchTerm");
        String sampleType = request.getParameter("sampleType");

        //Set parameter study type
        if (sampleType != null && sampleType.trim().length() > 0) {
            try {
                Sample.SampleType type = Sample.SampleType.valueOf(sampleType);
                if (type != null) {
                    filter.setSampleType(type);
                }
            } catch (Exception e) {
                log.warn("Could not find any sample type value for name: " + sampleType);
                filter.setSampleType(null);
            }
        }

        //Set parameters to the filter
        if (searchTerm != null && searchTerm.trim().length() > 0) {
            filter.setSearchTerm(searchTerm);
        }
        //The visibility parameter can only be set if a user is logged in, means a session object exists
        if (sessionManager.getSessionBean().getSubmitter() != null && sampleVisibility != null && sampleVisibility.trim().length() > 0) {
            SampleFilter.SampleVisibility vis = SampleFilter.SampleVisibility.valueOf(sampleVisibility);
            if (vis != null) {
                filter.setSampleVisibility(vis);
            }
        } else {
            filter.setSampleVisibility(SampleFilter.SampleVisibility.ALL_PUBLISHED_SAMPLES);
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(SampleFilter.SampleVisibility.class, "sampleVisibility", new SampleVisibilityEditor());
        binder.registerCustomEditor(Sample.SampleType.class, "sampleType", new SampleTypeEditor());
    }

    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model, SampleFilter filter) {
        final ViewSamplesModel subModel = MGModelFactory.getViewSamplesPageModel(sessionManager, sampleDAO, filter);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, subModel);
    }

    private List<String> getSampleProperties() {
        List<String> result = new ArrayList<String>();
        result.add("SAMPLE_ID");
        result.add("SAMPLE_NAME");
        result.add("COLLECTION_DATE");
        result.add("VALID_METADATA");
        result.add("VALID_RAW_DATA");
        result.add("ANALYSIS_COMPLETED");
        result.add("ARCHIVED_IN_ENA");

        return result;
    }

    /**
     * Generates the page title subject to the value of the sample filter.
     */
    @ModelAttribute(value = "pageTitle")
    public String getPageTitle(@ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter) {
        SampleFilter.SampleVisibility vis = filter.getSampleVisibility();
        if (vis != null) {
            if (vis.equals(SampleFilter.SampleVisibility.MY_SAMPLES)) {
                return "My samples (published and pre-published)";
            } else if (vis.equals(SampleFilter.SampleVisibility.MY_PUBLISHED_SAMPLES)) {
                return "My published samples";
            } else if (vis.equals(SampleFilter.SampleVisibility.MY_PREPUBLISHED_SAMPLES)) {
                return "My pre-published samples";
            } else if (vis.equals(SampleFilter.SampleVisibility.ALL_PUBLISHED_SAMPLES)) {
                return "All published samples";
            } else if (vis.equals(SampleFilter.SampleVisibility.ALL_SAMPLES)) {
                return "All published and my pre-published samples";
            }
        }
        return "List of samples";
    }
}