package uk.ac.ebi.interpro.metagenomics.memi.tools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgLogFileInfoDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple tool class which provides general used methods.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class MemiTools {
    private final static Log log = LogFactory.getLog(MemiTools.class);

    public static String[] getSampleIds(List<Sample> samples) {
        log.info("Getting sample IDs from the specified sample list...");
        String[] result = new String[samples.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = samples.get(i).getSampleId();
        }
        return result;
    }

    public static String createFileName(final String fileName) {
        log.info("Creating file name in the following format - FILENAME_yyyyMMdd.csv");
        final String suffix = ".csv";
        Format dateFormatter = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        final String infix = dateFormatter.format(date);
        return fileName + infix + suffix;
    }

    public static List<String> getArchivedSeqs(EmgLogFileInfoDAO fileInfoDAO, Sample sample) {
        List<String> result = new ArrayList<String>();
        if (fileInfoDAO != null && sample != null) {
            result = fileInfoDAO.getSraIDs(sample.getSampleId());
        }
        return result;
    }
}
