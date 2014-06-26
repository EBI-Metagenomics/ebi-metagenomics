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
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.ComparisonForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the controller for the MG portal home page.
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
    protected Map<String, DownloadableFileDefinition> fileDefinitionsMap;

    @Override
    public ModelAndView doGet(ModelMap model) {
        return buildModelAndView(
                getModelViewName(),
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Compare samples of same project", getBreadcrumbs(null), propertyContainer);
                        final ViewModel defaultViewModel = builder.getModel();
                        defaultViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_COMPARE_VIEW);

                        // Retrieving list of public studies and samples + add attributes
                        List<Study> studyList = studyDAO.retrieveOrderedPublicStudies("studyName", false);
                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                        model.addAttribute("studies", studyList);
                        model.addAttribute("comparisonForm", new ComparisonForm());
                    }
                });
    }

    @RequestMapping(method = RequestMethod.POST)
    //TODO: Either throw an exception OR use a try-catch block, but I wouldn't recommend using both
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
                            final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Compare samples of same project", getBreadcrumbs(null), propertyContainer);
                            final ViewModel defaultViewModel = builder.getModel();
                            defaultViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_COMPARE_VIEW);
                            // Retrieving list of public studies and samples + add attributes
                            List<Study> studyList = studyDAO.retrieveOrderedPublicStudies("studyName", false);
                            model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                            model.addAttribute("studies", studyList);
                        }
                    });
        }
        //Get form input data
        final String usedData = comparisonForm.getUsedData();
        final List<Long> allSamples = comparisonForm.getSamples();

        //Get input file paths, which are used to generate abundance table
        final List<String> inputFilePaths = getInputFilePaths(usedData, allSamples);

        // Samples instead of retrieved ID.
        List<Sample> sampleList = new ArrayList<Sample>();
        List<String> sampleTextId = new ArrayList<String>();
        for (int i = 0; i < allSamples.size(); i++) {
            sampleList.add(sampleDAO.read(allSamples.get(i)));
            if (comparisonForm.isKeepNames())
                sampleTextId.add("S" + String.format("%02d", i + 1) + "(" + sampleList.get(i).getSampleId() + ")");
            else
                sampleTextId.add("Sample" + String.format("%02d", i + 1));
        }

        // Print working directory, just to see what's happening
        log.info("Creating abundance table and visualisations...");
        //TODO: We should make all these directories configurable in a Spring context file (working dir, temp dir, path to the script etc.)
        log.info("Working Directory = " + System.getProperty("user.dir"));
        final String rTmpFileDirectory = propertyContainer.getROutputDir();
        //absolute path of the R script
        final String rScriptPath = propertyContainer.getRScriptLocation();
        final String rInstallationLocation = propertyContainer.getRInstallationLocation();
        //Check if R script exists and if it is executable
        doCheckScriptFile(rScriptPath);

        // Creation of 'R friendly' String objects containing : absolute paths to the files used by the R script, correct samples names if we want to keep them, heatmap parameters.
        String rFriendlyFileList = inputFilePaths.toString().replace("[", "").replace("]", "").replace(", ", ",");
        String rFriendlySampleNames = sampleTextId.toString().replace("[", "").replace("]", "").replace(", ", ",");
        String hmPar = comparisonForm.isHmLegend() + "," + comparisonForm.getHmClust() + "," + comparisonForm.getHmDendrogram();

        // Creation of unique file name/id used during the whole procedure
        final String uniqueOutputName = usedData + "_" + System.currentTimeMillis();

        // First try: run shell script with test files (so without using form data)

        final char WHITESPACE = ' ';
        String executionCommand;
        executionCommand = rInstallationLocation + WHITESPACE + rScriptPath + WHITESPACE + uniqueOutputName + WHITESPACE + rFriendlyFileList +
                WHITESPACE + comparisonForm.getUsedData() + WHITESPACE + rFriendlySampleNames + WHITESPACE + comparisonForm.getStackThreshold() +
                WHITESPACE + hmPar + " " + comparisonForm.getGOnumber();
//            executionCommand = "Rscript R/simple.R";
        // Print the command we will use to see if it's correct (format / order of parameters)
        log.info("Running the following R command to generate abundance table and visualisations:\n" + executionCommand);

        // use the Runtime exec method:
        Process p = Runtime.getRuntime().exec(executionCommand);
        //Other method ? Not working...Process p = Runtime.getRuntime().exec("R CMD BATCH --no-save --no-restore '--args "+rFriendlyFileList+" "+comparisonForm.getUsedData()+" "+comparisonForm.isKeepNames()+" "+abundanceTableName+" 0' R/launch.R output.out");

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));

        // read the output from the command
        System.out.println("Command output:\n");
        String stdOutput = null;
        while ((stdOutput = stdInput.readLine()) != null) {
            System.out.println(stdOutput);
        }

        // read any errors from the attempted command
        System.out.println("Command error (if any):\n");
        String stdErrorOuput = null;
        while ((stdErrorOuput = stdError.readLine()) != null) {
            System.out.println(stdErrorOuput);
        }
        //Throw exception if stdError output (from running R) isn't NULL
        if (stdErrorOuput != null) {
            throw new IllegalStateException("R has thrown an exception:\n" + stdErrorOuput);
        }

        //Consume abundance file and render the page
        // We have HTML parts (different files), it would be cool to store it as a string array.
        String tmpGraphDir = rTmpFileDirectory;
        final String[] htmlFile = {
                ReadFile(tmpGraphDir + uniqueOutputName + "_overview.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_bar.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_stack.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_hm.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_pca.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_table.htm")
        };


        ModelAndView mav = new ModelAndView("/compareResult");
        mav.addObject("graphCode", htmlFile);
        // Result Header elements
        mav.addObject("study", studyDAO.read(Long.valueOf(comparisonForm.getStudy())));
        mav.addObject("samples", sampleList);
        mav.addObject("data", usedData);
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
     * @param usedData
     * @param allSamples
     * @return List of input file paths.
     */
    private List<String> getInputFilePaths(String usedData, List<Long> allSamples) {
        List<String> inputFilePaths = new ArrayList<String>();
        for (Long sampleId : allSamples) {
            final EmgFile emgFile = getEmgFile(sampleId);

            if (emgFile != null) {
                // If statements depending on the nature of the data type chosen by the user
                DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.INTERPRO_MATCHES_SUMMARY_FILE.name());
                if (usedData.equals("GO"))
                    fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_COMPLETE_FILE.name());
                if (usedData.equals("GOslim"))
                    fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_SLIM_FILE.name());
                File fileObject = FileObjectBuilder.createFileObject(emgFile, propertyContainer, fileDefinition);
                String absoluteFilePath = fileObject.getAbsolutePath();
                inputFilePaths.add(absoluteFilePath);
            }
        }
        return inputFilePaths;
    }

    @RequestMapping(value = "/samples")
    public ModelAndView getSamplesByID(
            @RequestParam(value = "studyId", required = true) final long studyId
    ) {
        ModelAndView mav = new ModelAndView("/compareSamples");
        List<Sample> sampleListForId = sampleDAO.retrievePublicSamplesByStudyId(studyId);
        mav.addObject("samples", sampleListForId);
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

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Comparison tool", "Compare samples of same project", VIEW_NAME));
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
            // Remove the temporary files, we don't want to keep them once they are displayed
            //boolean success = (new File(fileName)).delete();
            //if (success) {
            //    System.out.println("Temporary file deleted (" + fileName + ")");
            //}
        }

    }
}