package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.AnalysisJobDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.ComparisonForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.CompareViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.CompareViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.*;
import java.util.*;

/**
 * Represents the controller for the comparison tool.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @author Francois Bucchini, trainee@EMBL-EBI
 * @since 1.0-SNAPSHOT
 */
@Controller(value = "compareController")
@RequestMapping('/' + CompareController.VIEW_NAME)
//TODO: Use the logger instead of system out as much as possible (e.g. log.info("some useful message") or debug, warning, error)
public class CompareController extends AbstractController implements IController {

    private final Log log = LogFactory.getLog(CompareController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "compare";


    @Resource
    private SampleDAO sampleDAO;

    @Resource
    private StudyDAO studyDAO;

    @Resource
    private AnalysisJobDAO analysisJobDAO;

    @Resource
    protected Map<String, DownloadableFileDefinition> fileDefinitionsMap;

    @Override
    public ModelAndView doGet(ModelMap model) {
        return buildModelAndView(
                getModelViewName(),
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        final ViewModelBuilder<CompareViewModel> builder = new CompareViewModelBuilder(sessionManager, "Compare samples of the same project", getBreadcrumbs(null), propertyContainer, studyDAO);
                        final CompareViewModel compareViewModel = builder.getModel();
                        compareViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_COMPARE_VIEW);

                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, compareViewModel);
                        model.addAttribute("comparisonForm", new ComparisonForm());
                    }
                });
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doPostComparisonToolForm(@Valid @ModelAttribute("comparisonForm") final ComparisonForm comparisonForm,
                                                 BindingResult result,
                                                 ModelMap model) throws IOException {
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        //Build model and view for error page
        if (result.hasErrors()) {
            return buildModelAndView(
                    getModelViewName(),
                    model,
                    new ModelPopulator() {
                        @Override
                        public void populateModel(ModelMap model) {
                            final ViewModelBuilder<CompareViewModel> builder = new CompareViewModelBuilder(sessionManager, "Compare samples of same project", getBreadcrumbs(null), propertyContainer, studyDAO);
                            final ViewModel compareViewModel = builder.getModel();
                            compareViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_COMPARE_VIEW);
                            // Retrieving list of public studies and samples + add attributes
//                            List<Study> studyList = studyDAO.retrieveOrderedPublicStudies("studyName", false);
                            model.addAttribute(ViewModel.MODEL_ATTR_NAME, compareViewModel);
//                            model.addAttribute("studies", studyList);
                            model.addAttribute("comparisonForm", comparisonForm);
                        }
                    });
        }
        //Get form input data
        final String usedData = comparisonForm.getUsedData();
        //Get selected analysis jobs
        final List<Long> analysisJobIds = comparisonForm.getAnalysisJobIds();
        //Get list of analysis job objects
        final List<AnalysisJob> analysisJobs = analysisJobDAO.readByJobIds(analysisJobIds);

        final List<String> inputFilePaths = new ArrayList<String>(analysisJobs.size());
        List<String> runTextId = new ArrayList<String>(analysisJobs.size());
        int i = 0;
        for (AnalysisJob analysisJob : analysisJobs) {
            //first task: Get input file paths, which are used to generate abundance table
            inputFilePaths.add(getInputFilePath(usedData, analysisJob));
            //second task
            if (comparisonForm.isKeepNames())
                runTextId.add(analysisJob.getExternalRunIDs());
            else
                runTextId.add("Sample" + String.format("%02d", i + 1));
            i++;
        }

