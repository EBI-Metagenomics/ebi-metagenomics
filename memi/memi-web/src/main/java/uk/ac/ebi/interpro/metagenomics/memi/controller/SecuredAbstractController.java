package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.model.Run;
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

    protected static final Log log = LogFactory.getLog(SecuredAbstractController.class);

    /**
     * Check if the specified secure entity object has the same submitter Id as the login session user. FYI, each study and sample has
     * a submitter, which is associated as submitter Id property. To submit samples to ENA OR to login into the MG portal you need
     * an ENA account/ you need to be a registered submitter.
     *
     * @param secureEntity
     * @return
     */
    protected boolean isAccessible(T secureEntity) {
        if (secureEntity.isPublic()) {
            return true;
        }
        String warningMsg = "Could not request security entity with ID " + secureEntity.getSecureEntityId() + "!";
        if (userManager != null && userManager.getUserAuthentication() != null) {
            Submitter submitter = userManager.getUserAuthentication().getSubmitter();
            if (submitter != null) {
                if (!submitter.getSubmissionAccountId().equalsIgnoreCase(secureEntity.getSubmissionAccountId())) {
                    log.warn(warningMsg + "Another submitter with ID " + submitter.getSubmissionAccountId() + " tried to access this study!");
                    return false;
                } else {
                    log.info("Submitter with ID " + submitter.getSubmissionAccountId() + " is allowed to access this study.");
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
    protected ModelAndView checkAccessAndBuildModel(ModelProcessingStrategy<T> modelProcessingStrategy, final ModelMap model, final T securedEntity, final String viewName) {
        if (securedEntity == null) {
            return getEntryNotExistMAV();
        } else if (securedEntity instanceof Study || securedEntity instanceof Sample || securedEntity instanceof Run) {
            if (!isAccessible(securedEntity)) {
                log.info("Requesting private study/sample with identifier " + securedEntity.getSecureEntityId() + "...");
                return buildAccessDeniedModelAndView(securedEntity.getSecureEntityId());
            }
        } else {
            throw new IllegalStateException("Unknown implementation of SecureEntity object (neither study nor sample), which cannot be handle.");
        }

        modelProcessingStrategy.processModel(model, securedEntity);
        if (model == null) {
            return null;
        }
        return buildModelAndView(
                viewName,
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                    }
                }
        );
    }

    /**
     * This view is shown if somebody tries to access a private entry OR if session has timed out.
     *
     * @param objectId
     * @return Access denied model and view.
     */
    protected ModelAndView buildAccessDeniedModelAndView(Object objectId) {
        return getModelAndView(DefaultController.ACCESS_DENIED_VIEW_NAME);
    }

    private ModelAndView getModelAndView(final String viewName) {
        final ViewModelBuilder<ViewModel> builder = new DefaultViewModelBuilder(userManager,
                getEbiSearchForm(), "Error page", null, propertyContainer);
        final ViewModel viewModel = builder.getModel();
        ModelMap model = new ModelMap();
        model.addAttribute(ViewModel.MODEL_ATTR_NAME, viewModel);
        //return new ModelAndView(viewName, model);
        return buildModelAndView(
                viewName,
                model,
                new ModelPopulator() {
                    @Override
                    public void populateModel(ModelMap model) {
                    }
                }
        );
    }


    /**
     * This view is shown if somebody types in a entry ID which does not exist.
     *
     * @return Entry not exists model and view.
     */
    private ModelAndView getEntryNotExistMAV() {
        return getModelAndView(DefaultController.ACCESSION_NOT_FOUND_VIEW_NAME);
    }

//    abstract ISecureEntityDAO<T> getDAO();
}