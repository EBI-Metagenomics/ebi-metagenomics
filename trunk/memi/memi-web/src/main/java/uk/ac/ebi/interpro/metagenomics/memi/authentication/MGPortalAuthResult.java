package uk.ac.ebi.interpro.metagenomics.memi.authentication;

import uk.ac.ebi.interpro.metagenomics.memi.model.apro.Submitter;

/**
 * MGPortal authentication result object.
 *
 * @author Maxim Scheremetjew
 */
public class MGPortalAuthResult {

    private Submitter submitter;

    private StatusCode statusCode;

    private String errorMessage;

    public MGPortalAuthResult() {
        this.statusCode = StatusCode.OK;
    }

    public Submitter getSubmitter() {
        return submitter;
    }

    public void setSubmitter(Submitter submitter) {
        this.submitter = submitter;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean failed() {
        return this.submitter == null ? true : false;
    }

    public enum StatusCode {
        AUTHENTICATION_FAILED, UNAUTHORIZED, AUTH_SERVICE_UNAVAILABLE, OK;
    }
}
