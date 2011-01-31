package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the model for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ListStudiesModel extends MGModel {

    private StudyFilter studyFilter;

    private List<Study> studies;

    private List<Study.StudyType> studyTypes;

    private List<Study.StudyStatus> studyStatusList;

    private List<StudyFilter.StudyVisibility> studyVisibilityList;


    public ListStudiesModel(Submitter submitter, List<Study> studies) {
        this(submitter, studies, new StudyFilter());
    }

    public ListStudiesModel(Submitter submitter, List<Study> studies, StudyFilter filter) {
        super(submitter);
        this.studyFilter = filter;
        this.studyTypes = getDefaultStudyTypes();
        this.studyStatusList = getDefaultStudyStati();
        this.studyVisibilityList = getDefaultStudyVisibilityList();
        this.studies = studies;
    }

    public StudyFilter getStudyFilter() {
        return studyFilter;
    }

    public void setStudyFilter(StudyFilter studyFilter) {
        this.studyFilter = studyFilter;
    }

    public List<Study> getStudies() {
        return studies;
    }

    public void setStudies(List<Study> studies) {
        this.studies = studies;
    }

    public List<Study.StudyStatus> getStudyStatusList() {
        return studyStatusList;
    }

    public void setStudyStatusList(List<Study.StudyStatus> studyStatusList) {
        this.studyStatusList = studyStatusList;
    }

    public List<Study.StudyType> getStudyTypes() {
        return studyTypes;
    }

    public void setStudyTypes(List<Study.StudyType> studyTypes) {
        this.studyTypes = studyTypes;
    }

    public List<StudyFilter.StudyVisibility> getStudyVisibilityList() {
        return studyVisibilityList;
    }

    public void setStudyVisibilityList(List<StudyFilter.StudyVisibility> studyVisibilityList) {
        this.studyVisibilityList = studyVisibilityList;
    }

    private List<Study.StudyType> getDefaultStudyTypes() {
        List<Study.StudyType> result = new ArrayList<Study.StudyType>();
        for (Study.StudyType type : Study.StudyType.values()) {
            result.add(type);
        }
        return result;
    }

    private List<Study.StudyStatus> getDefaultStudyStati() {
        List<Study.StudyStatus> result = new ArrayList<Study.StudyStatus>();
        for (Study.StudyStatus status : Study.StudyStatus.values()) {
            result.add(status);
        }
        return result;
    }

    private List<StudyFilter.StudyVisibility> getDefaultStudyVisibilityList() {
        List<StudyFilter.StudyVisibility> result = new ArrayList<StudyFilter.StudyVisibility>();
        for (StudyFilter.StudyVisibility vis : StudyFilter.StudyVisibility.values()) {
            result.add(vis);
        }
        return result;
    }
}