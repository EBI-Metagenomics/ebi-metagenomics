package uk.ac.ebi.interpro.metagenomics.memi.controller;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.basic.StudyDAOTestImpl;
import uk.ac.ebi.interpro.metagenomics.memi.dao.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.FilterForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Represents the unit test for the {@link uk.ac.ebi.interpro.metagenomics.memi.controller.ListStudiesController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class ListStudiesControllerTest extends TestCase {

    private ListStudiesController controller;

    @Before
    public void setUp() throws Exception {
        controller = new ListStudiesController();

        //Replace study DAO for simpler testing
        Field studyDaoField = ListStudiesController.class.
                getDeclaredField("studyDAO");
        studyDaoField.setAccessible(true);
        StudyDAO newStudyDAO = new StudyDAOTestImpl();
        studyDaoField.set(controller, newStudyDAO);
    }

    @Test
    public void testInitPage() throws Exception {
        ModelMap model = new ModelMap();
        assertEquals("listStudies", controller.initPage(model));
        //check model
        assertEquals(1, model.size());
        assertTrue(model.containsKey("filterForm"));
        assertFalse(model.containsKey("test"));
        FilterForm filterForm = (FilterForm) model.get("filterForm");
        assertNotNull(filterForm);
        assertNull("All input and selection fields should be NULL at initialization phase!", filterForm.getStudyStatus());
        assertNull("All input and selection fields should be NULL at initialization phase!", filterForm.getStudyType());
    }

    @Test
    public void testPopulateStudyList() throws Exception {
        List<Study> studies = controller.populateStudyList();
        assertNotNull(studies);
        assertEquals(15, studies.size());
        assertEquals("study_1", studies.get(0).getStudyName());
        assertEquals(true, studies.get(0).isPublic());
        assertEquals("study_10", studies.get(9).getStudyName());
        assertEquals(false, studies.get(9).isPublic());
    }

    @Test
    @Ignore
    public void testProcessSubmit() throws Exception {
        //TODO: When the filter function is implemented, then we can implement the test as well
        fail("Not yet implemented!");
    }

    @Test
    public void testPopulateStudyTypes() throws Exception {
        Study.StudyType[] result = controller.populateStudyTypes();
        assertNotNull(result);
        assertEquals(3, result.length);
        assertTrue(containsType(Study.StudyType.UNDEFINED, result));
        assertTrue(containsType(Study.StudyType.ENVIRONMENT, result));
        assertTrue(containsType(Study.StudyType.HUMAN, result));
    }

    private boolean containsType(Study.StudyType type, Study.StudyType[] result) {
        for (int i = 0; i < result.length; i++) {
            if (result[i].equals(type))
                return true;
        }
        return false;
    }

    @Test
    public void testPopulateStudyStati() throws Exception {
        Study.StudyStatus[] result = controller.populateStudyStati();
        assertNotNull(result);
        assertEquals(3, result.length);
        assertTrue(containsStatus(Study.StudyStatus.QUEUED, result));
        assertTrue(containsStatus(Study.StudyStatus.IN_PROCESS, result));
        assertTrue(containsStatus(Study.StudyStatus.FINISHED, result));
    }

    private boolean containsStatus(Study.StudyStatus status, Study.StudyStatus[] result) {
        for (int i = 0; i < result.length; i++) {
            if (result[i].equals(status))
                return true;
        }
        return false;
    }
}