package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Joins pipeline release versions with pipeline tool versions.
 */
@Embeddable
public class PipelineReleaseToolId implements Serializable {

    // Class inspired by:
    // http://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/

    @ManyToOne
    private PipelineRelease pipelineRelease;

    @ManyToOne
    private PipelineTool pipelineTool;

    public PipelineRelease getPipelineRelease() {
        return pipelineRelease;
    }

    public void setPipelineRelease(PipelineRelease pipelineRelease) {
        this.pipelineRelease = pipelineRelease;
    }

    public PipelineTool getPipelineTool() {
        return pipelineTool;
    }

    public void setPipelineTool(PipelineTool pipelineTool) {
        this.pipelineTool = pipelineTool;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PipelineReleaseToolId that = (PipelineReleaseToolId) o;

        if (pipelineRelease != null ? !pipelineRelease.equals(that.pipelineRelease) : that.pipelineRelease != null) return false;
        if (pipelineTool != null ? !pipelineTool.equals(that.pipelineTool) : that.pipelineTool != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (pipelineRelease != null ? pipelineRelease.hashCode() : 0);
        result = 31 * result + (pipelineTool != null ? pipelineTool.hashCode() : 0);
        return result;
    }
}
