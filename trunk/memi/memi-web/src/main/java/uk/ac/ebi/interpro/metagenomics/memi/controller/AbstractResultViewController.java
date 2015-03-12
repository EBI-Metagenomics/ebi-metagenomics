package uk.ac.ebi.interpro.metagenomics.memi.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.AnalysisJobDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.ISecureEntityDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.temp.SampleAnnotationDAO;
import uk.ac.ebi.interpro.metagenomics.memi.exceptionHandling.EntryNotFoundException;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ResultViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ResultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class extends {@link uk.ac.ebi.interpro.metagenomics.memi.controller.SampleViewController}, {@link uk.ac.ebi.interpro.metagenomics.memi.controller.KronaChartsController} and {@link uk.ac.ebi.interpro.metagenomics.memi.controller.ResultViewExportController}.
 */
public abstract class AbstractResultViewController extends SecuredAbstractController<Run> {

    private static final Log log = LogFactory.getLog(AbstractResultViewController.class);
    /**
     * View name of this controller which is used several times.
     */
//    public static final String VIEW_NAME = "sample";

    @Resource
    protected SampleDAO sampleDAO;

    @Resource
    private SampleAnnotationDAO sampleAnnotationDAO;

    @Resource
    protected AnalysisJobDAO analysisJobDAO;

    @Resource
    protected RunDAO runDAO;

    @Resource
    private MemiDownloadService downloadService;

    @Resource(name = "qualityControlFileDefinitions")
    private List<ResultFileDefinitionImpl> qualityControlFileDefinitions;

    @Resource(name = "functionalAnalysisFileDefinitions")
    private List<FunctionalAnalysisFileDefinition> functionalAnalysisFileDefinitions;

    @Resource(name = "taxonomicAnalysisFileDefinitions")
    private List<ResultFileDefinitionImpl> taxonomicAnalysisFileDefinitions;

    protected Run getSecuredEntity(final String projectId,
                                   final String sampleId,
                                   final String runId,
                                   String version) {
        return runDAO.readByRunIdDeep(projectId, sampleId, runId, version);
    }


    protected List<Breadcrumb> getBreadcrumbs(SecureEntity secureEntity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (secureEntity != null && secureEntity instanceof Run) {
            Run run = (Run) secureEntity;
            String runURL = "projects/" + run.getExternalProjectId() + "/samples/" + run.getExternalSampleId() + "/runs/" + run.getExternalRunId() + "/results/versions/" + run.getReleaseVersion();
            String sampleURL = "projects/" + run.getExternalProjectId() + "/samples/" + run.getExternalSampleId();
            String projectURL = "projects/" + run.getExternalProjectId();
            result.add(new Breadcrumb("Project: " + run.getExternalProjectId(), "View project " + run.getExternalProjectId(), projectURL));
            result.add(new Breadcrumb("Sample: " + run.getExternalSampleId(), "View sample " + run.getExternalSampleId(), sampleURL));
            result.add(new Breadcrumb("Run: " + run.getExternalRunId(), "View run " + run.getExternalRunId(), runURL));
        }
        return result;
    }

    protected void openDownloadDialog(final HttpServletResponse response,
                                      final HttpServletRequest request,
                                      final AnalysisJob analysisJob,
                                      final String fileNameEnd,
                                      final File fileObject) {
        if (downloadService != null) {
            //white spaces are replaced by underscores
            final String fileNameForDownload = getFileName(analysisJob, fileNameEnd);
            downloadService.openDownloadDialog(response, request, fileObject, fileNameForDownload, false);
        }
    }

    private String getFileName(final AnalysisJob analysisJob,
                               final String fileNameEnd) {
        return analysisJob.getInputFileName().replace(" ", "_") + fileNameEnd;
    }

