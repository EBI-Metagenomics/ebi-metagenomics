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
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.session.SessionManager;
import uk.ac.ebi.interpro.metagenomics.memi.tools.MemiModelAndViewFactory;

import javax.annotation.Resource;

/**
 * Represents an abstract controller class, which extends all more specific controllers.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public abstract class AbstractController<T extends SecureEntity> {

    private static final Log log = LogFactory.getLog(AbstractController.class);

    @Resource
    protected SessionManager sessionManager;

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

    protected ModelAndView checkAccessAndBuildModel(ModelProcessingStrategy<T> modelProcessingStrategy, final ModelMap model, final String stringId) {
        ISampleStudyDAO<T> dao = getDAO();
        if (dao != null) {
            final Study study;
            final T securedEntity = dao.readByStringId(stringId);
            if (securedEntity == null) {
                return MemiModelAndViewFactory.getAccessDeniedMAV(stringId);
            } else if (securedEntity instanceof Study) {
                study = (Study) securedEntity;
            } else if (securedEntity instanceof Sample) {
                study = ((Sample) securedEntity).getStudy();
            } else {
                throw new IllegalStateException("Have introduced a new implementation of SecureEntity, but are not handling it in this method.");
            }

            if (!study.isPublic() && !isAccessible(study)) {
                log.info("Requesting private study with ID " + stringId + "...");
                return MemiModelAndViewFactory.getAccessDeniedMAV(stringId);
            }

            modelProcessingStrategy.processModel(model, securedEntity);
        } else {
            log.error("Check why study DAO is null!");
            throw new IllegalStateException("Configuration error - the Study DAO is null");
        }
        return new ModelAndView(getModelViewName(), model);
    }

    abstract ISampleStudyDAO<T> getDAO();

    abstract String getModelViewName();
}
