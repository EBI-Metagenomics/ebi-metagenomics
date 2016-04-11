package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import java.util.List;

/**
 * Represents the model for the home page error view.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public final class HomePageErrorViewModel extends ViewModel {

    public HomePageErrorViewModel(Submitter submitter, EBISearchForm ebiSearchForm, String pageTitle,
                                  List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer) {
        super(submitter, ebiSearchForm, pageTitle, breadcrumbs, propertyContainer);
    }
}