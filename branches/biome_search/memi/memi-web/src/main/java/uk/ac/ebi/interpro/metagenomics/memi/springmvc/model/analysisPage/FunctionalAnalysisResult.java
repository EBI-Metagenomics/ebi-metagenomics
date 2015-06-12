package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.ArrayList;

/**
 * Represents a simple model object which is used to render the sample analysis page, more specifically the functional analysis tab.
 * The InterPro matches data is used to render the InterPro matches table and pie charts.
 *
 * @author Maxim Scheremetjew
 */
public class FunctionalAnalysisResult {

    private InterProMatchesSection interProMatchesSection;

    private GoTermSection goTermSection;


    public FunctionalAnalysisResult() {
        this(new InterProMatchesSection(new ArrayList<InterProEntry>(0), new ArrayList<InterProEntry>(0), 0));
    }

    public FunctionalAnalysisResult(InterProMatchesSection interProMatchesSection) {
        this.interProMatchesSection = interProMatchesSection;
        this.goTermSection = new GoTermSection();
    }

    public InterProMatchesSection getInterProMatchesSection() {
        return interProMatchesSection;
    }

    public void setInterProMatchesSection(InterProMatchesSection interProMatchesSection) {
        this.interProMatchesSection = interProMatchesSection;
    }

    public GoTermSection getGoTermSection() {
        return goTermSection;
    }

    public void setGoTermSection(GoTermSection goTermSection) {
        this.goTermSection = goTermSection;
    }
}
