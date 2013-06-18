package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ebi.interpro.metagenomics.memi.dao.ISampleStudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.Submitter;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Sample;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;

/**
 * Represents a secured abstract controller class, which extends secured specific controllers.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public abstract class SecuredAbstractController<T extends SecureEntity> extends AbstractController {

    private static final Log log = LogFactory.getLog(SecuredAbstractController.class);

    private boolean isAccessible(Study study) {
        String warningMsg = "Could not request private study with ID " + study.getStudyId() + "!";
        if (sessionManager != null && sessionManager.getSessionBean() != null) {
            Submitter submitter = sessionManager.getSessionBean().getSubmitter();
            if (submitter != null) {
                if (submitter.getSubmitterId() != study.getSubmitterId()) {
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

    protected ModelAndView checkAccessAndBuildModel(ModelProcessingStrategy<T> modelProcessingStrategy, final ModelMap model, final String stringId, String viewName) {
        ISampleStudyDAO<T> dao = getDAO();
        if (dao != null) {
            final Study study;
            final T securedEntity = dao.readByStringId(stringId);
            if (securedEntity == null) {
                return getEntryNotExistMAV(stringId);
            } else if (securedEntity instanceof Study) {
                study = (Study) securedEntity;
            } else if (securedEntity instanceof Sample) {
                study = ((Sample) securedEntity).getStudy();
            } else {
                throw new IllegalStateException("Have introduced a new implementation of SecureEntity, but are not handling it in this method.");
            }

            if (!study.isPublic() && !isAccessible(study)) {
                log.info("Requesting private study with ID " + stringId + "...");
                return getAccessDeniedMAV(stringId);
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
    private ModelAndView getAccessDeniedMAV(String objectId) {
        ModelMap model = new ModelMap();
        model.addAttribute("objectId", objectId);
        return new ModelAndView(CommonController.ACCESS_DENIED_VIEW_NAME, model);
    }

    /**
     * This view is shown if somebody types in a entry ID which does not exist.
     *
     * @param objectId
     * @return Entry not exists model and view.
     */
    private ModelAndView getEntryNotExistMAV(String objectId) {
        ModelMap model = new ModelMap();
        model.addAttribute("objectId", objectId);
        return new ModelAndView(CommonController.ENTRY_NOT_FOUND_VIEW_NAME, model);
    }

    abstract ISampleStudyDAO<T> getDAO();
}