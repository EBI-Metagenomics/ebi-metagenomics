package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineRelease;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.AnalysisJobVO;

import java.util.List;

/**
 * Represents the data access object interface for analysis jobs.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public interface AnalysisJobDAO {

    public AnalysisJob readByRunIdAndVersionDeep(String externalRunIDs, String releaseVersion, String analysisStatus);

    public List<AnalysisJob> readNonSuppressedBySampleId(Long sampleId, Long studyId);

    public List<AnalysisJob> readBySampleIds(List<Long> sampleIds, String analysisStatus);

    public List<AnalysisJob> readByJobIds(List<Long> jobIds);

    public List<AnalysisJobVO> retrieveAnalysisJobVOsDeepByStudyId(long studyId, String analysisStatus);
}