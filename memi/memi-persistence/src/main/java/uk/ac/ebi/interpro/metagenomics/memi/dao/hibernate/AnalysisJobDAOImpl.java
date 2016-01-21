package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAOImpl;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.AnalysisJobVO;

import java.util.Collection;
import java.util.List;

/**
 * Represents the implementation class of {@link AnalysisJobDAO}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Repository
public class AnalysisJobDAOImpl extends GenericDAOImpl<AnalysisJob, Long> implements AnalysisJobDAO {

    public AnalysisJobDAOImpl() {
    }

    @Transactional(readOnly = true)
    public AnalysisJob readByRunIdAndVersionDeep(final String externalRunIDs,
                                                 final String releaseVersion,
                                                 final String analysisStatus) {
        try {
            Criteria criteria = getSession().createCriteria(AnalysisJob.class);
            criteria.add(Restrictions.eq("externalRunIDs", externalRunIDs));
            criteria.createAlias("analysisStatus", "status").add(Restrictions.eq("status.analysisStatus", analysisStatus));
            criteria.createAlias("pipelineRelease", "release").add(Restrictions.eq("release.releaseVersion", releaseVersion));
            return (AnalysisJob) criteria.uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve list of analysis jobs for external run ids " + externalRunIDs, e);
        }
    }

    @Transactional(readOnly = true)
    public List<AnalysisJob> readBySampleId(Long sampleId, String analysisStatus) {
        try {
            Criteria criteria = getSession().createCriteria(AnalysisJob.class);
            criteria.add(Restrictions.eq("sample.id", sampleId));
//            criteria.createAlias("sample", "sampleAlias").add(Restrictions.eq("sampleAlias.id", sampleId));
            criteria.createAlias("analysisStatus", "status").add(Restrictions.eq("status.analysisStatus", analysisStatus));
            return criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve list of analysis jobs by sample id " + sampleId, e);
        }
    }

    @Transactional(readOnly = true)
    public List<AnalysisJob> readBySampleIds(List<Long> sampleIds, String analysisStatus) {
        try {
            Criteria criteria = getSession().createCriteria(AnalysisJob.class);
            criteria.add(Restrictions.in("sample.id", sampleIds));
//            criteria.createAlias("sample", "sampleAlias").add(Restrictions.eq("sampleAlias.id", sampleId));
            criteria.createAlias("analysisStatus", "status").add(Restrictions.eq("status.analysisStatus", analysisStatus));
            return criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve list of analysis jobs by the following list of sample ids " + sampleIds, e);
        }
    }

    @Transactional(readOnly = true)
    public List<AnalysisJob> readByJobIds(List<Long> jobIds) {
        try {
            Criteria criteria = getSession().createCriteria(AnalysisJob.class);
            criteria.add(Restrictions.in("jobId", jobIds));
            return criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve list of analysis jobs by job ids " + jobIds.toString(), e);
        }
    }

    @Transactional(readOnly = true)
    public List<AnalysisJobVO> retrieveAnalysisJobVOsDeepByStudyId(long studyId, String analysisStatus) {
        try {
            Criteria criteria = getSession().createCriteria(AnalysisJob.class)
                    .createAlias("sample", "sa")
                    .createAlias("experimentType", "et")
                    .createAlias("analysisStatus", "status")
                    .setProjection(Projections.projectionList()
                            .add(Projections.property("jobId"), "jobId")
                            .add(Projections.property("externalRunIDs"), "externalRunIDs")
                            .add(Projections.property("resultDirectory"), "resultDirectory")
                            .add(Projections.property("inputFileName"), "inputFileName")
                            .add(Projections.property("sa.sampleName"), "sampleName")
                            .add(Projections.property("sa.sampleAlias"), "sampleAlias")
                            .add(Projections.property("et.experimentType"), "experimentType")
                    )
                    .add(Restrictions.eq("sa.study.id", studyId))
                    .add(Restrictions.eq("status.analysisStatus", analysisStatus))
                    .setResultTransformer(new AliasToBeanResultTransformer(AnalysisJobVO.class));
            return (List<AnalysisJobVO>) criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve list of analysis jobs by job ids ", e);
        }
    }

    @Override
    public AnalysisJob insert(AnalysisJob newInstance) {
        //TODO: Implement
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<AnalysisJob> insert(Collection<AnalysisJob> newInstances) {
        //TODO: Implement
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void update(AnalysisJob modifiedInstance) {
        //TODO: Implement
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AnalysisJob read(Long id) {
        //TODO: Implement
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AnalysisJob readDeep(Long id, String... deepFields) {
        //TODO: Implement
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(AnalysisJob persistentObject) {
        //TODO: Implement
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long count() {
        //TODO: Implement
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AnalysisJob> retrieveAll() {
        //TODO: Implement
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int deleteAll() {
        //TODO: Implement
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Long getMaximumPrimaryKey() {
        //TODO: Implement
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}