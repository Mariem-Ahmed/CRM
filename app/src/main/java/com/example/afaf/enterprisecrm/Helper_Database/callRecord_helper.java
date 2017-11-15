package com.example.afaf.enterprisecrm.Helper_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by enterprise on 08/01/17.
 */

public class callRecord_helper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "Records.db";
    private static final String table_Recording = "recording";
    private static final String ID = "id";
    private static final String recordName = "recordName";
    private static final String recordPath = "recordPath";
    private static final String cashDFlag = "cashDFlag";

    private static final String[] COLUMNS = { ID, recordName, recordPath, cashDFlag};

    public callRecord_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE recording ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "recordName TEXT," +
                " " + "recordPath TEXT, " + "cashDFlag TEXT)";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_Recording);
        this.onCreate(db);

    }

    public void createRecord(String recordName_, String recordPath_, String cashDFlag_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(recordName,recordName_ );
        values.put( recordPath,recordPath_);
        values.put( cashDFlag, cashDFlag_);

        // insert Event
        db.insert(table_Recording, null,  values);

        // close database transaction

        db.close();
    }

    public callRecord_Model readRecord(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_Recording, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        callRecord_Model EM = new callRecord_Model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setRecordName(cursor.getString(1));
        EM.setRecordPath(cursor.getString(2));
        EM.setCashDFlag(cursor.getString(3));


        return EM;
    }


    //-----------------------------------------------------------------------
    public List<callRecord_Model> getAllRecords( ) {
        List<callRecord_Model> eventsM = new LinkedList<callRecord_Model>();

        // select Event query
        String query = "SELECT  * FROM "+ table_Recording;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        callRecord_Model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new callRecord_Model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setRecordName(cursor.getString(1));
                EM.setRecordPath(cursor.getString(2));
                EM.setCashDFlag(cursor.getString(3));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }
        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(callRecord_Model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_Recording, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }

    // insert  event
    public long insertEvent(callRecord_Model EM) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

     //   contentValues.put(ID,EM.getId());
        contentValues.put(recordName,EM.getRecordName());
        contentValues.put(recordPath, EM.getRecordPath());
        contentValues.put(cashDFlag,EM.getCashDFlag());

        return db.insert(table_Recording, null,contentValues);
    }



}
