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

public class interestedIn_helper extends SQLiteOpenHelper {
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "interestedInSpinner.db";
    private static final String table_interestedInSpinner = "interestedInSpinner";
    private static final String ID = "id";
    private static final String interestedInTranslationName = "interestedInTranslationName";
    private static final String interestedInId = "interestedInId";
    private static final String interestedInOrder = "interestedInOrder";

    private static final String[] COLUMNS = { ID, interestedInTranslationName, interestedInId, interestedInOrder};

    public interestedIn_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE interestedInSpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "interestedInTranslationName TEXT, " + "interestedInId TEXT, "+"interestedInOrder TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_interestedInSpinner);
        this.onCreate(db);

    }

    public void createinterestedInSpinner(String interestedInTranslationName_, String interestedInId_, String interestedInOrder_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(interestedInTranslationName,interestedInTranslationName_ );
        values.put( interestedInId,interestedInId_);
        values.put(interestedInOrder,interestedInOrder_);


        // insert Event
        db.insert(table_interestedInSpinner, null,  values);

        // close database transaction

        db.close();
    }

    public interestedIn_Model readinterestedInSpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_interestedInSpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        interestedIn_Model EM = new interestedIn_Model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setInterestedInTranslationName(cursor.getString(1));
        EM.setInterestedInId(cursor.getString(2));
        EM.setInterestedInOrder(cursor.getString(3));

        return EM;
    }

    public List<interestedIn_Model> getAllInterestedIn() {
        List<interestedIn_Model> eventsM = new LinkedList<interestedIn_Model>();

        // select Event query
        String query = "SELECT  * FROM "+table_interestedInSpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        interestedIn_Model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new interestedIn_Model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setInterestedInTranslationName(cursor.getString(1));
                EM.setInterestedInId(cursor.getString(2));
                EM.setInterestedInOrder(cursor.getString(3));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }
        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(interestedIn_Model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_interestedInSpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }

}
