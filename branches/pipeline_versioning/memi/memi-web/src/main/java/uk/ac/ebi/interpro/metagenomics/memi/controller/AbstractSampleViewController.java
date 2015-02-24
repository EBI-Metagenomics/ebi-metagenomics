package uk.ac.ebi.interpro.metagenomics.memi.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.AnalysisJobDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.ISecureEntityDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.temp.SampleAnnotationDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * This class extends {@link SampleViewController}, {@link KronaChartsController} and {@link ResultViewExportController}.
 */
public abstract class AbstractSampleViewController extends SecuredAbstractController<Sample> {

    private static final Log log = LogFactory.getLog(AbstractSampleViewController.class);

    @Resource
    private AnalysisJobDAO analysisJobDAO;

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (entity != null && entity instanceof Sample) {
            result.add(new Breadcrumb("Project: " + ((Sample) entity).getStudy().getStudyName(), "View project " + ((Sample) entity).getStudy().getStudyName(), StudyViewController.VIEW_NAME + '/' + ((Sample) entity).getStudy().getStudyId()));
            result.add(new Breadcrumb("Sample: " + ((Sample) entity).getSampleName(), "View sample " + ((Sample) entity).getSampleName(), getModelViewName() + '/' + ((Sample) entity).getSampleId()));
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

        List<AnalysisJob> analysisJobs = analysisJobDAO.readBySampleId(sample.getId(), "completed");

        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Sample page", getBreadcrumbs(null), propertyContainer);
        final ViewModel defaultViewModel = builder.getModel();
        defaultViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_CONTACT_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
        model.addAttribute("analysisJobs", analysisJobs);


//        EmgFile emgFile = getEmgFile(sample.getId());
//        //TODO: For the moment the system only allows to represent one file on the analysis page, but
//        //in the future it should be possible to represent all different data types (genomic, transcriptomic)
//        ResultViewModel.ExperimentType experimentType = ResultViewModel.ExperimentType.GENOMIC;
//        final List<EmgSampleAnnotation> sampleAnnotations = (List<EmgSampleAnnotation>) sampleAnnotationDAO.getSampleAnnotations(sample.getId());
//
//
//        final ViewModelBuilder<ResultViewModel> builder = new ResultViewModelBuilder(
//                sessionManager,
//                sample,
//                pageTitle,
//                getBreadcrumbs(sample),
//                emgFile,
//                MemiTools.getArchivedSeqs(fileInfoDAO, sample),
//                propertyContainer,
//                experimentType,
//                buildDownloadSection(sample, fileDefinitionsMap, emgFile),
//                sampleAnnotations,
//                qualityControlFileDefinitions,
//                functionalAnalysisFileDefinitions,
//                taxonomicAnalysisFileDefinitions);
//        final ResultViewModel sampleModel = builder.getModel();
//        //End
//
//        sampleModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
//        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
//        model.addAttribute(ViewModel.MODEL_ATTR_NAME, sampleModel);
    }
}
