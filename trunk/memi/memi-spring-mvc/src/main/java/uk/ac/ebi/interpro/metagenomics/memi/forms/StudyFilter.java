package uk.ac.ebi.interpro.metagenomics.memi.forms;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

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

    private Study.StudyStatus studyStatus;

    private StudyVisibility studyVisibility;

    public StudyFilter() {
        studyVisibility = StudyVisibility.ALL_PUBLISHED_PROJECTS;
    }


    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
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

    /**
     * ALL_PROJECTS: All published and my pre-published studies
     * ALL_PUBLISHED_PROJECTS: All published studies
     * MY_PROJECTS: All my published and my pre-published studies
     * MY_PUBLISHED_PROJECTS: All my published studies
     * MY_PRE-PUBLISHED_PROJECTS: All my pre-published studies
     */
    public enum StudyVisibility {
        ALL_PROJECTS, ALL_PUBLISHED_PROJECTS, MY_PROJECTS, MY_PUBLISHED_PROJECTS, MY_PREPUBLISHED_PROJECTS;
    }
}