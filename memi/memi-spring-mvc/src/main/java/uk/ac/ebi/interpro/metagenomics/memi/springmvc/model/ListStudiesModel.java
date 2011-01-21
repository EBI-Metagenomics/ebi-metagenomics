package uk.ac.ebi.interpro.metagenomics.memi.springmvc.model;

import uk.ac.ebi.interpro.metagenomics.memi.forms.StudySearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

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

    private StudySearchForm studySearchForm;

    private List<EmgStudy> studies;

    private List<EmgStudy.StudyType> studyTypes;

    private List<EmgStudy.StudyStatus> studyStatusList;


    public ListStudiesModel(Submitter submitter, List<EmgStudy> studies) {
        super(submitter);
        this.studySearchForm = new StudySearchForm();
        this.studyTypes = getDefaultStudyTypes();
        this.studyStatusList = getDefaultStudyStati();
        this.studies = studies;
    }

    public StudySearchForm getFilterForm() {
        return studySearchForm;
    }

    public void setFilterForm(StudySearchForm studySearchForm) {
        this.studySearchForm = studySearchForm;
    }

    public List<EmgStudy> getStudies() {
        return studies;
    }

    public void setStudies(List<EmgStudy> studies) {
        this.studies = studies;
    }

    public List<EmgStudy.StudyStatus> getStudyStatusList() {
        return studyStatusList;
    }

    public void setStudyStatusList(List<EmgStudy.StudyStatus> studyStatusList) {
        this.studyStatusList = studyStatusList;
    }

    public List<EmgStudy.StudyType> getStudyTypes() {
        return studyTypes;
    }

    public void setStudyTypes(List<EmgStudy.StudyType> studyTypes) {
        this.studyTypes = studyTypes;
    }

    private List<EmgStudy.StudyType> getDefaultStudyTypes() {
        List<EmgStudy.StudyType> result = new ArrayList<EmgStudy.StudyType>();
        for (EmgStudy.StudyType type : EmgStudy.StudyType.values()) {
            if (!type.equals(EmgStudy.StudyType.UNDEFINED)) {
                result.add(type);
            }
        }
        return result;
    }

    private List<EmgStudy.StudyStatus> getDefaultStudyStati() {
        List<EmgStudy.StudyStatus> result = new ArrayList<EmgStudy.StudyStatus>();
        for (EmgStudy.StudyStatus status : EmgStudy.StudyStatus.values()) {
            if (!status.equals(EmgStudy.StudyStatus.UNDEFINED)) {
                result.add(status);
            }
        }
        return result;
    }


}
