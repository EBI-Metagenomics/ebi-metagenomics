package uk.ac.ebi.interpro.metagenomics.memi.dao.erapro;

import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import java.util.List;

/**
 * Represents the data access object interface for {@link uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface SubmissionContactDAO {

    boolean checkAccountByEmailAddress(String emailAddress);

    String getSubmissionAccountIdByEmail(String emailAddress);

    Submitter getSubmitterBySubmissionAccountId(String submissionAccountId);

    Submitter getSubmitterByEmail(String emailAddress);
}