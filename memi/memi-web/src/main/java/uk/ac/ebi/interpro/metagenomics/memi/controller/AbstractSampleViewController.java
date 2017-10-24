package uk.ac.ebi.interpro.metagenomics.memi.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.AnalysisJobDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.temp.SampleAnnotationDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.*;
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
    private StudyDAO studyDAO;

    @Resource
    protected SampleAnnotationDAO sampleAnnotationDAO;

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (entity != null && entity instanceof Sample) {
            Sample sample = (Sample) entity;
            String projectId = sample.getExternalProjectId();
            Study study = studyDAO.readByStringId(projectId);
            if (study != null) {
                // If private study, check if it belongs to the right user
                if (isStudyAccessible(study)) {
                    String studyName = study.getStudyName();
                    String sampleURL = "projects/" + sample.getExternalProjectId() + "/samples/" + sample.getSampleId();
                    String projectURL = "projects/" + sample.getExternalProjectId();
                    result.add(new Breadcrumb("Project: " + studyName, "View project " + studyName, projectURL));
                    result.add(new Breadcrumb("Sample: " + sample.getSampleName(), "View sample " + sample.getSampleName(), sampleURL));
                }
            }
        }
        return result;
    }

    protected boolean isStudyAccessible(final Study study) {
        if (study.isPublic()) {
            return true;
        }
        Submitter submitter = super.getSessionSubmitter();
        String accountId = null;
        if (submitter != null) {
            accountId = submitter.getSubmissionAccountId();
        }
        return study.getSubmissionAccountId().equalsIgnoreCase(accountId);
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
        String projectId = sample.getExternalProjectId();
        Study study = studyDAO.readByStringId(projectId);

        List<AnalysisJob> analysisJobs = analysisJobDAO.readNonSuppressedBySampleId(sample.getId(), study.getId());
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
        model.addAttribute("isStudyAccessible", isStudyAccessible(study));
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