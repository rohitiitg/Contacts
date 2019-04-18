package com.example.rohitmm.contacts;


import android.content.ContentUris;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Details extends AppCompatActivity {
    String phones = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView text_name = (TextView) findViewById(R.id.text_name);
        TextView text_number = (TextView) findViewById(R.id.text_number);
        TextView text_email = (TextView) findViewById(R.id.text_email);
        Intent intent = getIntent();
        long id = intent.getExtras().getLong("id");
        Cursor c = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                }, ContactsContract.CommonDataKinds.Phone._ID + "=?", new String[]{Long.toString(id)}, null);

        String contactid = new String();

        if (c != null) {

            if (c.moveToFirst()) {
                String number = c.getString(0);
                String name = c.getString(1);
                int photoId = c.getInt(2);
                contactid = c.getString(3);

                text_name.setText("Name       :  "+name);
                text_number.setText("Number   :  "+number);

                Bitmap bitmap = queryContactImage(photoId);
                imageView.setImageBitmap(bitmap);

            }
            c.close();

        }

        Cursor ce = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                new String[]{
                        ContactsContract.CommonDataKinds.Email.DATA
                },ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[] {contactid}, null);
        if(ce!=null && ce.moveToFirst()){
            String emailAdd = ce.getString(0);
            text_email.setText("Email        :  "+emailAdd);
        }
        ce.close();


    }

    private Bitmap queryContactImage(int imageDataRow) {
        Cursor c = getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{
                ContactsContract.CommonDataKinds.Photo.PHOTO
        }, ContactsContract.Data._ID + "=?", new String[]{
                Integer.toString(imageDataRow)
        }, null);
        byte[] imageBytes = null;
        if (c != null) {
            if (c.moveToFirst()) {
                imageBytes = c.getBlob(0);
            }
            c.close();
        }

        if (imageBytes != null) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            return null;
        }

    }

}