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

public class project_helper  extends SQLiteOpenHelper {
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "projectSpinner.db";
    private static final String table_projectSpinner = "projectSpinner";
    private static final String ID = "id";
    private static final String projectSearchkey = "projectSearchkey";
    private static final String projectId = "projectId";
    private static final String projectName = "projectName";

    private static final String[] COLUMNS = { ID, projectSearchkey, projectId, projectName};

    public project_helper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE projectSpinner ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "projectSearchkey TEXT, " + "projectId TEXT, "+"projectName TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_projectSpinner);
        this.onCreate(db);

    }

    public void createprojectSpinner(String projectSearchkey_, String projectId_, String projectName_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(projectSearchkey,projectSearchkey_ );
        values.put( projectId,projectId_);
        values.put(projectName,projectName_);


        // insert Event
        db.insert(table_projectSpinner, null,  values);

        // close database transaction

        db.close();
    }

    public project_Model readprojectSpinner(int id) throws JSONException {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_projectSpinner, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        project_Model EM = new project_Model();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setProjectSearchkey(cursor.getString(1));
        EM.setProjectId(cursor.getString(2));
        EM.setProjectName(cursor.getString(3));


        return EM;
    }

    public List<project_Model> getAllprojectIds() {
        List<project_Model> eventsM = new LinkedList<project_Model>();

        // select Event query
        String query = "SELECT  * FROM "+table_projectSpinner;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        project_Model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new project_Model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setProjectSearchkey(cursor.getString(1));
                EM.setProjectId(cursor.getString(2));
                EM.setProjectName(cursor.getString(3));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }



    // Deleting single Event
    public void deleteEvent(project_Model EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_projectSpinner, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
