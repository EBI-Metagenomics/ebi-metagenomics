package uk.ac.ebi.interpro.metagenomics.memi.controller;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.interpro.metagenomics.memi.dao.NewsDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.News;
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

        //Replace study DAO for simpler testing
        Field studyDaoField = HomePageController.class.
                getDeclaredField("studyDAO");
        studyDaoField.setAccessible(true);
        StudyDAO newStudyDAO = new StudyDAOTestImpl();
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
        assertEquals("homePage", controller.initPage());
    }

    @Test
    public void testPopulateStudyList() throws Exception {
        List<Study> studies = controller.populateStudyList();
        assertNotNull(studies);
        assertEquals(10, studies.size());
        assertEquals("study_1", studies.get(0).getStudyName());
        assertEquals(true, studies.get(0).isPublic());
        assertEquals("study_10", studies.get(9).getStudyName());
        assertEquals(true, studies.get(9).isPublic());
    }

    @Test
    public void testPopulateNewsList() throws Exception {
        List<News> newsList = controller.populateNewsList();
        assertNotNull(newsList);
        assertEquals(10, newsList.size());
        assertEquals("Headline 1", newsList.get(0).getNewsHeadline());
        assertEquals("message", newsList.get(0).getNewsMsg());
    }

    /**
     * This DAO implementation should only be used for JUnit test.
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