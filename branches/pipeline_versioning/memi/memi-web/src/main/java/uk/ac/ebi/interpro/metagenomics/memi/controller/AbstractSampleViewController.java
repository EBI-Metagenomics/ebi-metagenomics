package uk.ac.ebi.interpro.metagenomics.memi.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.ISecureEntityDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.temp.SampleAnnotationDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;

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
    /**
     * View name of this controller which is used several times.
     */
//    public static final String VIEW_NAME = "sample";

    @Resource
    protected SampleDAO sampleDAO;

    @Resource
    private SampleAnnotationDAO sampleAnnotationDAO;

    @Resource
    private MemiDownloadService downloadService;

    @Resource(name = "qualityControlFileDefinitions")
    private List<ResultFileDefinitionImpl> qualityControlFileDefinitions;

    @Resource(name = "functionalAnalysisFileDefinitions")
    private List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions;

    @Resource(name = "taxonomicAnalysisFileDefinitions")
    private List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions;

    ISecureEntityDAO<Sample> getDAO() {
        return sampleDAO;
    }

//    protected String getModelViewName() {
//        return VIEW_NAME;
//    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (entity != null && entity instanceof Sample) {
            result.add(new Breadcrumb("Project: " + ((Sample) entity).getStudy().getStudyName(), "View project " + ((Sample) entity).getStudy().getStudyName(), StudyViewController.VIEW_NAME + '/' + ((Sample) entity).getStudy().getStudyId()));
            result.add(new Breadcrumb("Sample: " + ((Sample) entity).getSampleName(), "View sample " + ((Sample) entity).getSampleName(), getModelViewName() + '/' + ((Sample) entity).getSampleId()));
        }
        return result;
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
    protected void populateModel(final ModelMap model, final AnalysisJob analysisJob, String pageTitle) {
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

    private DownloadSection buildDownloadSection(final Sample sample,
                                                 final Map<String, DownloadableFileDefinition> fileDefinitionsMap,
                                                 final EmgFile emgFile) {
        final String sampleId = sample.getSampleId();
        final boolean sampleIsPublic = sample.isPublic();
        final Long runId = sample.getId();

        final List<DownloadLink> seqDataDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> funcAnalysisDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> taxaAnalysisDownloadLinks = new ArrayList<DownloadLink>();

        final String linkURL = (sampleIsPublic ? "https://www.ebi.ac.uk/ena/data/view/" + sampleId : "https://www.ebi.ac.uk/ena/submit/sra/#home");
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
                            "sample/" + sampleId + fileDefinition.getLinkURL() + runId,
                            fileDefinition.getOrder(),
                            getFileSize(fileObject)));
                } else if (fileDefinition instanceof TaxonomicAnalysisFileDefinition) {
                    taxaAnalysisDownloadLinks.add(new DownloadLink(fileDefinition.getLinkText(),
                            fileDefinition.getLinkTitle(),
                            "sample/" + sampleId + fileDefinition.getLinkURL() + runId,
                            fileDefinition.getOrder(),
                            getFileSize(fileObject)));
                } else if (fileDefinition instanceof FunctionalAnalysisFileDefinition) {
                    funcAnalysisDownloadLinks.add(new DownloadLink(fileDefinition.getLinkText(),
                            fileDefinition.getLinkTitle(),
                            "sample/" + sampleId + fileDefinition.getLinkURL() + runId,
                            fileDefinition.getOrder(),
                            getFileSize(fileObject)));

                } else {
                    //do nothing
                }
            } else {
                log.warn("Download page warning: The following file does Not exist or is empty:");
                log.warn(fileObject.getAbsolutePath());
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
