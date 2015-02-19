package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAOImpl;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineRelease;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;

import java.util.Collections;
import java.util.List;

/**
 * Represents the implementation class of {@link PipelineReleaseDAO}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 */
@Repository
public class PipelineReleaseDAOImpl extends GenericDAOImpl<PipelineRelease, Long> implements PipelineReleaseDAO {

    public PipelineReleaseDAOImpl() {
    }


    @Transactional(readOnly = true)
    public PipelineRelease read(Long id) {
        try {
            return (PipelineRelease) getSession().get(PipelineRelease.class, id);
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve pipeline release by identifier " + id, e);
        }
    }

    @Transactional(readOnly = true)
    public List<PipelineRelease> retrieveAll() {
        try {
            return getSession().createCriteria(PipelineRelease.class).list();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve all pipeline release!", e);
        }
    }

    @Transactional(readOnly = true)
    public PipelineRelease readByReleaseVersion(String releaseVersion) {
        try {
            Criteria criteria = getSession().createCriteria(PipelineRelease.class);
            criteria.add(Restrictions.eq("releaseVersion", releaseVersion));
            return (PipelineRelease) criteria.uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve all pipeline release!", e);
        }
    }
}