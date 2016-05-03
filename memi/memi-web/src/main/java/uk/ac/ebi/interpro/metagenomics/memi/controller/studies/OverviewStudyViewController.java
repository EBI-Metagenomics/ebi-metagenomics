package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.controller.MGPortalURLCollection;
import uk.ac.ebi.interpro.metagenomics.memi.controller.ModelProcessingStrategy;
import uk.ac.ebi.interpro.metagenomics.memi.core.VelocityTemplateWriter;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.files.MemiFileWriter;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.OverviewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study.StudyViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.study.OverviewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;


/**
 * The controller for the analysis results page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Controller
public class OverviewStudyViewController extends AbstractStudyViewController {

    private static final Log log = LogFactory.getLog(OverviewStudyViewController.class);
    private final String VELOCITY_TEMPLATE_LOCATION_PATH = "exportStudy.vm";

    /* The maximum allowed number of characters per column within the study list table*/
    private final int MAX_CHARS_PER_COLUMN = 35;


    @Resource
    private RunDAO runDAO;

    @Resource
    private BiomeDAO biomeDAO;

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;


    protected String getModelViewName() {
        return "tabs/study/overview";
    }

    @Resource
    private SubmissionContactDAO submissionContactDAO;

    private ModelProcessingStrategy<Study> createNewModelProcessingStrategy() {
        return new ModelProcessingStrategy<Study>() {
            @Override
            public void processModel(ModelMap model, Study study) {
                log.info("Building overview model...");
                populateModel(model, study);
            }
        };
    }


    /**
     * Request method for the overview tab on the project page.
     *
     * @throws java.io.IOException
     */
    @RequestMapping(value = MGPortalURLCollection.PROJECT_OVERVIEW)
    public ModelAndView ajaxLoadOverviewTab(@PathVariable final String studyId,
                                            final ModelMap model) {
        return checkAccessAndBuildModel(createNewModelProcessingStrategy(), model, getSecuredEntity(studyId), getModelViewName());
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_OVERVIEW + "/runs")
    public
    @ResponseBody
    String ajaxLoadRunData(@PathVariable final String studyId) throws JsonIOException {
        List<QueryRunsForProjectResult> runs = getProjectRuns(studyId);

        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.add("data", gson.toJsonTree(runs));
//        System.out.println("Size of the json: " + jsonResponse.toString());
        return jsonResponse.toString();
    }

    @RequestMapping(value = MGPortalURLCollection.PROJECT_OVERVIEW + "/doExport")
    public ModelAndView doExportStudy(@PathVariable final String studyId,
                                      HttpServletRequest request,
                                      HttpServletResponse response,
                                      final ModelMap model) {
        log.info("doExportStudy");
        List<QueryRunsForProjectResult> runs = getProjectRuns(studyId);

        if (runs != null && runs.size() > 0) {
            List<String> columnNames = new ArrayList<>();
            columnNames.add("Sample Name");
            columnNames.add("Sample ID");
            columnNames.add("Run ID");
            columnNames.add("Experiment type");
            columnNames.add("Release version");

            //Create velocity spring_model
            Map<String, Object> velocityModel = new HashMap<String, Object>();
            velocityModel.put("tableHeaderNames", columnNames);
            velocityModel.put("runs", runs);
            velocityModel.put("columnLength", MAX_CHARS_PER_COLUMN);
            //Create file content
            String fileContent = VelocityTemplateWriter.createFileContent(velocityEngine, VELOCITY_TEMPLATE_LOCATION_PATH, velocityModel);
            File file = MemiFileWriter.writeCSVFile(fileContent);
            String fileName = MemiTools.createFileName("mg_project_summary_");
            if (file != null && file.canRead()) {
                downloadService.openDownloadDialog(response, request, file, fileName, true);
            }
        }

        return null;
    }

    protected List<QueryRunsForProjectResult> getProjectRuns(String studyId) {
        List<QueryRunsForProjectResult> runs = new LinkedList<QueryRunsForProjectResult>();
        Study study = getSecuredEntity(studyId);
        if (study != null) {
            long projectId = study.getId();

            Submitter submitter = getSessionSubmitter();
            if (submitter == null) {
                runs = runDAO.retrieveRunsByProjectId(projectId, true);
            } else {
                //Check if submitter is study owner
                if (submitter.getSubmissionAccountId().equalsIgnoreCase(study.getSubmissionAccountId())) {
                    runs = runDAO.retrieveRunsByProjectId(projectId, false);
                } else {
                    runs = runDAO.retrieveRunsByProjectId(projectId, true);
                }
            }
        }
        return runs;
    }

    /**
     * Creates the analysis page model and adds it to the specified model map.
     */
    protected void populateModel(final ModelMap model, final Study study) {
        // Assign biome CSS class to the study
        MemiTools.assignBiomeIconCSSClass(study, biomeDAO);
        String pageTitle = "Project: " + study.getStudyName() + "";
        final ViewModelBuilder<OverviewModel> builder = new OverviewModelBuilder(userManager, getEbiSearchForm(),
                pageTitle, getBreadcrumbs(study), propertyContainer, study, runDAO);
        final OverviewModel overviewModel = builder.getModel();
        overviewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_PROJECTS_VIEW);
        model.addAttribute(StudyViewModel.MODEL_ATTR_NAME, overviewModel);
    }
}