package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AnalysisResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a simple model object which is used to render the sample analysis page, more specifically the functional analysis tab.
 * The InterPro matches data is used to render the InterPro matches table and pie charts.
 *
 * @author Maxim Scheremetjew
 */
public class FunctionalAnalysisResult extends AnalysisResult {

    private List<InterProEntry> interProEntryList;

    private int totalReadsCount;

    private GoTermSection goTermSection;


    public FunctionalAnalysisResult() {
        this(new ArrayList<InterProEntry>(0), 0);
    }

    public FunctionalAnalysisResult(List<InterProEntry> interProEntryList, int totalReadsCount) {
        this.interProEntryList = interProEntryList;
        this.totalReadsCount = totalReadsCount;
    }

    public List<InterProEntry> getInterProEntryList() {
        return interProEntryList;
    }

    public int getTotalReadsCount() {
        return totalReadsCount;
    }

    public GoTermSection getGoTermSection() {
        return goTermSection;
    }

    public void setGoTermSection(GoTermSection goTermSection) {
        this.goTermSection = goTermSection;
    }
}
