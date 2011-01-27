package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the implementation class of {@link uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
//@Repository
@Transactional
public class HibernateSampleDAOImpl implements HibernateSampleDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public HibernateSampleDAOImpl() {
    }

    @Override
    public Sample insert(Sample newInstance) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Sample> insert(Collection<Sample> newInstances) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(Sample modifiedInstance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Sample read(String id) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            return (Sample) session.get(Sample.class, id);
        }
        return null;
    }

    @Override
    public Sample readDeep(String id, String... deepFields) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Sample persistentObject) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long count() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Sample> retrieveAll() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            return session.createCriteria(Sample.class).list();
        }
        return null;
    }

    /**
     * @return All samples filtered by the specified study Id.
     */
    @Override
    public List<Sample> retrieveSamplesByStudyId(String studyId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            return session.createCriteria(Sample.class).add(Restrictions.eq("studyId", studyId)).list();
        }
        return null;
    }

    @Override
    public List<Sample> retrieveOrderedPublicSamples(String propertyName, boolean isDescendingOrder) {
        List<Sample> result = new ArrayList<Sample>();
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            //add ORDER BY clause
            if (isDescendingOrder) {
                crit.addOrder(Order.desc(propertyName));
            } else {
                crit.addOrder(Order.asc(propertyName));
            }
            //add WHERE clause
            crit.add(Restrictions.eq("isPublic", true));
            //add distinction clause
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            result = crit.list();
        }
        return result;
    }

    @Override
    public List<Sample> retrieveOrderedSamplesBySubmitter(long submitterId, String propertyName, boolean isDescendingOrder) {
        List<Sample> result = new ArrayList<Sample>();
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            //add ORDER BY clause
            if (isDescendingOrder) {
                crit.addOrder(Order.desc(propertyName));
            } else {
                crit.addOrder(Order.asc(propertyName));
            }
            //add WHERE clause
            crit.add(Restrictions.eq("submitterId", submitterId));
            //add distinction clause
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            result = crit.list();
        }
        return result;
    }

    @Override
    public List<Sample> retrieveOrderedPublicSamplesWithoutSubId(long submitterId, String propertyName, boolean isDescendingOrder) {
        List<Sample> result = new ArrayList<Sample>();
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
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
            //add distinction clause
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            result = crit.list();
        }
        return result;
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