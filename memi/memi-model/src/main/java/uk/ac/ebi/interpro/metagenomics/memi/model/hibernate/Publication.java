package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Comparator;


/**
 * Represents a publication object. Please notice, this table is a copy of already existing publication table in InterPro.
 * The only difference is that the publication type was transformed from a char type to a enum.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Entity
@Table(name = "PUBLICATION")
public class Publication implements Comparator<Publication> {

    @Id
    @Column(name = "PUB_ID", columnDefinition = "INT(11)")
    private long pubId;

    @Column(name = "PUB_TYPE", length = 100, nullable = false)
    private String pubType;

    @Column(name = "ISBN", length = 10)
    private String isbn;

    @Column(name = "VOLUME", length = 55)
    private String volume;

    @Column(name = "ISSUE", length = 55)
    private String issue;

    @Column(name = "PUBLISHED_YEAR", columnDefinition = "SMALLINT(6)")
    private Integer year;

    @Column(name = "PUB_TITLE", length = 740, nullable = false)
    private String pubTitle;

    @Column(name = "URL", length = 740)
    private String url;

    @Column(name = "RAW_PAGES", length = 30)
    private String rawPages;

    @Column(name = "MEDLINE_JOURNAL", length = 255)
    private String medlineJournal;

    @Column(name = "ISO_JOURNAL", length = 255)
    private String isoJournal;

    /**
     * Comma separated list of authors
     */
    @Column(name = "AUTHORS", length = 4000)
    private String authors;

    @Column(name = "DOI", length = 1500)
    private String doi;

    @Column(name = "PUBMED_ID", columnDefinition = "INT(11)")
    private Integer pubMedId;

    @Column(name = "PUBMED_CENTRAL_ID", columnDefinition = "INT(11)")
    private Integer pubMedCentralId;

    @Column(name = "PUB_ABSTRACT")
    @Lob
    private String pubAbstract;

    protected Publication() {
    }

    public Publication(String pubType, String isbn, String volume, Integer year, String pubTitle, String authors, String doi) {
        this.pubType = pubType;
        this.isbn = isbn;
        this.volume = volume;
        this.year = year;
        this.pubTitle = pubTitle;
        this.authors = authors;
        this.doi = doi;
    }

    public long getPubId() {
        return pubId;
    }

    public void setPubId(long pubId) {
        this.pubId = pubId;
    }

    public Integer getPubMedId() {
        return pubMedId;
    }

    public void setPubMedId(Integer pubMedId) {
        this.pubMedId = pubMedId;
    }

    public String getPubAbstract() {
        return pubAbstract;
    }

    public void setPubAbstract(String pubAbstract) {
        this.pubAbstract = pubAbstract;
    }

    public String getPubTitle() {
        return pubTitle;
    }

    public void setPubTitle(String pubTitle) {
        this.pubTitle = pubTitle;
    }

    public String getPubType() {
        return pubType;
    }

    public void setPubType(String pubType) {
        this.pubType = pubType;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRawPages() {
        return rawPages;
    }

    public void setRawPages(String rawPages) {
        this.rawPages = rawPages;
    }

    public String getMedlineJournal() {
        return medlineJournal;
    }

    public void setMedlineJournal(String medlineJournal) {
        this.medlineJournal = medlineJournal;
    }

    public String getIsoJournal() {
        return isoJournal;
    }

    public void setIsoJournal(String isoJournal) {
        this.isoJournal = isoJournal;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getAuthors() {
        return authors;
    }

    /**
     * Returns a maximum of 5 authors.
     */
    @Transient
    public String getShortAuthors() {
        if (authors != null && authors.length() > 0) {
            int pos = getNthOccurrence(authors, ',', 5);
            if (pos == -1) {
                return getAuthors();
            } else {
                return getAuthors().substring(0, pos);
            }
        }
        return null;
    }

    /**
     * Returns the nth occurrence of a specified character (c) in a specified string (str).
     *
     * @param str The string to look for the occurrence of character c.
     * @param c   The character who's occurrence is of interest
     * @param nth The nth occurrence.
     */
    @Transient
    protected int getNthOccurrence(String str, char c, int nth) {
        if (str != null) {
            int pos = str.indexOf(c, 0);
            while (nth-- > 1 && pos != -1)
                pos = str.indexOf(c, pos + 1);
            return pos;
        }
        return -1;
    }


    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Integer getPubMedCentralId() {
        return pubMedCentralId;
    }

    public void setPubMedCentralId(Integer pubMedCentralId) {
        this.pubMedCentralId = pubMedCentralId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(83, 23).
                append(getPubType()).
                append(getAuthors()).
                append(getPubTitle()).
                append(getDoi()).
                append(getIsbn()).
                append(getYear()).
                append(getVolume()).
                toHashCode();
    }


    @Override
    public int compare(Publication o1, Publication o2) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Publication pub = (Publication) obj;
        return new EqualsBuilder()
//                .appendSuper(super.equals(obj))
                .append(getPubType(), pub.getPubType())
                .append(getAuthors(), pub.getAuthors())
                .append(getPubTitle(), pub.getPubTitle())
                .append(getDoi(), pub.getDoi())
                .append(getIsbn(), pub.getIsbn())
                .append(getYear(), pub.getYear())
                .append(getVolume(), pub.getVolume())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", getPubId()).
                append("title", getPubTitle()).
                append("type", getPubType()).
                append("authors", getAuthors()).
                toString();
    }
}