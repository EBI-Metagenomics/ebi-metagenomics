package uk.ac.ebi.interpro.metagenomics.memi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.support.RequestContextUtils;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.EngineeredSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.EnvironmentSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.HostSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.tools.StreamCopyUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.Channel;
import java.util.Set;

/**
 * Represents a service class. This class handles the creation of a HTTP response,
 * which opens a download dialog.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MemiDownloadService {
    private final static Log log = LogFactory.getLog(MemiDownloadService.class);

    private final String CLASS_PATH = "uk/ac/ebi/interpro/metagenomics/memi/services/";

    /**
     * Create a HTTP response, which opens a download dialog with a stream of the specified file.
     *
     * @return TRUE if a downloadable file exists and 'Save to file' dialog could be open.
     */
    public boolean openDownloadDialog(final HttpServletResponse response, final HttpServletRequest request,
                                      final File file, String fileName, boolean isDeleteFile) {
        log.info("Trying to open the download dialog for the file with name " + file.getName() + "...");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            //configure HTTP response
            assembleServletResponse(response, request, file, fis, fileName);
            if (isDeleteFile) {
                file.delete();
            }
            log.info("Opened download dialog successfully.");
            return true;
        } catch (FileNotFoundException e) {
            log.warn("Could not find the specified downloadable file: " + file.getAbsolutePath(), e);
        } catch (IOException e) {
            log.error("Could not create input stream for the specified downloadable file (s.a.)!", e);
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    log.error("Could not close input stream correctly!", e);
                }
        }
        return false;
    }

    /**
     * Opens a download dialog to export more detailed sample info. For each sample was built a CSV file in
     * advance. If a user wants to download detailed info for more than one sample, then all CSV files will be
     * assembled to one CSV file containing all sample info. CSV files are stream from project resource package.
     *
     * @param response  HTTP response.
     * @param clazz     Specifies the sample type. This is necessary distinguish between two CSV file
     *                  headers, one header file for Host-associated and another header file
     *                  for environmental samples.
     * @param sampleIDs Specifies the name of the CSV file for each sample.
     * @return TRUE if a downloadable file exists and 'Save to file' dialog could be open.
     */
    public boolean openDownloadDialog(HttpServletResponse response, Class<? extends Sample> clazz, Set<String> sampleIDs) {
        log.info("Trying to open the download dialog to export a CSV file for sample(s) with ID(s)" + sampleIDs.toArray() + "...");

        boolean result = false;
        InputStream sampleFileIs = null;
        InputStream headerFileIs = null;
        //sequences input stream instance
        InputStream sis = null;
        //name of the downloadable file
        String fileName = "";
        //create input stream for header file
        if (clazz != null) {
            Resource headerResource = getCSVFileHeaderStream(clazz);
            if (headerResource != null && headerResource.exists()) {
                try {
                    headerFileIs = headerResource.getInputStream();
                } catch (IOException e) {
                    log.warn("Could not get input stream for the specified resource: " + headerResource.getFilename(), e);
                }
            } else {
                log.debug("Header file resource is NULL or doesn't exist!");
            }

        }
        //create and stream sample files from project resource directory
        for (String sampleID : sampleIDs) {
            if (sampleIDs.size() == 1) {
                fileName = sampleID;
            }
            //create a file input stream and concatenate if the previous input stream if exists
            String pathName = sampleID + ".csv";
            String path = CLASS_PATH + pathName;
            log.debug("Creating sample file resource with path " + path);
            Resource sampleResource = new ClassPathResource(path);
            if (sampleResource.exists()) {
                try {
                    sampleFileIs = sampleResource.getInputStream();
                } catch (IOException e) {
                    log.warn("Could not get input stream for the specified resource: " + sampleResource.getFilename(), e);
                }
            }
            if (headerFileIs != null && sampleFileIs != null) {
                if (sis == null) {
                    sis = new SequenceInputStream(headerFileIs, sampleFileIs);
                } else {
                    sis = new SequenceInputStream(sis, sampleFileIs);
                }
            } else {
                log.debug("Header file input stream or sample file input stream is NULL!");
            }
        }
        if (sis != null) {
            fileName = (sampleIDs.size() == 1 ? fileName : "samples") + ".csv";
            //configure HTTP response
            result = assembleServletResponse(response, sis, fileName);
        }
        try {
            if (sampleFileIs != null) {
                sampleFileIs.close();
            }
        } catch (IOException e) {
            log.warn("Could not close SAMPLE file InputStream!", e);
        }
        try {
            if (headerFileIs != null) {
                headerFileIs.close();
            }
        } catch (IOException e) {
            log.warn("Could not close HEADER file InputStream!", e);
        }
        return result;
    }

    /**
     * Configures HTTP servlet response for a file download.
     */

    private boolean assembleServletResponse(final HttpServletResponse response, final HttpServletRequest request,
                                            final File file, final FileInputStream fis, final String fileName) {
        log.debug("Trying to assemble servlet response");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        //Resumable download options
        response.setContentLength((int) file.length());
        response.setHeader("Accept-Ranges", "bytes");
        response.setBufferSize(2048000);
        //Transfer-Encoding
//        response.setHeader("Transfer-Encoding", "chunked");

        //Get and set content size
        ServletContext context = RequestContextUtils.getWebApplicationContext(request).getServletContext();
        String mimetype = context.getMimeType(file.getAbsolutePath());
        //response.setContentType("text/plain; charset=utf-8");
        response.setContentType(mimetype);
        try {
            ServletOutputStream sot = response.getOutputStream();
            StreamCopyUtil.copy(fis, sot);
            sot.flush();
            sot.close();
            return true;
        } catch (IOException e) {
            log.warn("Could not get output stream to open the download dialog!", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    log.warn("Could not close input stream after the assembly of the HTTP response!");
                }
            }
        }
        return false;
    }


    /**
     * Configures HTTP servlet response.
     */

    private boolean assembleServletResponse(HttpServletResponse response, InputStream is, String fileName) {
        log.debug("Trying to assemble servlet response");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        try {
            FileCopyUtils.copy(is, response.getOutputStream());
            log.info("Opened download dialog successfully.");
            return true;
        } catch (IOException e) {
            log.warn("Could not get output stream to open the download dialog!", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.warn("Could not close input stream after the assembly of the HTTP response!");
                }
            }
        }
        return false;
    }

    /**
     * Creates a resource to CSV files, which is related to the specified study type. These files contain
     * CSV header information.
     */
    private Resource getCSVFileHeaderStream(Class<? extends Sample> clazz) {
        String headerFileName = null;
        if (clazz.equals(EnvironmentSample.class) || clazz.equals(EngineeredSample.class)) {
            headerFileName = "data_EMG_env_samples.csv";
        } else if (clazz.equals(HostSample.class)) {
            headerFileName = "data_EMG_host_samples.csv";
        } else {
            log.warn("Could not set any header file name, because an undefined study type was specified!");
        }
        if (headerFileName != null) {
            String path = CLASS_PATH + headerFileName;
            log.debug("Creating header file resource with path " + path);
            return new ClassPathResource(path);
        }
        return null;
    }
}
