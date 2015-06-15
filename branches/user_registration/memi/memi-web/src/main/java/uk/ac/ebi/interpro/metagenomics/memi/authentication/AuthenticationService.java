package uk.ac.ebi.interpro.metagenomics.memi.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ac.ebi.ena.authentication.client.AuthenticationClient;
import uk.ac.ebi.ena.authentication.exception.AuthException;
import uk.ac.ebi.ena.authentication.model.AuthRealm;
import uk.ac.ebi.ena.authentication.model.AuthResult;
import uk.ac.ebi.interpro.metagenomics.memi.dao.erapro.SubmissionContactDAO;
import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an authentication service using ENA's authentication client API. You will find more documentation on how to use ENA's API on Confluence, if you search for 'ena-authentication'.
 * <p/>
 * NOTE: As ENA do not have a load balancer in place yet for both tomcat instance,
 * we need to cope with 2 authentication clients at the moment.
 *
 * @author Maxim Scheremetjew
 */
public class AuthenticationService {
    private final Log log = LogFactory.getLog(AuthenticationService.class);

    @Resource(name = "authenticationClientTomcat9")
    private AuthenticationClient authenticationClientTomcat9;

    @Resource
    private SubmissionContactDAO submissionContactDAO;

    public MGPortalAuthResult login(final String username, final String password) {
        MGPortalAuthResult mgPortalAuthResult = new MGPortalAuthResult();
        Submitter submitter = null;
        final List<AuthRealm> realms = Arrays.asList(new AuthRealm[]{AuthRealm.SRA});
        try {
            AuthResult authResult = authenticationClientTomcat9.login(username, password, realms);
            submitter = getSubmissionAccountIdAndCreateSubmitter(authResult);
        }//If authentication failed
        catch (AuthException authException) {
            handleAuthException(mgPortalAuthResult, authException);
        }
        mgPortalAuthResult.setSubmitter(submitter);
        return mgPortalAuthResult;
    }

    public boolean logout(final String sessionId) {
        boolean result = false;
        try {
            result = authenticationClientTomcat9.logout(sessionId);
        } catch (Exception ex) {
            log.info("Log out failed on both tomcat instances!\n" + ex);
        }
        return result;
    }

    private void handleAuthException(final MGPortalAuthResult authResult,
                                     final AuthException authException) {
        String message = authException.getMessage();
        authResult.setErrorMessage(message);
        log.info("Authentication failed: " + message);
        if (message.equalsIgnoreCase("404 Not Found")) {
            authResult.setStatusCode(MGPortalAuthResult.StatusCode.AUTH_SERVICE_UNAVAILABLE);
        } else if (message.equalsIgnoreCase("Invalid username or password")) {
            authResult.setStatusCode(MGPortalAuthResult.StatusCode.UNAUTHORIZED);
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