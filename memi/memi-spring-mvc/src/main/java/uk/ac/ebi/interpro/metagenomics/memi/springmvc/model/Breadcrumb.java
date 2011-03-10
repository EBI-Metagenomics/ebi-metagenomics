package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

/**
 * User: Matthew Fraser
 * Date: 09-Mar-2011
 * Time: 15:05:06
 */
public class Breadcrumb {
    private String linkName;
    private String linkDescription;
    private String url;

    public Breadcrumb(String linkName, String linkDescription, String url) {
        this.linkName = linkName;
        this.linkDescription = linkDescription;
        this.url = url;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
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
}
