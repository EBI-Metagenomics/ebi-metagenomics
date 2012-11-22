package uk.ac.ebi.interpro.metagenomics.memi.basic;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import uk.ac.ebi.interpro.metagenomics.memi.controller.HomePageController;
import uk.ac.ebi.interpro.metagenomics.memi.controller.ModelPopulator;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.HomePageErrorViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.HomePageErrorViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customized exception resolver.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class CustomizedExceptionResolver extends SimpleMappingExceptionResolver {

    @Resource
    protected SessionManager sessionManager;

    @Resource
    protected MemiPropertyContainer propertyContainer;

    @Resource(name = "emailNotificationServiceExceptionResolver")
    private INotificationService emailService;

    @Override
    public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //Sending email notification
        if (!(ex instanceof MissingServletRequestParameterException)) {
            sendEmail(ex);
        }
        //Build error view
        if (ex instanceof DataAccessException || ex instanceof TransactionException) {
            if (handler instanceof HomePageController) {
                return buildModelAndView(
                        "errors/homeError",
                        new ModelMap(),
                        new ModelPopulator() {
                            @Override
                            public void populateModel(ModelMap model) {
//                            log.info("Building model of " + HomePageController.class + "...");
                                final ViewModelBuilder<HomePageErrorViewModel> builder = new HomePageErrorViewModelBuilder(sessionManager, "EBI metagenomics: archiving, analysis and integration of metagenomics data",
                                        getBreadcrumbsForErrorPage(null, "home"), propertyContainer);
                                final HomePageErrorViewModel errorViewModel = builder.getModel();
                                errorViewModel.changeToHighlightedClass(ViewModel.TAB_CLASS_HOME_VIEW);
                                model.addAttribute(ViewModel.MODEL_ATTR_NAME, errorViewModel);
                            }
                        });
            } else {
                return buildErrorModelAndView("/errors/databaseException");
            }
        }
        ModelAndView modelAndView = super.doResolveException(request, response, handler, ex);
        modelAndView.addAllObjects(buildErrorModelMap());
        return modelAndView;
    }

    protected ModelAndView buildModelAndView(String viewName, ModelMap model, ModelPopulator populator) {
        populator.populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return new ModelAndView(viewName, model);
    }

    protected ModelAndView buildErrorModelAndView(String viewName) {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager,
                "Error page - EBI metagenomics", getBreadcrumbsForErrorPage(null, viewName), null);
        final ViewModel viewModel = builder.getModel();
        ModelMap model = new ModelMap();
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, viewModel);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return new ModelAndView(viewName, model);
    }

    protected ModelMap buildErrorModelMap() {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager,
                "Error page - EBI metagenomics", null, null);
        final ViewModel viewModel = builder.getModel();
        ModelMap model = new ModelMap();
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, viewModel);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return model;
    }

    private List<Breadcrumb> getBreadcrumbsForErrorPage(SecureEntity entity, String viewName) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        result.add(new Breadcrumb("Error Page", "Error appeared. Sorry for any inconvenience!", "/errors/" + viewName));
        return result;
    }

    protected void sendEmail(Exception ex) {
        emailService.sendNotification("Following exception has been occurred:", ex);
    }
}
