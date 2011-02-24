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
import uk.ac.ebi.interpro.metagenomics.memi.dao.NewsDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.News;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
        //Replace news DAO
        Field newsDaoField = HomePageController.class.
                getDeclaredField("newsDAO");
        newsDaoField.setAccessible(true);
        NewsDAO newNewsDAO = new NewsDAOTestImpl();
        newsDaoField.set(controller, newNewsDAO);


    }

    @Test
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

    /**
     * This DAO implementation should only be used for JUnit test.
     */
    class NewsDAOTestImpl implements NewsDAO {

        public NewsDAOTestImpl() {
        }

        @Override
        public List<News> getLatestNews() {
            List<News> result = new ArrayList<News>();
            result.add(new News("Headline 1", "message"));
            result.add(new News("Headline 2", "message"));
            result.add(new News("Headline 3", "message"));
            result.add(new News("Headline 4", "message"));
            result.add(new News("Headline 5", "message"));
            result.add(new News("Headline 6", "message"));
            result.add(new News("Headline 7", "message"));
            result.add(new News("Headline 8", "message"));
            result.add(new News("Headline 9", "message"));
            result.add(new News("Headline 10", "message"));
            return result;
        }
    }
}
