package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.RunStatisticsVO;
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

    public Run readByRunIdDeep(Long studyId, String sampleId, String runId, String version);

    public Map<String, Long> retrieveRunCountsGroupedByExperimentType(int analysisStatusI);

    public RunStatisticsVO retrieveStatistics();

    public List<QueryRunsForProjectResult> retrieveRunsByProjectId(long projectId, boolean publicOnly);

    public List<ProjectSampleRunMappingVO> retrieveListOfRunAccessionsByProjectId(long projectId);

    Map<String, Object> retrieveStudyAndSampleAccessions(String runId);
}