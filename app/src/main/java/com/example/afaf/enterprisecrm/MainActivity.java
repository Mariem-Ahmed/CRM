package com.example.afaf.enterprisecrm;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;
import com.example.afaf.enterprisecrm.Helper_Database.activity_Status_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.activity_Type_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.casePriority_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.caseStatus_helper;
import com.example.afaf.enterprisecrm.Helper_Database.interestedIn_helper;
import com.example.afaf.enterprisecrm.Helper_Database.leadSource_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.leadStatus_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.notification_helper;
import com.example.afaf.enterprisecrm.Helper_Database.project_helper;
import com.example.afaf.enterprisecrm.Helper_Database.relatedLead_helper;
import com.example.afaf.enterprisecrm.Helper_Database.round_helper;
import com.example.afaf.enterprisecrm.Helper_Database.title_helper;

import org.json.JSONArray;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.StringTokenizer;

public class  MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener, SearchView.OnQueryTextListener {

    public static LinearLayout lin;
    // -------------------------------noification_activity -------------------
    String actnotifName;

    notification_helper dbActNotif = new notification_helper(this);
    String notifSubject = "";
    String notifDesc = "";
    String sentFlag = "";
    String updatedDate = "";
    String createdDate = "";
    public static String actNotifId = "";

    //------------------- url -------------------------------------------------------------
    public static final String MyPREFERENCES = "MyPrefs" ;
  //  public static final String URL = "nameKey";

    SharedPreferences sharedpreferences;
    String uRl="";
// ----------------------- log in ------------------------------------------------------
    public static final String LoginPREFERENCES = "LoginPrefs" ;

    SharedPreferences sharedpreferencesLogin;
    String uname="";
    String passw="";
    String userId2="";

    // -----------------------------------------------------------------------------------
    // activity Type status
    activity_Type_spinner_helper dbActType = new activity_Type_spinner_helper(this);
    String actTypeId="";
    String actTypeKey="";
    String actTypeName="";

    // leadsource
    leadSource_spinner_helper dbLeadSource = new leadSource_spinner_helper(this);
    String lSTranslationName="";
    String lSId="";
    String lSOrder="";

    // assignedTo
    assignedTo_spinner_helper dbAssignedTo = new assignedTo_spinner_helper(this);
    String userName="";
    String userId="";


    // leadStatus
    leadStatus_spinner_helper dbLeadStatus = new leadStatus_spinner_helper(this);
    String leadStatusId="";
    String leadStatusName="";
    String leadStatusValue="";

    // case periority
    casePriority_spinner_helper dbCasePeriority = new casePriority_spinner_helper(this);
    String casePeriorityValue="";
    String casePeriorityName="";
    String casePeriorityId="";

    // lcaseStatus
    caseStatus_helper dbCaseStatus = new caseStatus_helper(this);
    String caseStatusId="";
    String caseStatusName="";
    String caseStatusValue="";
    // activity status
    activity_Status_spinner_helper dbActivitystatus = new activity_Status_spinner_helper(this);

    String activityStatusname="";
    String activityStatusId="";
    String activityStatusSearchKey="";

    // --------------------------------------------------------
    // interested in
    interestedIn_helper dbInterestedIn = new interestedIn_helper(this);
    String interestedInTranslationName="";
    String interestedInId="";
    String interestedInOrder="";

    // title
    title_helper dbTitle = new title_helper(this);
    String titleTranslationName="";
    String titleId="";
    String titleOrder="";

    // round id
    round_helper dbRound= new round_helper(this);
    String roundSearchkey="";
    String roundName="";
    String roundId="";

    // project id
    project_helper dbProject = new project_helper(this);
    String projectSearchkey="";
    String projectName="";
    String projectId="";

//    // nationality
//    nationality_helper dbNationality = new nationality_helper(this);
//    String isoCountrycode="";
//    String countryName="";
//    String nationalityId="";

    // relaed lead
    relatedLead_helper dbRLead = new relatedLead_helper(this);
    String rLeadName="";
    String rLeadId="";

    // --------------------------------------------------------
    private RadioButton rd1,rd2,rd3,rd5,rd4,rd6,rd7,rd8,rd9;
     Dialog dialog;


    // -------------------------------------------------------------------------------------
    private FloatingActionButton fab;
    private Animation rotate_forward;
    public static Boolean listFlag,fabFlag, navFlag, casesflag, opportunityflag =false;
    public static Boolean lead,activities, cases,oppo,calender , dashboard=false;
    public static Boolean dialoglead,dialogactivity, dialogcases,dialogoppo,dialogcalender, actionbar =false;
// ---------------------------------------------------------------------------------------------------------

