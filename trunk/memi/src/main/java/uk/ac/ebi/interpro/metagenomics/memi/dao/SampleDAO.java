package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.model.Sample;

import java.util.List;

/**
 * Represents the data access object interface for samples (implemented only for the first version to get example data).
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface SampleDAO {

    /**
     * Returns all studies which are available from database.
     */
    public List<Sample> getAllSamples();

    public Sample getSampleById(long id);
}