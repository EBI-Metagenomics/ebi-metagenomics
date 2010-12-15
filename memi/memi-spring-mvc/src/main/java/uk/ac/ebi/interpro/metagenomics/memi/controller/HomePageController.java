package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.NewsDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.model.News;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the controller for the MG portal home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping(value = "/homePage")
public class HomePageController {

    @Resource
    private EmgSampleDAO emgSampleDAO;

    @Resource
    private EmgStudyDAO emgStudyDAO;

    @Resource
    private NewsDAO newsDAO;


    @RequestMapping(method = RequestMethod.GET)
    public String initPage() {
        return "homePage";
    }

    @ModelAttribute("studyList")
    public List<EmgStudy> populatePublicStudyList() {
//        EmgSample sample = null;
//        if (emgSampleDAO != null) {
//            sample = emgSampleDAO.read("SRS009922");
//        }
        EmgStudy study = null;
        if (emgStudyDAO != null) {
            study = emgStudyDAO.read("SRP001709");
        }
        List<EmgStudy> studies = null;
        if (emgStudyDAO != null) {
            studies = emgStudyDAO.retrieveAll();
        }
        if (studies == null) {
            studies = new ArrayList<EmgStudy>();
        }
        return studies;
    }

    @ModelAttribute("newsList")
    public List<News> populateNewsList() {
        List<News> newsList = newsDAO.getLatestNews();
        if (newsList == null) {
            newsList = new ArrayList<News>();
        }
        return newsList;
    }
}