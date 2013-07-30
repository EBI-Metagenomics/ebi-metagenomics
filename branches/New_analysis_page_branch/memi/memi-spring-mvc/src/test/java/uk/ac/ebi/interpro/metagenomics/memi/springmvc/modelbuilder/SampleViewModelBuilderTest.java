package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewPagination;

import static junit.framework.Assert.*;

/**
 * JUnit test for {@link SampleViewModelBuilder}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SampleViewModelBuilderTest {

    /**
     * Tests if replaceAll method works as expected.
     */
    @Test
    public void testEncodeSingleQuoteMarks() {
        String testStr = "2'-3'-beta-blah";
        String expected = "2\\\'-3\\\'-beta-blah";
        assertEquals("Result string differs from the expected one!", expected, SampleViewModelBuilder.encodeSingleQuoteMarks(testStr));
    }
}
