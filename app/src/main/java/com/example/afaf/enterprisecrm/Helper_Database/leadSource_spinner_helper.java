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

public class leadSource_spinner_helper extends SQLiteOpenHelper {
    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "leadSourceSpinner.db";
    private static final String table_leadSourceSpinner = "leadSourceSpinner";
    private static final String ID = "id";
    private static final String lSTranslationName = "lSTranslationName";
    private static final String lSId = "lSId";
    private static final String lSOrder = "lSOrder";

    private static final String[] COLUMNS = { ID, lSTranslationName, lSId, lSOrder};

    public leadSource_spinner_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE leadSourceSpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "lSTranslationName TEXT, " + "lSId TEXT, "+"lSOrder TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_leadSourceSpinner);
        this.onCreate(db);

    }

    public void createleadSourceSpinner(String lSTranslationName_, String lSId_, String lSOrder_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(lSTranslationName,lSTranslationName_ );
        values.put( lSId,lSId_);
        values.put(lSOrder,lSOrder_);


        // insert Event
        db.insert(table_leadSourceSpinner, null,  values);

        // close database transaction

        db.close();
    }

    public leadSource_spinner_model readleadSourceSpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_leadSourceSpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        leadSource_spinner_model EM = new leadSource_spinner_model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setlSTranslationName(cursor.getString(1));
        EM.setlSId(cursor.getString(2));
        EM.setlSOrder(cursor.getString(3));


        return EM;
    }

    public List<leadSource_spinner_model> getAllLeadSource() {
        List<leadSource_spinner_model> eventsM = new LinkedList<leadSource_spinner_model>();

        // select Event query
        String query = "SELECT  * FROM "+table_leadSourceSpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        leadSource_spinner_model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new leadSource_spinner_model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setlSTranslationName(cursor.getString(1));
                EM.setlSId(cursor.getString(2));
                EM.setlSOrder(cursor.getString(3));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(leadSource_spinner_model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_leadSourceSpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
