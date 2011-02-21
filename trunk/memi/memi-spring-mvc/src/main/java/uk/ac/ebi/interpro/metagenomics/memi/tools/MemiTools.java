package uk.ac.ebi.interpro.metagenomics.memi.tools;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.util.List;

/**
 * Simple tool class which provides general used methods.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class MemiTools {

    public static String[] getSampleIds(List<Sample> samples) {
        String[] result = new String[samples.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = samples.get(i).getSampleId();
        }
        return result;
    }
}
