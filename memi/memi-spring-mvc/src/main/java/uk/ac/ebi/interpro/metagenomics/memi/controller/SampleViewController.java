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
import uk.ac.ebi.interpro.metagenomics.memi.basic.comparators.EmgSampleAnnComparator;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgLogFileInfoDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.ISampleStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.temp.SampleAnnotationDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SampleViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SampleViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.tools.MemiTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Represents the controller for sample overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/" + SampleViewController.VIEW_NAME + "/{sampleId}")
public class SampleViewController extends SecuredAbstractController<Sample> {
    private static final Log log = LogFactory.getLog(SampleViewController.class);
    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "sample";

    @Resource
    private HibernateSampleDAO sampleDAO;

    @Resource
    private EmgLogFileInfoDAO fileInfoDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @Resource(name = "annotationDAO")
    private SampleAnnotationDAO sampleAnnotationDAO;

    //GET request method

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGetSample(final ModelMap model, @PathVariable final String sampleId) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building model...");
                populateModel(model, sample);
            }
        }, model, sampleId, getModelViewName());
    }


    @RequestMapping(value = "/doExportDetails", method = RequestMethod.GET)
    public ModelAndView doExportDetails(@PathVariable final String sampleId, ModelMap model, final HttpServletResponse response) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building model...");
                populateModel(model, sample);
                if (downloadService != null) {
                    Set<String> samplesIDs = new HashSet<String>();
                    samplesIDs.add(sampleId);
                    boolean isDialogOpen = downloadService.openDownloadDialog(response, sample.getClazz(), samplesIDs);
                    model.addAttribute("isDialogOpen", isDialogOpen);
                }
            }
        }, model, sampleId, getModelViewName());
    }

    /**
     * Creates the home page model and adds it to the specified model map.
     */
    private void populateModel(final ModelMap model, final Sample sample) {
        final String pageTitle = "Sample overview: " + sample.getSampleName() + " - EBI metagenomics";
        List<EmgSampleAnnotation> sampleAnnotations = (List<EmgSampleAnnotation>) sampleAnnotationDAO.getSampleAnnotations(sample.getId());
        Collections.sort(sampleAnnotations, new EmgSampleAnnComparator());
        final ViewModelBuilder<SampleViewModel> builder = new SampleViewModelBuilder(sessionManager,
                pageTitle, getBreadcrumbs(sample), propertyContainer, sample,
                MemiTools.getArchivedSeqs(fileInfoDAO, sample), sampleAnnotations);
        final SampleViewModel sampleModel = builder.getModel();
        sampleModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, sampleModel);
    }

    ISampleStudyDAO<Sample> getDAO() {
        return sampleDAO;
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (entity != null && entity instanceof Sample) {
            result.add(new Breadcrumb("Project: " + ((Sample) entity).getStudy().getStudyName(), "View project " + ((Sample) entity).getStudy().getStudyName(), StudyViewController.VIEW_NAME + '/' + ((Sample) entity).getStudy().getStudyId()));
            result.add(new Breadcrumb("Sample: " + ((Sample) entity).getSampleName(), "View project " + ((Sample) entity).getSampleName(), VIEW_NAME + '/' + ((Sample) entity).getSampleId()));
        }
        return result;
    }
}
