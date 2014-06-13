package uk.ac.ebi.interpro.metagenomics.memi.controller;

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
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents the controller for the MG portal home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller(value = "compareController")
@RequestMapping('/' + CompareController.VIEW_NAME)
public class CompareController extends AbstractController implements IController {

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
                        List<Study> studyList = studyDAO.retrieveOrderedPublicStudies("studyName",false);
                        model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                        model.addAttribute("studies",studyList);
                        model.addAttribute("comparisonForm",new ComparisonForm());
                    }
                });
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doPostComparisonToolForm(@Valid @ModelAttribute("comparisonForm") final ComparisonForm comparisonForm,
                                                 BindingResult result,
                                                 ModelMap model) throws IOException {
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
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
                            List<Study> studyList = studyDAO.retrieveOrderedPublicStudies("studyName",false);
                            model.addAttribute(ViewModel.MODEL_ATTR_NAME, defaultViewModel);
                            model.addAttribute("studies",studyList);
                            //model.addAttribute("comparisonForm",comparisonForm);
                        }
                    });
        }
        String usedData = comparisonForm.getUsedData();
        List<String> inputFilePaths = new ArrayList<String>();
        for(Long sampleId : comparisonForm.getSamples()) {
            final EmgFile emgFile = getEmgFile(sampleId);

            if (emgFile != null) {
                // If statements depending on the nature of the data type chosen by the user
                DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.INTERPRO_MATCHES_SUMMARY_FILE.name());
                if(usedData.equals("GO"))
                    fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_COMPLETE_FILE.name());
                if(usedData.equals("GOslim"))
                    fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_SLIM_FILE.name());
                File fileObject = FileObjectBuilder.createFileObject(emgFile, propertyContainer, fileDefinition);
                String absoluteFilePath = fileObject.getAbsolutePath();
                inputFilePaths.add(absoluteFilePath);
            }
        }

        // Samples instead of retrieved ID.
        List <Sample> sampleList = new ArrayList<Sample>();
        List<Long> allSamples = comparisonForm.getSamples();
        List<String> sampleTextId = new ArrayList<String>();
        for(int i=0;i<allSamples.size();i++){
            sampleList.add(sampleDAO.read(allSamples.get(i)));
            if(comparisonForm.isKeepNames())
                sampleTextId.add("S" + String.format("%02d", i+1) + "(" + sampleList.get(i).getSampleId() + ")");
            else
                sampleTextId.add("Sample"+String.format("%02d", i+1));
        }

        // Print working directory, just to see what's happening
        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        // Creation of 'R friendly' String objects containing : absolute paths to the files used by the R script, correct samples names if we want to keep them, heatmap parameters.
        String rFriendlyFileList = inputFilePaths.toString().replace("[", "").replace("]", "").replace(", ", ",");
        String rFriendlySampleNames = sampleTextId.toString().replace("[", "").replace("]", "").replace(", ", ",");
        String hmPar = comparisonForm.isHmLegend()+","+comparisonForm.getHmClust()+","+comparisonForm.getHmDendrogram();

        // Creation of unique file name/id used during the whole procedure
            final String uniqueOutputName = usedData+"_"+System.currentTimeMillis();
        // Print the command we will use to see if it's correct (format / order of parameters)
            System.out.println("Rscript R/launch_v8.R "+uniqueOutputName+" "+rFriendlyFileList+" "+comparisonForm.getUsedData()+" "+rFriendlySampleNames+" "+comparisonForm.getStackThreshold()+" "+hmPar);

        // First try: run shell script with test files (so without using form data)
        String s = null;

        try {

            // use the Runtime exec method:
            Process p = Runtime.getRuntime().exec("Rscript R/launch_v8.R "+uniqueOutputName+" "+rFriendlyFileList+" "+comparisonForm.getUsedData()+" "+rFriendlySampleNames+" "+comparisonForm.getStackThreshold()+" "+hmPar);
            //Other method ? Not working...Process p = Runtime.getRuntime().exec("R CMD BATCH --no-save --no-restore '--args "+rFriendlyFileList+" "+comparisonForm.getUsedData()+" "+comparisonForm.isKeepNames()+" "+abundanceTableName+" 0' R/launch.R output.out");

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Command output:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Command error (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

        } catch (IOException e) {
            System.out.println("Exception! What happened? ");
            e.printStackTrace();
        }

        //TODO: 3. Check status of R script (RUNNING or FINISHED)

        //TODO: 4. Consume abundance file and render the page
        // We have HTML parts (different files), it would be cool to store it as a string array.
        String tmpGraphDir = "R/tmpGraph/";
        final String[] htmlFile = {
                ReadFile(tmpGraphDir+uniqueOutputName+"_overview.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_bar.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_stack.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_hm.htm"),
                ReadFile(tmpGraphDir + uniqueOutputName + "_table.htm")
        };


        ModelAndView mav = new ModelAndView("/compareResult");
        mav.addObject("graphCode",htmlFile);
        // Result Header elements
        mav.addObject("study",studyDAO.read(Long.valueOf(comparisonForm.getStudy())));
        mav.addObject("samples",sampleList);
        mav.addObject("data",comparisonForm.getUsedData());
        return mav;
    }

        @RequestMapping(value = "/samples")
    public ModelAndView getSamplesByID(
            @RequestParam(value = "studyId", required = true) final long studyId
    ){
            ModelAndView mav = new ModelAndView("/compareSamples");
        List<Sample> sampleListForId = sampleDAO.retrievePublicSamplesByStudyId(studyId);
        mav.addObject("samples",sampleListForId);
        return mav;
    }

    @RequestMapping(value = "/studies")
    public ModelAndView getStudyDescription(
            @RequestParam(value = "studyId", required = true) final String studyId
    ){
        ModelAndView mav = new ModelAndView("/compareStudies");
        Study currentStudy = studyDAO.readByStringId(studyId);
        mav.addObject("study",currentStudy);
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
            boolean success = (new File (fileName)).delete();
            if(success){
                System.out.println("Temporary file deleted ("+fileName+")");
            }
        }

    }
}