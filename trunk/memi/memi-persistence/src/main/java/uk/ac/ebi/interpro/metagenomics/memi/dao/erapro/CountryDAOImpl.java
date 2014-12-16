package uk.ac.ebi.interpro.metagenomics.memi.dao.erapro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Country;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Represents the implementation class of {@link CountryDAO}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Repository
public class CountryDAOImpl implements CountryDAO {

    private JdbcTemplate jdbcTemplate;

    private final static Log log = LogFactory.getLog(CountryDAOImpl.class);

    @Resource
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public CountryDAOImpl() {
    }

    @Override
    public Collection<Country> getAllCountries() {
        try {
            List<Country> countryList = jdbcTemplate.query(
                    "select * from cv_country",
                    new Object[]{},
                    new RowMapper() {
                        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return new Country(rs.getString("country"));
                        }
                    }
            );
            return countryList;

        } catch (Exception e) {
            log.warn("Could not perform database query. It might be that the JDBC connection could not build" +
                    " or is wrong configured. For more info take a look at the stack trace!", e);
        }
        return null;
    }
}