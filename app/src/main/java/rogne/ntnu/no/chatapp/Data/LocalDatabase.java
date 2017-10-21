package rogne.ntnu.no.chatapp.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kristoffer on 2017-10-19.
 */

public class LocalDatabase {
    private static List<Conversation> convos = new ArrayList<>();

    {
        convos.add(new Conversation("Tom:Frank.convo"));
        convos.add(new Conversation("Sandra:Frank.convo"));
        convos.add(new Conversation("Sandra:Tom.convo"));
        convos.add(new Conversation("Sandra:Frank:Tom.convo"));
    }

    private static List<Message> msgs = new ArrayList<>();

    {
        msgs.add(new Message(new Long(0), "Tom", "Hello", new Timestamp(new Date().getTime()), convos.get(0)));
        msgs.add(new Message(new Long(1), "Sandra", "Hello", new Timestamp(new Date().getTime()), convos.get(1)));
        msgs.add(new Message(new Long(2), "Frank", "hi", new Timestamp(new Date().getTime()), convos.get(1)));
        msgs.add(new Message(new Long(3), "Sandra", "Hi", new Timestamp(new Date().getTime()), convos.get(2)));
        msgs.add(new Message(new Long(4), "Sandra", "How are you", new Timestamp(new Date().getTime()), convos.get(2)));
        msgs.add(new Message(new Long(5), "Tom", "IM good", new Timestamp(new Date().getTime()), convos.get(2)));
        msgs.add(new Message(new Long(6), "Frank", "Group chat", new Timestamp(new Date().getTime()), convos.get(3)));
        msgs.add(new Message(new Long(7), "Tom", "k", new Timestamp(new Date().getTime()), convos.get(3)));
        msgs.add(new Message(new Long(8), "Tom", "u there", new Timestamp(new Date().getTime()), convos.get(0)));
        msgs.add(new Message(new Long(9), "Frank", "Hello", new Timestamp(new Date().getTime()), convos.get(0)));
        msgs.add(new Message(new Long(10), "Sandra", "Hello", new Timestamp(new Date().getTime()), convos.get(3)));
        msgs.add(new Message(new Long(11), "Tom", "sup", new Timestamp(new Date().getTime()), convos.get(2)));
    }

    public static Conversation getConversation(String conversationId) {
        return convos.get(convos.indexOf(new Conversation(conversationId)));
    }

    public static List<Conversation> getConvos(String username){
        List<Conversation> myConvos = new ArrayList<>();
        for (Conversation c : convos){
            if(c.getId().contains(username)){
                myConvos.add(c);
            }
        }
        return myConvos;
    }
    public static List<Message> getMessages(Conversation convo) {
        List<Message> result = new ArrayList<>();
        for (Message m : msgs) {
            if(m.getConversation().getId().equals(convo.getId())){
                result.add(m);
            }
        }
        return result;
    }
}
