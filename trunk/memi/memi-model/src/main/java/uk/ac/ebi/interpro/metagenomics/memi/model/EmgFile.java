package uk.ac.ebi.interpro.metagenomics.memi.model;

/**
 * Represents a simple EMG file.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class EmgFile {

    private String fileID;

    private String fileName;

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
