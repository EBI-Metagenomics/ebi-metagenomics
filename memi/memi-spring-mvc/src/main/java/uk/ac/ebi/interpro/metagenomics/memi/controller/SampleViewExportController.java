package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FilePathNameBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * This controller handles the request for all exports on the sample view.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class SampleViewExportController extends AbstractSampleViewController {
    private static final Log log = LogFactory.getLog(SampleViewExportController.class);

    private final String[] requestParamValues = new String[]{"biom", "taxa", "tree"};

    @RequestMapping(value = '/' + SampleViewController.VIEW_NAME + "/{sampleId}/export")
    public void doHandleSampleViewExports(@PathVariable final String sampleId,
                                          @RequestParam(required = false, value = "exportValue") final String exportValue,
                                          final HttpServletResponse response,
                                          final HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setLocale(Locale.ENGLISH);
        Sample sample = sampleDAO.readByStringId(sampleId);
        if (sample != null) {
            log.debug("Checking accessibility before streaming Krona result charts...");
            if (isAccessible(sample)) {
                final EmgFile emgFile = getEmgFile(sample.getId());
                if (emgFile != null) {
                    //set default type
                    EmgFile.ResultFileType resultFileType = EmgFile.ResultFileType.TAX_ANALYSIS_BIOM_FILE;
                    //set default file ending
                    String fileEnding = "_otu.biom";
                    if (exportValue.equalsIgnoreCase(this.requestParamValues[1])) {
                        resultFileType = EmgFile.ResultFileType.TAX_ANALYSIS_TSV_FILE;
                        fileEnding = "_otu.tsv";
                    } else if (exportValue.equalsIgnoreCase(this.requestParamValues[2])) {
                        resultFileType = EmgFile.ResultFileType.TAX_ANALYSIS_TREE_FILE;
                        fileEnding = "_newick.tre";
                    }
                    String filePathName = FilePathNameBuilder.getFilePathNameByResultType(resultFileType, emgFile, propertyContainer);
                    createFileObjectAndOpenDownloadDialog(response, request, emgFile, fileEnding, filePathName);
                }
            }
        } else {//access denied
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
        }
    }
}
