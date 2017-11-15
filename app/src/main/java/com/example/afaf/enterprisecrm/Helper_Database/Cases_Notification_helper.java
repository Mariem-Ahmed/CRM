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
 * Created by enterprise on 26/12/16.
 */

public class Cases_Notification_helper extends SQLiteOpenHelper{


        // database version
        private static final int database_VERSION = 1;
        // database name
        private static final String database_NAME = "CasesNotification.db";
        private static final String table_CasesNotif = "Cases_notif";
        private static final String ID = "id";
        private static final String notifID = "notifID";
        private static final String notifSubject = "notifSubject";
        private static final String notifDesc= "notifDesc";
        private static final String createdDate = "createdDate";
        private static final String updatedDate = "updatedDate";
        private static final String notifSentFlag = "notifSentFlag";
        private static final String[] COLUMNS = { ID, notifID, notifSubject, notifDesc, notifSentFlag, updatedDate, createdDate};

        public Cases_Notification_helper(Context context) {
            super(context, database_NAME, null, database_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // SQL statement to create Event table
            String CREATE_Event_TABLE = "CREATE TABLE Cases_notif ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "notifID TEXT, " + "notifSubject TEXT," +
                    " " + "notifDesc TEXT, " + "notifSentFlag TEXT, " + "updatedDate TEXT, " + "createdDate TEXT)";
            db.execSQL(CREATE_Event_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+table_CasesNotif);
            this.onCreate(db);

        }

        public void createCasesNotif(String notifID_,String notifSubject_, String notifDesc_, String notifSentFlag_
                , String updatedDate_, String createdDate_) {
            // get reference of the EventDB database
            SQLiteDatabase db = this.getWritableDatabase();

            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(notifID,notifID_ );
            values.put(notifSubject,notifSubject_ );
            values.put( notifDesc,notifDesc_);
            values.put( notifSentFlag, notifSentFlag_);
            values.put( updatedDate, updatedDate_);
            values.put( createdDate, createdDate_);
            // insert Event
            db.insert(table_CasesNotif, null,  values);

            // close database transaction

            db.close();
        }

        public Cases_Notification_Model readCasesNotif(int id) throws JSONException {
            // get reference of the EventDB database
            SQLiteDatabase db = this.getReadableDatabase();

            // get Event query
            Cursor cursor = db.query(table_CasesNotif, // a. table
                    COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
            // if results !=null, parse the first one
            if (cursor != null)
                cursor.moveToFirst();

            Cases_Notification_Model EM = new Cases_Notification_Model();
            EM.setId(Integer.parseInt(cursor.getString(0)));
            EM.setNotifID(cursor.getString(1));
            EM.setNotifSubject(cursor.getString(2));
            EM.setNotifDesc(cursor.getString(3));
            EM.setNotifSentFlag(cursor.getString(4));
            EM.setUpdatedDate(cursor.getString(5));
            EM.setCreatedDate(cursor.getString(6));

            return EM;
        }

        public List<Cases_Notification_Model> getAllCasesNotif( ) {
            List<Cases_Notification_Model> eventsM = new LinkedList<Cases_Notification_Model>();

            // select Event query
            String query = "SELECT  * FROM "+ table_CasesNotif;

            // get reference of the EventDB database
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            // parse all results
            Cases_Notification_Model EM = null;
            if (cursor.moveToFirst()) {
                do {

                    EM = new Cases_Notification_Model();
                    EM.setId(Integer.parseInt(cursor.getString(0)));
                    EM.setNotifID(cursor.getString(1));
                    EM.setNotifSubject(cursor.getString(2));
                    EM.setNotifDesc(cursor.getString(3));
                    EM.setNotifSentFlag(cursor.getString(4));
                    EM.setUpdatedDate(cursor.getString(5));
                    EM.setCreatedDate(cursor.getString(6));

                    eventsM .add(EM);
                } while (cursor.moveToNext());
            }
            return eventsM ;
        }



        // Deleting single Event
        public void deleteEvent(Cases_Notification_Model EM) {

            // get reference of the EventDB database
            SQLiteDatabase db = this.getWritableDatabase();

            // delete Event
            db.delete(table_CasesNotif, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
            db.close();
        }

        // update  event
        public long updateEvent(Cases_Notification_Model EM) {

            SQLiteDatabase db1 = this.getWritableDatabase();
            ContentValues contentValues= new ContentValues();

//        contentValues.put(ID,EM.getId());
//        contentValues.put(notifSubject,EM.getNotifSubject());
//        contentValues.put(notifDesc, EM.getNotifDesc());
            contentValues.put(notifSentFlag,true);

            return db1.update(table_CasesNotif, contentValues,"ID=?",new String[] {String.valueOf(EM.getId())});
        }


    }



