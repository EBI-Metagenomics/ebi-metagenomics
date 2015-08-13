package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

/**
 * Represents an interface which have to be implemented by {@link Study} and {@link Sample}. Security entity objects can be checked for
 * privacy and accessibility.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface SecureEntity {

    /**
     * Should represent the sample or a study Id.
     */
    public String getSecureEntityId();

    /**
     * Defines privacy, if the secure entity is private or public.
     */
    public boolean isPublic();

    /**
     * Defines the Id of the ENA submitter.
     */
//    public Long getSubmitterId();

    public String getSubmissionAccountId();
}
