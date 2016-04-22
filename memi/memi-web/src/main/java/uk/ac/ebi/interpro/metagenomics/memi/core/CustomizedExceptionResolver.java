package uk.ac.ebi.interpro.metagenomics.memi.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.TransactionException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import uk.ac.ebi.interpro.metagenomics.memi.controller.ModelPopulator;
import uk.ac.ebi.interpro.metagenomics.memi.exceptionHandling.EntryNotFoundException;
import uk.ac.ebi.interpro.metagenomics.memi.forms.EBISearchForm;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.services.INotificationService;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.UserManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * Represents a customized exception resolver.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class CustomizedExceptionResolver extends SimpleMappingExceptionResolver {

    @Resource
    protected UserManager userManager;

    @Autowired
    private EBISearchForm ebiSearchForm;

    @Resource
    protected MemiPropertyContainer propertyContainer;

    @Resource(name = "emailNotificationServiceExceptionResolver")
    private INotificationService emailService;

    @Override
    public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //Sending email notifications OR render exception specific views
        if (ex instanceof MissingServletRequestParameterException || ex.getClass().getSimpleName().equals("ClientAbortException")) {
            //Ignore
            //DO NOT SEND AN EMAIL OUT
        } else if (ex instanceof EntryNotFoundException || ex instanceof EmptyResultDataAccessException) {
            //DO NOT SEND AN EMAIL OUT
            return buildErrorModelAndView("/entryNotFound");
        } else if (ex instanceof DataAccessException || ex instanceof TransactionException) {
            sendEmail(ex);
            return buildErrorModelAndView("/errors/databaseException");
        } else {
            sendEmail(ex);
        }
        ModelAndView modelAndView = super.doResolveException(request, response, handler, ex);
        modelAndView.addAllObjects(buildErrorModelMap());
        return modelAndView;
    }

    protected ModelAndView buildModelAndView(String viewName, ModelMap model, ModelPopulator populator) {
        populator.populateModel(model);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(EBISearchForm.MODEL_ATTR_NAME, ebiSearchForm);
        return new ModelAndView(viewName, model);
    }

    protected ModelAndView buildErrorModelAndView(String viewName) {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(userManager,
                ebiSearchForm, "Error page", new ArrayList<Breadcrumb>(), null);
        final ViewModel viewModel = builder.getModel();
        ModelMap model = new ModelMap();
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, viewModel);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(EBISearchForm.MODEL_ATTR_NAME, ebiSearchForm);
        return new ModelAndView(viewName, model);
    }

    protected ModelMap buildErrorModelMap() {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(userManager,
                ebiSearchForm, "Error page", new ArrayList<Breadcrumb>(), null);
        final ViewModel viewModel = builder.getModel();
        ModelMap model = new ModelMap();
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, viewModel);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        model.addAttribute(EBISearchForm.MODEL_ATTR_NAME, ebiSearchForm);
        return model;
    }

    protected void sendEmail(Exception ex) {
        emailService.sendNotification("Following exception has been occurred:", ex);
    }
}
