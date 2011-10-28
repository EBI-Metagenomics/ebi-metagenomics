package uk.ac.ebi.interpro.metagenomics.memi.dao;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Generic data access object (DAO) that can be used with any model class, providing basic CRUD methods.
 * <p/>
 * T is the model type (e.g. Protein, Model, Signature etc.)
 * PK is the type of the primary key (normally {@link java.lang.Long})
 * <p/>
 * Based on the pattern described in
 * <a href ="http://www.ibm.com/developerworks/java/library/j-genericdao.html">Don't repeat the DAO!</a>
 * by Per Mellqvist (per@mellqvist.name) in IBM Developer Works Technical Library, 12 May 2006.
 *
 * @author Phil Jones, EMBL-EBI
 * @author Antony Quinn
 */
public interface GenericDAO<T, PK extends Serializable> extends Serializable {

    /**
     * Hibernate will create in clauses of any size if left to
     * it's own devices, so need to consider this when building
     * potentially unrestricted in-clauses.
     */
    int MAXIMUM_IN_CLAUSE_SIZE = 100;


    /**
     * Insert a new Model instance.
     *
     * @param newInstance being a new instance to persist.
     * @return the inserted Instance.  This MAY NOT be the same object as
     *         has been passed in, for sub-classes that check for the pre-existence of the object
     *         in the database.
     */
    T insert(T newInstance);

    /**
     * Insert a Set of new Model instances.
     *
     * @param newInstances being a Set of instances to persist.
     * @return the Set of persisted instances.
     *         This MAY NOT contain the same objects as
     *         have been passed in, for sub-classes that check for the pre-existence of the object
     *         in the database.
     */
    Collection<T> insert(Collection<T> newInstances);

    /**
     * Update the instance into the database
     *
     * @param modifiedInstance being an attached or unattached, persisted object that has been modified.
     */
    void update(T modifiedInstance);

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key
     *
     * @param id being the primary key value of the required object.
     * @return a single instance of the object with the specified primary key,
     *         or null if it does not exist.
     */
    T read(PK id);

    /**
     * Retrieve an object that was previously persisted to the database using
     * the indicated id as primary key and go deep on the fields listed.
     *
     * @param id         being the primary key value of the required object.
     * @param deepFields being the names of the fields to retrieve with the main object.
     * @return a single instance of the object with the specified primary key,
     *         or null if it does not exist, with the lazy objects initialised.
     */
    T readDeep(PK id, String... deepFields);

    /**
     * Remove an object from persistent storage in the database
     *
     * @param persistentObject being the (attached or unattached) object to be deleted.
     */
    void delete(T persistentObject);

    /**
     * Returns a count of all instances of the type.  Note that select count(object) JSQL
     * returns a Long object.
     *
     * @return a count of all instances of the type.
     */
    Long count();

    /**
     * Returns a List of all the instances of T in the database.
     *
     * @return a List of all the instances of T in the database.
     */
    List<T> retrieveAll();

    /**
     * Deletes all instances of class T in the database.
     *
     * @return the number of rows affected by this operation.
     */
    int deleteAll();

    /**
     * Returns the highest primary key value for the Model class.
     *
     * @return the highest primary key value for the Model class.
     */
    Long getMaximumPrimaryKey();
}
