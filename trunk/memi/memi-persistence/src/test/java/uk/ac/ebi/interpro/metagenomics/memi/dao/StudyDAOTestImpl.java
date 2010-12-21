package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This DAO implementation should only be used for JUnit test.
 */
public class StudyDAOTestImpl implements EmgStudyDAO {

    private List<EmgStudy> studies;

    public StudyDAOTestImpl() {
        studies = new ArrayList<EmgStudy>();
        studies.add(new EmgStudy("1", "study_1", "Environmental", EmgStudy.StudyStatus.IN_PROCESS));
        studies.add(new EmgStudy("2", "study_2", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("3", "study_3", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("4", "study_4", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("5", "study_5", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("6", "study_6", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("7", "study_7", "Environmental", EmgStudy.StudyStatus.IN_PROCESS));
        studies.add(new EmgStudy("8", "study_8", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("9", "study_9", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("10", "study_10", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("11", "study_11", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("12", "study_12", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("13", "study_13", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("14", "study_14", "Environmental", EmgStudy.StudyStatus.FINISHED));
        studies.add(new EmgStudy("15", "study_15", "Environmental", EmgStudy.StudyStatus.FINISHED));
    }

    @Override
    public EmgStudy insert(EmgStudy newInstance) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<EmgStudy> insert(Collection<EmgStudy> newInstances) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(EmgStudy modifiedInstance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EmgStudy read(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EmgStudy readDeep(String id, String... deepFields) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(EmgStudy persistentObject) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long count() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<EmgStudy> retrieveAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int deleteAll() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long getMaximumPrimaryKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<EmgStudy> retrieveStudiesLimitedByRows(int rowNumber) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}