package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import com.sun.syndication.feed.synd.SyndEntry;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Represents the model for the home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
public final class HomePageModel extends MGModel {

    /**
     * Maps studies and their number of samples.
     */
    private SortedMap<Study, Long> publicStudiesMap;

    private List<Sample> publicSamples;

    /**
     * Maps studies and their number of samples.
     */
    private SortedMap<Study, Long> myStudiesMap;

    private List<Sample> mySamples;

    private final String rssUrl;
    private final List<SyndEntry> rssItems;

    HomePageModel(Submitter submitter, SortedMap<Study, Long> publicStudiesMap, List<Sample> publicSamples, String rssUrl, List<SyndEntry> rssItems, List<Breadcrumb> breadcrumbs) {
        super(submitter, breadcrumbs);
        this.publicStudiesMap = publicStudiesMap;
        this.publicSamples = publicSamples;
        this.myStudiesMap = new TreeMap<Study, Long>();
        this.mySamples = new ArrayList<Sample>();
        this.rssUrl = rssUrl;
        this.rssItems = rssItems;
    }

    HomePageModel(Submitter submitter, SortedMap<Study, Long> publicStudiesMap, List<Sample> publicSamples, String rssUrl, List<SyndEntry> rssItems, SortedMap<Study, Long> myStudiesMap, List<Sample> mySamples, List<Breadcrumb> breadcrumbs) {
        this(submitter, publicStudiesMap, publicSamples, rssUrl, rssItems, breadcrumbs);
        this.myStudiesMap = myStudiesMap;
        this.mySamples = mySamples;
    }

    public SortedMap<Study, Long> getPublicStudiesMap() {
        return publicStudiesMap;
    }

    public void setPublicStudiesMap(SortedMap<Study, Long> publicStudiesMap) {
        this.publicStudiesMap = publicStudiesMap;
    }

    public SortedMap<Study, Long> getMyStudiesMap() {
        return myStudiesMap;
    }

    public void setMyStudiesMap(SortedMap<Study, Long> myStudiesMap) {
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
}
