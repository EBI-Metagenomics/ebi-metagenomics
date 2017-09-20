package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter for expected study summary filenames only.
 */
public class StudySummaryFileFilter implements FileFilter {
    public boolean accept(File file) {
        String filename = file.getName();
        return !filename.equals("diversity-sample.tsv") && StudySummaryFile.contains(filename);
    }
}
