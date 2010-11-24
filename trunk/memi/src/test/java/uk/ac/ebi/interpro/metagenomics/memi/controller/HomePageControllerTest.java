package uk.ac.ebi.interpro.metagenomics.memi.controller;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the unit test for the {@link HomePageController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class HomePageControllerTest extends TestCase {

    private HomePageController controller;

    @Before
    public void setUp() throws Exception {
        this.controller = new HomePageController();

        Field daoField = HomePageController.class.
                getDeclaredField("studyDAO");
        daoField.setAccessible(true);
        StudyDAO newStudyDAO = new StudyDAOTestImpl();
        daoField.set(controller, newStudyDAO);
    }

    @Test
    public void testCreateModel() throws Exception {
        ModelAndView mav = controller.createModel();
        assertTrue(mav.getModel() != null);
        assertEquals(1, mav.getModel().size());
//          tests the requested ModelAndView object
        ModelAndViewAssert.assertViewName(mav, "homePage");
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "studies");
        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "studies", List.class);
//          creates an expected MAV and checks against the actual
//        TODO: Check why assertModelAttributeValues does not use the implemented equals method of the Study object
//        StudyDAO studyDAO = new StudyDAOTestImpl();
//        List<Study> studies = studyDAO.getStudiesByVisibility(true);
//        ModelMap expectedModel = new ModelMap();
//        expectedModel.put("studies", studies);
//        ModelAndViewAssert.assertModelAttributeValues(mav, expectedModel);
    }

    /**
     * This DAO implementation should only used for JUnit test.
     */
    class StudyDAOTestImpl implements StudyDAO {

        public StudyDAOTestImpl() {
        }

        @Override
        public List<Study> getStudies() {
            List<Study> result = new ArrayList<Study>();
            result.add(new Study("study_1", true));
            result.add(new Study("study_2", true));
            result.add(new Study("study_3", true));
            result.add(new Study("study_4", true));
            result.add(new Study("study_5", true));
            result.add(new Study("study_6", false));
            result.add(new Study("study_7", false));
            result.add(new Study("study_8", false));
            result.add(new Study("study_9", false));
            result.add(new Study("study_10", false));
            return result;
        }

        @Override
        public List<Study> getStudiesByVisibility(boolean isPublic) {
            List<Study> result = new ArrayList<Study>();
            result.add(new Study("study_1", true));
            result.add(new Study("study_2", true));
            result.add(new Study("study_3", true));
            result.add(new Study("study_4", true));
            result.add(new Study("study_5", true));
            result.add(new Study("study_6", true));
            result.add(new Study("study_7", true));
            result.add(new Study("study_8", true));
            result.add(new Study("study_9", true));
            result.add(new Study("study_10", true));
            return result;
        }

//    TODO: Implement

        @Override
        public Study getStudyById(long id) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

//    TODO: Implement

        @Override
        public void deleteStudy(Study study) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}