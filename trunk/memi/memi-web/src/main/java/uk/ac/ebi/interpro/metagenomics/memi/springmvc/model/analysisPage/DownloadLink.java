package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import java.util.Comparator;

/**
 * Simple download link object .Used to render the download section on the analysis page.
 *
 * @author Maxim Scheremetjew
 */
public class DownloadLink {
    /*The following 3 attributes are mandatory anchor attributes. */
    private String linkText;
    private String linkTitle;
    private String linkURL;
    /*Mandatory. Default is FALSE. */
    private boolean isExternalLink;
    //Mandatory. This attribute is used to give an order to a list of links. 1 is highest priority
    private int order;
    /* Optional. If the link is for a downloadable file it will hold the file size. */
    private String fileSize;

    public DownloadLink(String linkText, String linkTitle, String linkURL, int order, String fileSize) {
        this(linkText, linkTitle, linkURL, false, order, fileSize);
    }

    public DownloadLink(String linkText, String linkTitle, String linkURL, boolean isExternalLink, int order) {
        this(linkText, linkTitle, linkURL, isExternalLink, order, null);
    }

    public DownloadLink(String linkText, String linkTitle, String linkURL, boolean isExternalLink, int order, String fileSize) {
        this.linkText = linkText;
        this.linkTitle = linkTitle;
        this.linkURL = linkURL;
        this.isExternalLink = isExternalLink;
        this.order = order;
        this.fileSize = fileSize;
    }

    public String getLinkText() {
        return linkText;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public String getLinkURL() {
        return linkURL;
    }

    public boolean isExternalLink() {
        return isExternalLink;
    }

    public int getOrder() {
        return order;
    }

    public String getFileSize() {
        return fileSize;
    }

    /**
     * Compares download links by the order property.
     */
    public static Comparator<DownloadLink> DownloadLinkComparator
            = new Comparator<DownloadLink>() {
        public int compare(DownloadLink o1, DownloadLink o2) {
            return o1.getOrder() - o2.getOrder();
        }
    };
}