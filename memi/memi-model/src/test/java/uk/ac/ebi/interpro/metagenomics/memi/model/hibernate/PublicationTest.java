package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

/**
 * Test class for {@link uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class PublicationTest {

    private Publication publication;

    private String authors;

    @Before
    public void setUp() {
        this.publication = new Publication();
        this.authors = "Frias-Lopez J., Shi Y., Tyson GW., Coleman ML., Schuster SC., Chisholm SW., Delong EF.";
    }

    @Test
    public void testGetNthOccurrence() {
        int pos = publication.getNthOccurrence(authors, ',', 1);
        assertEquals(',', authors.charAt(pos));
        assertEquals(14, pos);
        //
        pos = publication.getNthOccurrence(authors, ',', 2);
        assertEquals(',', authors.charAt(pos));
        assertEquals(22, pos);
        //
        pos = publication.getNthOccurrence(authors, ',', 5);
        assertEquals(',', authors.charAt(pos));
        assertEquals(60, pos);
        //
        pos = publication.getNthOccurrence("Frias-Lopez J.", ',', 1);
        assertEquals(-1, pos);
        //
        pos = publication.getNthOccurrence("", ',', 1);
        assertEquals(-1, pos);
    }

    @Test
    public void testGetShortAuthors() {
        publication.setAuthors(authors);
        assertNotNull("Authors shouldn't be NULL!", publication.getAuthors());
        assertEquals(authors, publication.getAuthors());
        String expected = "Frias-Lopez J., Shi Y., Tyson GW., Coleman ML., Schuster SC.";
        assertEquals(expected, publication.getShortAuthors());
        //
        expected = "Frias-Lopez J., Shi Y., Tyson GW.";
        publication.setAuthors(expected);
        assertEquals(expected, publication.getAuthors());
        assertEquals(expected, publication.getShortAuthors());
        //
        publication.setAuthors(null);
        assertNull(publication.getAuthors());
        assertNull(publication.getShortAuthors());
    }
}