package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Leads;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.interestedIn_helper;
import com.example.afaf.enterprisecrm.Helper_Database.leadSource_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.leadStatus_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.project_helper;
import com.example.afaf.enterprisecrm.Helper_Database.round_helper;
import com.example.afaf.enterprisecrm.Helper_Database.title_helper;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.interestedIn_Model;
import com.example.afaf.enterprisecrm.Helper_Database.leadSource_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.leadStatus_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.project_Model;
import com.example.afaf.enterprisecrm.Helper_Database.round_Model;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Afaf on 10/7/2016.
 */

public class leads_form  extends AppCompatActivity {





   public static EditText commerialName,phone,email,comment,currentAddress;
    public static Spinner leadSource,status, interestedInSpinner, title, roundID, projectID, assignedTo;
    int leadId;
    public static leadsModel Em= new leadsModel();
    int x,y,z,a,b,c,d,f=0;


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

    public static   String leadid;

   public static String getleadidwithinsert;


    // requried
    boolean check, cancel = false;
    View focusView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_form);
        if (lid==null && lidname==null) {
            Intent ii3 = getIntent();
            lid = ii3.getStringExtra("leadidid");
            lidname = ii3.getStringExtra("ComName");
        }
 //-----------------------------url &  login  ------------------------------------------
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl= sharedpreferences.getString("URL",null);
        if (uRl != null) {
            sharedpreferencesLogin = getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
            uname = sharedpreferencesLogin.getString("uName", null);
            passw = sharedpreferencesLogin.getString("passWord", null);
            userId =  sharedpreferencesLogin.getString("userId", null);
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
   // ------------------------------------------------------------------------------------------

        if(Main4Activity.editleadflag==true) {
            if(LoginActivity.arabicflag==true ){
                setTitle("تعديل العميل");
            }
            else if(insertUrl.arflagURL==true ){
                setTitle("تعديل العميل");
            }else{
                setTitle("Edit Lead");
            }
        }else{
            if (LoginActivity.arabicflag == true) {
                setTitle("اضافه عميل ");
            } else if (insertUrl.arflagURL == true) {
                setTitle("اضافه عميل");
            } else {
                setTitle("Add Lead");
            }
        }
        commerialName= (EditText) findViewById(R.id.editText3);
        commerialName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                commerialName.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        phone= (EditText) findViewById(R.id.editText4);
        phone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                phone.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        email= (EditText) findViewById(R.id.mail);
        comment= (EditText) findViewById(R.id.editText6);
        currentAddress= (EditText) findViewById(R.id.editText11);

        leadSource = (Spinner) findViewById(R.id.leadSourceSpinner);
        // change color of selected item
        leadSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        assignedTo = (Spinner) findViewById(R.id.assignetosSpinner);
        // change color of selected item
        assignedTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        status = (Spinner) findViewById(R.id.StatusSpinner);
// change color of selected item
        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        interestedInSpinner = (Spinner) findViewById(R.id.leadNeedsSpinner);
        // change color of selected item
        interestedInSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        title = (Spinner) findViewById(R.id.titleSpinner);
        // change color of selected item
        title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        roundID = (Spinner) findViewById(R.id.roundidSpinner);
        // change color of selected item
        roundID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        projectID = (Spinner) findViewById(R.id.projectidSpinner);
        // change color of selected item
        projectID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //  nationality = (Spinner) findViewById(R.id.nationalitySpinner);

        Intent i = getIntent();

        lead_info_activity.leadflag=true;
        String lid = i.getStringExtra("leadid");
        leadid = lid;


//------------------------------lead source  spinner----------------------------------------------------------------------------
        String leadsource = i.getStringExtra("leadsource");

        leadSource_spinner_helper dbLS = new leadSource_spinner_helper(this);
        List<leadSource_spinner_model> LSLists = dbLS.getAllLeadSource();
        List<String> list1 = new ArrayList<String>();
        list1.add(0, " ");
        for (int i1 = 0; i1 < LSLists.size(); i1++) {
            list1.add(LSLists.get(i1).getlSTranslationName());

        }
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leadSource.setAdapter(spinnerAdapter1);
// ----------------------------------------------lead status spinner------------------------------------------------------
        String leadstatus = i.getStringExtra("leadstatus");
        leadStatus_spinner_helper db = new leadStatus_spinner_helper(this);
        leadStatus_spinner_model leadStatusLists1 = null;
        List<String> list = new ArrayList<String>();
     //   list.add(0, " ");
        for (int ii = 0; ii <db.getAllLeadStatus().size(); ii++) {
            try {
                leadStatusLists1= db.readLeadStatusSpinner(ii+1);
            } catch (JSONException e) {

            }
            list.add(leadStatusLists1.getLeadStatusName());
            if (leadStatusLists1.getLeadStatusValue().equals(leadstatus))
                y = ii;
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(spinnerAdapter);
// -----------------------------------------------------------------------------
// ----------------------------------------------interested in spinner------------------------------------------------------
        String interstedin = i.getStringExtra("interestIn");

        interestedIn_helper db4 = new interestedIn_helper(this);
        interestedIn_Model interestLists1 = null;
        List<String> list4 = new ArrayList<String>();
        list4.add(0, " ");
        for (int ii = 0; ii <db4.getAllInterestedIn().size(); ii++) {
            try {
                interestLists1= db4.readinterestedInSpinner(ii+1);
            } catch (JSONException e) {

            }
            list4.add(interestLists1.getInterestedInTranslationName());
            if (interestLists1.getInterestedInId().equals(interstedin))
               z= ii+1;
        }
        ArrayAdapter<String> spinnerAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list4);
        spinnerAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestedInSpinner.setAdapter(spinnerAdapter4);
// -----------------------------------------------------------------------------
// ----------------------------------------------title spinner------------------------------------------------------
        String titl = i.getStringExtra("title");

        title_helper dbt = new title_helper(this);
        title_Model titleLists1 = null;
        List<title_Model> listtest= dbt.getAllTitles();
        List<String> list5 = new ArrayList<String>();
        list5.add(0, " ");
        for (int iii = 0; iii <listtest.size(); iii++) {
            try {
                titleLists1= dbt.readtitleSpinner(iii+1);
            } catch (JSONException e) {

            }
            list5.add(titleLists1.getTitleTranslationName());
            if (titleLists1.getTitleId().equals(titl))
                a= iii+1;
        }
        ArrayAdapter<String> spinnerAdapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list5);
        spinnerAdapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        title.setAdapter(spinnerAdapter5);
