package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AbstractGOTerm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.BiologicalProcessGOTerm;

import java.util.List;

/**
 * Simple model class which holds 'biological process' data.
 *
 * @author Maxim Scheremetjew
 */
public class BiologicalProcessGoTerms {
    private int totalHitsCount;

    private List<BiologicalProcessGOTerm> biologicalProcessGOTermList;

    public BiologicalProcessGoTerms(int totalHitsCount, List<BiologicalProcessGOTerm> biologicalProcessGOTermList) {
        this.totalHitsCount = totalHitsCount;
        this.biologicalProcessGOTermList = biologicalProcessGOTermList;
    }

    public int getTotalHitsCount() {
        return totalHitsCount;
    }

    public List<BiologicalProcessGOTerm> getBiologicalProcessGOTermList() {
        return biologicalProcessGOTermList;
    }

    public float getSliceVisibilityThresholdValue() {
        if (biologicalProcessGOTermList.size() > 10) {
            return (biologicalProcessGOTermList.get(9).getNumberOfMatches() - 0.5f) / totalHitsCount;
        }
        return AbstractGOTerm.DEFAULT_SLICE_VISIBILITY_THRESHOLD;
    }
}
