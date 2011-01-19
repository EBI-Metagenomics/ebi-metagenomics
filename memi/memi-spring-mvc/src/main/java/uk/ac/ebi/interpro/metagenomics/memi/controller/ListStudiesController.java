package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.basic.VelocityTemplateWriter;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.FilterForm;
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
 * Represents the controller for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/listStudies")
public class ListStudiesController {

    /* The maximum allowed number of characters per column within the study list table*/
    private final int MAX_CHARS_PER_COLUMN = 35;

    private final String VELOCITY_TEMPLATE_LOCATION_PATH = "WEB-INF/velocity_templates/exportStudies.vm";

    private final String DOWNLOAD_FILE_NAME = "studies.csv";

    @Resource
    private EmgStudyDAO emgStudyDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @RequestMapping(method = RequestMethod.GET)
    public String initPage(ModelMap model) {
        FilterForm filterForm = new FilterForm();
        model.put("filterForm", filterForm);
        return "listStudies";
    }


    @RequestMapping(value = "/exportStudies", method = RequestMethod.GET)
    public ModelAndView exportStudiesHandler(HttpServletResponse response) {
        List<EmgStudy> studies = emgStudyDAO.retrieveAll();
        if (studies != null && studies.size() > 0) {
            //Create velocity spring_model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            velocityModel.put("studyPropertyList", getStudyPropertyList(studies.get(0)));
            velocityModel.put("studies", studies);
            velocityModel.put("columnLength", MAX_CHARS_PER_COLUMN);
            //Create file content
            String fileContent = VelocityTemplateWriter.createFileContent(velocityEngine, VELOCITY_TEMPLATE_LOCATION_PATH, velocityModel);
            File file = MemiFileWriter.writeCSVFile(fileContent);
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, file, DOWNLOAD_FILE_NAME);
            }
        }
        return null;
    }

    /**
     * Returns a list of study properties.
     */
    private List<String> getStudyPropertyList(EmgStudy study) {
        List<String> result = new ArrayList<String>();
        for (String key : study.getProperties().keySet()) {
            result.add(key);
        }
        return result;
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