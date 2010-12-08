package uk.ac.ebi.interpro.metagenomics.memi.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a news object. There will be an area on the MG portal home page where the lastest news will be accessible.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class News implements Serializable {

    private long newsId;
    /* Date of announcement*/
    private Date announcedDate;

    private Date latestUpdate;

    private String newsHeadline;

    /* News message */
    private String newsMsg;

    /* Full name of the editor */
    private String editor;

    public News() {
        this.announcedDate = new Date();
        this.latestUpdate = new Date();
    }

    public News(String newsHeadline, String newsMsg) {
        this();
        this.newsHeadline = newsHeadline;
        this.newsMsg = newsMsg;
    }


    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public Date getAnnouncedDate() {
        return announcedDate;
    }

    public String getFormattedAnnouncedDate() {
        return new SimpleDateFormat("dd.MMM.yy").format(announcedDate);
    }

    public void setAnnouncedDate(Date announcedDate) {
        this.announcedDate = announcedDate;
    }

    public Date getLatestUpdate() {
        return latestUpdate;
    }

    public String getFormattedLatestUpdate() {
        return new SimpleDateFormat("dd.MMM.yy").format(latestUpdate);
    }

    public void setLatestUpdate(Date latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public String getNewsHeadline() {
        return newsHeadline;
    }

    public void setNewsHeadline(String newsHeadline) {
        this.newsHeadline = newsHeadline;
    }

    public String getNewsMsg() {
        return newsMsg;
    }

    public void setNewsMsg(String newsMsg) {
        this.newsMsg = newsMsg;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}