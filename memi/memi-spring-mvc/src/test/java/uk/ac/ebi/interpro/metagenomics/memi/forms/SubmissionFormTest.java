package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Before;
import org.junit.Ignore;
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
    @Ignore("Needs re-write")
    public void testSetterAndGetter() {
        String title = "title";
        form.setSubTitle(title);
        assertEquals(title, form.getSubTitle());
        String releaseDate = "20/12/2010";
        form.setReleaseDate(releaseDate);
        assertEquals(releaseDate, form.getReleaseDate());
        String desc = "desc";
        form.setDataDesc(desc);
        assertEquals(desc, form.getDataDesc());
        boolean isAnalysisRequired = true;
        form.setAnalysisRequired(isAnalysisRequired);
        assertEquals(isAnalysisRequired, form.isAnalysisRequired());
        boolean isHumanAssociated = false;
        form.setHumanAssociated(isHumanAssociated);
        assertEquals(isHumanAssociated, form.isHumanAssociated());
    }

    @Test
    public void testFieldAnnotations() {
        try {
            //check the annotation of the submission title field
            Field subtitleField = SubmissionForm.class.
                    getDeclaredField("subTitle");
            assertNotNull(subtitleField);
            subtitleField.setAccessible(true);
            NotEmpty ann = subtitleField.getAnnotation(NotEmpty.class);
            assertNotNull(ann);
            Size sizeAnn = subtitleField.getAnnotation(Size.class);
            assertNull(sizeAnn);
            //check the annotation of the data description field
            Field dataDesc = SubmissionForm.class.
                    getDeclaredField("dataDesc");
            assertNotNull(dataDesc);
            dataDesc.setAccessible(true);
            ann = dataDesc.getAnnotation(NotEmpty.class);
            assertNotNull(ann);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
