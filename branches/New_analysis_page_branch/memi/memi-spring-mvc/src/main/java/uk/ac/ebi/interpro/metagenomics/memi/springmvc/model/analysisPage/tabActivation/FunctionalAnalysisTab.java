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

    private boolean isSequenceFeatureSectionDisabled;

    public FunctionalAnalysisTab(boolean interProMatchSectionDisabled, boolean goSectionDisabled, boolean isSequenceFeatureSectionDisabled) {
        this.isGoSectionDisabled = goSectionDisabled;
        this.isInterProMatchSectionDisabled = interProMatchSectionDisabled;
        this.isSequenceFeatureSectionDisabled = isSequenceFeatureSectionDisabled;
    }

    public boolean isGoSectionDisabled() {
        return isGoSectionDisabled;
    }

    public boolean isInterProMatchSectionDisabled() {
        return isInterProMatchSectionDisabled;
    }

    public boolean isSequenceFeatureSectionDisabled() {
        return isSequenceFeatureSectionDisabled;
    }
}
