package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;

/**
 * Joins pipeline release versions with pipeline tool versions, along with any extra information.
 */
@Entity
@Table(name = "STUDY_SAMPLE")
@AssociationOverrides({
        @AssociationOverride(name = "pk.study",
                joinColumns = @JoinColumn(name = "STUDY_ID")),
        @AssociationOverride(name = "pk.sample",
                joinColumns = @JoinColumn(name = "SAMPLE_ID"))})
public class StudySample implements Serializable {

    // Class inspired by:
    // http://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/

    @EmbeddedId
    private StudySampleId pk = new StudySampleId();

    public StudySample() {
    }

    public StudySampleId getPk() {
        return pk;
    }

    public void setPk(StudySampleId pk) {
        this.pk = pk;
    }

    @Transient
    public Study getStudy() {
        return getPk().getStudy();
    }

    public void setStudy(Study study) {
        getPk().setStudy(study);
    }

    @Transient
    public Sample getSample() {
        return getPk().getSample();
    }

    public void setSample(Sample sample) {
        getPk().setSample(sample);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        StudySample that = (StudySample) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }

    public static class StudySampleComparator implements Comparator<StudySample> {

        // Use of comparator inspired by http://www.codereye.com/2009/05/hibernate-annotations-sort-using.html

        @Override
        public int compare(StudySample prt1, StudySample prt2) {
            if (prt1 == prt2) return 0;

            // First sort by pipeline ID ascending
            long thisPipelineId = prt1.getStudy().getId();
            long thatPipelineId = prt2.getStudy().getId();
            if (thisPipelineId < thatPipelineId) return -1;
            if (thisPipelineId > thatPipelineId) return 1;

            // Sort by pipeline tool group ID ascending
            if (prt1.getSample().getId() < prt2.getSample().getId()) return -1;
            if (prt1.getSample().getId() > prt2.getSample().getId()) return 1;

            return 0;
        }

    }

}
