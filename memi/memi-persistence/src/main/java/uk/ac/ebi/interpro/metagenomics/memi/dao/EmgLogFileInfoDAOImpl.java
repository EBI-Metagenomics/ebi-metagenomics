package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.apache.commons.collections.iterators.ArrayListIterator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
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
    @Override
    public List<String> getFileIdsBySampleId(String sampleId) {
        log.info("Querying file IDs by sample ID: " + sampleId + " from table EMGLogFileInfo...");
        List<String> result = new ArrayList<String>();
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select file_id from emg_log_file_info where sample_id=?", new String[]{sampleId});
            for (Map row : rows) {
                result.add((String) row.get("FILE_ID"));
            }
        } catch (Exception e) {
            log.warn("Could not query file IDs!", e);
        }
        return result;
    }

    @Override
    public List<String> getFileNamesBySampleId(String sampleId) {
        log.info("Querying file IDs by sample ID: " + sampleId + " from table EMGLogFileInfo...");
        List<String> result = new ArrayList<String>();
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select file_name from emg_log_file_info where sample_id=?", new String[]{sampleId});
            for (Map row : rows) {
                result.add((String) row.get("FILE_NAME"));
            }
        } catch (Exception e) {
            log.warn("Could not query file names!", e);
        }
        return result;
    }
}