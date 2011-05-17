package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.basic.StudyStatusEditor;
import uk.ac.ebi.interpro.metagenomics.memi.basic.StudyVisibilityEditor;
import uk.ac.ebi.interpro.metagenomics.memi.basic.VelocityTemplateWriter;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewStudiesModel;
import uk.ac.ebi.interpro.metagenomics.memi.tools.MemiTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * Represents the controller for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/" + ViewStudiesController.VIEW_NAME)
public class ViewStudiesController extends AbstractController implements IMGController {

    private final static Log log = LogFactory.getLog(ViewStudiesController.class);

    /* The maximum allowed number of characters per column within the study list table*/
    private final int MAX_CHARS_PER_COLUMN = 35;

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "studies";

    private final String VELOCITY_TEMPLATE_LOCATION_PATH = "exportStudies.vm";

    @Resource
    private HibernateStudyDAO studyDAO;

    @Resource
    private HibernateSampleDAO sampleDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    //GET request handler methods

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
    public ModelAndView doExportStudies(@ModelAttribute(StudyFilter.MODEL_ATTR_NAME) StudyFilter filter, HttpServletResponse response,
                                        @RequestParam(required = false) final String searchTerm, @RequestParam(required = false) final StudyFilter.StudyVisibility studyVisibility,
                                        @RequestParam(required = false) final Study.StudyStatus studyStatus) {
        log.info("Requesting exportStudies (GET method)...");
        ModelMap model = new ModelMap();
        processRequestParams(filter, searchTerm, studyVisibility, studyStatus);
        populateModel(model, filter);
        Map<Study, Long> studyMap = ((ViewStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getStudySampleSizeMap();

        if (studyMap != null && studyMap.size() > 0) {
            //Create velocity spring_model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            velocityModel.put("studyProperties", getStudyProperties());
            velocityModel.put("studyMap", studyMap);
            velocityModel.put("columnLength", MAX_CHARS_PER_COLUMN);
            //Create file content
            String fileContent = VelocityTemplateWriter.createFileContent(velocityEngine, VELOCITY_TEMPLATE_LOCATION_PATH, velocityModel);
            File file = MemiFileWriter.writeCSVFile(fileContent);
            String fileName = MemiTools.createFileName("mg_projects_");
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, file, fileName, true);
            }
        }
        return null;
    }

    /**
     * Handles the export of the study table.
     * Creates a tab separated file using the Velocity engine and afterwards it is open a
     * download dialog.
     */
    @RequestMapping(value = "doExportDetails", method = RequestMethod.GET)
    public ModelAndView doExportDetailsStudies(@ModelAttribute(StudyFilter.MODEL_ATTR_NAME) StudyFilter filter, HttpServletResponse response,
                                               @RequestParam(required = false) final String searchTerm, @RequestParam(required = false) final StudyFilter.StudyVisibility studyVisibility,
                                               @RequestParam(required = false) final Study.StudyStatus studyStatus) {
        log.info("Requesting exportStudies (GET method)...");
        ModelMap model = new ModelMap();
        processRequestParams(filter, searchTerm, studyVisibility, studyStatus);
        populateModel(model, filter);
        Map<Study, Long> studyMap = ((ViewStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getStudySampleSizeMap();

        if (studyMap != null && studyMap.size() > 0) {
            //Create export CSV text

            // TODO Not a good idea to hold all file content text in a big string - this could get very large! OK for now with not much data...
            // TODO Refactor Study and Sample table CSV exports and detailed exports to use common method!
            StringBuffer fileContent = new StringBuffer("STUDY_ID,PROJECT_NAME,NUMBER_OF_SAMPLES,SUBMITTED_DATE,ANALYSIS,NCBI_PROJECT_ID,PUBLIC_RELEASE_DATE,CENTRE_NAME,EXPERIMENTAL_FACTOR,IS_PUBLIC,STUDY_LINKOUT,STUDY_ABSTRACT");
            fileContent.append("\n");

            for (Map.Entry<Study, Long> mapEntry : studyMap.entrySet()) {
                Study study = mapEntry.getKey();
                Long numSamples = mapEntry.getValue();

                fileContent.append(study.getStudyId()).append(',');

                fileContent.append("\"").append(study.getStudyName()).append("\",");

                fileContent.append(numSamples).append(',');

                fileContent.append(study.getLastMetadataReceived()).append(',');

                fileContent.append(study.getStudyStatus()).append(',');

                long npi = study.getNcbiProjectId();
                if (npi == 0) {
                    fileContent.append(',');
                } else {
                    fileContent.append(npi).append(',');
                }

                Date prd = study.getPublicReleaseDate();
                if (prd == null) {
                    fileContent.append(',');
                } else {
                    fileContent.append(prd).append(',');
                }

                String cn = study.getCentreName();
                if (cn == null || cn.equals("")) {
                    fileContent.append(',');
                } else {
                    fileContent.append("\"").append(cn).append("\",");
                }
                fileContent.append(study.getExperimentalFactor()).append(',');

                fileContent.append(study.isPublic()).append(',');

                String spu = study.getStudyPageURL();
                if (spu == null || spu.equals("")) {
                    fileContent.append(',');
                } else {
                    fileContent.append(spu).append(',');
                }

                fileContent.append("\"").append(study.getStudyAbstract()).append("\",");

                fileContent.append("\n");
            }

            File file = MemiFileWriter.writeCSVFile(fileContent.toString());
            String fileName = MemiTools.createFileName("mg_projects_");
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, file, fileName, true);
            }
        }
        return null;
    }

