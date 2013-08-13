package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage;

import org.springframework.beans.factory.annotation.Required;

/**
 * Simplest implementation of IResultFileDefinition.
 */
public class ResultFileDefinitionImpl implements IResultFileDefinition {
    private String identifier;

    private String description;

    private String fileNameEnding;

    /**
     * Is used to build the absolute file path.
     */
    private String relativePath;

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
}