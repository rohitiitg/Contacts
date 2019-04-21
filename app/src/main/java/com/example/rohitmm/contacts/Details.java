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

        ImageView imageView = (ImageView) findViewById(R.id.imageView);             // Instatiation of various views in the activity
        TextView text_name = (TextView) findViewById(R.id.text_name);
        TextView text_number = (TextView) findViewById(R.id.text_number);
        TextView text_email = (TextView) findViewById(R.id.text_email);
        Intent intent = getIntent();                                               // getting intent of current activity
        long id = intent.getExtras().getLong("id");                                // getting the phoneid of the clicked contact
        Cursor c = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,                 // creating cursor to read the datils as name, number,photoidand contact id of the contact clicked 
                new String[]{                                                      // creating corresponding string to read the datils as mentioned in above line
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                }, ContactsContract.CommonDataKinds.Phone._ID + "=?", new String[]{Long.toString(id)}, null); //setting constraint to matching phone id of contact

        String contactid = new String();

        if (c != null) {

            if (c.moveToFirst()) {
                String number = c.getString(0);                 //retrieving number of the contact
                String name = c.getString(1);                   // retrieving name of the contact
                int photoId = c.getInt(2);                      // getting photoid of contact
                contactid = c.getString(3);                     // getting contact id of the contact( this is differnt from the phoneid

                text_name.setText("Name       :  "+name);       // viewing name and number in text view
                text_number.setText("Number   :  "+number);

                Bitmap bitmap = queryContactImage(photoId);     // getting the contact image by query contact function
                imageView.setImageBitmap(bitmap);               // setting image to imageview

            }
            c.close();                                          // closing the cursor

        }

        Cursor ce = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,          // creating cursor for reading the email of the contact
                new String[]{                                                                               // crating corresponding string for retrieving email address
                        ContactsContract.CommonDataKinds.Email.DATA
                },ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[] {contactid}, null);    // setting constraint to desired contact id
        if(ce!=null && ce.moveToFirst()){
            String emailAdd = ce.getString(0);                      // getting email id of contact as string
            text_email.setText("Email        :  "+emailAdd);
        }
        ce.close();                                                 // closing the email cursor.


    }

    private Bitmap queryContactImage(int imageDataRow) {                //this fuction is used to retrieve the image as bitmap when photo id is known
        Cursor c = getContentResolver().query(ContactsContract.Data.CONTENT_URI, new String[]{          // crating corresponding cursor to read the image 
                ContactsContract.CommonDataKinds.Photo.PHOTO
        }, ContactsContract.Data._ID + "=?", new String[]{                  // setting constraint to photo id
                Integer.toString(imageDataRow)
        }, null);
        byte[] imageBytes = null;
        if (c != null) {
            if (c.moveToFirst()) {
                imageBytes = c.getBlob(0);                  // getting the blob of the image
            }
            c.close();          // closing the cursor
        }

        if (imageBytes != null) {
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length); // reeturn the image blob in form of bitmap
        } else {
            return null;    // return null if no image found
        }

    }

}
