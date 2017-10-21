package rogne.ntnu.no.chatapp.Loaders;

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

/**
 * Created by Kristoffer on 2017-10-18.
 */

public class LoadConversations extends AsyncTask<String, Integer, List<Conversation>> {
    public LoadConversations(OnPostExecute callback) {
        this.callback = callback;
    }

    public interface OnPostExecute{
        void onPostExecute(List<Conversation> convos);
    }
    OnPostExecute callback;
    @Override
    protected List<Conversation> doInBackground(String... strings) {
        if(strings.length <1){
            return Collections.emptyList();
        }
        List<Conversation> result = LocalDatabase.getConvos();
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
    protected void onPostExecute(List<Conversation> convos) {
        if(callback != null)
            callback.onPostExecute(convos);
    }
}
