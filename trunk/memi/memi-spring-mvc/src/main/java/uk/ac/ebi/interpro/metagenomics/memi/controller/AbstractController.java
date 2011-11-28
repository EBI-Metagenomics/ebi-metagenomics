package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import uk.ac.ebi.interpro.metagenomics.memi.basic.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.EmailNotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Resource(name = "defaultEmailNotificationService")
    private INotificationService emailService;

    protected abstract String getModelViewName();

    protected abstract List<Breadcrumb> getBreadcrumbs(SecureEntity obj);

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setPropertyContainer(MemiPropertyContainer propertyContainer) {
        this.propertyContainer = propertyContainer;
    }

    //List of exception handler methods

    // TODO: This handler does not work for HTTP status code 404 errors
    @ExceptionHandler(NoSuchRequestHandlingMethodException.class)
    public ModelAndView handleNoSuchRequestException(NoSuchRequestHandlingMethodException ex) {
        log.error("Called no such request exception handler!", ex);
        sendEmail(ex);
        return buildErrorModelAndView(CommonController.NO_SUCH_REQUEST_PAGE_VIEW_NAME);
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView handleNPExceptions(NullPointerException ex) {
        log.error("Called Null pointer exception handler!", ex);
        sendEmail(ex);
        return buildErrorModelAndView(CommonController.EXCEPTION_PAGE_VIEW_NAME);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(Exception ex) {
        log.error("Called all exception handler!", ex);
        sendEmail(ex);
        return buildErrorModelAndView(CommonController.EXCEPTION_PAGE_VIEW_NAME);
    }

    private void sendEmail(Exception ex) {
        ((EmailNotificationService) emailService).setSender("mg-portal@ebi.ac.uk");
        ((EmailNotificationService) emailService).setReceiver("maxim@ebi.ac.uk");
        ((EmailNotificationService) emailService).setEmailSubject("[MG portal] Exception occurred");
        emailService.sendNotification("Following exception has been occurred:", ex);
    }

    protected ModelAndView buildModelAndView(String viewName, ModelMap model, ModelPopulator populator) {
        populator.populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return new ModelAndView(viewName, model);
    }

    private ModelAndView buildErrorModelAndView(String viewName) {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager,
                "EBI Metagenomics Portal", getBreadcrumbsForErrorPage(null, viewName), null);
        final ViewModel viewModel = builder.getModel();
        ModelMap model = new ModelMap();
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, viewModel);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return new ModelAndView("/errors/" + viewName, model);
    }

    private List<Breadcrumb> getBreadcrumbsForErrorPage(SecureEntity entity, String viewName) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Error Page", "Error appeared. Sorry for any inconvenience!", "/errors/" + viewName));
        return result;
    }
}