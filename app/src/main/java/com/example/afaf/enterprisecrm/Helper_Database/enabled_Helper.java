package com.example.afaf.enterprisecrm.Helper_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ibrahimfouad on 8/23/2017.
 */

public class enabled_Helper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "EnabledUsers.db";
    private static final String table_Enabled = "enabled";
    private static final String ID = "ID";
    private static final String Enabled_Id = "EID";
    private static final String Enabled_Name = "EName";
    private static final String Lead_Access_ID = "LeadAccessID";

    private static final String[] COLUMNS = {ID, Enabled_Id, Enabled_Name, Lead_Access_ID};

    public enabled_Helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE opportunity ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EID TEXT, " + "EName TEXT, " + "LeadAccessID TEXT)";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_Enabled);
        this.onCreate(db);

    }

    public void createEnabled(String EnabledID_, String EnabledName_, String LeadAccessID_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(Enabled_Id, EnabledID_);
        values.put(Enabled_Name, EnabledName_);
        values.put(Lead_Access_ID, LeadAccessID_);

        // insert Event
        db.insert(table_Enabled, null, values);
    }

    public List<enabled_Model> getAllEnabledLead(String leadid) {
        List<enabled_Model> eventsM = new LinkedList<enabled_Model>();

        // select Event query
        String query = "SELECT  * FROM " + table_Enabled + " where LeadAccessID = '" + leadid + "' ";

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        enabled_Model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new enabled_Model();
                EM.setEnbled_ID(Integer.parseInt(cursor.getString(0)));
                EM.setEnabled_name(cursor.getString(1));
                EM.setLead_access_ID(cursor.getString(2));
                // Add event to events
                eventsM.add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM;
    }
}
