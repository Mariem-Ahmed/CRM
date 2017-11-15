package com.example.afaf.enterprisecrm.Helper_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.afaf.enterprisecrm.Helper_Database.opportunityModedel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by enterprise on 11/10/16.
 */

public class JCGSQLiteHelper_Oppo extends SQLiteOpenHelper {

    public static boolean listFlagInsert=false;
    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "Opportunities.db";
    private static final String table_Opportunity = "opportunity";
    private static final String ID = "id";
    private static final String oppoSubject = "oppoSubject";
    private static final String oppoCloseDate = "oppoCloseDate";
    private static final String oppoAmount = "oppoAmount";
    private static final String oppoProbablity = "oppoProbablity";
    private static final String oppoRelatedLead = "oppoRelatedLead";
    private static final String oppoAssignedto = "oppoAssignedto";
    private static final String oppoID = "oppoID";
    private static final String oppoRelatedLeadID = "oppoRelatedLeadID";
    private static final String oppoAssignedToID = "oppoAssignedToID";


    private static final String[] COLUMNS = { ID, oppoSubject, oppoCloseDate,oppoAmount,oppoProbablity,oppoRelatedLead,oppoAssignedto,oppoID, oppoRelatedLeadID, oppoAssignedToID };

    public JCGSQLiteHelper_Oppo(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE opportunity ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "oppoSubject TEXT, " + "oppoCloseDate TEXT, "+
                "oppoAmount TEXT, "+"oppoProbablity TEXT, "+"oppoRelatedLead TEXT, "+"oppoAssignedto TEXT, " + "oppoID TEXT," +
                " "+"oppoRelatedLeadID TEXT, "+"oppoAssignedToID TEXT)";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_Opportunity);
        this.onCreate(db);

    }

    public void createOpportunity(String oppoSubject_, String oppoCloseDate_, String oppoAmount_
            , String oppoProbablity_, String oppoRelatedLead_, String oppoAssignedto_, String oppoID_, String oppoRelatedLeadID_, String oppoAssignedToID_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(oppoSubject,oppoSubject_ );
        values.put( oppoCloseDate,oppoCloseDate_);
        values.put( oppoAmount, oppoAmount_);
        values.put(oppoProbablity , oppoProbablity_ );
        values.put(oppoRelatedLead , oppoRelatedLead_ );
        values.put(oppoAssignedto , oppoAssignedto_ );
        values.put(oppoID , oppoID_ );
        values.put(oppoRelatedLeadID, oppoRelatedLeadID_);
        values.put(oppoAssignedToID, oppoAssignedToID_);

        String xcf= oppoSubject_;
        // insert Event
        db.insert(table_Opportunity, null,  values);

        // close database transaction

    }

    public opportunityModedel readOpportunity(int id) {
        // get reference of the EventDB database
        listFlagInsert=false;
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_Opportunity, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        opportunityModedel EM = new opportunityModedel();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setOppoSubject(cursor.getString(1));
        EM.setOppoCloseDate(cursor.getString(2));
        EM.setOppoAmount(cursor.getString(3));
        EM.setOppoProbablity(cursor.getString(4));
        EM.setOppoRelatedLead(cursor.getString(5));
        EM.setOppoAssignedto(cursor.getString(6));
        EM.setOppoID(cursor.getString(7));
        EM.setOppoRelatedLeadID(cursor.getString(8));
        EM.setOppoAssignedToID(cursor.getString(9));



        return EM;
    }
    //-----------------------------get all cases for activity----------------------------------------------------------------
    public List<opportunityModedel> getAllOppoActivities( String actid) {
        List<opportunityModedel> eventsM = new LinkedList<opportunityModedel>();

        // select Event query
        String query = "SELECT  * FROM "+table_Opportunity +" where oppoID = '"+actid+"' ";

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        opportunityModedel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new opportunityModedel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setOppoSubject(cursor.getString(1));
                EM.setOppoCloseDate(cursor.getString(2));
                EM.setOppoAmount(cursor.getString(3));
                EM.setOppoProbablity(cursor.getString(4));
                EM.setOppoRelatedLead(cursor.getString(5));
                EM.setOppoAssignedto(cursor.getString(6));
                EM.setOppoID(cursor.getString(7));
                EM.setOppoRelatedLeadID(cursor.getString(8));
                EM.setOppoAssignedToID(cursor.getString(9));
                // Add event to events
                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }
    // ----------------------------- read all oppo related to lead --------------------------
    public List<opportunityModedel> getAllOpportunityLead(String leadid) {
        List<opportunityModedel> eventsM = new LinkedList<opportunityModedel>();

        // select Event query
        String query = "SELECT  * FROM "+table_Opportunity +" where oppoRelatedLeadID = '"+leadid+"' ";

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        opportunityModedel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new opportunityModedel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setOppoSubject(cursor.getString(1));
                EM.setOppoCloseDate(cursor.getString(2));
                EM.setOppoAmount(cursor.getString(3));
                EM.setOppoProbablity(cursor.getString(4));
                EM.setOppoRelatedLead(cursor.getString(5));
                EM.setOppoAssignedto(cursor.getString(6));
                EM.setOppoID(cursor.getString(7));
                EM.setOppoRelatedLeadID(cursor.getString(8));
                EM.setOppoAssignedToID(cursor.getString(9));
                // Add event to events
                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }
