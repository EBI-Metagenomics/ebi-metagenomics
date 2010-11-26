package uk.ac.ebi.interpro.metagenomics.memi.forms;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Represents a form for submissions.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SubmissionForm {

    @NotEmpty
    /* Submission title */
    private String subTitle;

    @NotEmpty
    /* Submission explanation or message */
    private String subExplanation;

    @NotEmpty
    /* Data description */
    private String dataDesc;

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubExplanation() {
        return subExplanation;
    }

    public void setSubExplanation(String subExplanation) {
        this.subExplanation = subExplanation;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }
}
