package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import com.sun.syndication.feed.synd.SyndEntry;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.*;

/**
 * Represents the model for the home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public final class HomePageViewModel extends ViewModel {

    /**
     * Maps studies and their number of samples.
     */
    private Map<Study, Long> publicStudiesMap;

    private List<Sample> publicSamples;

    /**
     * Maps studies and their number of samples.
     */
    private Map<Study, Long> myStudiesMap;

    private List<Sample> mySamples;

    private final String rssUrl;

    private final List<SyndEntry> rssItems;

    /**
     * The number of latest project and samples to show on the home page.
     */
    private final int maxRowNumberOfLatestItems;

    public HomePageViewModel(Submitter submitter, Map<Study, Long> publicStudiesMap, List<Sample> publicSamples,
                             String rssUrl, List<SyndEntry> rssItems, String pageTitle, List<Breadcrumb> breadcrumbs,
                             MemiPropertyContainer propertyContainer, int maxRowNumberOfLatestItems) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.publicStudiesMap = publicStudiesMap;
        this.publicSamples = publicSamples;
        this.myStudiesMap = new TreeMap<Study, Long>();
        this.mySamples = new ArrayList<Sample>();
        this.rssUrl = rssUrl;
        this.rssItems = rssItems;
        this.maxRowNumberOfLatestItems = maxRowNumberOfLatestItems;
    }

    public HomePageViewModel(Submitter submitter, Map<Study, Long> publicStudiesMap, List<Sample> publicSamples,
                             String rssUrl, List<SyndEntry> rssItems, Map<Study, Long> myStudiesMap,
                             List<Sample> mySamples, String pageTitle, List<Breadcrumb> breadcrumbs,
                             MemiPropertyContainer propertyContainer, int maxRowNumberOfLatestItems) {
        this(submitter, publicStudiesMap, publicSamples, rssUrl, rssItems, pageTitle, breadcrumbs, propertyContainer, maxRowNumberOfLatestItems);
        this.myStudiesMap = myStudiesMap;
        this.mySamples = mySamples;
    }

    public Map<Study, Long> getPublicStudiesMap() {
        return publicStudiesMap;
    }

    public void setPublicStudiesMap(Map<Study, Long> publicStudiesMap) {
        this.publicStudiesMap = publicStudiesMap;
    }

    public Map<Study, Long> getMyStudiesMap() {
        return myStudiesMap;
    }

    public void setMyStudiesMap(Map<Study, Long> myStudiesMap) {
        this.myStudiesMap = myStudiesMap;
    }

    public List<Sample> getPublicSamples() {
        return publicSamples;
    }

    public void setPublicSamples(List<Sample> publicSamples) {
        this.publicSamples = publicSamples;
    }


    public List<Sample> getMySamples() {
        return mySamples;
    }

    public void setMySamples(List<Sample> mySamples) {
        this.mySamples = mySamples;
    }

    public List<SyndEntry> getRssItems() {
        return Collections.unmodifiableList(rssItems);
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public int getMaxRowNumberOfLatestItems() {
        return maxRowNumberOfLatestItems;
    }
}
