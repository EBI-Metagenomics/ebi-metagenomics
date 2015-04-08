package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.ui.ModelMap;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;

/**
 * Represents a simple interface that defines a method which is shared between samples and studies controller.
 * This interface is used as a parameter with {@link SecuredAbstractController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface ModelProcessingStrategy<T extends SecureEntity> {
    void processModel(ModelMap model, T secureEntity);
}
