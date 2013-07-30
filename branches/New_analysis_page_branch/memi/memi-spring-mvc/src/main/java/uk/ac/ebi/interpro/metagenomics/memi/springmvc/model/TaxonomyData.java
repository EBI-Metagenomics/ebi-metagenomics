package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

/**
 * Simple representation of a column row to render the taxonomy Google charts.
 *
 * @author Maxim Scheremetjew
 */
public class TaxonomyData {

    private String superKingdom;

    private String phylum;

    private Integer numberOfHits;

    private String percentage;

    private String colourCode;

    public TaxonomyData(String superKingdom, String phylum, Integer numberOfHits, String percentage, String colourCode) {
        this.superKingdom = superKingdom;
        this.phylum = phylum;
        this.numberOfHits = numberOfHits;
        this.percentage = percentage;
        this.colourCode = colourCode;
    }

    public String getSuperKingdom() {
        return superKingdom;
    }

    public String getPhylum() {
        return phylum;
    }

    public Integer getNumberOfHits() {
        return numberOfHits;
    }

    public String getPercentage() {
        return percentage;
    }

    public String getColourCode() {
        return colourCode;
    }
}