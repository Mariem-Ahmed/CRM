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

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Leads;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Oppo;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.opportunityModedel;
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
public class opportunity extends AppCompatActivity {

    String oppid, oppleadid= null;

    static EditText closedate;
     EditText subjectName, amont , probablity;
    Spinner  relatedLead, assignedTo;
    int y ,z, x = 0;
    String oppoId ="0";
    public static opportunityModedel Em= new opportunityModedel();

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

boolean doneflag=false;
    // requried
    boolean check, cancel = false;
    View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opportunity);

     //   if (lid==null && lidname==null) {
            Intent ii3 = getIntent();
            lid = ii3.getStringExtra("leadidid");
            lidname = ii3.getStringExtra("ComName");

      //  }

      //  if (actid==null && aname==null) {
            Intent ii4 = getIntent();
            actid = ii4.getStringExtra("actidid");
            aname = ii4.getStringExtra("subName");
        oppoId =ii4.getStringExtra("iddd");
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

        if(MainActivity.opportunityflag==true&& MainActivity.listFlag==true  || lead_oppo_adapter.tabbedoppolead==true ) {
            if(LoginActivity.arabicflag==true ){
                setTitle("تعديل الفرصه");
            }
            else if(insertUrl.arflagURL==true ){
                setTitle("تعديل الفرصه");
            }else{
                setTitle(" Edit Opportunity");
            }
        }else{
            if (LoginActivity.arabicflag == true) {
                setTitle("اضافه فرصه ");
            } else if (insertUrl.arflagURL == true) {
                setTitle("اضافه فرصه ");
            } else {
                setTitle("Add Opportunity");
            }
        }

        subjectName = (EditText) findViewById(R.id.editText1);
        subjectName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                subjectName.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        //closeDate = (TextView) findViewById(R.id.textDate);
        amont = (EditText) findViewById(R.id.editText3);
        amont.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                amont.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        probablity = (EditText) findViewById(R.id.editText4);
        probablity.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                probablity.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        relatedLead = (Spinner) findViewById(R.id.relatedLeadSpinner);
        // change color of selected item
        relatedLead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        assignedTo = (Spinner) findViewById(R.id.assignedToSpinner);
        // change color of selected item
        assignedTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK); //Change selected text color
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        closedate= (EditText) findViewById(R.id.textcloseddate);
        closedate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                closedate.setError(null);
                cancel=false;
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        closedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        // --------------------------------------- set data -----------------------------
        Intent i = getIntent();

        String opplead = i.getStringExtra("oppleadid");
        oppleadid=opplead;
        String opid = i.getStringExtra("oppid");
        oppid=opid;

        // ---------------------------------------assignedTo-------------------------------------------------------------
        String assigne = i.getStringExtra("assignto");

        String oppAssignedToid = i.getStringExtra("oppAssignedToid");

        assignedTo_spinner_helper db1 = new assignedTo_spinner_helper(this);
        assignedTo_spinner_model assignedLists = null;
        List<String> list1 = new ArrayList<String>();
        for (int ii = 0; ii < db1.getAllAssignedTo().size(); ii++) {
            try {
                assignedLists = db1.readAssignedToSpinner(ii+1);
            } catch (JSONException e) {

            }
            list1.add(assignedLists.getUserName());
            if (assignedLists.getUserId().equals(oppAssignedToid)||assignedLists.getUserId().equals(userId))
                z = ii;
        }


        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedTo.setAdapter(spinnerAdapter1);
        assignedTo.setSelection(z);
        // ------------------------------r lead ----------------------------------------

        String rlead = i.getStringExtra("relatedLead");
        String leadnameid = i.getStringExtra("oppleadid");

        if (Main4Activity.fabcasesFlag==true){
            JCGSQLiteHelper_Leads db3 = new JCGSQLiteHelper_Leads(this);
            relatedLead_Model rleadLists1 = null;
            List<String> list3 = new ArrayList<String>();
            for (int i1 = 0; i1 < db3.getAllRLeads().size(); i1++) {
                rleadLists1 = db3.getAllRLeads().get(i1);
                list3.add(rleadLists1.getrLeadName());

                if (rleadLists1.getrLeadId().equals(lead_info_activity.leadid))
                    x = i1+1;
            }
            ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list3);
            spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            relatedLead.setAdapter(spinnerAdapter3);
            relatedLead.setSelection(x-1);

        }else{
            JCGSQLiteHelper_Leads db3 = new JCGSQLiteHelper_Leads(this);
            relatedLead_Model rleadLists1 = null;
            List<String> list3 = new ArrayList<String>();
            list3.add(0," ");
            for (int i1 = 0; i1 < db3.getAllRLeads().size(); i1++) {
                rleadLists1 = db3.getAllRLeads().get(i1);
                list3.add(rleadLists1.getrLeadName());

                if (rleadLists1.getrLeadId().equals(leadnameid))
                    x = i1+1;
            }
            ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list3);
            spinnerAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            relatedLead.setAdapter(spinnerAdapter3);
        }


