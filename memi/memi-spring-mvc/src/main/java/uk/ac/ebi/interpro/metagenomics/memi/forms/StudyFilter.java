package uk.ac.ebi.interpro.metagenomics.memi.forms;

import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.List;

/**
 * Represents a filter form, which is used within the study list page.
 * Use this object to filter studies by type or status.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class StudyFilter {

    public final static String MODEL_ATTR_NAME = "studyFilter";

    private String searchTerm;

    private Study.StudyType studyType;

    private Study.StudyStatus studyStatus;

    private StudyVisibility studyVisibility;

    public StudyFilter() {
        studyVisibility = StudyVisibility.PUBLIC;
    }


    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

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

    public StudyVisibility getStudyVisibility() {
        return studyVisibility;
    }

    public void setStudyVisibility(StudyVisibility studyVisibility) {
        this.studyVisibility = studyVisibility;
    }

    public enum StudyVisibility {
        ALL, PUBLIC, PRIVATE;


    }
}