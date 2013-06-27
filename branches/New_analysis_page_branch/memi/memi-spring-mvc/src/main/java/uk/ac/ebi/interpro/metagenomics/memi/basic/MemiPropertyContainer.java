package uk.ac.ebi.interpro.metagenomics.memi.basic;

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

    private String enaMasterUserEmail;

    /**
     * Maps string identifiers and file names (like Krona chart file names).
     * e.g.
     * <p/>
     * TAXONOMY_CHART_FULL_VERSION->Krona_chart_taxonomy.html
     * TAXONOMY_CHART_SLIM_VERSION->Krona_chart_taxonomy_simple.html
     */
    private Map<String, String> resultFileNameMap = new HashMap<String, String>();

    /**
     * Collection of identifiers which is used for the Krona charts file name map.
     */
    public enum FileNameIdentifier {
        TAXONOMY_CHART_FULL_VERSION, TAXONOMY_CHART_SLIM_VERSION, FUNCTION_CHART_FULL_VERSION, FUNCTION_CHART_SLIM_VERSION,PHYLUM_COUNTS;
    }

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

    public void setResultFileNameMap(Map<String, String> resultFileNameMap) {
        this.resultFileNameMap = resultFileNameMap;
    }

    public String getResultFileName(FileNameIdentifier identifier) {
        return resultFileNameMap.get(identifier.name());
    }
}