package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

/**
 * Expected file names that help to summary a studies details.
 */
public enum StudySummaryFile {
    BP_GO_SLIM("BP_GO-slim_abundances.tsv", "Abundance tables for the Biological Process GO-slim terms found in the samples from a study.", 1),
    BP_GO("BP_GO_abundances.tsv", "Abundance tables for the Biological Process GO terms found in the samples from a study", 2),
    CC_GO_SLIM("CC_GO-slim_abundances.tsv", "Abundance tables for the Cellular Component GO-slim terms found in the samples from a study.", 3),
    CC_GO("CC_GO_abundances.tsv", "Abundance tables for the Cellular Component GO terms found in the samples from a study.", 4),
    GO_SLIM("GO-slim_abundances.tsv", "Abundance tables for all GO-slim terms found in the samples from a study.", 5),
    GO("GO_abundances.tsv", "Abundance tables for all GO terms found in the samples from a study.", 6),
    IPR("IPR_abundances.tsv", "Abundance tables for the InterPro entries found in the samples from a study.", 7),
    MF_GO_SLIM("MF_GO-slim_abundances.tsv", "Abundance tables for the Molecular Function GO-slim terms found in the samples from a study.", 8),
    MF_GO("MF_GO_abundances.tsv", "Abundance tables for the Molecular Function GO terms found in the samples from a study.", 9),
    PHYLUM_TAXONOMY("phylum_taxonomy_abundances.tsv", "Abundance tables for the phylum_level taxonomies found in the samples from a study.", 10),
    TAXONOMY("taxonomy_abundances.tsv", "Abundance tables for the full taxonomies found in the samples from a study.", 11);

    private String filename;
    private String description;
    private int fileOrder;

    StudySummaryFile(String filename, String description, int fileOrder) {
        this.filename = filename;
        this.description = description;
        this.fileOrder = fileOrder;
    }

    public String getFilename() {
        return filename;
    }

    public String getDescription() {
        return description;
    }

    public int getFileOrder() {
        return fileOrder;
    }

    /**
     * Does the supplied filename match one of the expected filenames in this enum?
     * @param filename Filename including file extension, but without the file path, e.g. "GO_abundances.tsv"
     * @return
     */
    public static boolean contains(String filename) {
        for (StudySummaryFile file : StudySummaryFile.values()) {
            if (file.getFilename().equals(filename)) {
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
            if (file.getFilename().equals(filename)) {
                return file;
            }
        }
        return null;
    }

}
