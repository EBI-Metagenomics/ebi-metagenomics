package uk.ac.ebi.interpro.metagenomics.memi.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@link uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
//TODO: Refactor
public class SubmitterTest {
    private Submitter submitter;

    @Before
    public void setUp() throws Exception {
        submitter = new Submitter("firstName", "surname", "test@mail.com");
    }

    @Test
    @Ignore
    public void testInitialization() {
        assertEquals("firstName", submitter.getFirstName());
        assertEquals("surname", submitter.getSurname());
        assertEquals("test@mail.com", submitter.getEmailAddress());
    }

    /**
     * Tests not all but the most important setter and getter methods.
     */
    @Test
    @Ignore
    public void testSetterAndGetter() {
        submitter.setFirstName("newFirstName");
        assertEquals("newFirstName", submitter.getFirstName());
        submitter.setSurname("newSurname");
        assertEquals("newSurname", submitter.getSurname());
        submitter.setEmailAddress("newTest@mail.com");
        assertEquals("newTest@mail.com", submitter.getEmailAddress());
    }
}