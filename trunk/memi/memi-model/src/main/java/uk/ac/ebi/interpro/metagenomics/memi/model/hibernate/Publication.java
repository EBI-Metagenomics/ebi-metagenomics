package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

import javax.persistence.*;


/**
 * Hibernate generated publication table.
 * TODO: Check why SequenceGenerator parameter allocationSize does not work
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Entity
@Table(name = "HB_PUBLICATION")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PUB_SEQ")
    @Column(name = "PUB_ID")
    @SequenceGenerator(
            name = "PUB_SEQ",
            sequenceName = "PUBLICATION_SEQ")
//        allocationSize = 1
    private long pubId;

    @Column(name = "PUB_TYPE", nullable = false)
    private char pubType;

    @Column(name = "ISBN", length = 10)
    private String isbn;

    @Column(name = "VOLUME", length = 55)
    private String volume;

    @Column(name = "ISSUE", length = 55)
    private String issue;

    @Column(name = "PUBLISHED_YEAR", length = 4)
    private Integer year;

    @Column(name = "PUB_TITLE", length = 740)
    private String pubTitle;

    @Column(name = "URL", length = 740)
    private String url;

    @Column(name = "RAW_PAGES", length = 30)
    private String rawPages;

    @Column(name = "MEDLINE_JOURNAL")
    private String medlineJournal;

    @Column(name = "ISO_JOURNAL")
    private String isoJournal;

    /**
     * Comma separated list of authors
     */
    @Column(name = "AUTHORS", length = 4000)
    private String authors;

    @Column(name = "DOI", length = 1500)
    private String doi;

    @Column(name = "PUBMED_ID", length = 10)
    private Integer pubMedId;

    @Column(name = "PUBMED_CENTRAL_ID", length = 10)
    private Integer pubMedCentralId;

    @Column(name = "PUB_ABSTRACT")
    @Lob
    private String pubAbstract;

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

    public char getPubType() {
        return pubType;
    }

    public void setPubType(char pubType) {
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

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Integer getPubMedCentralId() {
        return pubMedCentralId;
    }

    public void setPubMedCentralId(Integer pubMedCentralId) {
        this.pubMedCentralId = pubMedCentralId;
    }
}