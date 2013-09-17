package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

/**
 * Simple model class which is part of {@link FunctionalAnalysisResult}.
 *
 * @author Maxim Scheremetjew
 */
public class GoTermSection {
    private BiologicalProcessGoTerms biologicalProcessGoTerms;

    private CellularComponentGoTerms cellularComponentGoTerms;

    private MolecularFunctionGoTerms molecularFunctionGoTerms;

    public GoTermSection(BiologicalProcessGoTerms biologicalProcessGoTerms, CellularComponentGoTerms cellularComponentGoTerms, MolecularFunctionGoTerms molecularFunctionGoTerms) {
        this.biologicalProcessGoTerms = biologicalProcessGoTerms;
        this.cellularComponentGoTerms = cellularComponentGoTerms;
        this.molecularFunctionGoTerms = molecularFunctionGoTerms;
    }

    public BiologicalProcessGoTerms getBiologicalProcessGoTerms() {
        return biologicalProcessGoTerms;
    }

    public CellularComponentGoTerms getCellularComponentGoTerms() {
        return cellularComponentGoTerms;
    }

    public MolecularFunctionGoTerms getMolecularFunctionGoTerms() {
        return molecularFunctionGoTerms;
    }
}
