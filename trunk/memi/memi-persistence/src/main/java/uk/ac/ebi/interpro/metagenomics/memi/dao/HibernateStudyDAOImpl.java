package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the implementation class of {@link HibernateStudyDAO}.
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
    public List<Study> retrieveStudiesOrderBy(String propertyName, boolean isDescendingOrder) {
        List<Study> result = new ArrayList<Study>();
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            if (isDescendingOrder) {
                result = session.createCriteria(Study.class).addOrder(Order.desc(propertyName)).list();
            } else {
                result = session.createCriteria(Study.class).addOrder(Order.asc(propertyName)).list();
            }
        }
        return null;
    }

    @Override
    public List<Study> retrieveOrderedPublicStudies(String propertyName, boolean isDescendingOrder) {
        List<Study> result = new ArrayList<Study>();
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Study.class);
            //add ORDER BY clause
            if (isDescendingOrder) {
                crit.addOrder(Order.desc(propertyName));
            } else {
                crit.addOrder(Order.asc(propertyName));
            }
            //add WHERE clause
            crit.add(Restrictions.eq("isPublic", true));
            result = crit.list();
        }
        return result;
    }

    @Override
    public List<Study> retrieveOrderedStudiesBySubmitter(long submitterId, String propertyName, boolean isDescendingOrder) {
        List<Study> result = new ArrayList<Study>();
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Study.class);
            //add ORDER BY clause
            if (isDescendingOrder) {
                crit.addOrder(Order.desc(propertyName));
            } else {
                crit.addOrder(Order.asc(propertyName));
            }
            //add WHERE clause
            crit.add(Restrictions.eq("submitterId", submitterId));
            result = crit.list();
        }
        return result;
    }

    @Override
    public List<Study> retrieveOrderedPublicStudiesWithoutSubId(long submitterId, String propertyName, boolean isDescendingOrder) {
        List<Study> result = new ArrayList<Study>();
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Study.class);
            //add ORDER BY clause
            if (isDescendingOrder) {
                crit.addOrder(Order.desc(propertyName));
            } else {
                crit.addOrder(Order.asc(propertyName));
            }
            //add WHERE clause
            crit.add(Restrictions.eq("isPublic", true));
            //add another WHERE clause
            crit.add(Restrictions.ne("submitterId", submitterId));
            result = crit.list();
        }
        return result;
    }

    @Override
    public List<Study> retrieveFilteredStudies(List<Criterion> crits) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = null;
        if (session != null) {
            criteria = session.createCriteria(Study.class);
            //add criterions
            for (Criterion crit : crits) {
                criteria.add(crit);
            }
        }
        if (criteria != null) {
            return criteria.list();
        }
        return new ArrayList<Study>();
    }

    @Override
    public int deleteAll
            () {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long getMaximumPrimaryKey
            () {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}