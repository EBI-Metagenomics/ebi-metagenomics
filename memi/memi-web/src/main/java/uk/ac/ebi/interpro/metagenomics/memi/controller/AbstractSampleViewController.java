package uk.ac.ebi.interpro.metagenomics.memi.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.AnalysisJobDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.temp.SampleAnnotationDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * This class extends {@link SampleViewController}, {@link KronaChartsController} and {@link ResultViewExportController}.
 */
public abstract class AbstractSampleViewController extends SecuredAbstractController<Sample> {

    private static final Log log = LogFactory.getLog(AbstractSampleViewController.class);

    @Resource
    private AnalysisJobDAO analysisJobDAO;

    @Resource
    protected SampleAnnotationDAO sampleAnnotationDAO;

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (entity != null && entity instanceof Sample) {
            Sample sample = (Sample) entity;
            String sampleURL = "projects/" + sample.getStudy().getStudyId() + "/samples/" + sample.getSampleId();
            String projectURL = "projects/" + sample.getStudy().getStudyId();
            result.add(new Breadcrumb("Project: " + sample.getStudy().getStudyName(), "View project " + sample.getStudy().getStudyName(), projectURL));
            result.add(new Breadcrumb("Sample: " + sample.getSampleName(), "View sample " + sample.getSampleName(), sampleURL));
        }
        return result;
    }

    /**
     * Creates the analysis page model and adds it to the specified model map.
     */
    protected void populateModel(final ModelMap model, final Sample sample) {
        String pageTitle = "Sample: " + sample.getSampleId() + "";
        populateModel(model, sample, pageTitle);
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     */
    protected void populateModel(final ModelMap model, final Sample sample, final String pageTitle) {

        List<AnalysisJob> analysisJobs = analysisJobDAO.readNonSuppressedBySampleId(sample.getId());
        final List<EmgSampleAnnotation> sampleAnnotations = (List<EmgSampleAnnotation>) sampleAnnotationDAO.getSampleAnnotations(sample.getId());

        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(userManager, getEbiSearchForm(),
                pageTitle, getBreadcrumbs(sample), propertyContainer);
        final ViewModel defaultViewModel = builder.getModel();
        final boolean isHostAssociated = isHostAssociated(sample);
        defaultViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
        model.addAttribute("analysisJobs", analysisJobs);
        model.addAttribute("sample", sample);
        model.addAttribute("hostAssociated", isHostAssociated);
        model.addAttribute("sampleAnnotations", sampleAnnotations);
    }

    private boolean isHostAssociated(Sample sample) {
        if (sample != null) {
            Biome biome = sample.getBiome();
            if (biome != null) {
                String lineage = biome.getLineage();
                if (lineage != null) {
                    if (lineage.startsWith("root:Host-associated")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}