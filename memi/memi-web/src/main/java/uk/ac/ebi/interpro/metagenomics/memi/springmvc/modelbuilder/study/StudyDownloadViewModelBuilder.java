package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.controller.studies.StudySummaryFile;
import uk.ac.ebi.interpro.metagenomics.memi.controller.studies.StudySummaryFileFilter;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.PipelineReleaseDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineRelease;
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

    private PipelineReleaseDAO pipelineReleaseDAO;

    public StudyDownloadViewModelBuilder(SessionManager sessionMgr,
                                         String pageTitle,
                                         List<Breadcrumb> breadcrumbs,
                                         MemiPropertyContainer propertyContainer,
                                         Map<String, DownloadableFileDefinition> fileDefinitionsMap,
                                         Study study, PipelineReleaseDAO pipelineReleaseDAO) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.study = study;
        this.fileDefinitionsMap = fileDefinitionsMap;
        this.pipelineReleaseDAO = pipelineReleaseDAO;
    }

    public DownloadViewModel getModel() {
        if (log.isInfoEnabled()) {
            log.info("Building instance of " + DownloadViewModel.class + "...");
        }
        final SortedMap<String, DownloadSection> downloadSectionMap = buildDownloadSection(fileDefinitionsMap, study);
        return new DownloadViewModel(getSessionSubmitter(sessionMgr), getEbiSearchForm(sessionMgr), pageTitle, breadcrumbs, propertyContainer, downloadSectionMap, study);
    }

    private SortedMap<String, DownloadSection> buildDownloadSection(final Map<String, DownloadableFileDefinition> fileDefinitionsMap,
                                                                    final Study study) {
        final Map<String, DownloadSection> downloadSectionMap = new HashMap<String, DownloadSection>();

        final String rootPath = propertyContainer.getPathToAnalysisDirectory();
        final String resultDirectoryAbsolute = rootPath + study.getResultDirectory();
        final File rootDir = new File(resultDirectoryAbsolute);

        if (rootDir == null) {
            log.error("Result directory for study " + study.getStudyId() + " not found");
            return null;
        } else if (!FileExistenceChecker.checkFileExistence(rootDir)) {
            log.error("Result directory for study " + study.getStudyId() + " not found: " + rootDir.getAbsolutePath());
            return null;
        }

        //Get all pipeline releases and iterate over them
        List<PipelineRelease> pipelines = pipelineReleaseDAO.retrieveAll();
        for (PipelineRelease pipeline : pipelines) {
            String releaseVersion = pipeline.getReleaseVersion();
            final File results = new File(resultDirectoryAbsolute + File.separator + "version_" + releaseVersion + File.separator + "project-summary");
            if (FileExistenceChecker.checkFileExistence(results)) {
                final DownloadSection downloadLinks = getDownloadLinks(rootDir, study.getStudyId(), releaseVersion);
                if (downloadLinks != null && (downloadLinks.getFuncAnalysisDownloadLinks().size() > 0 || downloadLinks.getTaxaAnalysisDownloadLinks().size() > 0)) {
                    downloadSectionMap.put(releaseVersion, downloadLinks);
                }
            }
        }

        // Sort the map with keys ordered by pipeline version number string (descending order)
        SortedMap<String, DownloadSection> sortedDownloadSectionMap = new TreeMap<String, DownloadSection>(
                new Comparator<String>() {

                    @Override
                    public int compare(String o1, String o2) {
                        // TODO This comparator may need improving to cope with some future pipeline version number strings
                        return o2.compareTo(o1); // Reverse order
                    }

                });
        sortedDownloadSectionMap.putAll(downloadSectionMap);
        return sortedDownloadSectionMap;
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