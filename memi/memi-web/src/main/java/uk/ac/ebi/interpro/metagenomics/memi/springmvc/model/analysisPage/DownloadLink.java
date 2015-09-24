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

    /**
     * Use this attribute to render chunked dowbloadable files.
     * To give you an example of the structure of a download link for chunked files:
     * link-prefix (file format) : link-text (file size)
     * <p/>
     * Here is an example:
     * <p/>
     * InterPro matches (TSV) : Part 1 (492 MB)
     */
    private String linkPrefix;

    public DownloadLink(String linkText, String linkTitle, String linkURL, int order, String fileSize, String linkPrefix) {
        this(linkText, linkTitle, linkURL, false, order, fileSize, linkPrefix);
    }

    public DownloadLink(String linkText, String linkTitle, String linkURL, int order, String fileSize) {
        this(linkText, linkTitle, linkURL, false, order, fileSize, null);
    }

    public DownloadLink(String linkText, String linkTitle, String linkURL, boolean isExternalLink, int order) {
        this(linkText, linkTitle, linkURL, isExternalLink, order, null, null);
    }

    public DownloadLink(String linkText, String linkTitle, String linkURL, boolean isExternalLink, int order, String fileSize) {
        this(linkText, linkTitle, linkURL, isExternalLink, order, fileSize, null);
    }

    public DownloadLink(String linkText, String linkTitle, String linkURL, boolean isExternalLink, int order, String fileSize, String linkPrefix) {
        this.linkText = linkText;
        this.linkTitle = linkTitle;
        this.linkURL = linkURL;
        this.isExternalLink = isExternalLink;
        this.order = order;
        this.fileSize = fileSize;
        this.linkPrefix = linkPrefix;
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

    public String getLinkPrefix() {
        return linkPrefix;
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