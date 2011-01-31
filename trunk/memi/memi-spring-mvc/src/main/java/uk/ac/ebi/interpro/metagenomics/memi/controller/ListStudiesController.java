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
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ListStudiesModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
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
@RequestMapping("/listStudies")
public class ListStudiesController extends LoginController implements IMGController {

    private final Log log = LogFactory.getLog(ListStudiesController.class);

    /* The maximum allowed number of characters per column within the study list table*/
    private final int MAX_CHARS_PER_COLUMN = 35;

    /**
     * View name of this controller which is used several times.
     */
    private final String VIEW_NAME = "listStudies";

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
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ListStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        model.addAttribute(StudyFilter.MODEL_ATTR_NAME, ((ListStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getStudyFilter());
        return new ModelAndView(VIEW_NAME, model);
    }


    /**
     * Handles the export of the study table.
     * Creates a tab separated file using the Velocity engine and afterwards it is open a
     * download dialog.
     */
    @RequestMapping(value = "exportStudies", method = RequestMethod.GET)
    public ModelAndView doExportStudies(HttpServletResponse response) {
        log.info("Requesting exportStudies (GET method)...");
        List<Study> studies = studyDAO.retrieveAll();
        if (studies != null && studies.size() > 0) {
            //Create velocity spring_model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            //velocityModel.put("studyPropertyList", getStudyPropertyList(studies.get(0)));
            velocityModel.put("studies", studies);
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
    @RequestMapping(value = "*/login", method = RequestMethod.POST)
    public ModelAndView doProcessLogin(@ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm, BindingResult result,
                                       ModelMap model, SessionStatus status) {
        log.info("Requesting doProcessLogin (POST method)...");
        //process login
        super.processLogin(loginForm, result, model, status);
        //create model and view
        populateModel(model, new StudyFilter());
        model.addAttribute(StudyFilter.MODEL_ATTR_NAME, ((ListStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getStudyFilter());
        return new ModelAndView(VIEW_NAME, model);
    }

    @RequestMapping(value = "doSearch", method = RequestMethod.POST)
    public ModelAndView doSearch(HttpServletRequest request, HttpServletResponse response, @ModelAttribute(StudyFilter.MODEL_ATTR_NAME) StudyFilter filter, ModelMap model) {
        log.info("Requesting doSearch (POST method)...");


        String contextPath = request.getServletPath();
        populateModel(model, filter);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((ListStudiesModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Study.StudyType.class, "studyType", new StudyTypeEditor());
        binder.registerCustomEditor(Study.StudyStatus.class, "studyStatus", new StudyStatusEditor());
        binder.registerCustomEditor(StudyFilter.StudyVisibility.class, "studyVisibility", new StudyVisibilityEditor());
    }

    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model) {
        final ListStudiesModel subModel = MGModelFactory.getListStudiesPageModel(sessionManager, studyDAO);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, subModel);
    }

    /**
     * Creates the MG model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model, StudyFilter filter) {
        final ListStudiesModel subModel = MGModelFactory.getListStudiesPageModel(sessionManager, studyDAO, filter);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, subModel);
    }

    /**
     * Returns a list of study properties.
     */
    //private List<String> getStudyPropertyList(Study study) {
    //  List<String> result = new ArrayList<String>();
    //for (String key : study.getProperties().keySet()) {
    //  result.add(key);
    //}
    // return result;
    //}
}