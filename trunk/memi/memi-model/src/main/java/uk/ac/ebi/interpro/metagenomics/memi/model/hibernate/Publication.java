package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;


import javax.persistence.*;

/**
 * TODO: Description
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Entity
public class Publication {

    @Id
    @GeneratedValue
    @Column(name = "PUB_ID")
    private long pubId;

    @Column(name = "PUB_TYPE", nullable = false)
    private char pubType;

    @Column(name = "ISBN", length = 10)
    private String isbn;

    @Column(name = "VOLUME", length = 55)
    private String volume;

    @Column(name = "ISSUE", length = 55)
    private String issue;

    @Column(name = "YEAR", length = 4)
    private int year;

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
    private int pubMedId;

    @Column(name = "PUBMED_CENTRAL_ID", length = 10)
    private int pubMedCentralId;

    @Column(name = "PUB_ABSTRACT")
    @Lob
    private String pubAbstract;

    @Column(name = "VIEW_TYPE")
    private ViewType viewType;


    public long getPubId() {
        return pubId;
    }

    public void setPubId(long pubId) {
        this.pubId = pubId;
    }

    public int getPubMedId() {
        return pubMedId;
    }

    public void setPubMedId(int pubMedId) {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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

    public int getPubMedCentralId() {
        return pubMedCentralId;
    }

    public void setPubMedCentralId(int pubMedCentralId) {
        this.pubMedCentralId = pubMedCentralId;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public enum ViewType {
        REF_LINE, DOI, URL, PUBMED_ID, PMID, PMC;
    }
}