package uk.ac.ebi.interpro.metagenomics.memi.forms;

import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;

import java.util.List;

/**
 * Represents a filter form, which is used within the study list page.
 * Use this object to filter studies by type or status.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class FilterForm {

    public final static String MODEL_ATTR_NAME = "filterForm";

    private EmgStudy.StudyType studyType;

    private EmgStudy.StudyStatus studyStatus;

    private List<EmgStudy.StudyType> studyTypes;

    private List<EmgStudy.StudyStatus> studyStati;

    public EmgStudy.StudyType getStudyType() {
        return studyType;
    }

    public void setStudyType(EmgStudy.StudyType studyType) {
        this.studyType = studyType;
    }

    public EmgStudy.StudyStatus getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(EmgStudy.StudyStatus studyStatus) {
        this.studyStatus = studyStatus;
    }

    public List<EmgStudy.StudyType> getStudyTypes() {
        return studyTypes;
    }

    public void setStudyTypes(List<EmgStudy.StudyType> studyTypes) {
        this.studyTypes = studyTypes;
    }

    public List<EmgStudy.StudyStatus> getStudyStati() {
        return studyStati;
    }

    public void setStudyStati(List<EmgStudy.StudyStatus> studyStati) {
        this.studyStati = studyStati;
    }
}