package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Represents the model for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class ViewStudiesModel extends ViewModel {

    private StudyFilter studyFilter;

    private Map<Study, Long> studySampleSizeMap;

    private List<Study.StudyStatus> studyStatusList;

    private List<StudyFilter.StudyVisibility> studyVisibilityList;

    /**
     * Specifies a list of table header names for the table on studies view page (the list of names should be in the order you like to show within the web
     * application).
     */
    private List<String> tableHeaderNames;


    ViewStudiesModel(Submitter submitter, Map<Study, Long> studySampleSizeMap, String pageTitle,
                     List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer, List<String> tableHeaders) {
        this(submitter, studySampleSizeMap, new StudyFilter(), pageTitle, breadcrumbs, propertyContainer, tableHeaders);
    }

    ViewStudiesModel(Submitter submitter, Map<Study, Long> studySampleSizeMap, StudyFilter filter,
                     String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                     List<String> tableHeaders) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.studyFilter = filter;
        this.studyStatusList = getDefaultStudyStatus();
        this.studyVisibilityList = getDefaultStudyVisibilityList();
        this.studySampleSizeMap = studySampleSizeMap;
        this.tableHeaderNames = tableHeaders;
    }

    public List<String> getTableHeaderNames() {
        return tableHeaderNames;
    }

    public StudyFilter getStudyFilter() {
        return studyFilter;
    }

    public void setStudyFilter(StudyFilter studyFilter) {
        this.studyFilter = studyFilter;
    }

    public Map<Study, Long> getStudySampleSizeMap() {
        return studySampleSizeMap;
    }

    public void setStudySampleSizeMap(Map<Study, Long> studySampleSizeMap) {
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
