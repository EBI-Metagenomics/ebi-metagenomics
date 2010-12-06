package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the controller for the study overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/studyOverview")
public class StudyOverviewController {

    @Resource
    private StudyDAO studyDAO;

    @Resource
    private SampleDAO sampleDAO;


    @RequestMapping(value = "/{studyId}", method = RequestMethod.GET)
    public String findStudy(@PathVariable Long studyId, ModelMap model) {
        Study study = studyDAO.getStudyById(studyId);
        //Add study to model
        model.put("study", study);
        return "studyOverview";
    }

    @ModelAttribute("sampleList")
    public List<Sample> populateSampleList() {
        List<Sample> samples = sampleDAO.getAllSamples();
        if (samples == null) {
            samples = new ArrayList<Sample>();
        }
        return samples;
    }

    @ModelAttribute("samplePropertyList")
    public List<String> populateSamplePropertyList() {
        Sample sample = new Sample();
        List<String> result = new ArrayList<String>();
        for (String key : sample.getPropertyMap().keySet()) {
            result.add(key);
        }
        return result;
    }
}