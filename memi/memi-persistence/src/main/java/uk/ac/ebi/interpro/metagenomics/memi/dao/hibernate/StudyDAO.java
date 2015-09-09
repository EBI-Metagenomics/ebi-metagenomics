package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.hibernate.criterion.Criterion;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.Collection;
import java.util.List;

/**
 * Represents the data access object interface for studies.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface StudyDAO extends ISecureEntityDAO<Study> {

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
     * Returns a list of public studies where the submitter ID IS equal the specified submitter ID.
     *
     * @param submissionAccountId Submitter ID for the IS equal restriction.
     */
    List<Study> retrieveStudiesBySubmitter(String submissionAccountId);

    /**
     * Returns an ordered list of public studies where the submitter ID IS NOT equal the specified submitter ID.
     *
     * @param submitterId       Submitter ID for the NOT equal restriction.
     * @param propertyName      Name of the column for which the result should be order by.
     * @param isDescendingOrder Order direction.
     */
    List<Study> retrieveOrderedPublicStudiesWithoutSubId(long submitterId, String propertyName, boolean isDescendingOrder);

    /**
     * Returns a list of public studies where the submitter ID IS NOT equal the specified submitter ID.
     *
     * @param submitterId Submitter ID for the NOT equal restriction.
     */
    List<Study> retrievePublicStudiesWithoutSubId(long submitterId);

    /**
     * Returns a list of studies by the specified criteria.
     */
    List<Study> retrieveFilteredStudies(List<Criterion> crits);


    /**
     * Returns a list of asc ordered studies by the specified criteria.
     */
    List<Study> retrieveFilteredStudies(List<Criterion> crits, Boolean isDescendingOrder, String propertyName);

    /**
     * Returns a sub list of studies by the specified criteria. This method is used for pagination.
     *
     * @param crits         A list of criteria which should be add to the Hibernate query.
     * @param startPosition Used for pagination. In terms of Oracle this parameter specifies the row number of the first entry which should be selected.
     * @param pageSize      Used for pagination.Specifies how many entries should be selected for the specified page.
     * @return Filtered list of studies.
     */
    List<Study> retrieveFilteredStudies(List<Criterion> crits, Integer startPosition, Integer pageSize, Boolean isDescendingOrder, String propertyName);

    /**
     * Returns a list of all public studies.
     */
    List<Study> retrievePublicStudies();

    Study readByStringId(String studyId);

    /**
     * @return Number of all public samples.
     */
    Long countAllPublic();

    /**
     * @return Number of all private samples.
     */
    Long countAllPrivate();

    /**
     * Counts studies by criteria.
     *
     * @param crits A list of Hibernate criteria which should be add to the Hibernate query.
     * @return
     */
    Long countByCriteria(List<Criterion> crits);

    Long countPublicStudiesFilteredByBiomes(Collection<Integer> biomeIds);

    /**
     * Counts all submission accounts associated to a study.
     *
     * @return Number of submission accounts.
     */
    Long countDistinctSubmissionAccounts();

    /**
     * Distinct count over column ext_study_id.
     *
     * @return Number of distinct studies (no matter which status, public, private or something else)
     */
    Long countDistinct();
}