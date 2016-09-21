package com.example.varunrv.databaseexample;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelp extends SQLiteOpenHelper {
    static final String databaseName = "location.db";
    static final String tableName = "Location_Details";
    static final String tableName2 = "Location_Info";

    static final String address = "_adresss";
    static final String id="_ID";
    static final String lat = "Latitude";
    static final String lon = "Longitude";

    static final String createQ = "CREATE TABLE " + tableName + "(" + address + " VARCHAR(25), " + lat + " NUMBER, " + lon + " NUMBER);";
    static final String createQ2 = "CREATE TABLE " + tableName + "( " +id+ "INTEGER PRIMARY KEY AUTOINCREMENT, " + address + " VARCHAR(25), " + lat + " NUMBER, " + lon + " NUMBER);";

    static final String delete= "DROP TABLE IF EXISTS"+tableName;
    private static final int DATABASE_VERSION = 33;

    public DBHelp(Context context) {
        super(context, databaseName, null, DATABASE_VERSION);
       //SQLiteDatabase db = this.getWritableDatabase();
        Log.d("val", "inside dbhelp");

        //this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createQ);
        Log.d("val", "created" + db.isOpen());
      // db.execSQL("INSERT INTO " + tableName + " VALUES('bangalore','44','-122');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           }

    public long insert(String address, String lat, String lon){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues c= new ContentValues();
        c.put(this.address,address);
        c.put(this.lat,lat);
        c.put(this.lon,lon);
        long id= db.insert(this.tableName,null,c);
        return id;
    }
    public Cursor getdata()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from "+tableName,null);
        return res;

    }


}
