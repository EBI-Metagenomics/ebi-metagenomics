package uk.ac.ebi.interpro.metagenomics.memi.services;

import java.io.File;

public final class FileExistenceChecker {

    /**
     * Also checks if file is empty (this may not work for some files;depends on the encoding).
     */
    public static boolean checkFileExistence(final File file) {
        if (file != null && file.exists() && file.length() != 0) {
            return true;
        }
        return false;
    }
}