    ArrayAdapter<String> myAdapter;
    ListView listView;
    MenuItem searchItem = null;
     SearchView searchView = null;

    // -----------------------------------------------------configration -----------------------------
    public static  boolean urlFlag=false;

    // ----------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lin= (LinearLayout) findViewById(R.id.lin);
        //-----------------------------url &  login  ------------------------------------------
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl= sharedpreferences.getString("URL",null);
        if (uRl != null) {
            sharedpreferencesLogin = getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
            uname = sharedpreferencesLogin.getString("uName", null);
            passw = sharedpreferencesLogin.getString("passWord", null);
            userId2 = sharedpreferencesLogin.getString("userId", null);


            if (uname == null && passw == null) {
                Intent i1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i1);
                finish();

            }
        }
        else {
            Intent i = new Intent(getApplicationContext(), inserturl_noparent.class);
            startActivity(i);
            finish();

        }

// -------------------calling excute--------------------------------------------------
        if (uRl != null&&uname!=null&&passw!=null) {
            if(isNetworkAvailable()==true) {
                AsyncCallWS task = new AsyncCallWS();
                task.execute();
            }
        }
// -------------------------------------------------------------------------------------------
        fab = (FloatingActionButton)findViewById(R.id.fab);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        fab.setOnClickListener(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // username in nav view
        View hView =  navigationView.getHeaderView(0);
        TextView t= (TextView)hView.findViewById(R.id.userName);
        t.setText(uname);
// -----------------------------------------------nav action------------------------------

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_leads);
        final View actionView = MenuItemCompat.getActionView(menuItem);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,leads_form.class));
                actionView.startAnimation(rotate_forward);
              navFlag=true;
              fabFlag=false;
                listFlag=false;
                Main4Activity.editleadflag=false;
                opportunityflag= false;
                casesflag= false;
                Main4Activity.fabcasesFlag=false ;
                Main5Activity.fabactivitiesFlag=false ;
            }
        });

        Menu menu1 = navigationView.getMenu();
        MenuItem menuItem1 = menu1.findItem(R.id.nav_activities);
        final View actionView1 = MenuItemCompat.getActionView(menuItem1);
        actionView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,acitvity.class));
                actionView1.startAnimation(rotate_forward);
                navFlag=true;
                fabFlag=false;
                listFlag=false;
                Main4Activity.editleadflag=false;
                opportunityflag= false;
                casesflag= false;
                Main4Activity.fabcasesFlag=false ;
                Main5Activity.fabactivitiesFlag=false ;
            }
        });
        Menu menu2 = navigationView.getMenu();
        MenuItem menuItem2 = menu.findItem(R.id.nav_cases);
        final View actionView2 = MenuItemCompat.getActionView(menuItem2);
        actionView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,cases.class));
                actionView2.startAnimation(rotate_forward);
                navFlag=true;
                fabFlag=false;
                listFlag=false;
                Main4Activity.editleadflag=false;
                opportunityflag= false;
                casesflag= true;
                Main4Activity.fabcasesFlag=false ;
                Main5Activity.fabactivitiesFlag=false ;
            }
        });
        Menu menu3 = navigationView.getMenu();
        MenuItem menuItem3 = menu.findItem(R.id.nav_opportunity);
        final View actionView3 = MenuItemCompat.getActionView(menuItem3);
        actionView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,opportunity.class));
                actionView3.startAnimation(rotate_forward);
                navFlag=true;
                fabFlag=false;
                listFlag=false;
                Main4Activity.editleadflag=false;
                opportunityflag= false;
                casesflag= false;
                Main4Activity.fabcasesFlag=false ;
                Main5Activity.fabactivitiesFlag=false ;
            }
        });

