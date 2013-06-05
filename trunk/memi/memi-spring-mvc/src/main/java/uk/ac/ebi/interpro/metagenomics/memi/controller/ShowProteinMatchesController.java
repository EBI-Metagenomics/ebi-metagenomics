package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;

import java.util.List;

/**
 * Simple extension of {@link SampleViewController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
public class ShowProteinMatchesController extends AbstractSampleViewController {

    private static final Log log = LogFactory.getLog(ShowProteinMatchesController.class);

    @RequestMapping('/' + SampleViewController.VIEW_NAME + "/{sampleId}/showProteinMatches")
    public ModelAndView showProteinMatches(final ModelMap model, @PathVariable final String sampleId) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building model...");
                String pageTitle="InterPro match summary: "+ sample.getSampleName()+"";
                populateModel(model, sample, false, pageTitle);
            }
        }, model, sampleId, "showProteinMatches");
    }

    @Override
    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = super.getBreadcrumbs(entity);
        if (entity != null && entity instanceof Sample) {
            result.add(new Breadcrumb("InterPro matches", "View InterPro matches", VIEW_NAME + '/' + ((Sample) entity).getSampleId() + "/showProteinMatches"));
        }
        return result;
    }
}