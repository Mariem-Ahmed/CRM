package com.example.afaf.enterprisecrm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Leads;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_cases;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.casePriority_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.caseStatus_helper;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.casePriority_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.caseStatus_Model;
import com.example.afaf.enterprisecrm.Helper_Database.casesModel;
import com.example.afaf.enterprisecrm.Helper_Database.relatedLead_Model;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

/**
 * Created by enterprise on 25/09/16.
 */
public class cases  extends AppCompatActivity {

    String cID= null;
    String cactID= null;
    // ----------------- activity date && time --------------
    static TextView spent;
    static EditText spenttime;
    // ----------------------------------
    static TextView startd;
    static TextView end;
    static TextView startdate;
    static TextView enddate;
    static  boolean  clickStartDateFlag=false;
    public static boolean  updateflag=false, actdone=false;
    public static EditText subject;
    Spinner priortySpinner ,assignedToSpinner, caseStatus ,leadNameSpinner, activitySpinner;
    static EditText deadline;

    public static casesModel Em= new casesModel();
    int caseId;
    int x,y,z,xx, x1=0;

    //------------------- url -------------------------------------------------------------
    public static final String MyPREFERENCES = "MyPrefs" ;
    //  public static final String URL = "nameKey";

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
    public static String lid;
    public static String lidname;
    public static String actid;
    public static String aname;
    String ID = "0";

    // requried
    boolean check, cancel = false;
    View focusView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cases);
     //   if (lid==null && lidname==null) {
            Intent ii3 = getIntent();
            lid = ii3.getStringExtra("leadidid");
            lidname = ii3.getStringExtra("ComName");
     //   }
      //  if (actid==null && aname==null) {
            Intent ii4 = getIntent();
            actid = ii4.getStringExtra("actidid");
            aname = ii4.getStringExtra("subName");
            ID= ii4.getStringExtra("id");
      //  }

 //-----------------------------url &  login  ------------------------------------------
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl= sharedpreferences.getString("URL",null);

        if (uRl != null) {
            sharedpreferencesLogin = getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
            uname = sharedpreferencesLogin.getString("uName", null);
            passw = sharedpreferencesLogin.getString("passWord", null);
            userId = sharedpreferencesLogin.getString("userId", null);
            if (uname == null && passw == null) {
                Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i1);
                finish();
            }
        }
        else{
            Intent i = new Intent(getApplicationContext(), insertUrl.class);
            startActivity(i);
            finish();
        }

// ----------------------------------------------------------------------------------------
//&& Main4Activity.fabcasesFlag==false

        if(MainActivity.listFlag==true ) {
            if(LoginActivity.arabicflag==true ){
                setTitle("تعديل الحاله");
            }
            else if(insertUrl.arflagURL==true ){
                setTitle("تعديل الحاله");
            }else{
                setTitle("Edit Case");
            }
        }else{
            if (LoginActivity.arabicflag == true) {
                setTitle("اضافه حاله");
            } else if (insertUrl.arflagURL == true) {
                setTitle("اضافه حاله");
            } else {
                setTitle("Add Case");
            }
        }

        subject= (EditText) findViewById(R.id.editText2);
        subject.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                subject.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        deadline= (EditText) findViewById(R.id.textdeaddate);
        deadline.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                deadline.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

       // deadlinetext= (TextView) findViewById(R.id.caseDeadlineDate);
        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        priortySpinner= (Spinner) findViewById(R.id.prioritySpinner);
        // change color of selected item
        priortySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        assignedToSpinner= (Spinner) findViewById(R.id.assignedToSpinner);
        // change color of selected item
        assignedToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        caseStatus= (Spinner) findViewById(R.id.StatusSpinner);
        // change color of selected item
        caseStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        leadNameSpinner= (Spinner) findViewById(R.id.rleadSpinner);
        // change color of selected item
        leadNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        activitySpinner= (Spinner) findViewById(R.id.activitySpinner);
        // change color of selected item
        activitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

// -----------------------------------------------------------------
//        spenttime = (EditText) findViewById(R.id.texttimespent);
//        spenttime.addTextChangedListener(new TextWatcher() {
//            public void afterTextChanged(Editable s) {
//
//                spenttime.setError(null);
//                cancel=false;
//            }
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {}
//        });
//
//        spenttime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showTimePickerDialog(view);
//            }
//        });

