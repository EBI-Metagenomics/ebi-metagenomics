package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.HomePageViewModelBuilder;

import java.util.ArrayList;

/**
 * Represents the JUnit test for {@link HomePageViewModelBuilder}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 */
public class HomePageModelBuilderTest {
    private HomePageViewModelBuilder modelBuilder;

    @Before
    public void setUp() throws Exception {
        modelBuilder = new HomePageViewModelBuilder(null, null, null, null, null, null, null, null, null);
    }

    @Test
    public void testAssignBiomeIconFeatures() {
        //test that it doesn't throw a null pointer exception
        modelBuilder.assignBiomeIconFeatures(null);

        modelBuilder.assignBiomeIconFeatures(new ArrayList<Study>());
    }
}