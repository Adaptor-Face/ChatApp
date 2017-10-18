package rogne.ntnu.no.chatapp;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Kristoffer on 2017-10-18.
 */

public class Conversation {
    String id;
    List<Message> messages;
    Timestamp version;

    public Conversation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }
}
