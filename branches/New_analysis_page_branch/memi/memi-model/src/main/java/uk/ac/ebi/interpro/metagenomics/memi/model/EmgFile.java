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

    public final static String TABLE_NAME = "log_file_info";

    public final static String SAMPLE_ID = "sample_id";

    private String fileID;

    private String fileName;

    private Map<String, String> fileSizeMap;

//    public static String[] fileExtensions = new String[]
//            {"_summary.go_slim", "_summary.go", "_masked.fasta",
//                    "_CDS.faa", "_I5.tsv", "_summary.ipr"};

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