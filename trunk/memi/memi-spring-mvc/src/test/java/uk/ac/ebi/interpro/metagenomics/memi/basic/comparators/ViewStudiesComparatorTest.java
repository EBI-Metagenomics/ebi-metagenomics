package uk.ac.ebi.interpro.metagenomics.memi.basic.comparators;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.HostSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.*;

/**
 * Test for {@link ViewStudiesComparator}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ViewStudiesComparatorTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ViewStudiesComparatorTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ViewStudiesComparatorTest.class);
    }

    /**
     * Tests the comparator in combination with the sorted set.
     */
    @org.junit.Test
    public void testCompareSortedSet() {
        Study study1 = new Study();
        study1.setStudyName("b");
        study1.setId(1);
        //
        Study study2 = new Study();
        study2.setStudyName("a");
        study2.setId(2);
        //
        Study study3 = new Study();
        study3.setStudyName("c");
        study3.setId(4);
        //
        Study study4 = new Study();
        study4.setStudyName("c");
        study4.setId(3);
        //
        SortedSet<Study> studies = new TreeSet<Study>(new ViewStudiesComparator());
        studies.add(study1);
        studies.add(study2);
        studies.add(study3);
        studies.add(study4);
        //
        Study[] studyArray = studies.toArray(new Study[4]);

        assertEquals("Unexpected study set size!", 4, studies.size());
        assertEquals("Unexpected study array length!", 4, studyArray.length);
        assertEquals("Study set is sorted in a wrong order!", study2, studyArray[0]);
        assertEquals("Study set is sorted in a wrong order!", study1, studyArray[1]);
        assertEquals("Study set is sorted in a wrong order!", study4, studyArray[2]);
        assertEquals("Study set is sorted in a wrong order!", study3, studyArray[3]);
    }

    /**
     * Tests the comparator in combination with collections.sort().
     */
    @org.junit.Test
    public void testCompareCollectionsSort() {
        Study study1 = new Study();
        study1.setStudyName("b");
        study1.setId(1);
        //
        Study study2 = new Study();
        study2.setStudyName("a");
        study2.setId(2);
        //
        Study study3 = new Study();
        study3.setStudyName("c");
        study3.setId(4);
        //
        Study study4 = new Study();
        study4.setStudyName("c");
        study4.setId(3);
        //
        List<Study> studies = new ArrayList<Study>();
        studies.add(study1);
        studies.add(study2);
        studies.add(study3);
        studies.add(study4);
        //
        assertEquals("Unexpected study list length!", 4, studies.size());
        assertEquals("Study list is in a wrong order!", study1, studies.get(0));
        assertEquals("Study list is in a wrong order!", study2, studies.get(1));
        assertEquals("Study list is in a wrong order!", study3, studies.get(2));
        assertEquals("Study list is in a wrong order!", study4, studies.get(3));
        //
        Collections.sort(studies, new ViewStudiesComparator());
        assertEquals("Unexpected study list length!", 4, studies.size());
        assertEquals("Study list is in a wrong order!", study2, studies.get(0));
        assertEquals("Study list is in a wrong order!", study1, studies.get(1));
        assertEquals("Study list is in a wrong order!", study4, studies.get(2));
        assertEquals("Study list is in a wrong order!", study3, studies.get(3));
    }
}