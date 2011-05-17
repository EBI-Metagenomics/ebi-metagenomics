package uk.ac.ebi.interpro.metagenomics.memi.basic.comparators;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.Comparator;

/**
 * Used to order samples by name within ViewSamples page {@link uk.ac.ebi.interpro.metagenomics.memi.controller.ViewSamplesController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ViewSamplesComparator implements Comparator<Sample> {

    /**
     * Compares samples by name. If names are equal then it compares by sample identifiers, which should be usually unique.
     */
    @Override
    public int compare(Sample sample1, Sample sample2) {
        String sampleName1 = sample1.getSampleName();
        String sampleName2 = sample2.getSampleName();
        int sampleNameComp = sampleName1.compareTo(sampleName2);
        long sampleIdDiff = sample1.getId() - sample2.getId();
        int sampleIdComp = 0;
        if (sampleIdDiff > 0) {
            sampleIdComp = 1;
        } else if (sampleIdDiff < 0) {
            sampleIdComp = -1;
        }
        return ((sampleNameComp == 0) ? sampleIdComp : sampleNameComp);
    }
}
