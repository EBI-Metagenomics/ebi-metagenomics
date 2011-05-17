package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.hibernate.Criteria;
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
@Repository
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
    @Transactional(readOnly = true)
    public Sample read(Long id) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            return (Sample) session.get(Sample.class, id);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Sample readByStringId(String sampleId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            crit.add(Restrictions.eq("sampleId", sampleId));
            List<Sample> samples = crit.list();
            if (samples != null && samples.size() > 0) {
                return samples.get(0);
            }
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public long retrieveSampleSizeByStudyId(long studyId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            if (crit != null) {
                crit.setProjection(Projections.rowCount())
                        .add(Restrictions.eq("study.id", studyId));
                List<Long> result = crit.list();
                if (result != null && result.size() > 0) {
                    return result.get(0);
                }
            }
        }
        return 0;
    }

    @Override
    public Sample readDeep(Long id, String... deepFields) {
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<Sample> retrieveSamplesByStudyId(long studyId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            if (crit != null) {
                //Add where clause
                crit.add(Restrictions.eq("study.id", studyId));
                //Add distinct criterion
                crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                return crit.list();
            }
        }
        return null;
    }


    @Override
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
            result = crit.list();
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sample> retrieveAllPublicSamples() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            //add WHERE clause
            crit.add(Restrictions.eq("isPublic", true));
            //Add distinct criterion
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return crit.list();
        }
        return new ArrayList<Sample>();
    }

    @Override
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
            result = crit.list();
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sample> retrieveSamplesBySubmitter(long submitterId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Sample.class);
            //add WHERE clause
            crit.add(Restrictions.eq("submitterId", submitterId));
            //Add distinct criterion
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            return crit.list();
        }
        return new ArrayList<Sample>();
    }

    @Override
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
            result = crit.list();
        }
        return result;
    }

    @Override
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
            return crit.list();
        }
        return new ArrayList<Sample>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sample> retrieveFilteredSamples(List<Criterion> crits, Class<? extends Sample> clazz) {
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
            return (List<Sample>) criteria.list();
        }
        return new ArrayList<Sample>();
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    @Override
    public Long getMaximumPrimaryKey() {
        return null;
    }
}
