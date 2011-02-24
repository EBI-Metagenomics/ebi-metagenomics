package uk.ac.ebi.interpro.metagenomics.memi.basic;

/**
 * Represents a container class which holds common machine specific variables like class paths to downloadable
 * files.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class MemiPropertyContainer {

    public String classPathToStatsFile;

    public String classPathToAnalysisDirectory;

    MemiPropertyContainer() {
    }

    public String getClassPathToStatsFile() {
        return classPathToStatsFile;
    }

    public void setClassPathToStatsFile(String classPathToStatsFile) {
        this.classPathToStatsFile = classPathToStatsFile;
    }

    public String getClassPathToAnalysisDirectory() {
        return classPathToAnalysisDirectory;
    }

    public void setClassPathToAnalysisDirectory(String classPathToAnalysisDirectory) {
        this.classPathToAnalysisDirectory = classPathToAnalysisDirectory;
    }
}
