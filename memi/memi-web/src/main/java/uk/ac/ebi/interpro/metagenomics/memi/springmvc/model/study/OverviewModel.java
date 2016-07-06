package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Publication;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;

import java.util.List;

/**
 * Represents the model for the study overview page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public class OverviewModel extends ViewModel {
    private List<QueryRunsForProjectResult> runs;

    private Study study;

    private List<Publication> relatedLinks;

    private List<Publication> relatedPublications;

    /* True if JSON file (name: google-map-sample-data.json) does exist. The JSON file is necessary to render the Google Map.*/
    private boolean isGoogleMapDataAvailable;

    public OverviewModel(Submitter submitter, EBISearchForm ebiSearchForm, Study study, List<QueryRunsForProjectResult> runs, String pageTitle,
                         List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                         List<Publication> relatedPublications, List<Publication> relatedLinks,
                         final boolean isGoogleMapDataAvailable) {
        super(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer);
        this.runs = runs;
        this.study = study;
        this.relatedLinks = relatedLinks;
        this.relatedPublications = relatedPublications;
        this.isGoogleMapDataAvailable = isGoogleMapDataAvailable;
    }

    public List<Publication> getRelatedLinks() {
        return relatedLinks;
    }

    public List<Publication> getRelatedPublications() {
        return relatedPublications;
    }

    public List<QueryRunsForProjectResult> getRuns() {
        return runs;
    }

    public void setRuns(List<QueryRunsForProjectResult> runs) {
        this.runs = runs;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public boolean isGoogleMapDataAvailable() {
        return isGoogleMapDataAvailable;
    }
}