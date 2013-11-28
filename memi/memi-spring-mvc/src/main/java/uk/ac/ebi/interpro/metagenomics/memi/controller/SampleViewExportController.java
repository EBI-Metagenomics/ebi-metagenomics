package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.StreamCopyUtil;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;

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
public class SampleViewExportController extends AbstractSampleViewController {
    private static final Log log = LogFactory.getLog(SampleViewExportController.class);

    private final String[] exportRequestParamValues = new String[]{"biom", "taxa", "tree", "5SrRNA", "16SrRNA", "23SrRNA", "readsWithMatches", "readsWithoutMatches"};

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
                    //set default file definition
                    DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.OTUS_BIOM_FORMAT_FILE.name());
                    //if export value = taxa
                    if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[1])) {
                        fileDefinition = fileDefinitionsMap.get(FileDefinitionId.TAX_ANALYSIS_TSV_FILE.name());
                    }//if export value = tree
                    else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[2])) {
                        fileDefinition = fileDefinitionsMap.get(FileDefinitionId.TAX_ANALYSIS_TREE_FILE.name());
                    }//if export value = 5SrRNA
                    else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[3])) {
                        fileDefinition = fileDefinitionsMap.get(FileDefinitionId.R_RNA_5S_FASTA_FILE.name());
                    } //if export value = 16SrRNA
                    else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[4])) {
                        fileDefinition = fileDefinitionsMap.get(FileDefinitionId.R_RNA_16S_FASTA_FILE.name());
                    }//if export value = 23SrRNA
                    else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[5])) {
                        fileDefinition = fileDefinitionsMap.get(FileDefinitionId.R_RNA_23S_FASTA_FILE.name());
                    }//if export value = readsWithMatches
                    else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[6])) {
                        fileDefinition = fileDefinitionsMap.get(FileDefinitionId.READS_WITH_MATCHES_FASTA_FILE.name());
                    }//if export value = readsWithoutMatches
                    else if (exportValue.equalsIgnoreCase(this.exportRequestParamValues[7])) {
                        fileDefinition = fileDefinitionsMap.get(FileDefinitionId.READS_WITHOUT_MATCHES_FASTA_FILE.name());
                    }

                    File fileObject = FileObjectBuilder.createFileObject(emgFile, propertyContainer, fileDefinition);
                    openDownloadDialog(response, request, emgFile, fileDefinition.getDownloadName(), fileObject);
                }
            } else {//access denied
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
            }
        } else {//sample not found
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.sendRedirect("/metagenomics/sample/" + sampleId + "/accessDenied");
        }
    }

    @RequestMapping(value = '/' + SampleViewController.VIEW_NAME + "/{sampleId}/export", method = RequestMethod.POST)
    public void doHandleSampleViewPostExports(@PathVariable final String sampleId,
                                              @RequestBody String imageData,
                                              final HttpServletResponse response) throws IOException {
        Sample sample = sampleDAO.readByStringId(sampleId);
        if (sample != null) {
            log.debug("Checking accessibility before streaming Krona result charts...");
            if (isAccessible(sample)) {
                BufferedReader reader = new BufferedReader(new StringReader(imageData));
                String line;
                String dataUrl = "";
                String fileName = "";
                while ((line = reader.readLine()) != null) {
                    int index = line.indexOf("=") + 1;
                    if (index > 1 && line.length() > index) {
                        if (line.startsWith("fileName=")) {
                            fileName = line.substring(index);
                        } else if (line.startsWith("dataUrl=")) {
                            dataUrl = line.substring(index);
                        }
                    }
                }
                String encodingPrefix = "base64,";
                int contentStartIndex = dataUrl.indexOf(encodingPrefix) + encodingPrefix.length();
                byte[] imageDataByteArray = Base64.decodeBase64(dataUrl.substring(contentStartIndex));
                InputStream is = new ByteArrayInputStream(imageDataByteArray);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + sample.getSampleId() + "_" + fileName + "\"");
                response.setContentLength(imageDataByteArray.length);
                response.setHeader("Accept-Ranges", "bytes");
                response.setBufferSize(2048000);
                //Get and set content size
                response.setContentType("image/png;charset=UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.setLocale(Locale.ENGLISH);
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
}