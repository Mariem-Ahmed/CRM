package com.example.afaf.enterprisecrm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Leads;
import com.example.afaf.enterprisecrm.Helper_Database.activity_Status_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.activity_Type_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.leadStatus_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.activity_Status_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.activity_Type_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.leadStatus_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.relatedLead_Model;

import org.json.JSONArray;
import org.json.JSONException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;


/**
 * Created by enterprise on 25/09/16.
 */
public class acitvity extends AppCompatActivity  {
     int activityId;

    String actID= null;
   public static String  LeadId= null;
    public static ActivityModel Em= new ActivityModel();

    // ----------------- activity date && time --------------
    static TextView start;
    static TextView duration;
    public static EditText starttime;
    public static EditText durationtime;
    // ----------------------------------
    static TextView startd;
    static TextView end;
    public static EditText startdate;
    static TextView enddate;
    static  boolean  clickStartTimeFlag=false;
    static boolean clickDurationTimeFlag=false;
    static  boolean  clickStartDateFlag=false;
    static boolean clickEnddateFlag=false;
   public static EditText actname, desc;
    public static Spinner actspinner, statusspinner, relead, leadstatusSpinner, assigeToSpinner;
    int x = 0;
    int y = 0;
    int z, a, f = 0;


    String uuid="";

    //------------------- url -------------------------------------------------------------
    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;
    String uRl="";
    // ----------------------- log in ------------------------------------------------------
    public static final String LoginPREFERENCES = "LoginPrefs" ;
    public static final String UserName = "username";
    public static final String PassWord = "password";

    SharedPreferences sharedpreferencesLogin;
    String uname="";
    String passw="";
    String userId="";


    public static Boolean fabactivityFlag =false;
    public static String lid;
    public static String lidname;
    public static String actid;
    public static  String aname;


    // requried
    boolean check, cancel = false;
    View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

     //   if (lid==null && lidname==null) {
            Intent ii3 = getIntent();
            lid = ii3.getStringExtra("leadidid");
            lidname = ii3.getStringExtra("ComName");
     //   }
      //  if (actid==null && aname==null) {
            Intent ii4 = getIntent();
            actid = ii4.getStringExtra("actidid");
            aname = ii4.getStringExtra("subName");
      //  }


//-----------------------------url &  login  ------------------------------------------
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl= sharedpreferences.getString("URL",null);
        if (uRl != null) {
            sharedpreferencesLogin = getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
            uname = sharedpreferencesLogin.getString("uName", null);
            passw = sharedpreferencesLogin.getString("passWord", null);
            userId=sharedpreferencesLogin.getString("userId", null);
            if (uname == null && passw == null) {
                Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i1);
                finish();
            }
        }
        else{
            Intent i = new Intent(getApplicationContext(), insertUrl.class);
            startActivity(i);
            //finish();
        }
