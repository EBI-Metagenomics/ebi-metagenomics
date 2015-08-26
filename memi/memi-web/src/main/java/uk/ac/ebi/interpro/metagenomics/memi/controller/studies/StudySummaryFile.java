package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

/**
 * Expected file names that help to summary a studies details.
 */
public enum StudySummaryFile {
//    BP_GO_SLIM("BP_GO-slim_abundances", "Biological Process GO-slim terms found in the samples from a study.", "func", 1),
//    BP_GO("BP_GO_abundances", "Biological Process GO terms found in the samples from a study", "func", 2),
//    CC_GO_SLIM("CC_GO-slim_abundances", "Cellular Component GO-slim terms found in the samples from a study.", "func", 3),
//    CC_GO("CC_GO_abundances", "Cellular Component GO terms found in the samples from a study.", "func", 4),
//    MF_GO_SLIM("MF_GO-slim_abundances", "Molecular Function GO-slim terms found in the samples from a study.", "func", 5),
//    MF_GO("MF_GO_abundances", "Molecular Function GO terms found in the samples from a study.", "func", 6),
    IPR("IPR_abundances", "InterPro matches (TSV)", "func", 7),
    GO("GO_abundances", "Complete GO annotation (TSV)", "func", 8),
    GO_SLIM("GO-slim_abundances", "GO slim annotation (TSV)", "func", 9),
    PHYLUM_TAXONOMY("phylum_taxonomy_abundances", "Phylum level taxonomies (TSV)", "taxa", 10),
    TAXONOMY("taxonomy_abundances", "Taxonomic assignments (TSV)", "taxa", 11);

    private String filename;
    private String description;
    private String category;
    private int fileOrder;

    StudySummaryFile(String filename, String description, String category, int fileOrder) {
        this.filename = filename;
        this.description = description;
        this.category = category;
        this.fileOrder = fileOrder;
    }

    public String getFilename() {
        return filename;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getFileOrder() {
        return fileOrder;
    }

    /**
     * Does the supplied filename match one of the expected filenames in this enum?
     * @param filename Filename including file extension, but without the file path, e.g. "GO_abundances_v1.0.tsv"
     * @return
     */
    public static boolean contains(String filename) {
        for (StudySummaryFile file : StudySummaryFile.values()) {
            if (filename.startsWith(file.getFilename())) {
                // E.g. "GO_abundances_v1.0.tsv" starts with "GO_abundances"
                return true;
            }
        }

        return false;
    }

    /**
     * Return {@link StudySummaryFile} from filename.
     * @param filename
     * @return
     */
    public static StudySummaryFile lookupFromFilename(String filename) {
        if (filename == null) {
            return null;
        }

        for (StudySummaryFile file : StudySummaryFile.values()) {
            if (filename.startsWith(file.getFilename())) {
                // E.g. "GO_abundances_v1.0.tsv" starts with "GO_abundances"
                return file;
            }
        }
        return null;
    }

}
