package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.basic.StudyDAOTestImpl;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Represents the unit test for the {@link ViewStudiesController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class ListStudiesControllerTest {

    private ViewStudiesController controller;

    @Before
    public void setUp() throws Exception {
        controller = new ViewStudiesController();

        //Replace study DAO for simpler testing
        Field studyDaoField = ViewStudiesController.class.
                getDeclaredField("emgStudyDAO");
        studyDaoField.setAccessible(true);
        EmgStudyDAO newStudyDAO = new StudyDAOTestImpl();
        studyDaoField.set(controller, newStudyDAO);
    }

    @Test
    public void testInitPage() throws Exception {
        ModelMap model = new ModelMap();
        assertEquals("listStudies", controller.doGet(model));
        //check model
        assertEquals(1, model.size());
        assertTrue(model.containsKey("studySearchForm"));
        assertFalse(model.containsKey("test"));
        StudyFilter studySearchForm = (StudyFilter) model.get("studySearchForm");
        assertNotNull(studySearchForm);
        assertNull("All input and selection fields should be NULL at initialization phase!", studySearchForm.getStudyStatus());
        assertNull("All input and selection fields should be NULL at initialization phase!", studySearchForm.getStudyType());
    }

//    @Test
//    @Ignore
//    public void testPopulateStudyList() throws Exception {
//        List<EmgStudy> studies = controller.populateStudyList();
//        assertNotNull(studies);
//        assertEquals(15, studies.size());
//        assertEquals("study_1", studies.get(0).getStudyName());
////        TODO: Refactor
////        assertEquals(true, studies.get(0).isPublic());
////        assertEquals("study_10", studies.get(9).getStudyName());
////        assertEquals(false, studies.get(9).isPublic());
//    }

    @Test
    @Ignore
    public void testProcessSubmit() throws Exception {
        //TODO: When the filter function is implemented, then we can implement the test as well
        fail("Not yet implemented!");
    }

//    @Test
//    public void testPopulateStudyTypes() throws Exception {
//        EmgStudy.StudyType[] result = controller.populateStudyTypes();
//        assertNotNull(result);
//        assertEquals(3, result.length);
//        assertTrue(containsType(EmgStudy.StudyType.UNDEFINED, result));
//        assertTrue(containsType(EmgStudy.StudyType.ENVIRONMENTAL, result));
//        assertTrue(containsType(EmgStudy.StudyType.HOST_ASSOCIATED, result));
//    }

    private boolean containsType(EmgStudy.StudyType type, EmgStudy.StudyType[] result) {
        for (int i = 0; i < result.length; i++) {
            if (result[i].equals(type))
                return true;
        }
        return false;
    }

    @Test
//    public void testPopulateStudyStati() throws Exception {
//        EmgStudy.StudyStatus[] result = controller.populateStudyStati();
//        assertNotNull(result);
//        assertEquals(3, result.length);
//        assertTrue(containsStatus(EmgStudy.StudyStatus.QUEUED, result));
//        assertTrue(containsStatus(EmgStudy.StudyStatus.IN_PROCESS, result));
//        assertTrue(containsStatus(EmgStudy.StudyStatus.FINISHED, result));
//    }

    private boolean containsStatus(EmgStudy.StudyStatus status, EmgStudy.StudyStatus[] result) {
        for (int i = 0; i < result.length; i++) {
            if (result[i].equals(status))
                return true;
        }
        return false;
    }
}