// ------------------------------------------------------------------------------------------------
        if(Main5Activity.editflag==true ) {
            if (LoginActivity.arabicflag == true) {
                setTitle("تعديل نشاط");
            } else if (insertUrl.arflagURL == true) {
                setTitle("تعديل نشاط");
            } else {
                setTitle("Edit Activity");
            }
        }else{
            if (LoginActivity.arabicflag == true) {
                setTitle("اضافه نشاط");
            } else if (insertUrl.arflagURL == true) {
                setTitle("اضافه نشاط");
            } else {
                setTitle("Add Activity");
            }
        }
        actname= (EditText) findViewById(R.id.editText4);
        actname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

              actname.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        desc= (EditText) findViewById(R.id.editTextdesc);


        relead= (Spinner) findViewById(R.id.relatedLeadSpinner);
        // change color of selected item
        relead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        actspinner= (Spinner) findViewById(R.id.activityTypeSpinner);
        actspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
                     ((TextView) view).setTextSize(5);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        statusspinner= (Spinner) findViewById(R.id.statusactSpinner);
        statusspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        leadstatusSpinner= (Spinner) findViewById(R.id.leadstatusSpinner);
        leadstatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        assigeToSpinner= (Spinner) findViewById(R.id.assignetosSpinner);
        assigeToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        starttime=(EditText)findViewById(R.id.textstarttime); // edit
        starttime.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                starttime.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          //      starttime.setError(getString(R.string.error_field_required));
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        durationtime=(EditText)findViewById(R.id.textdurationtime); // edit
        durationtime.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                durationtime.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        startdate=(EditText)findViewById(R.id.textstartdate); // edit text
        startdate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                startdate.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });


        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view);
                clickStartTimeFlag=true;
                clickDurationTimeFlag=false;
            }
        });
        durationtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view);
                clickDurationTimeFlag=true;
                clickStartTimeFlag=false;
            }
        });

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
                clickStartDateFlag=true;
                clickEnddateFlag=false;
            }
        });


        // --------------------------------------activity type spinner----------------------------------------------------------------
        Intent i = getIntent();


        // ----------------------------------------------------------
        String idName = i.getStringExtra("activityId");
        actID=idName;

        String idLead = i.getStringExtra("LeadId");
        LeadId=idLead;
        // ----------------------------------------------assignedTo  spinner------------------------------------------------------
        String assigned = i.getStringExtra("AssignedTo");

        assignedTo_spinner_helper dba = new assignedTo_spinner_helper(this);
        assignedTo_spinner_model assignedLists = null;
        List<String> lista= new ArrayList<String>();
        for (int ii1 = 0; ii1 < dba.getAllAssignedTo().size(); ii1++) {
            try {
                assignedLists = dba.readAssignedToSpinner(ii1+1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            lista.add(assignedLists.getUserName());
            if (assignedLists.getUserId().equals(assigned)||assignedLists.getUserId().equals(userId))
                f = ii1;
        }


        ArrayAdapter<String> spinnerAdaptera = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        spinnerAdaptera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assigeToSpinner.setAdapter(spinnerAdaptera);
        assigeToSpinner.setSelection(f);

// ----------------------- activity type -----------------------------------
        String acttype = i.getStringExtra("acttype");
        activity_Type_spinner_helper db = new activity_Type_spinner_helper(this);
        activity_Type_spinner_model spinnerLists = null;
        List<String> list = new ArrayList<String>();
        for (int ii = 0; ii < db.getAllActivityType().size(); ii++) {
            try {
                spinnerLists = db.readActivitySpinner(ii+1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            list.add(spinnerLists.getActivitykey());
            if (spinnerLists.getActivitykey().toString().equals(acttype))
                x = ii+1;


        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actspinner.setAdapter(spinnerAdapter);
        // add activity status
        // ------------------------------- activity status spinner--------------------------------------------------------------------
        final String actstatus = i.getStringExtra("actstatus");
        actspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                activity_Status_spinner_helper db1 = new activity_Status_spinner_helper(getApplicationContext());
                activity_Status_spinner_model spinnerLists1 = null;
                List<String> list1 = new ArrayList<String>();
                list1.add(0," ");
                for (int i1 = 1; i1 < db1.getAllActivityStatus().size(); i1++) {
                    try {
                        spinnerLists1 = db1.readActivityStatusSpinner(i1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String s=actspinner.getSelectedItem().toString();

                    if (spinnerLists1.getActivityStatusSearchKey().equals(s)) {
                        list1.add(spinnerLists1.getActivityStatusname());

                        if (spinnerLists1.getActivityStatusId().toString().equals(actstatus))
                            y = i1+1;
                    }
                }
                ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list1);
                spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                statusspinner.setAdapter(spinnerAdapter1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


// ------------------------------- related Lead spinner--------------------------------------------------------------------
        String leadName = i.getStringExtra("relatedlead");

        if (Main4Activity.fabcasesFlag == true) {
            JCGSQLiteHelper_Leads db3 = new JCGSQLiteHelper_Leads(this);
              relatedLead_Model rleadLists1 = null;
            List<String> list3 = new ArrayList<String>();
            for (int i1 = 0; i1 < db3.getAllRLeads().size(); i1++) {
                    rleadLists1 = db3.getAllRLeads().get(i1);
                    list3.add(rleadLists1.getrLeadName());
                if (rleadLists1.getrLeadId().toString().equals(lead_info_activity.leadid))
                    z = i1+1;
            }
            ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list3);
            spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            relead.setAdapter(spinnerAdapter3);
            relead.setSelection(z-1);

        }else {

            JCGSQLiteHelper_Leads db3 = new JCGSQLiteHelper_Leads(this);
            relatedLead_Model rleadLists1 = null;
            List<String> list3 = new ArrayList<String>();
            list3.add(0," ");
            for (int i1 = 0; i1 < db3.getAllRLeads().size(); i1++) {
                rleadLists1 = db3.getAllRLeads().get(i1);
                list3.add(rleadLists1.getrLeadName());
                if (rleadLists1.getrLeadId().toString().equals(LeadId))
                    z = i1+1;
            }
            ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list3);
            spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            relead.setAdapter(spinnerAdapter3);
        }
// ------------------------------- related Lead status spinner--------------------------------------------------------------------
        String leadStatus = i.getStringExtra("leadStatus");

        leadStatus_spinner_helper dbLS = new leadStatus_spinner_helper(this);
        leadStatus_spinner_model leadStatusLists1 = null;
        List<String> listLs = new ArrayList<String>();
        for (int ii = 0; ii <dbLS.getAllLeadStatus().size(); ii++) {
            try {
                leadStatusLists1= dbLS.readLeadStatusSpinner(ii+1);
            } catch (JSONException e) {

            }
            listLs.add(leadStatusLists1.getLeadStatusName());
            if (leadStatusLists1.getLeadStatusValue().equals(leadStatus))
                a = ii;
        }
        ArrayAdapter<String> spinnerAdapterLS = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listLs);
        spinnerAdapterLS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leadstatusSpinner.setAdapter(spinnerAdapterLS);
// --------------------------------------- set data -----------------------------
          if(Main5Activity.editflag==true) {
              activityId = Integer.parseInt(Main5Activity.idactlocal);

    String subName = i.getStringExtra("subName");
    actname.setText(subName);

    String description = i.getStringExtra("description");
    desc.setText(description);



    String sDate = i.getStringExtra("startDate");
    if (  JCGSQLiteHelper.listFlagInsert==true){
        startdate.setText(sDate);
    }else if(sDate!=null){

        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = output.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millisecond = d.getTime();

        String currentDate = getDate(millisecond, "yyyy-MM-dd");

        startdate.setText(currentDate);
    }else if (sDate==null){
            startdate.setText(sDate);
        }


    String houemin = i.getStringExtra("hour");
    String min = i.getStringExtra("minute");
    starttime.setText(houemin+":"+min);



    String dura = i.getStringExtra("duration");
    durationtime.setText(dura);

    actspinner.setSelection(x-1);
    statusspinner.setSelection(y-1);
    relead.setSelection(z);
    leadstatusSpinner.setSelection(a);

    /////////////////////////////////////////////////////////////

}
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    // Ch

    // Check for requried fields.
   public void check(){

       if (  MainActivity.navFlag==true || MainActivity.fabFlag==true || Main4Activity.fabcasesFlag==true && Main4Activity.editleadflag==false) {
           if (TextUtils.isEmpty(actname.getText())) {
               actname.setError(getString(R.string.error_field_required));
               focusView = actname;
               cancel = true;
           }

           if (TextUtils.isEmpty(starttime.getText())) {
               starttime.setError(getString(R.string.error_field_required));
               focusView = starttime;
               cancel = true;
           }
           if (TextUtils.isEmpty(durationtime.getText())) {
               durationtime.setError(getString(R.string.error_field_required));
               focusView = durationtime;
               cancel = true;
           }
           if (TextUtils.isEmpty(startdate.getText())) {
               startdate.setError(getString(R.string.error_field_required));
               focusView = startdate;
               cancel = true;
           }

       }
     //  return true;
   }



 // -----------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        return true;
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
       //  Em =new ActivityModel();
        if (id==R.id.done){

              if (Main5Activity.editflag==true) {

                  String subname = actname.getText().toString();
                  String leadname = relead.getSelectedItem()+""; // spinner
                  String sdate = startdate.getText().toString();
                  String stime = starttime.getText().toString();
                  String dtime = durationtime.getText().toString();
                  String statusspin = statusspinner.getSelectedItem()+"";
                  String actSpin = actspinner.getSelectedItem()+"";
                  String leadS = leadstatusSpinner.getSelectedItem()+"";
                  String descr = desc.getText().toString();
                  String as=null;
                  as=assigeToSpinner.getSelectedItem()+"";
                  String actrelatedleadid="";
                  JCGSQLiteHelper_Leads db3 = new JCGSQLiteHelper_Leads(this);
                  relatedLead_Model rleadLists1 = null;
                  for (int i1 = 0; i1 < db3.getAllRLeads().size(); i1++) {
                      rleadLists1 = db3.getAllRLeads().get(i1);
                      if (rleadLists1.getrLeadName().toString().equals(leadname)){
                          actrelatedleadid= rleadLists1.getrLeadId();
                          break;
                      }

                  }

                  Em.setId(activityId);
                  Em.setActivityId(actid);
                  Em.setActivitySubject(subname);
                  Em.setActRelatdLead(actrelatedleadid);
                  Em.setRelatedLead(leadname);
                  Em.setActivityStartdate(sdate);
                  Em.setStartHour(stime);
                  Em.setDurationHours(dtime);
                  Em.setActivityType(actSpin);
                  Em.setActivityStatus(statusspin);
                  Em.setLeadStatus(leadS);
                  Em.setDescription(descr);
                  Em.setAssigeTo(as);

                  JCGSQLiteHelper h = new JCGSQLiteHelper(this);
                  h.updateEvent(Em);

                  AsyncCallWS1 task = new AsyncCallWS1();
                  task.execute();

              }

                          else{



                  String subname = actname.getText().toString();
                  String leadname = relead.getSelectedItem()+""; // spinner
                  String sdate = startdate.getText().toString();
                  String stime = starttime.getText().toString();
                  String dtime = durationtime.getText().toString();

                  // activity spinner
                  String actSpin = null;
                  activity_Type_spinner_helper db = new activity_Type_spinner_helper(this);
                  activity_Type_spinner_model spinnerLists = null;
                  for (int ii = 1; ii < db.getAllActivityType().size(); ii++) {
                      try {
                          spinnerLists = db.readActivitySpinner(ii);
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                      if (spinnerLists.getActivitykey().toString().equals(actspinner.getSelectedItem()+"")) {
                          actSpin=spinnerLists.getActivitykey();
                          break;
                      }
                  }
                  String statusspin = statusspinner.getSelectedItem()+"";

                 // lead status
                  String leadS = null;
                  leadStatus_spinner_helper dbLS = new leadStatus_spinner_helper(this);
                  leadStatus_spinner_model leadStatusLists1 = null;
                  for (int ii = 0; ii < dbLS.getAllLeadStatus().size(); ii++) {
                      try {
                          leadStatusLists1 = dbLS.readLeadStatusSpinner(ii + 1);
                      } catch (JSONException e) {
                      }
                      if (leadStatusLists1.getLeadStatusName().equals(leadstatusSpinner.getSelectedItem()+"")) {
                          leadS = leadStatusLists1.getLeadStatusValue();
                          break;
                      }
                  }

                 // related lead id
                  String actrelatedleadid="";
                  JCGSQLiteHelper_Leads db3 = new JCGSQLiteHelper_Leads(this);
                  relatedLead_Model rleadLists1 = null;
                  for (int i1 = 0; i1 < db3.getAllRLeads().size(); i1++) {
                      rleadLists1 = db3.getAllRLeads().get(i1);
                      if (rleadLists1.getrLeadName().toString().equals(leadname)){
                          actrelatedleadid= rleadLists1.getrLeadId();
                          break;
                      }

                  }
                  String descr = desc.getText().toString();
                  //assigned to
                  String as=null;
                  assignedTo_spinner_helper dba = new assignedTo_spinner_helper(this);
                  assignedTo_spinner_model assignedLists = null;
                  for (int ii = 0; ii < dba.getAllAssignedTo().size(); ii++) {
                      try {
                          assignedLists = dba.readAssignedToSpinner(ii+1);
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  //    if (assignedLists.getUserId().equals(assigeToSpinner.getSelectedItem()+""))
                      if (assignedLists.getUserName().equals(assigeToSpinner.getSelectedItem().toString()))
                          as= assignedLists.getUserName();
                  }
                  StringTokenizer tokenizer = new StringTokenizer(stime,":");
                  String H="",M="";
                  while (tokenizer.hasMoreTokens()){
                      H = tokenizer.nextToken();
                      M = tokenizer.nextToken();
                  }
                  uuid = UUID.randomUUID().toString().replaceAll("-", "");

                  JCGSQLiteHelper h = new JCGSQLiteHelper(this);
                  Em.setId(h.getAllActivities().size()+1);
                  Em.setActivityId(uuid);
                  Em.setActivitySubject(subname);
                  Em.setRelatedLead(leadname);
                  Em.setActRelatdLead(actrelatedleadid);
                  Em.setActivityStartdate(sdate);
                  Em.setStartHour(H);
                  Em.setStartMinute(M);
                  Em.setDurationHours(dtime);
                  Em.setActivityType(actSpin);
                  Em.setActivityStatus(statusspin);
                  Em.setLeadStatus(leadS);
                  Em.setDescription(descr);
                  Em.setAssigeTo(as);

                  aname=subname;

                  // check ----------------
                  check();
                  if (cancel==true) {
                     // cancel=false;
                      check=true;

                  }else{
                      h.insertEvent(Em);
                      check=false;
                      acitvity.AsyncCallWS task = new acitvity.AsyncCallWS();
                      task.execute();

                  }
              }
            // \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            if (check==false) {
                if (Main4Activity.fabcasesFlag == true) {
                    Intent i = new Intent(this, Main4Activity.class);
                    i.putExtra("leadid", lid);
                    i.putExtra("leadname", lidname);
                    startActivity(i);
                    finish();
                    return true;
                } else if (MainActivity.fabFlag == true) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Window", "2");
                    startActivity(i);
                    finish();
                    return true;
                } else if (MainActivity.navFlag == true) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Window", "2");
                    startActivity(i);
                    finish();
                    return true;
                } else if (Main5Activity.editflag == true) {
                    Intent i = new Intent(this, Main5Activity.class);
                    i.putExtra("activityId", actid);
                    i.putExtra("activityname", aname);
                    startActivity(i);
                    finish();
                    return true;
                }
            }
        }

        else if (id == android.R.id.home) {
            if (Main4Activity.fabcasesFlag==true ){
                Intent i = new Intent(this, Main4Activity.class);
                i.putExtra("leadid", lid);
                i.putExtra("leadname",lidname);
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.listFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","2");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.fabFlag==true){
                Intent i = new Intent(this, MainActivity.class);
                   i.putExtra("Window","2");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.navFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","2");
                startActivity(i);
                finish();
                return true;
            }
            else if (Main5Activity.editflag==true ){
                Intent i = new Intent(this, Main5Activity.class);
                i.putExtra("activityId",actid);
                i.putExtra("activityname",aname);
                startActivity(i);
                finish();
                Main5Activity.editflag=false;
                return true;
            }
            }
        else if (id == R.id.close) {
              if (Main4Activity.fabcasesFlag==true ){
                Intent i = new Intent(this, Main4Activity.class);
                  i.putExtra("leadid", lid);
                  i.putExtra("leadname",lidname);
                  i.putExtra("activity","1");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.listFlag==true ){
                  Intent i = new Intent(this, MainActivity.class);
                  i.putExtra("Window","2");
                  startActivity(i);
                  finish();
                  return true;
            }
            else if (MainActivity.fabFlag==true){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","2");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.navFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","2");
                startActivity(i);
                finish();
                return true;
            }
              else if (Main5Activity.editflag==true ){
                  Intent i = new Intent(this, Main5Activity.class);
                  i.putExtra("activityId",actid);
                  i.putExtra("activityname",aname);
                  startActivity(i);
                  finish();
                  return true;
              }

        }
        return true;
        //return super.onOptionsItemSelected(item);
    }

    // --------------------------------------Time ------------------------------
private class AsyncCallWS extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        testInsert("/ws/it.openia.crm.insertActivity?");

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {


    }

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }
}
    private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
           testUpdate("/ws/it.openia.crm.updateActivity?userid="+userId);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
