package uk.ac.ebi.interpro.metagenomics.memi.controller;

import uk.ac.ebi.interpro.metagenomics.memi.services.NotificationService;

/**
 * Represents an notification service for unit test.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class EmailNotificationTestService implements NotificationService {

    public void sendNotification(String message) {
        sendNotification(message, null);
    }

    public void sendNotification(String message, Exception exception) {
        //do nothing during Unit testing
    }
}
