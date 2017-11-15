package com.example.afaf.enterprisecrm.Helper_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.afaf.enterprisecrm.Helper_Database.casesModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by enterprise on 11/10/16.
 */

public class JCGSQLiteHelper_cases extends SQLiteOpenHelper {

    public static boolean listFlagInsert=false;
    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "Cases.db";
    private static final String table_Cases = "cases";
    private static final String ID = "id";
    private static final String leadName = "leadName";
    private static final String subjectName = "subjectName";
    private static final String spentTime = "spentTime";
    private static final String assignedTo = "assignedTo";
    private static final String status = "status";
    private static final String priority = "priority";
    private static final String deadLine = "deadLine";
    private static final String caseID = "caseID";
    private static final String caseRelatedLead = "caseRelatedLead";
    private static final String spenthours = "spenthours";
    private static final String spentmintues = "spentmintues";
    private static final String caseActivity= "caseActivity";
    private static final String caseActivityId= "caseActivityId";

    private static final String[] COLUMNS = {ID,assignedTo, leadName,spentTime,priority, subjectName,caseID , deadLine
            ,status, caseRelatedLead, spenthours, spentmintues, caseActivity,caseActivityId};

    public JCGSQLiteHelper_cases(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE cases ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "assignedTo TEXT, "+"leadName TEXT , " + "spentTime TEXT,  " + "priority TEXT,"+" subjectName TEXT, " + "caseID TEXT, "+"deadLine TEXT, "+"status TEXT, "+"caseRelatedLead TEXT," +
                " "+"spenthours TEXT, "+"spentmintues TEXT, "+"caseActivity TEXT, "+"caseActivityId TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_Cases);
        this.onCreate(db);

    }

    public void createCases( String assignedTo_,String leadName_ ,String spentTime_
            ,String priority_ , String subjectName_, String caseID_, String deadLine_,
                             String status_, String  caseRelatedLead_, String spenthours_,
                             String spentmintues_, String caseActivity_,String caseActivityId_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(assignedTo, assignedTo_);
        values.put(leadName, leadName_);
        values.put(spentTime, spentTime_);
        values.put(priority, priority_);
        values.put(subjectName, subjectName_);
        values.put(caseID, caseID_);
        values.put(deadLine, deadLine_);
        values.put(status, status_);
        values.put(caseRelatedLead, caseRelatedLead_);
        values.put(spenthours, spenthours_);
        values.put(spentmintues, spentmintues_);
        values.put(caseActivity, caseActivity_);
        values.put(caseActivityId, caseActivityId_);
      //  String xcf = leadName_;
        // insert Event
        db.insert(table_Cases, null, values);

        // close database transaction
      //  db.close();
    }

    public casesModel readCase(int id) {
        // get reference of the EventDB database

        listFlagInsert=false;
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_Cases, // a. table
                COLUMNS, " id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        casesModel EM = new casesModel();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setAssignedTo(cursor.getString(1));
        EM.setLeadName(cursor.getString(2));
        EM.setTimeSpent(cursor.getString(3));
        EM.setPriority(cursor.getString(4));
        EM.setSubjectName(cursor.getString(5));
        EM.setCaseID(cursor.getString(6));
        EM.setDeadLine(cursor.getString(7));
        EM.setStatus(cursor.getString(8));
        EM.setCaseRelatedLead(cursor.getString(9));
        EM.setSpenthours(cursor.getString(10));
        EM.setSpentmintues(cursor.getString(11));
        EM.setCaseActivity(cursor.getString(12));
        EM.setCaseActivityId(cursor.getString(13));

        return EM;
    }
    //-----------------------------get all cases for activity----------------------------------------------------------------
    public List<casesModel> getAllCasesActivities( String actid) {
        List<casesModel> eventsM = new LinkedList<casesModel>();

        // select Event query
        String query = "SELECT  * FROM "+table_Cases +" where caseActivityId = '"+actid+"' ";

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        casesModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new casesModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setAssignedTo(cursor.getString(1));
                EM.setLeadName(cursor.getString(2));
                EM.setTimeSpent(cursor.getString(3));
                EM.setPriority(cursor.getString(4));
                EM.setSubjectName(cursor.getString(5));
                EM.setCaseID(cursor.getString(6));
                EM.setDeadLine(cursor.getString(7));
                EM.setStatus(cursor.getString(8));
                EM.setCaseRelatedLead(cursor.getString(9));
                EM.setSpenthours(cursor.getString(10));
                EM.setSpentmintues(cursor.getString(11));
                EM.setCaseActivity(cursor.getString(12));
                EM.setCaseActivityId(cursor.getString(13));
                // Add event to events
                eventsM.add(EM);
            } while (cursor.moveToNext());
        }
        return eventsM;
    }
    //-----------------------------get all cases for lead----------------------------------------------------------------
    public List<casesModel> getAllCasesLeads( String leadid) {
        List<casesModel> eventsM = new LinkedList<casesModel>();

        // select Event query
        String query = "SELECT  * FROM "+table_Cases +" where caseRelatedLead = '"+leadid+"' ";

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        casesModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new casesModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setAssignedTo(cursor.getString(1));
                EM.setLeadName(cursor.getString(2));
                EM.setTimeSpent(cursor.getString(3));
                EM.setPriority(cursor.getString(4));
                EM.setSubjectName(cursor.getString(5));
                EM.setCaseID(cursor.getString(6));
                EM.setDeadLine(cursor.getString(7));
                EM.setStatus(cursor.getString(8));
                EM.setCaseRelatedLead(cursor.getString(9));
                EM.setSpenthours(cursor.getString(10));
                EM.setSpentmintues(cursor.getString(11));
                EM.setCaseActivity(cursor.getString(12));
                EM.setCaseActivityId(cursor.getString(13));
                // Add event to events
                eventsM.add(EM);
            } while (cursor.moveToNext());
        }
        return eventsM;
    }


