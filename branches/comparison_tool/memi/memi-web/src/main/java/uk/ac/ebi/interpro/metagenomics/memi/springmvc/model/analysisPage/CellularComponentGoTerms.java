package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AbstractGOTerm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.CellularComponentGOTerm;

import java.util.List;

/**
 * Simple model class which holds 'cellular component' data.
 *
 * @author Maxim Scheremetjew
 */
public class CellularComponentGoTerms {
    private int totalHitsCount;

    private List<CellularComponentGOTerm> cellularComponentGOTermList;

    public CellularComponentGoTerms(int totalHitsCount, List<CellularComponentGOTerm> cellularComponentGOTermList) {
        this.totalHitsCount = totalHitsCount;
        this.cellularComponentGOTermList = cellularComponentGOTermList;
    }

    public int getTotalHitsCount() {
        return totalHitsCount;
    }

    public List<CellularComponentGOTerm> getCellularComponentGOTermList() {
        return cellularComponentGOTermList;
    }

    public float getSliceVisibilityThresholdValue() {
        if (cellularComponentGOTermList.size() > 10) {
            return (cellularComponentGOTermList.get(9).getNumberOfMatches() - 0.5f) / totalHitsCount;
        }
        return AbstractGOTerm.DEFAULT_SLICE_VISIBILITY_THRESHOLD;
    }
}
