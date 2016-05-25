package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Represents an analysis status object.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.3-SNAPSHOT
 */
@Entity
@Table(name = "ANALYSIS_STATUS")
public class AnalysisStatus {

    @Id
    @Column(name = "ANALYSIS_STATUS_ID", columnDefinition = "TINYINT(4)")
    private int analysisStatusId;

    @Column(name = "ANALYSIS_STATUS", length = 15, nullable = false)
    private String analysisStatus;

    public AnalysisStatus() {
    }

    public int getAnalysisStatusId() {
        return analysisStatusId;
    }

    public void setAnalysisStatusId(int analysisStatusId) {
        this.analysisStatusId = analysisStatusId;
    }

    public String getAnalysisStatus() {
        return analysisStatus;
    }

    public void setAnalysisStatus(String analysisStatus) {
        this.analysisStatus = analysisStatus;
    }
}