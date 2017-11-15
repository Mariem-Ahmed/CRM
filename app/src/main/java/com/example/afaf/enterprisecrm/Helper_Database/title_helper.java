package com.example.afaf.enterprisecrm.Helper_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.afaf.enterprisecrm.title_Model;

import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by enterprise on 19/01/17.
 */

public class title_helper extends SQLiteOpenHelper{
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "titleSpinner.db";
    private static final String table_titleSpinner = "titleSpinner";
    private static final String ID = "id";
    private static final String titleTranslationName = "titleTranslationName";
    private static final String titleId = "titleId";
    private static final String titleOrder = "titleOrder";

    private static final String[] COLUMNS = { ID, titleTranslationName, titleId, titleOrder};

    public title_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE titleSpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "titleTranslationName TEXT, " + "titleId TEXT, "+"titleOrder TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_titleSpinner);
        this.onCreate(db);

    }

    public void createtitleSpinner(String titleTranslationName_, String titleId_, String titleOrder_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(titleTranslationName,titleTranslationName_ );
        values.put( titleId,titleId_);
        values.put(titleOrder,titleOrder_);


        // insert Event
        db.insert(table_titleSpinner, null,  values);

        // close database transaction

    }

    public title_Model readtitleSpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_titleSpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        title_Model EM = new title_Model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setTitleTranslationName(cursor.getString(1));
        EM.setTitleId(cursor.getString(2));
        EM.setTitleOrder(cursor.getString(3));


        return EM;
    }

    public List<title_Model> getAllTitles() {
        List<title_Model> eventsM = new LinkedList<title_Model>();

        // select Event query
        String query = "SELECT  * FROM "+table_titleSpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        title_Model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new title_Model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setTitleTranslationName(cursor.getString(1));
                EM.setTitleId(cursor.getString(2));
                EM.setTitleOrder(cursor.getString(3));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(title_Model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_titleSpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
