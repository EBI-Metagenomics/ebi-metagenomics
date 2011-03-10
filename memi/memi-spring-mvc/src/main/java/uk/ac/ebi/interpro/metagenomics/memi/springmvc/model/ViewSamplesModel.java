package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ViewSamplesModel extends MGModel {

    private SampleFilter sampleFilter;

    private List<Sample> samples;

    private List<SampleFilter.SampleVisibility> sampleVisibilityList;

    private List<Sample.SampleType> sampleTypes;


    ViewSamplesModel(Submitter submitter, List<Sample> samples, List<Breadcrumb> breadcrumbs) {
        this(submitter, samples, new SampleFilter(), breadcrumbs);
    }

    ViewSamplesModel(Submitter submitter, List<Sample> samples, SampleFilter filter, List<Breadcrumb> breadcrumbs) {
        super(submitter, breadcrumbs);
        this.sampleFilter = filter;
        this.samples = samples;
        this.sampleVisibilityList = getDefaultStudyVisibilityList();
        this.sampleTypes = getDefaultSampleTypes();
    }

    public SampleFilter getSampleFilter() {
        return sampleFilter;
    }

    public void setSampleFilter(SampleFilter sampleFilter) {
        this.sampleFilter = sampleFilter;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public List<SampleFilter.SampleVisibility> getSampleVisibilityList() {
        return sampleVisibilityList;
    }

    public void setSampleVisibilityList(List<SampleFilter.SampleVisibility> sampleVisibilityList) {
        this.sampleVisibilityList = sampleVisibilityList;
    }

    public List<Sample.SampleType> getSampleTypes() {
        return sampleTypes;
    }

    public void setSampleTypes(List<Sample.SampleType> sampleTypes) {
        this.sampleTypes = sampleTypes;
    }

    private List<SampleFilter.SampleVisibility> getDefaultStudyVisibilityList() {
        List<SampleFilter.SampleVisibility> result = new ArrayList<SampleFilter.SampleVisibility>();
        for (SampleFilter.SampleVisibility vis : SampleFilter.SampleVisibility.values()) {
            result.add(vis);
        }
        return result;
    }

    private List<Sample.SampleType> getDefaultSampleTypes() {
        List<Sample.SampleType> result = new ArrayList<Sample.SampleType>();
        for (Sample.SampleType type : Sample.SampleType.values()) {
            result.add(type);
        }
        return result;
    }
}
