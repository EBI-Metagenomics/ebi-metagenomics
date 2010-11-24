package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.Study;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the controller for the home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping("/homePage")
public class HomePageController {

    private StudyDAO studyDAO;

    @Autowired
    public void setStudyDAO(StudyDAO studyDAO) {
        this.studyDAO = studyDAO;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView createModel() {
        List<Study> studies = studyDAO.getStudiesByVisibility(true);
        ModelMap model = new ModelMap();
        model.put("studies", studies);
        return new ModelAndView("homePage", model);
    }
}