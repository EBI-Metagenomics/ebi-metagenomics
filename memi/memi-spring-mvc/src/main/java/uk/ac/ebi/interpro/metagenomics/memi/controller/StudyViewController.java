package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.ISampleStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.StudyViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.tools.MemiTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Represents the controller for the study overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/" + StudyViewController.VIEW_NAME + "/{studyId}")
public class StudyViewController extends SecuredAbstractController<Study> {

//    private interface ModelProcessingStrategy {
//        void processModel(ModelMap model, Study study);
//    }

    private final static Log log = LogFactory.getLog(StudyViewController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "study";

    @Resource
    private HibernateStudyDAO studyDAO;

    @Resource
    private HibernateSampleDAO sampleDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    //GET Methods

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGetStudy(final ModelMap model, @PathVariable final String studyId) {
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Study>() {
            @Override
            public void processModel(ModelMap model, Study study) {
                populateModel(model, study);
            }
        }, model, studyId);
    }

    @RequestMapping(value = "/doExport", method = RequestMethod.GET)
    public ModelAndView doExportSamples(final ModelMap model, final HttpServletResponse response, @PathVariable final String studyId) throws Exception {
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Study>() {
            @Override
            public void processModel(ModelMap model, Study study) {
                populateModel(model, study);

                List<Sample> samples = ((StudyViewModel) model.get(StudyViewModel.MODEL_ATTR_NAME)).getSamples();
                if (samples != null && samples.size() > 0) {
                    String[] samplesIDs = MemiTools.getSampleIds(samples);
                    if (downloadService != null) {
                        boolean isDialogOpen = downloadService.openDownloadDialog(response, samples.get(0).getClazz(), samplesIDs);
                        model.addAttribute("isDialogOpen", isDialogOpen);
                    } else {
                        log.warn("Could not open download dialog to export samples. Download service is null!");
                    }
                } else {
                    log.info("There are no samples to be exported!");
                }
            }
        }, model, studyId);
    }

    /**
     * Creates a {@link StudyViewModel} and adds it to the specified model map.
     */

    private void populateModel(ModelMap model, final Study study) {
        List<Sample> samples = sampleDAO.retrieveSamplesByStudyId(study.getId());
        final StudyViewModel studyModel = MGModelFactory.getStudyViewModel(sessionManager, study, samples);
        model.addAttribute(StudyViewModel.MODEL_ATTR_NAME, studyModel);
    }

    ISampleStudyDAO<Study> getDAO() {
        return studyDAO;
    }

    String getModelViewName() {
        return VIEW_NAME;
    }
}