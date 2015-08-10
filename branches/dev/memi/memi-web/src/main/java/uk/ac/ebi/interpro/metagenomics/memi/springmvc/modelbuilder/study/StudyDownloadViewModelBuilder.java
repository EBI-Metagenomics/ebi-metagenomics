package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.controller.studies.StudySummaryFile;
import uk.ac.ebi.interpro.metagenomics.memi.controller.studies.StudySummaryFileFilter;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileExistenceChecker;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadLink;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.DownloadSection;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.DownloadViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.AbstractViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.io.File;
import java.util.*;

/**
 * Model builder class for {@link DownloadViewModel}.
 * <p/>
 * See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public class StudyDownloadViewModelBuilder extends AbstractViewModelBuilder<DownloadViewModel> {

    private final static Log log = LogFactory.getLog(StudyDownloadViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private Study study;

    private Map<String, DownloadableFileDefinition> fileDefinitionsMap;

    public StudyDownloadViewModelBuilder(SessionManager sessionMgr,
                                         String pageTitle,
                                         List<Breadcrumb> breadcrumbs,
                                         MemiPropertyContainer propertyContainer,
                                         Map<String, DownloadableFileDefinition> fileDefinitionsMap,
                                         Study study) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.study = study;
        this.fileDefinitionsMap = fileDefinitionsMap;
    }

    public DownloadViewModel getModel() {
        if (log.isInfoEnabled()) {
            log.info("Building instance of " + DownloadViewModel.class + "...");
        }
        final Map<String, DownloadSection> downloadSectionMap = buildDownloadSection(fileDefinitionsMap, study);
        return new DownloadViewModel(getSessionSubmitter(sessionMgr), pageTitle, breadcrumbs, propertyContainer, downloadSectionMap, study);
    }

    private Map<String, DownloadSection> buildDownloadSection(final Map<String, DownloadableFileDefinition> fileDefinitionsMap,
                                                              final Study study) {
        final Map<String, DownloadSection> downloadSectionMap = new HashMap<String, DownloadSection>();

        final String rootPath = propertyContainer.getPathToAnalysisDirectory();
        final String resultDirectoryAbsolute = rootPath + study.getResultDirectory();
        final File rootDir = new File(resultDirectoryAbsolute);

        if (!FileExistenceChecker.checkFileExistence(rootDir)) {
            throw new IllegalStateException("Result directory for study " + study.getStudyId() + " not found");
        }

        final File v2 = new File(resultDirectoryAbsolute + File.separator + "version_2.0" + File.separator + "project-summary");
        if (FileExistenceChecker.checkFileExistence(v2)) {
            final DownloadSection downloadLinks = getDownloadLinks(rootDir, study.getStudyId(), "2.0");
            if (downloadLinks != null && (downloadLinks.getFuncAnalysisDownloadLinks().size() > 0 || downloadLinks.getTaxaAnalysisDownloadLinks().size() > 0)) {
                downloadSectionMap.put("2.0", downloadLinks);
            }
        }

        final File v1 = new File(resultDirectoryAbsolute + File.separator + "version_1.0" + File.separator + "project-summary");
        if (FileExistenceChecker.checkFileExistence(v1)) {
            final DownloadSection downloadLinks = getDownloadLinks(rootDir, study.getStudyId(), "1.0");
            if (downloadLinks != null && (downloadLinks.getFuncAnalysisDownloadLinks().size() > 0 || downloadLinks.getTaxaAnalysisDownloadLinks().size() > 0)) {
                downloadSectionMap.put("1.0", downloadLinks);
            }
        }

        return downloadSectionMap;
    }

    public static DownloadSection getDownloadLinks(final File resultDirectoryAbsolute, final String studyId, final String version) {
        // Check location exists and is a directory

        if (resultDirectoryAbsolute == null || studyId == null || version == null) {
            return null;
        }

        final File summaryFilesDir = new File(resultDirectoryAbsolute + File.separator + "version_" + version + File.separator + "project-summary");
        if (FileExistenceChecker.checkFileExistence(summaryFilesDir) && summaryFilesDir.isDirectory()) {

            // Build list of download links (only include files with one of the expected names)
            final List<DownloadLink> funcDownloadLinks = new ArrayList<DownloadLink>();
            final List<DownloadLink> taxaDownloadLinks = new ArrayList<DownloadLink>();

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

                if (studySummaryFile.getCategory().equalsIgnoreCase("func")) {
                    funcDownloadLinks.add(new DownloadLink(filename,
                            studySummaryFile.getDescription(),
                            "projects/" + studyId + "/download/" + version + "/export?contentType=text&amp;exportValue=" + studySummaryFile.getFilename(),
                            true,
                            studySummaryFile.getFileOrder(),
                            getFileSize(file)));
                } else if (studySummaryFile.getCategory().equalsIgnoreCase("taxa")) {
                    taxaDownloadLinks.add(new DownloadLink(filename,
                            studySummaryFile.getDescription(),
                            "projects/" + studyId + "/download/" + version + "/export?contentType=text&amp;exportValue=" + studySummaryFile.getFilename(),
                            true,
                            studySummaryFile.getFileOrder(),
                            getFileSize(file)));
                } else {
                    log.warn("Project summary file did not have an expected category, it had: " + studySummaryFile.getCategory());
                }
            }
            Collections.sort(funcDownloadLinks, DownloadLink.DownloadLinkComparator);
            Collections.sort(taxaDownloadLinks, DownloadLink.DownloadLinkComparator);
            return new DownloadSection(funcDownloadLinks, taxaDownloadLinks);
        }
        return null;
    }

    private static String getFileSize(final File file) {
        if (file.canRead()) {
            long fileLength = file.length();
            long cutoff = 1024 * 1024;
            //If file size is bigger than 1MB
            if (fileLength > cutoff) {
                long fileSize = fileLength / (long) (1024 * 1024);
                return fileSize + " MB";
            } else {
                //If file size is bigger than 1KB
                if (fileLength > 1024) {
                    long fileSize = fileLength / (long) 1024;
                    return fileSize + " KB";
                } else {
                    return fileLength + " bytes";
                }
            }
        }
        return "";
    }
}