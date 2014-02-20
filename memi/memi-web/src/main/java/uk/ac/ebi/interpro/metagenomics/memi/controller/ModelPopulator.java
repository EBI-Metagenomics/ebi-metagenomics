package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.ui.ModelMap;

/**
 * Represents a simple interface that defines a method which is shared between samples and studies controller.
 * This interface is used as a parameter with {@link uk.ac.ebi.interpro.metagenomics.memi.controller.SecuredAbstractController}.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface ModelPopulator {
    void populateModel(ModelMap model);
}
