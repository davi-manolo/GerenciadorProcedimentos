package utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class Email {

    private String[] recipientEmails;
    private String subject;
    private String mesage;
    private static EmailAttachment attachment;
    private PropertiesManager props = new PropertiesManager("emailProvider.properties");
    
    public Email(String[] recipientEmails, String subject, String mesage) {
        this.recipientEmails = recipientEmails;
        this.subject = subject;
        this.mesage = mesage;
    }

    public void send() {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(props.getValue("email.setting.hostname"));
        try {
            email.addTo(recipientEmails);
            email.setFrom(props.getValue("email.sender"));
            email.setSubject(subject);
            email.setMsg(mesage);
            email.setSmtpPort(Integer.valueOf(props.getValue("email.setting.port")));
            email.setAuthenticator(new DefaultAuthenticator(props.getValue("email.sender"),
                    props.getValue("email.password")));
            email.setSSLOnConnect(Boolean.valueOf(props.getValue("email.setting.ssl")));
            if (attachment != null) {
                email.attach(attachment);
            }
            email.send();
        } catch (EmailException e) {
            System.out.println("Erro ao enviar email: " + e.getMessage());
        }
    }

    public static void attachFile(String path, String name) {
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(path);
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Arquivo de procedimentos.");
        attachment.setName(name);
        Email.attachment = attachment;
    }

}
