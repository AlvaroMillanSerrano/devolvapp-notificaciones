package es.devolvapp.notification.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.devolvapp.notification.messaging.DevolvMessage;
import es.devolvapp.notification.messaging.MessageProducer;

@RestController
@RequestMapping("/messages")
public class MessageController {
    public record SendRequest(String content) {
    }

    private final MessageProducer producer;

    public MessageController(MessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public DevolvMessage send(@RequestBody SendRequest request) {
        return producer.send(request.content());
    }
}