// -----------------------------------------------------------------------------
// ----------------------------------------------project id spinner------------------------------------------------------
        String pid = i.getStringExtra("project");

        project_helper db6 = new project_helper(this);
        project_Model pidLists1 = null;
        List<String> list6 = new ArrayList<String>();
        list6.add(0, " ");
        for (int ii = 0; ii <db6.getAllprojectIds().size(); ii++) {
            try {
                pidLists1= db6.readprojectSpinner(ii+1);
            } catch (JSONException e) {

            }
            list6.add(pidLists1.getProjectName());
            if (pidLists1.getProjectId().equals(pid))
                b= ii+1;
        }
        ArrayAdapter<String> spinnerAdapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list6);
        spinnerAdapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectID.setAdapter(spinnerAdapter6);
// -----------------------------------------------------------------------------
// ----------------------------------------------round id spinner------------------------------------------------------
        String rid = i.getStringExtra("round");

        round_helper db7 = new round_helper(this);
        round_Model ridLists1 = null;
        List<String> list7 = new ArrayList<String>();
        list7.add(0, " ");
        for (int ii = 0; ii <db7.getAllRoundIds().size(); ii++) {
            try {
                ridLists1= db7.readroundSpinner(ii+1);
            } catch (JSONException e) {

            }
            list7.add(ridLists1.getRoundName());
            if (ridLists1.getRoundId().equals(rid))
                c= ii+1;
        }
        ArrayAdapter<String> spinnerAdapter7 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list7);
        spinnerAdapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roundID.setAdapter(spinnerAdapter7);
