package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import com.sun.syndication.feed.synd.SyndEntry;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.*;

/**
 * Represents the model for the home page error view.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public final class HomePageErrorViewModel extends ViewModel {

    private final String rssUrl;

    private final List<SyndEntry> rssItems;


    public HomePageErrorViewModel(Submitter submitter, String rssUrl, List<SyndEntry> rssItems,
                                  String pageTitle, List<Breadcrumb> breadcrumbs,
                                  MemiPropertyContainer propertyContainer) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.rssUrl = rssUrl;
        this.rssItems = rssItems;
    }

    public List<SyndEntry> getRssItems() {
        return Collections.unmodifiableList(rssItems);
    }

    public String getRssUrl() {
        return rssUrl;
    }
}