package rogne.ntnu.no.chatapp.Tasks;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rogne.ntnu.no.chatapp.Activities.MainActivity;
import rogne.ntnu.no.chatapp.Data.Conversation;
import rogne.ntnu.no.chatapp.Data.LocalDatabase;
import rogne.ntnu.no.chatapp.Data.Message;

/**
 * Created by Kristoffer on 2017-10-18.
 */

public class LoadConversationsTask extends AsyncTask<String, Integer, List<Conversation>> {
    public LoadConversationsTask(OnPostExecute callback) {
        this.callback = callback;
    }

    public interface OnPostExecute{
        void onPostExecute(List<Conversation> conversations);
    }
    OnPostExecute callback;
    @Override
    protected List<Conversation> doInBackground(String... strings) {
        if(strings.length <1){
            return Collections.emptyList();
        }
        List<Conversation> conversations = fetchConversationsFromServer(strings[0]);
        for(Conversation c : conversations){
            c.setMessages(fetchMessagesFromServer(c, strings[0]));
        }
        return conversations;
    }

    private List<Message> fetchMessagesFromServer(Conversation c, String urlString) {

        //TODO: check if actually works
        List<Message> result = new ArrayList<>();
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL(urlString + "?name=" + c.getId()).openConnection();
            JsonReader reader = new JsonReader(new InputStreamReader(con.getInputStream()));
            reader.beginArray();
            while (reader.hasNext()) {
                String user = null;
                String text = null;
                reader.beginObject();
                while (reader.hasNext()) {
                    switch (reader.nextName()) {
                        case "user":
                            user = reader.nextString();
                            break;
                        case "text":
                            text = reader.nextString();
                            break;
                        default:
                            reader.skipValue();
                    }
                }
                reader.endObject();
                result.add(new Message(user,text));
            }
            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Conversation> fetchConversationsFromServer(String urlString) {
        List<Conversation> result = new ArrayList<>();
        HttpURLConnection con;
        urlString +=  "/conversation";
        try {
            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            JsonReader reader = new JsonReader(new InputStreamReader(con.getInputStream()));
            reader.beginArray();
            while (reader.hasNext()) {
                String id = null;
                reader.beginObject();
                while (reader.hasNext()) {
                    switch (reader.nextName()) {
                        case "id":
                            id = reader.nextString();
                            break;
                        default:
                            reader.skipValue();
                    }
                }
                reader.endObject();
                if(id.contains(MainActivity.USERNAME) && id.contains(".convo")) {
                    result.add(new Conversation(id));
                }
            }
            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //TODO: replace tester function (doInBackground) with (doInBackgroundActual)
    protected List<Conversation> doInBackgroundActual(URL... strings) {
        if (strings.length < 1) {
            return Collections.emptyList();
        }
        List<Conversation> result = new ArrayList<>();
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) strings[0].openConnection();
            JsonReader reader = new JsonReader(new InputStreamReader(con.getInputStream()));
            reader.beginArray();
            while (reader.hasNext()) {
                String id = null;
                reader.beginObject();
                while (reader.hasNext()) {
                    switch (reader.nextName()) {
                        case "id":
                            id = reader.nextString();
                            break;
                        default:
                            reader.skipValue();
                    }
                }
                reader.endObject();
                result.add(new Conversation(id));
            }
            reader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    protected void onPostExecute(List<Conversation> conversations) {
        if(callback != null)
            callback.onPostExecute(conversations);
    }

    private List<Conversation> fetchConversations(String user){
        return LocalDatabase.getConvos(user);
    }
    private List<Message> fetchMessages(Conversation conversation){
        return LocalDatabase.getMessages(conversation);
    }
}
