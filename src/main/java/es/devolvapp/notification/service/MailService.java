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

    public void sendCollectionCreated(String to, String pedidoId) {
        sendEmail(to, "Pedido " + pedidoId + " creado",
                "<h2>Pedido creado</h2><p>Tu pedido <strong>" + pedidoId + "</strong> ha sido creado correctamente.</p>");
    }

    public void sendCollectionCanceled(String to, String pedidoId) {
        sendEmail(to, "Pedido " + pedidoId + " cancelado",
                "<h2>Pedido cancelado</h2><p>Tu pedido <strong>" + pedidoId + "</strong> ha sido cancelado.</p>");
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
