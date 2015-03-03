package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Comparator;


/**
 * Represents a pipeline tool object.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.3-SNAPSHOT
 */
@Entity
@Table(name = "PIPELINE_TOOL")
public class PipelineTool implements Comparator<PipelineTool> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOOL_SEQ")
    @Column(name = "TOOL_ID")
    @SequenceGenerator(
            name = "TOOL_SEQ",
            sequenceName = "PIPELINE_TOOL_SEQ")
    private long toolId;

    @Column(name = "TOOL_NAME", length = 30, nullable = false)
    private String toolName;

    @Column(name = "DESCRIPTION", length = 1000, nullable = false)
    private String description;

    @Column(name = "WEB_LINK", length = 500)
    private String webLink;

    @Column(name = "VERSION", length = 30, nullable = false)
    private String toolVersion;

    @Column(name = "EXE_COMMAND", length = 500, nullable = false)
    private String executionCmd;

    @Column(name = "INSTALLATION_DIR", length = 200)
    private String installationDir;

    @Column(name = "CONFIGURATION_FILE")
    @Lob
    private String configurationFile;

    @Column(name = "NOTES")
    @Lob
    private String notes;

    public PipelineTool() {
    }

    public PipelineTool(long toolId, String toolName, String toolVersion, String executionCmd, String installationDir, String configurationFile, String notes,
                        String description, String webLink) {
        this.toolId = toolId;
        this.toolName = toolName;
        this.toolVersion = toolVersion;
        this.executionCmd = executionCmd;
        this.installationDir = installationDir;
        this.configurationFile = configurationFile;
        this.notes = notes;
        this.description = description;
        this.webLink = webLink;
    }

    public long getToolId() {
        return toolId;
    }

    public void setToolId(long toolId) {
        this.toolId = toolId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolVersion() {
        return toolVersion;
    }

    public void setToolVersion(String toolVersion) {
        this.toolVersion = toolVersion;
    }

    public String getExecutionCmd() {
        return executionCmd;
    }

    public void setExecutionCmd(String executionCmd) {
        this.executionCmd = executionCmd;
    }

    public String getInstallationDir() {
        return installationDir;
    }

    public void setInstallationDir(String installationDir) {
        this.installationDir = installationDir;
    }

    public String getConfigurationFile() {
        return configurationFile;
    }

    public void setConfigurationFile(String configurationFile) {
        this.configurationFile = configurationFile;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    @Override
    public int hashCode() {
        int result = (int) (toolId ^ (toolId >>> 32));
        result = 31 * result + toolName.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (webLink != null ? webLink.hashCode() : 0);
        result = 31 * result + toolVersion.hashCode();
        result = 31 * result + executionCmd.hashCode();
        result = 31 * result + (installationDir != null ? installationDir.hashCode() : 0);
        result = 31 * result + (configurationFile != null ? configurationFile.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }

    //TODO: Implement
    public int compare(PipelineTool o1, PipelineTool o2) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        PipelineTool pt = (PipelineTool) obj;
        return new EqualsBuilder()
                .append(getToolId(), pt.getToolId())
                .append(getToolName(), pt.getToolName())
                .append(getDescription(),pt.getDescription())
                .append(getWebLink(),pt.getWebLink())
                .append(getToolVersion(), pt.getToolVersion())
                .append(getExecutionCmd(), pt.getExecutionCmd())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", getToolId()).
                append("name", getToolName()).
                append("description", getDescription()).
                append("webLink", getWebLink()).
                append("version", getToolVersion()).
                append("executionCommand", getExecutionCmd()).
                append("installDir", getInstallationDir()).
                append("configFile", getConfigurationFile()).
                append("notes", getNotes()).
                toString();
    }
}