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
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AnalysisStatsModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.tools.MemiTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value = "/showProteinMatches", method = RequestMethod.GET)
    public ModelAndView showProteinMatches(final ModelMap model, @PathVariable final String sampleId) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building model...");
                populateModel(model, sample, false);
            }
        }, model, sampleId, "showProteinMatches");
    }

    @RequestMapping(value = "/doExportGOSlimFile/{fileName}", method = RequestMethod.GET)
    public ModelAndView doExportGOSlimFile(@PathVariable final String sampleId, @PathVariable final String fileName, ModelMap model, final HttpServletResponse response) {
        return handleExport(sampleId, model, response, "_summary.go_slim", "_GO_slim.tsv");
    }

    @RequestMapping(value = "/doExportGOFile/{fileName}", method = RequestMethod.GET)
    public ModelAndView doExportGOFile(@PathVariable final String sampleId, @PathVariable final String fileName, final ModelMap model, final HttpServletResponse response) {
        return handleExport(sampleId, model, response, "_summary.go", "_GO.tsv");
    }

    @RequestMapping(value = "/doExportMaskedFASTAFile/{fileName}", method = RequestMethod.GET)
    public ModelAndView doExportMaskedFASTAFile(@PathVariable final String sampleId, @PathVariable final String fileName, final ModelMap model, final HttpServletResponse response) {
        return handleExport(sampleId, model, response, "_masked.fasta", "_nt_reads.fasta");
    }

    @RequestMapping(value = "/doExportCDSFile/{fileName}", method = RequestMethod.GET)
    public ModelAndView doExportCDSFile(@PathVariable final String sampleId, @PathVariable final String fileName, final ModelMap model, final HttpServletResponse response) {
        return handleExport(sampleId, model, response, "_CDS.faa", "_pCDS.fasta");
    }

    @RequestMapping(value = "/doExportI5File/{fileName}", method = RequestMethod.GET)
    public ModelAndView doExportI5File(@PathVariable final String sampleId, @PathVariable final String fileName, final ModelMap model, final HttpServletResponse response) {
        return handleExport(sampleId, model, response, "_I5.tsv", "_InterPro.tsv");
    }

    @RequestMapping(value = "/doExportIPRFile/{fileName}", method = RequestMethod.GET)
    public ModelAndView doExportIPRFile(@PathVariable final String sampleId, @PathVariable final String fileName, final ModelMap model, final HttpServletResponse response) {
        return handleExport(sampleId, model, response, "_summary.ipr", "_InterPro_sum.tsv");
    }

    private ModelAndView handleExport(final String sampleId, ModelMap model, final HttpServletResponse response,
                                      final String fileNameSuffix, final String fileExtension) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building model...");
                populateModel(model, sample);
                EmgFile emgFile = ((AnalysisStatsModel) model.get(MGModel.MODEL_ATTR_NAME)).getEmgFile();

                String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
                File file = new File(propertyContainer.getPathToAnalysisDirectory() + directoryName + '/' + directoryName + fileNameSuffix);

                if (downloadService != null) {
                    downloadService.openDownloadDialog(response, file, emgFile.getFileName() + fileExtension, false);
                }
            }
        }, model, sampleId, getModelViewName());
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     */
    private void populateModel(final ModelMap model, final Sample sample, boolean isReturnSizeLimit) {
        String pageTitle = "Results " + sample.getSampleName();
        List<EmgFile> emgFiles = fileInfoDAO.getFilesBySampleId(sample.getSampleId());
        //TODO: For the moment the system only allows to represent one file on the analysis page, but
        //in the future it should be possible to represent all different data types (genomic, transcripomic)
        EmgFile emgFile = (emgFiles.size() > 0 ? emgFiles.get(0) : null);
        final AnalysisStatsModel mgModel = MGModelFactory.
                getAnalysisStatsModel(sessionManager, sample, pageTitle, getBreadcrumbs(sample), emgFile,
                        MemiTools.getArchivedSeqs(fileInfoDAO, sample), propertyContainer, isReturnSizeLimit);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, mgModel);
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