package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.basic.SampleTypeEditor;
import uk.ac.ebi.interpro.metagenomics.memi.basic.SampleVisibilityEditor;
import uk.ac.ebi.interpro.metagenomics.memi.basic.VelocityTemplateWriter;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewSamplesModel;
import uk.ac.ebi.interpro.metagenomics.memi.tools.MemiTools;

import javax.annotation.Resource;
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
public class ViewSamplesController extends AbstractController implements IMGController {

    private final static Log log = LogFactory.getLog(ViewSamplesController.class);

    /* The maximum allowed number of characters per column within the study list table*/
    private final int MAX_CHARS_PER_COLUMN = 35;

    /**
     * View name of this controller which is used several times.
     */
    public final static String VIEW_NAME = "samples";

    private final String VELOCITY_TEMPLATE_LOCATION_PATH = "WEB-INF/velocity_templates/exportSamples.vm";

    @Resource
    private HibernateSampleDAO sampleDAO;

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
    public ModelAndView doExportSampleTable(@ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter,
                                            @RequestParam(required = false) final SampleFilter.SampleVisibility sampleVisibility,
                                            @RequestParam(required = false) final String searchTerm,
                                            @RequestParam(required = false) final Sample.SampleType sampleType,
                                            HttpServletResponse response) {
        log.info("Requesting exportSamples (GET method)...");

        final ModelMap model = new ModelMap();
        processRequestParams(filter, searchTerm, sampleVisibility, sampleType);
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
            String fileName = MemiTools.createFileName("mg_samples_");
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, file, fileName, true);
            }
        }
        return new ModelAndView(VIEW_NAME, model);
    }


    /**
     * Handles the export of a more detailed export file.
     */
    @RequestMapping(value = "doExportDetails", method = RequestMethod.GET)
    public ModelAndView doExportDetails(@ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter,
                                        @RequestParam(required = false) final SampleFilter.SampleVisibility sampleVisibility,
                                        @RequestParam(required = false) final String searchTerm,
                                        @RequestParam(required = false) final Sample.SampleType sampleType,
                                        HttpServletResponse response) {
        log.info("Requesting exportSamples of ViewSamplesController...");

        final ModelMap model = new ModelMap();
        processRequestParams(filter, searchTerm, sampleVisibility, sampleType);
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

    @RequestMapping(params = "search", value = "doSearch", method = RequestMethod.GET)
    public ModelAndView doSearch(@ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter,
                                 @RequestParam(required = false) final SampleFilter.SampleVisibility sampleVisibility,
                                 @RequestParam(required = false) final String searchTerm,
                                 @RequestParam(required = false) final Sample.SampleType sampleType,
                                 ModelMap model) {
        log.info("Requesting doSearch of " + ViewSamplesController.class + "...");

        processRequestParams(filter, searchTerm, sampleVisibility, sampleType);
        populateModel(model, filter);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewSamplesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    private void processRequestParams(SampleFilter filter, String searchTerm,
                                      SampleFilter.SampleVisibility sampleVisibility,
                                      Sample.SampleType sampleType) {
        //Set filter parameters
        filter.setSampleType(sampleType);
        filter.setSearchTerm(searchTerm);

        //The visibility parameter can only be set if a user is logged in, means a session object exists
        if (sessionManager.getSessionBean().getSubmitter() != null) {
            filter.setSampleVisibility(sampleVisibility);
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
        final ViewSamplesModel subModel = MGModelFactory.getViewSamplesPageModel(sessionManager,
                sampleDAO, filter, "Metagenomics View Samples", getBreadcrumbs(null), propertyContainer);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, subModel);
    }

    private List<String> getSampleProperties() {
        List<String> result = new ArrayList<String>();
        result.add("SAMPLE_NAME");
        result.add("PROJECT_NAME");
        result.add("SOURCE");
        result.add("ANALYSIS");
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

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Samples", "View samples", VIEW_NAME));
        return result;
    }
}
