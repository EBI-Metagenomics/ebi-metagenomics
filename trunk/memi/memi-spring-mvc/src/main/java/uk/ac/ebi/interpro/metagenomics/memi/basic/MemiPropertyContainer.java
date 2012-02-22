package uk.ac.ebi.interpro.metagenomics.memi.basic;

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

    private String pathToSubmissionDirectory;

    private ENASubmissionURL enaSubmissionURL;

    private String enaMasterUserEmail;

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

    public String getPathToSubmissionDirectory() {
        return pathToSubmissionDirectory;
    }

    public void setPathToSubmissionDirectory(String pathToSubmissionDirectory) {
        this.pathToSubmissionDirectory = pathToSubmissionDirectory;
    }
}