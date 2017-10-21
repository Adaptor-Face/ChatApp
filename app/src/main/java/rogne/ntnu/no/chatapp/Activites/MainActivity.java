package rogne.ntnu.no.chatapp.Activites;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import rogne.ntnu.no.chatapp.Adapters.ConversationAdapter;
import rogne.ntnu.no.chatapp.Loaders.LoadConversations;
import rogne.ntnu.no.chatapp.Data.LocalDatabase;
import rogne.ntnu.no.chatapp.R;

public class MainActivity extends AppCompatActivity {
    private String[] drawer_options;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private LocalDatabase ld = new LocalDatabase();


    ConversationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_with_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "" + view.getId() + "  " + R.id.fab, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        });

        drawer_options = getResources().getStringArray(R.array.drawer_option_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawer_options));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        RecyclerView rv = (RecyclerView) findViewById(R.id.conversations);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new ConversationAdapter(this);
        new LoadConversations(c -> adapter.setConvos(c)).execute("asd");
        //TODO: Make own divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                ((LinearLayoutManager)rv.getLayoutManager()).getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        adapter.setListener(v->  Snackbar.make(findViewById(R.id.fab), "" + adapter.getConvos().get(v).getId(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        rv.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    private void selectItem(int position) {
        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(drawer_options[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
    }
}