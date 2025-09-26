package com.senai.chat.advancedwebsocketchat.service;

import com.senai.chat.advancedwebsocketchat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Message saveMessage(User sender, String content) {
        Message message = new Message(sender, content) {

            @Override
            public Object getPayload() {
                return null;
            }

            @Override
            public MessageHeaders getHeaders() {
                return null;
            }
        };
        return messageRepository.save(message);
    }
}