// --------------------------------------------------------------
        Intent i = getIntent();


        String caseID = i.getStringExtra("caseID");
        cID=caseID;

        String caseactID = i.getStringExtra("caseactid");
        cactID=caseactID;
        // -------------------------------------------activity --------------------------------------------------------
        String caseActivity = i.getStringExtra("caseact");
        String caseActivityid = i.getStringExtra("caseactid");

        if (Main5Activity.fabactivitiesFlag==true){

            JCGSQLiteHelper dbactcase = new JCGSQLiteHelper(this);
            String first;
            List<String> listactcase = new ArrayList<String>();
            for (int ii = 0; ii < dbactcase.getAllCasesActivities().size(); ii++) {
                first= dbactcase.getAllCasesActivities().get(ii);
                listactcase.add(first);
                //&& caseActivityid.equals(activity_info.actID)
                if (dbactcase.getAllCasesActivities().get(ii).equals(activity_info.sub.getText().toString()) )
                    xx = ii;
            }
            ArrayAdapter<String> spinnerAdapteractcase = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listactcase);
            spinnerAdapteractcase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            activitySpinner.setAdapter(spinnerAdapteractcase);
            activitySpinner.setSelection(xx);

        }else  if (Main4Activity.fabcasesFlag==true){

            JCGSQLiteHelper dbactcase = new JCGSQLiteHelper(this);
            String first = null;
            List<String> listactcase = new ArrayList<String>();
            listactcase.add(0, " ");
            for (int ii = 0; ii < dbactcase.getAllActivitiesLeads(lead_info_activity.leadid).size(); ii++) {
                first= dbactcase.getAllActivitiesLeads(lead_info_activity.leadid).get(ii);
                            listactcase.add(first);

                if (first.equals(caseActivity) )
                    xx = ii;

            }

            ArrayAdapter<String> spinnerAdapteractcase = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listactcase);
            spinnerAdapteractcase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            activitySpinner.setAdapter(spinnerAdapteractcase);


        } else {
            JCGSQLiteHelper dbactcase = new JCGSQLiteHelper(this);
            String first;
            List<String> listactcase = new ArrayList<String>();
            listactcase.add(0, " ");
            for (int ii = 0; ii < dbactcase.getAllCasesActivities().size(); ii++) {

                first= dbactcase.getAllCasesActivities().get(ii);
                listactcase.add(first);
                String leadnameid = i.getStringExtra("relatedLeadid");

                if (first.equals(caseActivity) )
                    xx = ii;
            }
            ArrayAdapter<String> spinnerAdapteractcase = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listactcase);
            spinnerAdapteractcase.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            activitySpinner.setAdapter(spinnerAdapteractcase);
        }

        // -------------------------------------------periority--------------------------------------------------------
        String priorty = i.getStringExtra("priority");

        casePriority_spinner_helper db = new casePriority_spinner_helper(this);
        casePriority_spinner_model spinnerLists =null;
        List<String> list = new ArrayList<String>();
        list.add(0," ");
        for (int ii = 0; ii < db.getAllCasePeriority().size(); ii++) {
            try {
                spinnerLists= db.readcasePerioritySpinner(ii+1);
            } catch (JSONException e) {

            }
            list.add(spinnerLists.getCasePeriorityName());
          if (actdone==true){
              if (spinnerLists.getCasePeriorityName().equals(priorty))
                  x = ii + 1;
          }else {
              if (spinnerLists.getCasePeriorityValue().equals(priorty))
                  x = ii + 1;
          }
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priortySpinner.setAdapter(spinnerAdapter);

        // ----------------------------------------status--------------------------------------------------------------
        String status = i.getStringExtra("status");
        caseStatus_helper db2 = new caseStatus_helper(this);
        caseStatus_Model spinner = null;
        List<String> list2 = new ArrayList<String>();
        list2.add(0," ");
        for (int i1 = 0; i1 < db2.getAllCaseStatus().size(); i1++) {
            try {
                spinner= db2.readcaseStatusSpinner(i1+1);
            } catch (JSONException e) {

            }
            list2.add(spinner.getCaseStatusName());
            if (actdone==true){

                if (spinner.getCaseStatusName().equals(status))
                    y = i1+1;
            }else {
                if (spinner.getCaseStatusValue().equals(status))
                    y = i1+1;
            }

        }
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        caseStatus.setAdapter(spinnerAdapter2);
        // ---------------------------------------assignedTo-------------------------------------------------------------

        String assignedTo = i.getStringExtra("assignedTo");
        assignedTo_spinner_helper db1 = new assignedTo_spinner_helper(this);
        assignedTo_spinner_model assignedLists = null;
        List<String> list1 = new ArrayList<String>();
        for (int ii = 0; ii < db1.getAllAssignedTo().size(); ii++) {
            try {
                assignedLists = db1.readAssignedToSpinner(ii+1);
            } catch (JSONException e) {
            }

                list1.add(assignedLists.getUserName());
                if (assignedLists.getUserId().equals(assignedTo)||assignedLists.getUserId().equals(userId))
                    z = ii;
            }

        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedToSpinner.setAdapter(spinnerAdapter1);
        assignedToSpinner.setSelection(z);

// ----------------------------------------------------------------------
// ----------------- related Lead spinner   -------------------------
        String leadname = i.getStringExtra("relatedLead");
        String leadnameid = i.getStringExtra("relatedLeadid");

        if (Main4Activity.fabcasesFlag==true){
            JCGSQLiteHelper_Leads db3 = new JCGSQLiteHelper_Leads(this);
            relatedLead_Model rleadLists1 = null;
            List<String> list3 = new ArrayList<String>();
            for (int i1 = 0; i1 < db3.getAllRLeads().size(); i1++) {
                rleadLists1 = db3.getAllRLeads().get(i1);
                list3.add(rleadLists1.getrLeadName());

                if (rleadLists1.getrLeadId().equals(lead_info_activity.leadid))
                    x1 = i1+1;
            }
            ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list3);
            spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            leadNameSpinner.setAdapter(spinnerAdapter3);
            leadNameSpinner.setSelection(x1-1);

        }else{
            JCGSQLiteHelper_Leads db3 = new JCGSQLiteHelper_Leads(this);
            relatedLead_Model rleadLists1 = null;
            List<String> list3 = new ArrayList<String>();
            list3.add(0," ");
            for (int i1 = 0; i1 < db3.getAllRLeads().size(); i1++) {
                rleadLists1 = db3.getAllRLeads().get(i1);
                list3.add(rleadLists1.getrLeadName());

                if (rleadLists1.getrLeadId().equals(leadnameid))
                    x1 = i1+1;
            }
            ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list3);
            spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            leadNameSpinner.setAdapter(spinnerAdapter3);
        }

 // ------------------------------------------------------------------------------

        if(MainActivity.listFlag==true && MainActivity.fabFlag==false  ) {

         //   caseId = Integer.parseInt(i.getStringExtra("id"));

            String subName = i.getStringExtra("subName");
            try {
                final String s = new String(subName.getBytes(), "UTF-8");
                subject.setText(s);
            } catch (UnsupportedEncodingException e) {
            }

            String dead = i.getStringExtra("deadline");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                Date d = null ;
            if (!dead.isEmpty()) {
                try {
                    d = output.parse(dead);
                } catch (ParseException e) {
                }
                long millisecond = d.getTime();

                String currentDate = getDate(millisecond, "yyyy-MM-dd");

                deadline.setText(currentDate);
            }else {
                deadline.setText(dead);
            }

            priortySpinner.setSelection(x);
            caseStatus.setSelection(y);
            assignedToSpinner.setSelection(z-1);
            leadNameSpinner.setSelection(x1);
            activitySpinner.setSelection(xx);

        }
