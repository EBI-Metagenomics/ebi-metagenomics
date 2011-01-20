package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.basic.VelocityTemplateWriter;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the controller for the study overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/studyOverview")
public class StudyOverviewController extends LoginController {

    /**
     * View name of this controller which is used several times.
     */
    private final String VIEW_NAME = "studyOverview";

    @Resource
    private EmgStudyDAO emgStudyDAO;

    @Resource
    private EmgSampleDAO emgSampleDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @Resource
    private SessionManager sessionManager;

    //GET Methods

    @RequestMapping(value = "/{studyId}", method = RequestMethod.GET)
    public ModelAndView doGetStudy(@PathVariable String studyId, ModelMap model) {
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }


    @RequestMapping(value = "/exportSamples/{studyId}", method = RequestMethod.GET)
    public ModelAndView doExportSamples(ModelMap model, HttpServletResponse response) throws Exception {
        List<EmgSample> samples = (List<EmgSample>) model.get("samples");
        if (samples != null && samples.size() > 0) {
            //Create velocity spring_model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            velocityModel.put("samplePropertyList", getSamplePropertyList(samples.get(0)));
            velocityModel.put("samples", samples);
            //Create file content
            String fileContent = VelocityTemplateWriter.createFileContent(velocityEngine, "WEB-INF/velocity_templates/exportSamples.vm", velocityModel);
            File file = MemiFileWriter.writeCSVFile(fileContent);
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, file, "samples.csv");
            }
        }
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    //POST Methods

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doProcessLogin(@ModelAttribute(LoginForm.MODEL_ATTR_NAME) @Valid LoginForm loginForm, BindingResult result,
                                       ModelMap model, SessionStatus status) {
        //process login
        super.processLogin(loginForm, result, model, status);
        //create model and view
        populateModel(model);
        return new ModelAndView(VIEW_NAME, model);
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model) {
        final MGModel hpModel = MGModelFactory.getMGModel(sessionManager);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, hpModel);
    }


    /**
     * Returns a list of sample properties.
     */
    private List<String> getSamplePropertyList(EmgSample sample) {
        List<String> result = new ArrayList<String>();
        for (String key : sample.getPropertyMap().keySet()) {
            result.add(key);
        }
        return result;
    }

    /**
     * Returns a list of study properties.
     */
    @ModelAttribute(value = "studyPropertyMap")
    private Map<String, Object> getStudyPropertyMap(@PathVariable String studyId) {
        EmgStudy study = emgStudyDAO.read(studyId);
        if (study != null) {
            return study.getProperties();
        }
        return new HashMap<String, Object>();
    }


    @ModelAttribute(value = "samples")
    public List<EmgSample> populateSampleList(@PathVariable String studyId) {
        List<EmgSample> samples = emgSampleDAO.retrieveSamplesByStudyId(studyId);
        if (samples == null) {
            samples = new ArrayList<EmgSample>();
        }
        return samples;
    }

    @ModelAttribute(value = "study")
    public EmgStudy populateStudy(@PathVariable String studyId) {
        if (emgStudyDAO != null) {
            return emgStudyDAO.read(studyId);
        }
        return null;
    }
}