package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Leads;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main4Activity extends AppCompatActivity implements View.OnClickListener {

    public static final String CallPREFERENCES = "CallPrefs" ;
    String activityName ="";
    String startDate="";
    String startHour="";
    String startMin ="";
    String Duration="";
    String activityName_ ="";
    String startDate_="";
    String startHour_="";
    String startMin_ ="";
    String Duration_="";

    SharedPreferences sharedpreferencesCall;
    List<leadsModel> leadsModels;
    Date date;
    Date date1;
    static long start_time, end_time;
    // ----------------------------call record -----------------------------------------

    public static leadsModel l= null;
    android.media.MediaRecorder m_recorder;
    TelephonyManager t_manager ;
    PhoneStateListener p_listener ;

    private static String mFileName = null;
    static final int BUFFER_SIZE = 4096;

    public static boolean openedit = false;
    public boolean recordstarted = false;

    public static String idleadslocal;
    // -------------------------------------------------------------------------------------------
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    // ----------------------fab button ------------------------------------

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2, fab3;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    public static Boolean fabcasesFlag, editleadflag,tabbed =false;

    String leadName;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Fragment fragment = null ;
        // ------------ set title of activiy- -------------------
        Intent i = getIntent();
        leadName = i.getStringExtra("CommertialName");
        if (leadName!=null) {
            this.setTitle(leadName);
        }else if (fabcasesFlag==true || lead_cases_adapter.tabbedcaselead==true){
            leadName = i.getStringExtra("leadname");
             fragment = new lead_related_activity();

            this.setTitle(leadName);
        }else if (editleadflag==true){
            this.setTitle(leads_form.commerialName.getText().toString());
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
            Intent inte = new Intent(getApplicationContext(), insertUrl.class);
            startActivity(inte);
            finish();
        }


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // --------------------------- fab button -----------------------------------------------------
      //  fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab3 = (FloatingActionButton)findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
       // fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);

    }
    // ------------------ fab button action ----------------------------------------------------------------

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
//            case R.id.fab:
//
//                animateFAB();
//
//                break;
            case R.id.fab1:
                Intent intent2 = new Intent(this,acitvity.class);
                intent2.putExtra("leadidid", lead_info_activity.leadid);
                intent2.putExtra("ComName", lead_info_activity.comName);
                startActivity(intent2);
                finish();
                fabcasesFlag=true;
                MainActivity.fabFlag=true;
                Main5Activity.fabactivitiesFlag=false;
                Main5Activity.editflag=false;
                MainActivity.listFlag=false;
                MainActivity.navFlag=false;
                MainActivity.casesflag=false;
                lead_cases_adapter.tabbedcaselead=false;

                break;
            case R.id.fab2:
                Intent intent = new Intent(this,cases.class);
                intent.putExtra("leadidid", lead_info_activity.leadid);
                intent.putExtra("ComName", lead_info_activity.comName);
                startActivity(intent);
                finish();
                fabcasesFlag=true;
                MainActivity.fabFlag=true;
                Main5Activity.fabactivitiesFlag=false;
                Main5Activity.editflag=false;
                MainActivity.casesflag=true;
                MainActivity.listFlag=false;
                MainActivity.navFlag=false;
                lead_cases_adapter.tabbedcaselead=false;

                break;
            case R.id.fab3:
                Intent intent1 = new Intent(this,opportunity.class);
                intent1.putExtra("leadidid", lead_info_activity.leadid);
                intent1.putExtra("ComName", lead_info_activity.comName);
                startActivity(intent1);
                finish();
                fabcasesFlag=true;
                MainActivity.fabFlag=true;
                Main5Activity.fabactivitiesFlag=false;
                Main5Activity.editflag=false;
                MainActivity.listFlag=false;
                MainActivity.navFlag=false;
                MainActivity.casesflag=false;
                lead_oppo_adapter.tabbedoppolead=false;
                MainActivity.listFlag=false;
                MainActivity.opportunityflag=false;
                lead_cases_adapter.tabbedcaselead=false;

                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

          //  fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);

            isFabOpen = false;

        } else {

          //  fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);

            isFabOpen = true;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.edit) {


            Intent intent = getIntent();

            if ( openedit==true) {
                idleadslocal = intent.getStringExtra("id");
            }else{
                String s = idleadslocal;
                if (idleadslocal==null || !idleadslocal.equals(s)) {
                    idleadslocal = intent.getStringExtra("id");
                }

            }

            editleadflag=true;
            openedit=false;
            Main4Activity.fabcasesFlag=false;
            Main5Activity.fabactivitiesFlag=false;
            MainActivity.fabFlag=false;
            MainActivity.listFlag=false;
            MainActivity.navFlag=false;
            Main5Activity.editflag=false;
            lead_cases_adapter.tabbedcaselead=false;

            JCGSQLiteHelper_Leads dblead = new JCGSQLiteHelper_Leads(this);

            leadsModel model = null;
            int t = 0;
             if(idleadslocal!=null) 
                 t = Integer.parseInt(idleadslocal);
            
                 model = dblead.readLead(t);
             
            String CommertialName= model.getCommertialName();
            String phone= model.getPhone();
            String email= model.getEmail();
            String comment = model.getComment();
            String leadsource = model.getLeadSource();
            String currentaddress= model.getEm_Opcrm_Current_Address();
            String assignedto = model.getAssignedTo();
            String leadstatus= model.getStatus();
            String interestIn= model.getInterestedIn();
            String leadid= model.getLeadId();
            String title= model.getTitle();
            String round= model.getEm_Opcrm_Round_id();
            String project= model.getEm_Opcrm_Project_id();
            String nationalty= model.getNationality();
            String assignedtoid= model.getAssignedToID();


            Intent i = new Intent(this,leads_form.class);

            i.putExtra("leadidid", lead_info_activity.leadid);
            i.putExtra("ComName", lead_info_activity.comName);
            i.putExtra("id", idleadslocal +"");
            i.putExtra("CommertialName",CommertialName);
            i.putExtra("phone",phone);
            i.putExtra("email",email);
            i.putExtra("comment",comment);
            i.putExtra("leadsource",leadsource);
            i.putExtra("currentaddress",currentaddress);
            i.putExtra("assignedto",assignedto);
            i.putExtra("leadstatus",leadstatus);
            i.putExtra("interestIn",interestIn);
            i.putExtra("leadid",leadid);
            i.putExtra("title",title);
            i.putExtra("round",round);
            i.putExtra("project",project);
            i.putExtra("nationalty",nationalty);
            i.putExtra("AssignedToID",assignedtoid);

            startActivity(i);
            finish();
        }
        else if (id == android.R.id.home) {

            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Window","1");
            startActivity(i);
            finish();
            openedit=true;
            Main4Activity.editleadflag=false;
            return true;
        }

    //    openedit=false;

      //  return super.onOptionsItemSelected(item);
        return true;
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.lead_profile_activity, container, false);
           // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
           // textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            Fragment fragment = null;
            if (position == 0) {
                fragment = new lead_info_activity();
            }
            else if (position == 1) {
                fragment = new lead_related_activity();
            }
           else if (position == 2) {
                fragment = new lead_related_case();
            }
           else if (position == 3) {
                fragment = new lead_related_oppo();
            }
