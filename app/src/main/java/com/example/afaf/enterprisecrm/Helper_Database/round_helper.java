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

public class round_helper extends SQLiteOpenHelper{
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "roundSpinner.db";
    private static final String table_roundSpinner = "roundSpinner";
    private static final String ID = "id";
    private static final String roundSearchkey = "roundSearchkey";
    private static final String roundId = "roundId";
    private static final String roundName = "roundName";

    private static final String[] COLUMNS = { ID, roundSearchkey, roundId, roundName};

    public round_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE roundSpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "roundSearchkey TEXT, " + "roundId TEXT, "+"roundName TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_roundSpinner);
        this.onCreate(db);

    }

    public void createroundSpinner(String roundSearchkey_, String roundId_, String roundName_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(roundSearchkey,roundSearchkey_ );
        values.put( roundId,roundId_);
        values.put(roundName,roundName_);


        // insert Event
        db.insert(table_roundSpinner, null,  values);

        // close database transaction

        db.close();
    }

    public round_Model readroundSpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_roundSpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        round_Model EM = new round_Model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setRoundSearchkey(cursor.getString(1));
        EM.setRoundId(cursor.getString(2));
        EM.setRoundName(cursor.getString(3));


        return EM;
    }

    public List<round_Model> getAllRoundIds() {
        List<round_Model> eventsM = new LinkedList<round_Model>();

        // select Event query
        String query = "SELECT  * FROM "+table_roundSpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        round_Model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new round_Model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setRoundSearchkey(cursor.getString(1));
                EM.setRoundId(cursor.getString(2));
                EM.setRoundName(cursor.getString(3));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(round_Model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_roundSpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
