package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the model for the study overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class StudyViewModel extends ViewModel {
    private List<Sample> samples;

    private Study study;

    private List<Publication> relatedLinks;

    private List<Publication> relatedPublications;

    public StudyViewModel(Submitter submitter, Study study, List<Sample> samples, String pageTitle,
                          List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                          List<Publication> relatedPublications, List<Publication> relatedLinks) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.samples = samples;
        this.study = study;
        this.relatedLinks = relatedLinks;
        this.relatedPublications = relatedPublications;
    }

    public List<Publication> getRelatedLinks() {
        return relatedLinks;
    }

    public List<Publication> getRelatedPublications() {
        return relatedPublications;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }
}