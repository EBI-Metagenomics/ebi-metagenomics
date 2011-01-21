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
public class StudySearchForm {

    public final static String MODEL_ATTR_NAME = "searchStudiesForm";

    private String searchTerm;

    private EmgStudy.StudyType studyType;

    private EmgStudy.StudyStatus studyStatus;


    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

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
}