package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.*;

/**
 * Represents the model for the list studies page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class StudiesViewModel extends ViewModel {

    private StudyFilter filter;

    /* Collection of studies which are shown in the table */
    private Collection<Study> studies;

    /* Collection of studies which are necessary for download. */
//    private Collection<Study> downloadableStudies;


    private List<StudyFilter.StudyVisibility> studyVisibilities;

    /**
     * Specifies a list of table header names for the table on samples view page (the list of names should be in the order you like to show within the web
     * application).
     */
    private List<String> tableHeaderNames;

    private ViewPagination pagination;

    private Map<Study, Long> studySampleSizeMap;

    private List<Study.StudyStatus> studyStatusList;


    public StudiesViewModel(Submitter submitter, Collection<Study> studies, Map<Study, Long> studySampleSizeMap, String pageTitle, List<Breadcrumb> breadcrumbs,
                            MemiPropertyContainer propertyContainer, List<String> tableHeaderNames, ViewPagination pagination, StudyFilter filter) {
        this(submitter, studies, studySampleSizeMap, filter, pageTitle, breadcrumbs, propertyContainer, tableHeaderNames, pagination);
    }

    StudiesViewModel(Submitter submitter, Collection<Study> studies, Map<Study, Long> studySampleSizeMap, StudyFilter filter, String pageTitle,
                     List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer, List<String> tableHeaderNames,
                     ViewPagination pagination) {
        super(submitter, pageTitle, breadcrumbs, propertyContainer);
        this.filter = filter;
        this.studies = studies;
        this.studyVisibilities = getDefaultStudyVisibilityList();
        this.tableHeaderNames = tableHeaderNames;
        this.pagination = pagination;
        this.studySampleSizeMap = studySampleSizeMap;
    }

    public List<String> getTableHeaderNames() {
        return tableHeaderNames;
    }

    public StudyFilter getFilter() {
        return filter;
    }

    public void setFilter(StudyFilter filter) {
        this.filter = filter;
    }

    public Collection<Study> getStudies() {
        return studies;
    }

    public void setStudies(Collection<Study> studies) {
        this.studies = studies;
    }

    public List<StudyFilter.StudyVisibility> getStudyVisibilities() {
        return studyVisibilities;
    }

    public void setStudyVisibilities(List<StudyFilter.StudyVisibility> studyVisibilities) {
        this.studyVisibilities = studyVisibilities;
    }

    public ViewPagination getPagination() {
        return pagination;
    }

    public void setPagination(ViewPagination pagination) {
        this.pagination = pagination;
    }

    private List<Study.StudyStatus> getDefaultStudyStatus() {
        return Arrays.asList(Study.StudyStatus.values());
    }

    private List<StudyFilter.StudyVisibility> getDefaultStudyVisibilityList() {
        return Arrays.asList(StudyFilter.StudyVisibility.values());
    }

    public Map<Study, Long> getStudySampleSizeMap() {
        return studySampleSizeMap;
    }

    public void setStudySampleSizeMap(Map<Study, Long> studySampleSizeMap) {
        this.studySampleSizeMap = studySampleSizeMap;
    }

    public List<Study.StudyStatus> getStudyStatusList() {
        return studyStatusList;
    }

    public void setStudyStatusList(List<Study.StudyStatus> studyStatusList) {
        this.studyStatusList = studyStatusList;
    }
}