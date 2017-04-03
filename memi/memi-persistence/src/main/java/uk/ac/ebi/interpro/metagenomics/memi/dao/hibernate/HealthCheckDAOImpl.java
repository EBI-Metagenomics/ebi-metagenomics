package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAOImpl;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDeepRowMapper;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineRelease;

import javax.sql.DataSource;
import java.util.List;

/**
 * Represents the implementation class of {@link HealthCheckDAO}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 3.0.2-SNAPSHOT
 */
@Repository
public class HealthCheckDAOImpl implements HealthCheckDAO {

    private JdbcTemplate jdbcTemplate;


    @Autowired(required = true)
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public boolean checkDatabaseConnectionAlive() {
        try {
            String sql = "SELECT VERSION()";
            List result = jdbcTemplate.queryForList(sql);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}