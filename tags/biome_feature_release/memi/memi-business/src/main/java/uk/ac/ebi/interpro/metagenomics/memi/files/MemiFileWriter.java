package uk.ac.ebi.interpro.metagenomics.memi.files;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * Represents a method provider/utility class, which creates downloadable files.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MemiFileWriter {
    private final static Log log = LogFactory.getLog(MemiFileWriter.class);

    private MemiFileWriter() {
    }

    /**
     * Creates a file with the specified name and the specified content.
     */
    public static File writeCSVFile(String fileContent) {
        Writer writer = null;

        File file = null;
        try {
            file = File.createTempFile("temp", ".tmp");
        } catch (IOException e) {
            log.warn("Could not create a TEMP file!", e);
        }
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            log.warn("Could not instantiate the buffered file writer!", e);
        }
        try {
            writer.write(fileContent);
        } catch (IOException e) {
            log.warn("Could not write the specified file content to the buffered file writer!", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error("Could not close buffered writer!", e);
                }
            }

        }
        return file;
    }
}