/*        Uncomment these lines if you plan to handle the 'file is empty' error by showing missing samples on the result page
        // Check if one of those is empty and catching empty files so we can display a nice error message on result page
        // Store missing samples
        final List<Sample> missingSampleList = new ArrayList<Sample>();
        for(int i = 0;i < inputFilePaths.size(); i++) {
            File f = new File(inputFilePaths.get(i));
            if(!f.exists()){
                inputFilePaths.remove(i); // Remove the missing samples from the file paths list
                sampleTextId.remove(i); // ... and the sample identifiers list
                missingSampleList.add(sampleList.get(i));
                sampleList.remove(i);  // ... and the sample list used on the webpage
            }
        }*/

        // Print working directory, just to see what's happening
        log.info("Creating abundance table and visualizations...");
        log.info("Working Directory = " + System.getProperty("user.dir"));

        // These variables have to be defined in the 'session-manager-context.xml' file
        final String rTmpFileDirectory = propertyContainer.getROutputDir(); // Directory to store temporary graphs
        final String rScriptPath = propertyContainer.getRScriptLocation(); // Directory for R scripts
        final String rScriptName = propertyContainer.getRScriptName(); // Name of the script to launch
        final String rImgDirectory = propertyContainer.getRTmpImgDir(); // Directory for images (heatmaps)
        final String rInstallationLocation = propertyContainer.getRInstallationLocation(); // Name of the command to use

        // Check if R script exists and if it is executable
        doCheckScriptFile(rScriptPath);

        // Creation of 'R friendly' String objects containing : absolute paths to the files used by the R script, correct samples names if we want to keep them, heatmap parameters.
        String rFriendlyFileList = inputFilePaths.toString().replace("[", "").replace("]", "").replace(", ", ",");
        final String rFriendlySampleNames = runTextId.toString().replace("[", "").replace("]", "").replace(", ", ",");
        String hmPar = comparisonForm.isHmLegend() + "," + comparisonForm.getHmClust() + "," + comparisonForm.getHmDendrogram();

        // Creation of unique file name/id used during the whole procedure
        final String uniqueOutputName = usedData + "_" + System.currentTimeMillis();

        // Execution command to launch the comparison job
        final char WHITESPACE = ' ';
        String executionCommand;
        executionCommand = rInstallationLocation + WHITESPACE + rScriptPath + "/" + rScriptName + WHITESPACE + rTmpFileDirectory + WHITESPACE +
                rScriptPath + WHITESPACE + rImgDirectory + WHITESPACE + uniqueOutputName + WHITESPACE + rFriendlyFileList + WHITESPACE + comparisonForm.getUsedData() +
                WHITESPACE + rFriendlySampleNames + WHITESPACE + comparisonForm.getStackThreshold() + WHITESPACE + hmPar + WHITESPACE + comparisonForm.getGOnumber();
