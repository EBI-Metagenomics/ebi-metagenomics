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
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.RunStatisticsVO;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.ProjectSampleRunMappingVO;

import javax.sql.DataSource;
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


    //    TODO: Adjust join query between Study and Samples
    public Run readByRunIdDeep(Long studyId, String sampleId, String runId, String version) {
        try {
            String sql = "SELECT aj.external_run_ids,sa.ext_sample_id,st.ext_study_id,st.submission_account_id,aj.RUN_STATUS_ID,aj.sample_id, r.release_version FROM emg.STUDY st, emg.STUDY_SAMPLE stsa, emg.SAMPLE sa, emg.ANALYSIS_JOB aj, PIPELINE_RELEASE r where st.STUDY_ID=stsa.STUDY_ID and stsa.SAMPLE_ID=sa.SAMPLE_ID and sa.SAMPLE_ID=aj.SAMPLE_ID and aj.study_id = ? and stsa.study_id = ? and aj.pipeline_id=r.pipeline_id AND sa.ext_sample_id = ? AND aj.external_run_ids = ? AND r.release_version = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{studyId, studyId, sampleId, runId, version}, new RunDeepRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    //TODO: Migrate to Hibernate SQL (AnalysisJobDAO)
    public Map<String, Long> retrieveRunCountsGroupedByExperimentType(final int analysisStatusId) {
        try {
            Map<String, Long> result = new HashMap<String, Long>();
            StringBuilder sql = new StringBuilder("select exp.experiment_type, count(distinct job.external_run_ids) as count ");
            sql.append("from ANALYSIS_JOB job,");
            sql.append("EXPERIMENT_TYPE exp,");
            sql.append("ANALYSIS_STATUS status,");
            sql.append("PIPELINE_RELEASE pipeline,");
            sql.append("SAMPLE sample,");
            sql.append("STUDY project ");
            sql.append("where  job.EXPERIMENT_TYPE_ID = exp.EXPERIMENT_TYPE_ID ");
            sql.append("and job.ANALYSIS_STATUS_ID = status.ANALYSIS_STATUS_ID ");
            sql.append("and job.PIPELINE_ID = pipeline.PIPELINE_ID ");
            sql.append("and job.STUDY_ID = project.STUDY_ID ");
            sql.append("and job.ANALYSIS_STATUS_ID = ? ");
            sql.append("AND job.RUN_STATUS_ID = 4 ");
            sql.append("and project.IS_PUBLIC = 1 ");
            sql.append("and job.SAMPLE_ID = sample.SAMPLE_ID ");
            sql.append("and sample.IS_PUBLIC = 1 ");
            sql.append("group by exp.experiment_type");
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString(), new Object[]{analysisStatusId});
            for (Map<String, Object> row : rows) {
                result.put((String) (row.get("EXPERIMENT_TYPE")), (Long) row.get("COUNT"));
            }
            return result;
        } catch (DataAccessException exception) {
            throw exception;
        }
    }

    public RunStatisticsVO retrieveStatistics() {
        try {
            RunStatisticsVO stats = new RunStatisticsVO();
            String sql = "select j.RUN_STATUS_ID as is_public, count(distinct j.EXTERNAL_RUN_IDS) as num_of_runs from ANALYSIS_JOB j where j.RUN_STATUS_ID in (2,4) and j.ANALYSIS_STATUS_ID = 3 group by j.RUN_STATUS_ID";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            for (Map<String, Object> row : rows) {
                int isPublic = (Integer) row.get("IS_PUBLIC");
                long numOfRuns = (Long) row.get("num_of_runs");
                if (isPublic == 4) {
                    stats.setNumOfPublicRuns(numOfRuns);
                } else {
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
    //    TODO: Adjust join query between Study and Samples
    public List<ProjectSampleRunMappingVO> retrieveListOfRunAccessionsByProjectId(long projectId) {
        try {
            StringBuilder sb = new StringBuilder()
                    .append("SELECT st.ext_study_id as study_id, sa.ext_sample_id as sample_id, aj.external_run_ids as run_id ")
                    .append("FROM ")
                    .append("ANALYSIS_JOB aj, ")
                    .append("SAMPLE sa, ")
                    .append("STUDY_SAMPLE stsa, ")
                    .append("STUDY st ")
                    .append("WHERE st.study_id = stsa.study_id AND stsa.sample_id = sa.sample_id and sa.sample_id = aj.sample_id AND st.study_id = ? and aj.study_id = ? group by aj.external_run_ids ");

            final String sql = sb.toString();
            List<ProjectSampleRunMappingVO> results = jdbcTemplate.query(sql, new Object[]{projectId, projectId}, new BeanPropertyRowMapper<ProjectSampleRunMappingVO>(ProjectSampleRunMappingVO.class));
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
//    TODO: Adjust join query between Study and Samples
    public List<QueryRunsForProjectResult> retrieveRunsByProjectId(long projectId, boolean publicOnly) {
        try {
            // Example...
            // SELECT st.ext_study_id, aj.sample_id, sa.ext_sample_id, tmp.ct, aj.external_run_ids, aj.experiment_type, sa.submission_account_id, sa.is_public, r.release_version FROM ANALYSIS_JOB aj, PIPELINE_RELEASE r, sample sa, study st, (select aj.sample_id, count(aj.sample_id) as ct from study st, sample sa, ANALYSIS_JOB aj where st.study_id=sa.study_id AND sa.sample_id = aj.sample_id AND st.ext_study_id = 'ERP008551' GROUP BY aj.sample_id) tmp WHERE aj.pipeline_id=r.pipeline_id AND st.study_id=sa.study_id AND sa.sample_id = aj.sample_id AND tmp.sample_id = aj.sample_id AND st.ext_study_id = 'ERP008551' order by sa.ext_sample_id, aj.external_run_ids;
            // SELECT aj.sample_id, sa.ext_sample_id, sa.sample_name, tmp.ct, aj.external_run_ids, aj.experiment_type, sa.submission_account_id, sa.is_public, r.release_version FROM ANALYSIS_JOB aj, PIPELINE_RELEASE r, sample sa, (select aj.sample_id, count(aj.sample_id) as ct from sample sa, ANALYSIS_JOB aj where sa.sample_id = aj.sample_id AND sa.study_id = 434 GROUP BY aj.sample_id) tmp WHERE aj.pipeline_id=r.pipeline_id AND sa.sample_id = aj.sample_id AND tmp.sample_id = aj.sample_id AND sa.study_id = 434 order by sa.ext_sample_id, aj.external_run_ids;

            StringBuilder sb = new StringBuilder()
                    .append("SELECT aj.sample_id, sa.ext_sample_id as external_sample_id, sa.sample_name, sa.sample_desc as sample_description, tmp.run_count, aj.external_run_ids, aj.secondary_accession, et.experiment_type, sa.submission_account_id, sa.is_public, r.release_version, st.ANALYSIS_STATUS, an.VAR_VAL_UCV as instrumentModel ")
                    .append("FROM ")
                    .append("ANALYSIS_STATUS st, ")
                    .append("ANALYSIS_JOB aj, ")
                    .append("PIPELINE_RELEASE r, ")
                    .append("STUDY_SAMPLE stsa, ")
                    .append("SAMPLE sa, ")
                    .append("SAMPLE_ANN an, ")
                    .append("EXPERIMENT_TYPE et, ")
                    .append("(SELECT aj.sample_id, count(aj.sample_id) as run_count FROM STUDY_SAMPLE stsa, SAMPLE sa, ANALYSIS_JOB aj where stsa.SAMPLE_ID=sa.SAMPLE_ID AND sa.sample_id = aj.sample_id \n" +
                            "AND stsa.study_id = ? AND aj.study_id = ? AND aj.analysis_status_id <> 5 GROUP BY aj.sample_id) tmp ")
                    .append("WHERE aj.experiment_type_id=et.experiment_type_id AND aj.pipeline_id=r.pipeline_id AND sa.sample_id = aj.sample_id AND stsa.sample_id = sa.sample_id AND sa.sample_id = an.sample_id AND aj.ANALYSIS_STATUS_ID=st.ANALYSIS_STATUS_ID AND an.VAR_ID = 352 AND tmp.sample_id = aj.sample_id AND stsa.study_id = ? AND aj.study_id = ? ");
            if (publicOnly) {
                sb.append("AND aj.run_status_id = 4 ");
            }
            // Allow private and public runs but no suppressed
            else {
                sb.append("AND aj.run_status_id in (2, 4) ");
            }
            sb.append("AND st.analysis_status_id <> 5 ");
            sb.append("order by sa.ext_sample_id, aj.external_run_ids");

            final String sql = sb.toString();
//            TODO: Change level to debug
            log.info("Running the following SQL query now:\n" + sql);

            List<QueryRunsForProjectResult> results = jdbcTemplate.query(sql, new Object[]{projectId, projectId, projectId, projectId}, new BeanPropertyRowMapper<QueryRunsForProjectResult>(QueryRunsForProjectResult.class));
            return results;

        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(1);
        }
    }
}