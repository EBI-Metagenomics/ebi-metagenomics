package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.tabActivation;

/**
 * Created with IntelliJ IDEA.
 * User: maxim
 * Date: 8/7/13
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class FunctionalAnalysisTab {

    private boolean isGoSectionDisabled;

    private boolean isInterProMatchSectionDisabled;

    public FunctionalAnalysisTab(boolean interProMatchSectionDisabled, boolean goSectionDisabled) {
        isGoSectionDisabled = goSectionDisabled;
        isInterProMatchSectionDisabled = interProMatchSectionDisabled;
    }

    public boolean isGoSectionDisabled() {
        return isGoSectionDisabled;
    }

    public boolean isInterProMatchSectionDisabled() {
        return isInterProMatchSectionDisabled;
    }
}
