package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.hibernate.criterion.*;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by maxim on 20/04/16.
 */
public class StudiesViewHelper {

    /**
     * Builds a list of criteria for the specified study filter. These criteria can be used for
     * a Hibernate query.
     */
    public static List<Criterion> buildFilterCriteria(final StudyFilter filter, final String submissionAccountId, final BiomeDAO biomeDAO) {
        String searchText = filter.getSearchTerm();
        String biomeLineage = filter.getBiomeLineage();
        Study.StudyStatus studyStatus = filter.getStudyStatus();

        List<Criterion> crits = new ArrayList<Criterion>();
        //add search term criterion
        if (searchText != null && searchText.trim().length() > 0) {
            crits.add(Restrictions.or(Restrictions.ilike("studyId", searchText, MatchMode.ANYWHERE), Restrictions.ilike("studyName", searchText, MatchMode.ANYWHERE)));
        }
        if (biomeLineage != null && biomeLineage.trim().length() > 0) {
//            List<Integer> biomeIds = new ArrayList<Integer>();
//            biomeIds.addAll(getBiomeIdsByLineage(biomeDAO, biomeLineage));
            uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome biome = biomeDAO.readByLineage(biomeLineage);
            if (filter.isIncludingChildren()) {
                DetachedCriteria biomeIds = DetachedCriteria.forClass(uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Biome.class)
                        .setProjection(Projections.projectionList()
                                .add(Projections.property("biomeId"), "biomeId"))
                        .add(Restrictions.between("left", biome.getLeft(), biome.getRight()));
                crits.add(Subqueries.propertyIn("biome", biomeIds));
            } else
                crits.add(Restrictions.eq("biome.biomeId", biome.getBiomeId()));
        }
        //add study status criterion
        if (studyStatus != null) {
            crits.add(Restrictions.eq("studyStatus", studyStatus));
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

            if (!biomeIds.isEmpty()) {
                crits.add(Restrictions.in("biome.biomeId", biomeIds));
            }
        }
        //add is public and submitter identifier criteria
        if (submissionAccountId != null) {
            //Set DEFAULT visibility if not defined
            StudyFilter.StudyVisibility visibility = (filter.getStudyVisibility() == null ? StudyFilter.StudyVisibility.MY_PROJECTS : filter.getStudyVisibility());
            //SELECT * FROM HB_STUDY where submitter_id=?;
            if (visibility.equals(StudyFilter.StudyVisibility.MY_PROJECTS)) {
                crits.add(Restrictions.eq("submissionAccountId", submissionAccountId));
            }
            //select * from hb_study where submitter_id=? and is_public=1;
            else if (visibility.equals(StudyFilter.StudyVisibility.MY_PUBLISHED_PROJECTS)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", 1), Restrictions.eq("submissionAccountId", submissionAccountId)));
            }
            //select * from hb_study where submitter_id=? and is_public=0;
            else if (visibility.equals(StudyFilter.StudyVisibility.MY_PREPUBLISHED_PROJECTS)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", 0), Restrictions.eq("submissionAccountId", submissionAccountId)));
            }
            //select * from hb_study where is_public=1;
            else if (visibility.equals(StudyFilter.StudyVisibility.ALL_PUBLISHED_PROJECTS)) {
                crits.add(Restrictions.eq("isPublic", 1));
            }
            //select * from hb_study where is_public=1 or submitter_id=? and is_public=0;
            else if (visibility.equals(StudyFilter.StudyVisibility.ALL_PROJECTS)) {
                crits.add(Restrictions.or(Restrictions.and(Restrictions.eq("isPublic", 0), Restrictions.eq("submissionAccountId", submissionAccountId)), Restrictions.eq("isPublic", 1)));
            }
        } else {
            crits.add(Restrictions.eq("isPublic", 1));
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

    public static void attachSampleSize(final List<Study> filteredStudies, final SampleDAO sampleDAO) {
        Map<Long, Long> studyToSampleCounts = sampleDAO.retrieveSampleCountsPerStudy();
        for (Study study : filteredStudies) {
            Long sampleCount = studyToSampleCounts.get(study.getId());
            study.setSampleCount(sampleCount);
        }
    }
}
