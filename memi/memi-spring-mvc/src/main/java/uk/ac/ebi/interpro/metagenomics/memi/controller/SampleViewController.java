package uk.ac.ebi.interpro.metagenomics.memi.controller;

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
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgLogFileInfoDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.*;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the controller for sample overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/sampleView")
public class SampleViewController extends LoginController {
    /**
     * View name of this controller which is used several times.
     */
    private final String VIEW_NAME = "sampleView";

    @Resource
    private HibernateSampleDAO sampleDAO;

    @Resource
    private EmgLogFileInfoDAO fileInfoDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @Resource
    private SessionManager sessionManager;

    @RequestMapping(value = "/{sampleId}", method = RequestMethod.GET)
    public ModelAndView doGetSample(ModelMap model) {
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }


    @RequestMapping(value = "/doExport/{sampleId}", method = RequestMethod.GET)
    public ModelAndView doExportSample(@PathVariable String sampleId, ModelMap model, HttpServletResponse response) {
        Study.StudyType type = getSampleType(sampleId);
        if (downloadService != null) {
            boolean isDialogOpen = downloadService.openDownloadDialog(response, type, sampleId);
            model.addAttribute("isDialogOpen", isDialogOpen);
        }
        populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    private Study.StudyType getSampleType(String sampleId) {
        Sample sample = sampleDAO.readByStringId(sampleId);
        if (sample instanceof HostSample) {
            return Study.StudyType.HOST_ASSOCIATED;

        } else if (sample instanceof EnvironmentSample) {
            return Study.StudyType.ENVIRONMENTAL;
        } else {
            return Study.StudyType.UNDEFINED;
        }
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

    @ModelAttribute(value = "sample")
    public Sample populateSample(@PathVariable String sampleId) {
        return sampleDAO.readByStringId(sampleId);
    }

    /**
     * Populates study associated publications.
     */
    @ModelAttribute(value = "publications")
    public Set<Publication> populatePub(@PathVariable String sampleId) {
        if (sampleDAO != null) {
            Sample sample = sampleDAO.readByStringId(sampleId);
            if (sample != null) {
                Set<Publication> pubs = sample.getPublications();
                if (pubs != null) {
                    return pubs;
                }
            }
        }
        return new HashSet<Publication>();
    }

    @ModelAttribute(value = "isHostInstance")
    public boolean populateInstance(@PathVariable String sampleId) {
        if (sampleDAO != null) {
            Sample sample = sampleDAO.readByStringId(sampleId);
            if (sample instanceof HostSample) {
                return true;
            }
        }
        return false;
    }

    @ModelAttribute(value = "archivedSequences")
    public List<String> populateSeqs(@PathVariable String sampleId) {
        List<String> result = null;
        if (sampleDAO != null && fileInfoDAO != null) {
            Sample sample = sampleDAO.readByStringId(sampleId);
            if (sample != null) {
                result = fileInfoDAO.getFileIdsBySampleId(sample.getSampleId());
            }
        }
        return result;
    }
}