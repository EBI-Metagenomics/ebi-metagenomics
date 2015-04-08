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
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the implementation class of {@link StudyDAO}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Repository
public class StudyDAOImpl implements StudyDAO {

    private final static Log log = LogFactory.getLog(StudyDAOImpl.class);

    @Autowired
    private SessionFactory sessionFactory;


    public StudyDAOImpl() {
    }

    //TODO: Do implement
    public Study insert(Study newInstance) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Do implement
    public Collection<Study> insert(Collection<Study> newInstances) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Do implement
    public void update(Study modifiedInstance) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Transactional(readOnly = true)
    public Study read(Long id) {
        Session session = sessionFactory.getCurrentSession();
        try {
            return (Study) session.get(Study.class, id);
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't read the study for study identifier " + id, e);
        }

    }

    @Transactional(readOnly = true)
    public Study readByStringId(String studyId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Study.class);
            crit.add(Restrictions.eq("studyId", studyId));
            try {
                List<Study> studies = crit.list();
//            TODO: Log OR print exception if size > 1
                if (studies != null) {
                    int studyListSize = studies.size();
                    if (studyListSize == 1) {
                        return studies.get(0);
                    } else if (studyListSize == 0) {
                        log.warn("No study found for study id " + studyId);
                    } else if (studyListSize > 1) {
                        log.warn("More then one study found for study id " + studyId);
                    }
                }
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't read the study for study identifier " + studyId, e);
            }
        }
        return null;
    }


    //TODO: Do implement
    public Study readDeep(Long id, String... deepFields) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Do implement
    public void delete(Study persistentObject) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Long count() {
        return getStudyCount(null);
    }

    @Transactional(readOnly = true)
    public Long countAllPublic() {
        return getStudyCount(new Boolean(true));
    }

    @Transactional(readOnly = true)
    public Long countAllPrivate() {
        return getStudyCount(new Boolean(false));
    }

    @Transactional(readOnly = true)
    public Long countByCriteria(final List<Criterion> crits) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            try {
                Criteria criteria = session.createCriteria(Study.class);
                //Adds filter criteria
                //add criterions
                for (Criterion criterion : crits) {
                    criteria.add(criterion);
                }
                //Adds row count
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                criteria.setProjection(Projections.rowCount());
                return ((Long) criteria.list().get(0)).longValue();
            } catch (HibernateException e) {
                throw new HibernateException("Cannot retrieve filtered study count!", e);
            }
        }
        return null;
    }

    private Long getStudyCount(final Boolean isPublic) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Study.class);
            if (isPublic != null) {
                criteria.add(Restrictions.eq("isPublic", isPublic));
            }
            criteria.setProjection(Projections.rowCount());
            try {
                return ((Long) criteria.list().get(0)).longValue();
            } catch (HibernateException e) {
                throw new HibernateException("Cannot retrieve study count!", e);
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Study> retrieveAll() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            try {
                return session.createCriteria(Study.class).list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve all studies!", e);
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<Study> retrieveStudiesOrderBy(String propertyName, boolean isDescendingOrder) {
        List<Study> result = new ArrayList<Study>();
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            try {
                if (isDescendingOrder) {
                    result = session.createCriteria(Study.class).addOrder(Order.desc(propertyName)).list();
                } else {
                    result = session.createCriteria(Study.class).addOrder(Order.asc(propertyName)).list();
                }
                return result;
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve studies ordered by " + propertyName, e);
            }
        }
        return null;
    }

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
            try {
                result = crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve public studies ordered by " + propertyName, e);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Study> retrieveOrderedStudiesBySubmitter(long submitterId, String propertyName,
                                                         boolean isDescendingOrder) {
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
            try {
                result = crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve public studies ordered by " + propertyName, e);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Study> retrieveStudiesBySubmitter(String submissionAccountId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Study.class);
            //add WHERE clause
            crit.add(Restrictions.eq("submissionAccountId", submissionAccountId));
            try {
                return crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve studies by submitter identifier!", e);
            }
        }
        return new ArrayList<Study>();
    }


    @Transactional(readOnly = true)
    public List<Study> retrieveOrderedPublicStudiesWithoutSubId(long submitterId, String propertyName,
                                                                boolean isDescendingOrder) {
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
            try {
                result = crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve public studies without submitter id ordered by " + propertyName, e);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Study> retrievePublicStudiesWithoutSubId(long submitterId) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria crit = session.createCriteria(Study.class);
            //add WHERE clause
            crit.add(Restrictions.eq("isPublic", true));
            //add another WHERE clause
            crit.add(Restrictions.ne("submitterId", submitterId));
            try {
                return crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve public studies without for submitter identifier!", e);
            }
        }
        return new ArrayList<Study>();
    }

    @Transactional(readOnly = true)
    public List<Study> retrieveFilteredStudies(final List<Criterion> crits) {
        return getFilteredStudies(crits, null, null, null, null);
    }

    @Transactional(readOnly = true)
    public List<Study> retrieveFilteredStudies(final List<Criterion> crits,
                                               final Boolean isDescendingOrder,
                                               final String propertyName) {
        return getFilteredStudies(crits, null, null, isDescendingOrder, propertyName);
    }

    @Transactional(readOnly = true)
    public List<Study> retrieveFilteredStudies(final List<Criterion> crits,
                                               final Integer startPosition,
                                               final Integer pageSize,
                                               final Boolean isDescendingOrder,
                                               final String propertyName) {
        return getFilteredStudies(crits, startPosition, pageSize, isDescendingOrder, propertyName);
    }


    private List<Study> getFilteredStudies(final List<Criterion> crits,
                                           final Integer startPosition,
                                           final Integer pageSize,
                                           final Boolean isDescendingOrder,
                                           final String propertyName) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Study.class);
            //add criterions
            for (Criterion criterion : crits) {
                criteria.add(criterion);
            }
            if (startPosition != null) {
                criteria.setFirstResult(startPosition);
            }
            if (pageSize != null) {
                criteria.setMaxResults(pageSize);
            }
            if (isDescendingOrder != null && propertyName != null) {
                if (isDescendingOrder) {
                    criteria.addOrder(Order.desc(propertyName));
                } else {
                    criteria.addOrder(Order.asc(propertyName));
                }
            }
//            criteria.addOrder(Property.forName("lastMetadataReceived").desc());
            try {
                return (List<Study>) criteria.list();
            } catch (HibernateException e) {
                throw new HibernateException("Cannot retrieve filtered studies!", e);
            }
        }
        return new ArrayList<Study>();
    }

    @Transactional(readOnly = true)
    public List<Study> retrievePublicStudies() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Study.class);
            criteria.add(Restrictions.eq("isPublic", true));
            try {
                return (List<Study>) criteria.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve all public studies!", e);
            }
        }
        return new ArrayList<Study>();
    }

    @Transactional(readOnly = true)
    public List<Study> retrieveOrderedPublicStudies(final Boolean isDescendingOrder,
                                                    final String propertyName) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Study.class);
            criteria.add(Restrictions.eq("isPublic", true));
            if (isDescendingOrder != null && propertyName != null) {
                if (isDescendingOrder) {
                    criteria.addOrder(Order.desc(propertyName));
                } else {
                    criteria.addOrder(Order.asc(propertyName));
                }
            }
            try {
                return (List<Study>) criteria.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve all public studies!", e);
            }
        }
        return new ArrayList<Study>();
    }

    //TODO: Do implement

    public int deleteAll() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Do implement
    public Long getMaximumPrimaryKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}