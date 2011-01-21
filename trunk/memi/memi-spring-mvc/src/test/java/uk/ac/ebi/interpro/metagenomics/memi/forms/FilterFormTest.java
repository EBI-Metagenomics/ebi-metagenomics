package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;

import static org.junit.Assert.assertEquals;

/**
 * Represents the JUnit test for {@link StudySearchForm}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class FilterFormTest {
    private StudySearchForm form;

    @Before
    public void setUp() throws Exception {
        form = new StudySearchForm();
    }

    @Test
    public void testSetterAndGetter() {
//        form.setStudyStatus(EmgStudy.StudyStatus.FINISHED);
//        assertEquals(EmgStudy.StudyStatus.FINISHED, form.getStudyStatus());
        form.setStudyType(EmgStudy.StudyType.ENVIRONMENTAL);
        assertEquals(EmgStudy.StudyType.ENVIRONMENTAL, form.getStudyType());
    }
}