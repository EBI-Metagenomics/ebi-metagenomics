package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.interpro.metagenomics.memi.controller.results.AbstractResultViewController;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * This controller handles the request for the Krona chars.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class KronaChartsController extends AbstractResultViewController {
    private static final Log log = LogFactory.getLog(KronaChartsController.class);

    @RequestMapping(value = MGPortalURLCollection.PROJECT_SAMPLE_RUN_RESULTS_TAXONOMIC_KRONA_CHART)
    public void doGetKronaChart(@PathVariable final String projectId,
                                @PathVariable final String sampleId,
                                @PathVariable final String runId,
                                @PathVariable final String releaseVersion,
                                @RequestParam(required = false, value = "taxonomy", defaultValue = "false") final boolean taxonomy,
                                @RequestParam(required = false, value = "rrnatype", defaultValue = "ssu") final String rRNAType,
                                final ModelMap model,
                                final HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setLocale(Locale.ENGLISH);
        //
        Run run = getSecuredEntity(projectId, sampleId, runId, releaseVersion);

        if (run != null) {
            log.debug("Checking security restriction before streaming Krona result charts...");
            if (isAccessible(run)) {
                log.debug("Building file path to the Krona HTML file...");
                AnalysisJob analysisJob = analysisJobDAO.readByRunIdAndVersionDeep(run.getExternalRunId(), releaseVersion, "completed");
                if (taxonomy && analysisJob != null) {
                    FileDefinitionId fileDefinitionIdKronaHTMLFile = FileDefinitionId.KRONA_HTML_FILE;
                    if (releaseVersion.equalsIgnoreCase("4.0") || releaseVersion.equalsIgnoreCase("4.1")) {
                        if (rRNAType.equalsIgnoreCase("ssu")) {
                            fileDefinitionIdKronaHTMLFile = FileDefinitionId.KRONA_HTML_FILE_SSU;
                        } else {//LSU
                            fileDefinitionIdKronaHTMLFile = FileDefinitionId.KRONA_HTML_FILE_LSU;
                        }
                    }
                    File fileToStream = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, propertyContainer.getResultFileDefinition(fileDefinitionIdKronaHTMLFile));
                    log.debug("Checking if Krona result file does exit before streaming it...");
                    if (fileToStream == null || !fileToStream.exists()) {
                        log.warn(fileToStream.getAbsolutePath() + " doesn't exist!");
                        return;
                    } else {
                        ServletOutputStream servletOutStream = null;
                        InputStream fileInputStream = null;
                        try {
                            log.debug("Streaming Krona chart...");
                            servletOutStream = response.getOutputStream();
                            byte[] buffer = new byte[4096];
                            fileInputStream = new FileInputStream(fileToStream);
                            int contentLengthCounter = 0;
                            int read;
                            while ((read = fileInputStream.read(buffer)) != -1) {
                                servletOutStream.write(buffer, 0, read);
                                contentLengthCounter += read;
                            }
                            response.setContentLength(contentLengthCounter);
                            fileInputStream.close();
                            servletOutStream.flush();
                            servletOutStream.close();
                        } catch (IOException e) {
                            if (servletOutStream != null) {
                                servletOutStream.close();
                            }
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            throw e;
                        }
                    }
                } else { //analysis job is NULL
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {//access denied
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
            }
        } else { //run is NULL
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    protected String getModelViewName() {
        //No need to implement as it doesn't return any model and view
        return null;
    }
}