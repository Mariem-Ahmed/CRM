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
 * Created by enterprise on 26/10/16.
 */

public class activity_Status_spinner_helper extends SQLiteOpenHelper {
    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "ActivityStatusSpinner.db";
    private static final String table_ActivityStatusSpinner = "activityStatusSpinner";
    private static final String ID = "id";
    private static final String activityStatusname = "activityStatusname";
    private static final String activityStatusId = "activityStatusId";
    private static final String activityStatusSearchKey = "activityStatusSearchKey";


    private static final String[] COLUMNS = { ID, activityStatusname, activityStatusId, activityStatusSearchKey};

    public activity_Status_spinner_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE activityStatusSpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "activityStatusname TEXT," +
                " " + "activityStatusId TEXT, "+"activityStatusSearchKey TEXT)";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_ActivityStatusSpinner);
        this.onCreate(db);

    }

    public void createactivityStatusSpinner(String activityStatusname_, String activityStatusId_, String activityStatusSearchKey_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(activityStatusname,activityStatusname_ );
        values.put( activityStatusId,activityStatusId_);
        values.put( activityStatusSearchKey,activityStatusSearchKey_);

        // insert Event
        db.insert(table_ActivityStatusSpinner, null,  values);

        // close database transaction
        db.close();
    }

    public activity_Status_spinner_model readActivityStatusSpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_ActivityStatusSpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        activity_Status_spinner_model EM = new activity_Status_spinner_model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setActivityStatusname(cursor.getString(1));
        EM.setActivityStatusId(cursor.getString(2));
        EM.setActivityStatusSearchKey(cursor.getString(3));
        // close database transaction
        db.close();
        return EM;
    }

    public List<activity_Status_spinner_model> getAllActivityStatus() {
        List<activity_Status_spinner_model> eventsM = new LinkedList<activity_Status_spinner_model>();

        // select Event query
        String query = "SELECT  * FROM "+table_ActivityStatusSpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        activity_Status_spinner_model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new activity_Status_spinner_model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setActivityStatusname(cursor.getString(1));
                EM.setActivityStatusId(cursor.getString(2));
                EM.setActivityStatusSearchKey(cursor.getString(3));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }
        // close database transaction
        db.close();
        return eventsM ;
    }

    public List<activity_Status_spinner_model> getAllActivityStatus(String id) {
        List<activity_Status_spinner_model> eventsM = new LinkedList<activity_Status_spinner_model>();

        // select Event query
        String query = "SELECT  * FROM "+table_ActivityStatusSpinner + " where activityStatusId = '"+id+"'";

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        activity_Status_spinner_model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new activity_Status_spinner_model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setActivityStatusname(cursor.getString(1));
                EM.setActivityStatusId(cursor.getString(2));
                EM.setActivityStatusSearchKey(cursor.getString(3));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }
        // close database transaction
        db.close();
        return eventsM ;
    }

    // Deleting single Event
    public void deleteEvent(activity_Status_spinner_model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_ActivityStatusSpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }

}