// -----------------activities as default ------------------
        setcontent();

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onResume(){
        super.onResume();

        if (uRl != null) {
            Intent intent = new Intent(this, MyBroadcastReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            int interval = 2000000;
            manager.setRepeating(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(), interval, pendingIntent);
        }

    }
// ------------------------------------------------------------------------------------
    private void setcontent() {

        Intent intent =getIntent();
        String director = intent.getStringExtra("Window");
        if(director!=null)
        {
            if(director.equals("1")){
                Fragment fragment = new leads_fragment("",null);
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                if(LoginActivity.arabicflag==true ){
                    setTitle("العملاء المحتملون");
                }
                else if(insertUrl.arflagURL==true ){
                    setTitle("العملاء المحتملون");
                }else{
                    setTitle("Leads");
                }




            }
            else if(director.equals("2")){
                Fragment  fragment =  new activity_fragment();

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();

                if(LoginActivity.arabicflag==true ){
                    setTitle("الانشطه");
                }
                else if(insertUrl.arflagURL==true ){
                    setTitle("الانشطه");
                }else{
                    setTitle("Activities");
                }
                listFlag = true;
                navFlag = false;
                fabFlag = false;
            }
            else if(director.equals("3")){
                Fragment  fragment =  new cases_fragment();

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();

                if(LoginActivity.arabicflag==true ){
                    setTitle("الحالات");
                }
                else if(insertUrl.arflagURL==true ){
                    setTitle("الحالات");
                }else{
                    setTitle("Cases");
                }
                listFlag = true;
                navFlag = false;
                fabFlag = false;
            }

            else if(director.equals("4")){
                Fragment  fragment =  new oppotunity_fragment("",null);

                FragmentManager fragmentManager = getSupportFragmentManager();


                fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();

                if(LoginActivity.arabicflag==true ){
                    setTitle("الفرص");
                }
                else if(insertUrl.arflagURL==true ){
                    setTitle("الفرص");
                }else{
                    setTitle("Opportunities");
                }
                listFlag = true;
                navFlag = false;
                fabFlag = false;
            }
        }
        else {
            Fragment  fragment =  new Dashboard();

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();

            if(LoginActivity.arabicflag==true ){
                setTitle("الرئيسية");
            }
            else if(insertUrl.arflagURL==true ){
                setTitle("الرئيسية");
            }else{
                setTitle("Dashboard");
            }
            listFlag = false;
            navFlag = false;
            fabFlag = false;

            dashboard=true;

            dialoglead=true;
            dialogactivity=false;
            dialogcalender=false;
            dialogoppo=false;
            dialogcases=false;
            opportunityflag= false;
            Main4Activity.fabcasesFlag=false;
            JCGSQLiteHelper.listFlagInsert=true;
            Main5Activity.fabactivitiesFlag=false;
            Main5Activity.editflag=false;
            Main4Activity.editleadflag=false;

            casesflag=false;
            /////////////
            lead=false;
            activities=false;
            cases=false;
            calender=false;
            oppo=false;


        }
    }

    // -------------------------------- fab action ---------------------
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Fragment title = null;
        switch (id) {
            case R.id.fab:
                listFlag=false;
                fabFlag=true;
                navFlag=false;
                Main5Activity.fabactivitiesFlag=false;
                Main4Activity.fabcasesFlag=false;
                Main4Activity.editleadflag=false;
                Main5Activity.editflag=false;
                opportunityflag= false;
                casesflag= false;

                if(dashboard==true){
//    Intent intent = new Intent(this,acitvity.class);
//    startActivity(intent);
                // animateFAB();
               }else if (activities==true){
                    Intent intent = new Intent(this,acitvity.class);
                     startActivity(intent);
                     finish();
                }
                else if (lead==true){
                    Intent intent = new Intent(this,leads_form.class);
                    startActivity(intent);
                    finish();
                }
                else if (cases==true){
                    Intent intent = new Intent(this,cases.class);
                    startActivity(intent);
                    finish();
                }
                else if (calender==true){
                   Fragment fragment =  new calender();
                    setTitle("Calender");

                    FragmentManager fragmentManager = getSupportFragmentManager();

                    fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                }
                else if (oppo==true){
                    Intent intent = new Intent(this,opportunity.class);
                    startActivity(intent);
                    finish();
                }

                break;


        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

       }

         else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);

         searchItem = menu.findItem(R.id.action_search);

        if (searchItem != null) {
              searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    //some operation
                    return false;
                }
            });
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //some operation
                }
            });

            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            // use this method for search process
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {

                    if(activities==true) {
                        Fragment fragment = new activity_fragment("", newText);
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                        try {
                            Thread.sleep(500);

                        } catch (InterruptedException e) {

                        }
                    }
                    else if(cases==true){
                        Fragment fragment = new cases_fragment("", newText);
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                        try {
                            Thread.sleep(500);

                        } catch (InterruptedException e) {

                        }
                    }
                    else if(oppo==true){
                        Fragment fragment = new oppotunity_fragment("", newText);
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                        try {
                            Thread.sleep(500);

                        } catch (InterruptedException e) {

                        }
                    }
                    else if(lead==true){
                        Fragment fragment = new leads_fragment("", newText);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                        try {
                            Thread.sleep(500);

                        } catch (InterruptedException e) {

                        }
                    }
                        return true;

                }

            });


        }
        return super.onCreateOptionsMenu(menu);
    }


