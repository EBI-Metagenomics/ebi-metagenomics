package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgLogFileInfoDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.ISampleStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AnalysisStatsModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.tools.MemiTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The controller for analysis overview page.
 *
 * @author Phil Jones, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping('/' + AnalysisStatsController.VIEW_NAME + "/{sampleId}")
public class AnalysisStatsController extends SecuredAbstractController<Sample> {
    private static final Log log = LogFactory.getLog(AnalysisStatsController.class);
    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "analysisStatsView";

    @Resource
    private HibernateSampleDAO sampleDAO;

    @Resource
    private EmgLogFileInfoDAO fileInfoDAO;

    @Resource
    private MemiDownloadService downloadService;

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

//    @RequestMapping(value = "/doExportGOSlimFile", method = RequestMethod.GET)
//    public ModelAndView doExportGOSlimFile(@PathVariable final String sampleId, ModelMap model, final HttpServletResponse response) {
//        return handleExport(sampleId, model, response, EmgFile.EmgFileExtension.GO_SLIM.getFileExtension(), "_GO_slim.csv");
//    }

    @RequestMapping(value = "/doExportGOFile", method = RequestMethod.GET)
    public ModelAndView doExportGOFile(@PathVariable final String sampleId, final ModelMap model,
                                       final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, model, response, request, EmgFile.EmgFileExtension.GO.getFileExtension(), "_GO.csv");
    }

    @RequestMapping(value = "/doExportMaskedFASTAFile", method = RequestMethod.GET)
    public ModelAndView doExportMaskedFASTAFile(@PathVariable final String sampleId, final ModelMap model,
                                                final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, model, response, request, EmgFile.EmgFileExtension.MASKED_FASTA.getFileExtension(), "_nt_reads.fasta");
    }

    @RequestMapping(value = "/doExportCDSFile", method = RequestMethod.GET)
    public ModelAndView doExportCDSFile(@PathVariable final String sampleId, final ModelMap model,
                                        final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, model, response, request, EmgFile.EmgFileExtension.CDS_FAA.getFileExtension(), "_pCDS.fasta");
    }

    @RequestMapping(value = "/doExportI5File", method = RequestMethod.GET)
    public ModelAndView doExportI5File(@PathVariable final String sampleId, final ModelMap model,
                                       final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, model, response, request, EmgFile.EmgFileExtension.I5_TSV.getFileExtension(), "_InterPro.tsv");
    }

    @RequestMapping(value = "/doExportIPRFile", method = RequestMethod.GET)
    public ModelAndView doExportIPRFile(@PathVariable final String sampleId, final ModelMap model,
                                        final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, model, response, request, EmgFile.EmgFileExtension.IPR.getFileExtension(), "_InterPro_sum.csv");
    }

    private ModelAndView handleExport(final String sampleId, ModelMap model, final HttpServletResponse response,
                                      final HttpServletRequest request, final String fileNameSuffix,
                                      final String fileExtension) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building model...");
                populateModel(model, sample);
                EmgFile emgFile = ((AnalysisStatsModel) model.get(ViewModel.MODEL_ATTR_NAME)).getEmgFile();

                String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
                File file = new File(propertyContainer.getPathToAnalysisDirectory() + directoryName + '/' + directoryName + fileNameSuffix);

                if (downloadService != null) {
                    //white spaces are replaced by underscores
                    downloadService.openDownloadDialog(response, request, file, emgFile.getFileName().replace(" ", "_") + fileExtension, false);
                }
            }
        }, model, sampleId, getModelViewName());
    }

    protected void populateModel(final ModelMap model, final Sample sample, boolean isReturnSizeLimit) {
        String pageTitle = "Sample analysis results: " + sample.getSampleName() + " - EBI metagenomics";
        populateModel(model, sample, isReturnSizeLimit, pageTitle);
    }

    /**
     * Creates the home page model and adds it to the specified model map.
     */
    protected void populateModel(final ModelMap model, final Sample sample, boolean isReturnSizeLimit, String pageTitle) {
        List<EmgFile> emgFiles = fileInfoDAO.getFilesBySampleId(sample.getId());
        //TODO: For the moment the system only allows to represent one file on the analysis page, but
        //in the future it should be possible to represent all different data types (genomic, transcripomic)
        EmgFile emgFile = (emgFiles.size() > 0 ? emgFiles.get(0) : null);
        if (emgFile != null) {
            emgFile.addFileSizeMap(getFileSizeMap(emgFile));
        }
//         TODO: The following 'if' case is a quick and dirty solution to solve the differentiation issue between genomic and transcriptomic analysis
        AnalysisStatsModel.ExperimentType experimentType = AnalysisStatsModel.ExperimentType.GENOMIC;
        if (sample.getId() == 367) {
            experimentType = AnalysisStatsModel.ExperimentType.TRANSCRIPTOMIC;
        }
        final AnalysisStatsModel mgModel = MGModelFactory.
                getAnalysisStatsModel(sessionManager, sample, pageTitle, getBreadcrumbs(sample), emgFile,
                        MemiTools.getArchivedSeqs(fileInfoDAO, sample), propertyContainer, isReturnSizeLimit, experimentType);
        mgModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, mgModel);
    }

    /**
     * Creates a map between file names and file sizes (for all downloadable files on analysis page). The file size of smaller files
     * (cutoff: 1024x1024 bytes) is shown in KB, for all the other files it is shown in MB.
     */
    private Map<String, String> getFileSizeMap(EmgFile emgFile) {
        Map<String, String> result = new HashMap<String, String>();
        String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
        for (EmgFile.EmgFileExtension fileExtension : EmgFile.EmgFileExtension.values()) {
            File file = new File(propertyContainer.getPathToAnalysisDirectory() + directoryName + '/' + directoryName + fileExtension);
            if (file.canRead()) {
                long cutoff = 1024 * 1024;
                if (file.length() > cutoff) {
                    long fileSize = file.length() / (long) (1024 * 1024);
                    result.put(fileExtension.getFileExtension(), "(" + fileSize + " MB)");
                } else {
                    long fileSize = file.length() / (long) 1024;
                    result.put(fileExtension.getFileExtension(), "(" + fileSize + " KB)");
                }
            }
        }
        return result;
    }

    /**
     * Example for pattern '000000.000':<br>
     * 123.78  000000.000  000123.780
     */
    private String getCustomFormat(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(value);
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     */
    private void populateModel(final ModelMap model, final Sample sample) {
        populateModel(model, sample, true);
    }

    @Override
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
            result.add(new Breadcrumb("Sample: " + ((Sample) entity).getSampleName(), "View sample " + ((Sample) entity).getSampleName(), SampleViewController.VIEW_NAME + '/' + ((Sample) entity).getSampleId()));
            result.add(new Breadcrumb("Analysis Results", "View analysis results", VIEW_NAME + '/' + ((Sample) entity).getSampleId()));
        }
        return result;
    }
}
