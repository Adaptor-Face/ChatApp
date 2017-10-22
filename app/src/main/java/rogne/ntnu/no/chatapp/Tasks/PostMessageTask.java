package rogne.ntnu.no.chatapp.Tasks;

import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import rogne.ntnu.no.chatapp.Activites.MainActivity;
import rogne.ntnu.no.chatapp.Data.Message;

/**
 * Created by krist on 2017-10-22.
 */

public class PostMessageTask extends AsyncTask<Message, Void, Boolean> {

    public PostMessageTask(OnPostExecute callback) {
        this.callback = callback;
    }

    public interface OnPostExecute {
        void onPostExecute(Boolean result);
    }

    OnPostExecute callback;

    @Override
    protected Boolean doInBackground(Message... messages) {
        if (messages.length != 1) {
            return false;
        }
        Message m = messages[0];
        try {
            URL url = new URL(MainActivity.URL + "/add?name=" + m.getConversation().getId());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
            JsonWriter writer = new JsonWriter(out);
            writer.beginObject().name("user").value(m.getUser()).name("text").value(m.getText()).endObject().close();
            con.disconnect();
        } catch (IOException e) {
            Log.e("PostPicture", "doInBackground: ", e);
        }
        return true;
    }
}
