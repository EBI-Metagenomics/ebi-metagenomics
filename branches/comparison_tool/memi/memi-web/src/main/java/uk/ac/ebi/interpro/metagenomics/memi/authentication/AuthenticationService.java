package uk.ac.ebi.interpro.metagenomics.memi.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import uk.ac.ebi.ena.account.admin.client.AuthenticationClient;
import uk.ac.ebi.ena.authentication.model.AuthResult;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import javax.annotation.Resource;

/**
 * NOTE: As ENA do not have a loader balancer inplace yet for both tomcat instance,
 * we need to cope with 2 authentication clients at the moment.
 *
 * @author Maxim Scheremetjew
 */
public class AuthenticationService {
    private final Log log = LogFactory.getLog(AuthenticationService.class);

    @Resource(name = "authenticationClientTomcat9")
    private AuthenticationClient authenticationClientTomcat9;

    @Resource(name = "authenticationClientTomcat10")
    private AuthenticationClient authenticationClientTomcat10;

    @Resource
    private SubmissionContactDAO submissionContactDAO;

    public MGPortalAuthResult login(final String username, final String password) {
        MGPortalAuthResult mgPortalAuthResult = new MGPortalAuthResult();
        Submitter submitter = null;
        try {
            AuthResult authResult = authenticationClientTomcat9.login(username, password);
            submitter = getSubmissionAccountIdAndCreateSubmitter(authResult);
        }//If authentication failed
        catch (HttpClientErrorException ex) {
            handleHttpClientErrorException(mgPortalAuthResult, ex);
        }//If Service is Unavailable (503)
        catch (HttpServerErrorException tomcat9ServerException) {
            log.warn("Tomcat 9 server exception (Status code: " + tomcat9ServerException.getStatusCode().value() + ")!\n" + tomcat9ServerException);
            try {
                AuthResult authResult = authenticationClientTomcat10.login(username, password);
                submitter = getSubmissionAccountIdAndCreateSubmitter(authResult);
            } catch (final HttpClientErrorException ex) {
                handleHttpClientErrorException(mgPortalAuthResult, ex);
            } catch (HttpServerErrorException tomcat10ServerException) {
                log.warn("Both ENA authentication web services (tomcat 9 and 10) are unavailable the moment!");
                mgPortalAuthResult.setStatusCode(MGPortalAuthResult.StatusCode.AUTH_SERVICE_UNAVAILABLE);
                mgPortalAuthResult.setErrorMessage("Authentication service unavailable at the moment. Please try again later.");
            }
        }
        mgPortalAuthResult.setSubmitter(submitter);
        return mgPortalAuthResult;
    }

    public boolean logout(final String sessionId) {
        boolean result = false;
        try {
            result = authenticationClientTomcat9.logout(sessionId);
            if (!result) {
                result = authenticationClientTomcat10.logout(sessionId);
            }
        } catch (Exception ex) {
            log.info("Log out failed on both tomcat instances!\n" + ex);
        }
        return result;
    }

    private void handleHttpClientErrorException(final MGPortalAuthResult authResult,
                                                final HttpClientErrorException ex) {
        int httpStatusValue = ex.getStatusCode().value();
        log.info("Authentication failed with code " + httpStatusValue);
        if (httpStatusValue == 401) {
            authResult.setStatusCode(MGPortalAuthResult.StatusCode.UNAUTHORIZED);
            authResult.setErrorMessage("Incorrect user name or password.");
        } else {
            authResult.setStatusCode(MGPortalAuthResult.StatusCode.AUTHENTICATION_FAILED);
        }
    }

    private Submitter getSubmissionAccountIdAndCreateSubmitter(final AuthResult authResult) {
        final String principle = authResult.getPrinciple();
        log.info("Authenticated as " + principle);
        String submissionAccountId = null;
        if (principle.startsWith("Webin")) {
            submissionAccountId = principle;
        } else {
            submissionAccountId = submissionContactDAO.getSubmissionAccountIdByEmail(principle);
        }
        return new Submitter(authResult.getLoginName(), authResult.getSessionId(), submissionAccountId);
    }
}