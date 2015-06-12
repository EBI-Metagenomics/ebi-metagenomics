package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains various analysis results (taxonomic, functional etc).
 *
 * @author Maxim Scheremetjew
 */
public class AnalysisResult {
    private AnalysisStatus analysisStatus;

    /* This object contains a list of InterPro entries. Loaded from the MG pipeline produced file with the IPR extension
     (summary of InterPro matches).
     */
    private FunctionalAnalysisResult functionalAnalysisResult;

    private TaxonomyAnalysisResult taxonomyAnalysisResult;

    public AnalysisResult(AnalysisStatus analysisStatus) {
        this.analysisStatus = analysisStatus;
    }

    public AnalysisStatus getAnalysisStatus() {
        return analysisStatus;
    }

    public FunctionalAnalysisResult getFunctionalAnalysisResult() {
        return functionalAnalysisResult;
    }

    public void setFunctionalAnalysisResult(FunctionalAnalysisResult functionalAnalysisResult) {
        this.functionalAnalysisResult = functionalAnalysisResult;
    }

    public TaxonomyAnalysisResult getTaxonomyAnalysisResult() {
        return taxonomyAnalysisResult;
    }

    public void setTaxonomyAnalysisResult(TaxonomyAnalysisResult taxonomyAnalysisResult) {
        this.taxonomyAnalysisResult = taxonomyAnalysisResult;
    }
}