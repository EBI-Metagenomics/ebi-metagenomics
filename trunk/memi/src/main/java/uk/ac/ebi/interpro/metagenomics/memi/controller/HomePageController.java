package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.interpro.metagenomics.memi.dao.NewsDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.News;
import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

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
    private StudyDAO studyDAO;

    @Resource
    private NewsDAO newsDAO;


    @RequestMapping(method = RequestMethod.GET)
    public String initPage() {
        return "homePage";
    }

    @ModelAttribute("studies")
    public List<Study> populateStudyList() {
        List<Study> studies = studyDAO.getStudiesByVisibility(true);
        if (studies == null) {
            studies = new ArrayList<Study>();
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