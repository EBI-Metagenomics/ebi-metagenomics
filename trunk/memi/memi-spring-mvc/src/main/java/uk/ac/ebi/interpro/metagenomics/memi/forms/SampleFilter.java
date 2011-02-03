package uk.ac.ebi.interpro.metagenomics.memi.forms;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

/**
 * Represents a filter form, which is used within the study list page.
 * Use this object to filter studies by type or status.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SampleFilter {

    public final static String MODEL_ATTR_NAME = "sampleFilter";

    private SampleVisibility sampleVisibility;

    private String searchTerm;

    public SampleFilter() {
        this.sampleVisibility = SampleVisibility.PUBLIC;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public SampleVisibility getSampleVisibility() {
        return sampleVisibility;
    }

    public void setSampleVisibility(SampleVisibility sampleVisibility) {
        this.sampleVisibility = sampleVisibility;
    }

    public enum SampleVisibility {
        ALL, PUBLIC, PRIVATE;
    }
}