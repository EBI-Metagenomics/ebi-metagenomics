package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;

import java.util.List;

/**
 * Represents a data access object interface for the EMG_LOG_FILE_INFO table of the EMG scheme.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface EmgLogFileInfoDAO {

    public List<EmgFile> getFilesBySampleId(long sampleId);

    public List<String> getSraIDs(long sampleId);
}
