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

public class nationality_helper extends SQLiteOpenHelper {
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "nationalitySpinner.db";
    private static final String table_nationalitySpinner = "nationalitySpinner";
    private static final String ID = "id";
    private static final String isoCountrycode = "isoCountrycode";
    private static final String nationalityId = "nationalityId";
    private static final String countryName = "countryName";

    private static final String[] COLUMNS = { ID, isoCountrycode, nationalityId, countryName};

    public nationality_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE nationalitySpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "isoCountrycode TEXT, " + "nationalityId TEXT, "+"countryName TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_nationalitySpinner);
        this.onCreate(db);

    }

    public void createcountrySpinner(String isoCountrycode_, String nationalityId_, String countryName_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(isoCountrycode,isoCountrycode_ );
        values.put( nationalityId,nationalityId_);
        values.put(countryName,countryName_);


        // insert Event
        db.insert(table_nationalitySpinner, null,  values);

        // close database transaction
        db.close();
    }

    public nationality_Model readcountrySpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_nationalitySpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        nationality_Model EM = new nationality_Model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setIsoCountrycode(cursor.getString(1));
        EM.setNationalityId(cursor.getString(2));
        EM.setCountryName(cursor.getString(3));


        return EM;
    }

    public List<nationality_Model> getAllnationality() {
        List<nationality_Model> eventsM = new LinkedList<nationality_Model>();

        // select Event query
        String query = "SELECT  * FROM "+table_nationalitySpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        nationality_Model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new nationality_Model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setIsoCountrycode(cursor.getString(1));
                EM.setNationalityId(cursor.getString(2));
                EM.setCountryName(cursor.getString(3));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(nationality_Model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_nationalitySpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
