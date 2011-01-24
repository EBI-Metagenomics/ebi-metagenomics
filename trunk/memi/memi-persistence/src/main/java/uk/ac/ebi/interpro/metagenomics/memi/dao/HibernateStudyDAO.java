package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.scan.genericjpadao.GenericDAO;

import java.util.List;

/**
 * Represents the data access object interface for studies.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface HibernateStudyDAO extends GenericDAO<Study, String> {

    /**
     * Retrieves a limited number of rows.
     *
     * @param rowNumber Number of rows which should be returned.
     */
    abstract List<Study> retrieveStudiesLimitedByRows(int rowNumber);
}