package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.model.Run;

/**
 * //TODO: Add description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public interface RunDAO {

    public Run readByRunIdDeep(String projectId, String sampleId, String runId, String version);

    int countAllPublic();

    int countAllPrivate();
}