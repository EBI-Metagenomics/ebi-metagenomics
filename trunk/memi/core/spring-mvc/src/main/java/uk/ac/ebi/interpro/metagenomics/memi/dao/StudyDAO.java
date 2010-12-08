package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

import java.util.List;

/**
 * Represents the data access object interface for studies.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface StudyDAO {

    /**
     * Returns all studies which are available from database
     */
    public List<Study> getAllStudies();

    /**
     * If TRUE specified, then it will return all public available studies, OTHERWISE it will return only private studies
     */
    public List<Study> getStudiesByVisibility(boolean isPublic);

    /**
     * self-explanatory
     */
    public Study getStudyById(long id);

    /**
     * self-explanatory
     */
    public void deleteStudy(Study study);
}