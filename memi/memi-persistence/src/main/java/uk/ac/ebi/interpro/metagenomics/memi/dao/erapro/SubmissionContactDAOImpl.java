package uk.ac.ebi.interpro.metagenomics.memi.dao.erapro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.dao.apro.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents the implementation class of {@link uk.ac.ebi.interpro.metagenomics.memi.dao.apro.SubmitterDAO}.
 * The Repository annotation makes it a candidate for component-scanning.
 * TODO: Associate with Hibernate (all methods still return mock-up objects)
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Repository
public class SubmissionContactDAOImpl implements SubmissionContactDAO {

    private JdbcTemplate jdbcTemplate;

    private final static Log log = LogFactory.getLog(SubmissionContactDAOImpl.class);

    @Resource
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public SubmissionContactDAOImpl() {
    }

    @Override
    public boolean checkAccountByEmailAddress(String emailAddress) {
        if (emailAddress == null)
            return false;
        try {
            int count = this.jdbcTemplate.queryForObject("select count(1) FROM submission_contact sc join submission_account sa using (submission_account_id) WHERE upper(sc.email_address) = UPPER(?) and sa.role_SRA = 'Y'",
                    new Object[]{emailAddress.toUpperCase()}, Integer.class);
            if (count > 0)
                return true;
            else return false;
        } catch (Exception e) {
            log.warn("Could not perform database query. It might be that the JDBC connection could not build" +
                    " or is wrong configured. For more info take a look at the stack trace!", e);
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
