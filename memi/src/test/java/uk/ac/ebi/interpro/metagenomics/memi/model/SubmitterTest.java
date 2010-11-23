package uk.ac.ebi.interpro.metagenomics.memi.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link uk.ac.ebi.interpro.metagenomics.memi.model.Submitter}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SubmitterTest {
    private Submitter submitter;

    @Before
    public void setUp() throws Exception {
        submitter = new Submitter("firstName", "surname", "test@mail.com", "password");
    }

    @Test
    public void testInitialization() {
        assertEquals("firstName", submitter.getFirstName());
        assertEquals("surname", submitter.getSurname());
        assertEquals("test@mail.com", submitter.getEmailAddress());
        assertEquals("password", submitter.getPassword());
    }

    /**
     * Tests not all but the most important setter and getter methods.
     */
    @Test
    public void testSetterAndGetter() {
        submitter.setFirstName("newFirstName");
        assertEquals("newFirstName", submitter.getFirstName());
        submitter.setSurname("newSurname");
        assertEquals("newSurname", submitter.getSurname());
        submitter.setEmailAddress("newTest@mail.com");
        assertEquals("newTest@mail.com", submitter.getEmailAddress());
        submitter.setPassword("newPassword");
        assertEquals("newPassword", submitter.getPassword());
    }
}