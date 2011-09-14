package uk.ac.ebi.interpro.metagenomics.memi.dao.temp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents DAO for {@link EmgSampleAnnotation} object.
 * User: maxim
 * Date: 14.09.11
 * Time: 18:19
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SampleAnnotationDAOImpl implements SampleAnnotationDAO {

    private JdbcTemplate jdbcTemplate;

    private final Log log = LogFactory.getLog(SampleAnnotationDAOImpl.class);

    @Resource(name = "memiDataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(readOnly = true)
    public Collection<EmgSampleAnnotation> getSampleAnnotations(Long sampleId) {
        log.info("Querying sample annotations for sample: " + sampleId + " from table EMG.SAMPLE_ANN...");
        List<EmgSampleAnnotation> result = new ArrayList<EmgSampleAnnotation>();
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select VAR_NAME,VAR_VAL_CV,VAR_VAL_UCV,UNITS from EMG.SAMPLE_ANN where SAMPLE_ID=?", new Long[]{sampleId});
            for (Map row : rows) {
                String varValCV = (String) row.get("VAR_VAL_CV");
                String varValUCV = (String) row.get("VAR_VAL_UCV");
                String value = (varValCV != null && varValCV.trim().length() > 0 ? varValCV : varValUCV);
                result.add(new EmgSampleAnnotation((String) row.get("VAR_NAME"), value, (String) row.get("UNITS")));
            }
        } catch (Exception e) {
            log.warn("Could not query file IDs!", e);
        }
        return result;
    }
}
