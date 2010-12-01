package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SimpleSessionStatus;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Represents the unit test for the {@link uk.ac.ebi.interpro.metagenomics.memi.controller.LoginController}.
 * Some useful tutorials for integration tests using Spring MVC annoations:<br>
 * 1. http://www.scarba05.co.uk/blog/2010/03/integration-testing-of-springs-mvc-annotation-mapppings-for-controllers/<br>
 * 2. http://stackoverflow.com/questions/1401128/how-to-unit-test-a-spring-mvc-controller-using-pathvariable<br>
 * 3. http://www.devx.com/Java/Article/30067/1954<br>
 * 4. http://forum.springsource.org/showthread.php?t=80411
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
//TODO: Find a way to simulate POST requests. We can not use class MockHttpServletRequest, because our controller uses annotation-based controller implementation instead of the old controller interface.
//If there is a way you can e.g. test empty input fields
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class LoginControllerTest {

    private LoginController loginController;


    @Before
    public void setUp() throws Exception {
        loginController = new LoginController();

        Field daoField = LoginController.class.
                getDeclaredField("submitterDAO");
        daoField.setAccessible(true);

        SubmitterDAO newSubmitterDAO = new SubmitterDAOTestImpl();
        daoField.set(loginController, newSubmitterDAO);
    }

    @Test
    public void testInitForm() {
        ModelMap model = new ModelMap();
        assertEquals("loginForm", loginController.initForm(model));
        assertEquals(1, model.size());
        assertTrue(model.containsKey("loginForm"));
        assertFalse(model.containsKey("test"));
        LoginForm loginFormBean = (LoginForm) model.get("loginForm");
        assertNotNull(loginFormBean);
        assertNull("Email address should be null at initialization phase!", loginFormBean.getEmailAddress());
        assertNull("Password should be null at initialization phase!", loginFormBean.getPassword());
    }

    /**
     *
     */
    @Test
    public void testProcessSubmit() {
        LoginForm loginFormBean = new LoginForm();
        ModelMap model = new ModelMap();
        BindingResult result = new BeanPropertyBindingResult(loginFormBean, "loginForm");
        //1. test case: no login form object provided
        assertEquals("If the submission form bean is not attached at the model a NPE will occur!!", "errorPage", loginController.processSubmit(loginFormBean, result, model, new SimpleSessionStatus()));
        //2. test case: wrong login data
        String wrongEmailStr = "wrong@email.com";
        String pwStr = "wrongPassword";
        loginFormBean.setEmailAddress(wrongEmailStr);
        loginFormBean.setPassword(pwStr);
        model.put("loginForm", loginFormBean);
        result = new BeanPropertyBindingResult(loginFormBean, "test");
        assertEquals(0, result.getErrorCount());
        assertEquals("loginForm", loginController.processSubmit(loginFormBean, result, model, new SimpleSessionStatus()));
        assertEquals("The content of the email input field should not be null!", wrongEmailStr, loginFormBean.getEmailAddress());
        assertEquals("The content of the password input field should not be null!", pwStr, loginFormBean.getPassword());
        assertEquals(1, model.size());
        assertTrue(model.containsKey("loginForm"));
        assertEquals(1, result.getErrorCount());
        assertEquals("Incorrect login data!", result.getAllErrors().get(0).getDefaultMessage());
        //3. test case: correct login data
        String emailStr = "test@email.com";
        pwStr = "test";
        loginFormBean.setEmailAddress(emailStr);
        loginFormBean.setPassword(pwStr);
        model.put("loginForm", loginFormBean);
        result = new BeanPropertyBindingResult(loginFormBean, "test");
        assertEquals(0, result.getErrorCount());
        assertEquals("loginSuccessPage", loginController.processSubmit(loginFormBean, result, model, new SimpleSessionStatus()));
        assertEquals(1, model.size());
        assertTrue(model.containsKey("loginForm"));
        assertEquals(0, result.getErrorCount());
        LoginForm modelLoginFormObj = (LoginForm) model.get("loginForm");
        assertEquals("The content of the email input field should not be null!", emailStr, modelLoginFormObj.getEmailAddress());
        assertEquals("The content of the password input field should not be null!", pwStr, modelLoginFormObj.getPassword());
    }

    class SubmitterDAOTestImpl implements SubmitterDAO {

        public SubmitterDAOTestImpl() {
        }

        @Override
        public Submitter getSubmitterById(long submitterId) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void deleteSubmitter(Submitter submitter) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Submitter getSubmitterByEmailAddress(String emailAddress) {
            if (emailAddress != null && emailAddress.equals("test@email.com")) {
                return new Submitter("test", "test", "test@email.com", "test");
            }
            return null;
        }

        public boolean isDatabaseAlive() {
            return true;
        }

        @Override
        public List<Submitter> getSubmitters() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}