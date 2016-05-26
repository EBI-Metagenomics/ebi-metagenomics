package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAOImpl;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.User;

import java.util.Collection;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Represents the implementation class of {@link uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.AnalysisJobDAO}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
@Repository
public class BiomeDAOImpl extends GenericDAOImpl<Biome, Long> implements BiomeDAO {
    private final static Log log = LogFactory.getLog(BiomeDAOImpl.class);

    public BiomeDAOImpl() {
    }

    @Transactional(readOnly = true)
    public Biome readByLineage(String lineage) {
        try {
            Criteria criteria = getSession().createCriteria(Biome.class);
            criteria.add(Restrictions.eq("lineage", lineage));
            return (Biome) criteria.uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve biome by the following lineage: " + lineage, e);
        }
    }

    @Transactional(readOnly = true)
    public List<Biome> readByLineages(String... lineages) {
        try {
            Criteria criteria = getSession().createCriteria(Biome.class)
                    .add(Restrictions.in("lineage", lineages));
            return (List<Biome>) criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve biomes by lineages using the IN clause: " + lineages, e);
        }
    }

    @Transactional(readOnly = true)
    public List<Integer> getListOfBiomeIdsBetween(int lowValue, int highValue) {
        try {
            Criteria criteria = getSession().createCriteria(Biome.class)
                    .setProjection(Projections.projectionList()
                            .add(Projections.property("biomeId"), "biomeId"))
                    .add(Restrictions.between("left", lowValue, highValue));
            return criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve list of biomes using the BETWEEN clause!", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Biome> getAllAncestorsInDescOrder(Biome biome) {
        try {
            Criteria criteria = getSession().createCriteria(Biome.class)
                    .add(Restrictions.gt("right", biome.getRight()))
                    .add(Restrictions.lt("left", biome.getLeft()))
                    .addOrder(Order.desc("left"));
            return criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve list of biomes using the greater/less than clause!", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Object[]> countProjects(){
        try {
            Criteria criteria = getSession().createCriteria(Study.class)
                    .add(Restrictions.eq("isPublic", 1))
                    .setProjection( Projections.projectionList()
                            .add(Projections.rowCount())
                            .add(Projections.groupProperty("biome"),"bm"))
                    .addOrder(Order.asc("bm"));
//            criteria.addOrder( Order.asc("bm.lineage") );
            return criteria.list();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't count the biomes ", e);
        }
    }

    public Biome read(Integer id) {
        try {
            Criteria criteria = getSession().createCriteria(Biome.class);
            criteria.add(Restrictions.eq("biomeId", id));
            return (Biome) criteria.uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve biome by the following id: " + id, e);
        }
    }

    public Biome read(Long id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //    TODO: Implement
    public List<Biome> retrieveAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}