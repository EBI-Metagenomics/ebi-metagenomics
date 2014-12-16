package uk.ac.ebi.interpro.metagenomics.memi.dao.erapro;

import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Country;

import java.util.Collection;

/**
 * Represents the data access object interface for {@link uk.ac.ebi.interpro.metagenomics.memi.model.apro.Country}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface CountryDAO {
    /**
     * @return All countries in the table.
     */
    public Collection<Country> getAllCountries();
}