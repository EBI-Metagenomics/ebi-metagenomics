package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.ISampleStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.forms.LoginForm;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.ViewModel;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.DefaultViewModelBuilder;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.modelbuilder.ViewModelBuilder;

/**
 * Represents a secured abstract controller class, which extends secured specific controllers.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public abstract class SecuredAbstractController<T extends SecureEntity> extends AbstractController {

    private static final Log log = LogFactory.getLog(SecuredAbstractController.class);

    /**
     * Check if the specified secure entity object has the same submitter Id as the login session user. FYI, each study and sample has
     * a submitter, which is associated as submitter Id property. To submit samples to ENA OR to login into the MG portal you need
     * an ENA account/ you need to be a registered submitter.
     *
     * @param secureEntity
     * @return
     */
    private boolean isAccessible(T secureEntity) {
        String warningMsg = "Could not request security entity with ID " + secureEntity.getSecureEntityId() + "!";
        if (sessionManager != null && sessionManager.getSessionBean() != null) {
            Submitter submitter = sessionManager.getSessionBean().getSubmitter();
            if (submitter != null) {
                if (submitter.getSubmitterId() != secureEntity.getSubmitterId()) {
                    log.warn(warningMsg + "Another submitter with ID " + submitter.getSubmitterId() + " tried to access this study!");
                    return false;
                } else {
                    log.info("Submitter with ID " + submitter.getSubmitterId() + " is allowed to access this study.");
                }
            } else {
                log.warn(warningMsg + "There is no submitter associated to the current session!");
                return false;
            }
        } else {
            log.warn(warningMsg + "Session manager resource seems to be null!");
            return false;
        }
        return true;
    }

    /**
     * Checks if the secure entity(study/sample) is public and owned by the logged in user/submitter.
     */
    protected ModelAndView checkAccessAndBuildModel(ModelProcessingStrategy<T> modelProcessingStrategy, final ModelMap model, final String stringId, String viewName) {
        ISampleStudyDAO<T> dao = getDAO();
        if (dao != null) {
            final T securedEntity = dao.readByStringId(stringId);
            if (securedEntity == null) {
                return getEntryNotExistMAV(stringId);
            } else if (securedEntity instanceof Study || securedEntity instanceof Sample) {
                if (!securedEntity.isPublic() && !isAccessible(securedEntity)) {
                    log.info("Requesting private study with ID " + stringId + "...");
                    return buildAccessDeniedModelAndView(stringId);
                }
            } else {
                throw new IllegalStateException("Unknown implementation of SecureEntity object (neither study nor sample), which cannot be handle.");
            }

            modelProcessingStrategy.processModel(model, securedEntity);
        } else {
            log.error("Check why study DAO is null!");
            throw new IllegalStateException("Configuration error - the Study DAO is null");
        }
        return new ModelAndView(viewName, model);
    }

    /**
     * This view is shown if somebody tries to access a private entry OR if session has timed out.
     *
     * @param objectId
     * @return Access denied model and view.
     */
    private ModelAndView buildAccessDeniedModelAndView(String objectId) {
        return getModelAndView(objectId, CommonController.ACCESS_DENIED_VIEW_NAME);
    }

    private ModelAndView getModelAndView(String objectId, String viewName) {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(sessionManager,
                "Error page - EBI metagenomics", null, propertyContainer);
        final ViewModel viewModel = builder.getModel();
        ModelMap model = new ModelMap();
        model.addAttribute("objectId", objectId);
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, viewModel);
        model.addAttribute(LoginForm.MODEL_ATTR_NAME, new LoginForm());
        return new ModelAndView(viewName, model);
    }


    /**
     * This view is shown if somebody types in a entry ID which does not exist.
     *
     * @param objectId
     * @return Entry not exists model and view.
     */
    private ModelAndView getEntryNotExistMAV(String objectId) {
        return getModelAndView(objectId, CommonController.ENTRY_NOT_FOUND_VIEW_NAME);
    }

    abstract ISampleStudyDAO<T> getDAO();
}