//          else  if (position == 4) {
//                fragment = new lead_related_document();
//            }

            //return PlaceholderFragment.newInstance(position + 1);

            return fragment;
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    if(LoginActivity.arabicflag==true ){
                        return "بيانات";
                    }
                    else if(insertUrl.arflagURL==true ) {
                        return "بيانات";
                    }else{
                        return "Info";
                    }
                case 1:
                    if(LoginActivity.arabicflag==true ){
                        return "النشاط";
                    }
                    else if(insertUrl.arflagURL==true ) {
                        return "النشاط";
                    }else {
                        return "Activity";
                    }

                case 2:
                    if(LoginActivity.arabicflag==true ){
                        return "الحالات";
                    }
                    else if(insertUrl.arflagURL==true ) {
                        return "الحالات";
                    }else {
                        return "Cases";
                    }

                case 3:
                    if(LoginActivity.arabicflag==true ){
                        return "الفرص";
                    }
                    else if(insertUrl.arflagURL==true ) {
                        return "الفرص";
                    }else {
                        return "Oppo";
                    }
//                case 4:
//                    return "Document";
            }
            return null;
        }
    }


    @Override
    public void onBackPressed() {
        Main4Activity.editleadflag=false;
        openedit=true;
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Window","1");
        startActivity(i);
        finish();

    }



    // send Record  connection
    public String sendRecord(String content) {
        try {

            File uploadFile = new File(mFileName);
            HttpURLConnection conn = createConnectionRecord(content, "POST");
            conn.setRequestProperty("pathName", uploadFile.getPath());
            // sending  an id of this object
            //    conn.setRequestProperty("activityID", actID);

            String str = new String(uploadFile.getName().getBytes(), "UTF-8");

            System.out.println(str);
            conn.connect();
            // -------------------------------------------
            ArrayList<Object> list=new ArrayList<Object>();
            list.add(0,str);
            JSONArray jsArray = new JSONArray(list);

            String s=jsArray.toString();
            //-----------------------------------------

            FileInputStream inputStream = new FileInputStream(uploadFile);


            System.out.println("File to upload: " + mFileName);

            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(s.getBytes());
            // Opens input stream of the file for reading data
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            System.out.println("Start writing data...");

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            System.out.println("Data was written.");
            inputStream.close();
            outputStream.flush();
            outputStream.close();

            //-----------------------------------------
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

    @Override
    public void onPause() {
        super.onPause();
//        if (m_recorder != null) {
//            m_recorder.release();
//            m_recorder = null;
//        }

    }
    // ---------------------------------- record ----------------------------------
    private class AsyncCallWSRecord extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            // ----- sending a record -----------
            sendRecord("/ws/it.openia.crm.savingRecord?");


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
    public HttpURLConnection createConnectionRecord(String wsPart, String method) throws Exception {
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

    // -------------------------------------------------------------------------
// -------------------------insert new activity -------------------------------------------

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


    /// insert new Activity
    public String testInsert(String content) {
        try {
            activityName_ = sharedpreferencesCall.getString("activityName", null);
            startDate_ = sharedpreferencesCall.getString("startDate", null);
            startHour_ = sharedpreferencesCall.getString("startHour", null);
            startMin_ = sharedpreferencesCall.getString("startMin", null);
            Duration_ = sharedpreferencesCall.getString("Duration", null);
            HttpURLConnection conn = createConnection( content,"POST");
            conn.connect();
            ArrayList<Object> list=new ArrayList<Object>();
            list.add(0,activityName_);
            list.add(1,leadsModels.get(0).getLeadId());
            list.add(2,startDate_);
            list.add(3,startHour_);
            list.add(4,startMin_);
            list.add(5,Duration_);
            list.add(6,"CALL");
            list.add(7,"done");
            list.add(8,"");

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
}
