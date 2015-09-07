package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.authentication.AuthenticationService;
import uk.ac.ebi.interpro.metagenomics.memi.authentication.MGPortalAuthResult;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.ConsentCheckForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.EmailNotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // Populate the model with additional attributes
        model.addAttribute("consentCheckForm", new ConsentCheckForm(true));
        model.addAttribute("displayUsernameBox", "none");
        return new ModelAndView("submission-check/accountCheck", model);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView handleConsentRequest(final @ModelAttribute("consentCheckForm") @Valid ConsentCheckForm form,
                                             final BindingResult bindingResult,
                                             final ModelMap model) {
        populateModel(model);
        final Submitter submitter = form.getSubmitter();
        //Used this boolean flag to indicate if a submitter registered with us or just gave consent
        //If FALSE then he just registered, otherwise he gave consent
        final boolean isRegistered = submitter.isRegistered();
        if (isRegistered && bindingResult.hasFieldErrors("consentCheck")) {
            return new ModelAndView("submission-check/userNameCheckSummary", model);
        }

        //TODO: Activate before going live
//        ((EmailNotificationService) emailService).setReceiverCC(submitter.getEmailAddress());
        final boolean isConsentChecked = form.getConsentCheck();
        String msg = buildMsg(submitter, isConsentChecked);
        emailService.sendNotification(msg);
        if (isRegistered) {
            return new ModelAndView("submission-check/giveConsentSummary", model);
        } else {
            return new ModelAndView("submission-check/registrationSummary", model);
        }
    }

    @RequestMapping(value = "/account-check")
    public ModelAndView handleAccountCheckRequest(final ModelMap model) {
        // Populate model with necessary standard attributes
        populateModel(model);
        // Populate the model with additional attributes
        model.addAttribute("consentCheckForm", new ConsentCheckForm());
        model.addAttribute("displayUsernameBox", "none");
        return new ModelAndView("submission-check/accountCheck", model);
    }

    /**
     * First step handler (if you want to map each step individually to a method). You should probably either use this
     * approach or the one below (mapping all pages to the same method and getting the page number as parameter).
     */
    @RequestMapping(method = RequestMethod.POST, value = "/account-check")
    public ModelAndView handleAccountCheckRequest(final @ModelAttribute("consentCheckForm") @Valid ConsentCheckForm form,
                                                  final BindingResult bindingResult,
                                                  final ModelMap modelMap) {
        populateModel(modelMap);
        if (bindingResult.hasFieldErrors("userName") || bindingResult.hasFieldErrors("password") || bindingResult.hasFieldErrors("email")) {
            return new ModelAndView("submission-check/accountCheck", modelMap);
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
            return new ModelAndView("submission-check/accountCheck", modelMap);

        } else {//NOT FAILED

            //Check if user gave consent to allow EMG to access their private
            final Submitter submitter = submissionContactDAO.getSubmitterBySubmissionAccountIdAndEmail(userName, email);

            if (submitter != null) {
                form.setSubmitter(submitter);
                if (submitter.isMainContact()) {
                    //  Now we do have 3 cases
                    //  Case 1: Not registered
                    //  Case 2: Registered, but consent not given
                    //  Case 3: Registered and consent given
                    if (submitter.isRegistered()) {
                        //  Case 3
                        if (submitter.isConsentGiven()) {
                            return new ModelAndView("submission-check/giveConsent", modelMap);
                        } else {//  Case 2
                            return new ModelAndView("submission-check/userNameCheckSummary", modelMap);
                        }
                    } else {//  Case 1
                        return new ModelAndView("submission-check/registerAndGiveConsent", modelMap);
                    }
                } else {
                    return new ModelAndView("submission-check/notMainContact", modelMap);
                }
            } else {
                bindingResult.addError(new FieldError("loginForm", "userName", userName, false, null, null, ""));
                bindingResult.addError(new FieldError("loginForm", "password", password, false, null, null, "Invalid username or email or password"));
                modelMap.addAttribute("displayUsernameBox", "block");
                return new ModelAndView("submission-check/accountCheck", modelMap);
            }
        }
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
        modelMap.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        status.setComplete();
        return new ModelAndView("submission-check/intro", modelMap);
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
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
    }
}