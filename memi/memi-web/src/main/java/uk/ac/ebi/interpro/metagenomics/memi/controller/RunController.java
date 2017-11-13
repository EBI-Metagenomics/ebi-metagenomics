package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDAO;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Represents a controller for the run endpoint.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class RunController {
    private final Log log = LogFactory.getLog(RunController.class);

    @Resource
    private RunDAO runDAO;

    /**
     * Temporary re-directions.
     */
    @RequestMapping(value = "/runs/{runId}")
    public String doGetStudy(@PathVariable final String runId) {
        Map<String, Object> runEntry = runDAO.retrieveStudyAndSampleAccessions(runId);
        String version = (String) runEntry.get("pipelineId");
        String sampleId = (String) runEntry.get("sampleId");
        String studyId = (String) runEntry.get("studyId");
        return "redirect:/projects/" + studyId + "/samples/" + sampleId + "/runs/" + runId + "/results/versions/" + version;
    }
}