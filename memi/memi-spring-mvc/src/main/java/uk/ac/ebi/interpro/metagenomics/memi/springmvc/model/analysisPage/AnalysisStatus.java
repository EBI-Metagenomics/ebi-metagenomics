package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.FunctionalAnalysisTab;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation.TaxonomicAnalysisTab;

/**
 * Represents the information of which tab on the sample page should be activated/disabled (if no analysis results are available).
 *
 * @author Maxim Scheremetjew
 */
public class AnalysisStatus {

    /* Holds the info, which sub tabs should be disabled. */
    private TaxonomicAnalysisTab taxonomicAnalysisTab;

    /* Holds the info, which sections of the functional analysis tab should be disabled. */
    private FunctionalAnalysisTab functionalAnalysisTab;

    private final boolean qualityControlTabDisabled;


    public AnalysisStatus(TaxonomicAnalysisTab taxonomicAnalysisTab, boolean qualityControlTabDisabled, FunctionalAnalysisTab functionalAnalysisTab) {
        this.taxonomicAnalysisTab = taxonomicAnalysisTab;
        this.qualityControlTabDisabled = qualityControlTabDisabled;
        this.functionalAnalysisTab = functionalAnalysisTab;
    }

    public boolean isQualityControlTabDisabled() {
        return qualityControlTabDisabled;
    }

    public boolean isTaxonomicAnalysisTabDisabled() {
        if (taxonomicAnalysisTab.isBarChartTabDisabled() && taxonomicAnalysisTab.isKronaTabDisabled()
                && taxonomicAnalysisTab.isPieChartTabDisabled() && taxonomicAnalysisTab.isStackChartTabDisabled()) {
            return true;
        }
        return false;
    }

    public boolean isFunctionalAnalysisTabDisabled() {
        if (functionalAnalysisTab.isGoSectionDisabled() && functionalAnalysisTab.isInterProMatchSectionDisabled()
                && functionalAnalysisTab.isSequenceFeatureSectionDisabled()) {
            return true;
        }
        return false;
    }

    public TaxonomicAnalysisTab getTaxonomicAnalysisTab() {
        return taxonomicAnalysisTab;
    }

    public FunctionalAnalysisTab getFunctionalAnalysisTab() {
        return functionalAnalysisTab;
    }

    public String getDisabledOption() {
        Boolean isTaxonomicAnalysisTabDisabled = new Boolean(isTaxonomicAnalysisTabDisabled());
        Boolean isFunctionalAnalysisTabDisabled = new Boolean(isFunctionalAnalysisTabDisabled());
        if (!qualityControlTabDisabled && !isFunctionalAnalysisTabDisabled && !isTaxonomicAnalysisTabDisabled) {
            return "";
        }
        StringBuilder indexes = new StringBuilder();
        if (qualityControlTabDisabled) {
            MemiTools.addIndex(indexes, 1);
        }
        if (isTaxonomicAnalysisTabDisabled) {
            MemiTools.addIndex(indexes, 2);
        }
        if (isFunctionalAnalysisTabDisabled) {
            MemiTools.addIndex(indexes, 3);
        }
        StringBuilder result = new StringBuilder("disabled: [");
        return result.append(indexes).append("]").toString();
    }
}