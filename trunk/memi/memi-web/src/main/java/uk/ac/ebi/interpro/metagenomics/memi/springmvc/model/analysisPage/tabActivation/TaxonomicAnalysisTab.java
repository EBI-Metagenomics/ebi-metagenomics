package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation;

import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;

/**
 * Created with IntelliJ IDEA.
 * User: maxim
 * Date: 8/7/13
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class TaxonomicAnalysisTab {

    boolean isKronaTabDisabled;

    boolean isStackChartTabDisabled;

    boolean isBarChartTabDisabled;

    boolean isPieChartTabDisabled;

    public TaxonomicAnalysisTab(boolean pieChartTabDisabled, boolean barChartTabDisabled, boolean stackChartTabDisabled, boolean kronaTabDisabled) {
        isKronaTabDisabled = kronaTabDisabled;
        isStackChartTabDisabled = stackChartTabDisabled;
        isBarChartTabDisabled = barChartTabDisabled;
        isPieChartTabDisabled = pieChartTabDisabled;
    }

    public boolean isKronaTabDisabled() {
        return isKronaTabDisabled;
    }

    public boolean isStackChartTabDisabled() {
        return isStackChartTabDisabled;
    }

    public boolean isBarChartTabDisabled() {
        return isBarChartTabDisabled;
    }

    public boolean isPieChartTabDisabled() {
        return isPieChartTabDisabled;
    }

    public String getTabsOptions() {
        if (!isPieChartTabDisabled && !isBarChartTabDisabled && !isStackChartTabDisabled && !isKronaTabDisabled) {
            return "";
        }
        int pieChartTabIndex = 0;
        int barChartTabIndex = 1;
        int stackChartTabIndex = 2;
        int kronaChartTabIndex = 3;
        int selectIndex = -1;
        StringBuilder indexes = new StringBuilder();
        if (isPieChartTabDisabled) {
            MemiTools.addIndex(indexes, pieChartTabIndex);
        } else {
            if (selectIndex == -1) {
                selectIndex = pieChartTabIndex;
            }
        }
        if (isBarChartTabDisabled) {
            MemiTools.addIndex(indexes, barChartTabIndex);
        } else {
            if (selectIndex == -1) {
                selectIndex = barChartTabIndex;
            }
        }
        if (isStackChartTabDisabled) {
            MemiTools.addIndex(indexes, stackChartTabIndex);
        } else {
            if (selectIndex == -1) {
                selectIndex = stackChartTabIndex;
            }
        }
        if (isKronaTabDisabled) {
            MemiTools.addIndex(indexes, kronaChartTabIndex);
        } else {
            if (selectIndex == -1) {
                selectIndex = kronaChartTabIndex;
            }
        }
        StringBuilder result = new StringBuilder("disabled: [");
        result.append(indexes).append("]");
        result.append(",selected:").append(selectIndex);
        return result.toString();
    }
}
