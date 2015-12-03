package uk.ac.ebi.interpro.metagenomics.memi.model.hibernate;

/**
 * Represents an interface which have to be implemented by {@link uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study} and {@link uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample}. Security entity objects can be checked for
 * privacy and accessibility.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 */
public interface BiomeEntity {

    Biome getBiome();

    void setBiomeIconCSSClass(String biomeIconCSSClass);

    String getBiomeIconCSSClass();

    void setBiomeIconTitle(String biomeIconTitle);

    String getBiomeIconTitle();
}