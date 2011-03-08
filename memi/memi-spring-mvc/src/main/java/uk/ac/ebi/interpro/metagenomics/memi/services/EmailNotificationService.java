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

    private final static Log log = LogFactory.getLog(EmailNotificationService.class);

    private JavaMailSenderImpl mailSender;

    private String receiver;

    private String receiverCC;

    private String sender;

    private String emailSubject;


    public EmailNotificationService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNotification(String message) {
        sendNotification(message, null);
    }

    public void sendNotification(String message, Exception exception) {
        log.info("Try to sending email notification...");
        //Create a mail message
        MimeMessage mimeMsg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
        try {
            if (receiver != null) {
                helper.setTo(receiver);
            }
            if (receiverCC != null) {
                helper.setCc(receiverCC);
            }
            if (sender != null) {
                helper.setFrom(sender);
            }
            if (emailSubject != null) {
                helper.setSubject(emailSubject);
            }
//            helper.setText("Thank you for ordering!");
            if (exception != null) {
                helper.setText("An exception has occured!");
                helper.setText(exception.getMessage());
            } else {
                helper.setText(message, true);
            }

        } catch (MessagingException e) {
            log.error("Could not build the message using the MimeMessageHelper!", e);
        }
        //try to send the email
        try {
            this.mailSender.send(mimeMsg);
            log.info("Sent email notification successfully to " + receiver + "!");
        } catch (MailException ex) {
            log.fatal("Could not sent email notification message!", ex);
        }
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverCC() {
        return receiverCC;
    }

    public void setReceiverCC(String receiverCC) {
        this.receiverCC = receiverCC;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }
}