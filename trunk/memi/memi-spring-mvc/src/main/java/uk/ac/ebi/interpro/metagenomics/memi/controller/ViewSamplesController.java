package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.basic.SampleVisibilityEditor;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
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
@RequestMapping("/viewSamples")
public class ViewSamplesController extends LoginController implements IMGController {

    private final Log log = LogFactory.getLog(ViewSamplesController.class);

    /* The maximum allowed number of characters per column within the study list table*/
    private final int MAX_CHARS_PER_COLUMN = 35;

    /**
     * View name of this controller which is used several times.
     */
    private final String VIEW_NAME = "viewSamples";

    private final String VELOCITY_TEMPLATE_LOCATION_PATH = "WEB-INF/velocity_templates/exportStudies.vm";

    private final String DOWNLOAD_FILE_NAME = "samples.csv";

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
     * Handles the export of the study table.
     * Creates a tab separated file using the Velocity engine and afterwards it is open a
     * download dialog.
     */
    @RequestMapping(value = "doExport", method = RequestMethod.GET)
    public ModelAndView doExportStudies(HttpServletResponse response) {
        log.info("Requesting exportStudies (GET method)...");
        List<Sample> samples = sampleDAO.retrieveAll();
        if (samples != null && samples.size() > 0) {
            //Create velocity spring_model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            //velocityModel.put("studyPropertyList", getStudyPropertyList(samples.get(0)));
            velocityModel.put("samples", samples);
            velocityModel.put("columnLength", MAX_CHARS_PER_COLUMN);
            //Create file content
            String fileContent = VelocityTemplateWriter.createFileContent(velocityEngine, VELOCITY_TEMPLATE_LOCATION_PATH, velocityModel);
            File file = MemiFileWriter.writeCSVFile(fileContent);
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, file, DOWNLOAD_FILE_NAME);
            }
        }
        return null;
    }


    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doProcessLogin(@ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm, BindingResult result,
                                       ModelMap model, SessionStatus status) {
        log.info("Requesting doProcessLogin (POST method)...");
        //process login
        super.processLogin(loginForm, result, model, status);
        //create model and view
        populateModel(model, new SampleFilter());
        model.addAttribute(SampleFilter.MODEL_ATTR_NAME, ((ViewSamplesModel) model.get(MGModel.MODEL_ATTR_NAME)).getSampleFilter());
        return new ModelAndView(VIEW_NAME, model);
    }

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
        filter.setSampleVisibility(SampleFilter.SampleVisibility.PUBLIC);
        populateModel(model, new SampleFilter());
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewSamplesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    private void processRequestParams(HttpServletRequest request, SampleFilter filter) {
        //Get URL parameter of GET request
        String sampleVisibility = request.getParameter("sampleVisibility");
        String searchTerm = request.getParameter("searchTerm");

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
            filter.setSampleVisibility(SampleFilter.SampleVisibility.PUBLIC);
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(SampleFilter.SampleVisibility.class, "sampleVisibility", new SampleVisibilityEditor());
    }

    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model, SampleFilter filter) {
        final ViewSamplesModel subModel = MGModelFactory.getViewSamplesPageModel(sessionManager, sampleDAO, filter);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, subModel);
    }
}