//            executionCommand = "Rscript R/simple.R";
        // Print the command we will use to see if it's correct (format / order of parameters)
        log.info("Running the following R command to generate abundance table and visualizations:\n" + executionCommand);

        // use the Runtime exec method:
        Process p = Runtime.getRuntime().exec(executionCommand);
        //Other method ? Not working...Process p = Runtime.getRuntime().exec("R CMD BATCH --no-save --no-restore '--args "+rFriendlyFileList+" "+comparisonForm.getUsedData()+" "+comparisonForm.isKeepNames()+" "+abundanceTableName+" 0' R/launch.R output.out");

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));

        // read the output from the command
        log.info("Command output:\n");
        String stdOutput = null;
        while ((stdOutput = stdInput.readLine()) != null) {
            System.out.println(stdOutput);
        }

        // read any errors from the attempted command
        log.info("Command error (if any):\n");
        String stdErrorOuput = null;
        while ((stdErrorOuput = stdError.readLine()) != null) {
            System.out.println(stdErrorOuput);
        }
        // Throw exception if stdError output (from running R) isn't NULL
        if (stdErrorOuput != null) {
            throw new IllegalStateException("R has thrown an exception:\n" + stdErrorOuput);
        }

        // Consume abundance file and render the page
        // We have HTML parts (different files), store them as a string array.
        String tmpGraphDir = rTmpFileDirectory;
        final String[] htmlFile = {
                ReadFile(tmpGraphDir + uniqueOutputName + "_overview.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_bar.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_stack.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_hm.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_pca.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_table.htm")
        };

        ModelAndView mav = buildModelAndView(
                "/compareResult",
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Sample comparison results", getBreadcrumbsForResultPage(), propertyContainer);
                        final ViewModel defaultViewModel = builder.getModel();
                        defaultViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_COMPARE_VIEW);
                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                        model.addAttribute("graphCode", htmlFile);
                        // Result Header elements
                        model.addAttribute("study", studyDAO.read(Long.valueOf(comparisonForm.getStudy())));
                        model.addAttribute("analysisJobs", analysisJobs);
                        // Atribute needed for functions of the result page
                        model.addAttribute("data", usedData);
                        // Enable this if you plan to handle the 'file is empty' error by showing missing samples on the result page
                        //  model.addAttribute("missingSamples", missingSampleList);
                        // Output name is required is you want te enable redrawing of charts. Uncomment to use (still an experimental functionality)
                        // model.addAttribute("outputName",uniqueOutputName);
                    }
                });
        return mav;
    }

    private void doCheckScriptFile(String rScriptPath) {
        File rScriptFile = new File(rScriptPath);
        log.info("Running some checks on the R script before execution (checking existence and whether the application can read the file)...");
        log.info("Checking file:\n" + rScriptFile.getAbsolutePath());
        if (!rScriptFile.canRead()) {
            log.warn("The R script isn't readable! Make sure you set the right permission.");
        } else {
            log.info("Successfully ran all tests.");
        }
    }

    /**
     * Get input file paths, which are used to generate the abundance table.
     *
     * @param usedData - type of data chosen by the user for the comparison
     * @return Map of sample IDs and the corresponding input file paths.
     */
    private Map<AnalysisJob, String> getInputFilePathMap(String usedData, List<AnalysisJob> completeAnalysisJobList) {
        Map<AnalysisJob, String> resultMap = new HashMap<AnalysisJob, String>();
        for (AnalysisJob analysisJob : completeAnalysisJobList) {
            // If statements depending on the nature of the data type chosen by the user
            DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.INTERPRO_MATCHES_SUMMARY_FILE.name());
            if (usedData.equals("GO"))
                fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_COMPLETE_FILE.name());
            if (usedData.equals("GOslim"))
                fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_SLIM_FILE.name());
            File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
            String absoluteFilePath = fileObject.getAbsolutePath();
            resultMap.put(analysisJob, absoluteFilePath);
        }
        return resultMap;
    }

    /**
     * Get input file paths, which are used to generate the abundance table.
     *
     * @param usedData - type of data chosen by the user for the comparison
     * @return List of input file paths.
     */
    private String getInputFilePath(String usedData, AnalysisJob analysisJob) {
        // If statements depending on the nature of the data type chosen by the user
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.INTERPRO_MATCHES_SUMMARY_FILE.name());
        if (usedData.equals("GO"))
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_COMPLETE_FILE.name());
        if (usedData.equals("GOslim"))
            fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_SLIM_FILE.name());
        File fileObject = FileObjectBuilder.createFileObject(analysisJob, propertyContainer, fileDefinition);
        return fileObject.getAbsolutePath();
    }

    @RequestMapping(value = "/samples")
    public ModelAndView getRunsByStudyId(@RequestParam(value = "studyId", required = true)
                                         final long studyId,
                                         @ModelAttribute(value = "comparisonForm")
                                         final ComparisonForm comparisonForm) {
        ModelAndView mav = new ModelAndView("/compareSamples");
        //First: Get all completed analysis jobs for the given study identifier
        List<AnalysisJob> completeAnalysisJobList = new ArrayList<AnalysisJob>();
        List<Sample> sampleList = sampleDAO.retrieveAllSamplesByStudyId(studyId);
        for (Sample sample : sampleList) {
            List<AnalysisJob> analysisJobs = analysisJobDAO.readBySampleId(sample.getId(), AnalysisJob.AnalysisJobStatus.COMPLETED.getStatus());
            completeAnalysisJobList.addAll(analysisJobs);
        }
        // Checking if requested analysis jobs have data for the selected data type on the comparison tool submission page (handling of the 'file is empty' error)
        // If they don't have any data, remove them from the sample list and add them to another list for missing samples
        Map<AnalysisJob, String> sampleToFilePathMap = getInputFilePathMap(comparisonForm.getUsedData(), completeAnalysisJobList);

        List<AnalysisJob> deactivatedAnalysisJobs = new ArrayList<AnalysisJob>();
        List<AnalysisJob> activatedAnalysisJobs = new ArrayList<AnalysisJob>();
        doFileExistenceCheck(sampleToFilePathMap, activatedAnalysisJobs, deactivatedAnalysisJobs);

        mav.addObject("analysisJobs", activatedAnalysisJobs);
        mav.addObject("deactivatedAnalysisJobs", deactivatedAnalysisJobs);
        return mav;
    }

    @RequestMapping(value = "/studies")
    public ModelAndView getStudyDescription(
            @RequestParam(value = "studyId", required = true) final String studyId
    ) {
        ModelAndView mav = new ModelAndView("/compareStudies");
        Study currentStudy = studyDAO.readByStringId(studyId);
        mav.addObject("study", currentStudy);
        return mav;
    }

    protected void doFileExistenceCheck(final Map<AnalysisJob, String> analysisToFilePathMap,
                                        final List<AnalysisJob> activatedAnalysisJob,
                                        final List<AnalysisJob> deactivatedAnalysisJob) {
        if (analysisToFilePathMap == null || activatedAnalysisJob == null || deactivatedAnalysisJob == null) {
            throw new IllegalStateException("Input arguments of this method cannot be NULL!");
        }
        for (AnalysisJob analysisJob : analysisToFilePathMap.keySet()) {
            String filePath = analysisToFilePathMap.get(analysisJob);
            if (!new File(filePath).exists()) {
                deactivatedAnalysisJob.add(analysisJob);
            } else {
                activatedAnalysisJob.add(analysisJob);
            }
        }
    }

