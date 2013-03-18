package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgLogFileInfoDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.ISampleStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AnalysisStatsModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadLink;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadSection;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FilePathNameBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.tools.MemiTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

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
    private SampleDAO sampleDAO;

    @Resource
    private EmgLogFileInfoDAO fileInfoDAO;

    @Resource
    private MemiDownloadService downloadService;

    private final String[] requestParamValues = new String[]{"biom", "taxa"};

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGetSample(@PathVariable final String sampleId,
                                    @RequestParam(required = false, value = "export") final String export,
                                    final ModelMap model,
                                    final HttpServletResponse response,
                                    final HttpServletRequest request) {
        log.info("Checking if sample is accessible...");
        if (export != null) {
            if (export.equalsIgnoreCase(this.requestParamValues[0])) {
                return handleExport(sampleId, response, request, EmgFile.ResultFileType.BIOM, ".biom");
            } else if (export.equalsIgnoreCase(this.requestParamValues[1])) {
                return handleExport(sampleId, response, request, EmgFile.ResultFileType.TAB_SEPARATED_TAX_RESULT_FILE, ".tsv");
            }
        }
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building model...");
                populateModel(model, sample);
            }
        }, model, sampleId, getModelViewName());
    }

    @RequestMapping(value = "/doExportGOSlimFile", method = RequestMethod.GET)
    public ModelAndView doExportGOSlimFile(@PathVariable final String sampleId,
                                           final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, response, request, EmgFile.ResultFileType.GO_SLIM, "_GO_slim.csv");
    }

    @RequestMapping(value = "/doExportGOFile", method = RequestMethod.GET)
    public ModelAndView doExportGOFile(@PathVariable final String sampleId,
                                       final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, response, request, EmgFile.ResultFileType.GO, "_GO.csv");
    }

    @RequestMapping(value = "/doExportMaskedFASTAFile", method = RequestMethod.GET)
    public ModelAndView doExportMaskedFASTAFile(@PathVariable final String sampleId,
                                                final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, response, request, EmgFile.ResultFileType.MASKED_FASTA, "_nt_reads.fasta");
    }

    @RequestMapping(value = "/doExportCDSFile", method = RequestMethod.GET)
    public ModelAndView doExportCDSFile(@PathVariable final String sampleId,
                                        final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, response, request, EmgFile.ResultFileType.CDS_FAA, "_pCDS.fasta");
    }

    @RequestMapping(value = "/doExportI5TSVFile", method = RequestMethod.GET)
    public ModelAndView doExportI5File(@PathVariable final String sampleId,
                                       final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, response, request, EmgFile.ResultFileType.I5_TSV, "_InterPro.tsv");
    }

    @RequestMapping(value = "/doExportIPRFile", method = RequestMethod.GET)
    public ModelAndView doExportIPRFile(@PathVariable final String sampleId,
                                        final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, response, request, EmgFile.ResultFileType.IPR, "_InterPro_sum.csv");
    }

    @RequestMapping(value = "/doExportIPRhitsFile", method = RequestMethod.GET)
    public ModelAndView doExportIPRhitsFile(@PathVariable final String sampleId,
                                            final HttpServletResponse response, final HttpServletRequest request) {
        return handleExport(sampleId, response, request, EmgFile.ResultFileType.IPR_HITS, "_InterPro_hits.fasta");
    }

    /**
     * @param sampleId
     * @param response
     * @param request
     * @param resultFileType
     * @param fileNameEnd    - Defines the file name extension and a file name suffix for the download file itself.
     * @return
     */
    //TODO: Parameter name fileExtension is a bit misleading
    private ModelAndView handleExport(final String sampleId, final HttpServletResponse response,
                                      final HttpServletRequest request, final EmgFile.ResultFileType resultFileType,
                                      final String fileNameEnd) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Open download dialog...");
                final EmgFile emgFile = getEmgFile(sample.getId());
                if (emgFile != null) {
                    String filePathName = FilePathNameBuilder.getFilePathNameByResultType(resultFileType, emgFile, propertyContainer);
                    createFileObjectAndOpenDownloadDialog(response, request, emgFile, fileNameEnd, filePathName);
                }
            }
        }, null, sampleId, getModelViewName());
    }

    private void createFileObjectAndOpenDownloadDialog(final HttpServletResponse response,
                                                       final HttpServletRequest request,
                                                       final EmgFile emgFile,
                                                       final String fileNameEnd,
                                                       final String filePathName) {
        File file = new File(filePathName);
        if (downloadService != null) {
            //white spaces are replaced by underscores
            final String fileNameForDownload = getFileName(emgFile, fileNameEnd);
            downloadService.openDownloadDialog(response, request, file, fileNameForDownload, false);
        }
    }

    private String getFileName(final EmgFile emgFile,
                               final String fileNameEnd) {
        return emgFile.getFileName().replace(" ", "_") + fileNameEnd;
    }

    private EmgFile getEmgFile(final long sampleId) {
        List<EmgFile> emgFiles = fileInfoDAO.getFilesBySampleId(sampleId);
        return (emgFiles.size() > 0 ? emgFiles.get(0) : null);
    }

    protected void populateModel(final ModelMap model, final Sample sample, boolean isReturnSizeLimit) {
        String pageTitle = "Sample analysis results: " + sample.getSampleName() + "";
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
        //Create a list of existing downloadable file for the download section on the analysis page
        final List<File> downloadableFiles = FilePathNameBuilder.createListOfDownloadableFiles(emgFile, propertyContainer);

//         TODO: The following 'if' case is a quick and dirty solution to solve the differentiation issue between genomic and transcriptomic analysis
        AnalysisStatsModel.ExperimentType experimentType = AnalysisStatsModel.ExperimentType.GENOMIC;
        if (sample.getId() == 367) {
            experimentType = AnalysisStatsModel.ExperimentType.TRANSCRIPTOMIC;
        }
        final AnalysisStatsModel mgModel = MGModelFactory.
                getAnalysisStatsModel(sessionManager, sample, pageTitle, getBreadcrumbs(sample), emgFile,
                        MemiTools.getArchivedSeqs(fileInfoDAO, sample), propertyContainer, isReturnSizeLimit, experimentType, buildDownloadSection(sample.getSampleId(), downloadableFiles));
        mgModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, mgModel);
    }

    private DownloadSection buildDownloadSection(final String sampleId, final List<File> downloadableFiles) {
        final List<DownloadLink> seqDataDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> funcAnalysisDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> taxaAnalysisDownloadLinks = new ArrayList<DownloadLink>();

        seqDataDownloadLinks.add(new DownloadLink("Submitted nucleotide reads (ENA website)",
                "Click to download all submitted nucleotide data on the ENA website",
                "https://www.ebi.ac.uk/ena/data/view/" + sampleId + "?display=html", true, 1));

        for (File file : downloadableFiles) {
            if (file.getName().endsWith(EmgFile.ResultFileType.MASKED_FASTA.getFileNameEnd())) {
                seqDataDownloadLinks.add(new DownloadLink("Processed nucleotide reads (FASTA)",
                        "Click to download processed fasta nucleotide sequences",
                        "analysisStatsView/" + sampleId + "/doExportMaskedFASTAFile",
                        2,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.CDS_FAA.getFileNameEnd())) {
                seqDataDownloadLinks.add(new DownloadLink("Predicted CDS (FASTA)",
                        "Click to download predicted CDS in fasta format",
                        "analysisStatsView/" + sampleId + "/doExportCDSFile",
                        3,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.I5_TSV.getFileNameEnd())) {
                funcAnalysisDownloadLinks.add(new DownloadLink("InterPro matches (TSV)",
                        "Click to download full InterPro matches table (TSV)",
                        "analysisStatsView/" + sampleId + "/doExportI5TSVFile",
                        4,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.GO.getFileNameEnd())) {
                funcAnalysisDownloadLinks.add(new DownloadLink("GO annotation result file (CSV)",
                        "Click to download GO annotation result file (CSV)",
                        "analysisStatsView/" + sampleId + "/doExportGOFile",
                        5,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.IPR_HITS.getFileNameEnd())) {
                funcAnalysisDownloadLinks.add(new DownloadLink("pCDS with InterPro matches (FASTA)",
                        "Click to download predicted CDS with InterPro matches (FASTA)",
                        "analysisStatsView/" + sampleId + "/doExportIPRhitsFile",
                        6,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.BIOM.getFileNameEnd())) {
                taxaAnalysisDownloadLinks.add(new DownloadLink("biom file format: Version 1.0",
                        "Click to download the biom formatted file",
                        "analysisStatsView/" + sampleId + "?export=" + this.requestParamValues[0],
                        7,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.TAB_SEPARATED_TAX_RESULT_FILE.getFileNameEnd())) {
                taxaAnalysisDownloadLinks.add(new DownloadLink("Humand readable taxonomy result file (TSV)",
                        "Click to download the human readable version",
                        "analysisStatsView/" + sampleId + "?export=" + this.requestParamValues[1],
                        8,
                        getFileSize(file)));
            }


        }
        Collections.sort(seqDataDownloadLinks, DownloadLink.DownloadLinkComparator);
        Collections.sort(funcAnalysisDownloadLinks, DownloadLink.DownloadLinkComparator);
        Collections.sort(taxaAnalysisDownloadLinks, DownloadLink.DownloadLinkComparator);
        return new DownloadSection(seqDataDownloadLinks, funcAnalysisDownloadLinks, taxaAnalysisDownloadLinks);
    }

    private String getFileSize(final File file) {
        if (file.canRead()) {
            long cutoff = 1024 * 1024;
            if (file.length() > cutoff) {
                long fileSize = file.length() / (long) (1024 * 1024);
                return fileSize + " MB";
            } else {
                long fileSize = file.length() / (long) 1024;
                return fileSize + " KB";
            }
        }
        return "";
    }


    /**
     * Creates a map between file names and file sizes (for all downloadable files on analysis page). The file size of smaller files
     * (cutoff: 1024x1024 bytes) is shown in KB, for all the other files it is shown in MB.
     */
    private Map<String, String> getFileSizeMap(EmgFile emgFile) {
        Map<String, String> result = new HashMap<String, String>();
        String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
        for (EmgFile.ResultFileType fileExtension : EmgFile.ResultFileType.values()) {
            File file = new File(propertyContainer.getPathToAnalysisDirectory() + directoryName + '/' + directoryName + fileExtension);
            if (file.canRead()) {
                long cutoff = 1024 * 1024;
                if (file.length() > cutoff) {
                    long fileSize = file.length() / (long) (1024 * 1024);
                    result.put(fileExtension.getFileNameEnd(), "(" + fileSize + " MB)");
                } else {
                    long fileSize = file.length() / (long) 1024;
                    result.put(fileExtension.getFileNameEnd(), "(" + fileSize + " KB)");
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
     * Creates the analysis page model and adds it to the specified model map.
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
