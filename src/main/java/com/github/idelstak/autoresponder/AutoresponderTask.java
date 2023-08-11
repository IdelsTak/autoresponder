package com.github.idelstak.autoresponder;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Objects;
import java.util.Properties;
import java.util.TimerTask;

public class AutoresponderTask extends TimerTask {
    private final String myEmail;
    private final String password;
    private final Store store;

    public AutoresponderTask(Store store, String myEmail, String password) {
        this.store = store;
        this.myEmail = myEmail;
        this.password = password;
    }

    @Override
    public void run() {
        try {
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                if (shouldAutoReply(message)) {
                    String senderEmail = ((InternetAddress) message.getFrom()[0]).getAddress();
                    sendAutoReply(senderEmail);
                }
            }

            inbox.close(false);
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle email retrieval errors
        }
    }

    private void sendAutoReply(String senderEmail) throws MessagingException {
        System.out.println("sending email to " + senderEmail);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });


        String autoReplyMessage = "Thank you for your email!";
        Message replyMessage = new MimeMessage(session);
        replyMessage.setFrom(new InternetAddress(myEmail));
        replyMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(senderEmail));
        replyMessage.setSubject("Re: " + replyMessage.getSubject());
        replyMessage.setText(autoReplyMessage);

        Transport.send(replyMessage);
    }

    private boolean shouldAutoReply(Message message) throws MessagingException {
        String email = ((InternetAddress) message.getFrom()[0]).getAddress();
        return Objects.equals("contact.kamau@gmail.com", email);
    }
}
