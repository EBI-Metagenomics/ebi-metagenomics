package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

/**
 * User: Matthew Fraser
 * Date: 09-Mar-2011
 * Time: 15:05:06
 */
public class Breadcrumb {
    private static final int maxLinkNameLength = 60; // Max number of characters that a breadcrumb link can have
    private String linkName;
    private String linkDescription;
    private String url;

    public Breadcrumb(String linkName, String linkDescription, String url) {
        this.linkName = shortenLinkName(linkName);
        this.linkDescription = linkDescription;
        this.url = url;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = shortenLinkName(linkName);
    }

    public String getLinkDescription() {
        return linkDescription;
    }

    public void setLinkDescription(String linkDescription) {
        this.linkDescription = linkDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Enforce a maximum string length, e.g. "Test String" shortened to "Test S..." if too long!
     * @param linkName String to shorten
     * @return The shortened string
     */
    private String shortenLinkName(String linkName) {
        if (!linkName.equals(null) && linkName.length() > maxLinkNameLength) {
            linkName =  linkName.substring(0, maxLinkNameLength-3) + "...";
        }
        return linkName;
    }
}
