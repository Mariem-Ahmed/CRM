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
 * Created by enterprise on 17/01/17.
 */

public class caseStatus_helper extends SQLiteOpenHelper {
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "caseStatusSpinner.db";
    private static final String table_caseStatusSpinner = "caseStatusSpinner";
    private static final String ID = "id";
    private static final String caseStatusValue = "caseStatusValue";
    private static final String caseStatusName = "caseStatusName";
    private static final String caseStatusId = "caseStatusId";

    private static final String[] COLUMNS = { ID, caseStatusId, caseStatusName, caseStatusValue};

    public caseStatus_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE caseStatusSpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "caseStatusId TEXT," +
                " " + "caseStatusName TEXT, " + "caseStatusValue TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_caseStatusSpinner);
        this.onCreate(db);

    }

    public void createCaseStatusSpinner(String caseStatusId_, String caseStatusName_, String caseStatusValue_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(caseStatusId,caseStatusId_ );
        values.put( caseStatusName,caseStatusName_);
        values.put( caseStatusValue, caseStatusValue_);


        // insert Event
        db.insert(table_caseStatusSpinner, null,  values);

        // close database transaction

        db.close();
    }

    public caseStatus_Model readcaseStatusSpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_caseStatusSpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        caseStatus_Model EM = new caseStatus_Model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setCaseStatusId(cursor.getString(1));
        EM.setCaseStatusName(cursor.getString(2));
        EM.setCaseStatusValue(cursor.getString(3));

        return EM;
    }

    public List<caseStatus_Model> getAllCaseStatus() {
        List<caseStatus_Model> eventsM = new LinkedList<caseStatus_Model>();

        // select Event query
        String query = "SELECT  * FROM "+table_caseStatusSpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        caseStatus_Model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new caseStatus_Model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setCaseStatusId(cursor.getString(1));
                EM.setCaseStatusName(cursor.getString(2));
                EM.setCaseStatusValue(cursor.getString(3));


                eventsM .add(EM);
            } while (cursor.moveToNext());
        }
        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(caseStatus_Model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_caseStatusSpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }

}
