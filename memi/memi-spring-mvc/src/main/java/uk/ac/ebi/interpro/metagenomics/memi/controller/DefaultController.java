package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

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

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView indexHandler(HttpServletRequest request) {
        if (sessionManager.getSessionBean() != null) {
            Submitter submitter = sessionManager.getSessionBean().getSubmitter();
            if (submitter != null) {
                // Session has not automatically timed out so do need to perform the logout
                if (log.isInfoEnabled()) {
                    log.info("Submitter with email address " + submitter.getEmailAddress() + " tries to logout...");
                }
                sessionManager.getSessionBean().removeSubmitter();
            }
        }
        String referer = request.getHeader("Referer");
        if (referer == null || referer.length() == 0) {
            referer = "/metagenomics";
        }
        return new ModelAndView("redirect:" + referer);
    }

    @RequestMapping(value = "/Krona_chart_taxonomy", method = RequestMethod.GET)
       public String doGetKronataxo() {
           return "Krona_chart_taxonomy";
       }
    @RequestMapping(value = "/Krona_chart_function", method = RequestMethod.GET)
       public String doGetKronafunc() {
           return "Krona_chart_function";
       }
    @RequestMapping(value = "/Krona_chart_taxonomy_simple", method = RequestMethod.GET)
       public String doGetKronataxosimple() {
           return "Krona_chart_taxonomy_simple";
       }
    @RequestMapping(value = "/Krona_chart_function_simple", method = RequestMethod.GET)
       public String doGetKronafuncsimple() {
           return "Krona_chart_function_simple";
       }
}
