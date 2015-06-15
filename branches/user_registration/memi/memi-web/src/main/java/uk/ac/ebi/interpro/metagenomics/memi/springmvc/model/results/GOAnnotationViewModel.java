package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.*;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.FunctionalAnalysisResult;

import java.util.Collections;
import java.util.List;

/**
 * GO annotation view model.
 *
 * @author Maxim Scheremetjew
 * @since 1.4-SNAPSHOT
 */
public class GOAnnotationViewModel extends AbstractResultViewModel {

    private FunctionalAnalysisResult functionalAnalysisResult;

    public GOAnnotationViewModel(Submitter submitter,
                                 String pageTitle,
                                 List<Breadcrumb> breadcrumbs,
                                 MemiPropertyContainer propertyContainer,
                                 FunctionalAnalysisResult functionalAnalysisResult) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.functionalAnalysisResult = functionalAnalysisResult;
    }

    public List<BiologicalProcessGOTerm> getBiologicalProcessGOTerms() {
        return functionalAnalysisResult.getGoTermSection().getBiologicalProcessGoTerms().getBiologicalProcessGOTermList();
    }

    /**
     * Sorted by number of hits.
     */
    public List<BiologicalProcessGOTerm> getSortedBiologicalProcessGOTerms() {
        List<BiologicalProcessGOTerm> result = functionalAnalysisResult.getGoTermSection().getBiologicalProcessGoTerms().getBiologicalProcessGOTermList();
        Collections.sort(result, AbstractGOTerm.GoTermComparator);
        return result;
    }

    public List<MolecularFunctionGOTerm> getMolecularFunctionGOTerms() {
        return functionalAnalysisResult.getGoTermSection().getMolecularFunctionGoTerms().getMolecularFunctionGOTermList();
    }

    /**
     * Sorted by number of hits.
     */
    public List<MolecularFunctionGOTerm> getSortedMolecularFunctionGOTerms() {
        List<MolecularFunctionGOTerm> result = functionalAnalysisResult.getGoTermSection().getMolecularFunctionGoTerms().getMolecularFunctionGOTermList();
        Collections.sort(result, AbstractGOTerm.GoTermComparator);
        return result;
    }

    public List<CellularComponentGOTerm> getCellularComponentGOTerms() {
        return functionalAnalysisResult.getGoTermSection().getCellularComponentGoTerms().getCellularComponentGOTermList();
    }

    /**
     * Sorted by number of hits.
     */
    public List<CellularComponentGOTerm> getSortedCellularComponentGOTerms() {
        List<CellularComponentGOTerm> result = functionalAnalysisResult.getGoTermSection().getCellularComponentGoTerms().getCellularComponentGOTermList();
        Collections.sort(result, AbstractGOTerm.GoTermComparator);
        return result;
    }

    public FunctionalAnalysisResult getFunctionalAnalysisResult() {
        return functionalAnalysisResult;
    }
}