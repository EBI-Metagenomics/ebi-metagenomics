package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import com.sun.syndication.feed.synd.SyndEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.basic.comparators.HomePageSamplesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.basic.comparators.HomePageStudiesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.HibernateStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.feed.RomeClient;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.HomePageErrorViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.HomePageViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.*;

/**
 * Model builder class for HomePageErrorViewModel. See {@link ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class HomePageErrorViewModelBuilder extends AbstractViewModelBuilder<HomePageErrorViewModel> {

    private final static Log log = LogFactory.getLog(HomePageErrorViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private RomeClient rssClient;

    /**
     * The number of RSS news items to show on the portal home page.
     */
    private final int maxRssRowNumber = 3;

    public HomePageErrorViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs,
                                         MemiPropertyContainer propertyContainer, RomeClient rssClient) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.rssClient = rssClient;
    }

    @Override
    public HomePageErrorViewModel getModel() {
        log.info("Building instance of " + HomePageErrorViewModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        // Get RSS URL
        // TODO: Replace local cached feed with Twitter feed when fixed @Cacheable problem
        String rssUrl = "https://api.twitter.com/1/statuses/user_timeline/EBImetagenomics.rss";
        List<SyndEntry> rssEntries = Collections.emptyList();
        try {
            rssEntries = rssClient.getEntries();
            if (rssEntries.size() > maxRssRowNumber) {
                // Limit number of rss entries
                rssEntries = rssEntries.subList(0, maxRssRowNumber);
            }
        } catch (Exception e) {
            log.warn("Could not get RSS entries", e);
        }
        return new HomePageErrorViewModel(submitter, rssUrl, rssEntries, pageTitle, breadcrumbs, propertyContainer);
    }
}