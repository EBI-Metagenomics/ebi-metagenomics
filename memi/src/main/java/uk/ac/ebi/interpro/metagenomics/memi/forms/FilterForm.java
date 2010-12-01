package uk.ac.ebi.interpro.metagenomics.memi.forms;

import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

/**
 * Represents a filter form, which is used within the study list page.
 * Use this object to filter studies by type or status.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class FilterForm {

    private Study.StudyType studyType;

    private Study.StudyStatus studyStatus;

    public Study.StudyType getStudyType() {
        return studyType;
    }

    public void setStudyType(Study.StudyType studyType) {
        this.studyType = studyType;
    }

    public Study.StudyStatus getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(Study.StudyStatus studyStatus) {
        this.studyStatus = studyStatus;
    }
}