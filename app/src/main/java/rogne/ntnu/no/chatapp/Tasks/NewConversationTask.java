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
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import rogne.ntnu.no.chatapp.Activites.MainActivity;
import rogne.ntnu.no.chatapp.Data.Conversation;

/**
 * Created by krist on 2017-10-22.
 */

public class NewConversationTask extends AsyncTask<Conversation, Void, Boolean> {
    public NewConversationTask(OnPostExecute callback) {
        this.callback = callback;
    }

    public interface OnPostExecute{
        void onPostExecute(Boolean result);
    }
    OnPostExecute callback;
    @Override
    protected Boolean doInBackground(Conversation... conversations) {
        if (conversations.length != 1) {
            return false;
        }
        Conversation conv = conversations[0];
        try {
            URL url = new URL(MainActivity.URL + "/addroom?room=" + conv.getId());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            StringBuilder str = new StringBuilder();
            int len;
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Reader br = new InputStreamReader(new BufferedInputStream(con.getInputStream()), "UTF-8");
                char[] cbuff = new char[1024];
                while ((len = br.read(cbuff)) != -1) {
                    str.append(cbuff, 0, len);
                }
                br.close();
            } else {
                Log.e("PostPicture", "ResponseCode: " + con.getResponseCode());
            }
            con.disconnect();
        } catch (ProtocolException e){
            return true;
        } catch (IOException e) {
            Log.e("PostPicture", "doInBackground: ", e);
        }
        return true;
    }
    @Override
    protected void onPostExecute(Boolean result) {
        if(callback != null)
            callback.onPostExecute(result);
    }

}
