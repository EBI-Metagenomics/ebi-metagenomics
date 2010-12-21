package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Represents the test for {@link uk.ac.ebi.interpro.metagenomics.memi.controller.SampleOverviewController}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class SampleOverviewControllerTest {

    private SampleOverviewController controller;

    @Before
    public void setUp() throws Exception {
        controller = new SampleOverviewController();
        //Replace sample DAO for simpler testing
        Field sampleDaoField = SampleOverviewController.class.
                getDeclaredField("sampleDAO");
        sampleDaoField.setAccessible(true);
        EmgSampleDAO newSampleDAO = new SampleDAOTestImpl();
        sampleDaoField.set(controller, newSampleDAO);
    }

    @Test
    @Ignore
    public void testInitPage() throws Exception {
        ModelMap model = new ModelMap();
        assertEquals("sampleOverview", controller.findSample("1", model));
        //check model
        assertEquals(1, model.size());
        assertTrue(model.containsKey("sample"));
        assertFalse(model.containsKey("test"));
        EmgSample sample = (EmgSample) model.get("sample");
        assertNotNull(sample);
        //check the model object
        assertEquals("sample_1", sample.getSampleTitle());
    }
}