// -----------------------------------------------------------------------------
// ----------------------------------------------nationality spinner------------------------------------------------------
//        String nat = i.getStringExtra("nationalty");
//
//        nationality_helper db8 = new nationality_helper(this);
//        nationality_Model nLists1 = null;
//        List<String> list8 = new ArrayList<String>();
//        list8.add(0, " ");
//        for (int ii = 0; ii <db8.getAllnationality().size(); ii++) {
//            try {
//                nLists1= db8.readcountrySpinner(ii+1);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            list8.add(nLists1.getCountryName());
//            if (nLists1.getNationalityId().equals(nat))
//                d= ii+1;
//        }
//        ArrayAdapter<String> spinnerAdapter8 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list8);
//        spinnerAdapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        nationality.setAdapter(spinnerAdapter8);
// -----------------------------------------------------------------------------
// ----------------------------------------------assignedTo  spinner------------------------------------------------------
        String assigned = i.getStringExtra("AssignedToID");

        assignedTo_spinner_helper dba = new assignedTo_spinner_helper(this);
        assignedTo_spinner_model assignedLists = null;
        List<String> lista= new ArrayList<String>();
        for (int ii = 0; ii < dba.getAllAssignedTo().size(); ii++) {
            try {
                assignedLists = dba.readAssignedToSpinner(ii+1);
            } catch (JSONException e) {

            }
            lista.add(assignedLists.getUserName());
            if (assignedLists.getUserId().equals(assigned)||assignedLists.getUserId().equals(userId))
                f = ii;
        }


        ArrayAdapter<String> spinnerAdaptera = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        spinnerAdaptera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedTo.setAdapter(spinnerAdaptera);
        assignedTo.setSelection(f);

