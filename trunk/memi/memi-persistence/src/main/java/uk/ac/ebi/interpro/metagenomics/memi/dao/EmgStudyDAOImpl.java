package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;

import java.util.Collection;
import java.util.List;

/**
 * Represents the implementation class of {@link EmgStudyDAO}
 * TODO: Associate with Hibernate (all methods still return mock-up objects)
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
//@Repository
@Transactional
public class EmgStudyDAOImpl implements EmgStudyDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public EmgStudyDAOImpl() {
    }

//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }


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
        Session session = sessionFactory.getCurrentSession();
        return (EmgStudy) session.get(EmgStudy.class, id);
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
        Session session = sessionFactory.getCurrentSession();
        List<EmgStudy> test = session.createSQLQuery("SELECT * FROM emg_study").list();

//        Transaction tx = session.beginTransaction();
//        tx.begin();
//        Criteria crit = session.createCriteria(EmgStudy.class);
//        List<EmgStudy> test2 = crit.list();
//        tx.commit();

        List<EmgStudy> test3 = session.createCriteria(EmgStudy.class).list();

        return session.createCriteria(EmgStudy.class).list();
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