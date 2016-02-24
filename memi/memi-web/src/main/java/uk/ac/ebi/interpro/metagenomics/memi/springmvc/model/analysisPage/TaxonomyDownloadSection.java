package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Object which is used to build the download section on the analysis page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI
 */
public class TaxonomyDownloadSection {

    private List<DownloadLink> downloadLinks;

    // Default value is False, will be changed to True once sorted
    private boolean isSorted = false;

    public TaxonomyDownloadSection() {
        this.downloadLinks = new ArrayList<DownloadLink>();
    }

    public void addDownloadLink(DownloadLink downloadLink) {
        this.downloadLinks.add(downloadLink);
    }

    public List<DownloadLink> getDownloadLinks() {
        if (!isSorted) {
            Collections.sort(downloadLinks, DownloadLink.DownloadLinkComparator);
            this.isSorted = true;
        }
        return downloadLinks;
    }
}