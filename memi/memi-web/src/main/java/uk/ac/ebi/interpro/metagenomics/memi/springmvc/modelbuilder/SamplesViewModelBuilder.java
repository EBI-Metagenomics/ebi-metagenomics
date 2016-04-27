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
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SampleFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SamplesViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewPagination;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

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

    public SamplesViewModelBuilder(UserManager sessionMgr, EBISearchForm ebiSearchForm, String pageTitle, List<Breadcrumb> breadcrumbs,
                                   MemiPropertyContainer propertyContainer, List<String> tableHeaderNames,
                                   SampleDAO sampleDAO, SampleFilter filter,
                                   int startPosition, BiomeDAO biomeDAO) {
        super(sessionMgr, ebiSearchForm);
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
        EBISearchForm ebiSearchForm = getEbiSearchForm();
        String submissionAccountId = (submitter != null ? submitter.getSubmissionAccountId() : null);
        //Get filtered and sorted samples
        List<Criterion> filterCriteria = SamplesViewHelper.buildFilterCriteria(filter, submissionAccountId, biomeDAO);
        List<Sample> filteredSamples = getFilteredSamples(sampleDAO, filterCriteria);
        long filteredSampleCount = sampleDAO.countFilteredSamples(filterCriteria);
        ViewPagination pagination = new ViewPagination(startPosition, filteredSampleCount, PAGE_SIZE);
        //Get downloadable samples
//        List<Sample> downloadableSamples = sampleDAO.retrieveFilteredSamples(filterCriteria, "sampleName");

        return new SamplesViewModel(submitter, ebiSearchForm, filteredSamples, null, pageTitle, breadcrumbs, propertyContainer, tableHeaderNames, pagination, filter);
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
}