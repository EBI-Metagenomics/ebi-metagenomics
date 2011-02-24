package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgLogFileInfoDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AnalysisStatsModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MGModelFactory;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

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
public class AnalysisStatsController {
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
    private SessionManager sessionManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGetSample(ModelMap model, @PathVariable String sampleId) {
        final Sample sample = sampleDAO.readByStringId(sampleId);
        populateModel(model, sample);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }

    @RequestMapping(value = "/doExportResultFile/{fileName}", method = RequestMethod.GET)
    public ModelAndView doExportResultFile(@PathVariable String fileName, ModelMap model, HttpServletResponse response) {
        //1. Do get file location
        String downloadPath = "/home/maxim/temp_memi_data/analyses/" + fileName + "/";
        if (downloadPath.contains("_I5")) {
            downloadPath = downloadPath.replace("_I5", "");
        } else {
            downloadPath = downloadPath.replace("_orf100_200_nameonly", "");
        }
        //2. Open file stream and download dialog
        String fileExtension = ".fasta";
        if (fileName.contains("I5"))
            fileExtension = ".tsv";
        String pathName = downloadPath + fileName + fileExtension;
        File file = new File(pathName);
        if (downloadService != null) {
            downloadService.openDownloadDialog(response, file, fileName + fileExtension, false);
        }
//        populateModel(model);
//        model.addAttribute(LoginForm.MODEL_ATTR_NAME, ((MGModel) model.get(MGModel.MODEL_ATTR_NAME)).getLoginForm());
        return new ModelAndView(VIEW_NAME, model);
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     */
    private void populateModel(ModelMap model, final Sample sample) {
        final AnalysisStatsModel mgModel = MGModelFactory.getAnalysisStatsModel(sessionManager, sample);
        model.addAttribute(MGModel.MODEL_ATTR_NAME, mgModel);
    }

    @ModelAttribute(value = "resultFileNames")
    public List<String> populateI5FileNames(@PathVariable String sampleId) {
        List<String> result = new ArrayList<String>();
        if (sampleId.equals("Sample_place_holder1")) {
            String fileName = "wheat_rhizosphere_ME.fasta";
            fileName = fileName.replace('.', '_').toUpperCase();
            String downloadPath = "/home/maxim/temp_memi_data/analyses/" + fileName + "/";
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
                    String downloadPath = "/home/maxim/temp_memi_data/analyses/" + fileName + "/";
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
}
