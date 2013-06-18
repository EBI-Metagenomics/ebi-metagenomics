package uk.ac.ebi.interpro.metagenomics.memi.model.apro;

/**
 * Represents a simple country object which can be easily expanded later on.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class Country {
    private String countryName;

    private Country() {
    }

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return getCountryName();
    }
}