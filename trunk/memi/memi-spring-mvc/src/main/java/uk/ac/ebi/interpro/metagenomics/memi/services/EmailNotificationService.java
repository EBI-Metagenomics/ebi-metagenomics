package uk.ac.ebi.interpro.metagenomics.memi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Represents an email notification service.
 *
 * @author Maxim Scheremetjew, EMBL-EBI, InterPro
 * @version $Id$
 * @since 1.0-SNAPSHOT
 */
public class EmailNotificationService implements INotificationService {

    private JavaMailSenderImpl mailSender;

    public static final Log log = LogFactory.getLog(EmailNotificationService.class);

    public EmailNotificationService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotification(String message) {
        sendNotification(message, null);
    }

    public void sendNotification(String message, Exception exception) {
        //Create a mail message
        MimeMessage mimeMsg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
        try {
            helper.setTo("maxim@ebi.ac.uk");
            helper.setCc("maxim.scheremetjew@gmail.com");
            helper.setFrom("metagenomics-notification-service@ebi.ac.uk");
            helper.setSubject("New submission from the MG portal");
//            helper.setText("Thank you for ordering!");
            if (exception != null) {
                helper.setText("An exception has occured!");
                helper.setText(exception.getMessage());
            } else {
                helper.setText(message, true);
            }

        } catch (MessagingException e) {
            log.error("Could not build the message using the MimeMessageHelper!", e);
            e.printStackTrace();
        }
        //try to send the email
        try {
            this.mailSender.send(mimeMsg);
        } catch (MailException ex) {
            log.fatal("Email notification message could not sent!", ex);
        }
    }
}