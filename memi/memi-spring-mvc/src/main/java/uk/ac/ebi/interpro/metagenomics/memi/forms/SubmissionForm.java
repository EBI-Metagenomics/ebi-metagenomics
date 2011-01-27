package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.NotEmpty;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents a form for submissions.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SubmissionForm {
    public final static String MODEL_ATTR_NAME = "subForm";

    /**
     * Submission title
     */
    @NotEmpty
    private String subTitle;

    private Date releaseDate;

    private boolean isAnalysisRequired;

    private boolean isHumanAssociated;

    /**
     * Data description
     */
    @NotEmpty
    private String dataDesc;

    public SubmissionForm() {
        long time = Calendar.getInstance().getTimeInMillis();
        //add time to 1 year in advance
        time = time + 1000L * 60L * 60L * 24L * 365L * 2L;
        releaseDate = new Date(time);
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getReleaseDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(releaseDate);
    }

    public void setReleaseDate(String releaseDate) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.releaseDate = df.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isAnalysisRequired() {
        return isAnalysisRequired;
    }

    public void setAnalysisRequired(boolean analysisRequired) {
        isAnalysisRequired = analysisRequired;
    }

    public boolean isHumanAssociated() {
        return isHumanAssociated;
    }

    public void setHumanAssociated(boolean humanAssociated) {
        isHumanAssociated = humanAssociated;
    }
}