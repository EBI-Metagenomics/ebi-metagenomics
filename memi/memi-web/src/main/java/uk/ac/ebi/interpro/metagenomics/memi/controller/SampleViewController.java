package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * The controller for analysis overview page.
 *
 * @author Phil Jones, EMBL-EBI, InterPro
 * @author Maxim Scheremetjew
 * @since 1.0-SNAPSHOT
 */
@Controller
public class SampleViewController extends AbstractSampleViewController {
    private static final Log log = LogFactory.getLog(SampleViewController.class);

    protected String getModelViewName() {
        return "sample";
    }

    @Resource
    private MemiDownloadService downloadService;

    @Resource
    private SampleDAO sampleDAO;

    @Resource
    private StudyDAO studyDAO;

    /**
     * @param projectId External project identifier (e.g. in ENA, for instance ERP000001)
     * @param sampleId  External sample identifier (e.g. in ENA, for instance ERS580795)
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE)
    public ModelAndView doGetSample(@PathVariable final String projectId,
                                    @PathVariable final String sampleId,
                                    final ModelMap model) throws IOException {
        Study study = studyDAO.readByStringId(projectId);
        model.addAttribute("study", study);
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(projectId, sampleId), getModelViewName());
    }

    private Sample getSecuredEntity(final String projectId,
                                    final String sampleId) {
        return sampleDAO.readBySampleIdAndStudyId(projectId, sampleId);
    }

    private ModelProcessingStrategy<Sample> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building sample view model...");
                populateModel(model, sample);
            }
        };
    }
}
