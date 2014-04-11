package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "STUDY_XREF")
public class StudyXref {
    @Id
    @Column(name = "STUDY_XREF_ID", length = 50)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMGUSER_SEQ")
//    @SequenceGenerator(
//            name = "EMGUSER_SEQ",
//            sequenceName = "EMGUSER_SEQ")
    private String studyXrefId;

    @Column(name = "SOURCE", nullable = false, length = 512)
    @Enumerated(EnumType.STRING)
    private ExternalResourceType source;

    @Column(name = "PRIMARY", nullable = false)
    private boolean primary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STUDY_ID", nullable = false, insertable = false, updatable = false)
    @ForeignKey(name = "STUDY_XREF_FK1", inverseName = "STUDY_ID")
    private Study study;

    @ManyToMany
    @JoinTable(
            name = "EMGUSER_XREF_STUDY_XREF",
            joinColumns = {@JoinColumn(name = "STUDY_XREF_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EMGUSER_XREF_ID")}
    )
    private Set<ExternalResourceLogin> logins;

    public String getStudyXrefId() {
        return studyXrefId;
    }

    public void setStudyXrefId(String studyXrefId) {
        this.studyXrefId = studyXrefId;
    }

    public ExternalResourceType getSource() {
        return source;
    }

    public void setSource(ExternalResourceType source) {
        this.source = source;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public Set<ExternalResourceLogin> getLogins() {
        return logins;
    }

    public void setLogins(Set<ExternalResourceLogin> logins) {
        this.logins = logins;
    }
}
