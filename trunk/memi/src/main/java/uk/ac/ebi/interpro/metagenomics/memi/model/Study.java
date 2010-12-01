package uk.ac.ebi.interpro.metagenomics.memi.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
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

    private StudyType studyType;

    private Date releaseDate;

    private Date submitDate;

    private boolean isPublic;

    /**
     * Status within the analysis pipeline.
     */
    private StudyStatus analyseStatus;

    private List<Sample> samples;

    public Study() {
        this.samples = new ArrayList<Sample>();
        this.isPublic = false;
        this.studyType = StudyType.UNDEFINED;
        this.analyseStatus = StudyStatus.QUEUED;
        //Set start and end date using DateFormat
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {
            this.submitDate = f.parse("2009-09-10T10:00:00+0000");
            this.releaseDate = f.parse("2010-10-10T10:00:00+0000");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Study(String studyName, boolean isPublic) {
        this();
        this.studyName = studyName;
        this.isPublic = isPublic;
    }

    public Study(long studyId, String studyName, StudyType studyType, StudyStatus analyseStatus, boolean isPublic) {
        this(studyName, isPublic);
        this.studyId = studyId;
        this.studyType = studyType;
        this.analyseStatus = analyseStatus;
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

    public String getFormattedSubmitDate() {
        Format formatter = new SimpleDateFormat("dd.MMM.yyyy");
        return formatter.format(submitDate);
    }

    public String getFormattedReleaseDate() {
        Format formatter = new SimpleDateFormat("dd.MMM.yyyy");
        return formatter.format(releaseDate);
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

    public int getSampleSize() {
        return (samples != null ? samples.size() : 0);
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Study) {
            return (((Study) obj).getStudyId() == this.getStudyId() && ((Study) obj).getStudyName().equals(this.getStudyName()) && ((Study) obj).getSubmitDate().equals(this.getSubmitDate()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (getStudyId() + getStudyName()).hashCode();
    }


    public StudyType getStudyType() {
        return studyType;
    }

    public void setStudyType(StudyType studyType) {
        this.studyType = studyType;
    }

    public StudyStatus getAnalyseStatus() {
        return analyseStatus;
    }

    public void setAnalyseStatus(StudyStatus analyseStatus) {
        this.analyseStatus = analyseStatus;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public enum StudyType {
        ENVIRONMENT(1), HUMAN(2), UNDEFINED(3);
        private long studyTypeId;

        private StudyType(long studyTypeId) {
            this.studyTypeId = studyTypeId;
        }

        @Override
        public String toString() {
            //only capitalize the first letter
            String s = super.toString();
            return s.substring(0, 1) + s.substring(1).toLowerCase();
        }

        public long getStudyTypeId() {
            return studyTypeId;
        }

        public String getStudyTypeName() {
            return this.name();
        }
    }

    public enum StudyStatus {
        QUEUED(1), IN_PROCESS(2), FINISHED(3);

        private long studyStatusId;

        private StudyStatus(long studyStatusId) {
            this.studyStatusId = studyStatusId;
        }

        public String getStudyStatusName() {
            return this.name();
        }

        public long getStudyStatusId() {
            return studyStatusId;
        }
    }
}