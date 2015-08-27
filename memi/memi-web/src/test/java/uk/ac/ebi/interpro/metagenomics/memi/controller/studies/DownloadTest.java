package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadLink;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.DownloadSection;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study.StudyDownloadViewModelBuilder;

import java.io.File;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners
public class DownloadTest {

    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void testDownloadLinks() {

        // Setup test input directory
        final String testResourceLocation = "uk/ac/ebi/interpro/metagenomics/memi/controller/studies/summary/ERP1";
        final String extraFile = "extra-file.txt";
        final File summaryFilesDir = new File(classLoader.getResource(testResourceLocation).getFile());

        // Get links
        final DownloadSection downloadSection = StudyDownloadViewModelBuilder.getDownloadLinks(summaryFilesDir, "ERP1", "1.0");

        // Check results
        Assert.assertNotNull(downloadSection);
        List<DownloadLink> funcDownloadLinks = downloadSection.getFuncAnalysisDownloadLinks();
        Assert.assertNotNull(funcDownloadLinks);
        Assert.assertEquals(3, funcDownloadLinks.size());
        for (DownloadLink link : funcDownloadLinks) {
            if (link.getLinkText().equals(extraFile)) {
                // This extra file from the directory should not be included in the list as it is not an expected summary file
                Assert.fail("Unexpected file in download list: " + testResourceLocation + File.separator + extraFile);
            }
        }
        List<DownloadLink> taxaDownloadLinks = downloadSection.getTaxaAnalysisDownloadLinks();
        Assert.assertNotNull(taxaDownloadLinks);
        Assert.assertEquals(2, taxaDownloadLinks.size());
        for (DownloadLink link : taxaDownloadLinks) {
            if (link.getLinkText().equals(extraFile)) {
                // This extra file from the directory should not be included in the list as it is not an expected summary file
                Assert.fail("Unexpected file in download list: " + testResourceLocation + File.separator + extraFile);
            }
        }

    }

    public void testDownloadLinksNotAvailable() {
        // Test input directory does not exist
        DownloadSection downloadSection = StudyDownloadViewModelBuilder.getDownloadLinks(null, null, null);
        Assert.assertNull(downloadSection);
    }

}
