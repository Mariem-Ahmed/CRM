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

public class casePriority_spinner_helper extends SQLiteOpenHelper {
    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "casePerioritySpinner.db";
    private static final String table_casePerioritySpinner = "casePerioritySpinner";
    private static final String ID = "id";
    private static final String casePeriorityValue = "casePeriorityValue";
    private static final String casePeriorityName = "casePeriorityName";
    private static final String casePeriorityId = "casePeriorityId";

    private static final String[] COLUMNS = { ID, casePeriorityId, casePeriorityName, casePeriorityValue};

    public casePriority_spinner_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE casePerioritySpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "casePeriorityId TEXT," +
                " " + "casePeriorityName TEXT, " + "casePeriorityValue TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_casePerioritySpinner);
        this.onCreate(db);

    }

    public void createcasePerioritySpinner(String casePeriorityId_, String casePeriorityName_, String casePeriorityValue_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(casePeriorityId,casePeriorityId_ );
        values.put( casePeriorityName,casePeriorityName_);
        values.put( casePeriorityValue, casePeriorityValue_);


        // insert Event
        db.insert(table_casePerioritySpinner, null,  values);

        // close database transaction

        db.close();
    }

    public casePriority_spinner_model readcasePerioritySpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_casePerioritySpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        casePriority_spinner_model EM = new casePriority_spinner_model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setCasePeriorityId(cursor.getString(1));
        EM.setCasePeriorityName(cursor.getString(2));
        EM.setCasePeriorityValue(cursor.getString(3));

        return EM;
    }

    public List<casePriority_spinner_model> getAllCasePeriority() {
        List<casePriority_spinner_model> eventsM = new LinkedList<casePriority_spinner_model>();

        // select Event query
        String query = "SELECT  * FROM "+table_casePerioritySpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        casePriority_spinner_model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new casePriority_spinner_model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setCasePeriorityId(cursor.getString(1));
                EM.setCasePeriorityName(cursor.getString(2));
                EM.setCasePeriorityValue(cursor.getString(3));


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
        db.delete(table_casePerioritySpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }

}