// ---------------------------------------------------------------------------
    public List<casesModel> getAllCases() {
        List<casesModel> eventsM = new LinkedList<casesModel>();

        // select Event query
        try {
            String query = "SELECT  * FROM " + table_Cases + " ORDER BY id ";

            // get reference of the EventDB database
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            // parse all results
            casesModel EM = null;
            if (cursor.moveToFirst()) {
                do {

                    EM = new casesModel();
                    EM.setId(Integer.parseInt(cursor.getString(0)));
                    EM.setAssignedTo(cursor.getString(1));
                    EM.setLeadName(cursor.getString(2));
                    EM.setTimeSpent(cursor.getString(3));
                    EM.setPriority(cursor.getString(4));
                    EM.setSubjectName(cursor.getString(5));
                    EM.setCaseID(cursor.getString(6));
                    EM.setDeadLine(cursor.getString(7));
                    EM.setStatus(cursor.getString(8));
                    EM.setCaseRelatedLead(cursor.getString(9));
                    EM.setSpenthours(cursor.getString(10));
                    EM.setSpentmintues(cursor.getString(11));
                    EM.setCaseActivity(cursor.getString(12));
                    EM.setCaseActivityId(cursor.getString(13));
                    // Add event to events
                    eventsM.add(EM);
                } while (cursor.moveToNext());
                return eventsM;
            }
        }catch (Exception ex){

        }
        return null;
    }
    public List<casesModel> getAllCases(String searchKey) {
        List<casesModel> eventsM = new LinkedList<casesModel>();

        // select Event query
        try {
            String query = "SELECT  * FROM " + table_Cases + " where subjectName LIKE '"+searchKey+"%' ";
            // get reference of the EventDB databaseg
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            // parse all results
            casesModel EM = null;
            if (cursor.moveToFirst()) {
                do {

                    EM = new casesModel();
                    EM.setId(Integer.parseInt(cursor.getString(0)));
                    EM.setAssignedTo(cursor.getString(1));
                    EM.setLeadName(cursor.getString(2));
                    EM.setTimeSpent(cursor.getString(3));
                    EM.setPriority(cursor.getString(4));
                    EM.setSubjectName(cursor.getString(5));
                    EM.setCaseID(cursor.getString(6));
                    EM.setDeadLine(cursor.getString(7));
                    EM.setStatus(cursor.getString(8));
                    EM.setCaseRelatedLead(cursor.getString(9));
                    EM.setSpenthours(cursor.getString(10));
                    EM.setSpentmintues(cursor.getString(11));
                    EM.setCaseActivity(cursor.getString(12));
                    EM.setCaseActivityId(cursor.getString(13));
                    // Add event to events
                    eventsM.add(EM);
                } while (cursor.moveToNext());
                return eventsM;
            }
        }catch (Exception ex){

        }
        return null;
    }

    // Deleting single Event
    public void deleteEvent(casesModel EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_Cases, ID + " = ?", new String[]{String.valueOf(EM.getId())});
        db.close();
    }

    public void updateEvent(casesModel EM) {
        // , String activity_Subject, String activity_Startdate, String start_Hour, String start_Minute, String duration_Hours
        // ,String activity_Type, String activity_Status, String related_Lead

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(assignedTo,EM.getAssignedTo());
        contentValues.put(leadName,EM.getLeadName());
        contentValues.put(caseRelatedLead,EM.getLeadName());
      //  contentValues.put(spentTime, EM.getTimeSpent());
        contentValues.put(priority,EM.getPriority());
        contentValues.put(subjectName, EM.getSubjectName());
        contentValues.put(caseID,EM.getCaseID());
        contentValues.put(deadLine,EM.getDeadLine());
        contentValues.put(status,EM.getStatus());
      //  contentValues.put(spenthours,EM.getSpenthours());
     //   contentValues.put(spentmintues,EM.getSpentmintues());
        contentValues.put(caseActivity,EM.getCaseActivity());
        contentValues.put(caseActivityId,EM.getCaseActivityId());

        db1.update(table_Cases, contentValues,"ID=?",new String[] {String.valueOf(EM.getId())});
        db1.close();
    }

    // insert  event
    public void insertEvent(casesModel EM) {

        listFlagInsert=true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(ID, EM.getId());
        contentValues.put(assignedTo,EM.getAssignedTo());
        contentValues.put(leadName,EM.getLeadName());
        contentValues.put(caseRelatedLead,EM.getLeadName());
       // contentValues.put(spentTime, EM.getTimeSpent());
        contentValues.put(priority,EM.getPriority());
        contentValues.put(subjectName, EM.getSubjectName());
      //  contentValues.put(caseID,EM.getCaseID());
        contentValues.put(deadLine,EM.getDeadLine());
        contentValues.put(status,EM.getStatus());
       // contentValues.put(spenthours,EM.getSpenthours());
      //  contentValues.put(spentmintues,EM.getSpentmintues());
        contentValues.put(caseActivity,EM.getCaseActivity());
        contentValues.put(caseActivityId,EM.getCaseActivity());

        db.insert(table_Cases, null,contentValues);
        db.close();

    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}

