package utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class Email {

    private String sender;
    private String[] recipientEmails;
    private String subject;
    private String mesage;
    private EmailAttachment attachment;

    public Email(String sender, String[] recipientEmails, String subject, String mesage) {
        this.sender = sender;
        this.recipientEmails = recipientEmails;
        this.subject = subject;
        this.mesage = mesage;
    }

    public void send() {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
        for (String recipientEmail : recipientEmails) {
            try {
                email.addTo(recipientEmail); //destinatario
                email.setFrom(sender); //remetente
                email.setSubject(subject); //Assunto
                email.setMsg(mesage); //conteudo do e-mail
                email.setSmtpPort(465);
                email.setAuthenticator(new DefaultAuthenticator("davi.manolo@gmail.com", "Manolo1993%"));
                email.setSSLOnConnect(true);
                if (attachment != null) {
                    email.attach(attachment); // adiciona o anexo à mensagem
                }
                email.send();
            } catch (EmailException e) {
                System.out.println("Erro ao enviar email: " + e.getMessage());
            }
        }
    }

    public void attachFile(String path, String name) {
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(path); //caminho da imagem mypictures/john.jpg
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Arquivo de procedimentos.");
        attachment.setName(name);
        this.attachment = attachment;
    }

}
