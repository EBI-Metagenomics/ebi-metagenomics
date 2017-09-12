package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Joins pipeline release versions with pipeline tool versions.
 */
@Embeddable
public class StudySampleId implements Serializable {

    // Class inspired by:
    // http://www.mkyong.com/hibernate/hibernate-many-to-many-example-join-table-extra-column-annotation/

    @ManyToOne
    private Study study;

    @ManyToOne
    private Sample sample;

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudySampleId that = (StudySampleId) o;

        if (study != null ? !study.equals(that.study) : that.study != null) return false;
        if (sample != null ? !sample.equals(that.sample) : that.sample != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (study != null ? study.hashCode() : 0);
        result = 31 * result + (sample != null ? sample.hashCode() : 0);
        return result;
    }
}
