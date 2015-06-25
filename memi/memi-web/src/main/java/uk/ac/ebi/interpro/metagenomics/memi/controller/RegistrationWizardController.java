package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.RegistrationForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles registration form requests..
 *
 * @author Maxim Scheremetjew
 */
@Controller
@RequestMapping("/wizard.form")
@SessionAttributes("registrationForm")
public class RegistrationWizardController extends AbstractController {

    private final static Log log = LogFactory.getLog(RegistrationWizardController.class);

    @Resource
    private SubmissionContactDAO submissionContactDAO;

    /**
     * The default handler (page=0)
     */
    @RequestMapping
    public ModelAndView doGet(final ModelMap modelMap) {
        // put your initial command
        modelMap.addAttribute("registrationForm", new RegistrationForm());
        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        // populate the model Map as needed
        modelMap.addAttribute("displayUsernameBox", "none");
        return new ModelAndView("user-registration/registAccountCheck", modelMap);
    }

//    @RequestMapping(params = "_target0")
//    public ModelAndView processFirstStep(final @ModelAttribute("registrationForm") RegistrationForm form,
//                                         final Errors errors,
//                                         final ModelMap modelMap) {
//        // do something with command, errors, request, response,
//        // model map or whatever you include among the method
//        // parameters. See the documentation for @RequestMapping
//        // to get the full picture.
//        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
//        modelMap.addAttribute("displayUsernameBox", "none");
//        return new ModelAndView("user-registration/registAccountCheck", modelMap);
//    }

    /**
     * First step handler (if you want to map each step individually to a method). You should probably either use this
     * approach or the one below (mapping all pages to the same method and getting the page number as parameter).
     */
    @RequestMapping(params = "_target1")
    public ModelAndView processSecondStep(final @ModelAttribute("registrationForm") @Valid RegistrationForm form,
                                          final Errors errors,
                                          final ModelMap modelMap) {
        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        if (errors.hasErrors()) {
            if (errors.hasFieldErrors("userName")) {
                return new ModelAndView("user-registration/registAccountCheck", modelMap);
            }
        }


        final String userName = form.getUserName();
        final Submitter submitter = submissionContactDAO.getSubmitterBySubmissionAccountId(userName);

        if (submitter != null) {
            log.info("found user");
            if (submitter.isConsentGiven()) {
                modelMap.addAttribute("accountCheckResult", "User does exist and has given consent!");
                return new ModelAndView("user-registration/registUserNameCheckSummary", modelMap);
            } else {
                modelMap.addAttribute("accountCheckResult", "User does exist but has not given consent!");
                return new ModelAndView("user-registration/registUserNameCheckSummary", modelMap);
            }
        }
        modelMap.addAttribute("accountCheckResult", "User does not exist, please try again.");
        modelMap.addAttribute("displayUsernameBox", "block");
        return new ModelAndView("user-registration/registAccountCheck", modelMap);
    }

    @RequestMapping(params = "_target2")
    public ModelAndView processThirdStep(final @ModelAttribute("registrationForm") @Valid RegistrationForm form,
                                         final Errors errors,
                                         final ModelMap modelMap) {
        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        if (errors.hasFieldErrors("consentCheck")) {

                return new ModelAndView("user-registration/registUserNameCheckSummary", modelMap);
            }

            return new ModelAndView("user-registration/registGiveConsentSummary", modelMap);
    }

    @RequestMapping(params = "_target3")
    public ModelAndView processFourthStep(final @ModelAttribute("registrationForm") @Valid RegistrationForm form,
                                          final Errors errors,
                                          final ModelMap modelMap) {
        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return new ModelAndView("user-registration/registGiveConsent", modelMap);
    }

    @RequestMapping(params = "_target3")
    public ModelAndView processFifthStep(final @ModelAttribute("registrationForm") @Valid RegistrationForm form,
                                         final Errors errors,
                                         final ModelMap modelMap) {
        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return new ModelAndView("user-registration/registGiveConsentSummary", modelMap);
    }

    /**
     * Maybe you want to be provided with the _page parameter (in order to map the same method for all), as you have in
     * AbstractWizardFormController.
     */
//    @RequestMapping(method = RequestMethod.POST)
//    public String processPage(@RequestParam("_page") final int currentPage,
//                              final @ModelAttribute("registrationForm") RegistrationForm form,
//                              final HttpServletResponse response) {
//        // do something based on page number
//        return pageViews[currentPage];
//    }
    @RequestMapping(params = "_cancel")
    public ModelAndView processCancel(final HttpServletRequest request,
                                      final HttpServletResponse response,
                                      final SessionStatus status,
                                      final ModelMap modelMap) {
        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        status.setComplete();
        return new ModelAndView("user-registration/registIntro", modelMap);
    }


    @Override
    protected String getModelViewName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static final String VIEW_NAME = "submit";

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Submit data", "Submit new data", VIEW_NAME));
        return result;
    }
}