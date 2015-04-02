package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
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