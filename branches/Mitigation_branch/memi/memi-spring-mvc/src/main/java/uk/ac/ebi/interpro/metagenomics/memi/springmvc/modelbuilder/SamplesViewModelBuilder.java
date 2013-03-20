package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
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
public class SamplesViewModelBuilder extends AbstractViewModelBuilder<SamplesViewModel> {

    private final static Log log = LogFactory.getLog(SamplesViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private List<String> tableHeaderNames;

    private SampleDAO sampleDAO;

    private SampleFilter filter;

    private int startPosition;

    private final static int PAGE_SIZE = 10;

    public SamplesViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs,
                                   MemiPropertyContainer propertyContainer, List<String> tableHeaderNames,
                                   SampleDAO sampleDAO, SampleFilter filter,
                                   int startPosition) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.tableHeaderNames = tableHeaderNames;
        this.sampleDAO = sampleDAO;
        this.filter = filter;
        this.startPosition = startPosition;
    }

    public SamplesViewModel getModel() {
        log.info("Building instance of " + SamplesViewModel.class + "...");
        //Get submitter and submitter Id
        Submitter submitter = getSessionSubmitter(sessionMgr);
        long submitterId = (submitter != null ? submitter.getSubmitterId() : -1L);
        //Get filtered and sorted samples
        List<Criterion> filterCriteria = buildFilterCriteria(filter, submitterId);
        List<Sample> filteredSamples = getFilteredSamples(sampleDAO, filterCriteria);
        long filteredSampleCount = sampleDAO.countFilteredSamples(filterCriteria, getSampleClass(filter.getSampleType()));
        ViewPagination pagination = new ViewPagination(startPosition, filteredSampleCount, PAGE_SIZE);
        //Get downloadable samples
        List<Sample> downloadableSamples = sampleDAO.retrieveFilteredSamples(filterCriteria, getSampleClass(filter.getSampleType()), "sampleName");

        return new SamplesViewModel(submitter, filteredSamples, downloadableSamples, pageTitle, breadcrumbs, propertyContainer, tableHeaderNames, pagination, filter);
    }

    private List<Sample> getFilteredSamples(SampleDAO sampleDAO, List<Criterion> filterCriteria) {
        List<Sample> result = sampleDAO.retrieveFilteredSamples(filterCriteria, getSampleClass(filter.getSampleType()),
                startPosition, PAGE_SIZE, "sampleName");
        return result;
    }


    private Class<? extends Sample> getSampleClass(Sample.SampleType type) {
        if (type != null) {
            return type.getClazz();
        }
        // Without knowing the type, return the superclass.
        return Sample.class;
    }

    /**
     * Builds a list of criteria for the specified {@link uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter}}.
     * These criteria can be used for a Hibernate query.
     */
    private List<Criterion> buildFilterCriteria(SampleFilter filter, long submitterId) {
        String searchText = filter.getSearchTerm();

        List<Criterion> crits = new ArrayList<Criterion>();
        //add search term criterion
        if (searchText != null && searchText.trim().length() > 0) {
            crits.add(Restrictions.or(Restrictions.ilike("sampleId", searchText, MatchMode.ANYWHERE), Restrictions.ilike("sampleName", searchText, MatchMode.ANYWHERE)));
        }
        //add 'isPublic' criterion
        if (submitterId > -1) {
            SampleFilter.SampleVisibility visibility = filter.getSampleVisibility();
            if (visibility == null) {
                visibility = SampleFilter.SampleVisibility.MY_SAMPLES;
            }
            //SELECT * FROM HB_STUDY where submitter_id=?;
            if (visibility.equals(SampleFilter.SampleVisibility.MY_SAMPLES)) {
                crits.add(Restrictions.eq("submitterId", submitterId));
            }
            //select * from hb_study where submitter_id=? and is_public=1;
            else if (visibility.equals(SampleFilter.SampleVisibility.MY_PUBLISHED_SAMPLES)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", true), Restrictions.eq("submitterId", submitterId)));
            }
            //select * from hb_study where submitter_id=? and is_public=0;
            else if (visibility.equals(SampleFilter.SampleVisibility.MY_PREPUBLISHED_SAMPLES)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submitterId", submitterId)));
            }
            //select * from hb_study where is_public=1;
            else if (visibility.equals(SampleFilter.SampleVisibility.ALL_PUBLISHED_SAMPLES)) {
                crits.add(Restrictions.eq("isPublic", true));
            }
            //select * from hb_study where is_public=1 or submitter_id=? and is_public=0;
            else if (visibility.equals(SampleFilter.SampleVisibility.ALL_SAMPLES)) {
                crits.add(Restrictions.or(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submitterId", submitterId)), Restrictions.eq("isPublic", true)));
            }
        } else {
            crits.add(Restrictions.eq("isPublic", true));
        }
        return crits;
    }
}