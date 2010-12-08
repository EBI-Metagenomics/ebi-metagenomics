package uk.ac.ebi.interpro.metagenomics.memi.controller;

import junit.framework.TestCase;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SimpleSessionStatus;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SubmissionForm;

import javax.annotation.Resource;
import java.lang.reflect.Field;

/**
 * Represents the unit test for the {@link SubmissionController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
//TODO: Find a way to simulate POST requests. We can not use class MockHttpServletRequest, because our controller uses annotation-based controller implementation instead of the old controller interface.
//If there is a way you can e.g. test empty input fields
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SubmissionControllerTest extends TestCase {

    private SubmissionController controller;

    @Resource(name = "emailNotificationTestService")
    private EmailNotificationTestService emailTestService;

    @Resource(name = "velocityTestEngine")
    private VelocityEngine velocityTestEngine;

    @Before
    public void setUp() throws Exception {
        controller = new SubmissionController();

        //Replace email service
        Field emailServiceField = SubmissionController.class.
                getDeclaredField("emailService");
        emailServiceField.setAccessible(true);
        emailServiceField.set(controller, emailTestService);
        //Replace velocityEngine
        Field velocityEngineField = SubmissionController.class.
                getDeclaredField("velocityEngine");
        velocityEngineField.setAccessible(true);
        velocityEngineField.set(controller, velocityTestEngine);
    }

    @Test
    public void testInitForm() throws Exception {
        ModelMap model = new ModelMap();
        assertEquals("submissionForm", controller.initForm(model));
        assertEquals(1, model.size());
        assertTrue(model.containsKey("subForm"));
        assertFalse(model.containsKey("test"));
        SubmissionForm subForm = (SubmissionForm) model.get("subForm");
        assertNotNull(subForm);
        assertNull("All input fields should be null at initialization phase!", subForm.getSubTitle());
        assertNull("All input fields should be null at initialization phase!", subForm.getSubExplanation());
        assertNull("All input fields should be null at initialization phase!", subForm.getDataDesc());
    }

    @Test
    public void testProcessSubmit() throws Exception {
        SubmissionForm subForm = new SubmissionForm();
        ModelMap model = new ModelMap();
        BindingResult result = new BeanPropertyBindingResult(subForm, "subForm");
        //1. test case: no submission form object provided
        assertEquals("If the submission form bean is not attached at the model a NPE will occur!", "errorPage", controller.processSubmit(subForm, result, model, new SimpleSessionStatus()));
        //2. test case: Input fields contain data
        String testTitle = "test title";
        String testExplanation = "test explanation";
        String testDescription = "test description";
        subForm.setSubTitle(testTitle);
        subForm.setSubExplanation(testExplanation);
        subForm.setDataDesc(testDescription);
        model.put("subForm", subForm);
        result = new BeanPropertyBindingResult(subForm, "subForm");
        assertEquals(0, result.getErrorCount());
        assertEquals("submissionSuccessPage", controller.processSubmit(subForm, result, model, new SimpleSessionStatus()));
        assertEquals(1, model.size());
        assertTrue(model.containsKey("subForm"));
        assertEquals(0, result.getErrorCount());
        SubmissionForm modelSubFormObj = (SubmissionForm) model.get("subForm");
        assertEquals("The content of the submission title input field should not be null!", testTitle, modelSubFormObj.getSubTitle());
        assertEquals("The content of the submission explanation input field should not be null!", testExplanation, modelSubFormObj.getSubExplanation());
        assertEquals("The content of the data description input field should not be null!", testDescription, modelSubFormObj.getDataDesc());
    }

    @Test
    public void testBuildMsg() {
        SubmissionForm subForm = new SubmissionForm();
        String testTitle = "test title";
        String testExplanation = "test explanation";
        String testDescription = "test description";
        subForm.setSubTitle(testTitle);
        subForm.setSubExplanation(testExplanation);
        subForm.setDataDesc(testDescription);
        String actual = controller.buildMsg(subForm);
        //Test the static part of the email content
        assertTrue(actual.contains("Submitter name:"));
        assertTrue(actual.contains("Submission title:"));
        assertTrue(actual.contains("Submission explanation:"));
        assertTrue(actual.contains("Data description:"));
        //Test if submission form values are part of the message text        
        assertTrue(actual.contains(testTitle));
        assertTrue(actual.contains(testExplanation));
        assertTrue(actual.contains(testDescription));
        assertFalse("Should not be a part of the message text!", actual.contains("hallo"));
    }
}