//-----------------------------------------------------end of search-------------------------------------------
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id== R.id.action_filter) {

            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog);

            rd1 = (RadioButton) findViewById(R.id.call);
            rd2 = (RadioButton) findViewById(R.id.meeting);
            rd3 = (RadioButton) findViewById(R.id.task);
            rd4 = (RadioButton) findViewById(R.id.email);
            rd5 = (RadioButton) findViewById(R.id.facebook);
            rd6 = (RadioButton) findViewById(R.id.sms);
            rd7 = (RadioButton) findViewById(R.id.viber);
            rd8 = (RadioButton) findViewById(R.id.whatsapp);
            rd9 = (RadioButton) findViewById(R.id.oppo);

            TextView dialogButton = (TextView) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();


                }
            });

            dialog.show();
        }



            return super.onOptionsItemSelected(item);
    }
    public void onRadioButtonClicked(View v)
    {
        //is the current radio button now checked?
        boolean  checked = ((RadioButton) v).isChecked();

        Fragment fragment = null;
        switch(v.getId()){

            case R.id.call:
                if(checked) {

                    if (activity_fragment.dialogactivity == true) {
                        if (activity_fragment.dialogactivity == true) {

                            fragment = new activity_fragment("CALL","");
                            FragmentManager fragmentManager = getSupportFragmentManager();

                            fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                        }
                        try {
                            Thread.sleep(500);
                            dialog.dismiss();
                        } catch (InterruptedException e) {

                        }
                    }
                    break;
                }
                break;

            case R.id.meeting:
                if(checked) {

                    if (activity_fragment.dialogactivity == true) {

                        fragment = new activity_fragment("MEETING","");
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                    }
                    try {
                        Thread.sleep(500);
                        dialog.dismiss();
                    } catch (InterruptedException e) {
                    }
                }
                break;

            case R.id.task:
                if(checked){
                    if (activity_fragment.dialogactivity == true) {

                        fragment = new activity_fragment("TASK","");
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                    }
                    try {
                        Thread.sleep(500);
                        dialog.dismiss();
                    } catch (InterruptedException e) {

                    }
                }

                break;
            case R.id.email:
                if(checked){
                    if (activity_fragment.dialogactivity == true) {

                        fragment = new activity_fragment("EMAIL","");
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                    }
                    try {
                        Thread.sleep(500);
                        dialog.dismiss();
                    } catch (InterruptedException e) {

                    }
                }

                break;
            case R.id.facebook:
                if(checked){
                    if (activity_fragment.dialogactivity == true) {

                        fragment = new activity_fragment("FACEBOOK","");
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                    }
                    try {
                        Thread.sleep(500);
                        dialog.dismiss();
                    } catch (InterruptedException e) {

                    }
                }

                break;
            case R.id.sms:
                if(checked){
                    if (activity_fragment.dialogactivity == true) {

                        fragment = new activity_fragment("SMS","");
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                    }
                    try {
                        Thread.sleep(500);
                        dialog.dismiss();
                    } catch (InterruptedException e) {

                    }
                }

                break;
            case R.id.viber:
                if(checked){
                    if (activity_fragment.dialogactivity == true) {

                        fragment = new activity_fragment("VIBER","");
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                    }
                    try {
                        Thread.sleep(500);
                        dialog.dismiss();
                    } catch (InterruptedException e) {

                    }
                }

                break;
            case R.id.whatsapp:
                if(checked){
                    if (activity_fragment.dialogactivity == true) {

                        fragment = new activity_fragment("WHATSAPP","");
                        FragmentManager fragmentManager = getSupportFragmentManager();


                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                    }
                    try {
                        Thread.sleep(500);
                        dialog.dismiss();
                    } catch (InterruptedException e) {

                    }
                }

                break;
            case R.id.oppo:
                if(checked){
                    if (activity_fragment.dialogactivity == true) {

                        fragment = new activity_fragment("OPPORTUNITY","");
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
                    }
                    try {
                        Thread.sleep(500);
                        dialog.dismiss();
                    } catch (InterruptedException e) {

                    }
                }

                break;
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        switch(item.getItemId()) {
            case R.id.nav_dashboard:

                fragment = new Dashboard();
                dashboard=true;

                navFlag=false;
                fabFlag=false;
                listFlag=true;
                dialoglead=true;
                dialogactivity=false;
                dialogcalender=false;
                dialogoppo=false;
                dialogcases=false;
                opportunityflag= false;
                Main4Activity.fabcasesFlag=false;
                JCGSQLiteHelper.listFlagInsert=true;
                Main5Activity.fabactivitiesFlag=false;
                Main5Activity.editflag=false;
                Main4Activity.editleadflag=false;

                casesflag=false;
                /////////////
                lead=false;
                activities=false;
                cases=false;
                calender=false;
                oppo=false;

                break;
            case R.id.nav_calender:

                fragment = new CalenderFragment();

                break;
            case R.id.nav_leads:
                actionbar=false;


                fragment = new leads_fragment("",null);

                navFlag=false;
                fabFlag=false;
                listFlag=true;
                dialoglead=true;
                dialogactivity=false;
                dialogcalender=false;
                dialogoppo=false;
                dialogcases=false;
                opportunityflag= false;
                Main4Activity.fabcasesFlag=false;
                JCGSQLiteHelper.listFlagInsert=true;
                Main5Activity.fabactivitiesFlag=false;
                Main5Activity.editflag=false;
                Main4Activity.editleadflag=false;

                casesflag=false;
                /////////////
                lead=true;
                activities=false;
                cases=false;
                calender=false;
                oppo=false;
                dashboard=false;

                break;
            case R.id.nav_activities:
                actionbar=false;
                fragment =  new activity_fragment();
                navFlag=false;
                fabFlag=false;
                listFlag=true;
                JCGSQLiteHelper.listFlagInsert=true;
                Main4Activity.fabcasesFlag=false;
                Main5Activity.fabactivitiesFlag=false;
                Main5Activity.editflag=false;
                Main4Activity.editleadflag=false;
                casesflag=false;
                opportunityflag= false;
                dialoglead=false;
                dialogactivity=true;
                dialogcalender=false;
                dialogoppo=false;
                dialogcases=false;
                /////////////
                lead=false;
                activities=true;
                cases=false;
                calender=false;
                oppo=false;
                dashboard=false;

                break;
            case R.id.nav_cases:
                actionbar=false;
                fragment =  new cases_fragment();
                navFlag=false;
                fabFlag=false;
                listFlag=true;
                JCGSQLiteHelper.listFlagInsert=true;
                Main4Activity.fabcasesFlag=false;
                Main5Activity.fabactivitiesFlag=false;
                Main5Activity.editflag=false;
                Main4Activity.editleadflag=false;
                casesflag=true;
                opportunityflag= false;
                dialogcases=true;
                dialoglead=false;
                dialogactivity=false;
                dialogcalender=false;
                dialogoppo=false;
                /////////////
                lead=false;
                activities=false;
                cases=true;
                calender=false;
                oppo=false;
                dashboard=false;

                break;
//            case R.id.nav_calender:
//                actionbar=true;
//                fragment =  new calender();
//                navFlag=false;
//                fabFlag=false;
//                listFlag=true;
//                JCGSQLiteHelper.listFlagInsert=true;
//                dialogcases=false;
//                dialoglead=false;
//                dialogactivity=false;
//                dialogcalender=true;
//                dialogoppo=false;
//                /////////////
//                lead=false;
//                activities=false;
//                cases=false;
//                calender=true;
//                oppo=false;
//
//                break;
            case R.id.nav_opportunity:
                actionbar=false;
                fragment =  new oppotunity_fragment("",null);
                navFlag=false;
                fabFlag=false;
                listFlag=true;
                JCGSQLiteHelper.listFlagInsert=true;
                Main4Activity.fabcasesFlag=false;
                Main5Activity.fabactivitiesFlag=false;
                Main5Activity.editflag=false;
                Main4Activity.editleadflag=false;
                opportunityflag= true;
                lead_oppo_adapter.tabbedoppolead=false;
                casesflag=false;
                dialogcases=false;
                dialoglead=false;
                dialogactivity=false;
                dialogcalender=false;
                dialogoppo=true;
                /////////////
                lead=false;
                activities=false;
                cases=false;
                calender=false;
                oppo=true;
                dashboard=false;

                break;
            case R.id.nav_insertUrl:
                urlFlag=true;

               Intent intent= new Intent(this, insertUrl.class);
                startActivity(intent);

                break;

            case R.id.nav_logout:

                if (LoginActivity.arabicflag==false) {
                    new AlertDialog.Builder(this)
                            .setTitle("Logout")
                            .setMessage("Would you like to logout?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    finish();
                                    SharedPreferences.Editor editor = sharedpreferencesLogin.edit();
                                    editor.putString("uName", null);
                                    editor.putString("passWord", null);
                                    editor.putString("userId", null);
                                    editor.commit();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent1);
                                    finish();

                                }
                            })
                            .show();
                }else{

                    new AlertDialog.Builder(this)
                            .setTitle("خروج")
                            .setMessage("هل تريد الخروج الان ؟")
                            .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    finish();
                                    SharedPreferences.Editor editor = sharedpreferencesLogin.edit();
                                    editor.putString("uName", null);
                                    editor.putString("passWord", null);
                                    editor.putString("userId", null);
                                    editor.commit();

                                }
                            })
                            .setNegativeButton("لا", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent1);
                                    finish();

                                }
                            })
                            .show();

                }
                break;

            default:
                fragment =  new Dashboard();
        }

