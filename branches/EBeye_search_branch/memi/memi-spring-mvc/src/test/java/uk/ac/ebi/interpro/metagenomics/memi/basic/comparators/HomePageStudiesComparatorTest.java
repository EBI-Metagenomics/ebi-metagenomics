package uk.ac.ebi.interpro.metagenomics.memi.basic.comparators;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.*;

/**
 * Test for {@link HomePageStudiesComparator}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class HomePageStudiesComparatorTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HomePageStudiesComparatorTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(HomePageStudiesComparatorTest.class);
    }

    /**
     * Tests the comparator in combination with the sorted set.
     */
    @org.junit.Test
    public void testCompareSortedSet() {
        Calendar calendar = null;

        Study study1 = new Study();
        study1.setStudyName("a");
        calendar = new GregorianCalendar(2011, 01, 02);
        study1.setLastMetadataReceived(calendar.getTime());
        //
        Study study2 = new Study();
        study2.setStudyName("b");
        calendar = new GregorianCalendar(2011, 01, 04);
        study2.setLastMetadataReceived(calendar.getTime());
        //
        Study study3 = new Study();
        study3.setStudyName("c");
        calendar = new GregorianCalendar(2011, 01, 01);
        study3.setLastMetadataReceived(calendar.getTime());
        //
        Study study4 = new Study();
        study4.setStudyName("d");
        calendar = new GregorianCalendar(2011, 01, 01);
        study4.setLastMetadataReceived(calendar.getTime());
        //
        SortedSet<Study> studies = new TreeSet<Study>(new HomePageStudiesComparator());
        studies.add(study1);
        studies.add(study2);
        studies.add(study3);
        studies.add(study4);

        Study[] studyArray = studies.toArray(new Study[4]);

        assertEquals("Unexpected study set size!", 4, studies.size());
        assertEquals("Unexpected study array length!", 4, studyArray.length);
        assertEquals("Study set is sorted in a wrong order!", study2, studyArray[0]);
        assertEquals("Study set is sorted in a wrong order!", study1, studyArray[1]);
        assertEquals("Study set is sorted in a wrong order!", study3, studyArray[2]);
        assertEquals("Study set is sorted in a wrong order!", study4, studyArray[3]);
    }

    /**
     * Tests the comparator in combination collections.sort().
     */
    @org.junit.Test
    public void testCompareCollectionsSort() {
        Calendar calendar = null;

        Study study1 = new Study();
        study1.setStudyName("a");
        calendar = new GregorianCalendar(2011, 01, 02);
        study1.setLastMetadataReceived(calendar.getTime());
        //
        Study study2 = new Study();
        study2.setStudyName("b");
        calendar = new GregorianCalendar(2011, 01, 04);
        study2.setLastMetadataReceived(calendar.getTime());
        //
        Study study3 = new Study();
        study3.setStudyName("c");
        calendar = new GregorianCalendar(2011, 01, 01);
        study3.setLastMetadataReceived(calendar.getTime());
        //
        Study study4 = new Study();
        study4.setStudyName("d");
        calendar = new GregorianCalendar(2011, 01, 01);
        study4.setLastMetadataReceived(calendar.getTime());
        //
        List<Study> studies = new ArrayList<Study>();
        studies.add(study1);
        studies.add(study2);
        studies.add(study3);
        studies.add(study4);
        //
        assertEquals("Unexpected study set size!", 4, studies.size());
        assertEquals("Study set is in a wrong order!", study1, studies.get(0));
        assertEquals("Study set is in a wrong order!", study2, studies.get(1));
        assertEquals("Study set is in a wrong order!", study3, studies.get(2));
        assertEquals("Study set is in a wrong order!", study4, studies.get(3));
        //
        Collections.sort(studies, new HomePageStudiesComparator());
        assertEquals("Unexpected study set size!", 4, studies.size());
        assertEquals("Study set is in a wrong order!", study2, studies.get(0));
        assertEquals("Study set is in a wrong order!", study1, studies.get(1));
        assertEquals("Study set is in a wrong order!", study3, studies.get(2));
        assertEquals("Study set is in a wrong order!", study4, studies.get(3));
    }
}