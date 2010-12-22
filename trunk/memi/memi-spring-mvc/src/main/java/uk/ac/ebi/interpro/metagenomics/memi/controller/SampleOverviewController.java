package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;

import javax.annotation.Resource;

/**
 * Represents the controller for sample overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/sampleOverview")
public class SampleOverviewController {
    @Resource
    private EmgSampleDAO sampleDAO;

    @RequestMapping(value = "/{sampleId}", method = RequestMethod.GET)
    public String findSample(@PathVariable String sampleId, ModelMap model) {
        EmgSample sample = sampleDAO.read(sampleId);
        //Add sample to model
        model.put("sample", sample);
        return "sampleOverview";
    }

    @RequestMapping(value = "/exportSample/{sampleId}", method = RequestMethod.GET)
    public String exportSampleHandler(@PathVariable String sampleId, ModelMap model) {
        EmgSample sample = sampleDAO.read(sampleId);
        //Add sample to model
        model.put("sample", sample);
        return "exportSample";
    }
}