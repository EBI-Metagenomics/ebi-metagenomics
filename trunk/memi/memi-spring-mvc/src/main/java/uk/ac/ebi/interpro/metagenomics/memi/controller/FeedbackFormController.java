package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.encryption.SHA256;
import uk.ac.ebi.interpro.metagenomics.memi.forms.FeedbackForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.*;

/**
 * Represents the feedback form controller for the JQuery feedback dialog on the right hand side, which is display on each page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class FeedbackFormController extends AbstractController {
    private final Log log = LogFactory.getLog(FeedbackFormController.class);

    @Resource
    private SubmitterDAO submitterDAO;

    @RequestMapping(value = "/doFeedback", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, String> doProcessLogin(@RequestParam String emailAddress, @RequestParam String emailSubject,
                                       @RequestParam String emailMessage, @RequestParam String leaveIt,
                                       HttpServletResponse response) {
        // Server side feedback form validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        FeedbackForm feedbackForm = new FeedbackForm(emailAddress, emailSubject, emailMessage, leaveIt);
        Set<ConstraintViolation<FeedbackForm>> constraintViolations = validator.validate(feedbackForm);
        if (!constraintViolations.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> errorMessages = new HashMap<String, String>();
            Iterator<ConstraintViolation<FeedbackForm>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<FeedbackForm> violation = iterator.next();
                String id = violation.getPropertyPath().toString();
                String message = errorMessages.get(id);
                if (message != null) {
                    message = message + "<br>" + violation.getMessage();
                } else {
                    message = violation.getMessage();
                }
                errorMessages.put(id, message);
            }
            return errorMessages;
        }
        return null;
    }

    @RequestMapping(value = "/doFeedbackJSON", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, ? extends Object> getAvailability(@RequestParam String emailAddress, @RequestParam String emailSubject,
                                                  @RequestParam String emailMessage, @RequestParam String leaveIt,
                                                  HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        for (Account a : accounts.values()) {
//            if (a.getName().equals(name)) {
//                return AvailabilityStatus.notAvailable(name);
//            }
//        }
        return Collections.singletonMap("id", 87436);
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
