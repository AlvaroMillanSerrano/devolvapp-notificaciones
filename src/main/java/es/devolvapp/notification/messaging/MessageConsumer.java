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

        String collectionId = message.message();;

        switch (message.type()) {
            case "created" -> mailService.sendCollectionCreated(message.email(), collectionId);
            case "canceled" -> mailService.sendCollectionCanceled(message.email(), collectionId);
            case "registered" -> mailService.sendRegistered(message.email());
            case "incident" -> mailService.sendIncident(message.email(), collectionId);
        }
    }
}
