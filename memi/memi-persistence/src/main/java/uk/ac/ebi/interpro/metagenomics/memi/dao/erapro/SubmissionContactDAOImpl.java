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

    public boolean checkAccountByEmailAddress(String emailAddress) {
        if (emailAddress == null)
            return false;
        try {
            int count = this.jdbcTemplate.queryForObject("select count(1) FROM submission_contact sc join submission_account sa using (submission_account_id) WHERE upper(sc.email_address) = UPPER(?)",
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

    public Submitter getSubmitterBySubmissionAccountId(final String submissionAccountId) {
        if (submissionAccountId == null)
            return null;
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select sa.submission_account_id, sa.role_metagenome_analysis FROM submission_account sa WHERE UPPER(sa.submission_account_id) = UPPER(?)",
                    new String[]{submissionAccountId});
            if (rows.size() > 1) {
                log.warn("Found more then one submission accounts for account id: " + submissionAccountId);
            }
            for (Map row : rows) {
                Submitter submitter = new Submitter();
                submitter.setSubmissionAccountId((String) row.get("submission_account_id"));
                submitter.setConsentGiven(((String) row.get("role_metagenome_analysis")).equalsIgnoreCase("Y") ? true : false);
                return submitter;
            }
        } catch (Exception e) {
            log.warn("Could not perform database query. It might be that the JDBC connection could not build" +
                    " or is wrong configured. For more info take a look at the stack trace!", e);
        }
        return null;
    }

    public Submitter getSubmitterBySubmissionAccountIdAndEmail(final String submissionAccountId, final String email) {
        if (submissionAccountId == null || email == null)
            return null;
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select sa.submission_account_id, sa.role_metagenome_analysis, c.first_name, c.surname, c.email_address, c.main_contact FROM era.submission_account sa, ERA.submission_contact c WHERE c.submission_account_id=sa.submission_account_id and UPPER(sa.submission_account_id) = UPPER(?) and UPPER(c.email_address) = UPPER(?)",
                    new String[]{submissionAccountId, email});
            if (rows.size() > 1) {
                log.warn("Found more then one submission accounts for account id: " + submissionAccountId);
            }
            for (Map row : rows) {
                Submitter submitter = new Submitter();
                submitter.setSubmissionAccountId((String) row.get("submission_account_id"));
                submitter.setConsentGiven(((String) row.get("role_metagenome_analysis")).equalsIgnoreCase("Y") ? true : false);
                submitter.setFirstName((String) row.get("first_name"));
                submitter.setSurname((String) row.get("surname"));
                submitter.setEmailAddress((String) row.get("email_address"));
                submitter.setMainContact(((String) row.get("main_contact")).equalsIgnoreCase("Y") ? true : false);
                return submitter;
            }
        } catch (Exception e) {
            log.warn("Could not perform database query. It might be that the JDBC connection could not build" +
                    " or is wrong configured. For more info take a look at the stack trace!", e);
        }
        return null;
    }

    public Submitter getSubmitterByEmail(final String emailAddress) {
        if (emailAddress == null)
            return null;
        Submitter submitter = new Submitter();
        try {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList("select sc.submission_account_id, sc.email_address, sc.first_name, sc.surname, sa.role_metagenome_analysis FROM submission_contact sc join submission_account sa on (sc.submission_account_id=sa.submission_account_id) WHERE UPPER(sc.email_address) = UPPER(?)",
                    new String[]{emailAddress});

            for (Map row : rows) {
                submitter.setSubmissionAccountId((String) row.get("submission_account_id"));
                submitter.setEmailAddress((String) row.get("email_address"));
                submitter.setFirstName((String) row.get("first_name"));
                submitter.setSurname((String) row.get("surname"));
                submitter.setConsentGiven(((String) row.get("role_metagenome_analysis")).equalsIgnoreCase("Y") ? true : false);
            }
            return submitter;
        } catch (Exception e) {
            log.warn("Could not perform database query. It might be that the JDBC connection could not build" +
                    " or is wrong configured. For more info take a look at the stack trace!", e);
            return null;
        }
    }
}