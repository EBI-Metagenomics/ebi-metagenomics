package uk.ac.ebi.interpro.metagenomics.memi.files;

import java.io.*;

/**
 * Represents a method provider/utility class, which creates downloadable files.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class MemiFileWriter {

    private MemiFileWriter() {
    }

    /**
     * Creates a file with the specified name and the specified content.
     */
    public static File writeCSVFile(String fileContent) {
        Writer writer = null;

        try {
            File file = File.createTempFile("temp", ".tmp");
            writer = new BufferedWriter(new java.io.FileWriter(file));
            writer.write(fileContent);
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}