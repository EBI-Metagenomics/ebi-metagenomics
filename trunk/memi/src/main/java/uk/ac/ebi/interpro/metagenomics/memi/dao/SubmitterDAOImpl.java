package uk.ac.ebi.interpro.metagenomics.memi.dao;

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

/**
 * Represents the implementation class of {@link uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO}.
 * The Repository annotation makes it a candidate for component-scanning.
 * TODO: Associate with Hibernate (all methods still return mock-up objects)
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Repository
public class SubmitterDAOImpl implements SubmitterDAO {

    private JdbcTemplate jdbcTemplate;

    private Log log = LogFactory.getLog(SubmitterDAOImpl.class);

    @Resource(name="adevDatasource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public SubmitterDAOImpl() {
    }

    @Override
    // TODO: Implement
    public Submitter getSubmitterById(long submitterId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    // TODO: Implement
    public void deleteSubmitter(Submitter submitter) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Submitter getSubmitterByEmailAddress(String emailAddress) {
        Submitter submitter = null;
        try {
            submitter = this.jdbcTemplate.queryForObject("SELECT submitterid, first_name, surname, password, email_address FROM submitter WHERE ACTIVE='Y' AND email_address=?",
                    new Object[]{emailAddress},
                    new RowMapper<Submitter>() {
                        public Submitter mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Submitter submitter = new Submitter();
                            submitter.setSubmitterId(Integer.parseInt(rs.getString("submitterid")));
                            submitter.setFirstName(rs.getString("first_name"));
                            submitter.setSurname(rs.getString("surname"));
                            submitter.setEmailAddress(rs.getString("email_address"));
                            submitter.setPassword(rs.getString("password"));
                            return submitter;
                        }
                    });

        }
        catch (Exception e) {
            log.warn("Could not perform database query. It might be that the JDBC connection could not build" +
                    " or is wrong configured. For more info take a look at the stack trace!", e);
        }
        return submitter;
    }

    @Override
    // TODO: Implement
    public List<Submitter> getSubmitters() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
