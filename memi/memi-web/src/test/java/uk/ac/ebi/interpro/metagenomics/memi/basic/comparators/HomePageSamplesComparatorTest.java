package uk.ac.ebi.interpro.metagenomics.memi.basic.comparators;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import uk.ac.ebi.interpro.metagenomics.memi.core.comparators.HomePageSamplesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.util.*;

/**
 * Test for {@link uk.ac.ebi.interpro.metagenomics.memi.core.comparators.HomePageSamplesComparator}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class HomePageSamplesComparatorTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HomePageSamplesComparatorTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(HomePageSamplesComparatorTest.class);
    }

    /**
     * Tests the comparator in combination with the sorted set.
     */
    @org.junit.Test
    public void testCompareSortedSet() {
        Calendar calendar = null;

        Sample sample1 = new Sample();
        sample1.setSampleName("a");
        calendar = new GregorianCalendar(2011, 01, 02);
        sample1.setLastMetadataReceived(calendar.getTime());
        //
        Sample sample2 = new Sample();
        sample2.setSampleName("b");
        calendar = new GregorianCalendar(2011, 01, 04);
        sample2.setLastMetadataReceived(calendar.getTime());
        //
        Sample sample3 = new Sample();
        sample3.setSampleName("c");
        calendar = new GregorianCalendar(2011, 01, 01);
        sample3.setLastMetadataReceived(calendar.getTime());
        //
        Sample sample4 = new Sample();
        sample4.setSampleName("d");
        calendar = new GregorianCalendar(2011, 01, 01);
        sample4.setLastMetadataReceived(calendar.getTime());

        SortedSet<Sample> samples = new TreeSet<Sample>(new HomePageSamplesComparator());
        samples.add(sample1);
        samples.add(sample2);
        samples.add(sample3);
        samples.add(sample4);

        Sample[] sampleArray = samples.toArray(new Sample[4]);

        assertEquals("Unexpected sample set size!", 4, samples.size());
        assertEquals("Unexpected sample array length!", 4, sampleArray.length);
        assertEquals("Sample set is sorted in a wrong order!", sample2, sampleArray[0]);
        assertEquals("Sample set is sorted in a wrong order!", sample1, sampleArray[1]);
        assertEquals("Sample set is sorted in a wrong order!", sample3, sampleArray[2]);
        assertEquals("Sample set is sorted in a wrong order!", sample4, sampleArray[3]);
    }

    /**
     * Tests the comparator in combination with Collections.sort().
     */
    @org.junit.Test
    public void testCompareCollectionsSort() {
        Calendar calendar = null;

        Sample sample1 = new Sample();
        sample1.setSampleName("a");
        calendar = new GregorianCalendar(2011, 01, 02);
        sample1.setLastMetadataReceived(calendar.getTime());
        //
        Sample sample2 = new Sample();
        sample2.setSampleName("b");
        calendar = new GregorianCalendar(2011, 01, 04);
        sample2.setLastMetadataReceived(calendar.getTime());
        //
        Sample sample3 = new Sample();
        sample3.setSampleName("c");
        calendar = new GregorianCalendar(2011, 01, 01);
        sample3.setLastMetadataReceived(calendar.getTime());
        //
        Sample sample4 = new Sample();
        sample4.setSampleName("d");
        calendar = new GregorianCalendar(2011, 01, 03);
        sample4.setLastMetadataReceived(calendar.getTime());
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
        Collections.sort(samples, new HomePageSamplesComparator());
        assertEquals("Unexpected sample set size!", 4, samples.size());
        assertEquals("Sample set is in a wrong order!", sample2, samples.get(0));
        assertEquals("Sample set is in a wrong order!", sample4, samples.get(1));
        assertEquals("Sample set is in a wrong order!", sample1, samples.get(2));
        assertEquals("Sample set is in a wrong order!", sample3, samples.get(3));
    }

    @org.junit.Test
    public void testExperimentTypeSorting() {
        Map<String, Integer> result = new TreeMap<String, Integer>();
        assertEquals("Unexpected map size!", 0, result.size());

        result.put("metagenomes", 33);
        result.put("assemblies", 11);
        result.put("amplicons", 44);
        result.put("metatranscriptomes", 22);
        assertEquals("Unexpected map size!", 4, result.size());
        Set<String> keySet = result.keySet();
        Object[] array = keySet.toArray();
        assertEquals(4, array.length);
        assertEquals("amplicons", array[0]);
        assertEquals("assemblies", array[1]);
        assertEquals("metagenomes", array[2]);
        assertEquals("metatranscriptomes", array[3]);
    }
}