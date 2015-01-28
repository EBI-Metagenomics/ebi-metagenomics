package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAOImpl;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.PipelineRelease;

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


    @Override
    public PipelineRelease read(Long id) {
        try {
            return (PipelineRelease) getSession().get(PipelineRelease.class, id);
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve sample by identifier " + id, e);
        }
    }

    @Override
    public List<PipelineRelease> retrieveAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}