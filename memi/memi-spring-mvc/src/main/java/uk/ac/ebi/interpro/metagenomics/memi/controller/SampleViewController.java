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
import uk.ac.ebi.interpro.metagenomics.memi.dao.temp.SampleAnnotationDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadLink;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadSection;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FilePathNameBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SampleViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
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
@RequestMapping('/' + SampleViewController.VIEW_NAME + "/{sampleId}")
public class SampleViewController extends SecuredAbstractController<Sample> {
    private static final Log log = LogFactory.getLog(SampleViewController.class);
    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "sample";

    @Resource
    private SampleDAO sampleDAO;

    @Resource
    private EmgLogFileInfoDAO fileInfoDAO;

    @Resource
    private SampleAnnotationDAO sampleAnnotationDAO;

    @Resource
    private MemiDownloadService downloadService;

    private final String[] requestParamValues = new String[]{"biom", "taxa", "tree"};

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGetSample(@PathVariable final String sampleId,
                                    @RequestParam(required = false, value = "export") final String export,
                                    final ModelMap model,
                                    final HttpServletResponse response,
                                    final HttpServletRequest request) {
        log.info("Checking if sample is accessible...");
        if (export != null) {
            if (export.equalsIgnoreCase(this.requestParamValues[0])) {
                return handleExport(sampleId, response, request, EmgFile.ResultFileType.TAX_ANALYSIS_BIOM_FILE, "_otu.biom");
            } else if (export.equalsIgnoreCase(this.requestParamValues[1])) {
                return handleExport(sampleId, response, request, EmgFile.ResultFileType.TAX_ANALYSIS_TSV_FILE, "_otu.tsv");
            } else if (export.equalsIgnoreCase(this.requestParamValues[2])) {
                return handleExport(sampleId, response, request, EmgFile.ResultFileType.TAX_ANALYSIS_TREE_FILE, "_newick.tre");
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
        SampleViewModel.ExperimentType experimentType = SampleViewModel.ExperimentType.GENOMIC;
        if (sample.getId() == 367) {
            experimentType = SampleViewModel.ExperimentType.TRANSCRIPTOMIC;
        }
        //New
        List<EmgSampleAnnotation> sampleAnnotations = (List<EmgSampleAnnotation>) sampleAnnotationDAO.getSampleAnnotations(sample.getId());

        final ViewModelBuilder<SampleViewModel> builder = new SampleViewModelBuilder(
                sessionManager,
                sample,
                pageTitle,
                getBreadcrumbs(sample),
                emgFile,
                MemiTools.getArchivedSeqs(fileInfoDAO, sample),
                propertyContainer,
                isReturnSizeLimit,
                experimentType,
                buildDownloadSection(sample.getSampleId(), sample.isPublic(), downloadableFiles),
                sampleAnnotations);
        final SampleViewModel sampleModel = builder.getModel();
        //End

        sampleModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, sampleModel);
    }

    private DownloadSection buildDownloadSection(final String sampleId, final boolean sampleIsPublic, final List<File> downloadableFiles) {
        final List<DownloadLink> seqDataDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> funcAnalysisDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> taxaAnalysisDownloadLinks = new ArrayList<DownloadLink>();

        final String linkURL = (sampleIsPublic ? "https://www.ebi.ac.uk/ena/data/view/" + sampleId + "?display=html" : "https://www.ebi.ac.uk/ena/submit/sra/#home");
        seqDataDownloadLinks.add(new DownloadLink("Submitted nucleotide reads (ENA website)",
                "Click to download all submitted nucleotide data on the ENA website",
                linkURL,
                true,
                1));

        for (File file : downloadableFiles) {
            if (file.getName().endsWith(EmgFile.ResultFileType.MASKED_FASTA.getFileNameEnd())) {
                seqDataDownloadLinks.add(new DownloadLink("Processed nucleotide reads (FASTA)",
                        "Click to download processed fasta nucleotide sequences",
                        "sample/" + sampleId + "/doExportMaskedFASTAFile",
                        2,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.CDS_FAA.getFileNameEnd())) {
                seqDataDownloadLinks.add(new DownloadLink("Predicted CDS (FASTA)",
                        "Click to download predicted CDS in fasta format",
                        "sample/" + sampleId + "/doExportCDSFile",
                        3,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.I5_TSV.getFileNameEnd())) {
                funcAnalysisDownloadLinks.add(new DownloadLink("InterPro matches (TSV)",
                        "Click to download full InterPro matches table (TSV)",
                        "sample/" + sampleId + "/doExportI5TSVFile",
                        4,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.GO.getFileNameEnd())) {
                funcAnalysisDownloadLinks.add(new DownloadLink("Complete GO annotation (CSV)",
                        "Click to download GO annotation result file (CSV)",
                        "sample/" + sampleId + "/doExportGOFile",
                        5,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.IPR_HITS.getFileNameEnd())) {
                funcAnalysisDownloadLinks.add(new DownloadLink("pCDS with InterPro matches (FASTA)",
                        "Click to download predicted CDS with InterPro matches (FASTA)",
                        "sample/" + sampleId + "/doExportIPRhitsFile",
                        6,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.TAX_ANALYSIS_BIOM_FILE.getFileNameEnd())) {
                taxaAnalysisDownloadLinks.add(new DownloadLink("OTUs and taxonomic assignments (BIOM)",
                        "Click to download the OTUs and taxonomic assignments (BIOM)",
                        "sample/" + sampleId + "?export=" + this.requestParamValues[0],
                        1,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.TAX_ANALYSIS_TSV_FILE.getFileNameEnd())) {
                taxaAnalysisDownloadLinks.add(new DownloadLink("OTUs and taxonomic assignments (TSV)",
                        "Click to download the OTUs and taxonomic assignments (TSV)",
                        "sample/" + sampleId + "?export=" + this.requestParamValues[1],
                        2,
                        getFileSize(file)));
            } else if (file.getName().endsWith(EmgFile.ResultFileType.TAX_ANALYSIS_TREE_FILE.getFileNameEnd())) {
                taxaAnalysisDownloadLinks.add(new DownloadLink("Phylogenetic tree (Newick format)",
                        "Click to download the phylogenetic tree file (Newick format)",
                        "sample/" + sampleId + "?export=" + this.requestParamValues[2],
                        3,
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
