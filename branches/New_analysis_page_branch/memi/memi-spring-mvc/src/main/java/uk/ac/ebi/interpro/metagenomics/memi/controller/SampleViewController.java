package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.services.FileObjectBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * The controller for analysis overview page.
 *
 * @author Phil Jones, EMBL-EBI, InterPro
 * @author Maxim Scheremetjew
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping('/' + SampleViewController.VIEW_NAME + "/{sampleId}")
public class SampleViewController extends AbstractSampleViewController {
    private static final Log log = LogFactory.getLog(SampleViewController.class);

    @Resource
    private MemiDownloadService downloadService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGetSample(@PathVariable final String sampleId,
                                    final ModelMap model,
                                    final HttpServletResponse response,
                                    final HttpServletRequest request) throws IOException {
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Building model...");
                populateModel(model, sample);
            }
        }, model, sampleId, getModelViewName());
    }

    @RequestMapping(value = "/accessDenied")
    public ModelAndView doGetAccessDeniedPage(@PathVariable final String sampleId) {
        return buildAccessDeniedModelAndView(sampleId);
    }

    @RequestMapping(value = "/doExportGOSlimFile", method = RequestMethod.GET)
    public ModelAndView doExportGOSlimFile(@PathVariable final String sampleId,
                                           final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_SLIM_FILE.name());
        return handleExport(sampleId, response, request, fileDefinition);
    }

    @RequestMapping(value = "/doExportGOFile", method = RequestMethod.GET)
    public ModelAndView doExportGOFile(@PathVariable final String sampleId,
                                       final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.GO_COMPLETE_FILE.name());
        return handleExport(sampleId, response, request, fileDefinition);
    }

    @RequestMapping(value = "/doExportMaskedFASTAFile", method = RequestMethod.GET)
    public ModelAndView doExportMaskedFASTAFile(@PathVariable final String sampleId,
                                                final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.MASKED_FASTA.name());
        return handleExport(sampleId, response, request, fileDefinition);
    }

    @RequestMapping(value = "/doExportCDSFile", method = RequestMethod.GET)
    public ModelAndView doExportCDSFile(@PathVariable final String sampleId,
                                        final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.PREDICTED_CDS_FILE.name());
        return handleExport(sampleId, response, request, fileDefinition);
    }

    @RequestMapping(value = "/doExportReadsWithCDSFile", method = RequestMethod.GET)
    public ModelAndView doExportReadsWithCDSFile(@PathVariable final String sampleId,
                                        final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.READS_WITH_PREDICTED_CDS_FILE.name());
        return handleExport(sampleId, response, request, fileDefinition);
    }

    @RequestMapping(value = "/doExportI5TSVFile", method = RequestMethod.GET)
    public ModelAndView doExportI5File(@PathVariable final String sampleId,
                                       final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.INTERPROSCAN_RESULT_FILE.name());
        return handleExport(sampleId, response, request, fileDefinition);
    }

//    @RequestMapping(value = "/doExportIPRFile", method = RequestMethod.GET)
//    public ModelAndView doExportIPRFile(@PathVariable final String sampleId,
//                                        final HttpServletResponse response, final HttpServletRequest request) {
//        return handleExport(sampleId, response, request, EmgFile.ResultFileType.IPR, "_InterPro_sum.csv");
//    }

    @RequestMapping(value = "/doExportIPRhitsFile", method = RequestMethod.GET)
    public ModelAndView doExportIPRhitsFile(@PathVariable final String sampleId,
                                            final HttpServletResponse response, final HttpServletRequest request) {
        DownloadableFileDefinition fileDefinition = fileDefinitionsMap.get(FileDefinitionId.PREDICTED_CDS_WITH_INTERPRO_MATCHES_FILE.name());
        return handleExport(sampleId, response, request, fileDefinition);
    }

    /**
     * @param sampleId
     * @param response
     * @param request
     * @return
     */
    private ModelAndView handleExport(final String sampleId, final HttpServletResponse response,
                                      final HttpServletRequest request, final DownloadableFileDefinition fileDefinition) {
        log.info("Checking if sample is accessible...");
        return checkAccessAndBuildModel(new ModelProcessingStrategy<Sample>() {
            @Override
            public void processModel(ModelMap model, Sample sample) {
                log.info("Open download dialog...");
                final EmgFile emgFile = getEmgFile(sample.getId());
                if (emgFile != null) {
                    File fileObject = FileObjectBuilder.createFileObject(emgFile, propertyContainer, fileDefinition);
                    openDownloadDialog(response, request, emgFile, fileDefinition.getDownloadName(), fileObject);
                }
            }
        }, null, sampleId, getModelViewName());
    }


    /**
     * Creates the analysis page model and adds it to the specified model map.
     */
    protected void populateModel(final ModelMap model, final Sample sample) {
        String pageTitle = "Sample analysis results: " + sample.getSampleName() + "";
        populateModel(model, sample, pageTitle);
    }


    /**
     * Example for pattern '000000.000':<br>
     * 123.78  000000.000  000123.780
     */
    private String getCustomFormat(String pattern, double value) {
        DecimalFormat myFormatter = new DecimalFormat(pattern);
        return myFormatter.format(value);
    }
}
