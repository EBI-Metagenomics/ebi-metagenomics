package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.results.AbstractResultViewController;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.StreamCopyUtil;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Locale;

/**
 * This controller handles the request for some exports on the sample view.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class ResultViewExportController extends AbstractResultViewController {
    private static final Log log = LogFactory.getLog(ResultViewExportController.class);

    protected void doHandleTaxonomyExports(@PathVariable final String projectId,
                                           @PathVariable final String sampleId,
                                           @PathVariable final String runId,
                                           @PathVariable final String releaseVersion,
                                           final HttpServletResponse response,
                                           final HttpServletRequest request,
                                           final FileDefinitionId fileDefinitionId) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setLocale(Locale.ENGLISH);

        // Please note: The actual security check will be done further down
        Run run = getSecuredEntity(projectId, sampleId, runId, releaseVersion);

        if (run != null && fileDefinitionId != null) {
            // Perform security check
            if (isAccessible(run)) {
                AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), releaseVersion, "completed");
                if (analysisJob != null) {
                    DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(fileDefinitionId.name());
                    if (fileDefinition != null) {
                        File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
                        try {
                            openDownloadDialog(response, request, analysisJob, fileDefinition.getDownloadName(), fileObject);
                        } catch (IndexOutOfBoundsException e) {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        }
                    } else {//analysis job is NULL
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                } else {//access denied
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
                }
            } else {//run is NULL
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
    }


    protected void doHandleFunctionalExports(final String projectId,
                                             final String sampleId,
                                             final String runId,
                                             final String releaseVersion,
                                             final Integer chunkValue,
                                             final HttpServletResponse response,
                                             final HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setLocale(Locale.ENGLISH);

        Run run = getSecuredEntity(projectId, sampleId, runId, releaseVersion);

        if (run != null) {
            if (isAccessible(run)) {
                AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), releaseVersion, "completed");
                if (analysisJob != null) {
                    DownloadableFileDefinition fileDefinition = chunkedResultFilesMap.get(FileDefinitionId.INTERPROSCAN_RESULT_FILE.name());
                    if (fileDefinition != null) {
                        File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
                        //get result file chunks as a list of absolute file paths
                        List<String> chunkedResultFiles = MemiTools.getListOfChunkedResultFiles(fileObject);
                        try {
                            final String downloadName = (chunkedResultFiles.size() == 1 ? fileDefinition.getDownloadName() : fileDefinition.getDownloadName().replace(".tsv.gz", "_" + String.valueOf(chunkValue) + ".tsv.gz"));
                            final String resultFileName = chunkedResultFiles.get(chunkValue - 1);
                            openDownloadDialog(response, request, analysisJob, downloadName, FileObjectBuilder.createFileObject(analysisJob, propertyContainer, resultFileName));
                        } catch (IndexOutOfBoundsException e) {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        }
                    } else {//analysis job is NULL
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                } else {//access denied
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
                }
            } else {//run is NULL
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
    }

    protected void doHandleSequenceFileExports(final String projectId,
                                               final String sampleId,
                                               final String runId,
                                               final String releaseVersion,
                                               final Integer chunkValue,
                                               final HttpServletResponse response,
                                               final HttpServletRequest request,
                                               final FileDefinitionId fileDefinitionId) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setLocale(Locale.ENGLISH);

        Run run = getSecuredEntity(projectId, sampleId, runId, releaseVersion);

        if (run != null && fileDefinitionId != null) {
            if (isAccessible(run)) {
                AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), releaseVersion, "completed");
                if (analysisJob != null) {
                    DownloadableFileDefinition fileDefinition = chunkedResultFilesMap.get(fileDefinitionId.name());
                    if (fileDefinition != null) {
                        File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
                        //get result file chunks as a list of absolute file paths
                        List<String> chunkedResultFiles = MemiTools.getListOfChunkedResultFiles(fileObject);
                        try {
                            String downloadName = null;
                            if (fileDefinitionId.equals(FileDefinitionId.PREDICTED_CDS_FILE) || fileDefinitionId.equals(FileDefinitionId.PREDICTED_CDS_WITHOUT_ANNOTATION_FILE)) {
                                downloadName = (chunkedResultFiles.size() == 1 ? fileDefinition.getDownloadName() : fileDefinition.getDownloadName().replace(".faa.gz", "_" + String.valueOf(chunkValue) + ".faa.gz"));
                            } else if (fileDefinitionId.equals(FileDefinitionId.PREDICTED_ORF_WITHOUT_ANNOTATION_FILE)) {
                                downloadName = (chunkedResultFiles.size() == 1 ? fileDefinition.getDownloadName() : fileDefinition.getDownloadName().replace(".ffn.gz", "_" + String.valueOf(chunkValue) + ".ffn.gz"));
                            } else {
                                downloadName = (chunkedResultFiles.size() == 1 ? fileDefinition.getDownloadName() : fileDefinition.getDownloadName().replace(".fasta.gz", "_" + String.valueOf(chunkValue) + ".fasta.gz"));
                            }
                            String resultFileName = chunkedResultFiles.get(chunkValue - 1);
                            String relativePath = fileDefinition.getRelativePath();
                            if (relativePath != null) {
                                String fileParent = new File(relativePath).getParent();
                                resultFileName = fileParent + "/" + resultFileName;
                            }
                            openDownloadDialog(response, request, analysisJob, downloadName, FileObjectBuilder.createFileObject(analysisJob, propertyContainer, resultFileName));
                        } catch (IndexOutOfBoundsException e) {
                            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        }
                    } else {//analysis job is NULL
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                } else {//access denied
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
                }
            } else {//run is NULL
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
    }

    /**
     * @param sampleId
     * @param requestBody The response body should contain 3 attributes (file type, file name and image or SVG document as plain text)
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_IMAGE_EXPORT, method = RequestMethod.POST)
    public void doHandleSampleViewPostExports(@PathVariable final String projectId,
                                              @PathVariable final String sampleId,
                                              @PathVariable final String runId,
                                              @PathVariable final String releaseVersion,
                                              @RequestBody String requestBody,
                                              final HttpServletResponse response) throws IOException {
        Run run = getSecuredEntity(projectId, sampleId, runId, releaseVersion);
        if (run != null) {
            log.debug("Checking accessibility before streaming SVG data...");
            if (isAccessible(run)) {
                BufferedReader reader = new BufferedReader(new StringReader(requestBody));
                //First of all get the first line from the request body and determine the file type (SVG or PNG)
                String line = reader.readLine();
                if (line != null) {
                    //Parse out the file type
                    int index = line.indexOf("=") + 1;
                    final String fileType = line.substring(index);

                    String svgDocument = "";
                    String dataUrl = "";
                    String fileName = "";
                    while ((line = reader.readLine()) != null) {
                        index = line.indexOf("=") + 1;
                        if (index > 1 && line.length() > index) {
                            if (line.startsWith("fileName=")) {
                                fileName = line.substring(index);
                            } else if (line.startsWith("svgDocumentAsString=")) {
                                svgDocument = line.substring(index);
                            } else if (line.startsWith("dataUrl=")) {
                                dataUrl = line.substring(index);
                            }
                        }
                    }
                    //Set HTTP response header attributes
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + run.getExternalRunId() + "_" + fileName + "\"");
                    response.setHeader("Accept-Ranges", "bytes");
                    response.setBufferSize(2048000);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setLocale(Locale.ENGLISH);

                    if (fileType.equalsIgnoreCase("svg")) {
//                        svgDocument = svgDocument.replace("<svg ", "<svg xmlns=\"http://www.w3.org/2000/svg\" ");
                        //Set HTTP response header attributes
                        response.setContentLength(svgDocument.length());
                        response.setContentType("image/svg+xml");

                        InputStream is = new ByteArrayInputStream(svgDocument.getBytes());
                        copyBytesToServletOutputStream(response, is);
                    } else if (fileType.equalsIgnoreCase("png")) {
                        String encodingPrefix = "base64,";
                        int contentStartIndex = dataUrl.indexOf(encodingPrefix) + encodingPrefix.length();
                        byte[] imageDataByteArray = Base64.decodeBase64(dataUrl.substring(contentStartIndex));
                        //Set HTTP response header attributes
                        response.setContentLength(imageDataByteArray.length);
                        response.setContentType("image/png");

                        InputStream is = new ByteArrayInputStream(imageDataByteArray);
                        copyBytesToServletOutputStream(response, is);
                    } else {
                        //unknown file type
                        log.warn("Unknown file type posted! Detected file type=" + fileType);
                    }
                }
            } else {//access denied
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
            }
        } else

        {//sample not found
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
        }
    }

    private void copyBytesToServletOutputStream(final HttpServletResponse response, InputStream is) {
        ServletOutputStream sot = null;
        try {
            sot = response.getOutputStream();
            StreamCopyUtil.copy(is, sot);
            sot.flush();
        } catch (IOException e) {
            log.error("Could not stream image to output stream!", e);
        } finally {
            if (sot != null)
                try {
                    sot.close();
                } catch (IOException e) {
                    log.error("Could not close input stream correctly!", e);
                }
        }
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_FUNCTION_INTERPROSCAN_CHUNKS)
    public ModelAndView doListInterProScanChunks(@PathVariable final String projectId,
                                                 @PathVariable final String sampleId,
                                                 @PathVariable final String runId,
                                                 @PathVariable final String releaseVersion,
                                                 final HttpServletResponse response, final HttpServletRequest request) {
        final DownloadableFileDefinition fileDefinition = chunkedResultFilesMap.get(FileDefinitionId.INTERPROSCAN_RESULT_FILE.name());
        final Run run = getSecuredEntity(projectId, sampleId, runId, releaseVersion);

        return checkAccessAndBuildModel(new ModelProcessingStrategy<Run>() {
            @Override
            public void processModel(ModelMap model, Run run) {
                AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), releaseVersion, "completed");
                if (analysisJob != null) {
                    File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
                    List<String> listOfChunks = MemiTools.getListOfChunkedResultFiles(fileObject);
                    model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
                    model.addAttribute("numOfChunks", listOfChunks.size());
                }
            }
        }, new ModelMap(), run, "chunks");
    }


    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_FUNCTION_INTERPROSCAN_CHUNKS_VALUE)
    public void doExportI5Results(@PathVariable final String projectId,
                                  @PathVariable final String sampleId,
                                  @PathVariable final String runId,
                                  @PathVariable final String releaseVersion,
                                  @PathVariable final Integer chunkValue,
                                  final HttpServletResponse response,
                                  final HttpServletRequest request) throws IOException {
        doHandleFunctionalExports(projectId, sampleId, runId, releaseVersion, chunkValue, response, request);
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES_SEQ_TYPE_CHUNKS)
    public ModelAndView doListSequenceChunks(@PathVariable final String projectId,
                                             @PathVariable final String sampleId,
                                             @PathVariable final String runId,
                                             @PathVariable final String releaseVersion,
                                             @PathVariable final String sequenceType) {
        FileDefinitionId fileDefinitionId = getFileDefinitionIdBySequenceType(sequenceType, releaseVersion);
        final DownloadableFileDefinition fileDefinition = chunkedResultFilesMap.get(fileDefinitionId.name());
        final Run run = getSecuredEntity(projectId, sampleId, runId, releaseVersion);
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Run>() {
            @Override
            public void processModel(ModelMap model, Run run) {
                AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), releaseVersion, "completed");
                if (analysisJob != null) {
                    int numberOfChunks = -1;
                    if (fileDefinition != null) {
                        File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
                        List<String> listOfChunks = MemiTools.getListOfChunkedResultFiles(fileObject);
                        numberOfChunks = listOfChunks.size();
                    }
                    model.addAttribute("numOfChunks", numberOfChunks);
                    model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());

                }
            }
        }, new ModelMap(), run, "chunks");
    }


    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES_SEQ_TYPE_CHUNKS_VALUE)
    public void doExportSequenceResults(@PathVariable final String projectId,
                                        @PathVariable final String sampleId,
                                        @PathVariable final String runId,
                                        @PathVariable final String releaseVersion,
                                        @PathVariable final String sequenceType,
                                        @PathVariable final Integer chunkValue,
                                        final HttpServletResponse response,
                                        final HttpServletRequest request) throws IOException {
        FileDefinitionId fileDefinitionId = getFileDefinitionIdBySequenceType(sequenceType, releaseVersion);
        doHandleSequenceFileExports(projectId, sampleId, runId, releaseVersion, chunkValue, response, request, fileDefinitionId);
    }

    private FileDefinitionId getFileDefinitionIdBySequenceType(final String sequenceType, final String releaseVersion) {
        FileDefinitionId fileDefinitionId = FileDefinitionId.DEFAULT;
        if (sequenceType.equalsIgnoreCase("ProcessedReads")) {
            if (releaseVersion.equalsIgnoreCase("1.0")) {
                fileDefinitionId = FileDefinitionId.MASKED_FASTA;
            } else if (releaseVersion.equalsIgnoreCase("2.0")) {
                fileDefinitionId = FileDefinitionId.PROCESSED_READS;
            } else {//Default value
                fileDefinitionId = FileDefinitionId.PROCESSED_READS;
            }
        } else if (sequenceType.equalsIgnoreCase("ReadsWithPredictedCDS")) {
            fileDefinitionId = FileDefinitionId.READS_WITH_PREDICTED_CDS_FILE;
        } else if (sequenceType.equalsIgnoreCase("ReadsWithMatches")) {
            fileDefinitionId = FileDefinitionId.READS_WITH_MATCHES_FASTA_FILE;
        } else if (sequenceType.equalsIgnoreCase("ReadsWithoutMatches")) {
            fileDefinitionId = FileDefinitionId.READS_WITHOUT_MATCHES_FASTA_FILE;
        } else if (sequenceType.equalsIgnoreCase("PredictedCDS")) {
            fileDefinitionId = FileDefinitionId.PREDICTED_CDS_FILE;
        } else if (sequenceType.equalsIgnoreCase("PredictedORFWithoutAnnotation")) {
            fileDefinitionId = FileDefinitionId.PREDICTED_ORF_WITHOUT_ANNOTATION_FILE;
        } else if (sequenceType.equalsIgnoreCase("PredictedCDSWithoutAnnotation")) {
            fileDefinitionId = FileDefinitionId.PREDICTED_CDS_WITHOUT_ANNOTATION_FILE;
        } else {
            log.warn("Sequence type: " + sequenceType + " not found!");
        }
        return fileDefinitionId;
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMY_TYPE)
    public void handleTaxonomyResultDownloads(@PathVariable final String projectId,
                                              @PathVariable final String sampleId,
                                              @PathVariable final String runId,
                                              @PathVariable final String releaseVersion,
                                              @PathVariable final String resultType,
                                              final HttpServletResponse response,
                                              final HttpServletRequest request) throws IOException {
        FileDefinitionId fileDefinitionId = FileDefinitionId.DEFAULT;
        if (resultType.equalsIgnoreCase("5S-rRNA-FASTA")) {
            fileDefinitionId = FileDefinitionId.R_RNA_5S_FASTA_FILE;
        } else if (resultType.equalsIgnoreCase("16S-rRNA-FASTA")) {
            fileDefinitionId = FileDefinitionId.R_RNA_16S_FASTA_FILE;
        } else if (resultType.equalsIgnoreCase("23S-rRNA-FASTA")) {
            fileDefinitionId = FileDefinitionId.R_RNA_23S_FASTA_FILE;
        } else if (resultType.equalsIgnoreCase("OTU-TSV")) {
            if (releaseVersion.equalsIgnoreCase("1.0")) {
                fileDefinitionId = FileDefinitionId.TAX_ANALYSIS_TSV_FILE;
            } else {// releases version greater 2.0
                fileDefinitionId = FileDefinitionId.OTU_TABLE_FILE;
            }
        } else if (resultType.equalsIgnoreCase("OTU-BIOM")) {
            fileDefinitionId = FileDefinitionId.OTUS_BIOM_FORMAT_FILE;
        } else if (resultType.equalsIgnoreCase("OTU-table-HDF5-BIOM")) {
            fileDefinitionId = FileDefinitionId.HDF5_BIOM_FILE;
        } else if (resultType.equalsIgnoreCase("OTU-table-JSON-BIOM")) {
            fileDefinitionId = FileDefinitionId.JSON_BIOM_FILE;
        } else if (resultType.equalsIgnoreCase("NewickTree")) {
            fileDefinitionId = FileDefinitionId.TAX_ANALYSIS_TREE_FILE;
        } else if (resultType.equalsIgnoreCase("NewickPrunedTree")) {
            fileDefinitionId = FileDefinitionId.PRUNED_TREE_FILE;
        } else {
            log.warn("Result type: " + resultType + " not found!");
        }
        doHandleTaxonomyExports(projectId, sampleId, runId, releaseVersion, response, request, fileDefinitionId);
    }

    @Override
    protected String getModelViewName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}