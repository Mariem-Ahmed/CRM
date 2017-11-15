package com.example.afaf.enterprisecrm.Helper_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.afaf.enterprisecrm.Helper_Database.relatedLead_Model;
import com.example.afaf.enterprisecrm.leadsModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by enterprise on 11/10/16.
 */

public class JCGSQLiteHelper_Leads extends SQLiteOpenHelper {

    public static Boolean listFlagInsert=false;
    // database version
    private static final int database_VERSION = 1;
    // database name
    private static final String database_NAME = "Leads.db";
    private static final String table_Leads = "leads";
    private static final String ID = "id";
    private static final String leadId = "leadId";
   // private static final String LastName = "LastName";
    private static final String CommertialName = "CommertialName";
    private static final String Phone = "Phone";
    private static final String Email = "Email";
    private static final String Comment = "Comment";
    private static final String interestedIn = "interestedIn";
    private static final String Title = "Title";
 //   private static final String LeadScore = "LeadScore";
    private static final String LeadSource = "LeadSource";
  //  private static final String LeadStage = "LeadStage";
    private static final String CurrentAddress = "CurrentAddress";
    private static final String AssignedToID = "AssignedToID";
   // private static final String LeadURL = "LeadURL";
 //   private static final String Annualrevenue = "Annualrevenue";
   // private static final String BusinessPartner = "BusinessPartner";
   // private static final String Profile = "Profile";
   // private static final String SalesAgent = "SalesAgent";
   // private static final String RejectionReasons = "RejectionReasons";
//    private static final String NumOfEmployee = "NumOfEmployee";
//    private static final String LeadsNeeds = "LeadsNeeds";
    private static final String AssignedTo = "AssignedTo";
    private static final String Status = "Status";
//    private static final String Position = "Position";
//    private static final String ImplementStrategy = "ImplementStrategy";
//    private static final String EvaluationTime = "EvaluationTime";
//    private static final String ExpectedAnnualRevenu = "ExpectedAnnualRevenu";
    private static final String Em_Opcrm_Project_id = "Em_Opcrm_Project_id";
    private static final String Em_Opcrm_Round_id = "Em_Opcrm_Round_id";
//    private static final String AttendPlace = "AttendPlace";
 // private static final String Em_Opcrm_Current_Address = "Em_Opcrm_Current_Address";
//    private static final String Em_Opcrm_fin_Pay_Id = "Em_Opcrm_fin_Pay_Id";
    private static final String Nationality = "Nationality";



    private static final String[] COLUMNS = { ID,CommertialName,Phone ,Email,
            Comment,LeadSource,CurrentAddress,AssignedTo,
            Status,interestedIn,leadId, Title, Em_Opcrm_Project_id, Em_Opcrm_Round_id, Nationality, AssignedToID};

