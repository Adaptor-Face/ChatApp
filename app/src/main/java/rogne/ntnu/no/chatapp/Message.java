package rogne.ntnu.no.chatapp;

import java.sql.Timestamp;

/**
 * Created by Kristoffer on 2017-10-18.
 */

public class Message {
    Long id;
    String user;
    String text;
    Timestamp version;
    Conversation conversation;

    public Message(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
