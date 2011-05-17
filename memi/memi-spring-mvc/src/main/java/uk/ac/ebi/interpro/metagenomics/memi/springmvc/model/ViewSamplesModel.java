package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents the model for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ViewSamplesModel extends MGModel {

    private SampleFilter sampleFilter;

    private Set<Sample> samples;

    private List<SampleFilter.SampleVisibility> sampleVisibilityList;

    private List<Sample.SampleType> sampleTypes;


    ViewSamplesModel(Submitter submitter, Set<Sample> samples, String pageTitle, List<Breadcrumb> breadcrumbs,
                     MemiPropertyContainer propertyContainer) {
        this(submitter, samples, new SampleFilter(), pageTitle, breadcrumbs, propertyContainer);
    }

    ViewSamplesModel(Submitter submitter, Set<Sample> samples, SampleFilter filter, String pageTitle,
                     List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
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

    public Set<Sample> getSamples() {
        return samples;
    }

    public void setSamples(Set<Sample> samples) {
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
