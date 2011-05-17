package uk.ac.ebi.interpro.metagenomics.memi.basic.comparators;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.util.Comparator;
import java.util.Date;

/**
 * Used to order samples by last meta data received date within Home page {@link uk.ac.ebi.interpro.metagenomics.memi.controller.HomePageController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class HomePageSamplesComparator implements Comparator<Sample> {

    /**
     * Compares samples by last meta data received date.
     */
    @Override
    public int compare(Sample sample1, Sample sample2) {
        Date sampleDate1 = sample1.getMetadataReceived();
        Date sampleDate2 = sample2.getMetadataReceived();
        int sampleDateComp = sampleDate2.compareTo(sampleDate1);
        return ((sampleDateComp == 0) ? sample1.getSampleName().compareTo(sample2.getSampleName()) : sampleDateComp);
    }
}
