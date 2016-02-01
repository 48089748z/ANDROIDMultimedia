package com.example.com.androidmultimedia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Firebase mainRef;
    Firebase usersRef;
    private String uri_database = "https://uridatabase.firebaseio.com/";
    private String sergi_database = "https://helicidatest.firebaseio.com";

    public void configFirebase()
    {
        Firebase.setAndroidContext(this);
        mainRef = new Firebase(uri_database);
        usersRef = mainRef.child("Users");
    }

    public void writeOnFirebase()
    {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("uRi Cuñado", 1994));
        users.add(new User("Alejandro Soriano",1993));
        users.add(new User("Sergi Barjola", 1995));


        for (int x=0; x<users.size(); x++)
        {
            Firebase user = usersRef.push();
            user.setValue(users.get(x));
        }

    }
    public void readFromFirebase()
    {
        mainRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
                firebaseError.getMessage();
            }
        });
    }

    public void populateListView()
    {
        ListView list = (ListView) this.findViewById(R.id.LVlist);

        mainRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("XXXXX", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        FirebaseListAdapter<User> adapter = new FirebaseListAdapter<User>(this, User.class, android.R.layout.two_line_list_item, usersRef)
        {
            @Override
            protected void populateView(View v, User model, int position)
            {
                super.populateView(v, model, position);
                ((TextView)v.findViewById(android.R.id.text1)).setText(model.getFullName());
                ((TextView)v.findViewById(android.R.id.text2)).setText(String.valueOf(model.getBirthYear()));
            }
        };
        list.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       configFirebase();
       writeOnFirebase();
       //readFromFirebase();
       populateListView();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
