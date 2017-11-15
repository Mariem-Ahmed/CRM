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

public class leadStatus_spinner_helper extends SQLiteOpenHelper {
    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "LeadStatusSpinner.db";
    private static final String table_LeadStatusSpinner = "leadStatusSpinner";
    private static final String ID = "id";
    private static final String leadStatusValue = "leadStatusValue";
    private static final String leadStatusName = "leadStatusName";
    private static final String leadStatusId = "leadStatusId";

    private static final String[] COLUMNS = { ID, leadStatusId, leadStatusName, leadStatusValue};

    public leadStatus_spinner_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE leadStatusSpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "leadStatusId TEXT," +
                " " + "leadStatusName TEXT, " + "leadStatusValue TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_LeadStatusSpinner);
        this.onCreate(db);

    }

    public void createLeadStatusSpinner(String leadStatusId_, String leadStatusName_, String leadStatusValue_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(leadStatusId,leadStatusId_ );
        values.put( leadStatusName,leadStatusName_);
        values.put( leadStatusValue, leadStatusValue_);


        // insert Event
        db.insert(table_LeadStatusSpinner, null,  values);

        // close database transaction

        db.close();
    }

    public leadStatus_spinner_model readLeadStatusSpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_LeadStatusSpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        leadStatus_spinner_model EM = new leadStatus_spinner_model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setLeadStatusId(cursor.getString(1));
        EM.setLeadStatusName(cursor.getString(2));
        EM.setLeadStatusValue(cursor.getString(3));


        return EM;
    }

    public List<leadStatus_spinner_model> getAllLeadStatus() {
        List<leadStatus_spinner_model> eventsM = new LinkedList<leadStatus_spinner_model>();

        // select Event query
        String query = "SELECT  * FROM "+table_LeadStatusSpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        leadStatus_spinner_model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new leadStatus_spinner_model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setLeadStatusId(cursor.getString(1));
                EM.setLeadStatusName(cursor.getString(2));
                EM.setLeadStatusValue(cursor.getString(3));


                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(leadStatus_spinner_model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_LeadStatusSpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
