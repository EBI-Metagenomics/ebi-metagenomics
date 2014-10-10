package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.UndefinedSample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Represents the unit test for the {@link uk.ac.ebi.interpro.metagenomics.memi.controller.CompareController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class CompareControllerTest {

    private CompareController controller;

    @Before
    public void setUp() throws Exception {
        this.controller = new CompareController();
    }

    @Test
    public void testDoFileExistenceCheck() throws Exception {
        //Set test method input
        Map<Sample, String> sampleToFilePathMap = new HashMap<Sample, String>();
        List<Sample> activeSamples = null;
        List<Sample> deactiveSamples = null;
        try {
            controller.doFileExistenceCheck(sampleToFilePathMap, activeSamples, deactiveSamples);
            assertTrue("Method should throw an exception.", false);
        } catch (IllegalStateException e) {
            //That is expected, go ahead
        }
        //Set test method input
        sampleToFilePathMap = new HashMap<Sample, String>();
        activeSamples = new ArrayList<Sample>();
        deactiveSamples = new ArrayList<Sample>();
        //Run the test method
        controller.doFileExistenceCheck(sampleToFilePathMap, activeSamples, deactiveSamples);
        //Set test method input
        sampleToFilePathMap = new HashMap<Sample, String>();
        activeSamples = new ArrayList<Sample>();
        deactiveSamples = new ArrayList<Sample>();
        sampleToFilePathMap.put(new UndefinedSample(), "/uk/ac/ebi/test.txt");
        sampleToFilePathMap.put(new UndefinedSample(), "/uk/ac/ebi/test_2.txt");
        controller.doFileExistenceCheck(sampleToFilePathMap, activeSamples, deactiveSamples);
    }

}