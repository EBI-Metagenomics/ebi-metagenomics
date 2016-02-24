package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.ProjectSampleRunMappingVO;

import java.util.List;
import java.util.Map;

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

    public Map<String, Integer> retrieveRunCountsGroupedByExperimentType(int analysisStatusI);

    public List<QueryRunsForProjectResult> retrieveRunsByProjectId(long projectId, boolean publicOnly);

    public List<ProjectSampleRunMappingVO> retrieveListOfRunAccessionsByProjectId(long projectId);
}