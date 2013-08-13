package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

/**
 * Definies a set of file definition identifiers which allows access to the file definition map. See also Spring context file 'downloadable-files-context.xml'.
 */
public enum FileDefinitionId {
    MASKED_FASTA,
    READS_WITH_PREDICTED_CDS_FILE,
    PREDICTED_CDS_FILE,
    OTUS_BIOM_FORMAT_FILE,
    TAX_ANALYSIS_TSV_FILE,
    TAX_ANALYSIS_TREE_FILE,
    R_RNA_5S_FASTA_FILE,
    R_RNA_16S_FASTA_FILE,
    R_RNA_23S_FASTA_FILE,
    READS_WITH_MATCHES_FASTA_FILE,
    READS_WITHOUT_MATCHES_FASTA_FILE,
    INTERPROSCAN_MATCHES_FILE,
    PREDICTED_CDS_WITH_INTERPRO_MATCHES_FILE,
    GO_SLIM_FILE,
    GO_COMPLETE_FILE,
    KRONA_HTML_FILE,
    KINGDOM_COUNTS_FILE;
}