    @RequestMapping(params = "search", value = "doSearch", method = RequestMethod.GET)
    public ModelAndView doSearch(@ModelAttribute(StudyFilter.MODEL_ATTR_NAME) StudyFilter filter, ModelMap model,
                                 @RequestParam(required = false) final String searchTerm, @RequestParam(required = false) final StudyFilter.StudyVisibility studyVisibility,
                                 @RequestParam(required = false) final Study.StudyStatus studyStatus) {
        log.info("Requesting doSearch (POST method)...");
        processRequestParams(filter, searchTerm, studyVisibility, studyStatus);
        populateModel(model, filter);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ViewStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    private void processRequestParams(StudyFilter filter, String searchTerm, StudyFilter.StudyVisibility studyVisibility,
                                      Study.StudyStatus studyStatus) {
        //Set filter parameters
        //Set parameter search term
        filter.setSearchTerm(searchTerm);

        //Set parameter study status
        filter.setStudyStatus(studyStatus);

        //The visibility parameter can only be set if a user is logged in, means a session object exists
        if (sessionManager.getSessionBean().getSubmitter() != null) {
            filter.setStudyVisibility(studyVisibility);
        } else {
            filter.setStudyVisibility(StudyFilter.StudyVisibility.ALL_PUBLISHED_PROJECTS);
        }
    }


    /**
     * Registers non primitive data types or classes.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(Study.StudyType.class, "studyType", new SampleTypeEditor());
        binder.registerCustomEditor(Study.StudyStatus.class, "studyStatus", new StudyStatusEditor());
        binder.registerCustomEditor(StudyFilter.StudyVisibility.class, "studyVisibility", new StudyVisibilityEditor());
    }


    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model, StudyFilter filter) {
        final ViewStudiesModel subModel = MGModelFactory.getViewStudiesPageModel(sessionManager, studyDAO,
                sampleDAO, filter, "Metagenomics View Projects", getBreadcrumbs(null), propertyContainer);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, subModel);
    }

    private List<String> getStudyProperties() {
        List<String> result = new ArrayList<String>();
        result.add("PROJECT_NAME");
        result.add("NUMBER_OF_SAMPLES");
        result.add("SUBMITTED_DATE");
        result.add("ANALYSIS");
        return result;
    }

    /**
     * Generates the page title subject to the value of the study filter.
     */
    @ModelAttribute(value = "pageTitle")
    public String populatePageTitle(@ModelAttribute(StudyFilter.MODEL_ATTR_NAME) StudyFilter filter) {
        StudyFilter.StudyVisibility vis = filter.getStudyVisibility();
        if (vis != null) {
            if (vis.equals(StudyFilter.StudyVisibility.MY_PROJECTS)) {
                return "My projects (published and pre-published)";
            } else if (vis.equals(StudyFilter.StudyVisibility.MY_PUBLISHED_PROJECTS)) {
                return "My published projects";
            } else if (vis.equals(StudyFilter.StudyVisibility.MY_PREPUBLISHED_PROJECTS)) {
                return "My pre-published projects";
            } else if (vis.equals(StudyFilter.StudyVisibility.ALL_PUBLISHED_PROJECTS)) {
                return "All published projects";
            } else if (vis.equals(StudyFilter.StudyVisibility.ALL_PROJECTS)) {
                return "All published and my pre-published projects";
            }
        }
        return "List of studies";
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Projects", "View projects", VIEW_NAME));
        return result;
    }
}
