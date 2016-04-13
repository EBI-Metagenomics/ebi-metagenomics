package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.authentication.AuthenticationService;
import uk.ac.ebi.interpro.metagenomics.memi.authentication.MGPortalAuthResult;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.ConsentCheckForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.EmailNotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Handles EMG consent check requests.
 *
 * @author Maxim Scheremetjew
 */
@Controller
@RequestMapping("/registration")
@SessionAttributes("consentCheckForm")
public class SubmissionConsentCheckController extends AbstractController {

    private final static Log log = LogFactory.getLog(SubmissionConsentCheckController.class);

    @Resource
    private SubmissionContactDAO submissionContactDAO;

    @Resource
    private AuthenticationService authenticationService;

    @Resource(name = "emailNotificationServiceRegistration")
    private INotificationService emailService;

    @Resource
    private VelocityEngine velocityEngine;


    @RequestMapping
    public ModelAndView handleNewUserRequest(final ModelMap model) {
        // Populate model with necessary standard attributes
        populateModel(model);
        //return new ModelAndView("submission-check/accountCheck", model);
        return buildModelAndView(
                "submission-check/accountCheck",
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        // Populate the model with additional attributes
                        model.addAttribute("consentCheckForm", new ConsentCheckForm(true));
                        model.addAttribute("displayUsernameBox", "none");
                    }
                }
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView handleConsentRequest(final @ModelAttribute("consentCheckForm") @Valid ConsentCheckForm form,
                                             final BindingResult bindingResult,
                                             final ModelMap model,
                                             final HttpServletResponse response) {
        populateModel(model);
        final Submitter submitter = form.getSubmitter();
        String viewName;
        //Used this boolean flag to indicate if a submitter registered with us or just gave consent
        //If FALSE then he just registered, otherwise he gave consent
        final boolean isRegistered = submitter.isRegistered();
        if (isRegistered && bindingResult.hasFieldErrors("consentCheck")) {
            viewName = "submission-check/userNameCheckSummary";
        }

        ((EmailNotificationService) emailService).setReceiverCC(submitter.getEmailAddress());
        final boolean isConsentChecked = form.getConsentCheck();
        String msg = buildMsg(submitter, isConsentChecked);
        emailService.sendNotification(msg);

        //Set registration delay HTTP cookie
        CookieValueObject cookieObject = new CookieValueObject(submitter.getSubmissionAccountId(), true, form.getConsentCheck());
        Cookie registrationCookie = new Cookie("reg-delay", cookieObject.toString()); //bake cookie
        registrationCookie.setMaxAge(604800); //set expire time to 1 week 60x60x24x7
        response.addCookie(registrationCookie); //put cookie in response

        if (isRegistered) {
            viewName = "submission-check/giveConsentSummary";
        } else {
            viewName = "submission-check/registrationSummary";
        }

        return buildModelAndView(
            viewName,
            model,
            new ModelPopulator() {
                @Override
                public void populateModel(ModelMap model) {
                    // Populate the model with additional attributes
                    model.addAttribute("consentCheckForm", new ConsentCheckForm(true));
                    model.addAttribute("displayUsernameBox", "none");
                }
            }
        );
    }

