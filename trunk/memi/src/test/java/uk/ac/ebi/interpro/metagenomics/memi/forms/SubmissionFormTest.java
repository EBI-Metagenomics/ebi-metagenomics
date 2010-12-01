package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.Size;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SubmissionFormTest {

    private SubmissionForm form;

    @Before
    public void setUp() throws Exception {
        form = new SubmissionForm();
    }

    @Test
    public void testSetterAndGetter() {
        String title = "title";
        form.setSubTitle(title);
        assertEquals(title, form.getSubTitle());
        String expl = "explanation";
        form.setSubExplanation(expl);
        assertEquals(expl, form.getSubExplanation());
        String desc = "desc";
        form.setDataDesc(desc);
        assertEquals(desc, form.getDataDesc());
    }

    @Test
    public void testFieldAnnotations() {
        try {
            Field subtitleField = SubmissionForm.class.
                    getDeclaredField("subTitle");
            assertNotNull(subtitleField);
            subtitleField.setAccessible(true);
            NotEmpty ann = subtitleField.getAnnotation(NotEmpty.class);
            assertNotNull(ann);
            Size sizeAnn = subtitleField.getAnnotation(Size.class);
            assertNull(sizeAnn);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}