package com.senai.chat.advancedwebsocketchat.dto;

public class UserStatusDTO {
    private String username;
    private String status;

    public UserStatusDTO(String username, String status) {
        this.username = username;
        this.status = status;
    }
}
