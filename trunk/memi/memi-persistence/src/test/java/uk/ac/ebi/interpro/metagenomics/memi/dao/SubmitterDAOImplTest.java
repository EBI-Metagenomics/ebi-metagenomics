package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Represents a unit test for {@link uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAOImpl}. This test queries
 * the real Oracle database. Pay attention: If you run these test methods outside the EBI, they will fail (then better
 * annotate the test methods with @Ignore).
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration()
public class SubmitterDAOImplTest {

    @Resource
    private SubmitterDAO submitterDAO;


    @Test
    @Ignore("Requires accessible Oracle database.")
    public void testGetSubmitterByEmailAddress() {
        assertNotNull("Something went wrong during the initialization of the data access object for the submitter!" +
                "Please note, if you run this test outside the EBI, it will fail (then better " +
                " annotate this method with @Ignore", submitterDAO);
        Submitter submitter = null;
        submitter = submitterDAO.getSubmitterByEmailAddress("");
        assertNull(submitter);
        submitter = submitterDAO.getSubmitterByEmailAddress("TEST");
        assertNotNull(submitter);
//      Check that all properties which should be set are correct 
        assertEquals(50, submitter.getSubmitterId());
        assertTrue(submitter.getFirstName().equals("test"));
        assertTrue(submitter.getSurname().equals("test"));
        assertTrue(submitter.getPassword().equals("test"));
        assertTrue(submitter.getEmailAddress().equals("TEST"));
//      Check all other  
        assertNull(submitter.getAddress());
        assertEquals(0, submitter.getActive());
        assertNull(submitter.getAuditTime());
        assertNull(submitter.getAuditUser());
        assertNull(submitter.getCountry());
        assertNull(submitter.getFax());
        assertNull(submitter.getMiddleInitials());
        assertNull(submitter.getTelNO());
    }
}
