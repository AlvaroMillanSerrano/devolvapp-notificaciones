package es.devolvapp.notification.service;

import es.devolvapp.notification.messaging.MessageConsumer;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    private String from = "no-reply@devolvup.com";

    public void sendCollectionCreated(String email, String collectionId) {
        sendEmail(email, "Pedido " + collectionId + " creado",
                "<h2>Pedido creado</h2><p>Tu pedido <strong>" + collectionId + "</strong> ha sido creado correctamente.</p>");
    }

    public void sendCollectionCanceled(String email, String collectionId) {
        sendEmail(email, "Pedido " + collectionId + " cancelado",
                "<h2>Pedido cancelado</h2><p>Tu pedido <strong>" + collectionId + "</strong> ha sido cancelado.</p>");
    }

    public void sendRegistered(String email) {
        sendEmail(email, "Registro",
                "<h2>Registrado</h2><p>Se ha registra en devolvUp correctamente.</p>");
    }

    public void sendIncident(String email, String collectionId) {
        sendEmail(email, "Incidencia",
                "<h2>Incidencia</h2><p>Ha habido una incidencia en el pedido "+ collectionId +"</p>");
    }

    private void sendEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("Error email {}: {}", to, e.getMessage(), e);
        }
    }

}
