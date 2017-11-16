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

    boolean isKronaTabDisabledSSU;

    boolean isStackChartTabDisabledSSU;

    boolean isBarChartTabDisabledSSU;

    boolean isPieChartTabDisabledSSU;

    boolean isKronaTabDisabledLSU;

    boolean isStackChartTabDisabledLSU;

    boolean isBarChartTabDisabledLSU;

    boolean isPieChartTabDisabledLSU;

    public TaxonomicAnalysisTab(boolean pieChartTabDisabledSSU, boolean barChartTabDisabledSSU, boolean stackChartTabDisabledSSU, boolean kronaTabDisabledSSU,
                                boolean pieChartTabDisabledLSU, boolean barChartTabDisabledLSU, boolean stackChartTabDisabledLSU, boolean kronaTabDisabledLSU) {
        isKronaTabDisabledSSU = kronaTabDisabledSSU;
        isStackChartTabDisabledSSU = stackChartTabDisabledSSU;
        isBarChartTabDisabledSSU = barChartTabDisabledSSU;
        isPieChartTabDisabledSSU = pieChartTabDisabledSSU;
        isKronaTabDisabledLSU = kronaTabDisabledLSU;
        isStackChartTabDisabledLSU = stackChartTabDisabledLSU;
        isBarChartTabDisabledLSU = barChartTabDisabledLSU;
        isPieChartTabDisabledLSU = pieChartTabDisabledLSU;
    }

    public boolean isKronaTabDisabledSSU() {
        return isKronaTabDisabledSSU;
    }

    public boolean isStackChartTabDisabledSSU() {
        return isStackChartTabDisabledSSU;
    }

    public boolean isBarChartTabDisabledSSU() {
        return isBarChartTabDisabledSSU;
    }

    public boolean isPieChartTabDisabledSSU() {
        return isPieChartTabDisabledSSU;
    }

    public boolean isKronaTabDisabledLSU() {
        return isKronaTabDisabledLSU;
    }

    public boolean isStackChartTabDisabledLSU() {
        return isStackChartTabDisabledLSU;
    }

    public boolean isBarChartTabDisabledLSU() {
        return isBarChartTabDisabledLSU;
    }

    public boolean isPieChartTabDisabledLSU() {
        return isPieChartTabDisabledLSU;
    }

    public String getTabsOptionsSSU() {
        return getTabsOptions(isPieChartTabDisabledSSU, isBarChartTabDisabledSSU, isStackChartTabDisabledSSU, isKronaTabDisabledSSU);
    }

    public String getTabsOptionsLSU() {
        return getTabsOptions(isPieChartTabDisabledLSU, isBarChartTabDisabledLSU, isStackChartTabDisabledLSU, isKronaTabDisabledLSU);
    }

    public String getTabsOptionsTaxa() {
        StringBuilder result = new StringBuilder("disabled: [");
        StringBuilder active = new StringBuilder();
        int initialLength = result.length();
        if (isPieChartTabDisabledSSU && isBarChartTabDisabledSSU && isStackChartTabDisabledSSU && isKronaTabDisabledSSU) {
            result.append("0");
            active.append(",active:1");
        }
        if (isPieChartTabDisabledLSU && isBarChartTabDisabledLSU && isStackChartTabDisabledLSU && isKronaTabDisabledLSU) {
            if (result.length() > initialLength) {
                result.append(",");
            }
            result.append("1");
        }

        return result.append("]").append(active.toString()).toString();
    }

    private String getTabsOptions(boolean isPieChartTabDisabled, boolean isBarChartTabDisabled, boolean isStackChartTabDisabled,
                                  boolean isKronaTabDisabled) {
        if (!isPieChartTabDisabled && !isBarChartTabDisabled && !isStackChartTabDisabled && !isKronaTabDisabled) {
            return "";
        }
        int kronaChartTabIndex = 0;
        int pieChartTabIndex = 1;
        int barChartTabIndex = 2;
        int stackChartTabIndex = 3;
        int selectIndex = -1;
        StringBuilder indexes = new StringBuilder();
        if (isKronaTabDisabled) {
            MemiTools.addIndex(indexes, kronaChartTabIndex);
        } else {
            if (selectIndex == -1) {
                selectIndex = kronaChartTabIndex;
            }
        }
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
        StringBuilder result = new StringBuilder("disabled: [");
        result.append(indexes).append("]");
        result.append(",active:").append(selectIndex);
        return result.toString();
    }
}
