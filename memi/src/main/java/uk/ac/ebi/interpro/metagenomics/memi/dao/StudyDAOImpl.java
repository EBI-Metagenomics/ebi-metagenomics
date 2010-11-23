package uk.ac.ebi.interpro.metagenomics.memi.dao;

import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the implementation class of {@link StudyDAO}
 * TODO: Associate with Hibernate (all methods still return mock-up objects)
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class StudyDAOImpl implements StudyDAO {

    public StudyDAOImpl() {
    }

    @Override
    public List<Study> getStudies() {
        List<Study> result = new ArrayList<Study>();
        result.add(new Study("study_1", true));
        result.add(new Study("study_2", true));
        result.add(new Study("study_3", true));
        result.add(new Study("study_4", true));
        result.add(new Study("study_5", true));
        result.add(new Study("study_6", false));
        result.add(new Study("study_7", false));
        result.add(new Study("study_8", false));
        result.add(new Study("study_9", false));
        result.add(new Study("study_10", false));
        return result;
    }

    @Override
    public List<Study> getStudiesByVisibility(boolean isPublic) {
        List<Study> result = new ArrayList<Study>();
        result.add(new Study("study_1", true));
        result.add(new Study("study_2", true));
        result.add(new Study("study_3", true));
        result.add(new Study("study_4", true));
        result.add(new Study("study_5", true));
        result.add(new Study("study_6", true));
        result.add(new Study("study_7", true));
        result.add(new Study("study_8", true));
        result.add(new Study("study_9", true));
        result.add(new Study("study_10", true));
        return result;
    }

//    TODO: Implement
    @Override
    public Study getStudyById(long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

//    TODO: Implement
    @Override
    public void deleteStudy(Study study) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
