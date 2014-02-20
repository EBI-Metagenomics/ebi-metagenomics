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

    private String colorCode;

    public TaxonomyData(String superKingdom, String phylum, Integer numberOfHits, String percentage) {
        this.superKingdom = superKingdom;
        this.phylum = phylum;
        this.numberOfHits = numberOfHits;
        this.percentage = percentage;
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

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}