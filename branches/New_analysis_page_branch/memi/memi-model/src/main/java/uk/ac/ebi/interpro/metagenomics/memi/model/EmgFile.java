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

    public void addFileSizeMap(Map<String, String> fileSizeMap) {
        this.fileSizeMap = fileSizeMap;
    }

    public Map<String, String> getFileSizeMap() {
        return fileSizeMap;
    }

    public enum ResultFileType {
        GO_SLIM("_summary.go_slim"),
        GO("_summary.go"),
        MASKED_FASTA("_masked.fasta"),
        CDS_FAA("_CDS.faa"),
        I5_TSV("_I5.tsv"),
        IPR("_summary.ipr"),
        IPR_HITS("_IPRhits.fasta"),
        TAX_ANALYSIS_BIOM_FILE(".biom"),
        TAX_ANALYSIS_TREE_FILE(".tre"),
        TAX_ANALYSIS_TSV_FILE("_rRNAFiltered.fasta_rep_set_tax_assignments.txt");

        private String fileNameEnd;

        private ResultFileType(String fileNameEnd) {
            this.fileNameEnd = fileNameEnd;
        }

        public String getFileNameEnd() {
            return fileNameEnd;
        }
    }
}