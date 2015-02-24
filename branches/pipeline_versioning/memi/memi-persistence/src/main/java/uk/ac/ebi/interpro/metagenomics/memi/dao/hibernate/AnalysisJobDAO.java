package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineRelease;

import java.util.List;

/**
 * Represents the data access object interface for pipeline releases.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public interface AnalysisJobDAO {

//    public Run readByRunId(String runId);

    public List<AnalysisJob> readByRunIdDeep(String externalRunIDs, String analysisStatus);

//    public Run readByRunIdDeep(String projectId, String sampleId, String runId);

}