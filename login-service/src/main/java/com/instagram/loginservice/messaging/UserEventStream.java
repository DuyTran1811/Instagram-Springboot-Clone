package com.instagram.loginservice.messaging;

import org.springframework.cloud.stream.annotation.StreamRetryTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface UserEventStream {
    String OUTPUT = "momentsUserChanged";
    @StreamRetryTemplate
    MessageChannel momentsUserChanged();
}
