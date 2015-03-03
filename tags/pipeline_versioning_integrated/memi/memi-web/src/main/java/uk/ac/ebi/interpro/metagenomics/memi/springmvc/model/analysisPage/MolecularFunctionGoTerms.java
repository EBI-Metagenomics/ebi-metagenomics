package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.AbstractGOTerm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MolecularFunctionGOTerm;

import java.util.List;

/**
 * Simple model class which holds 'molecular function' data.
 *
 * @author Maxim Scheremetjew
 */
public class MolecularFunctionGoTerms {
    private int totalHitsCount;

    private List<MolecularFunctionGOTerm> molecularFunctionGOTermList;

    public MolecularFunctionGoTerms(int totalHitsCount, List<MolecularFunctionGOTerm> molecularFunctionGOTermList) {
        this.totalHitsCount = totalHitsCount;
        this.molecularFunctionGOTermList = molecularFunctionGOTermList;
    }

    public int getTotalHitsCount() {
        return totalHitsCount;
    }

    public List<MolecularFunctionGOTerm> getMolecularFunctionGOTermList() {
        return molecularFunctionGOTermList;
    }

    public float getSliceVisibilityThresholdValue() {
        if (molecularFunctionGOTermList.size() > 10) {
            return (molecularFunctionGOTermList.get(9).getNumberOfMatches() - 0.5f) / totalHitsCount;
        }
            return AbstractGOTerm.DEFAULT_SLICE_VISIBILITY_THRESHOLD;
    }
}