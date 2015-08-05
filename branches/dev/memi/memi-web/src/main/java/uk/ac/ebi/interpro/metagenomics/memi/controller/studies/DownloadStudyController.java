package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.MGPortalURLCollection;
import uk.ac.ebi.interpro.metagenomics.memi.controller.ModelProcessingStrategy;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.AnalysisJobDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadLink;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Download tab on the project page.
 */
@Controller
public class DownloadStudyController extends AbstractStudyViewController {

    private static final Log log = LogFactory.getLog(DownloadStudyController.class);

    @Resource
    protected AnalysisJobDAO analysisJobDAO;

    protected String getModelViewName() {
        return "tabs/study/download";
    }

    private ModelProcessingStrategy<Study> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Study>() {
            @Override
            public void processModel(ModelMap model, Study study) {
                log.info("Building download view model...");
                populateModel(model, study);
            }
        };
    }

    /**
     * Request method for the download tab on the sample view page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_DOWNLOAD)
    public ModelAndView ajaxLoadDownloadTab(@PathVariable final String studyId,
                                            final ModelMap model) {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(studyId), getModelViewName());
    }

    protected void populateModel(final ModelMap model, final Study study) {

        model.addAttribute("study", study);

        final String rootPath = propertyContainer.getPathToAnalysisDirectory();
        System.out.println("TEMP DEBUG1: " + rootPath);
        final String resultDirectoryAbsolute = rootPath + study.getResultDirectory();
        System.out.println("TEMP DEBUG2: " + resultDirectoryAbsolute);

        if (!FileExistenceChecker.checkFileExistence(new File(resultDirectoryAbsolute))) {
            throw new IllegalStateException("Result directory for study " + study.getStudyId() + " not found");
        }

        final File v1 = new File(resultDirectoryAbsolute + File.separator + "version_1.0" + File.separator + "project-summary");
        if (FileExistenceChecker.checkFileExistence(v1)) {
            final List<DownloadLink> downloadLinks = getDownloadLinks(v1);
            if (downloadLinks != null && downloadLinks.size() > 0) {
                model.addAttribute("downloadLinksV1", downloadLinks);
            }
        }

        final File v2 = new File(resultDirectoryAbsolute + File.separator + "version_2.0" + File.separator + "project-summary");
        if (FileExistenceChecker.checkFileExistence(v2)) {
            final List<DownloadLink> downloadLinks = getDownloadLinks(v2);
            if (downloadLinks != null && downloadLinks.size() > 0) {
                model.addAttribute("downloadLinksV2", downloadLinks);
            }
        }
    }

    public static List<DownloadLink> getDownloadLinks(final File summaryFilesDir) {
        // Check location exists and is a directory

        if (summaryFilesDir == null) {
            //throw new IllegalStateException("Does not exist or is not a directory: NULL");
            return null;
        }
        if (!summaryFilesDir.isDirectory()) {
            //throw new IllegalStateException("Does not exist or is not a directory: " + summaryFilesDir.getAbsolutePath());
            return null;
        }

        // Build list of download links (only include files with one of the expected names)
        List<DownloadLink> downloadLinks = new ArrayList<DownloadLink>();
        File[] files = summaryFilesDir.listFiles(new StudySummaryFileFilter());
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (!file.isFile()) {
                throw new IllegalStateException("Does not exist or is not a file: " + file.getAbsolutePath());
            }

            final String filename = file.getName();
            StudySummaryFile studySummaryFile = StudySummaryFile.lookupFromFilename(filename);
            if (studySummaryFile == null) {
                throw new IllegalStateException("Could not lookup study summary file details: " + filename);
            }

            final String fileAbsolutePath = file.getAbsolutePath();

            downloadLinks.add(new DownloadLink(filename,
                    studySummaryFile.getDescription(),
                    fileAbsolutePath,
                    true,
                    studySummaryFile.getFileOrder()));
        }
        Collections.sort(downloadLinks, DownloadLink.DownloadLinkComparator);
        return downloadLinks;
    }
}
