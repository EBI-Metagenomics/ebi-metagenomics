package uk.ac.ebi.interpro.metagenomics.memi.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.PrintWriter;
import java.io.StringWriter;

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

    private String[] receiverCCList;

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
            if (receiverCCList != null) {
                helper.setCc(receiverCCList);
            }
            if (sender != null) {
                helper.setFrom(sender);
            }
            if (emailSubject != null) {
                helper.setSubject(emailSubject);
            }
            if (exception != null) {
                helper.setText(buildPlainText(message, exception));
            } else {
                helper.setText(message, true);
            }

        } catch (MessagingException e) {
            log.error("Could not build the message using the MimeMessageHelper!", e);
        }
        //try to send the email
        try {
//            Properties p = new Properties();
//            p.put("mail.mime.charset","UTF-8");
//            p.put("mail.mime.content","text/html");
//            this.mailSender.setJavaMailProperties(p);
            this.mailSender.send(mimeMsg);
            log.info("Sent email notification successfully to " + receiver + "!");
        } catch (MailException ex) {
            log.fatal("Could not sent email notification message!", ex);
            log.fatal("Email message content:\n" + mimeMsg);
        }
    }

    //TODO: write JUnit test
    //TODO: Implementation not finished
    private String buildPlainText(String message, Exception e) {
        StringBuffer result = new StringBuffer();
        if (message != null) {
            result.append(message + "\n");
        }
        if (e.toString() != null) {
            result.append(e.toString() + "\n");
        }
        if (e.getMessage() != null) {
            result.append("Message:" + "\n");
            result.append(e.getMessage() + "\n");
        }
        String stackTrace = getStackTrace(e);
        if (stackTrace != null) {
            result.append("Stack trace:" + "\n");
            result.append(stackTrace);
        }

        return result.toString();
    }

    private static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
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

    public String[] getReceiverCCList() {
        return receiverCCList;
    }

    public void setReceiverCCList(String[] receiverCCList) {
        this.receiverCCList = receiverCCList;
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