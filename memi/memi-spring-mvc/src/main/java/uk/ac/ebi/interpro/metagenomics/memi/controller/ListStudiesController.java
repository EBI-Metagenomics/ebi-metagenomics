package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.FilterForm;
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

    @ModelAttribute("studyList")
    public List<EmgStudy> populateStudyList() {
        List<EmgStudy> result = emgStudyDAO.retrieveAll();
        if (result == null) {
            result = new ArrayList<EmgStudy>();
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(@ModelAttribute("filterForm") FilterForm filterForm, ModelMap model, BindingResult result, SessionStatus status) {
        return "listStudies";
    }

    @ModelAttribute("studyTypeList")
    public EmgStudy.StudyType[] populateStudyTypes() {
        return EmgStudy.StudyType.values();
    }

    @ModelAttribute("studyStatusList")
    public EmgStudy.StudyStatus[] populateStudyStati() {
        return EmgStudy.StudyStatus.values();
    }
}