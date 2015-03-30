package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Joins pipeline release versions with pipeline tool versions, along with any extra information.
 */
@Entity
@Table(name = "PIPELINE_RELEASE_TOOL")
@AssociationOverrides({
        @AssociationOverride(name = "pk.pipelineRelease",
                joinColumns = @JoinColumn(name = "PIPELINE_ID")),
        @AssociationOverride(name = "pk.pipelineTool",
                joinColumns = @JoinColumn(name = "TOOL_ID")) })
public class PipelineReleaseTool implements Serializable {

    // Class inspired by:
    // http://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/

    @EmbeddedId
    private PipelineReleaseToolId pk = new PipelineReleaseToolId();

    // An extra column on the pipeline release/tools join table
    @Column(name = "TOOL_GROUP_ID", nullable = false, precision = 6, scale = 3)
    private BigDecimal toolGroupId;

    // An extra column on the pipeline release/tools join table
    @Column(name = "HOW_TOOL_USED_DESC", nullable = false, length = 1000)
    private String howToolUsedDesc;

    public PipelineReleaseTool() {
    }

    public PipelineReleaseToolId getPk() {
        return pk;
    }

    public void setPk(PipelineReleaseToolId pk) {
        this.pk = pk;
    }

    @Transient
    public PipelineRelease getPipelineRelease() {
        return getPk().getPipelineRelease();
    }

    public void setPipelineRelease(PipelineRelease pipelineRelease) {
        getPk().setPipelineRelease(pipelineRelease);
    }

    @Transient
    public PipelineTool getPipelineTool() {
        return getPk().getPipelineTool();
    }

    public void setPipelineTool(PipelineTool pipelineTool) {
        getPk().setPipelineTool(pipelineTool);
    }

    public BigDecimal getToolGroupId() {
        return toolGroupId;
    }

    public void setToolGroupId(BigDecimal toolGroupId) {
        this.toolGroupId = toolGroupId;
    }

    public String getHowToolUsedDesc() {
        return howToolUsedDesc;
    }

    public void setHowToolUsedDesc(String howToolUsedDesc) {
        this.howToolUsedDesc = howToolUsedDesc;
    }

    @Transient
    public int getToolGroupMajorId() {
        return toolGroupId.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PipelineReleaseTool that = (PipelineReleaseTool) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }

    public static class PipelineReleaseToolComparator implements Comparator<PipelineReleaseTool> {

        // Use of comparator inspired by http://www.codereye.com/2009/05/hibernate-annotations-sort-using.html

        @Override
        public int compare(PipelineReleaseTool prt1, PipelineReleaseTool prt2) {
            if (prt1 == prt2) return 0;

            // First sort by pipeline ID ascending
            long thisPipelineId = prt1.getPipelineRelease().getPipelineId();
            long thatPipelineId = prt2.getPipelineRelease().getPipelineId();
            if (thisPipelineId < thatPipelineId) return -1;
            if (thisPipelineId > thatPipelineId) return 1;

            // Sort by pipeline tool group ID ascending
            if (prt1.getToolGroupId().doubleValue() < prt2.getToolGroupId().doubleValue()) return -1;
            if (prt1.getToolGroupId().doubleValue() > prt2.getToolGroupId().doubleValue()) return 1;

            return 0;
        }

    }

}
