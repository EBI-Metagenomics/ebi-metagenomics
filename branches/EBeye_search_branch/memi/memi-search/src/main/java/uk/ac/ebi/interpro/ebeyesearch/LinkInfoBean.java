package uk.ac.ebi.interpro.ebeyesearch;

/**
 * A Java bean to hold information about a link (search pagination) on the user interface.
 *
 * @author Matthew Fraser, EMBL-EBI, InterPro
 * @version $Id: LinkInfoBean.java,v 1.2 2013/02/07 14:48:17 nuka Exp $
 * @since 1.0-SNAPSHOT
 */
public class LinkInfoBean {
    private String name; // The link text shown to the user
    private String description; // Extra text description about the link (e.g. for use in title text)
    private String link; // The URL pattern to link to

    private String className =""; //class for the page link

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
