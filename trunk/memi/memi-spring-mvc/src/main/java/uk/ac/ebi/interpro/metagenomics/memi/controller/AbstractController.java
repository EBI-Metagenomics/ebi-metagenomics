package uk.ac.ebi.interpro.metagenomics.memi.controller;

import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;

/**
 * Represents an abstract controller class, which extends all more specific controllers.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public abstract class AbstractController {
    @Resource
    protected SessionManager sessionManager;

    abstract String getModelViewName();
}
