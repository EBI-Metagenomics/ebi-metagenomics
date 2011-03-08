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
public class EmailContactFormService implements INotificationService {

    private JavaMailSenderImpl mailSender;
    private String to;
    private String cc;
    private String from;
    private String subject;
    private String message;

    public static final Log log = LogFactory.getLog(EmailContactFormService.class);

    public EmailContactFormService(JavaMailSenderImpl mailSender, String to, String cc, String from, String subject, String message) {
        this.mailSender = mailSender;
        this.to = to;
        this.cc = cc;
        this.from = from;
        this.subject = subject;
        this.message = message;
    }

    public void sendNotification(String message) {
        sendNotification(message, null);
    }

    public void sendNotification(String message, Exception exception) {
        //Create a mail message
        MimeMessage mimeMsg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
        try {
            helper.setTo(to);
            helper.setCc(cc);
            helper.setFrom(from);
            helper.setSubject(subject);
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
