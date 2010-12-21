package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgSample;

import java.util.Collection;
import java.util.List;

/**
 * Represents the implementation class of {@link EmgSampleDAO}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
//@Repository
@Transactional
public class EmgSampleDAOImpl implements EmgSampleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public EmgSampleDAOImpl() {
    }

    @Override
    public EmgSample insert(EmgSample newInstance) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<EmgSample> insert(Collection<EmgSample> newInstances) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(EmgSample modifiedInstance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public EmgSample read(String id) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            return (EmgSample) session.get(EmgSample.class, id);
        }
        return null;
    }

    @Override
    public EmgSample readDeep(String id, String... deepFields) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(EmgSample persistentObject) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long count() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<EmgSample> retrieveAll() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            return session.createCriteria(EmgSample.class).list();
        }
        return null;
    }

    /**
     * @return All samples filtered by the specified study Id.
     */
    @Override
    public List<EmgSample> retrieveSamplesByStudyId(String studyId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            return session.createCriteria(EmgSample.class).add(Restrictions.eq("studyId", studyId)).list();
        }
        return null;
    }

    @Override
    public int deleteAll() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long getMaximumPrimaryKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}