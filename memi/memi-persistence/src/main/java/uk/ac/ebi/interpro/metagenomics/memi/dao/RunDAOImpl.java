package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.DataStatisticsVO;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.ProjectSampleRunMappingVO;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.*;

/**
 * This data access object is mainly used to query the analysis job table in EMG.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Repository
public class RunDAOImpl implements RunDAO {

    private JdbcTemplate jdbcTemplate;

    private String schemaName;

    private final Log log = LogFactory.getLog(RunDAOImpl.class);

    @Autowired(required = true)
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired(required = true)
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }


    public Run readByRunIdDeep(String projectId, String sampleId, String runId, String version) {
        try {
//            String sql = "SELECT * FROM " + schemaName + "." + "log_file_info l, SAMPLE sa, study st WHERE st.ext_study_id = ? AND sa.ext_SAMPLE_id = ? AND l.sra_run_ids = ?";
            String sql = "SELECT aj.external_run_ids,sa.ext_sample_id,st.ext_study_id,sa.submission_account_id,sa.is_public,aj.sample_id, r.release_version FROM ANALYSIS_JOB aj, PIPELINE_RELEASE r, SAMPLE sa, STUDY st WHERE aj.pipeline_id=r.pipeline_id AND st.ext_study_id = ? AND sa.ext_sample_id = ? AND aj.external_run_ids = ? AND r.release_version = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{projectId, sampleId, runId, version}, new RunDeepRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    //TODO: Migrate to Hibernate SQL (AnalysisJobDAO)
    public Map<String, Long> retrieveRunCountsGroupedByExperimentType(final int analysisStatusId) {
        try {
            Map<String, Long> result = new HashMap<String, Long>();
            String sql = "select et.experiment_type, count(distinct j.external_run_ids) as count from ANALYSIS_JOB j,  EXPERIMENT_TYPE et where et.experiment_type_id = j.experiment_type_id AND j.analysis_status_id = ? group by et.experiment_type";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new Object[]{analysisStatusId});
            for (Map<String, Object> row : rows) {
                result.put((String) (row.get("EXPERIMENT_TYPE")), (Long) row.get("COUNT"));
            }
            return result;
        } catch (DataAccessException exception) {
            throw exception;
        }
    }

    public DataStatisticsVO retrieveStatistics() {
        try {
            DataStatisticsVO stats = new DataStatisticsVO();
            String sql = "select p.is_public, count(distinct p.EXT_STUDY_ID) as num_of_studies, count(distinct sa.EXT_SAMPLE_ID) as num_of_samples, count(distinct aj.EXTERNAL_RUN_IDS) as num_of_runs from STUDY p join SAMPLE sa on (p.STUDY_ID = sa.STUDY_ID) join ANALYSIS_JOB aj on (sa.SAMPLE_ID = aj.SAMPLE_ID) where p.IS_PUBLIC in (0,1) and sa.IS_PUBLIC in (0,1) and aj.ANALYSIS_STATUS_ID = 3 group by p.IS_PUBLIC";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            for (Map<String, Object> row : rows) {
                int isPublic = (Integer) row.get("IS_PUBLIC");
                long numOfStudies = (Long) row.get("num_of_studies");
                long numOfSamples = (Long) row.get("num_of_samples");
                long numOfRuns = (Long) row.get("num_of_runs");
                if (isPublic == 1) {
                    stats.setNumOfPublicStudies(numOfStudies);
                    stats.setNumOfPublicSamples(numOfSamples);
                    stats.setNumOfPublicRuns(numOfRuns);
                } else {
                    stats.setNumOfPrivateStudies(numOfStudies);
                    stats.setNumOfPrivateSamples(numOfSamples);
                    stats.setNumOfPrivateRuns(numOfRuns);
                }
            }
            return stats;
        } catch (DataAccessException exception) {
            throw exception;
        }
    }

    /**
     * Returns a list of all project runs.
     *
     * @param projectId
     * @return Mapping between project, sample and run accessions.
     */
    public List<ProjectSampleRunMappingVO> retrieveListOfRunAccessionsByProjectId(long projectId) {
        try {
            StringBuilder sb = new StringBuilder()
                    .append("SELECT st.ext_study_id as study_id, sa.ext_sample_id as sample_id, aj.external_run_ids as run_id ")
                    .append("FROM ")
                    .append("ANALYSIS_JOB aj, ")
                    .append("SAMPLE sa, ")
                    .append("STUDY st ")
                    .append("WHERE st.study_id = sa.study_id AND sa.sample_id = aj.sample_id AND st.study_id = ? ");

            final String sql = sb.toString();
            List<ProjectSampleRunMappingVO> results = jdbcTemplate.query(sql, new Object[]{projectId}, new BeanPropertyRowMapper<ProjectSampleRunMappingVO>(ProjectSampleRunMappingVO.class));
            return results;

        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    /**
     * List of runs for a study.
     *
     * @param projectId
     * @return
     */
    public List<QueryRunsForProjectResult> retrieveRunsByProjectId(long projectId, boolean publicOnly) {
        try {
            // Example...
            // SELECT st.ext_study_id, aj.sample_id, sa.ext_sample_id, tmp.ct, aj.external_run_ids, aj.experiment_type, sa.submission_account_id, sa.is_public, r.release_version FROM ANALYSIS_JOB aj, PIPELINE_RELEASE r, sample sa, study st, (select aj.sample_id, count(aj.sample_id) as ct from study st, sample sa, ANALYSIS_JOB aj where st.study_id=sa.study_id AND sa.sample_id = aj.sample_id AND st.ext_study_id = 'ERP008551' GROUP BY aj.sample_id) tmp WHERE aj.pipeline_id=r.pipeline_id AND st.study_id=sa.study_id AND sa.sample_id = aj.sample_id AND tmp.sample_id = aj.sample_id AND st.ext_study_id = 'ERP008551' order by sa.ext_sample_id, aj.external_run_ids;
            // SELECT aj.sample_id, sa.ext_sample_id, sa.sample_name, tmp.ct, aj.external_run_ids, aj.experiment_type, sa.submission_account_id, sa.is_public, r.release_version FROM ANALYSIS_JOB aj, PIPELINE_RELEASE r, sample sa, (select aj.sample_id, count(aj.sample_id) as ct from sample sa, ANALYSIS_JOB aj where sa.sample_id = aj.sample_id AND sa.study_id = 434 GROUP BY aj.sample_id) tmp WHERE aj.pipeline_id=r.pipeline_id AND sa.sample_id = aj.sample_id AND tmp.sample_id = aj.sample_id AND sa.study_id = 434 order by sa.ext_sample_id, aj.external_run_ids;

            StringBuilder sb = new StringBuilder()
                    .append("SELECT aj.sample_id, sa.ext_sample_id as external_sample_id, sa.sample_name, sa.sample_desc as sample_description, tmp.run_count, aj.external_run_ids, et.experiment_type, sa.submission_account_id, sa.is_public, r.release_version, st.ANALYSIS_STATUS ")
                    .append("FROM ")
                    .append("ANALYSIS_STATUS st, ")
                    .append("ANALYSIS_JOB aj, ")
                    .append("PIPELINE_RELEASE r, ")
                    .append("SAMPLE sa, ")
                    .append("EXPERIMENT_TYPE et, ")
                    .append("(SELECT aj.sample_id, count(aj.sample_id) as run_count FROM SAMPLE sa, ANALYSIS_JOB aj where sa.sample_id = aj.sample_id AND sa.study_id = ? AND aj.analysis_status_id <> 5 GROUP BY aj.sample_id) tmp ")
                    .append("WHERE aj.experiment_type_id=et.experiment_type_id AND aj.pipeline_id=r.pipeline_id AND sa.sample_id = aj.sample_id AND aj.ANALYSIS_STATUS_ID=st.ANALYSIS_STATUS_ID AND tmp.sample_id = aj.sample_id AND sa.study_id = ? ");
            if (publicOnly) {
                sb.append("AND sa.is_public = 1 ");
            }
            // Allow private and public samples but no suppressed
            else {
                sb.append("AND sa.is_public <> 5 ");
            }
            sb.append("AND st.analysis_status_id <> 5 ");
            sb.append("order by sa.ext_sample_id, aj.external_run_ids");

            final String sql = sb.toString();

            List<QueryRunsForProjectResult> results = jdbcTemplate.query(sql, new Object[]{projectId, projectId}, new BeanPropertyRowMapper<QueryRunsForProjectResult>(QueryRunsForProjectResult.class));
            return results;

        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(1);
        }
    }


    public int countAllPublic() {
        return getAnalysisJobCount(1);
    }

    public int countAllPrivate() {
        return getPrivateAnalysisJobCount(1);
    }

    private int getAnalysisJobCount(int isPublic) {
        String sql = "SELECT count(distinct aj.external_run_ids) FROM ANALYSIS_JOB aj, SAMPLE s WHERE aj.sample_id=s.sample_id and s.is_public = ? and aj.analysis_status_id = ?";
        int analysisStatusId = 3;
        return jdbcTemplate.queryForInt(sql, isPublic, analysisStatusId);
    }

    private int getPrivateAnalysisJobCount(int isPublic) {
        String sql = "SELECT count(distinct aj.external_run_ids) FROM ANALYSIS_JOB aj, SAMPLE s WHERE aj.sample_id=s.sample_id and s.is_public <> ? and aj.analysis_status_id =?";
        int analysisStatusId = 3;
        return jdbcTemplate.queryForInt(sql, isPublic, analysisStatusId);
    }
}