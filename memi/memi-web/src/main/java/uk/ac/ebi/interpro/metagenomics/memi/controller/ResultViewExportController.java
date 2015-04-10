package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.interpro.metagenomics.memi.controller.results.AbstractResultViewController;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.StreamCopyUtil;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.services.ExportValueService;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Resource
    private ExportValueService exportValueService;


    @RequestMapping(value = {
            MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMY_EXPORT,
            MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_SEQUENCES_EXPORT
    })
    public void doHandleTaxonomyExports(@PathVariable final String projectId,
                                        @PathVariable final String sampleId,
                                        @PathVariable final String runId,
                                        @PathVariable final String releaseVersion,
                                        @RequestParam(required = true, value = "exportValue") final String exportValue,
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
                    DownloadableFileDefinition fileDefinition = exportValueService.findResultFileDefinition(exportValue);
                    if (fileDefinition != null) {
                        File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
                        openDownloadDialog(response, request, analysisJob, fileDefinition.getDownloadName(), fileObject);
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

    @Override
    protected String getModelViewName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}