package uk.ac.ebi.interpro.metagenomics.memi.controller;


import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgLogFileInfoDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.ISampleStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class extends {@link SampleViewController}, {@link KronaChartsController} and {@link SampleViewExportController}.
 */
public class AbstractSampleViewController extends SecuredAbstractController<Sample> {
    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "sample";

    @Resource
    protected SampleDAO sampleDAO;

    @Resource
    protected EmgLogFileInfoDAO fileInfoDAO;

    @Resource
    private MemiDownloadService downloadService;

    ISampleStudyDAO<Sample> getDAO() {
        return sampleDAO;
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (entity != null && entity instanceof Sample) {
            result.add(new Breadcrumb("Project: " + ((Sample) entity).getStudy().getStudyName(), "View project " + ((Sample) entity).getStudy().getStudyName(), StudyViewController.VIEW_NAME + '/' + ((Sample) entity).getStudy().getStudyId()));
            result.add(new Breadcrumb("Sample: " + ((Sample) entity).getSampleName(), "View sample " + ((Sample) entity).getSampleName(), SampleViewController.VIEW_NAME + '/' + ((Sample) entity).getSampleId()));
            result.add(new Breadcrumb("Analysis Results", "View analysis results", VIEW_NAME + '/' + ((Sample) entity).getSampleId()));
        }
        return result;
    }

    protected EmgFile getEmgFile(final long sampleId) {
        List<EmgFile> emgFiles = fileInfoDAO.getFilesBySampleId(sampleId);
        return (emgFiles.size() > 0 ? emgFiles.get(0) : null);
    }

    protected void createFileObjectAndOpenDownloadDialog(final HttpServletResponse response,
                                                         final HttpServletRequest request,
                                                         final EmgFile emgFile,
                                                         final String fileNameEnd,
                                                         final String filePathName) {
        File file = new File(filePathName);
        if (downloadService != null) {
            //white spaces are replaced by underscores
            final String fileNameForDownload = getFileName(emgFile, fileNameEnd);
            downloadService.openDownloadDialog(response, request, file, fileNameForDownload, false);
        }
    }

    private String getFileName(final EmgFile emgFile,
                               final String fileNameEnd) {
        return emgFile.getFileName().replace(" ", "_") + fileNameEnd;
    }
}
