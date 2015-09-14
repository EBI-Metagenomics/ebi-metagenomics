package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Represents an experiment type object as in EMG.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.5-SNAPSHOT
 */
@Entity
@Table(name = "EXPERIMENT_TYPE")
public class ExperimentType {

    @Id
    @Column(name = "EXPERIMENT_TYPE_ID")
    private int experimentTypeId;

    @Column(name = "EXPERIMENT_TYPE", length = 30, nullable = false)
    private String experimentType;

    public ExperimentType() {
    }

    public int getExperimentTypeId() {
        return experimentTypeId;
    }

    public void setExperimentTypeId(int experimentTypeId) {
        this.experimentTypeId = experimentTypeId;
    }

    public String getExperimentType() {
        return experimentType;
    }

    public void setExperimentType(String experimentType) {
        this.experimentType = experimentType;
    }
}