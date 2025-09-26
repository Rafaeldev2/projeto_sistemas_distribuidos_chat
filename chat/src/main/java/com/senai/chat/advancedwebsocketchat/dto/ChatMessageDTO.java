package com.senai.chat.advancedwebsocketchat.dto;

import lombok.Data;
import org.springframework.messaging.Message;

import java.time.LocalDateTime;

@Data
public class ChatMessageDTO {
    private String sender;
    private String content;
    private LocalDateTime timestamp;

    public ChatMessageDTO(Message message) {
        this.sender = message.getSender().getUsername();
        this.content = message.getContent();
        this.timestamp = message.getTimestamp();
    }
}
