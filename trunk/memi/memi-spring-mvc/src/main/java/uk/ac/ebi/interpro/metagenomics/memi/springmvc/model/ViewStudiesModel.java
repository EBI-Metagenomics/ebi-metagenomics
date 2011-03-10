package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

/**
 * Represents the model for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ViewStudiesModel extends MGModel {

    private StudyFilter studyFilter;

    private SortedMap<Study, Long> studySampleSizeMap;

    private List<Study.StudyStatus> studyStatusList;

    private List<StudyFilter.StudyVisibility> studyVisibilityList;


    ViewStudiesModel(Submitter submitter, SortedMap<Study, Long> studySampleSizeMap, List<Breadcrumb> breadcrumbs) {
        this(submitter, studySampleSizeMap, new StudyFilter(), breadcrumbs);
    }

    ViewStudiesModel(Submitter submitter, SortedMap<Study, Long> studySampleSizeMap, StudyFilter filter, List<Breadcrumb> breadcrumbs) {
        super(submitter, breadcrumbs);
        this.studyFilter = filter;
        this.studyStatusList = getDefaultStudyStatus();
        this.studyVisibilityList = getDefaultStudyVisibilityList();
        this.studySampleSizeMap = studySampleSizeMap;
    }

    public StudyFilter getStudyFilter() {
        return studyFilter;
    }

    public void setStudyFilter(StudyFilter studyFilter) {
        this.studyFilter = studyFilter;
    }

    public SortedMap<Study, Long> getStudySampleSizeMap() {
        return studySampleSizeMap;
    }

    public void setStudySampleSizeMap(SortedMap<Study, Long> studySampleSizeMap) {
        this.studySampleSizeMap = studySampleSizeMap;
    }

    //    public List<Study> getStudies() {
//        return studies;
//    }
//
//    public void setStudies(List<Study> studies) {
//        this.studies = studies;
//    }

    public List<Study.StudyStatus> getStudyStatusList() {
        return studyStatusList;
    }

    public void setStudyStatusList(List<Study.StudyStatus> studyStatusList) {
        this.studyStatusList = studyStatusList;
    }

    /**
     * Getter used by Spring.
     *
     * @return
     */
    public List<StudyFilter.StudyVisibility> getStudyVisibilityList() {
        return studyVisibilityList;
    }

    public void setStudyVisibilityList(List<StudyFilter.StudyVisibility> studyVisibilityList) {
        this.studyVisibilityList = studyVisibilityList;
    }

    private List<Study.StudyStatus> getDefaultStudyStatus() {
        return Arrays.asList(Study.StudyStatus.values());
    }

    private List<StudyFilter.StudyVisibility> getDefaultStudyVisibilityList() {
        return Arrays.asList(StudyFilter.StudyVisibility.values());
    }
}
