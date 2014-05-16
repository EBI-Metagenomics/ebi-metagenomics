package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a form for comparison submission.
 *
 * @author Francois Bucchini, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ComparisonForm {
    public final static String MODEL_ATTR_NAME = "comparisonForm";

    @NotEmpty(message = "Please select a project")
    private String study;

    @NotEmpty(message = "Please select data")
    private String usedData;

    @NotEmpty(message = "Please select visualization")
    private String usedVis;

    private boolean keepNames;

    private List<Long> samples = new ArrayList<Long>();


//TODO: Implement other selection attribute + getter and setter methods

    public boolean isKeepNames() {
        return keepNames;
    }

    public void setKeepNames(boolean keepNames) {
        this.keepNames = keepNames;
    }

    public List<Long> getSamples() {
        return samples;
    }

    public void setSamples(List<Long> samples) {
        this.samples = samples;
    }

    public String getUsedVis() {
        return usedVis;
    }

    public void setUsedVis(String usedVis) {
        this.usedVis = usedVis;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getUsedData() {
        return usedData;
    }

    public void setUsedData(String usedData) {
        this.usedData = usedData;
    }
}