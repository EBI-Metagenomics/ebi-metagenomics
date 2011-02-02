package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.hibernate.criterion.Criterion;
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
public interface HibernateStudyDAO extends GenericDAO<Study, Long> {

    /**
     * Retrieves studies order by the specified property.
     */
    List<Study> retrieveStudiesOrderBy(String propertyName, boolean isDescendingOrder);

    /**
     * Returns an ordered list of studies.
     *
     * @param propertyName      Name of the column for which the result should be order by.
     * @param isDescendingOrder Order direction.
     */
    List<Study> retrieveOrderedPublicStudies(String propertyName, boolean isDescendingOrder);

    /**
     * Returns an ordered list of public studies where the submitter ID IS equal the specified submitter ID.
     *
     * @param submitterId       Submitter ID for the IS equal restriction.
     * @param propertyName      Name of the column for which the result should be order by.
     * @param isDescendingOrder Order direction
     */
    List<Study> retrieveOrderedStudiesBySubmitter(long submitterId, String propertyName, boolean isDescendingOrder);

    /**
     * Returns an ordered list of public studies where the submitter ID IS NOT equal the specified submitter ID.
     *
     * @param submitterId       Submitter ID for the NOT equal restriction.
     * @param propertyName      Name of the column for which the result should be order by.
     * @param isDescendingOrder Order direction.
     */
    List<Study> retrieveOrderedPublicStudiesWithoutSubId(long submitterId, String propertyName, boolean isDescendingOrder);

    List<Study> retrieveFilteredStudies(List<Criterion> crits);

}