package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAOImpl;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.User;

import java.util.List;

/**
 * Represents the implementation class of {@link uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {

    public UserDAOImpl() {
    }

    @Transactional(readOnly = true)
    public User read(Long id) {
        try {
            return (User) getSession().get(User.class, id);
        } catch (HibernateException e) {
            throw new HibernateException("Couldn't retrieve sample by identifier " + id, e);
        }
    }

    @Transactional(readOnly = true)
    public List<User> retrieveAll() {
        return getSession().createCriteria(Sample.class).list();
    }

    @Transactional
    @Override
    public int deleteAll() {
        Query query = getSession().getNamedQuery(User.DELETE_ALL_USERS);
        List result = query.list();
        if (result != null) {
            return result.size();
        }
        return 0;
    }
}