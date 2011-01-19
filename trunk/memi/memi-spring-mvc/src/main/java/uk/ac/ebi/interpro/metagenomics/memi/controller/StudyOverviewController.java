package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.basic.VelocityTemplateWriter;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
public class StudyOverviewController {

    @Resource
    private EmgStudyDAO emgStudyDAO;

    @Resource
    private EmgSampleDAO emgSampleDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @RequestMapping(value = "/{studyId}", method = RequestMethod.GET)
    public String findStudy(@PathVariable String studyId, ModelMap model) {
        EmgStudy study = emgStudyDAO.read(studyId);
        //Add study to spring_model
        model.put("study", study);
        Map<String, Object> studyPropMap = study.getProperties();
        model.put("studyPropertyMap", studyPropMap);
        return "studyOverview";
    }


    @RequestMapping(value = "/exportSamples/{studyId}", method = RequestMethod.GET)
    public ModelAndView exportSamplesHandler(ModelMap model, HttpServletResponse response) throws Exception {
        List<EmgSample> samples = (List<EmgSample>) model.get("sampleList");
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
        return null;
    }


    /**
     * Creates and add a sample property list to the specified spring_model.
     */
    private List<String> getSamplePropertyList(EmgSample sample) {
        List<String> result = new ArrayList<String>();
        for (String key : sample.getPropertyMap().keySet()) {
            result.add(key);
        }
        return result;
    }

    @ModelAttribute(value = "sampleList")
    public List<EmgSample> populateSampleList(@PathVariable String studyId) {
        List<EmgSample> samples = emgSampleDAO.retrieveSamplesByStudyId(studyId);
        if (samples == null) {
            samples = new ArrayList<EmgSample>();
        }
        return samples;
    }
}