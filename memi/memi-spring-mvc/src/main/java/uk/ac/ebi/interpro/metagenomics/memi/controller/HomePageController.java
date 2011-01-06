package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgSampleDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.EmgStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.NewsDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.EmgStudy;
import uk.ac.ebi.interpro.metagenomics.memi.model.News;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;

import javax.annotation.Resource;
import javax.validation.Valid;
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

    @Resource(name = "submitterDAO")
    private SubmitterDAO submitterDAO;

    @Resource
    private EmgStudyDAO emgStudyDAO;

    @Resource
    private NewsDAO newsDAO;

    /**
     * The number of rows, which should be shown on the portal home page.
     */
    private final int rowNumber = 10;

    private final Log log = LogFactory.getLog(HomePageController.class);


    @RequestMapping(method = RequestMethod.GET)
    public String initPage(ModelMap model) {
        LoginForm loginForm = new LoginForm();
        model.put("loginForm", loginForm);
        return "homePage";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView processSubmit(@ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result,
                                      ModelMap model, SessionStatus status) {
        if (result.hasErrors()) {
            return new ModelAndView("homePage");
        }
        loginForm = (LoginForm) model.get("loginForm");
        Submitter submitter = null;
        if (loginForm != null) {
            String emailAddress = loginForm.getEmailAddress();
            if (!submitterDAO.isDatabaseAlive()) {
                result.addError(new FieldError("loginForm", "emailAddress", "Database is down! We are sorry for that."));
                return new ModelAndView("homePage");
            }
            submitter = submitterDAO.getSubmitterByEmailAddress(emailAddress);
            if (submitter != null) {
                if (!submitter.getPassword().equals(loginForm.getPassword())) {
                    result.addError(new FieldError("loginForm", "emailAddress", "Incorrect login data!"));
                    return new ModelAndView("homePage");
                }
            } else {
                log.warn("Could not find any submitter for the specified email address: " + emailAddress);
                result.addError(new FieldError("loginForm", "emailAddress", "Incorrect login data!"));
                return new ModelAndView("homePage");
            }
        } else {
            return new ModelAndView("errorPage");
        }
        //clear the command object from the session
        status.setComplete();
        ModelAndView resultModel = new ModelAndView("loginSuccessPage");
        resultModel.addObject("submitter", submitter);
        return resultModel;
    }


    /**
     * @return A list of studies limited by a specified number of rows
     */
    @ModelAttribute("studyList")
    public List<EmgStudy> populatePublicStudyList() {
        List<EmgStudy> studies = null;
        if (emgStudyDAO != null) {
            studies = emgStudyDAO.retrieveStudiesLimitedByRows(rowNumber);
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