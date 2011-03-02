package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.HostSample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents the model for the study overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class SampleViewModel extends MGModel {
    private Sample sample;

    private final Set<Publication> pubs;

    private List<String> archivedSequences;

    /**
     * Indicates if the sample of this model is host-associated OR not
     */
    private final boolean isHostAssociated;

    SampleViewModel(Submitter submitter, Sample sample, List<String> archivedSequences) {
        super(submitter);
        this.sample = sample;
        this.archivedSequences = archivedSequences;
        this.pubs = initialisePubs();
        this.isHostAssociated = initialiseHostAssociation();
    }


    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    private Set<Publication> initialisePubs() {
        if (sample != null) {
            Set<Publication> pubs = sample.getPublications();
            if (pubs != null) {
                return pubs;
            }
        }
        return new HashSet<Publication>();

    }

    public Set<Publication> getPubs() {
        return pubs;
    }

    public List<String> getArchivedSequences() {
        return archivedSequences;
    }

    public void setArchivedSequences(List<String> archivedSequences) {
        this.archivedSequences = archivedSequences;
    }

    private boolean initialiseHostAssociation() {
        if (sample != null) {
            if (sample instanceof HostSample) {
                return true;
            }
        }
        return false;
    }

    public boolean isHostAssociated() {
        return isHostAssociated;
    }
}
