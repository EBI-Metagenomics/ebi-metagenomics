package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgLogFileInfoDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.ISampleStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AnalysisStatsModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller for analysis overview page.
 *
 * @author Phil Jones, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping('/' + AnalysisStatsController.VIEW_NAME + "/{sampleId}")
public class AnalysisStatsController extends SecuredAbstractController<Sample> {
    private static final Log log = LogFactory.getLog(AnalysisStatsController.class);
    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "analysisStatsView";

    @Resource
    private HibernateSampleDAO sampleDAO;

    @Resource
    private EmgLogFileInfoDAO fileInfoDAO;

    @Resource
    private MemiDownloadService downloadService;

    @Resource
    private MemiPropertyContainer propertyContainer;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGetSample(final ModelMap model, @PathVariable final String sampleId) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building model...");
                populateModel(model, sample);
            }
        }, model, sampleId);
    }

    @RequestMapping(value = "/doExportResultFile/{fileName}", method = RequestMethod.GET)
    public ModelAndView doExportResultFile(@PathVariable final String sampleId, @PathVariable final String fileName, ModelMap model, final HttpServletResponse response) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building model...");
                populateModel(model, sample);
//
                String downloadPath = propertyContainer.getPathToAnalysisDirectory() + fileName + "/";
                if (downloadPath.contains("_I5")) {
                    downloadPath = downloadPath.replace("_I5", "");
                } else {
                    downloadPath = downloadPath.replace("_orf100_200_nameonly", "");
                }
                //Open file stream and download dialog
                String fileExtension = ".fasta";
                if (fileName.contains("I5"))
                    fileExtension = ".tsv";
                String pathName = downloadPath + fileName + fileExtension;
                File file = new File(pathName);
                if (downloadService != null) {
                    downloadService.openDownloadDialog(response, file, fileName + fileExtension, false);
                }
            }
        }, model, sampleId);
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     */
    private void populateModel(final ModelMap model, final Sample sample) {
        String pageTitle = "Results " + sample.getSampleTitle();
        final AnalysisStatsModel mgModel = MGModelFactory.getAnalysisStatsModel(sessionManager, sample, propertyContainer.getPathToAnalysisDirectory(), pageTitle, getBreadcrumbs(sample));
        model.addAttribute(MGModel.MODEL_ATTR_NAME, mgModel);
    }

    @ModelAttribute(value = "resultFileNames")
    public List<String> populateI5FileNames(@PathVariable String sampleId) {
        List<String> result = new ArrayList<String>();
        if (sampleId.equals("Sample_place_holder1")) {
            String fileName = "wheat_rhizosphere_ME.fasta";
            fileName = fileName.replace('.', '_').toUpperCase();
            String downloadPath = propertyContainer.getPathToAnalysisDirectory() + fileName + "/";
            String pathName = downloadPath + fileName + "_I5.tsv";
            File file = new File(pathName);
            if (file.exists() && file.canRead()) {
                result.add(fileName + "_I5.tsv");
            }
            pathName = downloadPath + fileName + "_I5.tsv";
            file = new File(pathName);
            if (file.exists() && file.canRead()) {
                result.add(fileName + "_orf100_200_nameonly.fasta");
            }
        } else {
            List<String> fileNames = fileInfoDAO.getFileNamesBySampleId(sampleId);
            for (String fileName : fileNames) {
                fileName = fileName.trim();
                if (fileName.length() > 0) {
                    fileName = fileName.replace('.', '_').toUpperCase();

                    //Check if files exist and show only if they are existing
                    String downloadPath = propertyContainer.getPathToAnalysisDirectory() + fileName + "/";
                    String pathName = downloadPath + fileName + "_I5.tsv";
                    File file = new File(pathName);
                    if (file.exists() && file.canRead()) {
                        result.add(fileName + "_I5.tsv");
                    }
                    pathName = downloadPath + fileName + "_I5.tsv";
                    file = new File(pathName);
                    if (file.exists() && file.canRead()) {
                        result.add(fileName + "_orf100_200_nameonly.fasta");
                    }
                }
            }
        }
        return result;
    }

    @Override
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
            result.add(new Breadcrumb("Sample: " + ((Sample) entity).getSampleTitle(), "View sample " + ((Sample) entity).getSampleTitle(), SampleViewController.VIEW_NAME + '/' + ((Sample) entity).getSampleId()));
            result.add(new Breadcrumb("Analysis Results", "View analysis results", VIEW_NAME + '/' + ((Sample) entity).getSampleId()));
        }
        return result;
    }
}
