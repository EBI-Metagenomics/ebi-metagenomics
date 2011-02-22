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
import uk.ac.ebi.interpro.metagenomics.memi.basic.StudyStatusEditor;
import uk.ac.ebi.interpro.metagenomics.memi.basic.StudyTypeEditor;
import uk.ac.ebi.interpro.metagenomics.memi.basic.StudyVisibilityEditor;
import uk.ac.ebi.interpro.metagenomics.memi.basic.VelocityTemplateWriter;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewStudiesModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
@RequestMapping("/viewStudies")
public class ViewStudiesController extends LoginController implements IMGController {

    private final Log log = LogFactory.getLog(ViewStudiesController.class);

    /* The maximum allowed number of characters per column within the study list table*/
    private final int MAX_CHARS_PER_COLUMN = 35;

    /**
     * View name of this controller which is used several times.
     */
    private final String VIEW_NAME = "viewStudies";

    private final String VELOCITY_TEMPLATE_LOCATION_PATH = "WEB-INF/velocity_templates/exportStudies.vm";

    private final String DOWNLOAD_FILE_NAME = "studies.csv";

    @Resource
    private HibernateStudyDAO studyDAO;

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
        populateModel(model, new StudyFilter());
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        model.addAttribute(StudyFilter.MODEL_ATTR_NAME, ((ViewStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getStudyFilter());
        return new ModelAndView(VIEW_NAME, model);
    }


    /**
     * Handles the export of the study table.
     * Creates a tab separated file using the Velocity engine and afterwards it is open a
     * download dialog.
     */
    @RequestMapping(value = "doExport", method = RequestMethod.GET)
    public ModelAndView doExportStudies(@ModelAttribute(StudyFilter.MODEL_ATTR_NAME) StudyFilter filter, HttpServletRequest request, HttpServletResponse response) {
        log.info("Requesting exportStudies (GET method)...");
        ModelMap model = new ModelMap();
        processRequestParams(request, filter);
        populateModel(model, filter);
        List<Study> studies = ((ViewStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getStudies();

        if (studies != null && studies.size() > 0) {
            //Create velocity spring_model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            velocityModel.put("studyProperties", getStudyProperties());
            velocityModel.put("studies", studies);
            velocityModel.put("columnLength", MAX_CHARS_PER_COLUMN);
            //Create file content
            String fileContent = VelocityTemplateWriter.createFileContent(velocityEngine, VELOCITY_TEMPLATE_LOCATION_PATH, velocityModel);
            File file = MemiFileWriter.writeCSVFile(fileContent);
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, file, DOWNLOAD_FILE_NAME, true);
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
        populateModel(model, new StudyFilter());
        model.addAttribute(StudyFilter.MODEL_ATTR_NAME, ((ViewStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getStudyFilter());
        return new ModelAndView(VIEW_NAME, model);
    }

    @RequestMapping(params = "search", value = "doSearch", method = RequestMethod.GET)
    public ModelAndView doSearch(HttpServletRequest request, @ModelAttribute(StudyFilter.MODEL_ATTR_NAME) StudyFilter filter, ModelMap model) {
        log.info("Requesting doSearch (POST method)...");
        processRequestParams(request, filter);
        populateModel(model, filter);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    /**
     * Values are set to an empty string for the search term and a public visibility. The other
     * parameter are set to all (null).
     */
    @RequestMapping(params = "clear", value = "doSearch", method = RequestMethod.GET)
    public ModelAndView doClearSearch(ModelMap model, @ModelAttribute(StudyFilter.MODEL_ATTR_NAME) StudyFilter filter) {
        log.info("Requesting doClear (POST method)...");

        filter.setSearchTerm("");
        filter.setStudyVisibility(StudyFilter.StudyVisibility.ALL_PUBLISHED_STUDIES);
        filter.setStudyStatus(null);
        filter.setStudyType(null);
        populateModel(model, new StudyFilter());
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    private void processRequestParams(HttpServletRequest request, StudyFilter filter) {
        //Get URL parameter of GET request
        String studyVisibility = request.getParameter("studyVisibility");
        String searchTerm = request.getParameter("searchTerm");
        String studyType = request.getParameter("studyType");
        String studyStatus = request.getParameter("studyStatus");

        //Set parameters to the filter
        //Set parameter search term        
        if (searchTerm != null && searchTerm.trim().length() > 0) {
            filter.setSearchTerm(searchTerm);
        }
        //Set parameter study type
        if (studyType != null && studyType.trim().length() > 0) {
            try {
                Study.StudyType type = Study.StudyType.valueOf(studyType);
                if (type != null) {
                    filter.setStudyType(type);
                }
            } catch (Exception e) {
                log.warn("Could not find any study type value for name: " + studyType);
                filter.setStudyType(null);
            }
        }
        //Set parameter study status
        if (studyStatus != null && studyStatus.trim().length() > 0) {
            try {
                Study.StudyStatus status = Study.StudyStatus.valueOf(studyStatus);
                if (status != null) {
                    filter.setStudyStatus(status);
                }
            } catch (Exception e) {
                log.warn("Could not find any study type value for name: " + studyStatus);
                filter.setStudyStatus(null);

            }
        }
        //The visibility parameter can only be set if a user is logged in, means a session object exists
        if (sessionManager.getSessionBean().getSubmitter() != null && studyVisibility != null && studyVisibility.trim().length() > 0) {
            StudyFilter.StudyVisibility vis = StudyFilter.StudyVisibility.valueOf(studyVisibility);
            if (vis != null) {
                filter.setStudyVisibility(vis);
            }
        } else {
            filter.setStudyVisibility(StudyFilter.StudyVisibility.ALL_PUBLISHED_STUDIES);
        }
    }


    /**
     * Registers non primitive data types or classes.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Study.StudyType.class, "studyType", new StudyTypeEditor());
        binder.registerCustomEditor(Study.StudyStatus.class, "studyStatus", new StudyStatusEditor());
        binder.registerCustomEditor(StudyFilter.StudyVisibility.class, "studyVisibility", new StudyVisibilityEditor());
    }


    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model, StudyFilter filter) {
        final ViewStudiesModel subModel = MGModelFactory.getViewStudiesPageModel(sessionManager, studyDAO, filter);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, subModel);
    }

    private List<String> getStudyProperties() {
        List<String> result = new ArrayList<String>();
        result.add("STUDY_ID");
        result.add("STUDY_NAME");
        result.add("STUDY_TYPE");
        result.add("PRIVACY");
        result.add("ANALYSIS_STATUS");
        result.add("EXPERIMENTAL_FACTOR");
        result.add("NCBI_PROJECT_ID");
        result.add("PUBLIC_RELEASE_DATE");
        result.add("CENTRE_NAME");
        result.add("STUDY_LINKOUT");

        return result;
    }

    /**
     * Generates the page title subject to the value of the study filter.
     */
    @ModelAttribute(value = "pageTitle")
    public String populatePageTitle(@ModelAttribute(StudyFilter.MODEL_ATTR_NAME) StudyFilter filter) {
        StudyFilter.StudyVisibility vis = filter.getStudyVisibility();
        if (vis != null) {
            if (vis.equals(StudyFilter.StudyVisibility.MY_STUDIES)) {
                return "My studies (published and pre-published)";
            } else if (vis.equals(StudyFilter.StudyVisibility.MY_PUBLISHED_STUDIES)) {
                return "My published studies";
            } else if (vis.equals(StudyFilter.StudyVisibility.MY_PREPUBLISHED_STUDIES)) {
                return "My pre-published studies";
            } else if (vis.equals(StudyFilter.StudyVisibility.ALL_PUBLISHED_STUDIES)) {
                return "All published studies";
            } else if (vis.equals(StudyFilter.StudyVisibility.ALL_STUDIES)) {
                return "All published and my pre-published studies";
            }
        }
        return "List of studies";
    }
}