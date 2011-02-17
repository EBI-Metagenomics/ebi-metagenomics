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

    private Study.StudyType sampleType;

    private String searchTerm;

    public SampleFilter() {
        this.sampleVisibility = SampleVisibility.ALL_PUBLISHED_SAMPLES;
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

    public Study.StudyType getSampleType() {
        return sampleType;
    }

    public void setSampleType(Study.StudyType sampleType) {
        this.sampleType = sampleType;
    }

    /**
     * ALL_SAMPLES: All published and my pre-published SAMPLES
     * ALL_PUBLISHED_SAMPLES: All published SAMPLES
     * MY_SAMPLES: All my published and my pre-published SAMPLES
     * MY_PUBLISHED_SAMPLES: All my published SAMPLES
     * MY_PRE-PUBLISHED_SAMPLES: All my pre-published SAMPLES
     */
    public enum SampleVisibility {
        ALL_SAMPLES, ALL_PUBLISHED_SAMPLES, MY_SAMPLES, MY_PUBLISHED_SAMPLES, MY_PREPUBLISHED_SAMPLES;
    }
}