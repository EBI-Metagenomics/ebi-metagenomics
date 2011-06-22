package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Repository
public class EmgLogFileInfoDAOImpl implements EmgLogFileInfoDAO {

    private JdbcTemplate jdbcTemplate;

    private final Log log = LogFactory.getLog(EmgLogFileInfoDAOImpl.class);

    @Resource
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Returns a list of file IDs by the specified sample ID.
     */
    public List<String> getFileIdsBySampleId(String sampleId) {
        log.info("Querying file IDs by sample ID: " + sampleId + " from table EMGLogFileInfo...");
        List<String> result = new ArrayList<String>();
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select file_id from " + EmgFile.TABLE_NAME + " where sample_id=?", new String[]{sampleId});
            for (Map row : rows) {
                result.add((String) row.get("FILE_ID"));
            }
        } catch (Exception e) {
            log.warn("Could not query file IDs!", e);
        }
        return result;
    }

    public List<String> getFileNamesBySampleId(String sampleId) {
        log.info("Querying file IDs by sample ID: " + sampleId + " from table EMGLogFileInfo...");
        List<String> result = new ArrayList<String>();
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select file_name from " + EmgFile.TABLE_NAME + " where sample_id=?", new String[]{sampleId});
            for (Map row : rows) {
                result.add((String) row.get("FILE_NAME"));
            }
        } catch (Exception e) {
            log.warn("Could not query file names!", e);
        }
        return result;
    }

    public List<EmgFile> getFilesBySampleId(String sampleId) {
        log.info("Querying file IDs by sample ID: " + sampleId + " from table EMGLogFileInfo...");
        List<EmgFile> result = new ArrayList<EmgFile>();
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select file_id, file_name from " + EmgFile.TABLE_NAME + " where sample_id=?", new String[]{sampleId});
            for (Map row : rows) {
                String fileName = (String) row.get("FILE_NAME");
                String fileID = (String) row.get("FILE_ID");
                result.add(new EmgFile(fileID, fileName));
            }
        } catch (Exception e) {
            log.warn("Could not query file names!", e);
        }
        return result;
    }

    public List<String> getSraIDs(String sampleId) {
        log.info("Querying SRA IDs by sample ID: " + sampleId + " from table EMGLogFileInfo...");
        List<String> result = new ArrayList<String>();
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select sra_run_ids from " + EmgFile.TABLE_NAME + " where sample_id=?", new String[]{sampleId});
            for (Map row : rows) {
                String rowResult = (String) row.get("SRA_RUN_IDS");
                String[] chunks = rowResult.split(";");
                for (String chunk : chunks) {
                    result.add(chunk);
                }
            }
        } catch (Exception e) {
            log.warn("Could not query file IDs!", e);
        }
        return result;
    }
}