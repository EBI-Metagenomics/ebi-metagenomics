package uk.ac.ebi.interpro.metagenomics.memi.basic;

/**
 * Represents a container class which holds common machine specific variables like the class paths to
 * downloadable files. The {@link MemiPropertyContainer} is provided by the bean container and can simple
 * be used by using the Resource annotation.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class MemiPropertyContainer {

    public String classPathToAnalysisDirectory;

    MemiPropertyContainer() {
    }

    public String getClassPathToAnalysisDirectory() {
        return classPathToAnalysisDirectory;
    }

    public void setClassPathToAnalysisDirectory(String classPathToAnalysisDirectory) {
        this.classPathToAnalysisDirectory = classPathToAnalysisDirectory;
    }
}
