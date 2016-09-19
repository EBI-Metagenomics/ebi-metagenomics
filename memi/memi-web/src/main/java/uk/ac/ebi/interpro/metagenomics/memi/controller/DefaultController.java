package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.authentication.AuthenticationService;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import javax.annotation.Resource;

/**
 * Represents a common controller, which currently holds request mappings
 * for simple pages that need no own controller class.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 1.0-SNAPSHOT
 */
@Controller
public class DefaultController {
    private static final Log log = LogFactory.getLog(DefaultController.class);

    public static final String EXCEPTION_PAGE_VIEW_NAME = "exception";

    public static final String ACCESS_DENIED_VIEW_NAME = "accessDenied";

    public static final String ACCESSION_NOT_FOUND_VIEW_NAME = "accessionNotFound";

    @Resource
    private UserManager userManager;

    @Resource
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logoutHandler() {
        if (userManager.getUserAuthentication() != null) {
            Submitter submitter = userManager.getUserAuthentication().getSubmitter();
            if (submitter != null) {
                // Session has not automatically timed out so do need to perform the logout
                if (log.isInfoEnabled()) {
                    log.info("Submitter with user name " + submitter.getLoginName() + " tries to logout...");
                }
                authenticationService.logout(submitter.getSessionId());
                userManager.getUserAuthentication().removeSubmitter();
            }
        }
        return new ModelAndView("redirect:/");
    }
}
