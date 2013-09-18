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

    private InterProMatchesSection interProMatchesSection;

    private GoTermSection goTermSection;


    public FunctionalAnalysisResult() {
        this(new InterProMatchesSection(new ArrayList<InterProEntry>(0), 0));
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
