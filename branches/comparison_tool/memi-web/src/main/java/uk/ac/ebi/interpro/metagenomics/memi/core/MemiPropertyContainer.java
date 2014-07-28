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

    private String rScriptLocation;

    private String rScriptName;

    private String rInstallationLocation;

    private String rOutputDir;
    private String rTmpImgDir;

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

    public String getRScriptLocation() {
        return rScriptLocation;
    }

    public void setRScriptLocation(String rScriptLocation) {
        this.rScriptLocation = rScriptLocation;
    }

    public String getRInstallationLocation() {
        return rInstallationLocation;
    }

    public void setRInstallationLocation(String rInstallationLocation) {
        this.rInstallationLocation = rInstallationLocation;
    }

    public String getROutputDir() {
        return rOutputDir;
    }

    public void setROutputDir(String rOutputDir) {
        this.rOutputDir = rOutputDir;
    }

    public String getRScriptName() {
        return rScriptName;
    }

    public void setRScriptName(String rScriptName) {
        this.rScriptName = rScriptName;
    }

    public void setRTmpImgDir(String RTmpImgDir) {
        rTmpImgDir = RTmpImgDir;
    }

    public String getRTmpImgDir() {
        return rTmpImgDir;
    }
}