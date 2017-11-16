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

    private final boolean abundanceTabDisabled;


    public AnalysisStatus(TaxonomicAnalysisTab taxonomicAnalysisTab, boolean qualityControlTabDisabled, boolean abundanceTabDisabled, FunctionalAnalysisTab functionalAnalysisTab) {
        this.taxonomicAnalysisTab = taxonomicAnalysisTab;
        this.qualityControlTabDisabled = qualityControlTabDisabled;
        this.abundanceTabDisabled = abundanceTabDisabled;
        this.functionalAnalysisTab = functionalAnalysisTab;
    }

    public boolean isQualityControlTabDisabled() {
        return qualityControlTabDisabled;
    }

    public boolean isAbundanceTabDisabled() {
        return abundanceTabDisabled;
    }

    public boolean isTaxonomicAnalysisTabDisabled() {
        if (taxonomicAnalysisTab.isBarChartTabDisabledSSU() && taxonomicAnalysisTab.isKronaTabDisabledSSU()
                && taxonomicAnalysisTab.isPieChartTabDisabledSSU() && taxonomicAnalysisTab.isStackChartTabDisabledSSU()
                && taxonomicAnalysisTab.isBarChartTabDisabledLSU() && taxonomicAnalysisTab.isKronaTabDisabledLSU()
                && taxonomicAnalysisTab.isPieChartTabDisabledLSU() && taxonomicAnalysisTab.isStackChartTabDisabledLSU()) {
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
        if (!qualityControlTabDisabled && !abundanceTabDisabled && !isFunctionalAnalysisTabDisabled && !isTaxonomicAnalysisTabDisabled) {
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
        if (abundanceTabDisabled) {
            MemiTools.addIndex(indexes, 4);
        }
        StringBuilder result = new StringBuilder("disabled: [");
        return result.append(indexes).append("]").toString();
    }
}