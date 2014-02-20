package uk.ac.ebi.interpro.metagenomics.memi.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgLogFileInfoDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.ISampleStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.temp.SampleAnnotationDAO;
import uk.ac.ebi.interpro.metagenomics.memi.exceptionHandling.ExceptionTag;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SampleViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SampleViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * This class extends {@link SampleViewController}, {@link KronaChartsController} and {@link SampleViewExportController}.
 */
public class AbstractSampleViewController extends SecuredAbstractController<Sample> {

    private static final Log log = LogFactory.getLog(AbstractSampleViewController.class);
    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "sample";

    @Resource
    protected SampleDAO sampleDAO;

    @Resource
    protected EmgLogFileInfoDAO fileInfoDAO;

    @Resource
    private SampleAnnotationDAO sampleAnnotationDAO;

    @Resource
    private MemiDownloadService downloadService;

    @Resource
    protected Map<String, DownloadableFileDefinition> fileDefinitionsMap;

    @Resource(name = "qualityControlFileDefinitions")
    private List<ResultFileDefinitionImpl> qualityControlFileDefinitions;

    @Resource(name = "functionalAnalysisFileDefinitions")
    private List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions;

    @Resource(name = "taxonomicAnalysisFileDefinitions")
    private List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions;

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
        }
        return result;
    }

    protected EmgFile getEmgFile(final long sampleId) {
        List<EmgFile> emgFiles = fileInfoDAO.getFilesBySampleId(sampleId);
        if (emgFiles.size() > 0) {
            return emgFiles.get(0);
        } else {
            final String errorMessage = ExceptionTag.DATABASE_CURATION_ISSUE.toString() + "No log_file_info entry (EMG schema) for sample with id " + sampleId + " exists!";
            log.error(errorMessage);
            throw new IllegalStateException(errorMessage);
        }
    }

    protected void openDownloadDialog(final HttpServletResponse response,
                                      final HttpServletRequest request,
                                      final EmgFile emgFile,
                                      final String fileNameEnd,
                                      final File fileObject) {
        if (downloadService != null) {
            //white spaces are replaced by underscores
            final String fileNameForDownload = getFileName(emgFile, fileNameEnd);
            downloadService.openDownloadDialog(response, request, fileObject, fileNameForDownload, false);
        }
    }

    private String getFileName(final EmgFile emgFile,
                               final String fileNameEnd) {
        return emgFile.getFileName().replace(" ", "_") + fileNameEnd;
    }

    /**
     * Creates the home page model and adds it to the specified model map.
     */
    protected void populateModel(final ModelMap model, final Sample sample, String pageTitle) {
        EmgFile emgFile = getEmgFile(sample.getId());
        //TODO: For the moment the system only allows to represent one file on the analysis page, but
        //in the future it should be possible to represent all different data types (genomic, transcriptomic)
        //TODO: The following 'if' case is a quick and dirty solution to solve the differentiation issue between genomic and transcriptomic analysis
        SampleViewModel.ExperimentType experimentType = SampleViewModel.ExperimentType.GENOMIC;
        if (sample.getId() == 367) {
            experimentType = SampleViewModel.ExperimentType.TRANSCRIPTOMIC;
        }
        final List<EmgSampleAnnotation> sampleAnnotations = (List<EmgSampleAnnotation>) sampleAnnotationDAO.getSampleAnnotations(sample.getId());

        final ViewModelBuilder<SampleViewModel> builder = new SampleViewModelBuilder(
                sessionManager,
                sample,
                pageTitle,
                getBreadcrumbs(sample),
                emgFile,
                MemiTools.getArchivedSeqs(fileInfoDAO, sample),
                propertyContainer,
                experimentType,
                buildDownloadSection(sample.getSampleId(), sample.isPublic(), fileDefinitionsMap, emgFile),
                sampleAnnotations,
                qualityControlFileDefinitions,
                functionalAnalysisFileDefinitions,
                taxonomicAnalysisFileDefinitions);
        final SampleViewModel sampleModel = builder.getModel();
        //End

        sampleModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, sampleModel);
    }

    private DownloadSection buildDownloadSection(final String sampleId, final boolean sampleIsPublic,
                                                 final Map<String, DownloadableFileDefinition> fileDefinitionsMap,
                                                 final EmgFile emgFile) {
        final List<DownloadLink> seqDataDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> funcAnalysisDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> taxaAnalysisDownloadLinks = new ArrayList<DownloadLink>();

        final String linkURL = (sampleIsPublic ? "https://www.ebi.ac.uk/ena/data/view/" + sampleId + "?display=html" : "https://www.ebi.ac.uk/ena/submit/sra/#home");
        seqDataDownloadLinks.add(new DownloadLink("Submitted nucleotide reads (ENA website)",
                "Click to download all submitted nucleotide data on the ENA website",
                linkURL,
                true,
                1));

        for (DownloadableFileDefinition fileDefinition : fileDefinitionsMap.values()) {
            File fileObject = FileObjectBuilder.createFileObject(emgFile, propertyContainer, fileDefinition);
            boolean doesExist = FileExistenceChecker.checkFileExistence(fileObject);

            //Check if file exists and if it is not empty
            if (doesExist && fileObject.length() > 0) {
                if (fileDefinition instanceof SequenceFileDefinition) {
                    seqDataDownloadLinks.add(new DownloadLink(fileDefinition.getLinkText(),
                            fileDefinition.getLinkTitle(),
                            "sample/" + sampleId + fileDefinition.getLinkURL(),
                            fileDefinition.getOrder(),
                            getFileSize(fileObject)));
                } else if (fileDefinition instanceof TaxonomicAnalysisFileDefinition) {
                    taxaAnalysisDownloadLinks.add(new DownloadLink(fileDefinition.getLinkText(),
                            fileDefinition.getLinkTitle(),
                            "sample/" + sampleId + fileDefinition.getLinkURL(),
                            fileDefinition.getOrder(),
                            getFileSize(fileObject)));
                } else if (fileDefinition instanceof FunctionalAnalysisFileDefinition) {
                    funcAnalysisDownloadLinks.add(new DownloadLink(fileDefinition.getLinkText(),
                            fileDefinition.getLinkTitle(),
                            "sample/" + sampleId + fileDefinition.getLinkURL(),
                            fileDefinition.getOrder(),
                            getFileSize(fileObject)));

                } else {
                    //do nothing
                }
            }

        }
        Collections.sort(seqDataDownloadLinks, DownloadLink.DownloadLinkComparator);
        Collections.sort(funcAnalysisDownloadLinks, DownloadLink.DownloadLinkComparator);
        Collections.sort(taxaAnalysisDownloadLinks, DownloadLink.DownloadLinkComparator);
        return new DownloadSection(seqDataDownloadLinks, funcAnalysisDownloadLinks, taxaAnalysisDownloadLinks);
    }

    private String getFileSize(final File file) {
        if (file.canRead()) {
            long fileLength = file.length();
            long cutoff = 1024 * 1024;
            //If file size is bigger than 1MB
            if (fileLength > cutoff) {
                long fileSize = fileLength / (long) (1024 * 1024);
                return fileSize + " MB";
            } else {
                //If file size is bigger than 1KB
                if (fileLength > 1024) {
                    long fileSize = fileLength / (long) 1024;
                    return fileSize + " KB";
                } else {
                    return fileLength + " bytes";
                }
            }
        }
        return "";
    }
}
