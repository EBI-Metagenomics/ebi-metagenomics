package uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.SampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.StudyFilter;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.StudiesViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewPagination;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Model builder class for StudiesViewModel. See {@link uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder} for more information of how to use.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class StudiesViewModelBuilder extends AbstractViewModelBuilder<StudiesViewModel> {

    private final static Log log = LogFactory.getLog(StudiesViewModelBuilder.class);

    private String pageTitle;

    private List<Breadcrumb> breadcrumbs;

    private MemiPropertyContainer propertyContainer;

    private List<String> tableHeaderNames;

    private SampleDAO sampleDAO;

    private StudyDAO studyDAO;

    private StudyFilter filter;

    private int startPosition;

    private final static int PAGE_SIZE = 10;

    private boolean doPagination;

    public StudiesViewModelBuilder(final SessionManager sessionMgr, final String pageTitle, final List<Breadcrumb> breadcrumbs,
                                   final MemiPropertyContainer propertyContainer, final List<String> tableHeaderNames,
                                   final SampleDAO sampleDAO,
                                   final StudyDAO studyDAO,
                                   final StudyFilter filter,
                                   final int startPosition,
                                   final boolean doPagination) {
        super(sessionMgr);
        this.pageTitle = pageTitle;
        this.breadcrumbs = breadcrumbs;
        this.propertyContainer = propertyContainer;
        this.tableHeaderNames = tableHeaderNames;
        this.sampleDAO = sampleDAO;
        this.studyDAO = studyDAO;
        this.filter = filter;
        this.startPosition = startPosition;
        this.doPagination = doPagination;
    }

    public StudiesViewModel getModel() {
        log.info("Building instance of " + StudiesViewModel.class + "...");
        Submitter submitter = getSessionSubmitter(sessionMgr);
        String submissionAccountId = (submitter != null ? submitter.getSubmissionAccountId() : null);

        //Get filtered studies
        List<Criterion> filterCriteria = buildFilterCriteria(filter, submissionAccountId);
        List<Study> filteredStudies = getFilteredStudies(filterCriteria, doPagination);
        long filteredStudiesCount = studyDAO.countByCriteria(filterCriteria);
        ViewPagination pagination = new ViewPagination(startPosition, filteredStudiesCount, PAGE_SIZE);

        //studies are sorted by study name at the moment
//        Map<Study, Long> sortedStudyMap = getStudySampleSizeMap(filteredStudies, sampleDAO, new ViewStudiesComparator());

        attachSampleSize(filteredStudies);
        return new StudiesViewModel(submitter, filteredStudies, null, pageTitle, breadcrumbs, propertyContainer, tableHeaderNames, pagination, filter);
    }

    private void attachSampleSize(List<Study> filteredStudies) {
        for (Study study : filteredStudies) {
            if (sampleDAO != null) {
                long sampleSize = sampleDAO.retrieveSampleSizeByStudyId(study.getId());
                study.setSampleSize(new Long(sampleSize));
            }
        }
    }

    private List<Study> getFilteredStudies(final List<Criterion> criteria,
                                           final boolean doPagination) {
        List<Study> result;
        if (doPagination) {
            result = studyDAO.retrieveFilteredStudies(criteria, startPosition, PAGE_SIZE, false, "studyName");
        } else {
            result = studyDAO.retrieveFilteredStudies(criteria, false, "studyName");
        }
        return (result == null ? new ArrayList<Study>() : result);
    }

    /**
     * Builds a list of criteria for the specified study filter. These criteria can be used for
     * a Hibernate query.
     */
    private static List<Criterion> buildFilterCriteria(final StudyFilter filter, final String submissionAccountId) {
        String searchText = filter.getSearchTerm();
        Study.StudyStatus studyStatus = filter.getStudyStatus();

        List<Criterion> crits = new ArrayList<Criterion>();
        //add search term criterion
        if (searchText != null && searchText.trim().length() > 0) {
            crits.add(Restrictions.or(Restrictions.ilike("studyId", searchText, MatchMode.ANYWHERE), Restrictions.ilike("studyName", searchText, MatchMode.ANYWHERE)));
        }
        //add study status criterion
        if (studyStatus != null) {
            crits.add(Restrictions.eq("studyStatus", studyStatus));
        }
        if (!filter.getBiome().equals(StudyFilter.Biome.ALL)) {
            final List<Integer> biomeIds = new ArrayList<Integer>();
            if (filter.getBiome().equals(StudyFilter.Biome.SOIL)) {
                biomeIds.add(234);
                biomeIds.add(235);
                biomeIds.add(236);
                biomeIds.add(237);
                biomeIds.add(238);
                biomeIds.add(239);
                biomeIds.add(240);
                biomeIds.add(241);
                biomeIds.add(242);
                biomeIds.add(243);
                biomeIds.add(244);
                biomeIds.add(245);
                biomeIds.add(246);
                biomeIds.add(247);
                biomeIds.add(248);
                biomeIds.add(249);
                biomeIds.add(250);
                biomeIds.add(251);
                biomeIds.add(252);
                biomeIds.add(253);
                biomeIds.add(254);
                biomeIds.add(255);
                biomeIds.add(256);
                biomeIds.add(257);
                biomeIds.add(258);
                biomeIds.add(259);
                biomeIds.add(260);
                biomeIds.add(261);
                biomeIds.add(262);
                biomeIds.add(263);
                biomeIds.add(264);
                biomeIds.add(265);
                biomeIds.add(266);
                biomeIds.add(267);
                biomeIds.add(268);
                biomeIds.add(269);
                biomeIds.add(270);
                biomeIds.add(271);
                biomeIds.add(272);
                biomeIds.add(273);
                biomeIds.add(274);
                biomeIds.add(275);
                biomeIds.add(276);
                biomeIds.add(277);
            } else if (filter.getBiome().equals(StudyFilter.Biome.MARINE)) {
                biomeIds.add(127);
                biomeIds.add(128);
                biomeIds.add(129);
                biomeIds.add(130);
                biomeIds.add(131);
                biomeIds.add(132);
                biomeIds.add(133);
                biomeIds.add(134);
                biomeIds.add(135);
                biomeIds.add(136);
                biomeIds.add(137);
                biomeIds.add(138);
                biomeIds.add(139);
                biomeIds.add(140);
                biomeIds.add(141);
                biomeIds.add(142);
                biomeIds.add(145);
                biomeIds.add(146);
                biomeIds.add(147);
                biomeIds.add(148);
                biomeIds.add(149);
                biomeIds.add(143);
                biomeIds.add(144);
                biomeIds.add(150);
                biomeIds.add(151);
                biomeIds.add(152);
                biomeIds.add(153);
                biomeIds.add(154);
                biomeIds.add(155);
                biomeIds.add(156);
                biomeIds.add(157);
                biomeIds.add(158);
                biomeIds.add(159);
                biomeIds.add(160);
                biomeIds.add(161);
                biomeIds.add(162);
                biomeIds.add(163);
                biomeIds.add(164);
                biomeIds.add(165);
                biomeIds.add(166);
                biomeIds.add(167);
                biomeIds.add(168);
                biomeIds.add(169);
                biomeIds.add(170);
                biomeIds.add(171);
                biomeIds.add(172);
                biomeIds.add(173);
                biomeIds.add(174);
                biomeIds.add(175);
                biomeIds.add(176);
                biomeIds.add(177);
                biomeIds.add(178);
                biomeIds.add(179);
                biomeIds.add(180);
                biomeIds.add(181);
                biomeIds.add(182);
                biomeIds.add(183);
                biomeIds.add(184);
                biomeIds.add(185);
                biomeIds.add(186);
            } else if (filter.getBiome().equals(StudyFilter.Biome.MARINE)) {
                biomeIds.add(127);
                biomeIds.add(128);
                biomeIds.add(129);
                biomeIds.add(130);
                biomeIds.add(131);
                biomeIds.add(132);
                biomeIds.add(133);
                biomeIds.add(134);
                biomeIds.add(135);
                biomeIds.add(136);
                biomeIds.add(137);
                biomeIds.add(138);
                biomeIds.add(139);
                biomeIds.add(140);
                biomeIds.add(141);
                biomeIds.add(142);
                biomeIds.add(145);
                biomeIds.add(146);
                biomeIds.add(147);
                biomeIds.add(148);
                biomeIds.add(149);
                biomeIds.add(143);
                biomeIds.add(144);
                biomeIds.add(150);
                biomeIds.add(151);
                biomeIds.add(152);
                biomeIds.add(153);
                biomeIds.add(154);
                biomeIds.add(155);
                biomeIds.add(156);
                biomeIds.add(157);
                biomeIds.add(158);
                biomeIds.add(159);
                biomeIds.add(160);
                biomeIds.add(161);
                biomeIds.add(162);
                biomeIds.add(163);
                biomeIds.add(164);
                biomeIds.add(165);
                biomeIds.add(166);
                biomeIds.add(167);
                biomeIds.add(168);
                biomeIds.add(169);
                biomeIds.add(170);
                biomeIds.add(171);
                biomeIds.add(172);
                biomeIds.add(173);
                biomeIds.add(174);
                biomeIds.add(175);
                biomeIds.add(176);
                biomeIds.add(177);
                biomeIds.add(178);
                biomeIds.add(179);
                biomeIds.add(180);
                biomeIds.add(181);
                biomeIds.add(182);
                biomeIds.add(183);
                biomeIds.add(184);
                biomeIds.add(185);
                biomeIds.add(186);
            } else if (filter.getBiome().equals(StudyFilter.Biome.FRESHWATER)) {
                biomeIds.add(85);
                biomeIds.add(86);
                biomeIds.add(87);
                biomeIds.add(88);
                biomeIds.add(89);
                biomeIds.add(90);
                biomeIds.add(91);
                biomeIds.add(92);
                biomeIds.add(93);
                biomeIds.add(94);
                biomeIds.add(95);
                biomeIds.add(96);
                biomeIds.add(97);
                biomeIds.add(98);
                biomeIds.add(99);
                biomeIds.add(100);
                biomeIds.add(101);
                biomeIds.add(102);
                biomeIds.add(103);
                biomeIds.add(104);
                biomeIds.add(105);
                biomeIds.add(106);
                biomeIds.add(107);
                biomeIds.add(108);
                biomeIds.add(109);
                biomeIds.add(110);
                biomeIds.add(111);
                biomeIds.add(112);
                biomeIds.add(113);
                biomeIds.add(114);
                biomeIds.add(115);
                biomeIds.add(116);
                biomeIds.add(117);
                biomeIds.add(118);
                biomeIds.add(119);
                biomeIds.add(120);
                biomeIds.add(121);
                biomeIds.add(122);
                biomeIds.add(123);
                biomeIds.add(124);
                biomeIds.add(125);
                biomeIds.add(126);
            } else if (filter.getBiome().equals(StudyFilter.Biome.HUMAN_GUT)) {
                biomeIds.add(375);
                biomeIds.add(376);
                biomeIds.add(377);
            } else if (filter.getBiome().equals(StudyFilter.Biome.ENGINEERED)) {
                biomeIds.add(1);
                biomeIds.add(2);
                biomeIds.add(3);
                biomeIds.add(4);
                biomeIds.add(5);
                biomeIds.add(6);
                biomeIds.add(7);
                biomeIds.add(8);
                biomeIds.add(9);
                biomeIds.add(10);
                biomeIds.add(11);
                biomeIds.add(12);
                biomeIds.add(13);
                biomeIds.add(14);
                biomeIds.add(15);
                biomeIds.add(16);
                biomeIds.add(17);
                biomeIds.add(18);
                biomeIds.add(19);
                biomeIds.add(20);
                biomeIds.add(21);
                biomeIds.add(22);
                biomeIds.add(23);
                biomeIds.add(24);
                biomeIds.add(25);
                biomeIds.add(26);
                biomeIds.add(27);
                biomeIds.add(28);
                biomeIds.add(29);
                biomeIds.add(30);
                biomeIds.add(31);
                biomeIds.add(32);
                biomeIds.add(33);
                biomeIds.add(34);
                biomeIds.add(35);
                biomeIds.add(36);
                biomeIds.add(37);
                biomeIds.add(38);
                biomeIds.add(39);
                biomeIds.add(40);
                biomeIds.add(41);
                biomeIds.add(42);
                biomeIds.add(43);
                biomeIds.add(44);
                biomeIds.add(45);
                biomeIds.add(46);
                biomeIds.add(47);
                biomeIds.add(48);
                biomeIds.add(49);
                biomeIds.add(50);
                biomeIds.add(51);
                biomeIds.add(52);
                biomeIds.add(53);
                biomeIds.add(54);
                biomeIds.add(55);
                biomeIds.add(56);
                biomeIds.add(57);
                biomeIds.add(58);
                biomeIds.add(59);
                biomeIds.add(60);
                biomeIds.add(61);
                biomeIds.add(62);
                biomeIds.add(63);
                biomeIds.add(64);
                biomeIds.add(65);
                biomeIds.add(66);
                biomeIds.add(67);
                biomeIds.add(68);
                biomeIds.add(69);
                biomeIds.add(70);
                biomeIds.add(71);
                biomeIds.add(72);
                biomeIds.add(73);
                biomeIds.add(74);
                biomeIds.add(75);
                biomeIds.add(76);
                biomeIds.add(77);
                biomeIds.add(78);
            } else if (filter.getBiome().equals(StudyFilter.Biome.AIR)) {
                biomeIds.add(80);
                biomeIds.add(81);
                biomeIds.add(82);
            } else if (filter.getBiome().equals(StudyFilter.Biome.WASTEWATER)) {
                biomeIds.add(61);
                biomeIds.add(62);
                biomeIds.add(63);
                biomeIds.add(64);
                biomeIds.add(65);
                biomeIds.add(66);
                biomeIds.add(67);
                biomeIds.add(68);
                biomeIds.add(69);
                biomeIds.add(70);
                biomeIds.add(71);
                biomeIds.add(72);
                biomeIds.add(73);
                biomeIds.add(74);
                biomeIds.add(75);
                biomeIds.add(76);
                biomeIds.add(77);
                biomeIds.add(78);
            }
            crits.add(Restrictions.in("biomeHierarchyItem.biomeId", biomeIds));
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
                crits.add(Restrictions.and(Restrictions.eq("isPublic", true), Restrictions.eq("submissionAccountId", submissionAccountId)));
            }
            //select * from hb_study where submitter_id=? and is_public=0;
            else if (visibility.equals(StudyFilter.StudyVisibility.MY_PREPUBLISHED_PROJECTS)) {
                crits.add(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submissionAccountId", submissionAccountId)));
            }
            //select * from hb_study where is_public=1;
            else if (visibility.equals(StudyFilter.StudyVisibility.ALL_PUBLISHED_PROJECTS)) {
                crits.add(Restrictions.eq("isPublic", true));
            }
            //select * from hb_study where is_public=1 or submitter_id=? and is_public=0;
            else if (visibility.equals(StudyFilter.StudyVisibility.ALL_PROJECTS)) {
                crits.add(Restrictions.or(Restrictions.and(Restrictions.eq("isPublic", false), Restrictions.eq("submissionAccountId", submissionAccountId)), Restrictions.eq("isPublic", true)));
            }
        } else {
            crits.add(Restrictions.eq("isPublic", true));
        }

        return crits;
    }
}