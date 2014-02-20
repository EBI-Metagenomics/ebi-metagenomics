package uk.ac.ebi.interpro.metagenomics.memi.services;

/**
 * Represents an general interface for notification services.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public interface INotificationService {

    /* Invoke this method in cases you want to send emails with user submission data */

    public void sendNotification(String message);

    /* Invoke this method in cases an exception occurred */

    public void sendNotification(String message, Exception exception);
}
