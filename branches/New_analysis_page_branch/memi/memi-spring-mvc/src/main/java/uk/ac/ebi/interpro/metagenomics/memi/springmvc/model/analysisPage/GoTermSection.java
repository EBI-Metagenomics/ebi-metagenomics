package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.BiologicalProcessGOTerm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.CellularComponentGOTerm;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.MolecularFunctionGOTerm;

import java.util.ArrayList;

/**
 * Simple model class which is part of {@link FunctionalAnalysisResult}.
 *
 * @author Maxim Scheremetjew
 */
public class GoTermSection {
    private BiologicalProcessGoTerms biologicalProcessGoTerms;

    private CellularComponentGoTerms cellularComponentGoTerms;

    private MolecularFunctionGoTerms molecularFunctionGoTerms;

    public GoTermSection() {
        this(new BiologicalProcessGoTerms(0, new ArrayList<BiologicalProcessGOTerm>()),
                new CellularComponentGoTerms(0, new ArrayList<CellularComponentGOTerm>()),
                new MolecularFunctionGoTerms(0, new ArrayList<MolecularFunctionGOTerm>()));
    }

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
