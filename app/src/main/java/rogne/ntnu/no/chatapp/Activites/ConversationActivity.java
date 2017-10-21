package rogne.ntnu.no.chatapp.Activites;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import rogne.ntnu.no.chatapp.Adapters.MessageAdapter;
import rogne.ntnu.no.chatapp.Loaders.LoadMessages;
import rogne.ntnu.no.chatapp.R;

public class ConversationActivity extends AppCompatActivity {
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Intent intent = getIntent();

        RecyclerView rv = (RecyclerView) findViewById(R.id.conversation_view);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MessageAdapter(this);

        new LoadMessages(m -> adapter.setMessages(m)).execute(intent.getStringExtra(MainActivity.CONVERSATION_ID));
        rv.setAdapter(adapter);
        adapter.setListener(v->  Snackbar.make(rv, "" + adapter.getMessages().get(v).getId(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
}
