package com.example.com.androidmultimedia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.firebase.client.Firebase;

public class NewNoteActivity extends AppCompatActivity implements LocationListener
{
    private String uri_database = "https://uridatabase.firebaseio.com/";

    ProgressDialog dialog;
    Location loc;
    Button button;
    EditText title;
    EditText desc;
    Firebase mainRef;
    Firebase notesRef;

    public void onStart()
    {
        super.onStart();
        dialog = ProgressDialog.show(this, "LOCATING ...", "Please wait a few seconds ...");
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

        Firebase.setAndroidContext(this);
        mainRef = new Firebase(uri_database);
        notesRef = mainRef.child("Notes");

        button = (Button) this.findViewById(R.id.BTadd);
        title = (EditText) this.findViewById(R.id.ETtitle);
        desc = (EditText) this.findViewById(R.id.ETdesc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Note note = new Note();
                note.setTitle(title.getText().toString());
                note.setDesc(desc.getText().toString());
                note.setLat(loc.getLatitude());
                note.setLng(loc.getLongitude());
                addNoteToFireBase(note);
                goBackToMain();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void addNoteToFireBase(Note note)
    {
        Firebase fnote = notesRef.push();
        fnote.setValue(note);
    }
    public void goBackToMain()
    {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    @Override
    public void onLocationChanged(Location location)
    {
        dialog.dismiss();
        loc = location;
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onProviderDisabled(String provider) {}
}