// update connection
    public String testUpdate(String content) {
        try {

            HttpURLConnection conn = createConnection( content,"POST");
            conn.connect();
            ArrayList<Object> list=new ArrayList<Object>();
            list.add(0,actname.getText().toString());

            list.add(1,actID);
            list.add(2,startdate.getText().toString());
            list.add(3,starttime.getText().toString());
            list.add(4,durationtime.getText().toString());

            // activity spinner
            String actSpin = null;
            activity_Type_spinner_helper db = new activity_Type_spinner_helper(this);
            activity_Type_spinner_model spinnerLists = null;
            for (int ii = 1; ii < db.getAllActivityType().size(); ii++) {
                try {
                    spinnerLists = db.readActivitySpinner(ii);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (spinnerLists.getActivitykey().toString().equals(actspinner.getSelectedItem()+"")) {
                    actSpin=spinnerLists.getActivitykey();
                    list.add(5,actSpin);
                }
            }

                list.add(6,statusspinner.getSelectedItem()+"");


            list.add(7, LeadId);
            // leadstatus
            String leadS = null;
            leadStatus_spinner_helper dbLS = new leadStatus_spinner_helper(this);
            leadStatus_spinner_model leadStatusLists1 = null;
            for (int ii = 0; ii < dbLS.getAllLeadStatus().size(); ii++) {
                try {
                    leadStatusLists1 = dbLS.readLeadStatusSpinner(ii + 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (leadStatusLists1.getLeadStatusName().equals(leadstatusSpinner.getSelectedItem()+"")) {
                    leadS = leadStatusLists1.getLeadStatusValue();
                    list.add(8,leadS);
                }
            }
            list.add(9,desc.getText().toString());

            //assigned to
            String as=null;
            assignedTo_spinner_helper dba = new assignedTo_spinner_helper(this);
            assignedTo_spinner_model assignedLists = null;
            for (int ii = 0; ii < dba.getAllAssignedTo().size(); ii++) {
                try {
                    assignedLists = dba.readAssignedToSpinner(ii+1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (assignedLists.getUserName().equals(assigeToSpinner.getSelectedItem()+"")) {
                    as = assignedLists.getUserId().toString();
                    list.add(10, as);
                }
            }

            JSONArray jsArray = new JSONArray(list);


            OutputStream os = conn.getOutputStream();

           String s=jsArray.toString();


            os.write(s.getBytes());
                os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }
    // insert connection
    public String testInsert(String content) {
        try {

            HttpURLConnection conn = createConnection( content,"POST");
            conn.connect();
            ArrayList<Object> list=new ArrayList<Object>();
            list.add(0,actname.getText().toString());


            if (!relead.getSelectedItem().toString().equals(" ")) {
                JCGSQLiteHelper_Leads db3 = new JCGSQLiteHelper_Leads(this);
                relatedLead_Model rleadLists1 = null;
                List<String> list3 = new ArrayList<String>();
                list3.add(0, " ");
                for (int i1 = 0; i1 < db3.getAllRLeads().size(); i1++) {
                    rleadLists1 = db3.getAllRLeads().get(i1);
                    list3.add(rleadLists1.getrLeadName());
                    if (rleadLists1.getrLeadName().equals(relead.getSelectedItem() + ""))
                        list.add(1, rleadLists1.getrLeadId());
                }
            }else {
                list.add(1, relead.getSelectedItem() + "");
            }

            list.add(2,startdate.getText().toString());
            list.add(3,starttime.getText().toString());
            list.add(4,durationtime.getText().toString());

            // activity spinner
            String actSpin = null;
            activity_Type_spinner_helper db = new activity_Type_spinner_helper(this);
            activity_Type_spinner_model spinnerLists = null;
            for (int ii = 1; ii < db.getAllActivityType().size(); ii++) {
                try {
                    spinnerLists = db.readActivitySpinner(ii);
                } catch (JSONException e) {

                }
                if (spinnerLists.getActivitykey().toString().equals(actspinner.getSelectedItem()+"")) {
                    actSpin=spinnerLists.getActivitykey();
                    list.add(5,actSpin);
                }
            }


                list.add(6,statusspinner.getSelectedItem()+"");


            // leadstatus
            String leadS = null;
            leadStatus_spinner_helper dbLS = new leadStatus_spinner_helper(this);
            leadStatus_spinner_model leadStatusLists1 = null;
            for (int ii = 0; ii < dbLS.getAllLeadStatus().size(); ii++) {
                try {
                    leadStatusLists1 = dbLS.readLeadStatusSpinner(ii + 1);
                } catch (JSONException e) {

                }
                if (leadStatusLists1.getLeadStatusName().equals(leadstatusSpinner.getSelectedItem()+"")) {
                    leadS = leadStatusLists1.getLeadStatusValue();
                    list.add(7,leadS);
                }
            }
            list.add(8,desc.getText().toString());
            //assigned to
            String as=null;
            assignedTo_spinner_helper dba = new assignedTo_spinner_helper(this);
            assignedTo_spinner_model assignedLists = null;
            for (int ii = 0; ii < dba.getAllAssignedTo().size(); ii++) {
                try {
                    assignedLists = dba.readAssignedToSpinner(ii+1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (assignedLists.getUserName().equals(assigeToSpinner.getSelectedItem()+"")) {
                    as = assignedLists.getUserId();
                    list.add(9, as);
                }
            }
            list.add(10,uuid);



            JSONArray jsArray = new JSONArray(list);


            OutputStream os = conn.getOutputStream();

            String s=jsArray.toString();


            os.write(s.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }
    public HttpURLConnection createConnection(String wsPart, String method) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname,passw.toCharArray());
            }
        });

        final URL url = new URL(uRl + wsPart);
        final HttpURLConnection hc = (HttpURLConnection) url.openConnection();
        hc.setRequestMethod(method);
        hc.setAllowUserInteraction(false);
        hc.setDefaultUseCaches(false);
        hc.setDoOutput(true);
        hc.setDoInput(true);
        hc.setInstanceFollowRedirects(true);
        hc.setUseCaches(false);
        hc.setRequestProperty("Content-Type", "text/xml");
        return hc;
    }
    // ---------------------------------------------------------------------------------------------
public static class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        if (clickStartTimeFlag==true) {
            starttime.setText(hourOfDay + ":" + minute);
        }else if (clickDurationTimeFlag==true) {
            durationtime.setText(hourOfDay + ":" + minute);
        }
    }
}

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment1 = new TimePickerFragment();
        newFragment1.show(getSupportFragmentManager(), "timePicker");
    }


    // --------------------------------- Date ------------------------------------

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            month=month+1;
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            if (clickStartDateFlag==true) {
                startdate.setText(year + "-" + month + "-" + day);
            }else if (clickEnddateFlag==true) {
            //    enddate.setText(year + "-" + month + "-" + day);
            }
        }
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    @Override
    public void onBackPressed() {

        if (Main4Activity.fabcasesFlag==true ){
            Intent i = new Intent(this, Main4Activity.class);
            i.putExtra("leadid", lid);
            i.putExtra("leadname",lidname);
            startActivity(i);
            finish();
        }
        else if (MainActivity.listFlag==true ){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","2");
            startActivity(i);
            finish();
        }
        else if (MainActivity.fabFlag==true){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","2");
            startActivity(i);
            finish();
        }
        else if (MainActivity.navFlag==true ){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","2");
            startActivity(i);
            finish();
        }
        else if (Main5Activity.editflag==true ){
            Intent i = new Intent(this, Main5Activity.class);
            i.putExtra("activityId",actid);
            i.putExtra("activityname",aname);
            startActivity(i);
            finish();
        }



    }



}
