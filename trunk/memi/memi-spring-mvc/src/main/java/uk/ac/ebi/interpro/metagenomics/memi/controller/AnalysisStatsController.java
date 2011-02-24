package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AnalysisStatsModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;

/**
 * The controller for analysis overview page.
 *
 * @author Phil Jones, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping('/' + AnalysisStatsController.VIEW_NAME + "/{sampleId}")
public class AnalysisStatsController {
    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "analysisStatsView";

    @Resource
    private HibernateSampleDAO sampleDAO;

    @Resource
    private SessionManager sessionManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGetSample(ModelMap model, @PathVariable String sampleId) {
        final Sample sample = sampleDAO.readByStringId(sampleId);
        populateModel(model, sample);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model, final Sample sample) {
        final AnalysisStatsModel mgModel = MGModelFactory.getAnalysisStatsModel(sessionManager, sample);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, mgModel);
    }
}
