package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import org.springframework.beans.factory.annotation.Required;

/**
 * @author Maxim Scheremetjew
 */
public abstract class DownloadableFileDefinition implements IResultFileDefinition, Cloneable {
    private String identifier;

    private String description;

    /**
     * Is used to build the absolute file path.
     */
    private String relativePath;

    /**
     * If relative file path does not exist, this should be set. Is used to build the absolute file path.
     * e.g. '_InterPro.tsv'
     */
    private String fileNameEnding;

    private String downloadName;

    private String linkText;

    private String linkTitle;

    private String linkURL;

    private int order;

    /**
     * Defines the version compatibility. This file definition is supported for this version and higher.
     */
    private String releaseVersion;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    @Required
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadName() {
        return downloadName;
    }

    @Required
    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public String getLinkText() {
        return linkText;
    }

    @Required
    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    @Required
    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public int getOrder() {
        return order;
    }

    @Required
    public void setOrder(int order) {
        this.order = order;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getFileNameEnding() {
        return fileNameEnding;
    }

    public void setFileNameEnding(String fileNameEnding) {
        this.fileNameEnding = fileNameEnding;
    }

    public String getLinkURL() {
        return linkURL;
    }

    @Required
    public void setLinkURL(String linkURL) {
        this.linkURL = linkURL;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public DownloadableFileDefinition clone() {
        try {
            return (DownloadableFileDefinition) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}