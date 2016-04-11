package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.analysisPage.DownloadableFileDefinition;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an abstract controller class, which extends all more specific controllers.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public abstract class AbstractController {
    private static final Log log = LogFactory.getLog(AbstractController.class);

    @Resource
    protected SessionManager sessionManager;

    @Resource
    protected MemiPropertyContainer propertyContainer;

    protected abstract String getModelViewName();

    protected abstract List<Breadcrumb> getBreadcrumbs(SecureEntity obj);

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setPropertyContainer(MemiPropertyContainer propertyContainer) {
        this.propertyContainer = propertyContainer;
    }

    //List of exception handler methods

    protected ModelAndView buildModelAndView(String viewName,
                                             ModelMap model,
                                             ModelPopulator populator) {
        populator.populateModel(model);
        // This code ensures that the EBI Search form is
        // instantiated and shared across all controllers
        if (model.containsAttribute(EBISearchForm.MODEL_ATTR_NAME)) {
            sessionManager.setEbiSearchForm((EBISearchForm) model.get(EBISearchForm.MODEL_ATTR_NAME));
        } else if (sessionManager.getEbiSearchForm() == null) {
            sessionManager.setEbiSearchForm(new EBISearchForm());
        }
        model.addAttribute(EBISearchForm.MODEL_ATTR_NAME, sessionManager.getEbiSearchForm());
        if (!model.containsAttribute(LoginForm.MODEL_ATTR_NAME)) {
            model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        }
        return new ModelAndView(viewName, model);
    }

    /**
     * Returns a representation of the current logged in user/submitter, but only if somebody is logged in.
     */
    public Submitter getSessionSubmitter() {
        if (sessionManager != null) {
            if (sessionManager.getSessionBean() != null) {
                return sessionManager.getSessionBean().getSubmitter();
            } else {
                log.warn("Session bean is NULL. It seems like there is an error within the application, because the session bean should never be NULL.");
            }
        } else {
            log.warn("Session manager is NULL. It seems like there is an error within the application, because the session manager should never be NULL.");
        }
        return null;
    }

    public EBISearchForm getEBISearchForm() {
        if (sessionManager != null) {
            if (sessionManager.getEbiSearchForm() != null) {
                return sessionManager.getEbiSearchForm();
            } else {
                log.warn("Session EBISearchForm is NULL. It seems like there is an error within the application, because the form should never be NULL.");
            }
        } else {
            log.warn("Session manager is NULL. It seems like there is an error within the application, because the session manager should never be NULL.");
        }
        return null;
    }
}