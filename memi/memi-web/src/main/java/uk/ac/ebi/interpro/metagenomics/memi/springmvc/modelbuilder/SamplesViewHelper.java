package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 20/04/16.
 */
public class SamplesViewHelper {

    /**
     * Builds a list of criteria for the specified {@link uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter}}.
     * These criteria can be used for a Hibernate query.
     */
    public static List<Criterion> buildFilterCriteria(SampleFilter filter, String submissionAccountId, BiomeDAO biomeDAO) {
        String searchText = filter.getSearchTerm();

        List<Criterion> crits = new ArrayList<Criterion>();
        //add search term criterion
        if (searchText != null && searchText.trim().length() > 0) {
            crits.add(Restrictions.or(Restrictions.ilike("sampleId", searchText, MatchMode.ANYWHERE), Restrictions.ilike("sampleName", searchText, MatchMode.ANYWHERE)));
        }
        //add 'isPublic' AND submitter identifier criteria
        if (submissionAccountId != null) {
            //Set DEFAULT visibility if not defined
            SampleFilter.SampleVisibility visibility = (filter.getSampleVisibility() == null ? SampleFilter.SampleVisibility.MY_SAMPLES : filter.getSampleVisibility());
            //SELECT * FROM HB_STUDY where submitter_id=?;
            if (visibility.equals(SampleFilter.SampleVisibility.MY_SAMPLES)) {
                crits.add(Restrictions.eq("submissionAccountId", submissionAccountId));
            }
            //select * from hb_study where submitter_id=? and is_public=1;
            else if (visibility.equals(SampleFilter.SampleVisibility.MY_PUBLISHED_SAMPLES)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", 1), Restrictions.eq("submissionAccountId", submissionAccountId)));
            }
            //select * from hb_study where submitter_id=? and is_public=0;
            else if (visibility.equals(SampleFilter.SampleVisibility.MY_PREPUBLISHED_SAMPLES)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", 0), Restrictions.eq("submissionAccountId", submissionAccountId)));
            }
            //select * from hb_study where is_public=1;
            else if (visibility.equals(SampleFilter.SampleVisibility.ALL_PUBLISHED_SAMPLES)) {
                crits.add(Restrictions.eq("isPublic", 1));
            }
            //select * from hb_study where is_public=1 or submitter_id=? and is_public=0;
            else if (visibility.equals(SampleFilter.SampleVisibility.ALL_SAMPLES)) {
                crits.add(Restrictions.or(Restrictions.and(Restrictions.eq("isPublic", 0), Restrictions.eq("submissionAccountId", submissionAccountId)), Restrictions.eq("isPublic", 1)));
            }
        } else {
            crits.add(Restrictions.eq("isPublic", 1));
        }
        if (!filter.getBiome().equals(Biome.ALL)) {
            List<Integer> biomeIds = new ArrayList<Integer>();

            // Soil
            if (filter.getBiome().equals(Biome.SOIL)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.SOIL.getLineages()));
            }
            // Marine
            else if (filter.getBiome().equals(Biome.MARINE)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.MARINE.getLineages()));
            }
            // Forest Soil
            else if (filter.getBiome().equals(Biome.FOREST_SOIL)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.FOREST_SOIL.getLineages()));
            }
            // Freshwater
            else if (filter.getBiome().equals(Biome.FRESHWATER)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.FRESHWATER.getLineages()));
            }
            // Grassland
            else if (filter.getBiome().equals(Biome.GRASSLAND)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.GRASSLAND.getLineages()));
            }
            // Human gut
            else if (filter.getBiome().equals(Biome.HUMAN_GUT)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.HUMAN_GUT.getLineages()));
            }
            //Engineered
            else if (filter.getBiome().equals(Biome.ENGINEERED)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.ENGINEERED.getLineages()));
            }
            // Air
            else if (filter.getBiome().equals(Biome.AIR)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.AIR.getLineages()));
            }
            // Wastewater
            else if (filter.getBiome().equals(Biome.WASTEWATER)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.WASTEWATER.getLineages()));
            }
            // Human host
            else if (filter.getBiome().equals(Biome.HUMAN_HOST)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.HUMAN_HOST.getLineages()));
            }
            // Host-associated
            else if (filter.getBiome().equals(Biome.HOST_ASSOCIATED)) {
                biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, Biome.HOST_ASSOCIATED.getLineages()));
            }
            // All Non-human hosts
            else if (filter.getBiome().equals(Biome.NON_HUMAN_HOST)) {
                List<Integer> biomeIdsForHumanHost = getBiomeIdsByLineage(biomeDAO, Biome.HUMAN_HOST.getLineages());
                List<Integer> biomeIdsForAllHosts = getBiomeIdsByLineage(biomeDAO, Biome.HOST_ASSOCIATED.getLineages());

                //human host is a subset of all hosts
                //so to get all non human host we remove all human host identifiers from the set of all hosts
                biomeIds.addAll(biomeIdsForAllHosts);
                biomeIds.removeAll(biomeIdsForHumanHost);
            }

            crits.add(Restrictions.in("biome.biomeId", biomeIds));
        }
        return crits;
    }


    private static List<Integer> getBiomeIdsByLineage(final BiomeDAO biomeDAO, final String... lineages) {
        List<Integer> biomeIdList = new ArrayList<Integer>();
        for (String lineage : lineages) {
            if (lineage != null && !lineage.isEmpty()) {
                final uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome biome = biomeDAO.readByLineage(lineage);
                if (biome != null) {
                    biomeIdList.addAll(biomeDAO.getListOfBiomeIdsBetween(biome.getLeft(), biome.getRight()));
                }
            }
        }
        return biomeIdList;
    }
}
