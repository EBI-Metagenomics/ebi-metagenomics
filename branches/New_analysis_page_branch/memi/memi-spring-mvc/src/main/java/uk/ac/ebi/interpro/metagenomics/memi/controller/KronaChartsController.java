package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
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
public class KronaChartsController extends AbstractSampleViewController {
    private static final Log log = LogFactory.getLog(KronaChartsController.class);

    @RequestMapping(value = '/' + SampleViewController.VIEW_NAME + "/{sampleId}/krona")
    public void doGetKronaChart(@PathVariable final String sampleId,
                                @RequestParam(required = false, value = "taxonomy", defaultValue = "false") final boolean taxonomy,
                                @RequestParam(required = false, value = "function", defaultValue = "false") final boolean function,
                                @RequestParam(required = false, value = "slim", defaultValue = "false") final boolean slim,
                                final ModelMap model,
                                final HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setLocale(Locale.ENGLISH);
        Sample sample = sampleDAO.readByStringId(sampleId);
        if (sample != null) {
            log.debug("Checking accessibility before streaming Krona result charts...");
            if (isAccessible(sample)) {
                log.debug("Building file path to the Krona HTML file...");
                EmgFile emgFile = getEmgFile(sample.getId());
                final String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
                final String rootDirPathName = propertyContainer.getPathToAnalysisDirectory() + directoryName;
                final String filePath;
                if (taxonomy && slim && !function) {
                    filePath = rootDirPathName + propertyContainer.getKronaChartFileName(MemiPropertyContainer.KronaChartIdentifier.TAXONOMY_CHART_SLIM_VERSION);
                } else if (taxonomy && !slim && !function) {
                    filePath = rootDirPathName + propertyContainer.getKronaChartFileName(MemiPropertyContainer.KronaChartIdentifier.TAXONOMY_CHART_FULL_VERSION);
                } else if (function && slim && !taxonomy) {
                    filePath = rootDirPathName + propertyContainer.getKronaChartFileName(MemiPropertyContainer.KronaChartIdentifier.FUNCTION_CHART_SLIM_VERSION);
                } else if (function && !slim && !taxonomy) {
                    filePath = rootDirPathName + propertyContainer.getKronaChartFileName(MemiPropertyContainer.KronaChartIdentifier.FUNCTION_CHART_FULL_VERSION);
                } else {
                    return;
                }
                File fileToStream = new File(filePath);
                log.debug("Checking if Krona result file does exit before streaming it...");
                if (!fileToStream.exists()) {
                    log.warn(fileToStream.getAbsolutePath() + " doesn't exist!");
                    return;
                } else {
                    log.debug("Streaming Krona chart...");
                    ServletOutputStream servletOut = response.getOutputStream();
                    byte[] buffer = new byte[4096];
                    InputStream inputStream = new FileInputStream(new File(filePath));
                    int contentLengthCounter = 0;
                    int read;
                    while ((read = inputStream.read(buffer)) != -1) {
                        servletOut.write(buffer, 0, read);
                        contentLengthCounter += read;
                    }
                    response.setContentLength(contentLengthCounter);
                    servletOut.flush();
                    servletOut.close();
                }
            } else {//access denied
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
            }
        }
    }
}