    public JCGSQLiteHelper_Leads(Context context) {
        super(context, database_NAME, null, database_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Event table
        String CREATE_Event_TABLE = "CREATE TABLE leads ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CommertialName TEXT, "+"Phone TEXT, "+"Email TEXT, "+
                "Comment TEXT, "+"LeadSource TEXT,"+
                "CurrentAddress TEXT," +
                " "+"  AssignedTo TEXT ," +
                ""+"Status TEXt , "+"interestedIn TEXT," +
                " "+"leadId TEXT, "+"Title TEXT, "+"Em_Opcrm_Project_id TEXT," +
                " "+"Em_Opcrm_Round_id TEXT, "+"Nationality TEXT, "+"AssignedToID TEXT)";
        db.execSQL(CREATE_Event_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+table_Leads);
        this.onCreate(db);

    }

    public void createLead(  String CommertialName_, String Phone_ , String Email_
            ,String Comment_, String LeadSource_, String CurrentAddress_
            , String AssignedTo_ , String Status_,String interestedIn_, String leadId_, String Title_
            , String Em_Opcrm_Project_id_, String Em_Opcrm_Round_id_, String Nationality_, String AssignedToID_) {
        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // make values to be inserted
        ContentValues values = new ContentValues();
        //values.put(FirstName,FirstName_ );
       // values.put( LastName,LastName_);
        values.put( CommertialName, CommertialName_);
        values.put(Phone , Phone_ );
        values.put( Email, Email_ );
        values.put( Comment, Comment_ );
        values.put(Title , Title_ );
       // values.put(LeadScore,LeadScore_ );
        values.put( LeadSource,LeadSource_);

        values.put( AssignedToID,AssignedToID_);
        values.put(CurrentAddress , CurrentAddress_ );
       // values.put( Fax,Fax_ );
      //  values.put( LeadURL, LeadURL_ );
      //  values.put( Annualrevenue,Annualrevenue_  );
      //  values.put(BusinessPartner , BusinessPartner_ );

       // values.put(Profile,Profile_ );
      //  values.put( SalesAgent,SalesAgent_);
       // values.put( RejectionReasons, RejectionReasons_);
       // values.put(NumOfEmployee , NumOfEmployee_ );
       // values.put( LeadsNeeds, LeadsNeeds_ );
        values.put( AssignedTo, AssignedTo_ );
        values.put( Status,Status_  );
        values.put( interestedIn,interestedIn_ );
        values.put( leadId,leadId_ );
      //  values.put(Position , Position_ );
      //  values.put(ImplementStrategy,ImplementStrategy_ );
       // values.put( EvaluationTime,EvaluationTime_);
       // values.put( ExpectedAnnualRevenu,ExpectedAnnualRevenu_);
        values.put(Em_Opcrm_Project_id ,Em_Opcrm_Project_id_ );
        values.put( Em_Opcrm_Round_id,Em_Opcrm_Round_id_ );
      //  values.put( AttendPlace, AttendPlace_ );
      ////  values.put( Em_Opcrm_Current_Address,Em_Opcrm_Current_Address_  );
      //  values.put(Em_Opcrm_fin_Pay_Id , Em_Opcrm_fin_Pay_Id_ );
       values.put(Nationality , Nationality_ );

       // String xcf= FirstName_;
        // insert Event
        db.insert(table_Leads, null,  values);

        // close database transaction
        db.close();
    }

    public leadsModel readLead(int id) {
        // get reference of the EventDB database
        listFlagInsert=false;
        SQLiteDatabase db = this.getReadableDatabase();

        // get Event query
        Cursor cursor = db.query(table_Leads, // a. table
                COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);

        // if results !=null, parse the first one
        if (cursor != null)
            cursor.moveToFirst();

        leadsModel EM = new leadsModel();
        EM.setId(Integer.parseInt(cursor.getString(0)));
        EM.setCommertialName(cursor.getString(1));
        EM.setPhone(cursor.getString(2));
        EM.setEmail(cursor.getString(3));
        EM.setComment(cursor.getString(4));
        EM.setLeadSource(cursor.getString(5));
        EM.setCurrentAddress(cursor.getString(6));
        EM.setAssignedTo(cursor.getString(7));
        EM.setStatus(cursor.getString(8));
        EM.setInterestedIn(cursor.getString(9));
        EM.setLeadId(cursor.getString(10));
        EM.setTitle(cursor.getString(11));
        EM.setEm_Opcrm_Project_id(cursor.getString(12));
        EM.setEm_Opcrm_Round_id(cursor.getString(13));
        EM.setNationality(cursor.getString(14));
        EM.setAssignedToID(cursor.getString(15));

      //  cursor.close();
        return EM;
    }

// ----------------------- get lead with this id -------------------------------
public List<leadsModel> getLeadWithId(String id) {
    List<leadsModel> eventsM = new LinkedList<>();

    // select Event query
    String query = "SELECT  * FROM "+table_Leads +" where leadId = '"+id+"' ";

    // get reference of the EventDB database
    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(query, null);

    // parse all results
    leadsModel EM = null;
    if (cursor.moveToFirst()) {
        do {

            EM = new leadsModel();
            EM.setId(Integer.parseInt(cursor.getString(0)));
            EM.setCommertialName(cursor.getString(1));
            EM.setPhone(cursor.getString(2));
            EM.setEmail(cursor.getString(3));
            EM.setComment(cursor.getString(4));
            EM.setLeadSource(cursor.getString(5));
            EM.setCurrentAddress(cursor.getString(6));
            EM.setAssignedTo(cursor.getString(7));
            EM.setStatus(cursor.getString(8));
            EM.setInterestedIn(cursor.getString(9));
            EM.setLeadId(cursor.getString(10));
            EM.setTitle(cursor.getString(11));
            EM.setEm_Opcrm_Project_id(cursor.getString(12));
            EM.setEm_Opcrm_Round_id(cursor.getString(13));
            EM.setNationality(cursor.getString(14));
            EM.setAssignedToID(cursor.getString(15));

            eventsM .add(EM);
        } while (cursor.moveToNext());
    }

    return eventsM ;
}
    public List<relatedLead_Model> getAllRLeads() {
        List<relatedLead_Model> eventsM = new LinkedList<relatedLead_Model>();

        // select Event query
        String query = "SELECT  id , CommertialName , LeadId FROM "+table_Leads;

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        relatedLead_Model EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new relatedLead_Model();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setrLeadName(cursor.getString(1));
                EM.setrLeadId(cursor.getString(2));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }

// -------------------------------------------------------------------------------

    public List<leadsModel> getLeadByName(String Name) {
        List<leadsModel> eventsM = new LinkedList<>();

        // select Event query
        String query = "SELECT  * FROM "+table_Leads+" where CommertialName = '"+Name+"' ";

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        leadsModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new leadsModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setCommertialName(cursor.getString(1));
                EM.setPhone(cursor.getString(2));
                EM.setEmail(cursor.getString(3));
                EM.setComment(cursor.getString(4));
                EM.setLeadSource(cursor.getString(5));
                EM.setCurrentAddress(cursor.getString(6));
                EM.setAssignedTo(cursor.getString(7));
                EM.setStatus(cursor.getString(8));
                EM.setInterestedIn(cursor.getString(9));
                EM.setLeadId(cursor.getString(10));
                EM.setTitle(cursor.getString(11));
                EM.setEm_Opcrm_Project_id(cursor.getString(12));
                EM.setEm_Opcrm_Round_id(cursor.getString(13));
                EM.setNationality(cursor.getString(14));
                EM.setAssignedToID(cursor.getString(15));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }
    public List<leadsModel> getLeadByPhone(String Phone) {
        List<leadsModel> eventsM = new LinkedList<>();

        // select Event query
        String query = "SELECT  * FROM "+table_Leads+" where Phone = '"+Phone+"' ";

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // parse all results
        leadsModel EM = null;
        if (cursor.moveToFirst()) {
            do {

                EM = new leadsModel();
                EM.setId(Integer.parseInt(cursor.getString(0)));
                EM.setCommertialName(cursor.getString(1));
                EM.setPhone(cursor.getString(2));
                EM.setEmail(cursor.getString(3));
                EM.setComment(cursor.getString(4));
                EM.setLeadSource(cursor.getString(5));
                EM.setCurrentAddress(cursor.getString(6));
                EM.setAssignedTo(cursor.getString(7));
                EM.setStatus(cursor.getString(8));
                EM.setInterestedIn(cursor.getString(9));
                EM.setLeadId(cursor.getString(10));
                EM.setTitle(cursor.getString(11));
                EM.setEm_Opcrm_Project_id(cursor.getString(12));
                EM.setEm_Opcrm_Round_id(cursor.getString(13));
                EM.setNationality(cursor.getString(14));
                EM.setAssignedToID(cursor.getString(15));

                eventsM .add(EM);
            } while (cursor.moveToNext());
        }

        return eventsM ;
    }


    public List<leadsModel> getAllLeads() {
        try {
            List<leadsModel> eventsM = new LinkedList<>();

            // select Event query
            String query = "SELECT  * FROM " + table_Leads;
            //+ " ORDER BY id "

            // get reference of the EventDB database
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            // parse all results
            leadsModel EM = null;
            if (cursor.moveToFirst()) {
                do {

                    EM = new leadsModel();
                    EM.setId(Integer.parseInt(cursor.getString(0)));
                    EM.setCommertialName(cursor.getString(1));
                    EM.setPhone(cursor.getString(2));
                    EM.setEmail(cursor.getString(3));
                    EM.setComment(cursor.getString(4));
                    EM.setLeadSource(cursor.getString(5));
                    EM.setCurrentAddress(cursor.getString(6));
                    EM.setAssignedTo(cursor.getString(7));
                    EM.setStatus(cursor.getString(8));
                    EM.setInterestedIn(cursor.getString(9));
                    EM.setLeadId(cursor.getString(10));
                    EM.setTitle(cursor.getString(11));
                    EM.setEm_Opcrm_Project_id(cursor.getString(12));
                    EM.setEm_Opcrm_Round_id(cursor.getString(13));
                    EM.setNationality(cursor.getString(14));
                    EM.setAssignedToID(cursor.getString(15));

                    eventsM.add(EM);
                } while (cursor.moveToNext());

                //   cursor.close();
            }
            return eventsM ;
        }
        catch (Exception ex){

        }
        return null ;
    }

    public List<leadsModel> getAllLeads(String searchKey) {
        try {
            List<leadsModel> eventsM = new LinkedList<>();

            // select Event query
            String query = "SELECT  * FROM " + table_Leads +  " where CommertialName LIKE '"+searchKey+"%' ";;
            //+ " ORDER BY id "

            // get reference of the EventDB database
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            // parse all results
            leadsModel EM = null;
            if (cursor.moveToFirst()) {
                do {

                    EM = new leadsModel();
                    EM.setId(Integer.parseInt(cursor.getString(0)));
                    EM.setCommertialName(cursor.getString(1));
                    EM.setPhone(cursor.getString(2));
                    EM.setEmail(cursor.getString(3));
                    EM.setComment(cursor.getString(4));
                    EM.setLeadSource(cursor.getString(5));
                    EM.setCurrentAddress(cursor.getString(6));
                    EM.setAssignedTo(cursor.getString(7));
                    EM.setStatus(cursor.getString(8));
                    EM.setInterestedIn(cursor.getString(9));
                    EM.setLeadId(cursor.getString(10));
                    EM.setTitle(cursor.getString(11));
                    EM.setEm_Opcrm_Project_id(cursor.getString(12));
                    EM.setEm_Opcrm_Round_id(cursor.getString(13));
                    EM.setNationality(cursor.getString(14));
                    EM.setAssignedToID(cursor.getString(15));

                    eventsM.add(EM);
                } while (cursor.moveToNext());

                //   cursor.close();
            }
            return eventsM ;
        }
        catch (Exception ex){

        }
        return null ;
    }

    // Deleting single Event
    public void deleteEvent(leadsModel EM) {

        // get reference of the EventDB database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete Event
        db.delete(table_Leads, ID + " = ?", new String[] { String.valueOf(EM.getId()) });
        db.close();
    }

    public void updateEvent(leadsModel EM) {
        // , String activity_Subject, String activity_Startdate, String start_Hour, String start_Minute, String duration_Hours
        // ,String activity_Type, String activity_Status, String related_Lead

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(CommertialName,EM.getCommertialName());
        contentValues.put(Phone, EM.getPhone());
        contentValues.put(Email,EM.getEmail());
        contentValues.put(Comment, EM.getComment());
        contentValues.put(LeadSource,EM.getLeadSource());
        contentValues.put(CurrentAddress,EM.getCurrentAddress());
        contentValues.put(AssignedTo,EM.getAssignedTo());
        contentValues.put(Status,EM.getStatus());
        contentValues.put(interestedIn,EM.getInterestedIn());
        contentValues.put(Title, EM.getTitle());
        contentValues.put(Em_Opcrm_Project_id, EM.getEm_Opcrm_Project_id());
        contentValues.put(Em_Opcrm_Round_id, EM.getEm_Opcrm_Round_id());
        contentValues.put(Nationality, EM.getNationality());
        contentValues.put(AssignedToID, EM.getAssignedToID());
        contentValues.put(leadId,EM.getLeadId());

        db1.update(table_Leads, contentValues,"ID=?",new String[] {String.valueOf(EM.getId())});
        db1.close();
    }

    // insert  event
    public void insertEvent(leadsModel EM) {

          listFlagInsert=true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(ID, EM.getId());
        contentValues.put(CommertialName,EM.getCommertialName());
        contentValues.put(Phone, EM.getPhone());
        contentValues.put(Email,EM.getEmail());
        contentValues.put(Comment, EM.getComment());
        contentValues.put(LeadSource,EM.getLeadSource());
        contentValues.put(CurrentAddress,EM.getCurrentAddress());
        contentValues.put(AssignedTo,EM.getAssignedTo());
        contentValues.put(Status,EM.getStatus());
        contentValues.put(interestedIn,EM.getInterestedIn());
        contentValues.put(Title, EM.getTitle());
        contentValues.put(Em_Opcrm_Project_id, EM.getEm_Opcrm_Project_id());
        contentValues.put(Em_Opcrm_Round_id, EM.getEm_Opcrm_Round_id());
        contentValues.put(Nationality, EM.getNationality());
        contentValues.put(AssignedToID, EM.getAssignedToID());
        contentValues.put(leadId,EM.getLeadId());

        db.insert(table_Leads, null,contentValues);
        db.close();
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}