// ---------------------------------------------------------------------
 if(MainActivity.listFlag==true  && MainActivity.fabFlag==false) {

         String subName = i.getStringExtra("subName");
         try {
             final String s = new String(subName.getBytes(), "UTF-8");
             subjectName.setText(s);
         } catch (UnsupportedEncodingException e) {

         }
         String closeD = i.getStringExtra("closedate");

         if (!closeD.isEmpty()) {
             //    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
             SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
             Date d = null;
             try {
                 d = output.parse(closeD);
             } catch (ParseException e) {
                 e.printStackTrace();
             }
             long millisecond = d.getTime();

             String currentDate = getDate(millisecond, "yyyy-MM-dd");

             closedate.setText(currentDate);
         } else {
             closedate.setText(closeD);
         }

         String amount = i.getStringExtra("amount");
         amont.setText(amount);

         String prob = i.getStringExtra("probablity");
         probablity.setText(prob);

   //  }
         assignedTo.setSelection(z - 1);

         relatedLead.setSelection(x);
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
    // ----------------------------------------------------------------------
    // Check for requried fields.
    public void check(){

        if (  MainActivity.navFlag==true || MainActivity.fabFlag==true || Main4Activity.fabcasesFlag==true && Main4Activity.editleadflag==false) {
            if (TextUtils.isEmpty(subjectName.getText())) {
                subjectName.setError(getString(R.string.error_field_required));
                focusView = subjectName;
                cancel = true;
            }

            if (TextUtils.isEmpty(closedate.getText())) {
                closedate.setError(getString(R.string.error_field_required));
                focusView = closedate;
                cancel = true;
            }
            if (TextUtils.isEmpty(amont.getText())) {
                amont.setError(getString(R.string.error_field_required));
                focusView = amont;
                cancel = true;
            }
            if (TextUtils.isEmpty(probablity.getText())) {
                probablity.setError(getString(R.string.error_field_required));
                focusView = probablity;
                cancel = true;
            }

        }
        //  return true;
    }

    // ---------------------------------------------------
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

             if (MainActivity.listFlag==true &&  MainActivity.fabFlag==false) {
                 doneflag=true;


                 String subname = subjectName.getText().toString();
                 String cdate = closedate.getText().toString();
                 String oppoamont = amont.getText().toString();
                 String oppopro = probablity.getText().toString();
                 String oppolead = relatedLead.getSelectedItem()+"";
                 String oppoassigned = assignedTo.getSelectedItem()+"";
                 String oppoid= oppotunity_fragment.oppoID;
                 Em.setId(Integer.parseInt(oppoId));
                 Em.setOppoSubject(subname);
                 Em.setOppoCloseDate(cdate);
                 Em.setOppoAmount(oppoamont);
                 Em.setOppoProbablity(oppopro);
                 JCGSQLiteHelper_Leads leads = new JCGSQLiteHelper_Leads(this);
                 if(leads.getLeadByName(oppolead).size()!=0)
                 Em.setOppoRelatedLead(leads.getLeadByName(oppolead).get(0).getLeadId());
                 else Em.setOppoRelatedLead("");
                 if(leads.getLeadByName(oppolead).size()!=0)
                 Em.setOppoRelatedLeadID(leads.getLeadByName(oppolead).get(0).getLeadId());
                 else Em.setOppoRelatedLead("");
                 Em.setOppoAssignedto(oppoassigned);


                 JCGSQLiteHelper_Oppo h = new JCGSQLiteHelper_Oppo(this);
                 h.updateEvent(Em);
                 AsyncCallWS task = new AsyncCallWS();
                 task.execute();

             } else {

                 String subname = subjectName.getText()+"";
                 String cdate = closedate.getText()+"";
                 String oppoamont = amont.getText()+"";
                 String oppopro = probablity.getText()+"";
                 String oppolead = relatedLead.getSelectedItem()+"";
                 String oppoassigned = assignedTo.getSelectedItem()+"";
                  JCGSQLiteHelper_Leads leads = new JCGSQLiteHelper_Leads(this);
                 Em.setOppoRelatedLead(leads.getLeadByName(oppolead).get(0).getLeadId());


                 JCGSQLiteHelper_Oppo h = new JCGSQLiteHelper_Oppo(this);

                 Em.setId(h.getAllOpportunity().size()+1);
                 Em.setOppoSubject(subname);
                 Em.setOppoCloseDate(cdate);
                 Em.setOppoAmount(oppoamont);
                 Em.setOppoProbablity(oppopro);
                 if(leads.getLeadByName(oppolead).size()!=0)
                     Em.setOppoRelatedLead(leads.getLeadByName(oppolead).get(0).getLeadId());
                 else Em.setOppoRelatedLead("");
                 if(leads.getLeadByName(oppolead).size()!=0)
                     Em.setOppoRelatedLeadID(leads.getLeadByName(oppolead).get(0).getLeadId());
                 else Em.setOppoRelatedLead("");
                 Em.setOppoAssignedto(oppoassigned);


                 // check ----------------
                 check();
                 if (cancel==true) {
                     Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                     // cancel=false;
                     check=true;

                 }else{


                     h.insertEvent(Em);
                     check=false;
                     AsyncCallWS1 task = new AsyncCallWS1();
                     task.execute();

                 }

             }
            if (check==false) {
                if (MainActivity.listFlag == true || MainActivity.navFlag == true) {
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("Window", "4");
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
                    i.putExtra("Window", "4");
                    startActivity(i);
                    finish();
                }
            }


        }
        else if (id==R.id.close){

            if (Main4Activity.fabcasesFlag==true  || lead_oppo_adapter.tabbedoppolead==true){
                Intent i = new Intent(this, Main4Activity.class);
                i.putExtra("leadid", lid);
                i.putExtra("leadname",lidname);
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.listFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","4");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.fabFlag==true){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","4");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.navFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","4");
                startActivity(i);
                finish();
                return true;
            }

        }
        else if (id ==android.R.id.home) {

            if (Main4Activity.fabcasesFlag==true  || lead_oppo_adapter.tabbedoppolead==true){
                Intent i = new Intent(this, Main4Activity.class);
                i.putExtra("leadid", lid);
                i.putExtra("leadname",lidname);
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.listFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","4");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.fabFlag==true){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","4");
                startActivity(i);
                finish();
                return true;
            }
            else if (MainActivity.navFlag==true ){
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("Window","4");
                startActivity(i);
                finish();
                return true;
            }

        }


        return false;
        //return super.onOptionsItemSelected(item);
    }

    // insert
    private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            testinsert("/ws/it.openia.crm.insertOppo?");
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
// update
    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            testUpdate("/ws/it.openia.crm.updateOppo?");
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
    public String testinsert(String content) {
        try {

            HttpURLConnection conn = createConnection( content,"POST");
            conn.connect();
            ArrayList<Object> list=new ArrayList<Object>();
            // ArrayList<String> list = new ArrayList<String>();

            list.add(0,subjectName.getText().toString());
            list.add(1, closedate.getText().toString());
            list.add(2, amont.getText().toString());
            list.add(3,probablity.getText().toString());
            list.add(4,relatedLead.getSelectedItem()+"");
            list.add(5,assignedTo.getSelectedItem()+"");
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

            list.add(0,subjectName.getText().toString());
            list.add(1, closedate.getText().toString());
            list.add(2, amont.getText().toString());
            list.add(3,probablity.getText().toString());
            list.add(4,oppleadid);

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
                if (assignedLists.getUserName().equals(assignedTo.getSelectedItem()+"")) {
                    as = assignedLists.getUserId().toString();
                    list.add(5,as);
                }
            }

            list.add(6,oppid);

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

            closedate .setText(year + "-" + month + "-" + day);

        }
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

// -------------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        if (Main4Activity.fabcasesFlag==true  || lead_oppo_adapter.tabbedoppolead==true){
            Intent i = new Intent(this, Main4Activity.class);
            i.putExtra("leadid", lid);
            i.putExtra("leadname",lidname);
            startActivity(i);
            finish();
        }
        else if (MainActivity.listFlag==true ){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","4");
            startActivity(i);
            finish();
        }
        else if (MainActivity.fabFlag==true){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","4");
            startActivity(i);
            finish();
        }
        else if (MainActivity.navFlag==true ){
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","4");
            startActivity(i);
            finish();
        }
    }
}
