package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.FilterForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the controller for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/listStudies")
public class ListStudiesController {

    @Resource
    private EmgStudyDAO emgStudyDAO;

    @RequestMapping(method = RequestMethod.GET)
    public String initPage(ModelMap model) {
        FilterForm filterForm = new FilterForm();
        model.put("filterForm", filterForm);
        return "listStudies";
    }


    @RequestMapping(value = "/exportStudies", method = RequestMethod.GET)
    public String exportStudiesHandler(ModelMap model) {
        List<EmgStudy> studies = emgStudyDAO.retrieveAll();
        if (studies != null && studies.size() > 0) {
            //Add studies to model
            model.put("studies", studies);
            //Create study property list and add it to the model
            addStudyPropertyList(model, studies.get(0));
        }
        model.put("columnLength", 35);
        return "exportStudies";
    }

    /**
     * Creates and add a study property list to the specified model.
     */
    private void addStudyPropertyList(ModelMap model, EmgStudy study) {
        List<String> result = new ArrayList<String>();
        for (String key : study.getProperties().keySet()) {
            result.add(key);
        }
        model.put("studyPropertyList", result);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit
            (@ModelAttribute("filterForm") FilterForm
                    filterForm, ModelMap
                    model, BindingResult
                    result, SessionStatus
                    status) {
        return "listStudies";
    }

    @ModelAttribute("studyList")
    public List<EmgStudy> populateStudyList
            () {
        List<EmgStudy> result = emgStudyDAO.retrieveAll();
        if (result == null) {
            result = new ArrayList<EmgStudy>();
        }
        return result;
    }

    @ModelAttribute("studyTypeList")
    public EmgStudy.StudyType[] populateStudyTypes
            () {
        return EmgStudy.StudyType.values();
    }

    @ModelAttribute("studyStatusList")
    public EmgStudy.StudyStatus[] populateStudyStati
            () {
        return EmgStudy.StudyStatus.values();
    }
}