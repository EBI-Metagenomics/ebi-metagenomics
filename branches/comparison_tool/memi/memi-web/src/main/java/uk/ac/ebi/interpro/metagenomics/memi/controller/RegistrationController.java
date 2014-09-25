package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.apro.CountryDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.SRARegistrationForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.SRARegistrationModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.SRARegistrationViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * Handles registration form requests..
 *
 * @author Maxim Scheremetjew
 */
@Controller
public class RegistrationController extends AbstractController implements IController {

    private final static Log log = LogFactory.getLog(RegistrationController.class);

    @Resource
    private CountryDAO countryDAO;


    @RequestMapping(value = "/register")
    public ModelAndView doGet(ModelMap model) {
        final ModelPopulator modelPopulator = new SRARegistrationModelPopulator();
        SRARegistrationForm registrationForm = new SRARegistrationForm();
        preFillRegistrationForm(registrationForm);
        model.addAttribute(SRARegistrationForm.MODEL_ATTR_NAME, registrationForm);
        return buildModelAndView("register", model, modelPopulator);
    }

    private void preFillRegistrationForm(SRARegistrationForm registrationForm) {
        Submitter submitter = getSessionSubmitter();
        if (submitter != null) {
            log.info("Pre fill the SRA registration form with submitter details.");
            String email = submitter.getEmailAddress();
            //trim email address
            if (email != null)
                email.trim();
            registrationForm.setEmail(email);
            //
            String firstName = submitter.getFirstName();
            //trim email address
            if (firstName != null) {
                firstName.trim();
            }
            //
            registrationForm.setFirstName(firstName);
            String lastName = submitter.getSurname();
            //trim last name
            if (lastName != null) {
                lastName.trim();
            }
            registrationForm.setLastName(lastName);
            //
            if (submitter.getAddress() != null) {
                parseAddressDetails(registrationForm, submitter.getAddress());
            }
            //
            registrationForm.setCountry(submitter.getCountry());
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

    @Override
    protected String getModelViewName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected List<Breadcrumb> getBreadcrumbs(SecureEntity obj) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}