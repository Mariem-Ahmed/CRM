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
 * Created by enterprise on 24/10/16.
 */

public class activity_Type_spinner_helper  extends SQLiteOpenHelper {
    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "ActivityTypeSpinner.db";
    private static final String table_ActivitySpinner = "activitySpinner";
    private static final String ID = "id";
    private static final String activitykey = "activitykey";
    private static final String activityname = "activityname";
    private static final String activityId = "activityId";

    private static final String[] COLUMNS = { ID, activitykey, activityname, activityId};

    public activity_Type_spinner_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE activitySpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "activitykey TEXT," +
                " " + "activityname TEXT, " + "activityId TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_ActivitySpinner);
        this.onCreate(db);

    }

    public void createActivitySpinner(String activitykey_, String activityname_, String activityId_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(activitykey,activitykey_ );
        values.put( activityname,activityname_);
        values.put( activityId, activityId_);


        // insert Event
        db.insert(table_ActivitySpinner, null,  values);

        // close database transaction

        db.close();
    }

    public activity_Type_spinner_model readActivitySpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_ActivitySpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        activity_Type_spinner_model EM = new activity_Type_spinner_model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setActivitykey(cursor.getString(1));
        EM.setActivityname(cursor.getString(2));
        EM.setActivityId(cursor.getString(3));

        return EM;
    }

    public List<activity_Type_spinner_model> getAllActivityType() {
        List<activity_Type_spinner_model> eventsM = new LinkedList<activity_Type_spinner_model>();

        // select Event query
        String query = "SELECT  * FROM "+table_ActivitySpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        activity_Type_spinner_model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new activity_Type_spinner_model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setActivitykey(cursor.getString(1));
                EM.setActivityname(cursor.getString(2));
                EM.setActivityId(cursor.getString(3));


                eventsM .add(EM);
            } while (cursor.moveToNext());
        }
        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(activity_Type_spinner_model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_ActivitySpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }

}
