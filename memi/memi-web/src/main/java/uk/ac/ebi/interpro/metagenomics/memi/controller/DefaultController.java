package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.authentication.AuthenticationService;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

    public static final String ENTRY_NOT_FOUND_VIEW_NAME = "entryNotFound";

    @Resource
    private SessionManager sessionManager;

    @Resource
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView indexHandler(HttpServletRequest request) {
        if (sessionManager.getSessionBean() != null) {
            Submitter submitter = sessionManager.getSessionBean().getSubmitter();
            if (submitter != null) {
                // Session has not automatically timed out so do need to perform the logout
                if (log.isInfoEnabled()) {
                    log.info("Submitter with user name " + submitter.getLoginName() + " tries to logout...");
                }
                authenticationService.logout(submitter.getSessionId());
                sessionManager.getSessionBean().removeSubmitter();
            }
        }
        String referer = request.getHeader("Referer");
        if (referer == null || referer.length() == 0) {
            referer = "/metagenomics";
        }
        return new ModelAndView("redirect:" + referer);
    }
}
