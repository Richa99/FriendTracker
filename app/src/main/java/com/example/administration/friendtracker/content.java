package com.example.administration.friendtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public  class content extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String LocationTable = "LocationTable";

    // Contacts Table Columns names

    private static final String placename = "Placename";
    private static final String latitude = "Latitude";
    private static final String longitude= "Longitude";
    private static final String KEY_ID = "KEY_ID";


    public content(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        /*String CREATE_Location_table = "CREATE TABLE " + LocationTable + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + latitude +"FLOAT"
                + longitude + " FLOAT" + ")";

        db.execSQL(CREATE_Location_table);*/
        db.execSQL("create table " + LocationTable+ "(placename TEXT, Latitude FLOAT,Longitude FLOAT,KEY_ID TEXT PRIMARY KEY)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +LocationTable);

        // Create tables again
        onCreate(db);
    }
    boolean addLocation(String plname,String lati,String longi,String kid)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(placename,plname);
        values.put(latitude,lati);
        values.put(longitude,longi);
        values.put(KEY_ID,kid);
        

        // Inserting Row
       long res= db.insert(LocationTable, null, values);
       if(res==-1)
           return false;

       else
           return true;
      // db.close();

    }
    public Integer deleteContact(String i1) {
        SQLiteDatabase db = this.getWritableDatabase();
       return  db.delete(LocationTable, KEY_ID + " = ?",
                new String[] { String.valueOf(i1) });

    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + LocationTable;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        return cursor.getCount();
    }

    /*public List<ShareLocation> getAllContacts() {
        List<ShareLocation> LocationList = new ArrayList<ShareLocation>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + LocationTable;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ShareLocation contact;
                contact = new ShareLocation();
                contact.setplacename(cursor.getString(0));
                contact.setLat(cursor.getString(1));
                contact.setlog(cursor.getString(2));
                contact.setKEY_ID(cursor.getString(3));
                // Adding contact to list
                LocationList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return LocationList;
    }
*/
    public Cursor  getAllLocation()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from " + LocationTable,null);
        return res;
    }

}


