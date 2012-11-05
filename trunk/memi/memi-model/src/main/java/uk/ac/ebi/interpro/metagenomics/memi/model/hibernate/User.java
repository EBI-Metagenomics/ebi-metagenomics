package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "EMGUSER")
//@TypeDef(
//        name = "encryptedString",
//        typeClass = EncryptedStringType.class,
//        parameters = {
//                @org.hibernate.annotations.Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")
//        }
//)
//TODO: Fix query
@NamedQueries(
        {
                @NamedQuery(name = User.DELETE_ALL_USERS, query = "delete from EMGUSER")
        }
)
public class User {

    public static final String DELETE_ALL_USERS = "delete.all.users";

    @Id
    @Column(name = "EMGUSER_ID")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMGUSER_SEQ")
//    @SequenceGenerator(
//            name = "EMGUSER_SEQ",
//            sequenceName = "EMGUSER_SEQ")
    private long id;

    @Column(name = "TITLE", length = 10)
    private String title;

    @Column(name = "FIRST_NAME", nullable = false, length = 20)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 20)
    private String lastName;

    @Column(name = "EMAIL_ADDRESS", nullable = false, length = 25)
    private String emailAddress;

    @Column(name = "PASSWD", nullable = false, length = 20)
    private String password;

    @Column(name = "IS_ADMIN", nullable = false)
    private boolean isAdmin;

    @Column(name = "IS_ACTIVATED", nullable = false)
    private boolean isActivated;

    @Column(name = "UUID", length = 20)
    private String uuid;

    @Column(name = "PHONE_NUMBER", length = 25)
    private String phoneNumber;

    @Column(name = "ALTERNATIVE_PHONE_NUMBER", length = 25)
    private String alternativePhoneNumber;

    @ManyToMany
    @JoinTable(
            name = "EMGUSER_STUDY",
            joinColumns = {@JoinColumn(name = "STUDY_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EMGUSER_ID")}
    )
    private Set<Study> studies;


    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(name = "EMGUSER_ID", nullable = false)
    private Set<ExternalResourceLogin> externalLogins;

    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(name = "ORGANISATION_ID", nullable = false)
    @ForeignKey(name = "FK1_EMGUSER", inverseName = "ORGANISATION_ID")
    private Organisation organisation;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlternativePhoneNumber() {
        return alternativePhoneNumber;
    }

    public void setAlternativePhoneNumber(String alternativePhoneNumber) {
        this.alternativePhoneNumber = alternativePhoneNumber;
    }

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }

    public void addStudy(Study study) {
        if (study != null) {
            if (studies == null) {
                studies = new HashSet<Study>();
            }
            studies.add(study);
        }
    }

    public Set<ExternalResourceLogin> getExternalLogins() {
        return externalLogins;
    }

    public void setExternalLogins(Set<ExternalResourceLogin> externalLogins) {
        this.externalLogins = externalLogins;
    }

    public void addExternalLogin(ExternalResourceLogin externalLogin) {
        if (externalLogin != null) {
            if (externalLogins == null) {
                externalLogins = new HashSet<ExternalResourceLogin>();
            }
            externalLogins.add(externalLogin);
        }
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
}