package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.springframework.jdbc.core.RowMapper;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class RunDeepRowMapper implements RowMapper<Run> {

    public Run mapRow(ResultSet rs, int rowNum) throws SQLException {
        Run run = new Run();
        run.setExternalRunId(rs.getString("EXTERNAL_RUN_IDS"));
        run.setExternalSampleId(rs.getString("EXT_SAMPLE_ID"));
        run.setExternalProjectId(rs.getString("EXT_STUDY_ID"));
        run.setSubmissionAccountId(rs.getString("SUBMISSION_ACCOUNT_ID"));
        run.setPublic(rs.getBoolean("IS_PUBLIC"));
        run.setSampleId(rs.getLong("SAMPLE_ID"));
        run.setReleaseVersion(rs.getString("RELEASE_VERSION"));
        return run;
    }
}