    /**
     * Creates the home page model and adds it to the specified model map.
     */
    protected void populateModel(final ModelMap model, final Run run, String pageTitle) {
//        EmgFile emgFile = getEmgFile(run.getSampleId());
        //TODO: For the moment the system only allows to represent one file on the analysis page, but
        //in the future it should be possible to represent all different data types (genomic, transcriptomic)
        ResultViewModel.ExperimentType experimentType = ResultViewModel.ExperimentType.GENOMIC;
        final List<EmgSampleAnnotation> sampleAnnotations = (List<EmgSampleAnnotation>) sampleAnnotationDAO.getSampleAnnotations(run.getSampleId());

        AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), run.getReleaseVersion(), "completed");
        if (analysisJob == null) {
            throw new EntryNotFoundException();
        }

        final ViewModelBuilder<ResultViewModel> builder = new ResultViewModelBuilder(
                sessionManager,
                analysisJob.getSample(),
                run,
                pageTitle,
                getBreadcrumbs(run),
                analysisJob,
                //TODO: Refactor
//                MemiTools.getArchivedSeqs(fileInfoDAO, sample),
                new ArrayList<String>(),
                propertyContainer,
                experimentType,
                buildDownloadSection(run, fileDefinitionsMap, analysisJob),
                sampleAnnotations,
                qualityControlFileDefinitions,
                functionalAnalysisFileDefinitions,
                taxonomicAnalysisFileDefinitions);
        final ResultViewModel resultModel = builder.getModel();
        //End

        resultModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SAMPLES_VIEW);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, resultModel);
    }

    private DownloadSection buildDownloadSection(final Run run,
                                                 final Map<String, DownloadableFileDefinition> fileDefinitionsMap,
                                                 final AnalysisJob analysisJob) {
        final String externalSampleId = run.getExternalSampleId();
        final String externalProjectId = run.getExternalProjectId();
        final String externalRunId = run.getExternalRunId();
        final boolean sampleIsPublic = run.isPublic();
        final String analysisJobReleaseVersion = analysisJob.getPipelineRelease().getReleaseVersion();

        final List<DownloadLink> seqDataDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> funcAnalysisDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> taxaAnalysisDownloadLinks = new ArrayList<DownloadLink>();

        final String linkURL = (sampleIsPublic ? "https://www.ebi.ac.uk/ena/data/view/" + externalSampleId : "https://www.ebi.ac.uk/ena/submit/sra/#home");
        seqDataDownloadLinks.add(new DownloadLink("Submitted nucleotide reads (ENA website)",
                "Click to download all submitted nucleotide data on the ENA website",
                linkURL,
                true,
                1));

        for (DownloadableFileDefinition fileDefinition : fileDefinitionsMap.values()) {
            File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
            boolean doesExist = FileExistenceChecker.checkFileExistence(fileObject);

            //Check if file exists and if it is not empty
            if (doesExist && fileObject.length() > 0) {
                if (fileDefinition instanceof SequenceFileDefinition) {
                    //filter out certain files by release version
                    if (fileDefinition.getReleaseVersion() == null || fileDefinition.getReleaseVersion().equals(analysisJobReleaseVersion)) {
                        seqDataDownloadLinks.add(new DownloadLink(fileDefinition.getLinkText(),
                                fileDefinition.getLinkTitle(),
                                "projects/" + externalProjectId + "/samples/" + externalSampleId + "/runs/" + externalRunId + "/results/sequences" + "/versions/" + analysisJobReleaseVersion + fileDefinition.getLinkURL(),
                                fileDefinition.getOrder(),
                                getFileSize(fileObject)));
                    }
                } else if (fileDefinition instanceof TaxonomicAnalysisFileDefinition) {
                    taxaAnalysisDownloadLinks.add(new DownloadLink(fileDefinition.getLinkText(),
                            fileDefinition.getLinkTitle(),
                            "projects/" + externalProjectId + "/samples/" + externalSampleId + "/runs/" + externalRunId + "/results/taxonomy" + "/versions/" + analysisJobReleaseVersion + fileDefinition.getLinkURL(),
                            fileDefinition.getOrder(),
                            getFileSize(fileObject)));
                } else if (fileDefinition instanceof FunctionalAnalysisFileDefinition) {
                    funcAnalysisDownloadLinks.add(new DownloadLink(fileDefinition.getLinkText(),
                            fileDefinition.getLinkTitle(),
                            "projects/" + externalProjectId + "/samples/" + externalSampleId + "/runs/" + externalRunId + "/results/" + fileDefinition.getLinkURL() + "/versions/" + analysisJobReleaseVersion,
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
