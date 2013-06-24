package uk.ac.ebi.interpro.ebeyesearch;

/**
 * A domain is a data resource under the EBI Search engine.
 * <p/>
 * Domains are organised in a hierarchy.
 * <p/>
 * A category is a first-level domain such as Genomes, Nucleotide sequences etc.
 * <p/>
 * A leaf domain is a dataset node with no children.
 */
public enum Domain {

    NUCLEOTIDE_SEQUENCES("nucleotideSequences", true),
    SRA_SAMPLE("sra-sample", false),
    SRA_STUDY("sra-study", false),
    PROJECT("project", false),
    INTERPRO("interpro", false);

    private String databaseName;

    private boolean isFirstLevelDomain;

    private Domain(String databaseName, boolean isFirstLevelDomain) {
        this.databaseName = databaseName;
        this.isFirstLevelDomain = isFirstLevelDomain;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