// ----------------------------------------------------------------
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
    // Check for requried fields.
    public void check(){

        if (  MainActivity.navFlag==true || MainActivity.fabFlag==true || Main4Activity.fabcasesFlag==true && Main4Activity.editleadflag==false  || Main5Activity.fabactivitiesFlag==true&& Main5Activity.editflag==false) {
            if (TextUtils.isEmpty(subject.getText())) {
                subject.setError(getString(R.string.error_field_required));
                focusView = subject;
                cancel = true;
            }

            if (TextUtils.isEmpty(deadline.getText())) {
                deadline.setError(getString(R.string.error_field_required));
                focusView = deadline;
                cancel = true;
            }
//            if (TextUtils.isEmpty(spenttime.getText())) {
//                spenttime.setError(getString(R.string.error_field_required));
//                focusView = spenttime;
//                cancel = true;
//            }
        }
        //  return true;
    }

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.nav_menu, menu);
    return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==R.id.done){

            actdone=true;
            if (MainActivity.listFlag==true && MainActivity.fabFlag==false) {

                String subname = subject.getText()+"";
                String leadname = leadNameSpinner.getSelectedItem() + "";  // spinner related Lead
                String dead = deadline.getText().toString();
                String priorty = priortySpinner.getSelectedItem() + "";
                String status = caseStatus.getSelectedItem() + "";
                String assigned = assignedToSpinner.getSelectedItem() + "";
                String caseid = cID;

                String caseact = activitySpinner.getSelectedItem() + "";

                JCGSQLiteHelper_Leads leads = new JCGSQLiteHelper_Leads(this);
                leads.getLeadByName(leadname);
                JCGSQLiteHelper activity = new JCGSQLiteHelper(this);

                Em.setSubjectName(subname);
                if (leads.getLeadByName(leadname).size() != 0) {
                    Em.setLeadName(leads.getLeadByName(leadname).get(0).getLeadId());
                } else {
                    Em.setLeadName("");
                }
                Em.setDeadLine(dead);
                Em.setStatus(status);
                Em.setAssignedTo(assigned);
                Em.setCaseID(caseid);
                if(ID!=null)
                Em.setId(Integer.parseInt(ID));
                Em.setPriority(priorty);
                if (activity.geEventActivity(caseact).size() != 0) {
                    Em.setCaseActivity(activity.geEventActivity(caseact).get(0).getActivityId());
                } else {
                    Em.setCaseActivity("");
                }


                JCGSQLiteHelper_cases h = new JCGSQLiteHelper_cases(this);
                h.updateEvent(Em);
                updateflag=true;
                AsyncCallWS task = new AsyncCallWS();
                task.execute();

            }
             else {


                String subname = subject.getText() + "";
                String leadname = leadNameSpinner.getSelectedItem() + ""; // spinner related Lead
                String dead = deadline.getText() + "";
                String priorty = priortySpinner.getSelectedItem() + "";
                String status = caseStatus.getSelectedItem() + "";
              //  String STime = spenttime.getText() + "";
                String assigned = assignedToSpinner.getSelectedItem() + "";
                String caseact = activitySpinner.getSelectedItem() + "";

                JCGSQLiteHelper_Leads leads = new JCGSQLiteHelper_Leads(this);
                leads.getLeadByName(leadname);
                JCGSQLiteHelper activity = new JCGSQLiteHelper(this);

                JCGSQLiteHelper_cases cases = new JCGSQLiteHelper_cases(this);
                Em.setId(cases.getAllCases().size()+1);
                Em.setSubjectName(subname);

                if (leads.getLeadByName(leadname).size() != 0) {
                    String ll = leads.getLeadByName(leadname).get(0).getLeadId();
                    Em.setLeadName(ll);
                } else {
                    Em.setLeadName("");
                }
                Em.setDeadLine(dead);
                Em.setStatus(status);
                Em.setAssignedTo(assigned);
                Em.setPriority(priorty);


                if (activity.geEventActivity(caseact).size() != 0) {
                    String dd = activity.geEventActivity(caseact).get(0).getActivityId();
                    Em.setCaseActivity(dd);
                } else {
                    Em.setCaseActivity("");
                }

                JCGSQLiteHelper_cases h = new JCGSQLiteHelper_cases(this);

                // check ----------------
                check();
                if (cancel == true) {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    // cancel=false;
                    check = true;

                } else {

                    h.insertEvent(Em);
                    check = false;
                    AsyncCallWS1 task = new AsyncCallWS1();
                    task.execute();
                }
            }





            if (check==false) {
                if (MainActivity.listFlag == true || MainActivity.navFlag == true) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Window", "3");
                    startActivity(i);
                    finish();
                } else if (Main5Activity.fabactivitiesFlag == true) {
                    Intent i = new Intent(this, Main5Activity.class);
                    i.putExtra("activityId", actid);
                    i.putExtra("activityname", aname);
                    startActivity(i);
                    finish();

                } else if (Main4Activity.fabcasesFlag == true) {
                    Intent i = new Intent(this, Main4Activity.class);
                    i.putExtra("leadid", lid);
                    i.putExtra("leadname", lidname);
                    startActivity(i);
                    finish();
                } else if (MainActivity.fabFlag == true) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Window", "3");
                    startActivity(i);
                    finish();
                }

            }

        }
        else if (id==R.id.close){
            if (activity_cases_adapter.tabbedcaseactivity==true || Main5Activity.fabactivitiesFlag==true ){
                Intent i = new Intent(this, Main5Activity.class);
                i.putExtra("activityId",actid);
                i.putExtra("activityname",aname);
                startActivity(i);
                finish();
                return true;
            }
            else if (lead_cases_adapter.tabbedcaselead==true || Main4Activity.fabcasesFlag==true){
                Intent i = new Intent(this, Main4Activity.class);
                i.putExtra("leadid", lid);
                i.putExtra("leadname",lidname);
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.listFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","3");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.fabFlag==true){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","3");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.navFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","3");
                startActivity(i);
                finish();
                return true;
            }
            }
        else if (id ==android.R.id.home) {
            if (activity_cases_adapter.tabbedcaseactivity==true || Main5Activity.fabactivitiesFlag==true){
                Intent i = new Intent(this, Main5Activity.class);
                i.putExtra("activityId",actid);
                i.putExtra("activityname",aname);
                startActivity(i);
                finish();
                return true;
            } else if (lead_cases_adapter.tabbedcaselead==true  || Main4Activity.fabcasesFlag==true ){
                Intent i = new Intent(this, Main4Activity.class);
                i.putExtra("leadid", lid);
                i.putExtra("leadname",lidname);
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.listFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","3");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.fabFlag==true){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","3");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.navFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","3");
                startActivity(i);
                finish();
                return true;
            }

            }

        return true;
       // return false;
        //return super.onOptionsItemSelected(item);
    }
    // --------------------------------------Time ------------------------------
