package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.*;

//TODO: Add organisation site
@Entity
@Table(name = "ORGANISATION")
public class Organisation {
    @Id
    @Column(name = "ORGANISATION_ID")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STUDY_SEQ")
//    @SequenceGenerator(
//            name = "STUDY_SEQ",
//            sequenceName = "STUDY_SEQ")
////            allocationSize = 1
    private long id;

    @Column(name = "ORGANISATION_NAME", nullable = false, length = 30)
    private String organisationName;

    @Column(name = "ORGANISATION_TYPE", length = 30)
    @Enumerated(EnumType.STRING)
    private OrganisationType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public OrganisationType getType() {
        return type;
    }

    public void setType(OrganisationType type) {
        this.type = type;
    }
}
