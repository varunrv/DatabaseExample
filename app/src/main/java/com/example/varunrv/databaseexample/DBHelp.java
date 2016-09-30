package com.example.varunrv.databaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelp extends SQLiteOpenHelper {

    static final String databaseName = "loC.db";
    static final String tableName = "Location_Details";
    static final String tableName2="Temporary";
    static final String address = "_adresss";
    static final String lat = "Latitude";
    static final String lon = "Longitude";
    static final String shortName = "ShortName";

    static final String createQ = "CREATE TABLE " + tableName + "(" + address + " VARCHAR(25) PRIMARY KEY, " + lat + " NUMBER, " + lon + " NUMBER);";
    static final String createQT = "CREATE TABLE " + tableName2 + "(" + address + " VARCHAR(25) PRIMARY KEY, " + lat + " NUMBER, " + lon + " NUMBER);";

    static final String alterQ="ALTER TABLE "+tableName+" add column " +shortName+" VARCHAR(15);";
    static final String alterQRename="ALTER TABLE "+tableName2+" rename to "+tableName+" ;";
    static final String deleteQ="DROP table "+tableName;

    private static final int DATABASE_VERSION = 4;

    public DBHelp(Context context) {
        super(context, databaseName, null, DATABASE_VERSION);

        Log.d("val", "inside constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL(createQ);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion){
            case 2 : alterTable( db);
                   break;
            case 3: copyValues(db,oldVersion);
                    break;
            case 4: deleteColumn(db,oldVersion);
        }
        Log.d("upgrade fun","entered");

    }
    public void alterTable(SQLiteDatabase db)
    {
        db.execSQL(alterQ);
    }
    public void copyValues(SQLiteDatabase db,int oldVersion){

        if(oldVersion==1){
            alterTable(db);
        }
        Cursor res = db.rawQuery("Select * from " + tableName, null);
        int i= res.getCount();
        res.moveToFirst();
        do{
            ContentValues c = new ContentValues();
            c.put(this.address,"Place"+i+"");
            c.put(this.lat, res.getString(1));
            c.put(this.lon, res.getString(2));
            c.put(this.shortName, "Place"+i+"");
            i--;
            long id = db.insert(this.tableName, null, c);
        } while(res.moveToNext());
    }

    public void deleteColumn(SQLiteDatabase db,int oldVersion){
        if(oldVersion==1 || oldVersion==2){
            copyValues(db,oldVersion);
        }
        db.execSQL(createQT);
        Cursor res= db.rawQuery("Select * from " + tableName, null);
        int count= res.getCount();
        res.moveToFirst();
        while(count!=0){
            String address=res.getString(0);
            String lat=res.getString(1);
            String lon=res.getString(2);
            ContentValues c = new ContentValues();
            c.put(this.address, address);
            c.put(this.lat, lat);
            c.put(this.lon, lon);
            long id = db.insert(this.tableName2, null, c);
            res.moveToNext();
            count--;
        }
        db.execSQL(deleteQ);
        db.execSQL(alterQRename);
    }

    public long insert(String address, String lat, String lon) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i =db.getVersion();
        Log.d("ver",""+String.valueOf(i));
        ContentValues c = new ContentValues();
        c.put(this.address, address);
        c.put(this.lat, lat);
        c.put(this.lon, lon);
        long id = db.insert(this.tableName, null, c);
        return id;

    }

    public Cursor getdata() {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + tableName, null);
        return res;

    }
}

