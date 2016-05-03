package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.core.SampleVisibilityEditor;
import uk.ac.ebi.interpro.metagenomics.memi.core.VelocityTemplateWriter;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SamplesViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SamplesViewHelper;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SamplesViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Represents the controller for the samples view page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/" + ViewSamplesController.VIEW_NAME)
public class ViewSamplesController extends AbstractController implements IController {

    private final static Log log = LogFactory.getLog(ViewSamplesController.class);

    /* The maximum allowed number of characters per column within the study list table*/
    private final int MAX_CHARS_PER_COLUMN = 35;

    /**
     * View name of this controller which is used several times.
     */
    public final static String VIEW_NAME = "samples";

    private final String VELOCITY_TEMPLATE_LOCATION_PATH = "exportSamples.vm";

    @Resource
    private SampleDAO sampleDAO;

    @Resource
    private BiomeDAO biomeDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @Override
    public ModelAndView doGet(ModelMap model) {
        log.info("Requesting doGet...");
        //build and add the page model
        populateModel(model, new SampleFilter(), 0);
        return buildModelAndView(
                getModelViewName(),
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        model.addAttribute(SampleFilter.MODEL_ATTR_NAME, ((SamplesViewModel) model.get(ViewModel.MODEL_ATTR_NAME)).getSampleFilter());
                    }
                }
        );
    }

    protected void doHandleSampleExport(final SampleFilter filter,
                                        final HttpServletResponse response,
                                        final HttpServletRequest request) throws IOException, HibernateException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setLocale(Locale.ENGLISH);

        //Get submitter account ID if exists
        Submitter submitter = getSessionSubmitter();
        String submissionAccountId = (submitter != null ? submitter.getSubmissionAccountId() : null);
        //Create sample filter criteria
        List<Criterion> filterCriteria = SamplesViewHelper.buildFilterCriteria(filter, submissionAccountId, biomeDAO);
        //Get list of filtered samples which will be provided for download
        List<Sample> downloadableSamples = sampleDAO.retrieveFilteredSamples(filterCriteria, "sampleName");

        if (downloadableSamples != null && downloadableSamples.size() > 0) {
            //Create velocity spring_model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            velocityModel.put("sampleProperties", getTableHeaderNames());
            velocityModel.put("samples", downloadableSamples);
            velocityModel.put("columnLength", MAX_CHARS_PER_COLUMN);
            //Create file content
            String fileContent = VelocityTemplateWriter.createFileContent(velocityEngine, VELOCITY_TEMPLATE_LOCATION_PATH, velocityModel);
            File file = MemiFileWriter.writeCSVFile(fileContent);
            String fileName = MemiTools.createFileName("mg_samples_");
            if (file != null && file.canRead()) {
                try {
                    //Open download dialog
                    downloadService.openDownloadDialog(response, request, file, fileName, true);
                } catch (IndexOutOfBoundsException e) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {//If no temp file has been writen
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {//If list of samples is NULL or zero
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    /**
     * Handles the export of the samples table.
     */
    @RequestMapping(value = "doExportTable")
    public void doExportSampleTable(@ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter,
//                                    @RequestParam(required = false) final SampleFilter.SampleVisibility sampleVisibility,
//                                    @RequestParam(required = false) final String searchTerm,
//                                    @RequestParam(required = true) final int startPosition,
                                    final HttpServletResponse response,
                                    final HttpServletRequest request) throws IOException, HibernateException {
        log.info("Exporting filtered samples of the sample table...");
        doHandleSampleExport(filter, response, request);
    }


    /**
     * Handles the export of a more detailed export file.
     */
    @RequestMapping(value = "doExportDetails", method = RequestMethod.GET)
    public ModelAndView doExportDetails(@ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter,
                                        @RequestParam(required = false) final SampleFilter.SampleVisibility sampleVisibility,
                                        @RequestParam(required = false) final String searchTerm,
                                        @RequestParam(required = true) final int startPosition,
                                        HttpServletResponse response) {
        log.info("Requesting exportSamples of ViewSamplesController...");

        final ModelMap model = new ModelMap();
        processRequestParams(filter, searchTerm, sampleVisibility);
        populateModel(model, filter, startPosition);
        Collection<Sample> samples = ((SamplesViewModel) model.get(ViewModel.MODEL_ATTR_NAME)).getSamples();
        if (samples != null && samples.size() > 0) {
            Set<String> samplesIDs = MemiTools.getSampleIds(samples);
            Class clazz = MemiTools.getTypeOfGenericSet(samples);
            if (downloadService != null) {
                boolean isDialogOpen = downloadService.openDownloadDialog(response, clazz, samplesIDs);
                model.addAttribute("isDialogOpen", isDialogOpen);
            } else {
                log.warn("Could not open download dialog to export samples. Download service is null!");
            }
        } else {
            log.info("There are no samples to be exported!");
        }
        return buildModelAndView(
                getModelViewName(),
                model,
                new SampleModelPopulator()
        );
    }

    @RequestMapping(params = "search", value = "doSearch", method = RequestMethod.GET)
    public ModelAndView doSearch(@ModelAttribute(SampleFilter.MODEL_ATTR_NAME) SampleFilter filter,
                                 @RequestParam(required = false) final SampleFilter.SampleVisibility sampleVisibility,
                                 @RequestParam(required = false) final String searchTerm,
                                 @RequestParam(required = true) final int startPosition,
                                 ModelMap model) {
        log.info("Requesting doSearch of " + ViewSamplesController.class + "...");

        processRequestParams(filter, searchTerm, sampleVisibility);
        populateModel(model, filter, startPosition);
        return buildModelAndView(
                getModelViewName(),
                model,
                new SampleModelPopulator()
        );
    }

    private void processRequestParams(SampleFilter filter, String searchTerm,
                                      SampleFilter.SampleVisibility sampleVisibility) {
        //Set filter parameters
        filter.setSearchTerm(searchTerm);

        //The visibility parameter can only be set if a user is logged in, means a session object exists
        if (userManager.getUserAuthentication().getSubmitter() != null) {
            filter.setSampleVisibility((sampleVisibility != null ? sampleVisibility : SampleFilter.SampleVisibility.MY_SAMPLES));
        } else {
            filter.setSampleVisibility(SampleFilter.SampleVisibility.ALL_PUBLISHED_SAMPLES);
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(SampleFilter.SampleVisibility.class, "sampleVisibility", new SampleVisibilityEditor());
//        binder.registerCustomEditor(Sample.SampleType.class, "sampleType", new SampleTypeEditor());
    }

    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model, SampleFilter filter, int startPosition) {
        final ViewModelBuilder<SamplesViewModel> builder = new SamplesViewModelBuilder(userManager, getEbiSearchForm(), "Samples list",
                getBreadcrumbs(null), propertyContainer, getTableHeaderNames(), sampleDAO, filter, startPosition, biomeDAO);
        final SamplesViewModel samplesViewModel = builder.getModel();
        samplesViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, samplesViewModel);
    }

    /**
     * Specifies a list of table header names for the table on studies view page (the list of names should be in the order you like to show within the web
     * application).
     *
     * @return A list of table header names.
     */
    private List<String> getTableHeaderNames() {
        List<String> result = new ArrayList<String>();
        result.add("Biome");
        result.add("Sample ID");
        result.add("Sample name");
        result.add("Project name");
        result.add("Project status");
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

    class SampleModelPopulator implements ModelPopulator {
        @Override
        public void populateModel(ModelMap model) {
            model.addAttribute(SampleFilter.MODEL_ATTR_NAME, ((SamplesViewModel) model.get(ViewModel.MODEL_ATTR_NAME)).getSampleFilter());
        }
    }
}
