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

        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS},Read_Contact_Permission);
        l1 = (ListView) findViewById(R.id.list_contacts);

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        startManagingCursor(cursor);

        String[] from = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER , ContactsContract.CommonDataKinds.Phone._ID};
        int [] to = {android.R.id.text1,android.R.id.text2};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,from,to);
        l1.setAdapter(simpleCursorAdapter);
        l1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),Details.class);

                i.putExtra("id",id);                                            //action is to start new activity which shows full view of the image.
                startActivity(i);
            }
        });

    }





}