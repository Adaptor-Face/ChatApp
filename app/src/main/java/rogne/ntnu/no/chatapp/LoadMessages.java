package rogne.ntnu.no.chatapp;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by krist on 2017-10-18.
 */

public class LoadMessages extends AsyncTask<String, Integer, List<Message>> {
    @Override
    protected List<Message> doInBackground(String... strings) {
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
}
