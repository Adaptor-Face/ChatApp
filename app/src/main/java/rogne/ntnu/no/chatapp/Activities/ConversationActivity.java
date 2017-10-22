package rogne.ntnu.no.chatapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import rogne.ntnu.no.chatapp.Adapters.MessageAdapter;
import rogne.ntnu.no.chatapp.Data.Conversation;
import rogne.ntnu.no.chatapp.Data.Message;
import rogne.ntnu.no.chatapp.R;
import rogne.ntnu.no.chatapp.Tasks.PostMessageTask;

public class ConversationActivity extends AppCompatActivity {
    MessageAdapter adapter;
    Conversation conversation;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Intent intent = getIntent();
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.conversation_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MessageAdapter(this);
        conversation = (Conversation) intent.getSerializableExtra(MainActivity.CONVERSATION_ID);
        adapter.setMessages(conversation.getMessages());
        ab.setTitle(conversation.getParticipants(getUsername()));
        recyclerView.setAdapter(adapter);
        updateView();
        adapter.setListener(v -> Snackbar.make(recyclerView, "" + adapter.getMessages().get(v).getId(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private String getUsername() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        String username = sp.getString("username", "admin");
        Log.d("getUsername", "Loaded data: username = " + username);
        return username;
    }

    public void onSendNewMessage(View view) {
        EditText input = (EditText) findViewById(R.id.conversation_new_message);
        String message = input.getText().toString();
        Message msg = new Message(MainActivity.USERNAME, message, conversation);
        new PostMessageTask(r -> {
            if (r) {
                input.setText("");
                adapter.addMessage(msg);
            }
        }).execute(msg);
        updateView();
    }

    public void updateView() {
        recyclerView.post(() -> recyclerView.smoothScrollToPosition(adapter.getItemCount()));
    }
}
