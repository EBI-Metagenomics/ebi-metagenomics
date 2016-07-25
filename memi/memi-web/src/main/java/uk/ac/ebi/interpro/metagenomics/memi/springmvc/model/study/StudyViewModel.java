package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.study;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.extensions.QueryRunsForProjectResult;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
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
public class StudyViewModel extends ViewModel {
    private Study study;

    private String disabledOption;

    /* True if JSON file (name: google-map-sample-data.json) does exist. The JSON file is necessary to render the Google Map.*/
    private boolean isGoogleMapDataAvailable;

    public StudyViewModel(Submitter submitter, EBISearchForm ebiSearchForm, Study study, String pageTitle,
                          List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer, final String disabledOption,
                          final boolean isGoogleMapDataAvailable) {
        super(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer);
        this.study = study;
        this.disabledOption = disabledOption;
        this.isGoogleMapDataAvailable = isGoogleMapDataAvailable;
    }

    public Study getStudy() {
        return study;
    }

    public String getDisabledOption() {
        return disabledOption;
    }

    public boolean isGoogleMapDataAvailable() {
        return isGoogleMapDataAvailable;
    }
}