/*  THIS FEATURE IS STILL EXPERIMENTAL. I did not have time to write it properly (with good paths etc.).
    However, this works on my local machine and gives the logic to be able to redraw samples. To do so, here are the steps:
    - Save the abundance table
    - Get the ouput ID with JSTL on the result page so you can retrieve the table
    - Do a R script reading the output and the new value to redraw the chart (here the threshold), loading needed packages and call the visualization script.
    - Store the result as html file, read it and replace html of wanted div with ajax method
    @RequestMapping(value = "/stack")
    public ModelAndView getNewStack(
            @RequestParam(value = "outputName", required = true) final String outputName,
            @RequestParam(value = "newThreshold", required = true) final double newThreshold) throws IOException{
        // use the Runtime exec method:
        Process p = Runtime.getRuntime().exec("Rscript R/redrawStack.R "+outputName+" "+newThreshold);

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));

        // read the output from the command
        log.info("Command output:\n");
        String stdOutput = null;
        while ((stdOutput = stdInput.readLine()) != null) {
            System.out.println(stdOutput);
        }

        // read any errors from the attempted command
        log.info("Command error (if any):\n");
        String stdErrorOuput = null;
        while ((stdErrorOuput = stdError.readLine()) != null) {
            System.out.println(stdErrorOuput);
        }
        //Throw exception if stdError output (from running R) isn't NULL
        if (stdErrorOuput != null) {
            throw new IllegalStateException("R has thrown an exception:\n" + stdErrorOuput);
        }

        String tmpGraphDir = "R/tmpGraph/";
        final String stackCode= ReadFile(tmpGraphDir + outputName + "_stack.htm");

        ModelAndView mav = new ModelAndView("/redrawStack");
        mav.addObject("newStackCode", stackCode);
        return mav;
    }
*/

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    /**
     * @return Breadcrumbs for the compare page.
     */
    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Comparison tool", "Compare samples of the same project", VIEW_NAME));
        return result;
    }

    /**
     * @return Breadcrumbs for the compareResult page.
     */
    private List<Breadcrumb> getBreadcrumbsForResultPage() {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Comparison tool", "Compare samples of the same project", VIEW_NAME));
        return result;
    }

    //TODO: Either throw an exception OR use a try-catch block, but I wouldn't recommend using both (Note, if you throw an exception you would have to catch that somewhere else)
    //TODO: The naming convention for method names is that they start with a lower case character
    String ReadFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
//            Remove the temporary files, we don't want to keep them once they are displayed
            boolean success = (new File(fileName)).delete();
            if (success) {
                System.out.println("Temporary file deleted (" + fileName + ")");
            }
        }

    }

    protected List<String> sortAndAdjustSampleNameList(List<String> inputList) {
        Collections.sort(inputList, String.CASE_INSENSITIVE_ORDER);
        final List<String> resultList = new ArrayList<String>();
        String previousListItem = null;
        int counter = 2;
        for (String listItem : inputList) {
            String newListItem = listItem;
            if (newListItem.equals(previousListItem)) {
                newListItem += "_" + counter;
                counter++;
            } else {
                //reset counter
                counter = 2;
            }
            previousListItem = listItem;
            resultList.add(newListItem);
        }
        return resultList;
    }
}