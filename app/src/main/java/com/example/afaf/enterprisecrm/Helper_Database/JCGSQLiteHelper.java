package com.example.afaf.enterprisecrm.Helper_Database;

/**
 * Created by ibrahimfouad on 02/10/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;

import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;

public class JCGSQLiteHelper extends SQLiteOpenHelper {

    public static Boolean listFlagInsert=false;

    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "Activities.db";
    private static final String table_Activity = "activity";
    private static final String ID = "id";
    private static final String activitySubject = "activitySubject";
    private static final String activityStartdate = "activityStartdate";
    private static final String startHour = "startHour";
    private static final String startMinute = "startMinute";
    private static final String durationHours = "durationHours";
    private static final String activityType = "activityType";
    private static final String activityStatus = "activityStatus";
    private static final String relatedLead = "relatedLead";
    private static final String activityId = "activityId";
    private static final String actRelatdLead = "actRelatdLead";
    private static final String leadStatus = "leadStatus";
    private static final String description = "description";
    private static final String assigeTo = "assigeTo";

       private static final String[] COLUMNS = { ID, activitySubject, activityStartdate, startHour,
               startMinute, durationHours, activityType, activityStatus, relatedLead, activityId,
               actRelatdLead, leadStatus, description, assigeTo};

    public JCGSQLiteHelper(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE activity ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "activitySubject TEXT," +
                " " + "activityStartdate TEXT, " + "startHour TEXT, " + "startMinute TEXT, " + "durationHours TEXT, " + "activityType TEXT," +
                " " + "activityStatus TEXT, " + "relatedLead TEXT ," + " activityId TEXT," +
                " " + "actRelatdLead TEXT, " + "leadStatus TEXT, "+"description TEXT, "+"assigeTo TEXT )";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS "+table_Activity);
        this.onCreate(db);

    }

    public void createActivity(String activitySubject_, String activityStartdate_, String startHour_, String startMinute_ , String durationHours_
    ,String activityType_, String activityStatus_, String relatedLead_,
                               String activityId_, String actRelatdLead_, String leadStatus_, String description_, String assigeTo_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(activitySubject,activitySubject_ );
        values.put( activityStartdate,activityStartdate_);
        values.put( startHour, startHour_);
        values.put(startMinute , startMinute_ );
        values.put( durationHours, durationHours_ );
        values.put( activityType, activityType_ );
        values.put( activityStatus,activityStatus_  );
        values.put(relatedLead , relatedLead_ );
        values.put(activityId , activityId_ );
        values.put(actRelatdLead,actRelatdLead_);
        values.put(leadStatus,leadStatus_);
        values.put(description,description_);
        values.put(assigeTo,assigeTo_);
        // insert Event
        db.insert(table_Activity, null,  values);

        // close database transaction
       // db.close();
    }

    public ActivityModel readActivity(int id) throws JSONException {
        // get reference of the EventDB database
        listFlagInsert=false;
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_Activity, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);
//        JSONArray arr =   convertCursorToJSON(cursor);
        // if results !=null, parse the first one
        try {
            if (cursor != null)
                cursor.moveToFirst();

            ActivityModel EM = new ActivityModel();
            EM.setId(Integer.parseInt(cursor.getString(0)));
            EM.setActivitySubject(cursor.getString(1));
            EM.setActivityStartdate(cursor.getString(2));
            EM.setStartHour(cursor.getString(3));
            EM.setStartMinute(cursor.getString(4));
            EM.setDurationHours(cursor.getString(5));
            EM.setActivityType(cursor.getString(6));
            EM.setActivityStatus(cursor.getString(7));
            EM.setRelatedLead(cursor.getString(8));
            EM.setActivityId(cursor.getString(9));
            EM.setActRelatdLead(cursor.getString(10));
            EM.setLeadStatus(cursor.getString(11));
            EM.setDescription(cursor.getString(12));
            EM.setAssigeTo(cursor.getString(13));
            return EM;
        }catch(Exception ex){

        }
         return null ;
        // close database transaction
    //    db.close();

    }
    //-----------------------------get  activity for activity id----------------------------------------------------------------
    public List<ActivityModel> getActivityWithActivityID(String actId) {
        List<ActivityModel> eventsM = new LinkedList<ActivityModel>();

        // select Event query
        String query = "SELECT  * FROM "+table_Activity +" where activityId = '"+actId+"' ";

        // get reference of the EventDB database
        try {
            SQLiteDatabase db = null;
            db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            // parse all results
            ActivityModel EM = null;
            if (cursor.moveToFirst()) {
                do {

                    EM = new ActivityModel();
                    EM.setId(Integer.parseInt(cursor.getString(0)));
                    EM.setActivitySubject(cursor.getString(1));
                    EM.setActivityStartdate(cursor.getString(2));
                    EM.setStartHour(cursor.getString(3));
                    EM.setStartMinute(cursor.getString(4));
                    EM.setDurationHours(cursor.getString(5));
                    EM.setActivityType(cursor.getString(6));
                    EM.setActivityStatus(cursor.getString(7));
                    EM.setRelatedLead(cursor.getString(8));
                    EM.setActivityId(cursor.getString(9));
                    EM.setActRelatdLead(cursor.getString(10));
                    EM.setLeadStatus(cursor.getString(11));
                    EM.setDescription(cursor.getString(12));
                    EM.setAssigeTo(cursor.getString(13));

                    eventsM.add(EM);
                } while (cursor.moveToNext());

            }
            // close database transaction
            //  db.close();
            return eventsM;
        }
        catch (Exception ex){

        }
        return  null;
    }
    //-----------------------------get all activities for lead----------------------------------------------------------------
    public List<ActivityModel> getActivityWithLeadID(String leadId) {
        List<ActivityModel> eventsM = new LinkedList<ActivityModel>();

        // select Event query
        String query = "SELECT  * FROM "+table_Activity +" where actRelatdLead = '"+leadId+"' ";

        // get reference of the EventDB database
        SQLiteDatabase db=null;
        db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        ActivityModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new ActivityModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setActivitySubject(cursor.getString(1));
                EM.setActivityStartdate(cursor.getString(2));
                EM.setStartHour(cursor.getString(3));
                EM.setStartMinute(cursor.getString(4));
                EM.setDurationHours(cursor.getString(5));
                EM.setActivityType(cursor.getString(6));
                EM.setActivityStatus(cursor.getString(7));
                EM.setRelatedLead(cursor.getString(8));
                EM.setActivityId(cursor.getString(9));
                EM.setActRelatdLead(cursor.getString(10));
                EM.setLeadStatus(cursor.getString(11));
                EM.setDescription(cursor.getString(12));
                EM.setAssigeTo(cursor.getString(13));

                eventsM .add(EM);
            } while (cursor.moveToNext());

        }
        // close database transaction
    //    db.close();
        return eventsM ;
    }
    // --------------------------------- activities with case lead ----------------------------------
    public List<String> getAllActivitiesLeads(String leadId ) {
        List<String> eventsM = new LinkedList<String>();

        // select Event query
        String query = "SELECT  activitySubject  FROM "+ table_Activity +" where actRelatdLead = '"+leadId+"' ";
        try {
            // get reference of the EventDB database
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            // parse all results
            ActivityModel EM = null;
            String em = null;
            if (cursor.moveToFirst()) {
                do {

                    EM = new ActivityModel();

                    EM.setActivitySubject(cursor.getString(0));

                    eventsM.add(EM.getActivitySubject());
                } while (cursor.moveToNext());
            }
            // close database transaction
            //  db.close();
            return eventsM;
        }catch (Exception ex){

        }
        return null ;
    }
    //-----------------------------Cases activity spinner----------------------------------------------------------------
    public List<String> getAllCasesActivities( ) {
        List<String> eventsM = new LinkedList<String>();

        // select Event query
        String query = "SELECT  activitySubject  FROM "+ table_Activity;
try {
    // get reference of the EventDB database
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);

    // parse all results
    ActivityModel EM = null;
    String em = null;
    if (cursor.moveToFirst()) {
        do {

            EM = new ActivityModel();

            EM.setActivitySubject(cursor.getString(0));

            eventsM.add(EM.getActivitySubject());
        } while (cursor.moveToNext());
    }
    // close database transaction
    //  db.close();
    return eventsM;
}catch (Exception ex){

}
        return null ;
    }
    //-----------------------------update id for acivity----------------------------------------------------------------
    public String getAllActivityID(String actType) {
        String eventsM = null;

        // select Event query
        String query = "SELECT  activityId FROM "+table_Activity +" where activitySubject = '"+actType+"' ";

        // get reference of the EventDB database
        SQLiteDatabase db=null;
                db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        ActivityModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new ActivityModel();

                EM.setActivityId(cursor.getString(0));

                eventsM =EM.getActivityId();
            } while (cursor.moveToNext());
        }
        // close database transaction
      //  db.close();
        return eventsM ;
    }
    //-----------------------------update id for lead----------------------------------------------------------------
    public ActivityModel getActivityLeadID(String leadId) {
        ActivityModel eventsM = new ActivityModel();

        // select Event query
        String query = "SELECT  actRelatdLead FROM "+table_Activity +" where activitySubject = '"+leadId+"' ";

        // get reference of the EventDB database
        SQLiteDatabase db=null;
        db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        ActivityModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new ActivityModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setActivitySubject(cursor.getString(1));
                EM.setActivityStartdate(cursor.getString(2));
                EM.setStartHour(cursor.getString(3));
                EM.setStartMinute(cursor.getString(4));
                EM.setDurationHours(cursor.getString(5));
                EM.setActivityType(cursor.getString(6));
                EM.setActivityStatus(cursor.getString(7));
                EM.setRelatedLead(cursor.getString(8));
                EM.setActivityId(cursor.getString(9));
                EM.setActRelatdLead(cursor.getString(10));
                EM.setLeadStatus(cursor.getString(11));
                EM.setDescription(cursor.getString(12));
                EM.setAssigeTo(cursor.getString(13));

                eventsM =EM;
            } while (cursor.moveToNext());
        }
        // close database transaction
       // db.close();
        return eventsM ;
    }
    public List<ActivityModel> getAllActivities(String Type) {
        List<ActivityModel> eventsM = new LinkedList<ActivityModel>();

        // select Event query
        String query = "SELECT  * FROM "+ table_Activity +" where activityType = '"+Type+"' "; ;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        ActivityModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new ActivityModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setActivitySubject(cursor.getString(1));
                EM.setActivityStartdate(cursor.getString(2));
                EM.setStartHour(cursor.getString(3));
                EM.setStartMinute(cursor.getString(4));
                EM.setDurationHours(cursor.getString(5));
                EM.setActivityType(cursor.getString(6));
                EM.setActivityStatus(cursor.getString(7));
                EM.setRelatedLead(cursor.getString(8));
                EM.setActivityId(cursor.getString(9));
                EM.setActRelatdLead(cursor.getString(10));
                EM.setLeadStatus(cursor.getString(11));
                EM.setDescription(cursor.getString(12));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }
        // close database transaction
     //   db.close();

        return eventsM ;
    }
    public List<ActivityModel> geEventActivity(String Subject) {
        List<ActivityModel> eventsM = new LinkedList<ActivityModel>();

        // select Event query
        String query = "SELECT  * FROM "+ table_Activity +" where activitySubject = '"+Subject+"' "; ;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        ActivityModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new ActivityModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setActivitySubject(cursor.getString(1));
                EM.setActivityStartdate(cursor.getString(2));
                EM.setStartHour(cursor.getString(3));
                EM.setStartMinute(cursor.getString(4));
                EM.setDurationHours(cursor.getString(5));
                EM.setActivityType(cursor.getString(6));
                EM.setActivityStatus(cursor.getString(7));
                EM.setRelatedLead(cursor.getString(8));
                EM.setActivityId(cursor.getString(9));
                EM.setActRelatdLead(cursor.getString(10));
                EM.setLeadStatus(cursor.getString(11));
                EM.setDescription(cursor.getString(12));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }
    public List<ActivityModel> getActivitiesByKey(String SearchKey) {
        List<ActivityModel> eventsM = new LinkedList<ActivityModel>();
        // select Event query
        String query = "SELECT  * FROM "+ table_Activity +" where activitySubject LIKE '"+SearchKey+"%' "; ;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        ActivityModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new ActivityModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setActivitySubject(cursor.getString(1));
                EM.setActivityStartdate(cursor.getString(2));
                EM.setStartHour(cursor.getString(3));
                EM.setStartMinute(cursor.getString(4));
                EM.setDurationHours(cursor.getString(5));
                EM.setActivityType(cursor.getString(6));
                EM.setActivityStatus(cursor.getString(7));
                EM.setRelatedLead(cursor.getString(8));
                EM.setActivityId(cursor.getString(9));
                EM.setActRelatdLead(cursor.getString(10));
                EM.setLeadStatus(cursor.getString(11));
                EM.setDescription(cursor.getString(12));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }
        // close database transaction
      //  db.close();
        return eventsM ;
    }
//---------------------------------search--------------------------------------
public List<ActivityModel> getActivityTypes(String actType) {
    List<ActivityModel> eventsM = new LinkedList<ActivityModel>();

    // select Event query
    String query = "SELECT  activityType FROM "+table_Activity +"ORDER BY id " ;

    // get reference of the EventDB database
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);

    // parse all results
    ActivityModel EM = null;
    if (cursor.moveToFirst()) {
        do {

            EM = new ActivityModel();
         //   EM.setId(Integer.parseInt(cursor.getString(0)));
            EM.setActivityType(cursor.getString(6));

            eventsM .add(EM);
        } while (cursor.moveToNext());
    }
    // close database transaction
 //   db.close();
    return eventsM ;
}
//-----------------------------------------------------------------------
    public List<ActivityModel> getAllActivities( ) {
        List<ActivityModel> eventsM = new LinkedList<ActivityModel>();

        // select Event query
        String query = "SELECT  * FROM "+ table_Activity;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        ActivityModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new ActivityModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setActivitySubject(cursor.getString(1));
                EM.setActivityStartdate(cursor.getString(2));
                EM.setStartHour(cursor.getString(3));
                EM.setStartMinute(cursor.getString(4));
                EM.setDurationHours(cursor.getString(5));
                EM.setActivityType(cursor.getString(6));
                EM.setActivityStatus(cursor.getString(7));
                EM.setRelatedLead(cursor.getString(8));
                EM.setActivityId(cursor.getString(9));
                EM.setActRelatdLead(cursor.getString(10));
                EM.setLeadStatus(cursor.getString(11));
                EM.setDescription(cursor.getString(12));
                EM.setAssigeTo(cursor.getString(13));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }

    public List<ActivityModel> getChartActivity(String month ) {
        List<ActivityModel> eventsM = new LinkedList<ActivityModel>();

        // select Event query
        String query = "SELECT  * FROM "+ table_Activity +" where strftime('%m', activityStartdate) = '"+month+"'";

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        ActivityModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new ActivityModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setActivitySubject(cursor.getString(1));
                EM.setActivityStartdate(cursor.getString(2));
                EM.setStartHour(cursor.getString(3));
                EM.setStartMinute(cursor.getString(4));
                EM.setDurationHours(cursor.getString(5));
                EM.setActivityType(cursor.getString(6));
                EM.setActivityStatus(cursor.getString(7));
                EM.setRelatedLead(cursor.getString(8));
                EM.setActivityId(cursor.getString(9));
                EM.setActRelatdLead(cursor.getString(10));
                EM.setLeadStatus(cursor.getString(11));
                EM.setDescription(cursor.getString(12));
                EM.setAssigeTo(cursor.getString(13));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }
        // close database transaction
      //  db.close();
        return eventsM ;
    }


    // Deleting single Event
    public void deleteEvent(ActivityModel EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_Activity, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }
        // update  event
    public void updateEvent(ActivityModel EM) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(activitySubject,EM.getActivitySubject());
        contentValues.put(activityStartdate, EM.getActivityStartdate());
        contentValues.put(startHour,EM.getStartHour());
        contentValues.put(startMinute, EM.getStartMinute());
        contentValues.put(durationHours,EM.getDurationHours());
        contentValues.put(activityType,EM.getActivityType());
        contentValues.put(activityStatus,EM.getActivityStatus());
        contentValues.put(actRelatdLead,EM.getActRelatdLead());
        contentValues.put(relatedLead,EM.getRelatedLead());
        contentValues.put(activityId,EM.getActivityId());
        contentValues.put(leadStatus,EM.getLeadStatus());
        contentValues.put(description,EM.getDescription());
        contentValues.put(assigeTo,EM.getAssigeTo());
        // close database transaction
      //  db1.close();
        db1.update(table_Activity, contentValues,"ID=?",new String[] {String.valueOf(EM.getId())});
  //      db1.close();
    }

    // insert  event
    public void insertEvent(ActivityModel EM) {

        listFlagInsert=true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(ID, EM.getId());
        contentValues.put(activityId, EM.getActivityId());
        contentValues.put(activitySubject,EM.getActivitySubject());
        contentValues.put(activityStartdate, EM.getActivityStartdate());
        contentValues.put(startHour,EM.getStartHour());
        contentValues.put(startMinute, EM.getStartMinute());
        contentValues.put(durationHours,EM.getDurationHours());
        contentValues.put(activityType,EM.getActivityType());
        contentValues.put(activityStatus,EM.getActivityStatus());
        contentValues.put(relatedLead,EM.getRelatedLead());
        contentValues.put(actRelatdLead,EM.getActRelatdLead());
        contentValues.put(leadStatus,EM.getLeadStatus());
        contentValues.put(description,EM.getDescription());
        contentValues.put(assigeTo,EM.getAssigeTo());

        // close database transaction
       // return db.insert(table_Activity, null,contentValues);
        db.insert(table_Activity, null,contentValues);

  //      db.close();
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
