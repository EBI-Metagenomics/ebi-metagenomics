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
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Represents the login controller for the login component on the right hand side, which is display on each page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class LoginDialogController extends AbstractController {
    private final Log log = LogFactory.getLog(LoginDialogController.class);

    @Resource
    private SubmitterDAO submitterDAO;

    @RequestMapping(value = "**/doLogin", method = RequestMethod.POST)
    public
    @ResponseBody
    String doProcessLogin(@RequestParam(required = true) String emailAddress, @RequestParam(required = true) String password, HttpServletResponse response) {
        sessionManager.getSessionBean().removeSubmitter();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        // LoginForm bean validation
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator validator = factory.getValidator();
        Set<ConstraintViolation<LoginForm>> constraintViolations = validator.validate(new LoginForm(emailAddress, password));
        if (!constraintViolations.isEmpty()) {
            Iterator<ConstraintViolation<LoginForm>> iterator = constraintViolations.iterator();
            StringBuffer errorMessage = new StringBuffer();
            while (iterator.hasNext()) {
                ConstraintViolation<LoginForm> violation = iterator.next();
                if (errorMessage.length() > 0) {
                    errorMessage.append("<br>");
                }
                errorMessage.append(violation.getMessage());
            }
            return errorMessage.toString();
        }
        Submitter submitter;
        //in general the login form should be never null (just in case anything unexpected happens)
        if (!submitterDAO.isDatabaseAlive()) {
            return "Internal error! We are sorry for any inconvenience.";
        }
        submitter = submitterDAO.getSubmitterByEmailAddress(emailAddress);
        if (submitter != null) {
            String encryptedPw = SHA256.encrypt(password);
            if (encryptedPw == null || !encryptedPw.equals(submitter.getPassword())) {
                return "Login failed. The email address or password was not recognised, please try again.";
            }
        } else {
            log.warn("Could not find any submitter for the specified email address: " + emailAddress);
            return "Login failed. The email address or password was not recognised, please try again.";
        }
        response.setStatus(HttpServletResponse.SC_OK);
        if (submitter != null) {
            sessionManager.getSessionBean().setSubmitter(submitter);
        }
        return null;
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
