package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;

import javax.sql.DataSource;
import java.util.List;

/**
 * TODO: Description
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
//            String sql = "SELECT * FROM " + schemaName + "." + "log_file_info l, sample sa, study st WHERE st.ext_study_id = ? AND sa.ext_sample_id = ? AND l.sra_run_ids = ?";
            String sql = "SELECT aj.external_run_ids,sa.ext_sample_id,st.ext_study_id,sa.submission_account_id,sa.is_public,aj.sample_id, r.release_version FROM " + schemaName + "." + "analysis_job aj," + schemaName + "." + "pipeline_release r," + schemaName + "." + "sample sa," + schemaName + "." + "study st WHERE aj.pipeline_id=r.pipeline_id AND st.ext_study_id = ? AND sa.ext_sample_id = ? AND aj.external_run_ids = ? AND r.release_version = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{projectId, sampleId, runId, version}, new RunDeepRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    /**
     * List of runs for a study.
     * @param projectId
     * @return
     */
    public List<QueryRunsForProjectResult> retrieveRunsByProjectId(long projectId, boolean publicOnly) {
        try {
            // Example...
            // SELECT st.ext_study_id, aj.sample_id, sa.ext_sample_id, tmp.ct, aj.external_run_ids, aj.experiment_type, sa.submission_account_id, sa.is_public, r.release_version FROM analysis_job aj, pipeline_release r, sample sa, study st, (select aj.sample_id, count(aj.sample_id) as ct from study st, sample sa, analysis_job aj where st.study_id=sa.study_id AND sa.sample_id = aj.sample_id AND st.ext_study_id = 'ERP008551' GROUP BY aj.sample_id) tmp WHERE aj.pipeline_id=r.pipeline_id AND st.study_id=sa.study_id AND sa.sample_id = aj.sample_id AND tmp.sample_id = aj.sample_id AND st.ext_study_id = 'ERP008551' order by sa.ext_sample_id, aj.external_run_ids;
            // SELECT aj.sample_id, sa.ext_sample_id, sa.sample_name, tmp.ct, aj.external_run_ids, aj.experiment_type, sa.submission_account_id, sa.is_public, r.release_version FROM analysis_job aj, pipeline_release r, sample sa, (select aj.sample_id, count(aj.sample_id) as ct from sample sa, analysis_job aj where sa.sample_id = aj.sample_id AND sa.study_id = 434 GROUP BY aj.sample_id) tmp WHERE aj.pipeline_id=r.pipeline_id AND sa.sample_id = aj.sample_id AND tmp.sample_id = aj.sample_id AND sa.study_id = 434 order by sa.ext_sample_id, aj.external_run_ids;

            StringBuilder sb = new StringBuilder()
                    .append("SELECT aj.sample_id, sa.ext_sample_id as external_sample_id, sa.sample_name, tmp.run_count, aj.external_run_ids, aj.experiment_type, sa.submission_account_id, sa.is_public, r.release_version ")
                    .append("FROM ")
                    .append(schemaName).append(".analysis_job aj, ")
                    .append(schemaName).append(".pipeline_release r, ")
                    .append(schemaName).append(".sample sa, ")
                    .append("(SELECT aj.sample_id, count(aj.sample_id) as run_count FROM ").append(schemaName).append(".sample sa, ").append(schemaName).append(".analysis_job aj where sa.sample_id = aj.sample_id AND sa.study_id = ? GROUP BY aj.sample_id) tmp ")
                    .append("WHERE aj.pipeline_id=r.pipeline_id AND sa.sample_id = aj.sample_id AND tmp.sample_id = aj.sample_id AND sa.study_id = ? ");
            if (publicOnly) {
                sb.append("AND sa.is_public = 1 ");
            }
            sb.append("order by sa.ext_sample_id, aj.external_run_ids");

            final String sql = sb.toString();

            List<QueryRunsForProjectResult> results = jdbcTemplate.query(sql, new Object[]{projectId, projectId}, new BeanPropertyRowMapper<QueryRunsForProjectResult>(QueryRunsForProjectResult.class));
            return results;

        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(1);
        }
    }


    public int countAllPublic() {
        return getSampleCount(1);
    }

    public int countAllPrivate() {
        return getSampleCount(0);
    }

    private int getSampleCount(int isPublic) {
        String sql = "SELECT count(*) FROM " + schemaName + "." + "analysis_job aj," + schemaName + "." + "sample s WHERE aj.sample_id=s.sample_id and s.is_public = ?";
        return jdbcTemplate.queryForInt(sql, isPublic);
    }


    public String readLatestPipelineVersionByRunId(final String runId,
                                                   final String analysisStatus) {
        try {
            String sql = "SELECT release_version FROM " + schemaName + "." + "analysis_job aj," + schemaName + "." + "pipeline_release r," + schemaName + "." + "analysis_status s WHERE aj.pipeline_id=r.pipeline_id AND aj.analysis_status_id=s.analysis_status_id AND aj.external_run_ids = ? AND s.analysis_status = ? order by r.release_version desc";
            List<String> results = jdbcTemplate.queryForList(sql, String.class, runId, analysisStatus);
            if (results.size() > 0) {
                return results.get(0);
            }
            return null;
        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(1);
        }
    }
}