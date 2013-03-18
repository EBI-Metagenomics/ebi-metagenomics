package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class FilePathNameBuilder {

    private static final String SUB_FILE_PATH_NAME_BIOM_RESULT_FILE = "/otus/otu_table.biom";

    private static final String FILE_NAME_ENDING_TAX_RESULT_FILE = "_rRNAFiltered.fasta_rep_set_tax_assignments.txt";

    private static final String SUB_DIR_PATH_NAME_TAX_RESULT_FILE = "/otus/rdp_assigned_taxonomy/";

    /**
     * Creates possible result file objects and checks existence. Only file objects which do exist will be added to the result set.
     *
     * @param emgFile
     * @param propertyContainer
     * @return
     */
    public static List<File> createListOfDownloadableFiles(final EmgFile emgFile,
                                                           final MemiPropertyContainer propertyContainer) {
        List<File> results = new ArrayList<File>();

        //Create and check result file objects which are part of the genomic and transcriptomic analysis.
        final String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
        final String rootDirPathName = propertyContainer.getPathToAnalysisDirectory() + directoryName;
        for (EmgFile.ResultFileType fileExtension : EmgFile.ResultFileType.values()) {
            File file = new File(rootDirPathName + '/' + directoryName + fileExtension.getFileNameEnd());
            if (file.exists()) {
                results.add(file);
            }
        }
        //Create and check result file objects which are part of the 16s amplicon taxonomy analysis
        //1. File with .biom extension (community standard format)
        File biomFile = new File(rootDirPathName + SUB_FILE_PATH_NAME_BIOM_RESULT_FILE);
        if (biomFile.exists()) {
            results.add(biomFile);
        }
        //2. Human readable file with .txt extension
        File humanReadableFile = new File(rootDirPathName + SUB_DIR_PATH_NAME_TAX_RESULT_FILE + directoryName + FILE_NAME_ENDING_TAX_RESULT_FILE);
        if (biomFile.exists()) {
            results.add(humanReadableFile);
        }
        return results;
    }

    /**
     * Return absolute file path name for the specified result file type. Result file types are can be find here:{@link EmgFile.ResultFileType}.
     *
     * @param resultFileType    {@link EmgFile.ResultFileType}
     * @param emgFile
     * @param propertyContainer
     * @return
     */
    public static String getFilePathNameByResultType(final EmgFile.ResultFileType resultFileType,
                                                     final EmgFile emgFile,
                                                     final MemiPropertyContainer propertyContainer) {
        String directoryName = emgFile.getFileIDInUpperCase().replace('.', '_');
        final String rootDirPathName = propertyContainer.getPathToAnalysisDirectory() + directoryName;
        switch (resultFileType) {
            case BIOM:
                return rootDirPathName + SUB_FILE_PATH_NAME_BIOM_RESULT_FILE;
            case TAB_SEPARATED_TAX_RESULT_FILE:
                return rootDirPathName + SUB_DIR_PATH_NAME_TAX_RESULT_FILE + directoryName + FILE_NAME_ENDING_TAX_RESULT_FILE;
            default:
                return rootDirPathName + '/' + directoryName + resultFileType.getFileNameEnd();
        }
    }
}