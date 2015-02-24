package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAOImpl;
import uk.ac.ebi.interpro.metagenomics.memi.dao.RunDeepRowMapper;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineRelease;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

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

    public List<AnalysisJob> readByRunIdDeep(final String externalRunIDs,
                                             final String analysisStatus) {
        try {
            Criteria criteria = getSession().createCriteria(AnalysisJob.class);
            criteria.add(Restrictions.eq("externalRunIDs", externalRunIDs));
            criteria.createAlias("analysisStatus", "status").add(Restrictions.eq("status.analysisStatus", analysisStatus));
            return criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve list of analysis jobs for external run ids " + externalRunIDs, e);
        }
    }

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