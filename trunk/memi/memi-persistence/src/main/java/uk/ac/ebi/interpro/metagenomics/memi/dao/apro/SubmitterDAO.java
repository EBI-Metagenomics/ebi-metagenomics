package uk.ac.ebi.interpro.metagenomics.memi.dao.apro;

import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import java.util.List;

/**
 * Represents the data access object interface for {@link uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface SubmitterDAO {

    public Submitter getSubmitterById(long submitterId);

    public void deleteSubmitter(Submitter submitter);

//    TODO: Confirm if email addresses are unique (at the moment I do not think so)
//    Otherwise a list of submitters should be return

    public Submitter getSubmitterByEmailAddress(String emailAddress);

    public List<Submitter> getSubmitters();

    public boolean isDatabaseAlive();

    public String getMasterPasswordByEmailAddress(String emailAddress);
}
