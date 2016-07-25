package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.results;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;

import java.util.List;

/**
 * Quality check view model.
 *
 * @author Maxim Scheremetjew
 * @since 1.4-SNAPSHOT
 */
public class MetaDataViewModel extends AbstractResultViewModel {

    private Sample sample;

    private List<Publication> relatedLinks;

    private List<Publication> relatedPublications;

    private AnalysisJob analysisJob;

    public MetaDataViewModel(Submitter submitter,
                             EBISearchForm ebiSearchForm,
                             String pageTitle,
                             List<Breadcrumb> breadcrumbs,
                             MemiPropertyContainer propertyContainer,
                             Sample sample,
                             AnalysisJob analysisJob,
                             List<Publication> relatedLinks,
                             List<Publication> relatedPublications) {
        super(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer);
        this.sample = sample;
        this.analysisJob = analysisJob;
        this.relatedLinks = relatedLinks;
        this.relatedPublications = relatedPublications;
    }

    public Sample getSample() {
        return sample;
    }

    public List<Publication> getRelatedLinks() {
        return relatedLinks;
    }

    public List<Publication> getRelatedPublications() {
        return relatedPublications;
    }

    public AnalysisJob getAnalysisJob() {
        return analysisJob;
    }
}