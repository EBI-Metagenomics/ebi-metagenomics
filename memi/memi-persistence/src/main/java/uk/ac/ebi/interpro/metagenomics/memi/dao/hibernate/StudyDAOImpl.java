package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.CompareToolStudyVO;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.StudyStatisticsVO;

import javax.sql.DataSource;
import java.util.*;

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

    private JdbcTemplate jdbcTemplate;

    @Autowired(required = true)
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


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
                if (studies != null) {
                    int studyListSize = studies.size();
                    if (studyListSize == 1) {
                        return studies.get(0);
                    } else if (studyListSize == 0) {
                        log.info("No study found for study id " + studyId);
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
        return getStudyCount(1);
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

    @Transactional(readOnly = true)
    public Long countPublicStudiesFilteredByBiomes(Collection<Integer> biomeIds) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            if (biomeIds != null && !biomeIds.isEmpty()) {
                Criteria criteria = session.createCriteria(Study.class)
                        .add(Restrictions.eq("isPublic", 1))
                        .add(Restrictions.in("biome.biomeId", biomeIds))
                        .setProjection(Projections.rowCount());
                try {
                    return (Long) criteria.uniqueResult();
                } catch (HibernateException e) {
                    throw new HibernateException("Cannot retrieve study count filtered by biomes!", e);
                }
            }
        }
        return null;
    }

    private Long getStudyCount(final Integer isPublic) {
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
    public Long countAllWithNotEqualsEx(final int isPublic) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Study.class);
            criteria.add(Restrictions.sqlRestriction("{alias}.is_public <> " + isPublic));
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
    public List<Study> retrieveOrderedPublicStudies(String propertyName, boolean isDescendingOrder, int maxResult) {
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
            crit.add(Restrictions.eq("isPublic", 1));
            //add distinct criterion
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            crit.setMaxResults(maxResult);
            try {
                result = crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve public studies ordered by " + propertyName, e);
            }
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<Study> retrieveOrderedStudiesBySubmitter(String submissionAccountId, String propertyName,
                                                         boolean isDescendingOrder, int maxResult) {
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
            crit.add(Restrictions.eq("submissionAccountId", submissionAccountId));
            crit.setMaxResults(maxResult);
            try {
                result = crit.list();
            } catch (HibernateException e) {
                throw new HibernateException("Couldn't retrieve studies ordered by " + propertyName, e);
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
            crit.add(Restrictions.eq("isPublic", 1));
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
            crit.add(Restrictions.eq("isPublic", 1));
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

    @Override
    @Transactional(readOnly = true)
    public List<CompareToolStudyVO> retrieveNonAmpliconStudies(String submissionAccountId) {
        try {
            StringBuilder sb = new StringBuilder();
            if (submissionAccountId == null) {
                sb.append("select st.STUDY_ID as study_id, st.EXT_STUDY_ID as external_study_id, st.STUDY_NAME as study_name, st.IS_PUBLIC as is_public, tmp.run_count as run_count ")
                        .append("FROM ")
                        .append("STUDY st, ")
                        .append("STUDY_SAMPLE stsa, ")
                        .append("SAMPLE sa, ")
                        .append("ANALYSIS_JOB aj, ")
                        .append("(select st.EXT_STUDY_ID, count(aj.EXTERNAL_RUN_IDS) as run_count FROM STUDY st, STUDY_SAMPLE stsa, SAMPLE sa, ANALYSIS_JOB aj where st.STUDY_ID=stsa.STUDY_ID AND stsa.SAMPLE_ID=sa.SAMPLE_ID and sa.sample_id = aj.sample_id and st.IS_PUBLIC=1 and aj.EXPERIMENT_TYPE_ID<>3 and aj.ANALYSIS_STATUS_ID=3 GROUP BY st.EXT_STUDY_ID) tmp ")
                        .append("where st.STUDY_ID=stsa.STUDY_ID AND stsa.SAMPLE_ID=sa.SAMPLE_ID AND sa.SAMPLE_ID=aj.SAMPLE_ID and tmp.EXT_STUDY_ID = st.EXT_STUDY_ID and st.IS_PUBLIC=1 and aj.EXPERIMENT_TYPE_ID<>3 and aj.ANALYSIS_STATUS_ID=3 and tmp.run_count > 1 group by st.EXT_STUDY_ID order by st.STUDY_NAME");
            } else {
                sb.append("select st.STUDY_ID as study_id, st.EXT_STUDY_ID as external_study_id, st.STUDY_NAME as study_name, st.IS_PUBLIC as is_public, tmp.run_count as run_count ")
                        .append("FROM ")
                        .append("STUDY st, ")
                        .append("STUDY_SAMPLE stsa, ")
                        .append("SAMPLE sa, ")
                        .append("ANALYSIS_JOB aj, ")
                        .append("(select st.EXT_STUDY_ID, count(aj.EXTERNAL_RUN_IDS) as run_count FROM STUDY st, STUDY_SAMPLE stsa, SAMPLE sa, ANALYSIS_JOB aj where st.STUDY_ID=stsa.STUDY_ID AND stsa.SAMPLE_ID=sa.SAMPLE_ID and sa.sample_id = aj.sample_id and st.IS_PUBLIC<>5 and aj.EXPERIMENT_TYPE_ID<>3 and aj.ANALYSIS_STATUS_ID=3 GROUP BY st.EXT_STUDY_ID) tmp ")
                        .append("where st.STUDY_ID=stsa.STUDY_ID AND stsa.SAMPLE_ID=sa.SAMPLE_ID AND sa.SAMPLE_ID=aj.SAMPLE_ID and tmp.EXT_STUDY_ID = st.EXT_STUDY_ID and aj.EXPERIMENT_TYPE_ID<>3 and aj.ANALYSIS_STATUS_ID=3 and tmp.run_count > 1 and st.SUBMISSION_ACCOUNT_ID = '" + submissionAccountId + "' group by st.EXT_STUDY_ID order by st.STUDY_NAME");
            }
            final String sql = sb.toString();

//            List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

            List<CompareToolStudyVO> results = jdbcTemplate.query(sql, new BeanPropertyRowMapper<CompareToolStudyVO>(CompareToolStudyVO.class));
            return results;

        } catch (EmptyResultDataAccessException exception) {
            throw new EmptyResultDataAccessException(1);
        }
    }

    private List<String> parseNonAmpliconStudiesResultHomePage(List<Object> results) {
        List<String> result = new ArrayList<>();
        for (Object resultItem : results) {
            String externalStudyId = (String) resultItem;
            result.add(externalStudyId);
        }
        return result;
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
    public List<Study> retrieveOrderedPublicStudies(final Boolean isDescendingOrder,
                                                    final String propertyName) {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Study.class);
            criteria.add(Restrictions.eq("isPublic", 1));
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

    @Transactional(readOnly = true)
    public Map<String, Long> retrieveRunCountsGroupedByExternalStudyId(Collection<String> externalStudyIds) {
        if (externalStudyIds != null && externalStudyIds.size() > 0) {
            Session session = sessionFactory.getCurrentSession();
            if (session != null) {
                try {
                    //Distinct count, which means multiple analysis versions of the same run will not take into account
                    Query query = session.createQuery("select p.studyId, count(distinct aj.externalRunIDs) as count FROM Study p inner join p.samples sample left join sample.analysisJobs as aj  where p.studyId in (:studyIds) group by p.studyId");
                    query.setParameterList("studyIds", externalStudyIds);
                    List results = query.list();
                    Map<String, Long> transformedResults = transformResultsToMap(results);
                    return transformedResults;
                } catch (HibernateException e) {
                    throw new HibernateException("Couldn't retrieve grouped run counts.", e);
                }
            }
        }
        return new HashMap<>();
    }


    @Transactional(readOnly = true)
    public List<String> retrieveNonAmpliconStudies(Collection<String> externalStudyIds) {
        if (externalStudyIds != null && externalStudyIds.size() > 0) {
            Session session = sessionFactory.getCurrentSession();
            if (session != null) {
                try {
                    Query query = session.createQuery("SELECT t1.studyId FROM Study as t1 INNER JOIN t1.samples as t2 INNER JOIN t2.analysisJobs as t3 where t1.studyId in (:studyIds) and t3.experimentType.experimentTypeId != 3 group by t1.studyName");
                    query.setParameterList("studyIds", externalStudyIds);
                    List results = query.list();
                    return parseNonAmpliconStudiesResultHomePage(results);
                } catch (HibernateException e) {
                    throw new HibernateException("Couldn't non amplicon studies.", e);
                }
            }
        }
        return new ArrayList<>();
    }

    private Map<String, Long> transformResultsToMap(List<Object[]> results) {
        Map<String, Long> result = new HashMap<String, Long>();
        for (Object[] resultItem : results) {
            result.put((String) resultItem[0], (Long) resultItem[1]);
        }
        return result;
    }

    public StudyStatisticsVO retrieveStatistics() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            try {
                StudyStatisticsVO stats = new StudyStatisticsVO();
                Query query = session.createQuery("select p.isPublic, count(distinct p.studyId) as num_of_studies from Study p where p.isPublic in (0,1) group by p.isPublic");
                List<Object[]> results = query.list();
                for (Object[] rowFields : results) {
                    int isPublic = (Integer) rowFields[0];
                    long numOfStudies = (Long) rowFields[1];
                    if (isPublic == 1) {
                        stats.setNumOfPublicStudies(numOfStudies);
                    } else {
                        stats.setNumOfPrivateStudies(numOfStudies);
                    }
                }
                return stats;
            } catch (DataAccessException exception) {
                throw exception;
            }
        }
        return null;
    }

    public Map<Long, Long> retrieveSampleCountsPerStudy() {
        Session session = sessionFactory.getCurrentSession();
        if (session != null) {
            try {
                Map<Long, Long> result = new HashMap<Long, Long>();

                StringBuilder sql = new StringBuilder("SELECT stsa.study_id as study_id, count(sa.sample_id) as sample_count ");
                sql.append("FROM STUDY_SAMPLE stsa left outer join SAMPLE sa on stsa.sample_id = sa.sample_id ");
                sql.append("where (sa.is_public = 1 OR sa.is_public = 0) group by stsa.study_id");
                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString());
                for (Map<String, Object> row : rows) {
                    result.put(new Long((int) row.get("STUDY_ID")), (Long) row.get("SAMPLE_COUNT"));
                }
                return result;
            } catch (DataAccessException exception) {
                throw exception;
            }
        }
        return null;
    }
}