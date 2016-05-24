package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

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
    @Column(name = "PIPELINE_ID", columnDefinition = "TINYINT(4)")
    private int pipelineId;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "CHANGES", nullable = false, columnDefinition = "TEXT")
    private String changes;

    @Column(name = "RELEASE_VERSION", length = 20, nullable = false)
    private String releaseVersion;

    @Temporal(TemporalType.DATE)
    @Column(name = "RELEASE_DATE", nullable = false)
    private Calendar releaseDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.pipelineRelease")
    @Sort(type = SortType.COMPARATOR, comparator = PipelineReleaseTool.PipelineReleaseToolComparator.class)
    private SortedSet<PipelineReleaseTool> pipelineReleaseTools;

    public PipelineRelease() {
    }

    public PipelineRelease(String changes, String releaseVersion, Calendar releaseDate, SortedSet<PipelineReleaseTool> pipelineReleaseTools) {
        this.changes = changes;
        this.releaseVersion = releaseVersion;
        this.releaseDate = releaseDate;
        this.pipelineReleaseTools = pipelineReleaseTools;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        return formatter.format(releaseDate.getTime());
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<PipelineReleaseTool> getPipelineReleaseTools() {
        return pipelineReleaseTools;
    }

    public void setPipelineReleaseTools(SortedSet<PipelineReleaseTool> pipelineReleaseTools) {
        this.pipelineReleaseTools = pipelineReleaseTools;
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