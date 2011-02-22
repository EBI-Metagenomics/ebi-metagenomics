package uk.ac.ebi.interpro.metagenomics.memi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import javax.servlet.http.HttpServletResponse;
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

    public static final String DOWNLOAD_PATH = "/home/maxim/projects/ebi-metagenomics/memi/memi-spring-mvc/src/main/resources/uk/ac/ebi/interpro/metagenomics/memi/services/";

    /**
     * Create a HTTP response, which opens a download dialog.
     */
    public void openDownloadDialog(HttpServletResponse response, File file, String fileName, boolean isDeleteFile) {
        log.info("Trying to open the download dialog for the file with name " + file.getName() + "...");
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            if (isDeleteFile) {
                file.delete();
            }
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
     * Opens a download dialog to export more detailed sample info. For each sample was built a CSV file in
     * advance. If a user wants to download detailed info for more than one sample, then all CSV files will be
     * assembled to one CSV file containing all sample info.
     *
     * @param response  HTTP response.
     * @param type      Specifies the sample/study type. This is necessary distinguish between two CSV file
     *                  headers, one header file for Host-associated and another header file
     *                  for environmental samples.
     * @param sampleIDs Specifies the name of the CSV file for each sample.
     * @return TRUE if a downloadable file exists and 'Save to file' dialog could be open.
     */
    public boolean openDownloadDialog(HttpServletResponse response, Study.StudyType type, String... sampleIDs) {
        log.info("Trying to open the download dialog to export a CSV file for sample(s) with ID(s)" + sampleIDs + "...");

        InputStream sampleFileIS = null;
        InputStream headerFileIS = null;
        //sequences input stream instance
        InputStream sis = null;
        //name of the downloadable file
        String fileName = "";
        try {
            //create input stream for header file
            if (type != null) {
                headerFileIS = getCSVFileHeaderStream(type);
            }
            //create and stream sample files
            for (String sampleID : sampleIDs) {
                if (sampleIDs.length == 1) {
                    fileName = sampleID;
                }
                String pathName = DOWNLOAD_PATH + sampleID + ".csv";
                File sampleFile = new File(pathName);
                if (sampleFile.canRead()) {
                    //create a file input stream and concatenate if the previous input stream if exists
                    sampleFileIS = new FileInputStream(sampleFile);
                    if (sis == null) {
                        sis = new SequenceInputStream(headerFileIS, sampleFileIS);
                    } else {
                        sis = new SequenceInputStream(sis, sampleFileIS);
                    }
                }
            }
            if (sis != null) {
                //configure HTTP response
                response.setContentType("text/html;charset=UTF-8");
                //response.setContentLength(file.getFile().length);
                fileName = (sampleIDs.length == 1 ? fileName : "samples") + ".csv";
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                FileCopyUtils.copy(sis, response.getOutputStream());
                log.info("Opened download dialog successfully.");
                return true;
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
        log.warn("Could not open download dialog!");
        return false;
    }

    /**
     * Creates an input stream from a hard coded file name.
     */
    private InputStream getCSVFileHeaderStream(Study.StudyType type) {
        File headerFile = null;
        if (type.equals(Study.StudyType.ENVIRONMENTAL)) {
            headerFile = new File(DOWNLOAD_PATH + "data_EMG_env_samples.csv");
        } else if (type.equals(Study.StudyType.HOST_ASSOCIATED)) {
            headerFile = new File(DOWNLOAD_PATH + "data_EMG_host_samples.csv");
        } else {
            log.warn("Could not set any header file, because an undefined study type was specified!");
        }
        if (headerFile != null && headerFile.canRead()) {
            try {
                return new FileInputStream(headerFile);
            } catch (FileNotFoundException e) {
                log.error("Could not build file input steam from the specified header file (" + headerFile.getAbsolutePath() + ")!", e);
            }
        }
        return null;
    }

    /**
     * Create a HTTP response, which opens a download dialog. Use this method to export sample specific
     * information.
     */

//    public void openDownloadDialog(HttpServletResponse response, String sampleId) {
//        log.info("Trying to open the download dialog to export a CSV file for sample with Id" + sampleId + "...");
//
//        InputStream sampleFileIS = null;
//        InputStream headerFileIS = null;
//        SequenceInputStream sis = null;
//        try {
//            String fileName = sampleId + ".csv";
//            String pathName = downloadPath + fileName;
//            File sampleFile = new File(pathName);
//            File headerFile = new File(downloadPath + "data_EMG_env_samples.csv");
//            if (sampleFile.canRead() && headerFile.canRead()) {
//                sampleFileIS = new FileInputStream(sampleFile);
//                headerFileIS = new FileInputStream(headerFile);
//
//                sis = new SequenceInputStream(headerFileIS, sampleFileIS);
//
//                //configure HTTP response
//                response.setContentType("text/html;charset=UTF-8");
//                //response.setContentLength(file.getFile().length);
//                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//                FileCopyUtils.copy(sis, response.getOutputStream());
//            }
//        } catch (FileNotFoundException e) {
//            log.error("Could not find the specified downloadable file!", e);
//        } catch (IOException e) {
//            log.error("Could not create input stream for the specified downloadable file!", e);
//        } finally {
//            if (sampleFileIS != null) {
//                try {
//                    sampleFileIS.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (headerFileIS != null) {
//                try {
//                    headerFileIS.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (sis != null) {
//                try {
//                    sis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        log.info("Opened download dialog successfully.");
//    }


}
