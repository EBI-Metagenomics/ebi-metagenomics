package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.util.*;

/**
 * Represents the implementation class of {@link uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO}
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Repository
public class SampleDAOImpl implements SampleDAO {

    private final static Log log = LogFactory.getLog(SampleDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    public SampleDAOImpl() {
    }

    //TODO: Do implement
    public Sample insert(Sample newInstance) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Do implement
    public Collection<Sample> insert(Collection<Sample> newInstances) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Do implement
    public void update(Sample modifiedInstance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Transactional(readOnly = true)
    public Sample read(Long id) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            try {
                return (Sample) session.get(Sample.class, id);
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve sample by identifier " + id, e);
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Sample readByStringId(String sampleId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            crit.add(Restrictions.eq("sampleId", sampleId));
            try {
                List<Sample> samples = crit.list();
                if (!samples.isEmpty()) {
                    return samples.get(0);
                }
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve sample by identifier " + sampleId, e);
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public long retrieveSampleSizeByStudyId(long studyId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            if (crit != null) {
                crit.setProjection(Projections.rowCount())
                        .add(Restrictions.eq("study.id", studyId));
                try {
                    List<Long> result = crit.list();
                    if (result != null && result.size() > 0) {
                        return result.get(0);
                    }
                } catch (HibernateException e) {
                    throw new HibernateException("Couldn't retrieve samples size by study Id " + studyId, e);
                }
            }
        }
        return 0;
    }

    //TODO: Do implement
    public Sample readDeep(Long id, String... deepFields) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Do implement
    public void delete(Sample persistentObject) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Transactional(readOnly = true)
    public Long count() {
        return getSampleCount(null);
    }

    @Transactional(readOnly = true)
    public Long countAllPublic() {
        return getSampleCount(new Boolean(true));
    }

    @Transactional(readOnly = true)
    public Long countAllPrivate() {
        return getSampleCount(new Boolean(false));
    }

    private Long getSampleCount(final Boolean isPublic) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Sample.class);
            if (isPublic != null) {
                criteria.add(Restrictions.eq("isPublic", isPublic));
            }
            criteria.setProjection(Projections.rowCount());
            try {
                return ((Long) criteria.list().get(0)).longValue();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve sample count!", e);
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Sample> retrieveAll() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            try {
                return session.createCriteria(Sample.class).list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve all samples!", e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves samples (public and private) by study ID.
     *
     * @return Public and private samples by the specified study Id.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Sample> retrieveAllSamplesByStudyId(long studyId) {
        Set<Criterion> criterionSet = new HashSet<Criterion>(2);
        criterionSet.add(Restrictions.eq("study.id", studyId));
        return retrieveSamplesByCriterionSet(criterionSet);
    }

    /**
     * Retrieves public samples by study ID.
     *
     * @return Public samples by the specified study Id.
     */
    @Transactional(readOnly = true)
    public List<Sample> retrievePublicSamplesByStudyId(long studyId) {
        Set<Criterion> criterionSet = new HashSet<Criterion>(2);
        criterionSet.add(Restrictions.eq("study.id", studyId));
        criterionSet.add(Restrictions.eq("isPublic", true));
        return retrieveSamplesByCriterionSet(criterionSet);
    }

    private List<Sample> retrieveSamplesByCriterionSet(Set<Criterion> criterionSet) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            if (crit != null) {
                for (Criterion criterion : criterionSet) {
                    //Add where clauses
                    crit.add(criterion);
                }
                //Add distinct criterion
                crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                try {
                    return crit.list();
                } catch (HibernateException e) {
                    throw new HibernateException("Couldn't retrieve samples by criteria!", e);
                }
            }
        }
        return Collections.emptyList();
    }


    @Transactional(readOnly = true)
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
            try {
                result = crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve ordered public samples!", e);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Sample> retrieveAllPublicSamples() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            //add WHERE clause
            crit.add(Restrictions.eq("isPublic", true));
            //Add distinct criterion
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            try {
                return crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve all public samples!", e);
            }
        }
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
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
            try {
                result = crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve ordered samples by submitter ID " + submitterId, e);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Sample> retrieveSamplesBySubmitter(long submitterId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            //add WHERE clause
            crit.add(Restrictions.eq("submitterId", submitterId));
            //Add distinct criterion
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            try {
                return crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve samples by submitter ID " + submitterId, e);
            }
        }
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
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
            try {
                result = crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve ordered public samples without submitter ID " + submitterId, e);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Sample> retrievePublicSamplesWithoutSubId(long submitterId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            //add WHERE clause
            crit.add(Restrictions.eq("isPublic", true));
            //add another WHERE clause
            crit.add(Restrictions.ne("submitterId", submitterId));
            //Add distinct criterion
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            try {
                return crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve public samples without submitter ID " + submitterId, e);
            }
        }
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    public List<Sample> retrieveFilteredSamples(List<Criterion> crits, Class<? extends Sample> clazz, int startPosition, int pageSize, String orderedByColumnWithName) {
        Criteria criteria = setUpFilteredSamplesCriteria(crits, clazz, orderedByColumnWithName);
        if (criteria != null) {
            criteria.setFirstResult(startPosition);
            criteria.setMaxResults(pageSize);
            try {
                return (List<Sample>) criteria.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve filtered samples!", e);
            }
        }
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    public List<Sample> retrieveFilteredSamples(List<Criterion> crits, Class<? extends Sample> clazz, String orderedByColumnWithName) {
        Criteria criteria = setUpFilteredSamplesCriteria(crits, clazz, orderedByColumnWithName);
        if (criteria != null) {
            try {
                return (List<Sample>) criteria.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve filtered samples!", e);
            }
        }
        return Collections.emptyList();
    }

    private Criteria setUpFilteredSamplesCriteria(final List<Criterion> crits,
                                                  Class<? extends Sample> clazz,
                                                  final String orderedByColumnWithName) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = null;
        if (session != null) {
            criteria = session.createCriteria(clazz);
            //add criteria
            for (Criterion crit : crits) {
                criteria.add(crit);
            }
            if (orderedByColumnWithName != null) {
                criteria.addOrder(Order.asc(orderedByColumnWithName).ignoreCase());
            }
        }
        return criteria;
    }

    @Transactional(readOnly = true)
    public Long countFilteredSamples
            (List<Criterion> crits, Class<? extends Sample> clazz) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = null;
        if (session != null) {
            criteria = session.createCriteria(clazz);
            //add criteria
            for (Criterion crit : crits) {
                criteria.add(crit);
            }
        }
        if (criteria != null) {
            //add distinct criterion
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.setProjection(Projections.rowCount());
            try {
                List result = criteria.list();
                if (!result.isEmpty()) {
                    return (Long) criteria.list().get(0);
                }
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't query filtered sample count!", e);
            }
        }
        return new Long(0);
    }

    //TODO: Do implement
    public int deleteAll() {
        return 0;
    }

    //TODO: Do implement
    public Long getMaximumPrimaryKey() {
        return null;
    }
}
