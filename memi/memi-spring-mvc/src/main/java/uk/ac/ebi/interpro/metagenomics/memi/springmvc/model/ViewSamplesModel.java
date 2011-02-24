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

    private List<Study.StudyType> sampleTypes;


    ViewSamplesModel(Submitter submitter, List<Sample> samples) {
        this(submitter, samples, new SampleFilter());
    }

    ViewSamplesModel(Submitter submitter, List<Sample> samples, SampleFilter filter) {
        super(submitter);
        this.sampleFilter = filter;
        this.samples = samples;
        this.sampleVisibilityList = getDefaultStudyVisibilityList();
        this.sampleTypes = getDefaultStudyTypes();
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

    public List<Study.StudyType> getSampleTypes() {
        return sampleTypes;
    }

    public void setSampleTypes(List<Study.StudyType> sampleTypes) {
        this.sampleTypes = sampleTypes;
    }

    private List<SampleFilter.SampleVisibility> getDefaultStudyVisibilityList() {
        List<SampleFilter.SampleVisibility> result = new ArrayList<SampleFilter.SampleVisibility>();
        for (SampleFilter.SampleVisibility vis : SampleFilter.SampleVisibility.values()) {
            result.add(vis);
        }
        return result;
    }

    private List<Study.StudyType> getDefaultStudyTypes() {
        List<Study.StudyType> result = new ArrayList<Study.StudyType>();
        for (Study.StudyType type : Study.StudyType.values()) {
            result.add(type);
        }
        return result;
    }
}
