package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Represents the JUnit test for {@link StudyFilter}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class FilterFormTest {
    private StudyFilter form;

    @Before
    public void setUp() throws Exception {
        form = new StudyFilter();
    }

    @Test
    @Ignore("Have to be reimplemented")
    public void testSetterAndGetter() {
//        form.setStudyStatus(EmgStudy.StudyStatus.FINISHED);
//        assertEquals(EmgStudy.StudyStatus.FINISHED, form.getStudyStatus());
//        form.setStudyType(Study.StudyType.ENVIRONMENTAL);
//        assertEquals(Study.StudyType.ENVIRONMENTAL, form.getStudyType());
    }
}