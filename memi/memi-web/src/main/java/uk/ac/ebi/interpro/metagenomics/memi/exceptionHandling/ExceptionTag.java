package uk.ac.ebi.interpro.metagenomics.memi.exceptionHandling;

/**
 * Helps to classify/tag {@link IllegalStateException}s for this application.
 */
public enum ExceptionTag {
    DATABASE_CURATION_ISSUE("Database curation issue", "Use this tag to indicate database curation issues!");

    private String tag;

    private String description;


    private ExceptionTag(String tag, String description) {
        this.tag = tag;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.tag + " caused by: ";
    }
}
