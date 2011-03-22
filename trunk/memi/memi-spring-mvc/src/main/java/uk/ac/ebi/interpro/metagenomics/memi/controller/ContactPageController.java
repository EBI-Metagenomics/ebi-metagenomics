package uk.ac.ebi.interpro.metagenomics.memi.controller;

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
import uk.ac.ebi.interpro.metagenomics.memi.forms.ContactForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.EmailNotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the controller for the MG portal home page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller(value = "contactPageController")
@RequestMapping('/' + ContactPageController.VIEW_NAME)
public class ContactPageController extends AbstractController implements IMGController {

    private final Log log = LogFactory.getLog(ContactPageController.class);

    /**
     * View name of this controller which is used several times.
     */
    public static final String VIEW_NAME = "contact";

    public static final String SUCCESS_VIEW_NAME = "contactSuccess";

    @Resource(name = "emailNotificationServiceContact")
    private INotificationService emailService;

    @Resource(name = "velocityEngine")
    private VelocityEngine velocityEngine;


    @Override
    public ModelAndView doGet(ModelMap model) {
        //build and add the page model
        populateModel(model);
        model.addAttribute(ContactForm.MODEL_ATTR_NAME, new ContactForm());
        return new ModelAndView(VIEW_NAME, model);
    }


    @RequestMapping(params = "submit", method = RequestMethod.POST)
    public ModelAndView doPost(@ModelAttribute("contactForm") @Valid ContactForm contactForm, BindingResult result,
                               ModelMap model, SessionStatus status) {
        populateModel(model);
        if (result.hasErrors()) {
            log.info("Contact form still has validation errors!");
            model.addAttribute(ContactForm.MODEL_ATTR_NAME, contactForm);
            return new ModelAndView(VIEW_NAME, model);
        }
        //build contact email message
        if (contactForm != null) {
            String msg = buildMsg(contactForm);
            ((EmailNotificationService) emailService).setSender(contactForm.getSender());
            ((EmailNotificationService) emailService).setEmailSubject("[beta-feedback] " + contactForm.getEmailSubject());
            ((EmailNotificationService) emailService).setReceiverCC(contactForm.getSender());
            emailService.sendNotification(msg);
            log.info("Sent an email with contact details: " + msg);
            status.setComplete();
        } else {
            return new ModelAndView(CommonController.ERROR_PAGE_VIEW_NAME);
        }
        return new ModelAndView(SUCCESS_VIEW_NAME);
    }


    /**
     * Creates the home page model and adds it to the specified model map.
     * @param model The model map to populate
     */
    private void populateModel(ModelMap model) {
        final ContactModel contactModel = MGModelFactory.getContactModel(sessionManager, "Metagenomics Contact", getBreadcrumbs(null));
        model.addAttribute(MGModel.MODEL_ATTR_NAME, contactModel);
    }

    /**
     * Builds the email message from the contact form using Velocity..
     *
     * @param contactForm Contact form object from which the user input will be read out.
     * @return The email message as String representation.
     */
    protected String buildMsg(ContactForm contactForm) {
        Map<String, Object> model = new HashMap<String, Object>();
        //Add contact form to Velocity model
        model.put("contactForm", contactForm);
        //Add logged in user to Velocity model
        if (sessionManager != null && sessionManager.getSessionBean() != null) {
            model.put("submitter", sessionManager.getSessionBean().getSubmitter());
        }
        return VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, "WEB-INF/velocity_templates/contact-email.vm", model);
    }

    protected String getModelViewName() {
        return VIEW_NAME;
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Contact", "Contact us", VIEW_NAME));
        return result;
    }
}
