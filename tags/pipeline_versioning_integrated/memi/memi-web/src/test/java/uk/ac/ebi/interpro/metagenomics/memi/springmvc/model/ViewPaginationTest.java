package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * JUnit test for the pagination.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ViewPaginationTest {

    private ViewPagination instance;

    @Before
    public void setUp() throws Exception {
        instance = null;
    }

    @Test
    public void testPagination() {
        instance = new ViewPagination(0, 66, 10);
        assertEquals(0, instance.getStartPosition());
        assertEquals(10, instance.getPageSize());
        assertEquals(66, instance.getTotalItems());
        assertEquals(10, instance.getNextStartPos());
        assertEquals(0, instance.getPreviousStartPos());
        assertFalse(instance.isExistPreviousStartPos());
        assertTrue(instance.isExistNextStartPos());
        assertEquals("1 - 10", instance.getDisplayedItemRange());
        assertEquals(60, instance.getLastLinkPosition());
        //2nd test instance
        instance = new ViewPagination(61, 66, 10);
        assertEquals(61, instance.getStartPosition());
        assertEquals(10, instance.getPageSize());
        assertEquals(66, instance.getTotalItems());
        assertEquals(61, instance.getNextStartPos());
        assertEquals(51, instance.getPreviousStartPos());
        assertTrue(instance.isExistPreviousStartPos());
        assertFalse(instance.isExistNextStartPos());
        assertEquals("62 - 66", instance.getDisplayedItemRange());
        assertEquals(60, instance.getLastLinkPosition());
        //3rd test instance
        instance = new ViewPagination(10, 66, 5);
        assertEquals(10, instance.getStartPosition());
        assertEquals(5, instance.getPageSize());
        assertEquals(66, instance.getTotalItems());
        assertEquals(15, instance.getNextStartPos());
        assertEquals(5, instance.getPreviousStartPos());
        assertTrue(instance.isExistPreviousStartPos());
        assertTrue(instance.isExistNextStartPos());
        assertEquals("11 - 15", instance.getDisplayedItemRange());
        assertEquals(65, instance.getLastLinkPosition());
        //4th test instance
        instance = new ViewPagination(10, 66, 4);
        assertEquals(10, instance.getStartPosition());
        assertEquals(4, instance.getPageSize());
        assertEquals(66, instance.getTotalItems());
        assertEquals(14, instance.getNextStartPos());
        assertEquals(6, instance.getPreviousStartPos());
        assertTrue(instance.isExistPreviousStartPos());
        assertTrue(instance.isExistNextStartPos());
        assertEquals("11 - 14", instance.getDisplayedItemRange());
        assertEquals(64, instance.getLastLinkPosition());
        //4th test instance
        instance = new ViewPagination(10, 17, 3);
        assertEquals(10, instance.getStartPosition());
        assertEquals(3, instance.getPageSize());
        assertEquals(17, instance.getTotalItems());
        assertEquals(13, instance.getNextStartPos());
        assertEquals(7, instance.getPreviousStartPos());
        assertTrue(instance.isExistPreviousStartPos());
        assertTrue(instance.isExistNextStartPos());
        assertEquals("11 - 13", instance.getDisplayedItemRange());
        assertEquals(15, instance.getLastLinkPosition());
    }
}
