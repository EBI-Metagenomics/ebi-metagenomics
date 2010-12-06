package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.Sample;

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
    private SampleDAO sampleDAO;

    @RequestMapping(value = "/{sampleId}", method = RequestMethod.GET)
    public String findSample(@PathVariable Long sampleId, ModelMap model) {
        Sample sample = sampleDAO.getSampleById(sampleId);
        //Add sample to model
        model.put("sample", sample);
        return "sampleOverview";
    }
}