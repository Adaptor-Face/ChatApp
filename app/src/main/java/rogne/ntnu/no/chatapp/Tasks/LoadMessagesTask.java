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

import rogne.ntnu.no.chatapp.Data.Conversation;
import rogne.ntnu.no.chatapp.Data.LocalDatabase;
import rogne.ntnu.no.chatapp.Data.Message;

/**
 * Created by krist on 2017-10-18.
 */

public class LoadMessagesTask extends AsyncTask<Conversation, Integer, List<Message>> {
    public LoadMessagesTask(OnPostExecute callback) {
        this.callback = callback;
    }

    public interface OnPostExecute{
        void onPostExecute(List<Message> messages);
    }
    OnPostExecute callback;
    @Override
    protected List<Message> doInBackground(Conversation... conversations) {
        if(conversations.length<1){
            return Collections.EMPTY_LIST;
        }
        List<Message> r = new ArrayList<>();
        for(int i = 0; i < conversations.length; i++) {
           r.addAll(LocalDatabase.getMessages(conversations[i]));
        }
        return r;
    }
    //TODO: Repalce local doInBacground with doInBackgroundActual
    protected List<Message> doInBackgroundActual(String... strings) {
        if(strings.length < 2){
            return Collections.emptyList();
        }
        //TODO: check if actually works
        String urlString = strings[0];
        String conversationId = strings[1];
        List<Message> result = new ArrayList<>();
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL(urlString + "?name=" + conversationId).openConnection();
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
    @Override
    protected void onPostExecute(List<Message> messages) {
        if(callback != null)
            callback.onPostExecute(messages);
    }

}
