package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.basic.SampleDAOTestImpl;
import uk.ac.ebi.interpro.metagenomics.memi.basic.StudyDAOTestImpl;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Represents a test for the {@link uk.ac.ebi.interpro.metagenomics.memi.controller.StudyOverviewController}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class StudyOverviewControllerTest {
    private StudyOverviewController controller;

    @Before
    public void setUp() throws Exception {
        controller = new StudyOverviewController();

        //Replace study DAO for simpler testing
        Field studyDaoField = StudyOverviewController.class.
                getDeclaredField("studyDAO");
        studyDaoField.setAccessible(true);
        StudyDAO newStudyDAO = new StudyDAOTestImpl();
        studyDaoField.set(controller, newStudyDAO);

        //Replace sample DAO for simpler testing
        Field sampleDaoField = StudyOverviewController.class.
                getDeclaredField("sampleDAO");
        sampleDaoField.setAccessible(true);
        SampleDAO newSampleDAO = new SampleDAOTestImpl();
        sampleDaoField.set(controller, newSampleDAO);
    }

    @Test
    public void testInitPage() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("id", "1");
        ModelMap model = new ModelMap();
        assertEquals("studyOverview", controller.initPage(model, request));
        //check model
        assertEquals(1, model.size());
        assertTrue(model.containsKey("study"));
        assertFalse(model.containsKey("test"));
        Study study = (Study) model.get("study");
        assertNotNull(study);
    }

    @Test
    public void testPopulateSampleList() throws Exception {
        List<Sample> result = controller.populateSampleList();
        assertNotNull(result);
        assertEquals(10, result.size());
        assertEquals("sample_1", result.get(0).getSampleName());
        Map<String, Object> propMap = result.get(0).getPropertyMap();
        assertNotNull(propMap);
        assertTrue(propMap.size() > 0);
        String value = (String) propMap.get("geo_area");
        assertEquals("Hinxton", value);
    }

    @Test
    public void testPopulateSamplePropertyList() throws Exception {
        List<String> result = controller.populateSamplePropertyList();
        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertTrue(result.contains("geo_area"));
        assertTrue(result.contains("biome"));
        assertFalse(result.contains("test"));
    }
}