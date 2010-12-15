package uk.ac.ebi.interpro.metagenomics.memi.basic;

import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Represents the data access object for the sample object.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SampleDAOTestImpl implements EmgSampleDAO {

    private List<EmgSample> samples;

    public SampleDAOTestImpl() {
        samples = new ArrayList<EmgSample>();
        samples.add(new EmgSample("1", "sample_1"));
        samples.add(new EmgSample("2", "sample_2"));
        samples.add(new EmgSample("3", "sample_3"));
        samples.add(new EmgSample("4", "sample_4"));
        samples.add(new EmgSample("5", "sample_5"));
        samples.add(new EmgSample("6", "sample_6"));
        samples.add(new EmgSample("7", "sample_7"));
        samples.add(new EmgSample("8", "sample_8"));
        samples.add(new EmgSample("9", "sample_9"));
        samples.add(new EmgSample("10", "sample_10"));
    }


    @Override
    public EmgSample insert(EmgSample newInstance) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<EmgSample> insert(Collection<EmgSample> newInstances) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(EmgSample modifiedInstance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EmgSample read(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EmgSample readDeep(String id, String... deepFields) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(EmgSample persistentObject) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long count() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<EmgSample> retrieveAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int deleteAll() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long getMaximumPrimaryKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}