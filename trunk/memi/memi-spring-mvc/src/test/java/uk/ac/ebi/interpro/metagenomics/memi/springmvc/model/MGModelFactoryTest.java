package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * JUnit test class for the MGModelFactory.
 *
 * @author Maxim Scheremetjew
 */
public class MGModelFactoryTest {

    @Test
    public void testLoadInterProMatchesFromCSV() {
        List<String[]> input = new ArrayList<String[]>();
        input.add(new String[]{"IPR017690", "Outer membrane insertion C-terminal signal, omp85 target", "5538"});

        //Test with one entry
        Assert.assertTrue("Unexpected size of the input list! Expected a list of size 1.", input.size() == 1);
        List<InterProEntry> actual = MGModelFactory.loadInterProMatchesFromCSV(input, true);
        Assert.assertTrue("Result list shouldn't be NULL!", actual != null);
        Assert.assertTrue("Unexpected size of the result list! Expected a list of size 1.", actual.size() == 1);

        //Test with 6 entries
        //Result list showed have only 5 entries as isReturnSizeLimit is activated
        input.add(new String[]{"IPR017690", "Outer membrane insertion C-terminal signal, omp85 target", "5538"});
        input.add(new String[]{"IPR017690", "Outer membrane insertion C-terminal signal, omp85 target", "5538"});
        input.add(new String[]{"IPR017690", "Outer membrane insertion C-terminal signal, omp85 target", "5538"});
        input.add(new String[]{"IPR017690", "Outer membrane insertion C-terminal signal, omp85 target", "5538"});
        input.add(new String[]{"IPR017690", "Outer membrane insertion C-terminal signal, omp85 target", "5538"});
        Assert.assertTrue("Unexpected size of the input list! Expected a list of size 6.", input.size() == 6);
        actual = MGModelFactory.loadInterProMatchesFromCSV(input, true);
        Assert.assertTrue("Result list shouldn't be NULL!", actual != null);
        Assert.assertTrue("Unexpected size of the result list! Expected a list of size 5.", actual.size() == 5);
    }
}