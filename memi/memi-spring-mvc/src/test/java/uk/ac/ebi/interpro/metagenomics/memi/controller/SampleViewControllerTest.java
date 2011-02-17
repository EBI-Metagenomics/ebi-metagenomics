package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.basic.SampleDAOTestImpl;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Represents the test for {@link SampleViewController}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class SampleViewControllerTest {

    private SampleViewController controller;

    @Before
    public void setUp() throws Exception {
        controller = new SampleViewController();
        //Replace sample DAO for simpler testing
        Field sampleDaoField = SampleViewController.class.
                getDeclaredField("sampleDAO");
        sampleDaoField.setAccessible(true);
        EmgSampleDAO newSampleDAO = new SampleDAOTestImpl();
        sampleDaoField.set(controller, newSampleDAO);
    }

    @Test
    @Ignore
    public void testInitPage() throws Exception {
        ModelMap model = new ModelMap();
        assertEquals("sampleOverview", controller.doGetSample(model));
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
