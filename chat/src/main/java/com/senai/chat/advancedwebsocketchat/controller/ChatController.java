package com.senai.chat.advancedwebsocketchat.controller;

import com.senai.chat.advancedwebsocketchat.dto.ChatMessageDTO;
import com.senai.chat.advancedwebsocketchat.dto.JoinMessageDTO;
import com.senai.chat.advancedwebsocketchat.dto.UserStatusDTO;
import com.senai.chat.advancedwebsocketchat.service.MessageService;
import com.senai.chat.advancedwebsocketchat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private SimpMessagingTemplate messagingTemplate;
    private UserService userService;
    private MessageService messageService;

    @MessageMapping("/chat")
    public void handleChat(@Payload ChatMessageDTO chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        User sender = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message savedMessage = messageService.saveMessage(sender, chatMessage.getContent());

        messagingTemplate.convertAndSend("/topic/messages", new ChatMessageDTO(savedMessage));
    }

    @MessageMapping("/join")
    public void handleJoin(@Payload JoinMessageDTO joinMessage, SimpMessageHeaderAccessor headerAccessor) {
        User user = userService.findByUsername(joinMessage.getUsername())
                .orElseGet(() -> userService.createUser(joinMessage.getUsername(), joinMessage.getPassword()));

        headerAccessor.getSessionAttributes().put("username", user.getUsername());
        userService.updateLastSeen(user);

        messagingTemplate.convertAndSend("/topic/users", new UserStatusDTO(user.getUsername(), "ONLINE"));
    }
}