    @RequestMapping(value = "/account-check")
    public ModelAndView handleAccountCheckRequest(final ModelMap model) {
        // Populate model with necessary standard attributes
        populateModel(model);
        // Populate the model with additional attributes
        return buildModelAndView(
                "submission-check/accountCheck",
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                        // Populate the model with additional attributes
                        model.addAttribute("consentCheckForm", new ConsentCheckForm(true));
                        model.addAttribute("displayUsernameBox", "none");
                    }
                }
        );
    }

    /**
     * First step handler (if you want to map each step individually to a method). You should probably either use this
     * approach or the one below (mapping all pages to the same method and getting the page number as parameter).
     */
    @RequestMapping(method = RequestMethod.POST, value = "/account-check")
    public ModelAndView handleAccountCheckRequest(final @ModelAttribute("consentCheckForm") @Valid ConsentCheckForm form,
                                                  final BindingResult bindingResult,
                                                  final ModelMap modelMap,
                                                  @CookieValue(value = "reg-delay", defaultValue = "notAvailable") String registrationCookie) {
        String viewName;
        populateModel(modelMap);
        if (bindingResult.hasFieldErrors("userName") || bindingResult.hasFieldErrors("password") || bindingResult.hasFieldErrors("email")) {
            viewName = "submission-check/accountCheck";
        }
        final String userName = form.getUserName();
        final String email = form.getEmail();
        final String password = form.getPassword();
        MGPortalAuthResult authResult = authenticationService.login(userName, password);
        MGPortalAuthResult.StatusCode statusCode = authResult.getStatusCode();
        //Check if failed
        if (authResult.failed() || !statusCode.equals(MGPortalAuthResult.StatusCode.OK)) {
            bindingResult.addError(new FieldError("loginForm", "userName", userName, false, null, null, ""));
            bindingResult.addError(new FieldError("loginForm", "password", password, false, null, null, authResult.getErrorMessage()));
            modelMap.addAttribute("displayUsernameBox", "block");
            viewName = "submission-check/accountCheck";

        } else {//NOT FAILED

            //Check if user gave consent to allow EMG to access their private
            final Submitter submitter = submissionContactDAO.getSubmitterBySubmissionAccountIdAndEmail(userName, email);

            if (submitter != null) {

                boolean isRegistered = submitter.isRegistered();
                boolean isConsentGiven = submitter.isConsentGiven();
                if (!isRegistered) {
                    //This might be due to the registration delay in ENA (manual step)
                    //Read HTTP cookie
                    if (!registrationCookie.equals("notAvailable")) {//If cookie is set
                        CookieValueObject cookieObject = new CookieValueObject(registrationCookie);
                        //Check if submitters are the same
                        if (submitter.getSubmissionAccountId().toLowerCase().equals(cookieObject.getUserName().toLowerCase())) {//HTTP cookie set for this submitter
                            isRegistered = cookieObject.isRegistered();
                            isConsentGiven = cookieObject.isConsentGiven();
                        }
                    }
                }

                form.setSubmitter(submitter);
                if (submitter.isMainContact()) {
                    //  Now we do have 3 cases
                    //  Case 1: Not registered
                    //  Case 2: Registered, but consent not given
                    //  Case 3: Registered and consent given
                    if (isRegistered) {
                        //  Case 3
                        if (isConsentGiven) {
                            viewName = "submission-check/redirectToWebin";
                        } else {//  Case 2
                            viewName = "submission-check/userNameCheckSummary";
                        }
                    } else {//  Case 1
                        viewName = "submission-check/registerAndGiveConsent";
                    }
                } else {
                    viewName = "submission-check/notMainContact";
                }
            } else {
                bindingResult.addError(new FieldError("loginForm", "userName", userName, false, null, null, ""));
                bindingResult.addError(new FieldError("loginForm", "password", password, false, null, null, "Invalid username or email or password"));
                modelMap.addAttribute("displayUsernameBox", "block");
                viewName = "submission-check/accountCheck";
            }
        }
        return buildModelAndView(
                viewName,
                modelMap,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {}
                }
        );
    }

    /**
     * Builds the email message using Velocity.
     *
     * @param submitter ENA submission account object.
     * @return The email message as String representation.
     */
    protected String buildMsg(final Submitter submitter, final boolean isConsentChecked) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("submitter", submitter);
        model.put("isConsentChecked", isConsentChecked);
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "metagenomics-consent-email.vm", model);
    }

    @RequestMapping(params = "_cancel")
    public ModelAndView processCancel(final HttpServletRequest request,
                                      final HttpServletResponse response,
                                      final SessionStatus status,
                                      final ModelMap modelMap) {
        status.setComplete();
        return buildModelAndView(
                "submission-check/intro",
                modelMap,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {}
                }
        );
    }


    @Override
    protected String getModelViewName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Submit data", "Submit new data", "submission"));
        return result;
    }

    protected void populateModel(ModelMap model) {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager, "Submit data", getBreadcrumbs(null), propertyContainer);
        final ViewModel submitDataModel = builder.getModel();
        submitDataModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SUBMIT_VIEW);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, submitDataModel);
    }

}

class CookieValueObject {
    private final static Log log = LogFactory.getLog(CookieValueObject.class);

    private String userName;

    private boolean isRegistered;

    private boolean isConsentGiven;

    CookieValueObject(String userName, boolean isRegistered, boolean isConsentGiven) {
        this.userName = userName;
        this.isRegistered = isRegistered;
        this.isConsentGiven = isConsentGiven;
    }

    CookieValueObject(String cookieValue) {
        String patternString = "[0-9]+\\|[a-z]+\\|[a-z]+";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(cookieValue).matches()) {
            String[] values = cookieValue.split("\\|");
            if (values.length == 3) {
                this.userName = "Webin-" + values[0];
                this.isRegistered = Boolean.valueOf(values[1]);
                this.isConsentGiven = Boolean.valueOf(values[2]);
            }
        } else {
            log.warn("Cookie value - " + cookieValue + " does NOT match the patter: " + patternString);
        }
    }

    public String getUserName() {
        return userName;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public boolean isConsentGiven() {
        return isConsentGiven;
    }

    @Override
    public String toString() {
        return getUserName().toLowerCase().replace("webin-", "") + "|" + isRegistered() + "|" + isConsentGiven();
    }
}