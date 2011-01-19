package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.basic.VelocityTemplateWriter;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
public class SampleOverviewController {
    @Resource
    private EmgSampleDAO sampleDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @RequestMapping(value = "/{sampleId}", method = RequestMethod.GET)
    public String findSample(@PathVariable String sampleId, ModelMap model) {
        EmgSample sample = sampleDAO.read(sampleId);
        //Add sample to spring_model
        model.put("sample", sample);
        return "sampleOverview";
    }


    @RequestMapping(value = "/exportSample/{sampleId}", method = RequestMethod.GET)
    public ModelAndView exportSampleHandler(@PathVariable String sampleId, HttpServletResponse response) {
        EmgSample sample = sampleDAO.read(sampleId);
        if (sample != null) {
            //Create velocity spring_model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            velocityModel.put("sample", sample);
            //Create file content
            String fileContent = VelocityTemplateWriter.createFileContent(velocityEngine, "WEB-INF/velocity_templates/exportSample.vm", velocityModel);
            File file = MemiFileWriter.writeCSVFile(fileContent);
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, file, "sample.csv");
            }
        }
        return null;
    }
}