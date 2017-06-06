package uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate;

import org.hibernate.criterion.Criterion;
import uk.ac.ebi.interpro.metagenomics.memi.model.hibernate.Study;
import uk.ac.ebi.interpro.metagenomics.memi.model.valueObjects.StudyStatisticsVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Represents the data access object interface for health checks..
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 3.0.2-SNAPSHOT
 */
public interface HealthCheckDAO {

    boolean checkDatabaseConnectionAlive();
}