package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
@Repository
public class HibernateStudyDAOImpl implements HibernateStudyDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public HibernateStudyDAOImpl() {
    }

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
    @Transactional(readOnly = true)
    public Study read(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return (Study) session.get(Study.class, id);
    }

    @Transactional(readOnly = true)
    public Study readByStringId(String studyId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Study.class);
            crit.add(Restrictions.eq("studyId", studyId));
            List<Study> studies = crit.list();
//            TODO: Log OR print exception if size > 1
            if (studies != null && studies.size() > 0) {
                return studies.get(0);
            }
        }
        return null;
    }


    @Override
    public Study readDeep(Long id, String... deepFields) {
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
    @Transactional(readOnly = true)
    public List<Study> retrieveAll() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            return session.createCriteria(Study.class).list();
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
            //add distinct criterion
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            result = crit.list();
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
            criteria.addOrder(Property.forName("lastMetadataReceived").desc());
            return (List<Study>) criteria.list();
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