if (item.getItemId()!=R.id.nav_insertUrl && item.getItemId()!=R.id.nav_logout ) {
    setTitle(item.getTitle());

    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.lin, fragment).commit();
    // Highlight the selected item has been done by NavigationView
}
        item.setChecked(true);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            activityTypeStatus("/ws/it.openia.crm.activityTypeSpinner?");
            leadSource("/ws/it.openia.crm.leadSourceSpinner?");
            assignedTo("/ws/it.openia.crm.assignedToSpinner?");
            LeadStatus("/ws/it.openia.crm.leadStatusSpinner?");
            casePeriority("/ws/it.openia.crm.casePrioritySpinner?");
            activityStatus("/ws/it.openia.crm.activityStatusSpinner?");
            GetRequest("/ws/it.openia.crm.activity_notification?");
            caseStatus("/ws/it.openia.crm.caseStatus?");
            interestedIn("/ws/it.openia.crm.interestedIn?");
            titleLeads("/ws/it.openia.crm.title_webservice?");
            roundID("/ws/it.openia.crm.roundId?");
            projectID("/ws/it.openia.crm.projectId?");
            //nationality("/ws/it.openia.crm.nationality?");
            relatedLead("/ws/it.openia.crm.relatedLead");


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

    // -------------------------------------Start of spinners------------------------------------------
    public HttpURLConnection createConnectionact(String wsPart, String method) throws Exception {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(uname, passw.toCharArray());
            }
        });

        final URL url = new URL(uRl+ wsPart);
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

    //--------------------------------activityType && status --------------------------------
    public String activityTypeStatus(String wsPart) {
        try {

            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbActType.onUpgrade(dbActType.getWritableDatabase(), 1, 2);

            final InputStream is = hc.getInputStream();
          //  final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;

            JSONArray newarray;
            String s;
            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();

                newarray = new JSONArray(s);

                if(newarray!=null){
                    actTypeKey  = newarray.getString(0);
                    actTypeName= newarray.getString(1);
                    actTypeId= newarray.getString(2);

                }

                dbActType.createActivitySpinner(actTypeKey,actTypeName,actTypeId);

            }


        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- LeadSource --------------------------------------
    public String leadSource(String wsPart) {
        try {
            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbLeadSource.onUpgrade(dbLeadSource.getWritableDatabase(), 1, 2);

            final InputStream is = hc.getInputStream();
            //  final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;


            JSONArray newarray;
            String s;

            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();

                newarray = new JSONArray(s);
                if(newarray!=null){
                    lSTranslationName=new String( newarray.getString(1).getBytes(), "UTF-8");
                    lSId= newarray.getString(0);
                   lSOrder = newarray.getString(2);


               }

                dbLeadSource.createleadSourceSpinner(lSTranslationName,lSId,lSOrder);
            }




        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- assigned To --------------------------------------
    public String assignedTo(String wsPart) {
        try {
            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbAssignedTo.onUpgrade(dbAssignedTo.getWritableDatabase(), 1, 2);

            final InputStream is = hc.getInputStream();
            //  final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;
            String f1;
            JSONArray newarray;
            String s;
            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();
                newarray = new JSONArray(s);
                if(newarray!=null){
                    userId = newarray.getString(0);
                    userName= newarray.getString(1);


                }


                dbAssignedTo.createAssignedToSpinner(userName,userId);

            }



        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- LeadStatus --------------------------------------
    public String LeadStatus(String wsPart) {
        try {

            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbLeadStatus.onUpgrade(dbLeadStatus.getWritableDatabase(), 1, 2);
            final InputStream is = hc.getInputStream();
            //  final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;
            String f1;
            JSONArray newarray;
            String s;
            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();
                newarray = new JSONArray(s);
                if(newarray!=null){
                    leadStatusValue = newarray.getString(0);
                    leadStatusName= newarray.getString(1);
                    leadStatusId= newarray.getString(2);

                }

                dbLeadStatus.createLeadStatusSpinner(leadStatusId,leadStatusName,leadStatusValue);
            }

        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- case Periority --------------------------------------
    public String casePeriority(String wsPart) {
        try {

            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbCasePeriority.onUpgrade(dbCasePeriority.getWritableDatabase(), 1, 2);
            final InputStream is = hc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;

            JSONArray newarray;
            String s;
            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();
                newarray = new JSONArray(s);
                if(newarray!=null){
                    casePeriorityValue = newarray.getString(0);
                    casePeriorityName= newarray.getString(1);
                    casePeriorityId= newarray.getString(2);

                }

                dbCasePeriority.createcasePerioritySpinner(casePeriorityId,casePeriorityName,casePeriorityValue);
            }

        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- activity status--------------------------------------
    public String activityStatus(String wsPart) {
        try {

            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbActivitystatus.onUpgrade(dbActivitystatus.getWritableDatabase(), 1, 2);
            final InputStream is = hc.getInputStream();
            //  final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;

            JSONArray newarray;
            String s;

            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();
                newarray = new JSONArray(s);
                if(newarray!=null){
                    activityStatusId=newarray.getString(0);
                    activityStatusname= newarray.getString(1);
                    activityStatusSearchKey= newarray.getString(2);
                }

                dbActivitystatus.createactivityStatusSpinner(activityStatusname,activityStatusId, activityStatusSearchKey);
            }




        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- CASEStatus --------------------------------------
    public String caseStatus(String wsPart) {
        try {

            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbCaseStatus.onUpgrade(dbCaseStatus.getWritableDatabase(), 1, 2);
            final InputStream is = hc.getInputStream();
            //  final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;
            String f1;
            JSONArray newarray;
            String s;
            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();
                newarray = new JSONArray(s);
                if(newarray!=null){
                    caseStatusValue   = newarray.getString(0);
                    caseStatusName= newarray.getString(1);
                    caseStatusId = newarray.getString(2);

                }

                dbCaseStatus.createCaseStatusSpinner(caseStatusId,caseStatusName,caseStatusValue);
            }

        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- interested in  --------------------------------------
    public String interestedIn(String wsPart) {
        try {

            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbInterestedIn.onUpgrade(dbInterestedIn.getWritableDatabase(), 1, 2);
            final InputStream is = hc.getInputStream();
            //  final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;


            JSONArray newarray;
            String s;

            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();

                newarray = new JSONArray(s);
                if(newarray!=null){
                    interestedInTranslationName=new String( newarray.getString(1).getBytes(), "UTF-8");
                    interestedInId= newarray.getString(0);
                    interestedInOrder = newarray.getString(2);


                }

                dbInterestedIn.createinterestedInSpinner(interestedInTranslationName,interestedInId,interestedInOrder);
            }




        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- title  --------------------------------------
    public String titleLeads(String wsPart) {
        try {

            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbTitle.onUpgrade(dbTitle.getWritableDatabase(), 1, 2);
            final InputStream is = hc.getInputStream();
            //  final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;


            JSONArray newarray;
            String s;

            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();

                newarray = new JSONArray(s);
                if(newarray!=null){
                    titleTranslationName=new String( newarray.getString(1).getBytes(), "UTF-8");
                    titleId= newarray.getString(0);
                    titleOrder = newarray.getString(2);


                }

                dbTitle.createtitleSpinner(titleTranslationName,titleId,titleOrder);
            }




        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- round id   --------------------------------------
    public String roundID(String wsPart) {
        try {

            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbRound.onUpgrade(dbRound.getWritableDatabase(), 1, 2);
            final InputStream is = hc.getInputStream();
            //  final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;


            JSONArray newarray;
            String s;

            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();

                newarray = new JSONArray(s);
                if(newarray!=null){
                    roundSearchkey=new String( newarray.getString(1).getBytes(), "UTF-8");
                    roundId= newarray.getString(0);
                    roundName = newarray.getString(2);


                }

                dbRound.createroundSpinner(roundSearchkey,roundId,roundName);
            }




        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- project id   --------------------------------------
    public String projectID(String wsPart) {
        try {

            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbProject.onUpgrade(dbProject.getWritableDatabase(), 1, 2);
            final InputStream is = hc.getInputStream();
            //  final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;


            JSONArray newarray;
            String s;

            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();

                newarray = new JSONArray(s);
                if(newarray!=null){
                    projectSearchkey=new String( newarray.getString(1).getBytes(), "UTF-8");
                    projectId= newarray.getString(0);
                    projectName = newarray.getString(2);


                }

                dbProject.createprojectSpinner(projectSearchkey,projectId,projectName);
            }




        } catch (final Exception e) {

        }

        return null;
    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- nationality  --------------------------------------
//    public String nationality(String wsPart) {
//        try {
//
//            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
//            hc.connect();
//            dbNationality.onUpgrade(dbNationality.getWritableDatabase(), 1, 2);
//            final InputStream is = hc.getInputStream();
//            //  final StringBuilder sb = new StringBuilder();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            String line = reader.readLine() ;
//
//
//            JSONArray newarray;
//            String s;
//
//            StringTokenizer st = new StringTokenizer(line,"#*#");
//            while (st.hasMoreTokens()) {
//                s=st.nextToken();
//
//                newarray = new JSONArray(s);
//                if(newarray!=null){
//                    isoCountrycode=new String( newarray.getString(1).getBytes(), "UTF-8");
//                    nationalityId= newarray.getString(0);
//                    countryName = newarray.getString(2);
//
//
//                }
//
//                dbNationality.createcountrySpinner(isoCountrycode,nationalityId,countryName);
//            }
//
//
//
//
//        } catch (final Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
    // ----------------------------------------------------------------------------------------------
    // ------------------------------------------- related lead --------------------------------------
    public String relatedLead(String wsPart) {
        try {


            final HttpURLConnection hc = createConnectionact(wsPart, "GET");
            hc.connect();
            dbRLead.onUpgrade(dbRLead.getWritableDatabase(), 1, 2);
            final InputStream is = hc.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = reader.readLine() ;
            String f1;
            JSONArray newarray;
            String s;
            StringTokenizer st = new StringTokenizer(line,"#*#");
            while (st.hasMoreTokens()) {
                s=st.nextToken();
                newarray = new JSONArray(s);
                if(newarray!=null){
                    rLeadId = newarray.getString(0);
                    rLeadName= newarray.getString(1);
                }
                dbRLead.createrLeadId(rLeadName,rLeadId);
            }

        } catch (final Exception e) {

        }

        return null;
    }

    // -------------------------------------end of spinners ---------------------------------------------

    // -------------------------------start of notification _ activity----------------------------------------
    public HttpURLConnection createConnectionnotif(String wsPart, String method) throws Exception {
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

    public String  GetRequest(String wsPart) {
        try {

            final HttpURLConnection hc = createConnectionnotif(wsPart, "GET");
            hc.connect();

            final InputStream is = hc.getInputStream();
            final StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }


            converterNotif(sb.toString());

            // -----------------------------------------------------------------------------------------


        } catch (final Exception e) {

        }

        return null;
    }


    public void converterNotif(String content)throws XmlPullParserException, IOException {
        dbActNotif.onUpgrade(dbActNotif.getWritableDatabase(), 1, 2);

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        // Log.d("aaa",content);

        xpp.setInput( new StringReader( content) );
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {

            }else if(eventType == XmlPullParser.END_TAG) {

            }else if(eventType == XmlPullParser.START_TAG) {
                actnotifName =xpp.getName();


            } else if(eventType == XmlPullParser.TEXT) {

                if (actnotifName.equals("id")&&!xpp.getText().startsWith("\n")) {
                    actNotifId=xpp.getText();

                }
                if (actnotifName.equals("notificationSubject")&&!xpp.getText().startsWith("\n")) {
                    //  xx[i] = xpp.getText();

                    notifSubject=xpp.getText();


                }
                if (actnotifName.equals("notificationDesc")&&!xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();

                    notifDesc=xpp.getText();



                }

                if (actnotifName.equals("updated")&&!xpp.getText().startsWith("\n")) {
                    updatedDate=xpp.getText();

                }
                if (actnotifName.equals("creationDate")&&!xpp.getText().startsWith("\n")) {
                    createdDate=xpp.getText();



                }
                if (actnotifName.equals("sentflag")&&!xpp.getText().startsWith("\n")) {
                    //   xx[i] = xpp.getText();
                    sentFlag =xpp.getText();

                    if(sentFlag.equals("false"))
                        dbActNotif.createActNotif(actNotifId,notifSubject,notifDesc,sentFlag,updatedDate, createdDate);

                }

            }
            eventType = xpp.next();
        }


    }
// ------------------------------------------------- end of activity notification -------------------------------------------
private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager
            = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}



}


