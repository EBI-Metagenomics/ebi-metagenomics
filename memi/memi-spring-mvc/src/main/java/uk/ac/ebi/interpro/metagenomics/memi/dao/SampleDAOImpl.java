package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.model.Sample;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents the data access object for the sample object.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SampleDAOImpl implements SampleDAO {

    private List<Sample> samples;

    public SampleDAOImpl() {
        samples = new ArrayList<Sample>();
        samples.add(new Sample(1, "sample_1"));
        samples.add(new Sample(2, "sample_2"));
        samples.add(new Sample(3, "sample_3"));
        samples.add(new Sample(4, "sample_4"));
        samples.add(new Sample(5, "sample_5"));
        samples.add(new Sample(6, "sample_6"));
        samples.add(new Sample(7, "sample_7"));
        samples.add(new Sample(8, "sample_8"));
        samples.add(new Sample(9, "sample_9"));
        samples.add(new Sample(10, "sample_10"));
    }

    @Override
    public List<Sample> getAllSamples() {
        return samples;
    }

    @Override
    public Sample getSampleById(long id) {
        for (Sample sample : samples) {
            if (sample.getSampleId() == id) {
                return sample;
            }
        }
        return null;
    }
}