package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.Sample;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(method = RequestMethod.GET)
    public String initPage(ModelMap model, HttpServletRequest request) {
        //Get the sample Id
        int requestedId = Integer.parseInt(request.getParameter("id"));
        Sample sample = sampleDAO.getSampleById(requestedId);
        //Add sample to model
        model.put("sample", sample);
        return "sampleOverview";
    }
}