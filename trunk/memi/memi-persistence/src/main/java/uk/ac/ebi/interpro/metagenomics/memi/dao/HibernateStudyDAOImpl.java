package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.Collection;
import java.util.List;

/**
 * Represents the implementation class of {@link uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO}
 * TODO: Associate with Hibernate (all methods still return mock-up objects)
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
//@Repository
@Transactional
public class HibernateStudyDAOImpl implements HibernateStudyDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public HibernateStudyDAOImpl() {
    }

//    public void setSessionFactory(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }


    @Override
    public Study insert(Study newInstance) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Study> insert(Collection<Study> newInstances) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Study modifiedInstance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Study read(String id) {
        Session session = sessionFactory.getCurrentSession();
        return (Study) session.get(Study.class, id);
    }

    @Override
    public Study readDeep(String id, String... deepFields) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Study persistentObject) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long count() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Study> retrieveAll() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            return session.createCriteria(Study.class).list();
        }
        return null;
    }

    @Override
    public List<Study> retrieveStudiesLimitedByRows(int rowNumber) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            return session.createCriteria(Study.class).setMaxResults(rowNumber).list();
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