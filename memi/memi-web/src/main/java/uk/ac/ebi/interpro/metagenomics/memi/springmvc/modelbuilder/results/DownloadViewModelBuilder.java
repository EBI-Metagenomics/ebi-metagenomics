package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.results;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.DownloadViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Model builder class for {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results.DownloadViewModel}.
 * <p/>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public class DownloadViewModelBuilder extends AbstractResultViewModelBuilder<DownloadViewModel> {

    private final static Log log = LogFactory.getLog(DownloadViewModelBuilder.class);

    private Run run;

    private Map<String, DownloadableFileDefinition> fileDefinitionsMap;

    public DownloadViewModelBuilder(SessionManager sessionMgr,
                                    String pageTitle,
                                    List<Breadcrumb> breadcrumbs,
                                    MemiPropertyContainer propertyContainer,
                                    Run run,
                                    Map<String, DownloadableFileDefinition> fileDefinitionsMap,
                                    AnalysisJob analysisJob) {
        super(sessionMgr, pageTitle, breadcrumbs, propertyContainer, null, null, null, analysisJob);
        this.run = run;
        this.fileDefinitionsMap = fileDefinitionsMap;
    }

    public DownloadViewModel getModel() {
        log.info("Building instance of " + DownloadViewModel.class + "...");
        final DownloadSection downloadSection = buildDownloadSection(run, fileDefinitionsMap, analysisJob);
        return new DownloadViewModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer, downloadSection, analysisJob.getSample());
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
        final List<DownloadLink> otherFuncAnalysisDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> interproscanDownloadLinks = new ArrayList<DownloadLink>();
        final List<DownloadLink> taxaAnalysisDownloadLinks = new ArrayList<DownloadLink>();

        final String linkURL = (sampleIsPublic ? "https://www.ebi.ac.uk/ena/data/view/" + externalRunId : "https://www.ebi.ac.uk/ena/submit/sra/#home");
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
                    if (!isAmpliconData()) {
                        if (fileDefinition.getIdentifier().equals("INTERPROSCAN_RESULT_FILE_NEW")) {
                            //get result file chunks as a list of absolute file paths
                            List<String> chunkedResultFiles = MemiTools.getListOfChunkedResultFiles(fileObject);
                            //We do need to distinguish 2 cases - case 1: single compressed file - case 2: chunked and compressed result files
                            //Case 1 will look like: InterPro matches (TSV) - 47 MB
                            //Case 2 will look like: InterPro matches (TSV) - compressed - File 1
                            String linkText = fileDefinition.getLinkText() + " - compressed";
                            int chunkCounter = 1;
                            for (String chunk : chunkedResultFiles) {
                                if (chunkedResultFiles.size() > 1) {
                                    String partStr = " Part " + String.valueOf(chunkCounter);
                                    linkText = partStr;
                                }
                                final File downloadFileObj = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, chunk);
                                interproscanDownloadLinks.add(new DownloadLink(linkText,
                                        fileDefinition.getLinkTitle(),
                                        "projects/" + externalProjectId + "/samples/" + externalSampleId + "/runs/" + externalRunId + "/results/versions/" + analysisJobReleaseVersion + "/function/InterProScan/chunks/" + String.valueOf(chunkCounter),
                                        fileDefinition.getOrder(),
                                        getFileSize(downloadFileObj)));
                                chunkCounter++;
                            }
                        } else if (fileDefinition.getIdentifier().equals("INTERPROSCAN_RESULT_FILE")) {
                            String filePath = fileObject.getAbsolutePath();
                            File newFileObject = new File(filePath + ".chunks");
                            if (!FileExistenceChecker.checkFileExistence(newFileObject)) {
                                otherFuncAnalysisDownloadLinks.add(new DownloadLink(fileDefinition.getLinkText(),
                                        fileDefinition.getLinkTitle(),
                                        "projects/" + externalProjectId + "/samples/" + externalSampleId + "/runs/" + externalRunId + "/results/" + fileDefinition.getLinkURL() + "/versions/" + analysisJobReleaseVersion,
                                        fileDefinition.getOrder(),
                                        getFileSize(fileObject)));
                            }
                        } else {
                            otherFuncAnalysisDownloadLinks.add(new DownloadLink(fileDefinition.getLinkText(),
                                    fileDefinition.getLinkTitle(),
                                    "projects/" + externalProjectId + "/samples/" + externalSampleId + "/runs/" + externalRunId + "/results/" + fileDefinition.getLinkURL() + "/versions/" + analysisJobReleaseVersion,
                                    fileDefinition.getOrder(),
                                    getFileSize(fileObject)));
                        }
                    }
                } else {
                    //do nothing
                }
            } else {
                log.warn("Download page warning: The following file does Not exist or is empty:");
                log.warn(fileObject.getAbsolutePath());
            }
        }
        Collections.sort(seqDataDownloadLinks, DownloadLink.DownloadLinkComparator);
        Collections.sort(otherFuncAnalysisDownloadLinks, DownloadLink.DownloadLinkComparator);
        Collections.sort(taxaAnalysisDownloadLinks, DownloadLink.DownloadLinkComparator);
        return new DownloadSection(seqDataDownloadLinks, new FunctionalDownloadSection(interproscanDownloadLinks, otherFuncAnalysisDownloadLinks), taxaAnalysisDownloadLinks);
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