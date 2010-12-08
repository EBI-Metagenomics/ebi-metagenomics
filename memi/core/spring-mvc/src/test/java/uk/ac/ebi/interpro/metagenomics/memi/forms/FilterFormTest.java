package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

import static org.junit.Assert.assertEquals;

/**
 * Represents the JUnit test for {@link uk.ac.ebi.interpro.metagenomics.memi.forms.FilterForm}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class FilterFormTest {
    private FilterForm form;

    @Before
    public void setUp() throws Exception {
        form = new FilterForm();
    }

    @Test
    public void testSetterAndGetter() {
        form.setStudyStatus(Study.StudyStatus.FINISHED);
        assertEquals(Study.StudyStatus.FINISHED, form.getStudyStatus());
        form.setStudyType(Study.StudyType.ENVIRONMENT);
        assertEquals(Study.StudyType.ENVIRONMENT, form.getStudyType());
    }
}