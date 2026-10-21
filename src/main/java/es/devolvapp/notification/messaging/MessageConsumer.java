package es.devolvapp.notification.messaging;

import es.devolvapp.notification.service.MailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import es.devolvapp.notification.config.RabbitConfig;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);
    private final MailService mailService;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receive(DevolvMessage message) {
        log.info("Mensaje recibido: {}", message);

        String msg = message.message();
        String email = extractEmail(msg);
        String pedidoId = extractCollectionId(msg);

        if (msg.contains("creado")) {
            mailService.sendCollectionCreated(email, pedidoId);
        } else if (msg.contains("eliminado")) {
            mailService.sendCollectionCanceled(email, pedidoId);
        } else {
            log.warn("Tipo de mensaje desconocido: {}", msg);
        }
    }

    private String extractEmail(String msg) {
        return msg.substring(msg.indexOf("Email: ") + 7).trim();
    }

    private String extractCollectionId(String msg) {
        int start = msg.indexOf("Pedido ") + 7;
        int end = msg.indexOf(' ', start);
        return msg.substring(start, end);
    }
}
