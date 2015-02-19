package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;

import javax.sql.DataSource;

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

    /**
     * Returns a list of file IDs by the specified sample ID.
     */
//    public List<String> getFileIdsBySampleId(long sampleId) {
//        log.info("Querying file IDs by sample ID: " + sampleId + " from table EMGLogFileInfo...");
//        List<String> result = new ArrayList<String>();
//        try {
//            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select file_id from " + tableNamePrefix + "." + EmgFile.TABLE_NAME + "where" + EmgFile.SAMPLE_ID + " = ? ", new Long[]{sampleId});
//            for (Map row : rows) {
//                result.add((String) row.get("FILE_ID"));
//            }
//        } catch (Exception e) {
//            log.warn("Could not query file IDs!", e);
//        }
//        return result;
//    }

//    public List<String> getFileNamesBySampleId(long sampleId) {
//        log.info("Querying file IDs by sample ID: " + sampleId + " from table EMGLogFileInfo...");
//        List<String> result = new ArrayList<String>();
//        try {
//            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select file_name from " + EmgFile.TABLE_NAME + " where " + EmgFile.SAMPLE_ID + "=?", new Long[]{sampleId});
//            for (Map row : rows) {
//                result.add((String) row.get("FILE_NAME"));
//            }
//        } catch (Exception e) {
//            log.warn("Could not query file names!", e);
//        }
//        return result;
//    }

//    public List<EmgFile> getFilesBySampleId(long sampleId) {
//        log.info("Querying file IDs by sample ID: " + sampleId + " from table EMGLogFileInfo...");
//        List<EmgFile> result = new ArrayList<EmgFile>();
//        try {
//            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select file_id, file_name from " + EmgFile.TABLE_NAME + " where " + EmgFile.SAMPLE_ID + "=?", new Long[]{sampleId});
//            for (Map row : rows) {
//                String fileName = (String) row.get(EmgFile.FILE_NAME);
//                String fileID = (String) row.get(EmgFile.FILE_ID);
//                result.add(new EmgFile(fileID, fileName));
//            }
//        } catch (Exception e) {
//            log.warn("Could not query file names!", e);
//        }
//        return result;
//    }

//    public List<String> getSraIDs(long sampleId) {
//        log.info("Querying SRA IDs by sample ID: " + sampleId + " from table EMGLogFileInfo...");
//        List<String> result = new ArrayList<String>();
//        try {
////            TODO: The following 'if else' case is a quick and dirty solution to solve the differentiation issue between genomic and transcriptomic analysis
//            List<Map<String, Object>> rows = null;
//
//            if (sampleId == 367) {
//                rows = this.jdbcTemplate.queryForList("select sra_run_ids from " + EmgFile.TABLE_NAME + " where " + EmgFile.SAMPLE_ID + "=?", new Long[]{sampleId});
//            } else {
//                rows = this.jdbcTemplate.queryForList("select sra_run_ids from " + EmgFile.TABLE_NAME + " where " + EmgFile.SAMPLE_ID + "=? and data_type_gta=?", new Object[]{new Long(sampleId), "genomic"});
//            }
//            for (Map row : rows) {
//                String rowResult = (String) row.get("SRA_RUN_IDS");
//                result.add(rowResult);
////                String[] chunks = rowResult.split(";");
////                for (String chunk : chunks) {
////                    result.add(chunk.trim());
////                }
//            }
//        } catch (Exception
//                e) {
//            log.warn("Could not query file IDs!", e);
//        }
//        return result;
//    }
    public Run readByRunId(String runId) {
//        String sql2 = "SELECT * FROM CUSTOMER WHERE CUST_ID = ?";
        String sql = "SELECT * FROM " + schemaName + "." + "log_file_info WHERE sra_run_ids = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{runId}, new RunRowMapper());
    }

    @Override
    public Run readByRunIdDeep(String runId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Run readByRunIdDeep(String projectId, String sampleId, String runId) {
        try {
            String sql = "SELECT * FROM " + schemaName + "." + "log_file_info l, sample sa, study st WHERE st.ext_study_id = ? AND sa.ext_sample_id = ? AND l.sra_run_ids = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{projectId, sampleId, runId}, new RunDeepRowMapper());
        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(1);
        }
    }
}