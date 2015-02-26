package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Represents a pipeline release object.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.3-SNAPSHOT
 */
@Entity
@Table(name = "PIPELINE_RELEASE")
public class PipelineRelease implements Comparator<PipelineRelease> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PIPELINE_REL_SEQ")
    @Column(name = "PIPELINE_ID")
    @SequenceGenerator(
            name = "PIPELINE_REL_SEQ",
            sequenceName = "PIPELINE_RELEASE_SEQ")
    private long pipelineId;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "CHANGES", nullable = false)
    @Lob
    private String changes;

    @Column(name = "RELEASE_VERSION", length = 20, nullable = false)
    private String releaseVersion;

    @Temporal(TemporalType.DATE)
    @Column(name = "RELEASE_DATE", nullable = false)
    private Calendar releaseDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "PIPELINE_RELEASE_TOOL", joinColumns = {
            @JoinColumn(name = "PIPELINE_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "TOOL_ID",
                    nullable = false, updatable = false) })
    private Set<PipelineTool> pipelineTools;

    public PipelineRelease() {
    }

    public PipelineRelease(long pipelineId, String description, String changes, String releaseVersion, Calendar releaseDate) {
        this.pipelineId = pipelineId;
        this.description = description;
        this.changes = changes;
        this.releaseVersion = releaseVersion;
        this.releaseDate = releaseDate;
    }

    public long getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(long pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }

    public String getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(String releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public String getReleaseDate() {
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(releaseDate.getTime());
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<PipelineTool> getPipelineTools() {
        return pipelineTools;
    }

    public void setPipelineTools(Set<PipelineTool> pipelineTools) {
        this.pipelineTools = pipelineTools;
    }

    public void addPipelineTool(PipelineTool pipelineTool) {
        if (pipelineTool != null) {
            if (pipelineTools == null) {
                pipelineTools = new HashSet<PipelineTool>();
            }
            pipelineTools.add(pipelineTool);
        }
    }

    @Override
    public int hashCode() {
        int result = (int) (pipelineId ^ (pipelineId >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + changes.hashCode();
        result = 31 * result + releaseVersion.hashCode();
        result = 31 * result + releaseDate.hashCode();
        return result;
    }

    //    @Override
//    public int hashCode() {
//        return new HashCodeBuilder(83, 23).
//                append(getPubType()).
//                append(getAuthors()).
//                append(getPubTitle()).
//                append(getDoi()).
//                append(getIsbn()).
//                append(getYear()).
//                append(getVolume()).
//                toHashCode();
//    }


    //TODO: Implement
    public int compare(PipelineRelease o1, PipelineRelease o2) {
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
        PipelineRelease pp = (PipelineRelease) obj;
        return new EqualsBuilder()
                .append(getPipelineId(), pp.getPipelineId())
                .append(getDescription(), pp.getDescription())
                .append(getChanges(), pp.getChanges())
                .append(getReleaseDate(), pp.getReleaseDate())
                .append(getReleaseVersion(), pp.getReleaseVersion())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", getPipelineId()).
                append("desc", getDescription()).
                append("changes", getChanges()).
                append("version", getReleaseVersion()).
                append("releaseDate", getReleaseDate()).
                toString();
    }
}