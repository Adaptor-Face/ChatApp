package rogne.ntnu.no.chatapp.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kristoffer on 2017-10-18.
 */

public class Conversation implements Serializable{
    String id;
    List<Message> messages = new ArrayList<>();
    Timestamp version = new Timestamp(new Date().getTime());

    public Conversation(String id) {
        this.id = id;
    }

    public Conversation() {
    }

    public Conversation(String... strings) {
        if(strings.length == 0){
            this.id = "";
        } else {
            String idString = "";
            for(String s : strings){
                idString += s + ":";
            }
            idString = idString.substring(0,idString.length()-2) + ".convo";
            this.id = idString;
        }

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

    public Message getLastMessage(){
        if(messages.isEmpty()){
            return null;
        }
        return messages.get(messages.size()-1);
    }

    public String getParticipants(String username) {
        String[] conversationParticipants = getId().split("\\.");
        String[] str = conversationParticipants[0].split(":");
        String finalString = "";
        for(String s : str){
            if(!s.contains(username)) {
                finalString += s + ", ";
            }
        }
        finalString = finalString.trim();
        if(finalString.startsWith(",")){
            finalString = finalString.substring(2);
        }
        if(finalString.endsWith(",")){
            finalString = finalString.substring(0, finalString.length() -1);
        }
        return finalString;
    }

}
