package uk.ac.ebi.interpro.metagenomics.memi.services;

import java.io.File;

public final class FileExistenceChecker {

    /**
     * Check if a file or directory exists (and is not hidden).
     * Also checks if file is empty (this may not work for some files;depends on the encoding).
     */
    public static boolean checkFileExistence(final File file) {
        if (file != null && file.exists() && !file.isHidden()) {
            if (file.isFile() && file.length() == 0) {
                return false;
            }
            return true; // Directories on Windows machines have length zero
        }
        return false;
    }
}