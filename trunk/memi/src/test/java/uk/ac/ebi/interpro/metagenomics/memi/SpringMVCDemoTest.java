package uk.ac.ebi.interpro.metagenomics.memi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.HelloWorldController;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Represents a first simple unit test for the Spring MVC Hello world application.
 * Tried to hold on Spring JUnit test conventions as described in the Spring framework reference.
 * Please find it on http://static.springsource.org/spring/docs/.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
@TestExecutionListeners
public class SpringMVCDemoTest {
    private HelloWorldController controller;

    @Before
    public void setUp() throws Exception {
        controller = new HelloWorldController();
    }

    @Test
    public void handleRequest() {
        ModelAndView mav = controller.helloWorld();
        assertTrue(mav != null);
//          tests the requested ModelAndView object
        ModelAndViewAssert.assertViewName(mav, "HelloWorldPage");
        ModelAndViewAssert.assertModelAttributeAvailable(mav, "message");
        ModelAndViewAssert.assertAndReturnModelAttributeOfType(mav, "message", String.class);
//          creates an expected MAV and checks against the actual
        Map<String, Object> expectedModel = new HashMap<String, Object>();
        expectedModel.put("message", "Hello World MVC!");
        ModelAndViewAssert.assertModelAttributeValues(mav, expectedModel);
        assertTrue(mav.getModel() != null);
        assertEquals(1, mav.getModel().size());
    }
}