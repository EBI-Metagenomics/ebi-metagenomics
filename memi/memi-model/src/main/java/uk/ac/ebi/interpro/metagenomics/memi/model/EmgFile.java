package uk.ac.ebi.interpro.metagenomics.memi.model;

import java.util.Map;

/**
 * Represents a simple EMG file (database model class).
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class EmgFile {

    /**
     * Table name in the EMG schema.
     */
    public final static String TABLE_NAME = "log_file_info";

    /**
     * Column name in the log_file_info table.
     */
    public final static String SAMPLE_ID = "sample_id";

    /**
     * Column name in the log_file_info table.
     */
    public final static String FILE_NAME = "FILE_NAME";

    /**
     * Column name in the log_file_info table.
     */
    public final static String FILE_ID = "FILE_ID";

    private String fileID;

    private String fileName;

    /**
     * Prevents an calls from outside the package.
     */
    private EmgFile() {

    }

    public EmgFile(String fileID, String fileName) {
        this.fileID = fileID;
        this.fileName = fileName;
    }

    public String getFileID() {
        return fileID;
    }

    public String getFileIDInUpperCase() {
        return fileID.toUpperCase();
    }

    public String getFileName() {
        return fileName;
    }
}