package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;
import uk.ac.ebi.interpro.metagenomics.memi.tools.MemiTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.util.*;

/**
 * Represents the controller for the study overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/studyView")
public class StudyViewController extends LoginController {

    private final Log log = LogFactory.getLog(StudyViewController.class);

    /**
     * View name of this controller which is used several times.
     */
    private final String VIEW_NAME = "studyView";

    @Resource
    private HibernateStudyDAO studyDAO;

    @Resource
    private HibernateSampleDAO sampleDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @Resource
    private SessionManager sessionManager;

    //GET Methods

    @RequestMapping(value = "/{studyId}", method = RequestMethod.GET)
    public ModelAndView doGetStudy(ModelMap model) {
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }


    @RequestMapping(value = "/doExport/{studyId}", method = RequestMethod.GET)
    public ModelAndView doExportSamples(@PathVariable Long studyId, ModelMap model, HttpServletResponse response) throws Exception {
        Study study = (Study) model.get("study");
        Study.StudyType type = Study.StudyType.UNDEFINED;
        if (study != null) {
            type = study.getStudyType();
        }
        List<Sample> samples = (List<Sample>) model.get("samples");
        if (samples != null && samples.size() > 0) {
            String[] samplesIDs = MemiTools.getSampleIds(samples);
            if (downloadService != null) {
                boolean isDialogOpen = downloadService.openDownloadDialog(response, type, samplesIDs);
                model.addAttribute("isDialogOpen", isDialogOpen);
            } else {
                log.warn("Could not open download dialog to export samples. Download service is null!");
            }
        } else {
            log.info("There are no samples to be exported!");
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


    /**
     * Returns a list of sample properties.
     */
    private List<String> getSamplePropertyList(EmgSample sample) {
        List<String> result = new ArrayList<String>();
        for (String key : sample.getPropertyMap().keySet()) {
            result.add(key);
        }
        return result;
    }

    /**
     * Returns a list of study properties.
     */
    @ModelAttribute(value = "studyPropertyMap")
    private Map<String, Object> getStudyPropertyMap(@PathVariable Long studyId) {
        Study study = studyDAO.read(studyId);
//        if (study != null) {
//            return study.getProperties();
//        }
        return new HashMap<String, Object>();
    }


    @ModelAttribute(value = "samples")
    public List<Sample> populateSampleList(@PathVariable Long studyId) {
        List<Sample> samples = sampleDAO.retrieveSamplesByStudyId(studyId);
        if (samples == null) {
            samples = new ArrayList<Sample>();
        }
        return samples;
    }

    /**
     * Populates study for requested study ID.
     */
    @ModelAttribute(value = "study")
    public Study populateStudy(@PathVariable Long studyId) {
        if (studyDAO != null) {
            return studyDAO.read(studyId);
        }
        return null;
    }

    /**
     * Populates study associated publications.
     */
    @ModelAttribute(value = "publications")
    public Set<Publication> populatePub(@PathVariable Long studyId) {
        if (studyDAO != null) {
            Study study = studyDAO.read(studyId);
            if (study != null) {
                Set<Publication> pubs = study.getPublications();
                if (pubs != null) {
                    return pubs;
                }
            }
        }
        return new HashSet<Publication>();
    }
}