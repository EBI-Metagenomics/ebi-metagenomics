package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.MGPortalURLCollection;
import uk.ac.ebi.interpro.metagenomics.memi.controller.ModelProcessingStrategy;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

/**
 * Download tab on the project page.
 */
@Controller
public class DownloadStudyController extends AbstractStudyViewController {

    private static final Log log = LogFactory.getLog(DownloadStudyController.class);

    protected String getModelViewName() {
        return "tabs/study/download";
    }

    private ModelProcessingStrategy<Study> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Study>() {
            @Override
            public void processModel(ModelMap model, Study study) {
                log.info("Building download view model...");
                populateModel(model, study);
            }
        };
    }

    /**
     * Request method for the download tab on the sample view page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_DOWNLOAD)
    public ModelAndView ajaxLoadDownloadTab(@PathVariable final String studyId,
                                            final ModelMap model) {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(studyId), getModelViewName());
    }

    protected void populateModel(final ModelMap model, final Study study) {
        model.addAttribute("study", study);
    }
}
