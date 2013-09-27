package uk.ac.ebi.interpro.metagenomics.memi.services;

import java.io.File;

public final class FileExistenceChecker {

    public static boolean checkFileExistence(final File file) {
        if (file != null && file.exists()) {
            return true;
        }
        return false;
    }
}