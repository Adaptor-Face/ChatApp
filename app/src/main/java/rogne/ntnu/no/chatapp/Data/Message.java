package rogne.ntnu.no.chatapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Kristoffer on 2017-10-18.
 */

public class Message implements Serializable{
    Long id;
    String user;
    String text;
    Timestamp version;
    Conversation conversation;

    public Message() {
    }

    public Message(String user, String text) {
        this.user = user;
        this.text = text;
    }

    public Message(Long id, String user, String text, Timestamp version, Conversation conversation) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.version = version;
        this.conversation = conversation;
    }

    protected Message(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        user = in.readString();
        text = in.readString();
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

    public String toMessageString(){
        return user + ": " + text;
    }
}
