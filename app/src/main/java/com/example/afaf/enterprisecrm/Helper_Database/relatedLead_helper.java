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
 * Created by enterprise on 19/01/17.
 */

public class relatedLead_helper extends SQLiteOpenHelper{
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "rLeadSpinner.db";
    private static final String table_rLeadSpinner = "rLeadSpinner";
    private static final String ID = "id";
    private static final String rLeadName = "rLeadName";
    private static final String rLeadId = "rLeadId";

    private static final String[] COLUMNS = { ID, rLeadName, rLeadId};

    public relatedLead_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE rLeadSpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "rLeadName TEXT, " + "rLeadId TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_rLeadSpinner);
        this.onCreate(db);

    }

    public void createrLeadId(String rLeadName_, String rLeadId_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(rLeadName,rLeadName_ );
        values.put( rLeadId,rLeadId_);


        // insert Event
        db.insert(table_rLeadSpinner, null,  values);

        // close database transaction

        db.close();
    }

    public relatedLead_Model readrLeadName(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_rLeadSpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        relatedLead_Model EM = new relatedLead_Model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setrLeadName(cursor.getString(1));
        EM.setrLeadId(cursor.getString(2));


        return EM;
    }

    public List<relatedLead_Model> getAllRLeads() {
        List<relatedLead_Model> eventsM = new LinkedList<relatedLead_Model>();

        // select Event query
        String query = "SELECT  * FROM "+table_rLeadSpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        relatedLead_Model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new relatedLead_Model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setrLeadName(cursor.getString(1));
                EM.setrLeadId(cursor.getString(2));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(relatedLead_Model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_rLeadSpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
