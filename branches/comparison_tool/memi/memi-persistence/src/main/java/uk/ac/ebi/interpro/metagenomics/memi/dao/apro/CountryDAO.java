package uk.ac.ebi.interpro.metagenomics.memi.dao.apro;

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
    //TODO: This variable shouldn't be visible on Google code
    public final static String TABLE_NAME = "spin2006.cv_submitter_country";

    /**
     * @return All countries in the table.
     */
    public Collection<Country> getAllCountries();
}