//    public static class TimePickerFragment extends DialogFragment
//            implements TimePickerDialog.OnTimeSetListener {
//        acitvity newact = new acitvity();
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the current time as the default values for the picker
//            final Calendar c = Calendar.getInstance();
//            int hour = c.get(Calendar.HOUR_OF_DAY);
//            int minute = c.get(Calendar.MINUTE);
//
//            // Create a new instance of TimePickerDialog and return it
//            return new TimePickerDialog(getActivity(), this, hour, minute,
//                    DateFormat.is24HourFormat(getActivity()));
//        }
//
//        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//            // Do something with the time chosen by the user
//            spenttime.setText(hourOfDay + ":" + minute);
//
//        }
//    }
//
//    public void showTimePickerDialog(View v) {
//        DialogFragment newFragment = new TimePickerFragment();
//        newFragment.show(getSupportFragmentManager(), "timePicker");
//    }

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
            month = month + 1;
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            deadline.setText(year + "-" + month + "-" + day);
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

// -------------------------------------------------------------------------------------------------
private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        testInsert("/ws/it.openia.crm.insertCase?");
//testUpdate();
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
private class AsyncCallWS extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... params) {
        testUpdate("/ws/it.openia.crm.updateCase?userid=100");
//testUpdate();
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
    // insert connection
    public String testInsert(String content) {
        try {

            HttpURLConnection conn = createConnection( content,"POST");
            conn.connect();
            ArrayList<Object> list=new ArrayList<Object>();
            // ArrayList<String> list = new ArrayList<String>();

            assignedTo_spinner_helper db1 = new assignedTo_spinner_helper(this);
            assignedTo_spinner_model assignedLists = null;
            for (int ii = 0; ii < db1.getAllAssignedTo().size(); ii++) {
                try {
                    assignedLists = db1.readAssignedToSpinner(ii+1);
                } catch (JSONException e) {
                }
                if (assignedLists.getUserName().equals(assignedToSpinner.getSelectedItem()+""))
                    list.add(0, assignedLists.getUserId());

            }

            list.add(1,leadNameSpinner.getSelectedItem()+"");  // spinner related Lead


            list.add(2,"0:0");

            if (!priortySpinner.getSelectedItem().toString().equals(" ")) {
                casePriority_spinner_helper db = new casePriority_spinner_helper(this);
                List<casePriority_spinner_model> spinnerLists = db.getAllCasePeriority();
                for (int ii = 0; ii < spinnerLists.size(); ii++) {
                    if (spinnerLists.get(ii).getCasePeriorityName().equals(priortySpinner.getSelectedItem().toString()))
                        list.add(3, spinnerLists.get(ii).getCasePeriorityValue());
                }
            }else {
                list.add(3, priortySpinner.getSelectedItem()+"");
            }

            list.add(4,subject.getText().toString());
            list.add(5,deadline.getText().toString());

            if (!caseStatus.getSelectedItem().toString().equals(" ")) {
                caseStatus_helper db2 = new caseStatus_helper(this);
                caseStatus_Model spinner = null;
                for (int i1 = 0; i1 < db2.getAllCaseStatus().size(); i1++) {
                    try {
                        spinner = db2.readcaseStatusSpinner(i1 + 1);
                    } catch (JSONException e) {
                    }

                    if (spinner.getCaseStatusName().equals(caseStatus.getSelectedItem().toString()))
                        list.add(6, spinner.getCaseStatusValue());
                }
            }else {
                list.add(6, caseStatus.getSelectedItem()+"");
            }


            list.add(7,activitySpinner.getSelectedItem()+"");
           // list.add(8,cactID);


            JSONArray jsArray = new JSONArray(list);
            OutputStream os = conn.getOutputStream();

            String s=jsArray.toString();

            os.write(s.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {

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

        } catch (IOException e) {



        } catch (Exception e) {

        }

        return null;


    }

    // update connection
    public String testUpdate(String content) {
        try {

            HttpURLConnection conn = createConnection( content,"POST");
            conn.connect();
            ArrayList<Object> list=new ArrayList<Object>();
            // ArrayList<String> list = new ArrayList<String>();

            list.add(0,assignedToSpinner.getSelectedItem().toString());
            list.add(1,leadNameSpinner.getSelectedItem().toString());  // spinner related Lead
            list.add(2,"0:0");


            casePriority_spinner_helper db = new casePriority_spinner_helper(this);
            casePriority_spinner_model spinnerLists ;
            System.out.println(db.getAllCasePeriority().size());
            for (int ii = 1; ii < db.getAllCasePeriority().size(); ii++) {
              //  if (spinnerLists.get(ii).getCasePeriorityName())
                spinnerLists= db.readcasePerioritySpinner(ii);
                if (spinnerLists.getCasePeriorityName().equals(priortySpinner.getSelectedItem().toString()))
                    list.add(3,spinnerLists.getCasePeriorityValue());
            }

            list.add(4, subject.getText().toString());

            list.add(5,cID);
            list.add(6,deadline.getText().toString());
         //   list.add(7,caseStatus.getSelectedItem().toString());
            caseStatus_helper db2 = new caseStatus_helper(this);
            caseStatus_Model spinner = null;
            for (int i1 = 0; i1 < db.getAllCasePeriority().size(); i1++) {
                try {
                    spinner= db2.readcaseStatusSpinner(i1+1);
                } catch (JSONException e) {

                }

                if (spinner.getCaseStatusName().equals(caseStatus.getSelectedItem().toString()))
                    list.add(7,spinner.getCaseStatusValue());
            }

            list.add(8,activitySpinner.getSelectedItem().toString());

            JSONArray jsArray = new JSONArray(list);
            OutputStream os = conn.getOutputStream();

            String s=jsArray.toString();

            os.write(s.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {

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



        } catch (IOException e) {


        } catch (Exception e) {

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


    @Override
    public void onBackPressed() {
        if (activity_cases_adapter.tabbedcaseactivity==true || Main5Activity.fabactivitiesFlag==true){
            Intent i = new Intent(this, Main5Activity.class);
            i.putExtra("activityId",actid);
            i.putExtra("activityname",aname);
            startActivity(i);
            finish();
        }
        else if (lead_cases_adapter.tabbedcaselead==true || Main4Activity.fabcasesFlag==true){
            Intent i = new Intent(this, Main4Activity.class);
            i.putExtra("leadid", lid);
            i.putExtra("leadname",lidname);
            startActivity(i);
            finish();
        }
        else if (MainActivity.listFlag==true ){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","3");
            startActivity(i);
            finish();
        }
        else if (MainActivity.fabFlag==true){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","3");
            startActivity(i);
            finish();
        }
        else if (MainActivity.navFlag==true ){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","3");
            startActivity(i);
            finish();
        }
    }
}