// ----------------------------------------------------------------------------------
    public List<opportunityModedel> getAllOpportunity() {
        List<opportunityModedel> eventsM = new LinkedList<opportunityModedel>();
try {
    // select Event query
    String query = "SELECT  * FROM " + table_Opportunity + " ORDER BY id ";

    // get reference of the EventDB database
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);

    // parse all results
    opportunityModedel EM = null;
    if (cursor.moveToFirst()) {
        do {

            EM = new opportunityModedel();
            EM.setId(Integer.parseInt(cursor.getString(0)));
            EM.setOppoSubject(cursor.getString(1));
            EM.setOppoCloseDate(cursor.getString(2));
            EM.setOppoAmount(cursor.getString(3));
            EM.setOppoProbablity(cursor.getString(4));
            EM.setOppoRelatedLead(cursor.getString(5));
            EM.setOppoAssignedto(cursor.getString(6));
            EM.setOppoID(cursor.getString(7));
            EM.setOppoRelatedLeadID(cursor.getString(8));
            EM.setOppoAssignedToID(cursor.getString(9));
            // Add event to events
            eventsM.add(EM);
        } while (cursor.moveToNext());
    }

    return eventsM;
}catch (Exception ex){

}return null;
    }
    public List<opportunityModedel> getAllOpportunity(String searchKey) {
        List<opportunityModedel> eventsM = new LinkedList<opportunityModedel>();
        try {
            // select Event query
            String query = "SELECT  * FROM " + table_Opportunity +  " where oppoSubject LIKE '"+searchKey+"%' ";

            // get reference of the EventDB database
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            // parse all results
            opportunityModedel EM = null;
            if (cursor.moveToFirst()) {
                do {

                    EM = new opportunityModedel();
                    EM.setId(Integer.parseInt(cursor.getString(0)));
                    EM.setOppoSubject(cursor.getString(1));
                    EM.setOppoCloseDate(cursor.getString(2));
                    EM.setOppoAmount(cursor.getString(3));
                    EM.setOppoProbablity(cursor.getString(4));
                    EM.setOppoRelatedLead(cursor.getString(5));
                    EM.setOppoAssignedto(cursor.getString(6));
                    EM.setOppoID(cursor.getString(7));
                    EM.setOppoRelatedLeadID(cursor.getString(8));
                    EM.setOppoAssignedToID(cursor.getString(9));
                    // Add event to events
                    eventsM.add(EM);
                } while (cursor.moveToNext());
            }

            return eventsM;
        }catch (Exception ex){

        }return null;
    }


    // Deleting single Event
    public void deleteEvent(opportunityModedel EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_Opportunity, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }

    // update  event
    public void updateEvent(opportunityModedel EM) {
        // , String activity_Subject, String activity_Startdate, String start_Hour, String start_Minute, String duration_Hours
        // ,String activity_Type, String activity_Status, String related_Lead

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(oppoSubject,EM.getOppoSubject());
        contentValues.put(oppoCloseDate, EM.getOppoCloseDate());
        contentValues.put(oppoAmount,EM.getOppoAmount());
        contentValues.put(oppoProbablity, EM.getOppoProbablity());
        contentValues.put(oppoRelatedLead, EM.getOppoRelatedLead());
        contentValues.put(oppoRelatedLeadID, EM.getOppoRelatedLead());
        contentValues.put(oppoAssignedto, EM.getOppoAssignedto());

        db1.update(table_Opportunity, contentValues,"ID=?",new String[] {String.valueOf(EM.getId())});
        db1.close();
    }
    // insert  event
    public void insertEvent(opportunityModedel EM) {

       listFlagInsert=true;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();

        values.put(ID, EM.getId());
        values.put(oppoSubject,EM.getOppoSubject() );
        values.put( oppoCloseDate,EM.getOppoCloseDate());
        values.put( oppoAmount, EM.getOppoAmount());
        values.put(oppoProbablity , EM.getOppoProbablity() );
        values.put(oppoRelatedLead , EM.getOppoRelatedLead() );
        values.put(oppoAssignedto , EM.getOppoAssignedto() );
        values.put(oppoID , EM.getOppoID() );
        values.put(oppoRelatedLeadID, EM.getOppoRelatedLead());
        values.put(oppoAssignedToID, EM.getOppoAssignedto());

        db.insert(table_Opportunity, null,values);

    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

}