// -----------------------------------------------------------------------------
        if(Main4Activity.editleadflag==true) {


           leadId = Integer.parseInt(i.getStringExtra("id"));
            String comName = i.getStringExtra("CommertialName");
            try {
                final String s = new String(comName.getBytes(), "UTF-8");
                commerialName.setText(s);
            } catch (UnsupportedEncodingException e) {

            }

            String phnum = i.getStringExtra("phone");
            phone.setText(phnum);
            String mail = i.getStringExtra("email");
            email.setText(mail);
            String address = i.getStringExtra("currentaddress");
            currentAddress.setText(address);

            String coment = i.getStringExtra("comment");
            try {
                final String s1 = new String(coment.getBytes(), "UTF-8");
                comment.setText(s1);
            } catch (UnsupportedEncodingException e) {

            }

            for (int i1 = 0; i1 < LSLists.size(); i1++) {
             //   list1.add(LSLists.get(i1).getlSTranslationName());
               // System.out.println(LSLists.get(i1).getlSTranslationName());
              //  System.out.println(leadsource);
                String c=LSLists.get(i1).getlSTranslationName().toString();

                    if (c.equals(leadsource)) {
                        x = i1;
                        leadSource.setSelection(x+1);
                        break;
                    }
            }
            status.setSelection(y);
            interestedInSpinner.setSelection(z);
            title.setSelection(a);
            projectID.setSelection(b);
            roundID.setSelection(c);
            //nationality.setSelection(d-1);
            assignedTo.setSelection(f);
// ----------------------------------------------------------------------------------------------------------------



        }

    }

    // Check for requried fields.
    public void check(){

        if (  MainActivity.navFlag==true || MainActivity.fabFlag==true || Main4Activity.fabcasesFlag==true && Main4Activity.editleadflag==false) {
            if (TextUtils.isEmpty(commerialName.getText())) {
                commerialName.setError(getString(R.string.error_field_required));
                focusView = commerialName;
             //   focusView.requestFocus();
                cancel = true;
            }else if (TextUtils.isEmpty(phone.getText())) {
                phone.setError(getString(R.string.error_field_required));
                focusView = phone;
                //focusView.requestFocus();
                cancel = true;
            }

            if (cancel){
                focusView.requestFocus();
            }


        }
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

        commerialName= (EditText) findViewById(R.id.editText3);
        phone= (EditText) findViewById(R.id.editText4);
        email= (EditText) findViewById(R.id.mail);
        comment= (EditText) findViewById(R.id.editText6);
        currentAddress= (EditText) findViewById(R.id.editText11);
        //interestIn= (EditText) findViewById(R.id.interest);

        leadSource = (Spinner) findViewById(R.id.leadSourceSpinner);
        assignedTo = (Spinner) findViewById(R.id.assignetosSpinner);
        status = (Spinner) findViewById(R.id.StatusSpinner);

        int id = item.getItemId();
        if(id==R.id.done) {

            if (Main4Activity.editleadflag==true) {

                String comname = commerialName.getText().toString();
                String ph = phone.getText().toString();
                String mail = email.getText().toString();
                String com = comment.getText().toString();
                String address = currentAddress.getText().toString();
                String interest = interestedInSpinner.getSelectedItem().toString(); // spinner
                String leadS = leadSource.getSelectedItem().toString();
                String assign = assignedTo.getSelectedItem().toString();
                String statusspin = status.getSelectedItem().toString();
                String tit = title.getSelectedItem().toString(); // spinner
                String roundid =roundID.getSelectedItem().toString(); // spinner
                String projectid = projectID.getSelectedItem().toString(); // spinner
             //   String nation = nationality.getSelectedItem().toString(); // spinner

                Em.setId(leadId);
                Em.setCommertialName(comname);
                Em.setPhone(ph);
                Em.setEmail(mail);
                Em.setComment(com);
                Em.setEm_Opcrm_Current_Address(address);
                Em.setInterestedIn(interest);
                Em.setLeadSource(leadS);
                Em.setAssignedTo(assign);
                Em.setStatus(statusspin);
                Em.setTitle(tit);
                Em.setEm_Opcrm_Project_id(projectid);
                Em.setEm_Opcrm_Round_id(roundid);
                Em.setLeadId(lid);
            //    Em.setNationality(nation);

                JCGSQLiteHelper_Leads h = new JCGSQLiteHelper_Leads(this);
                h.updateEvent(Em);

                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            } else{


                String comname = commerialName.getText().toString();
                String ph = phone.getText().toString();
                String mail = email.getText().toString();
                String com = comment.getText().toString();
                String address = currentAddress.getText().toString();
                String interest = interestedInSpinner.getSelectedItem()+""; // spinner
                String leadS = leadSource.getSelectedItem()+"";
                String assign = assignedTo.getSelectedItem()+"";
                String statusspin = status.getSelectedItem()+"";
                String tit = title.getSelectedItem()+""; // spinner
                String roundid =roundID.getSelectedItem()+""; // spinner
                String projectid = projectID.getSelectedItem()+""; // spinner
             //   String nation = nationality.getSelectedItem().toString(); // spinner
                JCGSQLiteHelper_Leads h = new JCGSQLiteHelper_Leads(this);

                // check ----------------
                check();
                if (cancel==true) {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    check=true;
                }else{

                    AsyncCallWS1 task = new AsyncCallWS1();
                    task.execute();
                    Em.setId(h.getAllLeads().size()+1);
                    Em.setCommertialName(comname);
                    Em.setPhone(ph);
                    Em.setEmail(mail);
                    Em.setComment(com);
                    Em.setEm_Opcrm_Current_Address(address);
                    Em.setInterestedIn(interest);
                    Em.setLeadSource(leadS);
                    Em.setAssignedTo(assign);
                    Em.setStatus(statusspin);
                    Em.setTitle(tit);
                    Em.setEm_Opcrm_Project_id(projectid);
                    Em.setEm_Opcrm_Round_id(roundid);
                    Em.setLeadId(getleadidwithinsert);

                    h.insertEvent(Em);

                    check=false;
                }

            }
            if (check==false) {
                if (Main4Activity.editleadflag == true) {
                    Intent i = new Intent(this, Main4Activity.class);
                    i.putExtra("leadid", lid);
                    i.putExtra("leadname", lidname);
                    startActivity(i);
                    finish();
                } else if (MainActivity.fabFlag == true) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Window", "1");
                    startActivity(i);
                    finish();
                } else if (MainActivity.navFlag == true) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Window", "1");
                    startActivity(i);
                    finish();
                }
            }

        }  else if (id==R.id.close){
               if (Main4Activity.editleadflag==true ){
                   Intent i = new Intent(this, Main4Activity.class);
                   i.putExtra("leadid", lid);
                   i.putExtra("leadname",lidname);
                   startActivity(i);
                   finish();
                   return true;
                }  else if (MainActivity.fabFlag==true){
                   Intent i = new Intent(this, MainActivity.class);
                   i.putExtra("Window","1");
                   startActivity(i);
                   finish();
                   return true;
               } else if (MainActivity.navFlag==true ){
                   Intent i = new Intent(this, MainActivity.class);
                   i.putExtra("Window","1");
                   startActivity(i);
                   finish();
                   return true;
               }

            }  else if (id ==android.R.id.home) {
                if (Main4Activity.editleadflag==true ){
                    Intent i = new Intent(this, Main4Activity.class);
                    i.putExtra("leadid", lid);
                    i.putExtra("leadname",lidname);
                    startActivity(i);
                    finish();
                    return true;
                }else if (MainActivity.fabFlag==true){
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Window","1");
                    startActivity(i);
                    finish();
                    return true;
                } else if (MainActivity.navFlag==true ){
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Window","1");
                    startActivity(i);
                    finish();
                    return true;
                }
            }
        return false;
        //return super.onOptionsItemSelected(item);
    }
    private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            testInsert("/ws/it.openia.crm.insertLead?");

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
            testUpdate("/ws/it.openia.crm.updateLead?userid="+userId);

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
    // insert  connection
    public String testInsert(String content) {
        try {

            HttpURLConnection conn = createConnectioninsert( content);
            conn.connect();
            ArrayList<Object> list=new ArrayList<Object>();
            // ArrayList<String> list = new ArrayList<String>();

            list.add(0,commerialName.getText().toString());
            list.add(1,phone.getText().toString());
            list.add(2,email.getText().toString());
            list.add(3,comment.getText().toString());
            list.add(4,currentAddress.getText().toString());
            list.add(5,interestedInSpinner.getSelectedItem().toString());

            list.add(6,leadSource.getSelectedItem().toString());

            list.add(7,assignedTo.getSelectedItem().toString());

            if(!status.getSelectedItem().toString().equals(" ")) {
                leadStatus_spinner_helper db = new leadStatus_spinner_helper(this);
                List<leadStatus_spinner_model> leadStatusLists1 = db.getAllLeadStatus();
                for (int ii = 0; ii < leadStatusLists1.size(); ii++) {
                    if (leadStatusLists1.get(ii).getLeadStatusName().equals(status.getSelectedItem().toString()))
                        list.add(8, leadStatusLists1.get(ii).getLeadStatusValue());
                }
            }else{
                list.add(8,status.getSelectedItem()+"");

            }
            if (!title.getSelectedItem().toString().equals(" ")) {
                title_helper dbt = new title_helper(this);
                title_Model titleLists1 = null;
                List<title_Model> listtest = dbt.getAllTitles();
                for (int iii = 0; iii < listtest.size(); iii++) {
                    try {
                        titleLists1 = dbt.readtitleSpinner(iii + 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (titleLists1.getTitleTranslationName().equals(title.getSelectedItem().toString()))
                        list.add(9, titleLists1.getTitleId());
                }
            }else{
                list.add(9, title.getSelectedItem().toString());
            }

            list.add(10,roundID.getSelectedItem().toString());
            list.add(11,projectID.getSelectedItem().toString());

//            if (!nationality.getSelectedItem().toString().equals(" ")) {
//                nationality_helper db8 = new nationality_helper(this);
//                nationality_Model nLists1 = null;
//                List<String> list8 = new ArrayList<String>();
//                for (int ii = 0; ii < db8.getAllnationality().size(); ii++) {
//                    try {
//                        nLists1 = db8.readcountrySpinner(ii + 1);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    list8.add(nLists1.getCountryName());
//                    if (nLists1.getCountryName().equals(nationality.getSelectedItem().toString()))
//                        list.add(12, nLists1.getNationalityId());
//                }
//            }else {
                list.add(12, "");
           // }


         //   list.add(8,leadid);
            JSONArray jsArray = new JSONArray(list);


            OutputStream os = conn.getOutputStream();

            String s=jsArray.toString();



            os.write(s.getBytes());
            os.flush();

//            // get id lead after insert
            try {
                BufferedReader  reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = reader.readLine();

                JSONArray newarray = new JSONArray(line);

                    getleadidwithinsert=newarray.getString(0);

            } catch (IOException e) {
            }





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

            list.add(0,commerialName.getText().toString());
            list.add(1,phone.getText().toString());
            list.add(2,email.getText().toString());
            list.add(3,comment.getText().toString());
            list.add(4,currentAddress.getText().toString());
            list.add(5,leadid);

            if (!leadSource.getSelectedItem().toString().equals(" ")){
            leadSource_spinner_helper dbLS = new leadSource_spinner_helper(this);
            leadSource_spinner_model LSLists = null;
            List<leadSource_spinner_model> lslisttest= dbLS.getAllLeadSource();
            for (int i1 = 0; i1 < lslisttest.size(); i1++) {
                LSLists = dbLS.readleadSourceSpinner(i1 + 1);
                if (LSLists.getlSTranslationName().equals(leadSource.getSelectedItem().toString()))
                    list.add(6, LSLists.getlSId());
            }
            }else {
                list.add(6, leadSource.getSelectedItem().toString());
            }

            list.add(7,assignedTo.getSelectedItem().toString());
            list.add(8,status.getSelectedItem().toString());


            if (!title.getSelectedItem().toString().equals(" ")) {
                title_helper dbt = new title_helper(this);
                title_Model titleLists1 = null;
                List<title_Model> listtest = dbt.getAllTitles();
                for (int iii = 0; iii < listtest.size(); iii++) {
                    try {
                        titleLists1 = dbt.readtitleSpinner(iii + 1);
                    } catch (JSONException e) {

                    }
                    if (titleLists1.getTitleTranslationName().equals(title.getSelectedItem().toString()))
                        list.add(9, titleLists1.getTitleId());
                }
            }else {
                list.add(9,title.getSelectedItem().toString());
            }

            if (!roundID.getSelectedItem().toString().equals(" ")) {
                round_helper db7 = new round_helper(this);
                round_Model ridLists1 = null;
                List<String> list7 = new ArrayList<String>();
                for (int ii = 0; ii < db7.getAllRoundIds().size(); ii++) {
                    try {
                        ridLists1 = db7.readroundSpinner(ii + 1);
                    } catch (JSONException e) {

                    }
                    list7.add(ridLists1.getRoundName());
                    if (ridLists1.getRoundName().equals(roundID.getSelectedItem().toString()))
                        list.add(10, ridLists1.getRoundId());
                }
            }else {
                list.add(10, roundID.getSelectedItem().toString());
            }

            if (!projectID.getSelectedItem().toString().equals(" ")) {
                project_helper db6 = new project_helper(this);
                project_Model pidLists1 = null;
                for (int ii = 0; ii < db6.getAllprojectIds().size(); ii++) {
                    try {
                        pidLists1 = db6.readprojectSpinner(ii + 1);
                    } catch (JSONException e) {

                    }
                    if (pidLists1.getProjectName().equals(projectID.getSelectedItem().toString()))
                        list.add(11, pidLists1.getProjectId());
                }
            }else {
                list.add(11,projectID.getSelectedItem().toString());
            }

//            if (!nationality.getSelectedItem().toString().equals(" ")) {
//                nationality_helper db8 = new nationality_helper(this);
//                nationality_Model nLists1 = null;
//                List<String> list8 = new ArrayList<String>();
//                //   if (db8.getAllnationality().size()>0)
//                for (int ii = 0; ii < db8.getAllnationality().size(); ii++) {
//                    try {
//                        nLists1 = db8.readcountrySpinner(ii + 1);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    list8.add(nLists1.getCountryName());
//                    if (nLists1.getCountryName().equals(nationality.getSelectedItem().toString()))
//                        list.add(12, nLists1.getNationalityId());
//                }
//            }else {
                list.add(12,"");
          //  }

            if (!interestedInSpinner.getSelectedItem().toString().equals(" ")) {
                interestedIn_helper db4 = new interestedIn_helper(this);
                interestedIn_Model interestLists1 = null;
                for (int ii = 0; ii < db4.getAllInterestedIn().size(); ii++) {
                    try {
                        interestLists1 = db4.readinterestedInSpinner(ii + 1);
                    } catch (JSONException e) {

                    }
                    if (interestLists1.getInterestedInTranslationName().equals(interestedInSpinner.getSelectedItem().toString()))
                        list.add(13, interestLists1.getInterestedInId());
                }
            }else {
                list.add(13,interestedInSpinner.getSelectedItem().toString());
            }

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
    public HttpURLConnection createConnectioninsert(String wsPart) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname, passw.toCharArray());
            }
        });

        final URL url = new URL(uRl + wsPart);
        final HttpURLConnection hc = (HttpURLConnection) url.openConnection();
      //  hc.setRequestMethod(method);
        hc.setAllowUserInteraction(false);
        hc.setDefaultUseCaches(false);
        hc.setDoOutput(true);
        hc.setDoInput(true);
        hc.setInstanceFollowRedirects(true);
        hc.setUseCaches(false);
        hc.setRequestProperty("Content-Type", "text/xml");
        return hc;
    }
    public HttpURLConnection createConnection(String wsPart, String method) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname, passw.toCharArray());
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
        if (Main4Activity.editleadflag==true ){
            Intent i = new Intent(this, Main4Activity.class);
            i.putExtra("leadid", lid);
            i.putExtra("leadname",lidname);
            startActivity(i);
            finish();
        }
        else if (MainActivity.fabFlag==true){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","1");
            startActivity(i);
            finish();
        }
        else if (MainActivity.navFlag==true ){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","1");
            startActivity(i);
            finish();
        }
    }
}
