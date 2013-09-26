package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.apro.CountryDAO;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SRARegistrationForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.EmailNotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.MemiDownloadService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SRARegistrationModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SRARegistrationViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the controller for the submission forms.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
@RequestMapping('/' + SubmissionController.VIEW_NAME)
public class SubmissionController extends CheckLoginController implements IController {

    private final static Log log = LogFactory.getLog(SubmissionController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "submit";

    public static final String SUCCESS_VIEW_NAME = "submitSuccess";

    @Resource(name = "emailNotificationServiceSRARegistration")
    private INotificationService emailService;

    private final String LOGIN_CONTROLLER_DISPLAY_PARAM = "?display=true";

    @Resource
    private VelocityEngine velocityEngine;

    @Resource
    private MemiDownloadService downloadService;

    @Resource
    private CountryDAO countryDAO;

    @Resource
    private SubmissionContactDAO submissionContactDAO;

    /**
     * Simple get request method. Request mapping annotation is inherited by IController!
     *
     * @param model
     * @return
     */
    public ModelAndView doGet(ModelMap model) {
        Submitter submitter = getSessionSubmitter();
        //If user is logged in
        if (submitter != null) {
            //If user has SRA account
            if (hasSraAccount(submitter.getEmailAddress())) {
                return buildModelAndView(model, "sraWebin");
            } //If user hasn't SRA account
            else {
                return buildModelAndView(model, "register");
            }
        } else {
            return new ModelAndView("redirect:" + LoginPageController.VIEW_NAME + LOGIN_CONTROLLER_DISPLAY_PARAM);
        }
    }


    private ModelAndView buildModelAndView(ModelMap model, String viewName) {
        final ModelPopulator modelPopulator = new SRARegistrationModelPopulator();
        SRARegistrationForm registrationForm = new SRARegistrationForm();
        preFillRegistrationForm(registrationForm);
        model.addAttribute(SRARegistrationForm.MODEL_ATTR_NAME, registrationForm);
        return buildModelAndView(viewName, model, modelPopulator);
    }

    /**
     * Checks if for the specified email address at least 1 SRA account exists.
     *
     * @param emailAddress
     * @return
     */
    private boolean hasSraAccount(String emailAddress) {
        if (submissionContactDAO != null) {
            return submissionContactDAO.checkAccountByEmailAddress(emailAddress);
        } else {
            log.warn("Internal application error. Submission contact data access object is unexpectedly NULL.");
        }
        return false;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doPost(@ModelAttribute(SRARegistrationForm.MODEL_ATTR_NAME) @Valid SRARegistrationForm registrationForm, BindingResult result,
                               ModelMap model, SessionStatus status) {
        final ModelPopulator modelPopulator = new SRARegistrationModelPopulator();
        modelPopulator.populateModel(model);
        if (result.hasErrors()) {
            log.info("The submission form has still validation errors!");
            model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
            return new ModelAndView("register", model);
        }
        if (registrationForm != null) {
            String leaveIt = registrationForm.getLeaveIt();
            if (leaveIt == null || leaveIt.equals("")) {
                String email = registrationForm.getEmail();
                ((EmailNotificationService) emailService).setSender(email);
                ((EmailNotificationService) emailService).setReceiverCC(email);
                String msg = buildEmailMsg(registrationForm);
                log.info("Sending a new SRA account request email to SRA.");
                emailService.sendNotification(msg);
                status.setComplete();
            } else {
                log.warn("Honey pot input field is filled. This might be a bot attack.");
            }
        } else {
            log.error("Internal application error. Registration form object is unexpectedly NULL!");
            return new ModelAndView(DefaultController.EXCEPTION_PAGE_VIEW_NAME);
        }
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return new ModelAndView(SUCCESS_VIEW_NAME, model);
//        } else {
//            return new ModelAndView("redirect:" + LoginPageController.VIEW_NAME + LOGIN_CONTROLLER_DISPLAY_PARAM);
//        }
    }


    /**
     * Builds the email message from the SRA registration form using Velocity..
     *
     * @param registrationForm
     * @return The email message as String representation.
     */
    protected String buildEmailMsg(final SRARegistrationForm registrationForm) {
        Map<String, Object> model = new HashMap<String, Object>();

        //Add submission form to Velocity model
        model.put("institute", registrationForm.getInstitute());
        model.put("country", registrationForm.getCountry());
        model.put("department", registrationForm.getDepartment());
        model.put("centreAcronym", registrationForm.getCentreAcronym());
        model.put("name", registrationForm.getFirstName() + " " + registrationForm.getLastName());
        model.put("email", registrationForm.getEmail());
        model.put("summary", registrationForm.getDataDesc());
        model.put("title", registrationForm.getSubTitle());
        model.put("releaseDate", registrationForm.getReleaseDate());
        model.put("telephoneNumber", registrationForm.getTelephoneNumber());

        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "sra-registration-email.vm", model);
    }


    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Submit", "Submit new data", VIEW_NAME));
        return result;
    }

    class SRARegistrationModelPopulator implements ModelPopulator {
        @Override
        public void populateModel(ModelMap model) {

            final ViewModelBuilder<SRARegistrationModel> builder = new SRARegistrationViewModelBuilder(sessionManager, "Submit data",
                    getBreadcrumbs(null), propertyContainer, countryDAO, getSessionSubmitter());
            final SRARegistrationModel sraRegistrationModel = builder.getModel();
            sraRegistrationModel.changeToHighlightedClass(ViewModel.TAB_CLASS_SUBMIT_VIEW);
            model.addAttribute(ViewModel.MODEL_ATTR_NAME, sraRegistrationModel);
        }
    }

    private void preFillRegistrationForm(SRARegistrationForm registrationForm) {
        Submitter submitter = getSessionSubmitter();
        if (submitter != null) {
            log.info("Pre fill the SRA registration form with submitter details.");
            String email = submitter.getEmailAddress();
            if (email != null) {
                email = email.trim();
            }
            registrationForm.setEmail(email);
            //
            String firstName = submitter.getFirstName();
            if (firstName != null) {
                firstName = firstName.trim();
            }
            registrationForm.setFirstName(firstName);
            //
            String lastName = submitter.getSurname();
            if (lastName != null) {
                lastName = lastName.trim();
            }
            registrationForm.setLastName(lastName);
            //
            if (submitter.getAddress() != null) {
                parseAddressDetails(registrationForm, submitter.getAddress());
            }
            //
            registrationForm.setCountry(submitter.getCountry());
            //
            String telNo = submitter.getTelNO();
            if (telNo != null) {
                telNo = telNo.trim();
            }
            registrationForm.setTelephoneNumber(telNo);
        } else {
            log.info("No submitter attached to the session. Therefore no submitter details to pre fill the registration form.");
        }
    }

    private void parseAddressDetails(SRARegistrationForm registrationForm, String address) {
        try {
            List<String> lines = IOUtils.readLines(new StringReader(address));
            if (lines != null && lines.size() > 3) {
                registrationForm.setDepartment(lines.get(0));
                registrationForm.setInstitute(lines.get(1));
                registrationForm.setPostalAddress(lines.get(2));
                registrationForm.setPostalCode(lines.get(3));
            } else {
                log.warn("Cannot parse the address field, because it has an unexpected format!");
            }
        } catch (IOException e) {
            log.error("Exception occurred while parsing the address field!", e);
        }
    }
}
