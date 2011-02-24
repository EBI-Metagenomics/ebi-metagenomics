package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import java.util.List;

/**
 * Represents the model for the study overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class StudyViewModel extends MGModel {
    private List<EmgSample> samples;

    private EmgStudy study;

    private List<String> studyProperties;

    StudyViewModel(Submitter submitter, List<EmgSample> samples, EmgStudy study, List<String> studyProperties) {
        super(submitter);
        this.samples = samples;
        this.study = study;
        this.studyProperties = studyProperties;
    }

    public List<EmgSample> getSamples() {
        return samples;
    }

    public void setSamples(List<EmgSample> samples) {
        this.samples = samples;
    }

    public EmgStudy getStudy() {
        return study;
    }

    public void setStudy(EmgStudy study) {
        this.study = study;
    }

    public List<String> getStudyProperties() {
        return studyProperties;
    }

    public void setStudyProperties(List<String> studyProperties) {
        this.studyProperties = studyProperties;
    }
}
