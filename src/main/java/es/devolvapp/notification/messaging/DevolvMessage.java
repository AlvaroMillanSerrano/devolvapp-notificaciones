package es.devolvapp.notification.messaging;

import java.time.Instant;

public record DevolvMessage(String message, Instant sentAt) {
}
