package rogne.ntnu.no.chatapp;

import android.os.AsyncTask;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kristoffer on 2017-10-18.
 */

public class LoadConversations extends AsyncTask<URL, Integer, List<Conversation>> {
    @Override
    protected List<Conversation> doInBackground(URL... urls) {
        if(urls.length <1){
            return Collections.emptyList();
        }
        List<Conversation> result = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) urls[0].openConnection();
            JsonReader reader = new JsonReader(new InputStreamReader(con.getInputStream()));
            reader.beginArray();
            while(reader.hasNext()){
                String id = null;
                reader.beginObject();
                while(reader.hasNext()){
                    switch(reader.nextName()){
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
}
