package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Entity
@Table(name = "EMGUSER_XREF")
public class ExternalResourceLogin {

    @Id
    @Column(name = "EMGUSER_XREF_ID")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMGUSER_SEQ")
//    @SequenceGenerator(
//            name = "EMGUSER_SEQ",
//            sequenceName = "EMGUSER_SEQ")
    private long emgUserXrefId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMGUSER_ID", nullable = false, insertable = false, updatable = false)
    @ForeignKey(name = "FK1_EMGUSER_XREF", inverseName = "EMGUSER_ID")
    private User user;

    @Column(name = "SOURCE", nullable = false, length = 25)
    @Enumerated(EnumType.STRING)
    private ExternalResourceType source;

    @Column(name = "LOGIN_ID", nullable = false, length = 20)
    private String loginId;

    @Column(name = "PASSWD", nullable = false, length = 15)
    private String password;

    public long getEmgUserXrefId() {
        return emgUserXrefId;
    }

    public void setEmgUserXrefId(long emgUserXrefId) {
        this.emgUserXrefId = emgUserXrefId;
    }

    public ExternalResourceType getSource() {
        return source;
    }

    public void setSource(ExternalResourceType source) {
        this.source = source;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}