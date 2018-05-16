package com.kidd.store.models.model_chat;

import java.io.Serializable;



public class UserMessage implements Serializable {
    private String id;
    private Message message;

    public UserMessage() {
    }

    public UserMessage(String id, Message message) {
        this.id = id;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
