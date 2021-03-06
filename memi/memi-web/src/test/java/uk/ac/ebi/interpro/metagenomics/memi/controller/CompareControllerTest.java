package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.AnalysisJobVO;

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
        Map<AnalysisJobVO, String> sampleToFilePathMap = new HashMap<AnalysisJobVO, String>();
        List<AnalysisJobVO> activeSamples = null;
        List<AnalysisJobVO> deactiveSamples = null;
        try {
            controller.doFileExistenceCheck(sampleToFilePathMap, activeSamples, deactiveSamples);
            assertTrue("Method should throw an exception.", false);
        } catch (IllegalStateException e) {
            //That is expected, go ahead
        }
        //Set test method input
        sampleToFilePathMap = new HashMap<AnalysisJobVO, String>();
        activeSamples = new ArrayList<AnalysisJobVO>();
        deactiveSamples = new ArrayList<AnalysisJobVO>();
        //Run the test method
        controller.doFileExistenceCheck(sampleToFilePathMap, activeSamples, deactiveSamples);
        //Set test method input
        sampleToFilePathMap = new HashMap<AnalysisJobVO, String>();
        activeSamples = new ArrayList<AnalysisJobVO>();
        deactiveSamples = new ArrayList<AnalysisJobVO>();
        //analysis job instance
        AnalysisJobVO analysisJobVO = new AnalysisJobVO();

        //
        sampleToFilePathMap.put(analysisJobVO, "/uk/ac/ebi/test.txt");
        sampleToFilePathMap.put(analysisJobVO, "/uk/ac/ebi/test_2.txt");
        controller.doFileExistenceCheck(sampleToFilePathMap, activeSamples, deactiveSamples);
    }

    @Test
    public void testRunIdHack() throws Exception {
        List<String> sampleIds = new ArrayList<String>();

        sampleIds.add("ERS000005");
        sampleIds.add("ERS000006");
        sampleIds.add("ERS000003");
        sampleIds.add("ERS000004");
        sampleIds.add("ERS000010");
        sampleIds.add("ERS000002");
        sampleIds.add("ERS000001");
        sampleIds.add("ERS000002");
        sampleIds.add("ERS000003");
        sampleIds.add("ERS000003");
        sampleIds.add("ERS000003");
        assertTrue(sampleIds.size() == 11);

        List<String> sortedList = controller.sortAndAdjustSampleNameList(sampleIds);
        assertTrue(sortedList.size() == 11);
        assertEquals("ERS000001", sortedList.get(0));
        assertEquals("ERS000002", sortedList.get(1));
        assertEquals("ERS000002_2", sortedList.get(2));
        assertEquals("ERS000003", sortedList.get(3));
        assertEquals("ERS000003_2", sortedList.get(4));
        assertEquals("ERS000003_3", sortedList.get(5));
        assertEquals("ERS000003_4", sortedList.get(6));
        assertEquals("ERS000004", sortedList.get(7));
        assertEquals("ERS000005", sortedList.get(8));
        assertEquals("ERS000006", sortedList.get(9));
        assertEquals("ERS000010", sortedList.get(10));
    }
}