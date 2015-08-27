package uk.ac.ebi.interpro.metagenomics.memi.controller.studies;

import uk.ac.ebi.interpro.metagenomics.memi.controller.SecuredAbstractController;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.StudyDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.SecureEntity;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.springmvc.model.Breadcrumb;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared study controller code.
 */
public abstract class AbstractStudyViewController extends SecuredAbstractController<Study> {

    @Resource
    private StudyDAO studyDAO;

    protected Study getSecuredEntity(final String projectId) {
        return studyDAO.readByStringId(projectId);
    }

    protected List<Breadcrumb> getBreadcrumbs(SecureEntity entity) {
        List<Breadcrumb> result = new ArrayList<Breadcrumb>();
        if (entity != null && entity instanceof Study) {
            result.add(new Breadcrumb("Project: " + ((Study) entity).getStudyName(), "View project " + ((Study) entity).getStudyName(), "projects/" + ((Study) entity).getStudyId()));
        }
        return result;
    }

}
