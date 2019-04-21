package com.example.rohitmm.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {                   
    private int Read_Contact_Permission = 1;
    ListView l1;
    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS},Read_Contact_Permission);       // Requesting the permission to read contacts
        l1 = (ListView) findViewById(R.id.list_contacts);

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME); // setting cursor to read name, phoneid and and number of contact 
        startManagingCursor(cursor);

        String[] from = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER , ContactsContract.CommonDataKinds.Phone._ID};   // Creating required string for the adapter
        int [] to = {android.R.id.text1,android.R.id.text2};            // making corresponding int array for listview

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,from,to);         // setting the adapter for listview
        l1.setAdapter(simpleCursorAdapter);                                                                                                 // setting adapter in listview
        l1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);    // setting appropriate layout of listview

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {                   // setting reaction when clicked on an item
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),Details.class);               // getting intent of Details class

                i.putExtra("id",id);                                            //action is to start new activity which shows details of contact
                startActivity(i);
            }
        });

    }





}
