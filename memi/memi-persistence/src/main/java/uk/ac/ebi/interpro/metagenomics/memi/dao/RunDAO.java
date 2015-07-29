package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;

import java.util.List;

/**
 * //TODO: Add description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public interface RunDAO {

    public Run readByRunIdDeep(String projectId, String sampleId, String runId, String version);

    public int countAllPublic();

    public int countAllPrivate();

    public List<QueryRunsForProjectResult> retrieveRunsByProjectId(long projectId, boolean publicOnly);
}