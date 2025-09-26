package com.senai.chat.websocketchat;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

public interface WebSocketInterface {
    public void configureMessageBroker(MessageBrokerRegistry config);

    public void registerStompEndpoints(StompEndpointRegistry registry);
}
