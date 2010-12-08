package uk.ac.ebi.interpro.metagenomics.memi.basic;

import uk.ac.ebi.interpro.metagenomics.memi.dao.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * This DAO implementation should only be used for JUnit test.
 */
public class StudyDAOTestImpl implements StudyDAO {

    private List<Study> studies;

    public StudyDAOTestImpl() {
        studies = new ArrayList<Study>();
        studies.add(new Study(1, "study_1", Study.StudyType.ENVIRONMENT, Study.StudyStatus.IN_PROCESS, true));
        studies.add(new Study(2, "study_2", Study.StudyType.ENVIRONMENT, Study.StudyStatus.QUEUED, false));
        studies.add(new Study(3, "study_3", Study.StudyType.HUMAN, Study.StudyStatus.FINISHED, true));
        studies.add(new Study(4, "study_4", Study.StudyType.UNDEFINED, Study.StudyStatus.IN_PROCESS, true));
        studies.add(new Study(5, "study_5", Study.StudyType.ENVIRONMENT, Study.StudyStatus.QUEUED, true));
        studies.add(new Study(6, "study_6", Study.StudyType.ENVIRONMENT, Study.StudyStatus.QUEUED, false));
        studies.add(new Study(7, "study_7", Study.StudyType.UNDEFINED, Study.StudyStatus.FINISHED, false));
        studies.add(new Study(8, "study_8", Study.StudyType.UNDEFINED, Study.StudyStatus.IN_PROCESS, true));
        studies.add(new Study(9, "study_9", Study.StudyType.ENVIRONMENT, Study.StudyStatus.FINISHED, false));
        studies.add(new Study(10, "study_10", Study.StudyType.ENVIRONMENT, Study.StudyStatus.IN_PROCESS, false));
        studies.add(new Study(11, "study_11", Study.StudyType.HUMAN, Study.StudyStatus.IN_PROCESS, true));
        studies.add(new Study(12, "study_12", Study.StudyType.ENVIRONMENT, Study.StudyStatus.IN_PROCESS, true));
        studies.add(new Study(13, "study_13", Study.StudyType.HUMAN, Study.StudyStatus.FINISHED, true));
        studies.add(new Study(14, "study_14", Study.StudyType.ENVIRONMENT, Study.StudyStatus.FINISHED, true));
        studies.add(new Study(15, "study_15", Study.StudyType.ENVIRONMENT, Study.StudyStatus.FINISHED, true));
    }

    @Override
    public List<Study> getAllStudies() {
        return studies;
    }

    @Override
    public List<Study> getStudiesByVisibility(boolean isPublic) {
        List<Study> result = new ArrayList<Study>();
        for (Study study : studies) {
            if (study.isPublic()) {
                result.add(study);
            }
        }
        return result;
    }

    @Override
    public Study getStudyById(long id) {
        for (Study study : studies) {
            if (study.getStudyId() == id) {
                return study;
            }
        }
        return null;
    }

    @Override
    public void deleteStudy(Study study) {
        studies.remove(study);
    }
}