package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineRelease;

/**
 * Represents the data access object interface for pipeline releases.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public interface PipelineReleaseDAO extends GenericDAO<PipelineRelease, Long> {

    PipelineRelease readByReleaseVersion(String releaseVersion);
}