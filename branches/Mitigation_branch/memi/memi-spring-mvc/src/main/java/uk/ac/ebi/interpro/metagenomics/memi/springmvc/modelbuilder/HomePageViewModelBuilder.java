package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.basic.comparators.HomePageSamplesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.basic.comparators.HomePageStudiesComparator;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.HomePageViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.*;

/**
 * Model builder class for StudyViewModel. See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class HomePageViewModelBuilder extends AbstractViewModelBuilder<HomePageViewModel> {

    private final static Log log = LogFactory.getLog(HomePageViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private StudyDAO studyDAO;

    private SampleDAO sampleDAO;

    /**
     * The number of latest project and samples to show on the home page. Used within this builder class, but also within the Java Server Page.
     */
    private final int maxRowNumberOfLatestItems = 4;


    public HomePageViewModelBuilder(SessionManager sessionMgr, String pageTitle, List<Breadcrumb> breadcrumbs, MemiPropertyContainer propertyContainer,
                                    StudyDAO studyDAO, SampleDAO sampleDAO) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.studyDAO = studyDAO;
        this.sampleDAO = sampleDAO;
    }

    @Override
    public HomePageViewModel getModel() {
        log.info("Building instance of " + HomePageViewModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
//        If case: if nobody is logged in
        if (submitter == null) {
            //retrieve public studies and order them by last meta data received
            List<Study> studies = getPublicStudies(studyDAO);
            Map<Study, Long> publicStudiesMap = getStudySampleSizeMap(studies, sampleDAO, new HomePageStudiesComparator());
            //retrieve public samples and order them by last meta data received
            List<Sample> samples = getPublicSamples(sampleDAO);
            Collections.sort(samples, new HomePageSamplesComparator());
            samples = samples.subList(0, getToIndex(samples));

            Long publicSamplesCount = sampleDAO.countAllPublic();
            Long privateSamplesCount = sampleDAO.countAllPrivate();

            return new HomePageViewModel(submitter, publicStudiesMap, samples,
                    pageTitle, breadcrumbs, propertyContainer, maxRowNumberOfLatestItems, publicSamplesCount, privateSamplesCount);
        }
//        Else case: if somebody is logged in
        else {
            //retrieve private studies and order them by last meta data received
            List<Study> myStudies = getStudiesBySubmitter(submitter.getSubmitterId(), studyDAO);
            Map<Study, Long> myStudiesMap = getStudySampleSizeMap(myStudies, sampleDAO, new HomePageStudiesComparator());

            //retrieve private samples and order them last meta data received
            List<Sample> mySamples = getSamplesBySubmitter(submitter.getSubmitterId(), sampleDAO);
            Collections.sort(mySamples, new HomePageSamplesComparator());
            mySamples = mySamples.subList(0, getToIndex(mySamples));

            //retrieve public studies and order them last meta data received
            List<Study> publicStudies = getPublicStudiesWithoutSubId(submitter.getSubmitterId(), studyDAO);
            Map<Study, Long> publicStudiesMap = getStudySampleSizeMap(publicStudies, sampleDAO, new HomePageStudiesComparator());

            //retrieve public samples and order them last meta data received
            List<Sample> publicSamples = getOrderedPublicSamplesWithoutSubId(submitter.getSubmitterId(), sampleDAO);
            Collections.sort(publicSamples, new HomePageSamplesComparator());
            publicSamples = publicSamples.subList(0, getToIndex(publicSamples));

            return new HomePageViewModel(submitter, publicStudiesMap, publicSamples,
                    myStudiesMap, mySamples, pageTitle, breadcrumbs, propertyContainer, maxRowNumberOfLatestItems);
        }
    }

    /**
     * Returns a list of public studies limited by a specified number of rows and order by meta data received date.
     */
    private List<Study> getPublicStudies(StudyDAO studyDAO) {
        List<Study> studies = new ArrayList<Study>();
        if (studyDAO != null) {
            studies = studyDAO.retrievePublicStudies();
        }
        return studies;
    }

    private Map<Study, Long> getStudySampleSizeMap(List<Study> studies, SampleDAO sampleDAO, Comparator<Study> comparator) {
        Map<Study, Long> result = new TreeMap<Study, Long>(comparator);
        for (Study study : studies) {
            if (sampleDAO != null) {
                result.put(study, sampleDAO.retrieveSampleSizeByStudyId(study.getId()));
            }
        }
        return result;
    }

    /**
     * Returns a list of public sample limited by a specified number of rows and order by received date.
     */
    public List<Sample> getPublicSamples(SampleDAO sampleDAO) {
        List<Sample> samples = new ArrayList<Sample>();
        if (sampleDAO != null) {
            samples = sampleDAO.retrieveAllPublicSamples();
        }
        return samples;
    }

    private int getToIndex(Collection<Sample> collection) {
        return ((collection.size() > 5) ? 5 : collection.size());
    }

    /**
     * Returns a list of studies for the specified submitter.
     */
    private List<Study> getStudiesBySubmitter(long submitterId, StudyDAO studyDAO) {
        List<Study> studies = new ArrayList<Study>();
        if (studyDAO != null) {
            studies = studyDAO.retrieveStudiesBySubmitter(submitterId);
        }
        return studies;
    }

    /**
     * Returns a list of studies for the specified submitter, limited by a specified number of rows.
     */
    private List<Sample> getSamplesBySubmitter(long submitterId, SampleDAO sampleDAO) {
        List<Sample> samples = new ArrayList<Sample>();
        if (sampleDAO != null) {
            samples = sampleDAO.retrieveSamplesBySubmitter(submitterId);
        }
        return samples;
    }

    private List<Study> getPublicStudiesWithoutSubId(long submitterId, StudyDAO studyDAO) {
        List<Study> studies = new ArrayList<Study>();
        if (studyDAO != null) {
            studies = studyDAO.retrievePublicStudiesWithoutSubId(submitterId);
        }
        return studies;
    }

    private List<Sample> getOrderedPublicSamplesWithoutSubId(long submitterId, SampleDAO sampleDAO) {
        List<Sample> samples = new ArrayList<Sample>();
        if (sampleDAO != null) {
            samples = sampleDAO.retrievePublicSamplesWithoutSubId(submitterId);
        }
        return samples;
    }

}