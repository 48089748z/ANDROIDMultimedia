package com.example.com.androidmultimedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

public class MainActivity extends AppCompatActivity
{
    private String uri_database = "https://uridatabase.firebaseio.com/";

    ListView list;
    Firebase mainRef;
    Firebase notesRef;

    public void onStart()
    {
        super.onStart();
        list = (ListView) this.findViewById(R.id.LVlist);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.PBprogress);
        list.setEmptyView(progressBar);
        configFirebase();
        FirebaseListAdapter<Note> adapter = new FirebaseListAdapter<Note>(this, Note.class, android.R.layout.two_line_list_item, notesRef)
        {
            @Override
            protected void populateView(View v, Note model, int position)
            {
                super.populateView(v, model, position);
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getTitle().toUpperCase()+"\nLat: "+model.getLat()+"\nLng: "+model.getLng());
                ((TextView)v.findViewById(android.R.id.text2)).setText(model.getDesc());
            }
        };
        list.setAdapter(adapter);
    }
    public void configFirebase()
    {
        Firebase.setAndroidContext(this);
        mainRef = new Firebase(uri_database);
        mainRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        notesRef = mainRef.child("Notes");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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
        if (id == R.id.action_settings) {return true;}
        if (id == R.id.action_newnote)
        {
            Intent newNote = new Intent(this, NewNoteActivity.class);
            startActivity(newNote);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
