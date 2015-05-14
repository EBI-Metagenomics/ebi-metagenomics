package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadLink;

import java.io.File;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class DownloadTest {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void testDownloadLinks() {

        // Setup test input directory
        final String testResourceLocation = "uk/ac/ebi/interpro/metagenomics/memi/controller/studies/summary";
        final String extraFile = "extra-file.txt";
        final File summaryFilesDir = new File(classLoader.getResource(testResourceLocation).getFile());

        // Get links
        final List<DownloadLink> links = DownloadStudyController.getDownloadLinks(summaryFilesDir);

        // Check results
        Assert.assertNotNull(links);
        Assert.assertEquals(11, links.size());

        for (DownloadLink link : links) {
            if (link.getLinkText().equals(extraFile)) {
                // This extra file from the directory should not be included in the list as it is not an expected summary file
                Assert.fail("Unexpected file in download list: " + testResourceLocation + "/" + extraFile);
            }
        }
    }

}
