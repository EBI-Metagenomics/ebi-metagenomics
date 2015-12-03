package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.*;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Represents the unit test for the {@link CompareController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class SubmissionConsentCheckControllerTest {

    @Test
    public void testCookieValueObjectConstructorSingleParameter() {
        //input test value
        final String userName = "1234";
        final boolean isRegistered = true;
        final boolean isConsentGiven = false;
        final String separated = "|";
        final String cookieValue = userName + separated + String.valueOf(isRegistered) + separated + String.valueOf(isConsentGiven);
        //Call test method
        CookieValueObject cookieValueObject = new CookieValueObject(cookieValue);
        //Test object attributes
        assertEquals("Webin-1234", cookieValueObject.getUserName());
        assertEquals(isRegistered, cookieValueObject.isRegistered());
        assertEquals(isConsentGiven, cookieValueObject.isConsentGiven());
        //Test invalid cookie values
        String invalidCookieValue = userName + separated + String.valueOf(isRegistered);
        //Call test method
        cookieValueObject = new CookieValueObject(invalidCookieValue);
        //Test object attributes
        assertEquals(null, cookieValueObject.getUserName());
        assertEquals(false, cookieValueObject.isRegistered());
        assertEquals(false, cookieValueObject.isConsentGiven());
        //second
        invalidCookieValue = separated + String.valueOf(isRegistered) + separated + String.valueOf(isConsentGiven);
        //Call test method
        cookieValueObject = new CookieValueObject(invalidCookieValue);
        //Test object attributes
        assertEquals(null, cookieValueObject.getUserName());
        assertEquals(false, cookieValueObject.isRegistered());
        assertEquals(false, cookieValueObject.isConsentGiven());
    }

    @Test
    public void testCookieValueObjectConstructorMultipleParameters() {
        //input test value
        final String userName = "Webin-1234";
        final boolean isRegistered = true;
        final boolean isConsentGiven = false;
        //Call test method
        CookieValueObject cookieValueObject = new CookieValueObject(userName, isRegistered, isConsentGiven);
        //Test object attributes
        assertEquals(userName, cookieValueObject.getUserName());
        assertEquals(isRegistered, cookieValueObject.isRegistered());
        assertEquals(isConsentGiven, cookieValueObject.isConsentGiven());
    }

    @Test
    public void testToStringMethod() {
        //input test value
        final String userName = "Webin-1234";
        final boolean isRegistered = true;
        final boolean isConsentGiven = false;
        //Call test method
        CookieValueObject cookieValueObject = new CookieValueObject(userName, isRegistered, isConsentGiven);
        assertEquals("1234|true|false", cookieValueObject.toString());
    }
}