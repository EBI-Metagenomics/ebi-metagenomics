package uk.ac.ebi.interpro.metagenomics.memi.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.interpro.metagenomics.memi.core.MemiPropertyContainer;
import uk.ac.ebi.interpro.metagenomics.memi.dao.hibernate.HealthCheckDAO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * Represents a very simple and basic health check controller..
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @since 3.0.2-SNAPSHOT
 */
@Controller(value = "healthCheckController")
public class HealthCheckController {

    private final Log logging = LogFactory.getLog(HealthCheckController.class);

    @Resource
    private HealthCheckDAO healthCheckDAO;

    @Resource
    protected MemiPropertyContainer propertyContainer;

    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
    @ResponseBody
    public String doGet(HttpServletResponse response) {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        boolean isDatabaseHealthy = performHealthCheckOnDatabase();
        if (!isDatabaseHealthy) {
            logging.error("Database health check failed!");
        }
        boolean isFilesystemHealthy = performHealthCheckOnFileSystem();
        if (!isFilesystemHealthy) {
            logging.error("File system health check failed!");
        }

        if (!isDatabaseHealthy || !isFilesystemHealthy) {
            return "FAILED";
        }
        return "OK";
    }

    private boolean performHealthCheckOnDatabase() {
        return healthCheckDAO.checkDatabaseConnectionAlive();
    }

    private boolean performHealthCheckOnFileSystem() {
        final String rootPath = propertyContainer.getPathToAnalysisDirectory();
        boolean doesExist = new File(rootPath).exists();
        if (!doesExist) {
            return false;
        }
        return true;
    }
}