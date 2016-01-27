package com.example.com.androidmultimedia;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Firebase ref;
    private String uri_database = "https://uridatabase.firebaseio.com/";
    private String sergi_database = "https://helicidatest.firebaseio.com";
    private TextView information;

    public void readFromFirebase()
    {
        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot snapshot)
            {
                information.setText(snapshot.getValue().toString());
            }
            @Override
            public void onCancelled(FirebaseError firebaseError)
            {
                information.setText("The read failed: " + firebaseError.getMessage());
            }
        });
    }
    public void writeOnFirebase()
    {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("uRi Cuñado", 1994));
        users.add(new User("Alejandro Soriano",1993));
        users.add(new User("Sergi Barjola", 1995));

        Firebase usersRef = ref.child("Users");

        for (int x=0; x<users.size(); x++)
        {
            Firebase user = usersRef.push();
            user.setValue(users.get(x));
        }

    }
    public void configFirebase()
    {
        Firebase.setAndroidContext(this);
        ref = new Firebase(uri_database);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        information = (TextView) findViewById(R.id.TVusers);

        configFirebase();
        writeOnFirebase();
        readFromFirebase();
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
}
