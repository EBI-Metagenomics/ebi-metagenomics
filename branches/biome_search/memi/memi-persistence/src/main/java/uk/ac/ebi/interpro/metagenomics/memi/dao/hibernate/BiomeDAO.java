package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.AnalysisJob;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome;

import java.util.List;

/**
 * Represents the data access object interface for analysis jobs.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.4-SNAPSHOT
 */
public interface BiomeDAO {

    public Biome readByLineage(String lineage);

    public List<Biome> readByLineages(String... lineages);

    public List<Integer> getListOfBiomeIdsBetween(int lowValue, int highValue);

    public List<Biome> getAllAncestorsInDescOrder(Biome biome);
}