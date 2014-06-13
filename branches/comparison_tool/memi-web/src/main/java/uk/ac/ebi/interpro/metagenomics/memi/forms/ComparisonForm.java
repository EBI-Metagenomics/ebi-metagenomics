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

    @NotEmpty(message = "Please select some data")
    private String usedData;

    private boolean keepNames = true;

    @NotEmpty(message = "Please select samples to compare (at least two)")
    private List<Long> samples = new ArrayList<Long>();

    private double stackThreshold = 1.0;

    private int GOnumber = 150;

    private String hmClust;

    private String hmDendrogram;

    private boolean hmLegend = true;

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

    public double getStackThreshold() {
        return stackThreshold;
    }

    public void setStackThreshold(long stackThreshold) {
        this.stackThreshold = stackThreshold;
    }

    public int getGOnumber() {
        return GOnumber;
    }

    public void setGOnumber(int GOnumber) {
        this.GOnumber = GOnumber;
    }

    public String getHmClust() {
        return hmClust;
    }

    public void setHmClust(String hmClust) {
        this.hmClust = hmClust;
    }

    public boolean isHmLegend() {
        return hmLegend;
    }

    public void setHmLegend(boolean hmLegend) {
        this.hmLegend = hmLegend;
    }

    public String getHmDendrogram() {
        return hmDendrogram;
    }

    public void setHmDendrogram(String hmDendrogram) {
        this.hmDendrogram = hmDendrogram;
    }
}