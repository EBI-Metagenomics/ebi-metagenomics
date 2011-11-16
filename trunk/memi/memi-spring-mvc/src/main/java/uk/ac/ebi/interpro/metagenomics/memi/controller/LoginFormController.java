package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.interpro.metagenomics.memi.dao.SubmitterDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the login controller for the login component on the right hand side, which is display on each page.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class LoginFormController extends LoginController {
    private final Log log = LogFactory.getLog(LoginFormController.class);

    @Autowired
    private Validator validator;

    @Resource
    private SubmitterDAO submitterDAO;

//    @RequestMapping(value = "**/doLogin", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    String doProcessLogin(@RequestParam(required = true) String emailAddress, @RequestParam(required = true) String password, HttpServletResponse response) {
//        LoginForm loginForm = new LoginForm(emailAddress, password);
//        // LoginForm bean validation
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        javax.validation.Validator validator = factory.getValidator();
//        Set<ConstraintViolation<LoginForm>> constraintViolations = validator.validate(loginForm);
//        if (!constraintViolations.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            Iterator<ConstraintViolation<LoginForm>> iterator = constraintViolations.iterator();
//            StringBuffer errorMessage = new StringBuffer();
//            while (iterator.hasNext()) {
//                ConstraintViolation<LoginForm> violation = iterator.next();
//                if (errorMessage.length() > 0) {
//                    errorMessage.append("<br>");
//                }
//                errorMessage.append(violation.getMessage());
//            }
//            return errorMessage.toString();
//        }
//
//        super.processLogin(loginForm, null, null, null);
//        response.setStatus(HttpServletResponse.SC_OK);
//        return null;
//    }

    @RequestMapping(value = "**/doLogin", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, String> doProcessLogin(@ModelAttribute("loginForm") @Valid LoginForm loginForm,
                                       BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return null;
        } else {
            super.processLogin(loginForm, result, null, null);
            if (result.hasFieldErrors()) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                Map<String, String> errorMessages = new HashMap<String, String>();
                errorMessages.put(result.getFieldError().getField(), result.getFieldError().getDefaultMessage());
                return errorMessages;
            }
            return null;
        }

//        Set<ConstraintViolation<LoginForm>> constraintViolations = validator.validate(loginForm);
//        if (!constraintViolations.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            Iterator<ConstraintViolation<LoginForm>> iterator = constraintViolations.iterator();
//            StringBuffer errorMessage = new StringBuffer();
//            while (iterator.hasNext()) {
//                ConstraintViolation<LoginForm> violation = iterator.next();
//                if (errorMessage.length() > 0) {
//                    errorMessage.append("<br>");
//                }
//                errorMessage.append(violation.getMessage());
//            }
//            return errorMessage.toString();
//        }
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