package uk.ac.ebi.interpro.metagenomics.memi.core;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FileDefinitionId;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.IResultFileDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a container class which holds common machine specific variables like the class paths to
 * downloadable files. The {@link MemiPropertyContainer} is provided by the bean container and can simple
 * be used by using the Resource annotation. Please use maven profiles to specify variables (like class paths).
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class MemiPropertyContainer {

    private String pathToAnalysisDirectory;

    private ENASubmissionURL enaSubmissionURL;

    //TODO: Check if deprecated and remove if so
    private String enaMasterUserEmail;

    /* Map of result file definitions. */
    private Map<FileDefinitionId, IResultFileDefinition> resultFileDefinitionMap = new HashMap<FileDefinitionId, IResultFileDefinition>();

    MemiPropertyContainer() {
    }

    public String getPathToAnalysisDirectory() {
        return pathToAnalysisDirectory;
    }

    public void setPathToAnalysisDirectory(String pathToAnalysisDirectory) {
        this.pathToAnalysisDirectory = pathToAnalysisDirectory;
    }

    public ENASubmissionURL getEnaSubmissionURL() {
        return enaSubmissionURL;
    }

    public void setEnaSubmissionURL(ENASubmissionURL enaSubmissionURL) {
        this.enaSubmissionURL = enaSubmissionURL;
    }

    public String getEnaMasterUserEmail() {
        return enaMasterUserEmail;
    }

    public void setEnaMasterUserEmail(String enaMasterUserEmail) {
        this.enaMasterUserEmail = enaMasterUserEmail;
    }

    public IResultFileDefinition getResultFileDefinition(FileDefinitionId fileDefinitionId) {
        return resultFileDefinitionMap.get(fileDefinitionId);
    }

    public void setResultFileDefinitionMap(Map<FileDefinitionId, IResultFileDefinition> resultFileDefinitionMap) {
        this.resultFileDefinitionMap = resultFileDefinitionMap;
    }
}