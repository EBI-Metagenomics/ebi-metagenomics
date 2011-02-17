package uk.ac.ebi.interpro.metagenomics.memi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.metal.MetalIconFactory;
import java.io.*;

/**
 * Represents a service class. This class handles the creation of a HTTP response,
 * which opens a download dialog.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MemiDownloadService {
    private static final Log log = LogFactory.getLog(MemiDownloadService.class);

    private final String downloadPath = "/home/maxim/projects/ebi-metagenomics/memi/memi-spring-mvc/src/main/resources/uk/ac/ebi/interpro/metagenomics/memi/services/";

    /**
     * Create a HTTP response, which opens a download dialog.
     */
    public void openDownloadDialog(HttpServletResponse response, File file, String fileName) {
        log.info("Trying to open the download dialog for the file with name " + file.getName() + "...");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            file.delete();
            //configure HTTP response
            response.setContentType("text/html;charset=UTF-8");
            //response.setContentLength(file.getFile().length);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            FileCopyUtils.copy(is, response.getOutputStream());
        } catch (FileNotFoundException e) {
            log.error("Could not find the specified downloadable file!", e);
        } catch (IOException e) {
            log.error("Could not create input stream for the specified downloadable file!", e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        log.info("Opened download dialog successfully.");
    }

    /**
     * Create a HTTP response, which opens a download dialog. Use this method to export sample specific
     * information.
     */
    public void openDownloadDialog(HttpServletResponse response, String sampleId) {
        log.info("Trying to open the download dialog to export a CSV for sample with Id" + sampleId + "...");

        InputStream sampleFileIS = null;
        InputStream headerFileIS = null;
        SequenceInputStream sis = null;
        try {
            String fileName = sampleId + ".csv";
            String pathName = downloadPath + fileName;
            File sampleFile = new File(pathName);
            File headerFile = new File(downloadPath + "data_EMG_env_samples.csv");
            if (sampleFile.canRead() && headerFile.canRead()) {
                sampleFileIS = new FileInputStream(sampleFile);
                headerFileIS = new FileInputStream(headerFile);

                sis = new SequenceInputStream(headerFileIS, sampleFileIS);

                //configure HTTP response
                response.setContentType("text/html;charset=UTF-8");
                //response.setContentLength(file.getFile().length);
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                FileCopyUtils.copy(sis, response.getOutputStream());
            }
        } catch (FileNotFoundException e) {
            log.error("Could not find the specified downloadable file!", e);
        } catch (IOException e) {
            log.error("Could not create input stream for the specified downloadable file!", e);
        } finally {
            if (sampleFileIS != null) {
                try {
                    sampleFileIS.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (headerFileIS != null) {
                try {
                    headerFileIS.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (sis != null) {
                try {
                    sis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("Opened download dialog successfully.");
    }


}
