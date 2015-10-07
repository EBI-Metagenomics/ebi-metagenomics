package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.core.tools.MemiTools;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.BiomeDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.Biome;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SamplesViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewPagination;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Model builder class for SamplesViewModel. See {@link ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class SamplesViewModelBuilder extends AbstractBiomeViewModelBuilder<SamplesViewModel> {

    private final static Log log = LogFactory.getLog(SamplesViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private List<String> tableHeaderNames;

    private SampleDAO sampleDAO;

    private BiomeDAO biomeDAO;

    private SampleFilter filter;

    private int startPosition;

    private final static int PAGE_SIZE = 10;

    public SamplesViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs,
                                   MemiPropertyContainer propertyContainer, List<String> tableHeaderNames,
                                   SampleDAO sampleDAO, SampleFilter filter,
                                   int startPosition, BiomeDAO biomeDAO) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.tableHeaderNames = tableHeaderNames;
        this.sampleDAO = sampleDAO;
        this.filter = filter;
        this.startPosition = startPosition;
        this.biomeDAO = biomeDAO;
    }

    public SamplesViewModel getModel() {
        log.info("Building instance of " + SamplesViewModel.class + "...");
        //Get submitter and submitter Id
        Submitter submitter = getSessionSubmitter(sessionMgr);
        String submissionAccountId = (submitter != null ? submitter.getSubmissionAccountId() : null);
        //Get filtered and sorted samples
        List<Criterion> filterCriteria = buildFilterCriteria(filter, submissionAccountId);
        List<Sample> filteredSamples = getFilteredSamples(sampleDAO, filterCriteria);
        long filteredSampleCount = sampleDAO.countFilteredSamples(filterCriteria);
        ViewPagination pagination = new ViewPagination(startPosition, filteredSampleCount, PAGE_SIZE);
        //Get downloadable samples
        List<Sample> downloadableSamples = sampleDAO.retrieveFilteredSamples(filterCriteria, "sampleName");

        return new SamplesViewModel(submitter, filteredSamples, downloadableSamples, pageTitle, breadcrumbs, propertyContainer, tableHeaderNames, pagination, filter);
    }

    private List<Sample> getFilteredSamples(SampleDAO sampleDAO, List<Criterion> filterCriteria) {
        List<Sample> result = sampleDAO.retrieveFilteredSamples(filterCriteria, startPosition, PAGE_SIZE, "sampleName");
        if (result != null && !result.isEmpty()) {
            for (Sample sample : result) {
                MemiTools.assignBiomeIconCSSClass(sample, biomeDAO);
                MemiTools.assignBiomeIconTitle(sample, biomeDAO);
            }
            return result;
        } else {
            return new ArrayList<Sample>();
        }
    }


//    private Class<? extends Sample> getSampleClass(Sample.SampleType type) {
//        if (type != null) {
//            return type.getClazz();
//        }
//        // Without knowing the type, return the superclass.
//        return Sample.class;
//    }

    /**
     * Builds a list of criteria for the specified {@link uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter}}.
     * These criteria can be used for a Hibernate query.
     */
    private List<Criterion> buildFilterCriteria(SampleFilter filter, String submissionAccountId) {
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
                crits.add(Restrictions.and(Restrictions.eq("isPublic", true), Restrictions.eq("submissionAccountId", submissionAccountId)));
            }
            //select * from hb_study where submitter_id=? and is_public=0;
            else if (visibility.equals(SampleFilter.SampleVisibility.MY_PREPUBLISHED_SAMPLES)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submissionAccountId", submissionAccountId)));
            }
            //select * from hb_study where is_public=1;
            else if (visibility.equals(SampleFilter.SampleVisibility.ALL_PUBLISHED_SAMPLES)) {
                crits.add(Restrictions.eq("isPublic", true));
            }
            //select * from hb_study where is_public=1 or submitter_id=? and is_public=0;
            else if (visibility.equals(SampleFilter.SampleVisibility.ALL_SAMPLES)) {
                crits.add(Restrictions.or(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submissionAccountId", submissionAccountId)), Restrictions.eq("isPublic", true)));
            }
        } else {
            crits.add(Restrictions.eq("isPublic", true));
        }
        if (!filter.getBiome().equals(Biome.ALL)) {
            List<Integer> biomeIds = new ArrayList<Integer>();

            // Soil
            if (filter.getBiome().equals(Biome.SOIL)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.SOIL.getLineages()));
            }
            // Marine
            else if (filter.getBiome().equals(Biome.MARINE)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.MARINE.getLineages()));
            }
            // Forest Soil
            else if (filter.getBiome().equals(Biome.FOREST_SOIL)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.FOREST_SOIL.getLineages()));
            }
            // Freshwater
            else if (filter.getBiome().equals(Biome.FRESHWATER)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.FRESHWATER.getLineages()));
            }
            // Grassland
            else if (filter.getBiome().equals(Biome.GRASSLAND)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.GRASSLAND.getLineages()));
            }
            // Human gut
            else if (filter.getBiome().equals(Biome.HUMAN_GUT)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.HUMAN_GUT.getLineages()));
            }
            //Engineered
            else if (filter.getBiome().equals(Biome.ENGINEERED)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.ENGINEERED.getLineages()));
            }
            // Air
            else if (filter.getBiome().equals(Biome.AIR)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.AIR.getLineages()));
            }
            // Wastewater
            else if (filter.getBiome().equals(Biome.WASTEWATER)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.WASTEWATER.getLineages()));
            }
            // Human host
            else if (filter.getBiome().equals(Biome.HUMAN_HOST)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.HUMAN_HOST.getLineages()));
            }
            // Host-associated
            else if (filter.getBiome().equals(Biome.HOST_ASSOCIATED)) {
                biomeIds.addAll(super.getBiomeIdsByLineage(biomeDAO, Biome.HOST_ASSOCIATED.getLineages()));
            }
            // All Non-human hosts
            else if (filter.getBiome().equals(Biome.NON_HUMAN_HOST)) {
                List<Integer> biomeIdsForHumanHost = super.getBiomeIdsByLineage(biomeDAO, Biome.HUMAN_HOST.getLineages());
                List<Integer> biomeIdsForAllHosts = super.getBiomeIdsByLineage(biomeDAO, Biome.HOST_ASSOCIATED.getLineages());

                //human host is a subset of all hosts
                //so to get all non human host we remove all human host identifiers from the set of all hosts
                biomeIds.addAll(biomeIdsForAllHosts);
                biomeIds.removeAll(biomeIdsForHumanHost);
            }

            crits.add(Restrictions.in("biome.biomeId", biomeIds));
        }
        return crits;
    }
}