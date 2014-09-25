package uk.ac.ebi.interpro.metagenomics.memi.dao.temp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents the DAO for {@link EmgSampleAnnotation} object.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SampleAnnotationDAOImpl implements SampleAnnotationDAO {

    private JdbcTemplate jdbcTemplate;

    private final Log log = LogFactory.getLog(SampleAnnotationDAOImpl.class);

    @Autowired(required = true)
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(readOnly = true)
    public Collection<EmgSampleAnnotation> getSampleAnnotations(Long sampleId) {
        log.info("Querying sample annotations from sample: " + sampleId + "...");
        final String oracleSql = "select vn.var_name, sa.var_val_cv, sa.var_val_ucv,sa.units from EMG.sample_ann sa join EMG.variable_names vn on (sa.var_id=vn.var_id) where sa.sample_id=?";
        List<EmgSampleAnnotation> result = new ArrayList<EmgSampleAnnotation>();
        try {
//            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select var_name, var_val_cv, var_val_ucv, units from sample_ann where sample_id=?", new Long[]{sampleId});
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(oracleSql, new Long[]{sampleId});
            for (Map row : rows) {
                String varValCV = (String) row.get("var_val_cv");
                String varValUCV = (String) row.get("var_val_ucv");
                String value = (varValCV != null && varValCV.trim().length() > 0 ? varValCV : varValUCV);
                result.add(new EmgSampleAnnotation((String) row.get("var_name"), value, (String) row.get("units")));
            }
        } catch (Exception e) {
            log.warn("Could not query file IDs!", e);
        }
        return result;
    }
}
