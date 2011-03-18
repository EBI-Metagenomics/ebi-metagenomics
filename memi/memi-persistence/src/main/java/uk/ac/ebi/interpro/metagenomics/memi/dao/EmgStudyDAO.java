package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;

import java.util.List;

/**
 * Represents the data access object interface for studies.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface EmgStudyDAO extends GenericDAO<EmgStudy, String> {

    /**
     * Retrieves a limited number of rows.
     *
     * @param rowNumber Number of rows which should be returned.
     */
    abstract List<EmgStudy> retrieveStudiesLimitedByRows(int rowNumber);
}
