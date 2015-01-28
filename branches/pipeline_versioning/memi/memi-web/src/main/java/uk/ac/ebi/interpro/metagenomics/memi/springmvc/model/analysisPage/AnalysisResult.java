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

    public static final List<String> colorCodeList = new ArrayList<String>(10);

    static {
        //init color code list
        colorCodeList.add("058dc7");
        colorCodeList.add("50b432");
        colorCodeList.add("ed561b");
        colorCodeList.add("edef00");
        colorCodeList.add("24cbe5");
        colorCodeList.add("64e572");
        colorCodeList.add("ff9655");
        colorCodeList.add("fff263");
        colorCodeList.add("6af9c4");
        colorCodeList.add("dabe88");
    }

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

    public List<String> getColorCodeList() {
        return colorCodeList;
    }
}
