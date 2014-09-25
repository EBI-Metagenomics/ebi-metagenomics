package uk.ac.ebi.interpro.metagenomics.memi.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        if (sessionFactory == null) {
            throw new IllegalStateException("Session factory is unexpectedly NULL!");
        }
        Session session = sessionFactory.getCurrentSession();
        if (session == null) {
            throw new IllegalStateException("The current session is unexpectedly NULL!");
        }
        return session;
    }

    @Transactional
    public T insert(T entity) {
        getSession().save(entity);
        return entity;
    }

    @Transactional
    public void update(T entity) {
        getSession().update(entity);
    }

    @Transactional
    public void delete(T entity) {
        getSession().delete(entity);
    }

    //TODO: Implement insert collection method
    public Collection<T> insert(Collection<T> newInstances) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Implement read deep method
    public T readDeep(ID id, String... deepFields) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Implement get count method
    public Long count() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Implement delete all method
    public int deleteAll() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Implement get max primary key
    public Long getMaximumPrimaryKey() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
