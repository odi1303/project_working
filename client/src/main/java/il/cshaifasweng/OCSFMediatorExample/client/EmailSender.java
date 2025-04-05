package il.cshaifasweng.OCSFMediatorExample.client;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.Properties;

public class EmailSender {
    final String senderEmail = "odifn567@gmail.com";  // Replace with your email
    final String senderPassword = "xeco tdac fztn nuva";  // Replace with your app password

    void send_email_respond(String recipient,String subject, String respond_text) {
        if (respond_text.isEmpty()) {
            System.out.println("Response text is empty. Email not sent.");
            return;
        }

        sendEmail(recipient, subject, respond_text);
    }
    private void sendEmail(String recipient, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}