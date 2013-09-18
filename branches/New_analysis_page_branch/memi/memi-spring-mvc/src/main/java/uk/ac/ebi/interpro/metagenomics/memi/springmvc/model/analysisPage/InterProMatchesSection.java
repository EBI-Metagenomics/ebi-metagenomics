package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.List;


/**
 * Simple model class which is part of {@link FunctionalAnalysisResult}. Holds data to render the InterPro matches chart.
 *
 * @author Maxim Scheremetjew
 */
public class InterProMatchesSection {

    private List<InterProEntry> interProEntryList;

    private int totalReadsCount;

    private final float DEFAULT_SLICE_VISIBILITY_THRESHOLD = 0.0058f;

    public InterProMatchesSection(List<InterProEntry> interProEntryList, int totalReadsCount) {
        this.interProEntryList = interProEntryList;
        this.totalReadsCount = totalReadsCount;
    }

    public List<InterProEntry> getInterProEntryList() {
        return interProEntryList;
    }

    public int getTotalReadsCount() {
        return totalReadsCount;
    }

    public float getSliceVisibilityThresholdValue() {
        if (interProEntryList.size() > 10) {
            return (interProEntryList.get(9).getNumOfEntryHits() - 0.5f) / totalReadsCount;
        }
        return DEFAULT_SLICE_VISIBILITY_THRESHOLD;
    }
}
