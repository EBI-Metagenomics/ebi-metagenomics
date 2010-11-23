package uk.ac.ebi.interpro.metagenomics.memi.model;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a first simple version of a study (just a simple version).
 * TODO: Ask for a detailed specification of a study object
 * TODO: Implement JUni test for this class
 * TODO: Add Hibernate annotations
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class Study implements Serializable {
    private long studyId;

    private String studyName;

    private Date date;

    private boolean isPublic;

    private List<Sample> samples;

    public Study() {
        this.samples = new ArrayList<Sample>();
        this.date = new Date();
        this.isPublic = false;
    }

    public Study(String studyName, boolean isPublic) {
        this();
        this.studyName = studyName;
        this.isPublic = isPublic;
    }

    public long getStudyId() {
        return studyId;
    }

    public void setStudyId(long studyId) {
        this.studyId = studyId;
    }

    public String getStudyName() {
        return studyName;
    }

    public void setStudyName(String studyName) {
        this.studyName = studyName;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getDateStringFormatted() {
        Format formatter = new SimpleDateFormat("dd.MMM.yy");
        return formatter.format(date);
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public void addSample(Sample sample) {
        if (samples == null)
            samples = new ArrayList<Sample>();
        samples.add(sample);
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}