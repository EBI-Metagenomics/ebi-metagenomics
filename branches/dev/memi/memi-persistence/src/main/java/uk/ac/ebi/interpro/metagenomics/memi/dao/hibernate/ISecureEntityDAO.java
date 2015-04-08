package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import uk.ac.ebi.interpro.metagenomics.memi.dao.GenericDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

/**
 * Represents an interface which defines a method which is shared by {@link Study}s and {@link uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample}s.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface ISecureEntityDAO<T extends SecureEntity> extends GenericDAO<T, Long>{

    T readByStringId(String stringId);
}
