package uk.ac.ebi.interpro.metagenomics.memi.dao.erapro;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Represents the implementation class of {@link uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO}.
 * The Repository annotation makes it a candidate for component-scanning.
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
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.warn("Could not perform database query. It might be that the JDBC connection could not build" +
                    " or is wrong configured. For more info take a look at the stack trace!", e);
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getSubmissionAccountIdByEmail(final String emailAddress) {
        if (emailAddress == null)
            return null;
        try {
            String submissionAccountId = this.jdbcTemplate.queryForObject("select submission_account_id FROM submission_contact sc join submission_account sa using (submission_account_id) WHERE upper(sc.email_address) = UPPER(?)",
                    new Object[]{emailAddress.toUpperCase()}, String.class);
            return submissionAccountId;
        } catch (Exception e) {
            log.warn("Could not perform database query. It might be that the JDBC connection could not build" +
                    " or is wrong configured. For more info take a look at the stack trace!", e);
            return null;
        }
    }

    @Override
    public Submitter getSubmitterBySubmissionAccountId(String submissionAccountId) {
        if (submissionAccountId == null)
            return null;
        Submitter submitter = new Submitter();
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select sc.submission_account_id,sc.email_address,sc.first_name,sc.surname FROM submission_contact sc join submission_account sa on (sc.submission_account_id=sa.submission_account_id) WHERE UPPER(sc.submission_account_id) = UPPER(?)",
                    new String[]{submissionAccountId});

            for (Map row : rows) {
                submitter.setSubmissionAccountId((String) row.get("submission_account_id"));
                submitter.setEmailAddress((String) row.get("email_address"));
                submitter.setFirstName((String) row.get("first_name"));
                submitter.setSurname((String) row.get("surname"));
            }
            return submitter;
        } catch (Exception e) {
            log.warn("Could not perform database query. It might be that the JDBC connection could not build" +
                    " or is wrong configured. For more info take a look at the stack trace!", e);
            return null;
        }
    }


}
