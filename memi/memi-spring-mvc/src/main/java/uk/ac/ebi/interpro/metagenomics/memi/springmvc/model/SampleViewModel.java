package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSampleAnnotation;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.util.List;

/**
 * Represents the model for the study overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class SampleViewModel extends ViewModel {
    private final Sample sample;

    private final List<Publication> publications;

    private final List<String> archivedSequences;

    private final List<EmgSampleAnnotation> sampleAnnotations;

    /**
     * Indicates if the sample of this model is host-associated OR not
     */
    private final boolean isHostAssociated;

    public SampleViewModel(Submitter submitter, Sample sample, List<String> archivedSequences, String pageTitle,
                           List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                           boolean isHostAssociated, List<Publication> publications, List<EmgSampleAnnotation> sampleAnnotations) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.sample = sample;
        this.archivedSequences = archivedSequences;
        this.publications = publications;
        this.isHostAssociated = isHostAssociated;
        this.sampleAnnotations = sampleAnnotations;
    }


    public Sample getSample() {
        return sample;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public List<String> getArchivedSequences() {
        return archivedSequences;
    }

    public boolean isHostAssociated() {
        return isHostAssociated;
    }

    public List<EmgSampleAnnotation> getSampleAnnotations() {
        return sampleAnnotations;
    }
}
