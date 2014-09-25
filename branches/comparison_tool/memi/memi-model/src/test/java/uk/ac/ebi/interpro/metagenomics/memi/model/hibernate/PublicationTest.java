package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.*;

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

    @Test
    public void testHashCode() {
        Publication pub1 = new Publication(PublicationType.PUBLICATION, "isbn1", "volumn1", 2001, "title1", "authors1", "doi1");
        Publication pub2 = new Publication(PublicationType.PUBLICATION, "isbn1", "volumn1", 2001, "title1", "authors1", "doi1");
        Publication pub3 = new Publication(PublicationType.WEBSITE_LINK, "isbn3", "volumn3", 2003, "title3", "authors3", "doi3");
        //
        Set<Publication> publications = new HashSet<Publication>();
        assertNotNull(publications);
        assertEquals(0, publications.size());
        //
        publications.add(pub1);
        assertEquals(1, publications.size());
        assertTrue(publications.contains(pub1));
        //
        publications.add(pub2);
        assertEquals(1, publications.size());
        assertTrue(publications.contains(pub2));
        assertTrue(publications.contains(pub1));
        //
        publications.add(pub3);
        assertEquals(2, publications.size());
        assertTrue(publications.contains(pub3));
    }
}