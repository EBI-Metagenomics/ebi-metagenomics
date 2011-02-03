package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.hibernate.criterion.Criterion;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.scan.genericjpadao.GenericDAO;

import java.util.List;

/**
 * Represents the data access object interface for EMG samples.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface HibernateSampleDAO extends GenericDAO<Sample, Long> {

    List<Sample> retrieveSamplesByStudyId(long studyId);

    /**
     * Returns an ordered list of public samples.
     *
     * @param propertyName      Name of the column for which the result should be order by.
     * @param isDescendingOrder Order direction.
     */
    List<Sample> retrieveOrderedPublicSamples(String propertyName, boolean isDescendingOrder);

    /**
     * Returns an ordered list of public samples where the submitter ID IS equal the specified submitter ID.
     *
     * @param submitterId       Submitter ID for the IS equal restriction.
     * @param propertyName      Name of the column for which the result should be order by.
     * @param isDescendingOrder Order direction
     */
    List<Sample> retrieveOrderedSamplesBySubmitter(long submitterId, String propertyName, boolean isDescendingOrder);

    /**
     * Returns an ordered list of public samples where the submitter ID IS NOT equal the specified submitter ID.
     *
     * @param submitterId       Submitter ID for the NOT equal restriction.
     * @param propertyName      Name of the column for which the result should be order by.
     * @param isDescendingOrder Order direction.
     */
    List<Sample> retrieveOrderedPublicSamplesWithoutSubId(long submitterId, String propertyName, boolean isDescendingOrder);

    /**
     * Returns a list of studies by the specified criteria.
     */
    List<Sample> retrieveFilteredSamples(List<Criterion> crits);
}