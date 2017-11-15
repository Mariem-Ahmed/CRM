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
 * Created by enterprise on 25/10/16.
 */

public class assignedTo_spinner_helper extends SQLiteOpenHelper {
    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "assignedToSpinner.db";
    private static final String table_assignedtoSpinner = "assignedToSpinner";
    private static final String ID = "id";
    private static final String userName = "userName";
    private static final String userId = "userId";

    private static final String[] COLUMNS = { ID, userName, userId};

    public assignedTo_spinner_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE assignedToSpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "userName TEXT, " + "userId TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_assignedtoSpinner);
        this.onCreate(db);

    }

    public void createAssignedToSpinner(String userName_, String userId_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(userName,userName_ );
        values.put( userId,userId_);


        // insert Event
        db.insert(table_assignedtoSpinner, null,  values);

        // close database transaction

        db.close();
    }

    public assignedTo_spinner_model readAssignedToSpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_assignedtoSpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        assignedTo_spinner_model EM = new assignedTo_spinner_model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setUserName(cursor.getString(1));
        EM.setUserId(cursor.getString(2));

        return EM;
    }

    public List<assignedTo_spinner_model> getAllAssignedTo() {
        List<assignedTo_spinner_model> eventsM = new LinkedList<assignedTo_spinner_model>();

        // select Event query
        String query = "SELECT  * FROM "+table_assignedtoSpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        assignedTo_spinner_model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new assignedTo_spinner_model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setUserName(cursor.getString(1));
                EM.setUserId(cursor.getString(2));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }
        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(assignedTo_spinner_model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_assignedtoSpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }

}
