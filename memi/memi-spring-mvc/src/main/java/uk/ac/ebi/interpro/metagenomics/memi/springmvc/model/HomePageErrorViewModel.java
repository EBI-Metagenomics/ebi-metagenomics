package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import com.sun.syndication.feed.synd.SyndEntry;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import java.util.List;

/**
 * Represents the model for the home page error view.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public final class HomePageErrorViewModel extends ViewModel {

    public HomePageErrorViewModel(Submitter submitter, String pageTitle,
                                  List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
    }
}