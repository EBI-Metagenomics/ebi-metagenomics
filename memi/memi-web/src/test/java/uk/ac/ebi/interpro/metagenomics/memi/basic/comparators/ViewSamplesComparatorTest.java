package uk.ac.ebi.interpro.metagenomics.memi.basic.comparators;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.ViewSamplesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.util.*;

/**
 * Test for {@link uk.ac.ebi.interpro.metagenomics.memi.core.comparators.ViewSamplesComparator}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ViewSamplesComparatorTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ViewSamplesComparatorTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ViewSamplesComparatorTest.class);
    }

    /**
     * Tests the comparator in combination with the sorted set.
     */
    @org.junit.Test
    public void testCompareSortedSet() {
        Sample sample1 = new Sample();
        sample1.setSampleName("b");
        sample1.setId(1);
        //
        Sample sample2 = new Sample();
        sample2.setSampleName("A");
        sample2.setId(2);
        //
        Sample sample3 = new Sample();
        sample3.setSampleName("C");
        sample3.setId(3);
        //
        Sample sample4 = new Sample();
        sample4.setSampleName("C");
        sample4.setId(4);
        //
        SortedSet<Sample> samples = new TreeSet<Sample>(new ViewSamplesComparator());
        samples.add(sample1);
        samples.add(sample2);
        samples.add(sample3);
        samples.add(sample4);
        //
        Sample[] sampleArray = samples.toArray(new Sample[4]);

        assertEquals("Unexpected sample set size!", 4, samples.size());
        assertEquals("Unexpected sample array length!", 4, sampleArray.length);
        assertEquals("Sample set is sorted in a wrong order!", sample2, sampleArray[0]);
        assertEquals("Sample set is sorted in a wrong order!", sample1, sampleArray[1]);
        assertEquals("Sample set is sorted in a wrong order!", sample3, sampleArray[2]);
        assertEquals("Sample set is sorted in a wrong order!", sample4, sampleArray[3]);
    }

    /**
     * Tests the comparator in combination with collections.sort().
     */
    @org.junit.Test
    public void testCompareCollectionsSort() {
        Sample sample1 = new Sample();
        sample1.setSampleName("B");
        sample1.setId(1);
        //
        Sample sample2 = new Sample();
        sample2.setSampleName("a");
        sample2.setId(2);
        //
        Sample sample3 = new Sample();
        sample3.setSampleName("c");
        sample3.setId(3);
        //
        Sample sample4 = new Sample();
        sample4.setSampleName("c");
        sample4.setId(4);
        //
        List<Sample> samples = new ArrayList<Sample>();
        samples.add(sample1);
        samples.add(sample2);
        samples.add(sample3);
        samples.add(sample4);
        //
        assertEquals("Unexpected sample set size!", 4, samples.size());
        assertEquals("Sample set is in a wrong order!", sample1, samples.get(0));
        assertEquals("Sample set is in a wrong order!", sample2, samples.get(1));
        assertEquals("Sample set is in a wrong order!", sample3, samples.get(2));
        assertEquals("Sample set is in a wrong order!", sample4, samples.get(3));
        //
        Collections.sort(samples, new ViewSamplesComparator());
        assertEquals("Unexpected sample set size!", 4, samples.size());
        assertEquals("Sample set is in a wrong order!", sample2, samples.get(0));
        assertEquals("Sample set is in a wrong order!", sample1, samples.get(1));
        assertEquals("Sample set is in a wrong order!", sample3, samples.get(2));
        assertEquals("Sample set is in a wrong order!", sample4, samples.get(3));
    }
}