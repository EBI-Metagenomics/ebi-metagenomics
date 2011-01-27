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
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the controller for sample overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/sampleOverview")
public class SampleOverviewController extends LoginController {
    /**
     * View name of this controller which is used several times.
     */
    private final String VIEW_NAME = "sampleOverview";
    @Resource
    private EmgSampleDAO emgSampleDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @Resource
    private SessionManager sessionManager;

    @RequestMapping(value = "/{sampleId}", method = RequestMethod.GET)
    public ModelAndView doGetSample(@PathVariable String sampleId, ModelMap model) {
        //Add sample to spring mvc model
//        EmgSample sample = emgSampleDAO.read(sampleId);
//        model.put("sample", sample);
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }


    @RequestMapping(value = "/exportSample/{sampleId}", method = RequestMethod.GET)
    public ModelAndView doExportSample(ModelMap model, @PathVariable String sampleId, HttpServletResponse response) {
        EmgSample sample = emgSampleDAO.read(sampleId);
        if (sample != null) {
            //Create velocity spring model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            velocityModel.put("sample", sample);
            //Create file content
            String fileContent = VelocityTemplateWriter.createFileContent(velocityEngine, "WEB-INF/velocity_templates/exportSample.vm", velocityModel);
            File file = MemiFileWriter.writeCSVFile(fileContent);
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, file, "sample.csv");
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

    @ModelAttribute(value = "sample")
    public EmgSample populateSample(@PathVariable String sampleId) {
        return emgSampleDAO.read(sampleId);
    }
}