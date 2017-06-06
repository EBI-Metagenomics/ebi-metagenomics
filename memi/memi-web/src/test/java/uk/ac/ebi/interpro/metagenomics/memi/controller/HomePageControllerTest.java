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

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * Represents the unit test for the {@link HomePageController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class HomePageControllerTest {

    private HomePageController controller;

    @Before
    public void setUp() throws Exception {
        this.controller = new HomePageController();

        //Replace study DAO for simpler testing
        Field studyDaoField = HomePageController.class.
                getDeclaredField("emgStudyDAO");
        studyDaoField.setAccessible(true);
        EmgStudyDAO newStudyDAO = new StudyDAOTestImpl();
        studyDaoField.set(controller, newStudyDAO);
    }

    @Test
    @Ignore("Needs re-writing")
    public void testInitPage() throws Exception {
        ModelMap model = new ModelMap();
        assertEquals("homePage", controller.doGet(model));
    }

    @Test
    @Ignore
    public void testPopulateStudyList() throws Exception {
//        List<EmgStudy> studies = MGModelFactory.getLimitedPublicStudiesFromDB(null);
//        assertNotNull(studies);
//        assertEquals(10, studies.size());
//        assertEquals("study_1", studies.get(0).getStudyName());
////        assertEquals(true, studies.get(0).isPublic());
//        assertEquals("study_15", studies.get(9).getStudyName());
////        assertEquals(true, studies.get(9).isPublic());
    }

//    @Test
//    public void testPopulateNewsList() throws Exception {
//        List<News> newsList = MGModelFactory.getNewsListFromDB(null);
//        assertNotNull(newsList);
//        assertEquals(10, newsList.size());
//        assertEquals("Headline 1", newsList.get(0).getNewsHeadline());
//        assertEquals("message", newsList.get(0).getNewsMsg());
//    }

}
