package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

/**
 * Represents the information of which tab on the sample page should be disabled (If no analysis results are available).
 *
 * @author Maxim Scheremetjew
 */
public class AnalysisStatus {

    private final boolean taxonomyTabDisabled;

    private final boolean qualityControlTabDisabled;

    private final boolean functionTabDisabled;

    private Boolean isComplete;

    public AnalysisStatus() {
        this(false, false, false);
        isComplete = new Boolean(true);
    }

    public AnalysisStatus(boolean taxonomyTabDisabled, boolean qualityControlTabDisabled, boolean functionTabDisabled) {
        this.taxonomyTabDisabled = taxonomyTabDisabled;
        this.qualityControlTabDisabled = qualityControlTabDisabled;
        this.functionTabDisabled = functionTabDisabled;
        if (isComplete == null) {
            this.isComplete = checkCompleteness();
        }
    }

    public boolean isTaxonomyTabDisabled() {
        return taxonomyTabDisabled;
    }

    public boolean isQualityControlTabDisabled() {
        return qualityControlTabDisabled;
    }

    public boolean isFunctionTabDisabled() {
        return functionTabDisabled;
    }

    private Boolean checkCompleteness() {
        if (!taxonomyTabDisabled) {
            if (!qualityControlTabDisabled) {
                if (!functionTabDisabled) {
                    return new Boolean(true);
                }
            }
        }
        return new Boolean(false);
    }

    public String getDisabledAttribute() {
        if (isComplete) {
            return "";
        }
        StringBuilder indexes = new StringBuilder();
        if (qualityControlTabDisabled) {
            addIndexe(indexes, "3");
        }
        if (taxonomyTabDisabled) {
            addIndexe(indexes, "1");
        }
        if (functionTabDisabled) {
            addIndexe(indexes, "2");
        }
        StringBuilder result = new StringBuilder("disabled: [");
        return result.append(indexes).append("]").toString();
    }

    private void addIndexe(StringBuilder indexes, String index) {
        if (indexes.length() > 0) {
            indexes.append(",");
        }
        indexes.append(index);
    }
}