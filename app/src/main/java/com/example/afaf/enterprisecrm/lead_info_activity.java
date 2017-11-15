package com.example.afaf.enterprisecrm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper;
import com.example.afaf.enterprisecrm.Helper_Database.JCGSQLiteHelper_Leads;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.callRecord_helper;
import com.example.afaf.enterprisecrm.Helper_Database.leadStatus_spinner_helper;
import com.example.afaf.enterprisecrm.Helper_Database.ActivityModel;
import com.example.afaf.enterprisecrm.Helper_Database.assignedTo_spinner_model;
import com.example.afaf.enterprisecrm.Helper_Database.callRecord_Model;
import com.example.afaf.enterprisecrm.Helper_Database.leadStatus_spinner_model;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by enterprise on 24/09/16.
 */
public class lead_info_activity extends Fragment {

    public static ActivityModel Em= new ActivityModel();

    // ---------------------------------
    public static String actID= null;
    public static String  leadid= null;
    public  static boolean leadflag= false;
    public static   String comName;


    //------------------- url -------------------------------------------------------------
    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedpreferences;
    String uRl="";
    // ----------------------- log in ------------------------------------------------------
    public static final String LoginPREFERENCES = "LoginPrefs" ;

    SharedPreferences sharedpreferencesLogin;
    String uname="";
    String passw="";
    String userId="";
    // ----------------------------call record -----------------------------------------

    public static leadsModel l= null;
    android.media.MediaRecorder m_recorder;
    TelephonyManager t_manager ;
    PhoneStateListener p_listener ;

    private static String mFileName = null;
    static final int BUFFER_SIZE = 4096;

    public boolean wasRinging = false;
    public boolean recordstarted = false;
    String activityName ="";
    String startDate="" ;
    String startHour="";
    String startMin ="";
    String Duration="";
    String uuid="";
    String relatedLead = "";
    static long start_time, end_time;
    public  static String callFlag ="";



    // ------------------------------------------------------------------------------------
public static EditText commerialName ,phone,email,comment,currentAddress, leadSource, assignedTo, status;
    ImageView telephone, mail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View rootview = inflater.inflate(R.layout.lead_profile_activity, container, false);


        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //-----------------------------url &  login  ------------------------------------------
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uRl= sharedpreferences.getString("URL",null);

        if (uRl != null) {
            sharedpreferencesLogin = getActivity().getSharedPreferences(LoginPREFERENCES, Context.MODE_PRIVATE);
            uname = sharedpreferencesLogin.getString("uName", null);
            passw = sharedpreferencesLogin.getString("passWord", null);
            userId=sharedpreferencesLogin.getString("userId", null);
            if (uname == null && passw == null) {
                Intent i1 = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                startActivity(i1);
                getActivity().finish();

            }
        }
        else{
            Intent i = new Intent(getActivity().getApplicationContext(), insertUrl.class);
            startActivity(i);
            getActivity().finish();

        }

        // --------------------------------------------------------------------------
        commerialName= (EditText) view.findViewById(R.id.editText4);
        phone= (EditText) view.findViewById(R.id.editText2);
        email= (EditText) view.findViewById(R.id.editText3);
        comment= (EditText) view.findViewById(R.id.editText6);
        currentAddress= (EditText) view.findViewById(R.id.editText1);

        leadSource = (EditText) view.findViewById(R.id.editText5);
        assignedTo = (EditText) view.findViewById(R.id.editText7);
        status = (EditText) view.findViewById(R.id.editText6);
// --------------------------------------- sending email -----------------------------------------------
        mail= (ImageView) view.findViewById(R.id.mail);
        mail.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, email.getText().toString());
                emailIntent.setType("text/plain");
                startActivity(emailIntent);
            }
        });

        Intent i = getActivity().getIntent();

            leadflag = true;
            String lid = i.getStringExtra("leadid");
            leadid = lid;

            List<leadsModel> emlist;
            leadsModel em;
            JCGSQLiteHelper_Leads dbLead = new JCGSQLiteHelper_Leads(getActivity());
            emlist = dbLead.getLeadWithId(leadid);
        if (Main4Activity.editleadflag==true){

            commerialName.setText(leads_form.commerialName.getText()+"");

            phone.setText(leads_form.phone.getText()+"");

            email.setText(leads_form.email.getText()+"");

            currentAddress.setText(leads_form.currentAddress.getText()+"");

            comment.setText(leads_form.comment.getText()+"");

            leadSource.setText(leads_form.leadSource.getSelectedItem()+"");

            status.setText(leads_form.status.getSelectedItem()+"");

            assignedTo.setText(leads_form.assignedTo.getSelectedItem()+"");


        }else if (emlist.size()>0){
                em=emlist.get(0);
                String idName = i.getStringExtra("activityId");
                actID = idName;

                comName = em.getCommertialName();
                try {
                    final String s = new String(comName.getBytes(), "UTF-8");
                    commerialName.setText(s);
                } catch (UnsupportedEncodingException e) {

                }
                String phnum = em.getPhone();
                phone.setText(phnum);

                String mail = em.getEmail();
                email.setText(mail);

                String address = em.getCurrentAddress();
                currentAddress.setText(address);
              status.setText("66565");

                String coment = em.getComment();
                try {
                    final String s1 = new String(coment.getBytes(), "UTF-8");
                    comment.setText(s1);
                } catch (UnsupportedEncodingException e) {

                }

                String leadsource = em.getLeadSource();
                leadSource.setText(leadsource);

            final String leadstatus = em.getStatus();
            leadStatus_spinner_helper dbLS = new leadStatus_spinner_helper(getActivity());
            leadStatus_spinner_model leadStatusLists1 = null;
            for (int ii = 0; ii <dbLS.getAllLeadStatus().size(); ii++) {
                try {
                    leadStatusLists1= dbLS.readLeadStatusSpinner(ii+1);
                } catch (JSONException e) {

                      }
                if (leadStatusLists1.getLeadStatusValue().equals(leadstatus))
                    status.setText(leadStatusLists1.getLeadStatusName());
                break;
            }


            String assigned = em.getAssignedToID();

            assignedTo_spinner_helper dba = new assignedTo_spinner_helper(getActivity());
            assignedTo_spinner_model assignedLists = null;
            for (int ii = 0; ii < dba.getAllAssignedTo().size(); ii++) {
                try {
                    assignedLists = dba.readAssignedToSpinner(ii+1);
                } catch (JSONException e) {

                }
                if (assignedLists.getUserId().equals(assigned))
                    assignedTo.setText(assignedLists.getUserName());
                break;
            }

            }

// ---------------------------------- action on call -----------------------------------------------------

                telephone = (ImageView) view.findViewById(R.id.imgedit1);
                telephone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callFlag = "outcome";
                        // get name for record
                        final callRecord_helper rc = new callRecord_helper(getActivity());
                        final String subname ="OutCome Call to "+ commerialName.getText().toString();
                        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                        int x = rc.getAllRecords().size() + 1;
                        mFileName = mFileName + "/" + subname + " " +x + ".mp3";

                        String t = phone.getText().toString();
                        JCGSQLiteHelper_Leads leadHelper = new JCGSQLiteHelper_Leads(getActivity());
                        final List<leadsModel> leadsModels = leadHelper.getLeadByPhone(t);
                        relatedLead = leadsModels.get(0).getLeadId();

                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", t, null));
                        try {
                            startActivity(intent);
                            // -----------------------------------------------------------------------------------------------
                            Toast.makeText(getActivity().getBaseContext(),
                                    "Executing Activity",
                                    Toast.LENGTH_LONG).show();

                            t_manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                            p_listener = new PhoneStateListener() {
                                @Override
                                public void onCallStateChanged(int state, String incomingNumber) {
                                    switch (state) {
                                        case (TelephonyManager.CALL_STATE_RINGING):
                                            wasRinging = true;

                                            break;

                                        case (TelephonyManager.CALL_STATE_OFFHOOK):
                                            start_time = System.currentTimeMillis();
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
                                            DateFormat hourFormat = new SimpleDateFormat("HH");
                                            DateFormat minFormat = new SimpleDateFormat("mm");
                                            Date date = new Date();
                                            startDate =dateFormat.format(date);
                                            startHour=hourFormat.format(date);
                                            startMin=minFormat.format(date);

                                            // if (wasRinging == true) {
                                            m_recorder = new MediaRecorder();
                                            m_recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                            m_recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                                            m_recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                                            m_recorder.setOutputFile(mFileName);

                                            try {
                                                m_recorder.prepare();

                                            } catch (IllegalStateException e) {

                                            } catch (IOException e) {

                                            }
                                            try {
                                                m_recorder.start();

                                            } catch (Exception e) {

                                            }
                                            recordstarted = true;
                                            // }

                                            break;
                                        case (TelephonyManager.CALL_STATE_IDLE):
                                            wasRinging = false;
                                            if (recordstarted) {
                                                m_recorder.stop();
                                                m_recorder.release();
                                                Uri file = Uri.fromFile(
                                                        new File(mFileName));
//                                                Toast.makeText(getActivity().getBaseContext(),
//                                                        "record stored at " + file.toString(),
//                                                        Toast.LENGTH_LONG).show();

                                                t_manager.listen(p_listener, PhoneStateListener.LISTEN_NONE);
                                                //  finish();
                                                // -------------------------------inserting it in database ----------------------------------------
                                                callRecord_Model em = new callRecord_Model();
                                                int x = rc.getAllRecords().size() + 1;
                                                em.setRecordName(subname + x);
                                                em.setRecordPath(file.toString());
                                                em.setCashDFlag(" ");

                                                callRecord_helper c = new callRecord_helper(getActivity());
                                                c.insertEvent(em);
                                                // insert new activity
                                                long   end_time = System.currentTimeMillis();
                                                Duration = end_time+"";
                                                activityName = subname+" "+x;
                                                long total_time = end_time - start_time;
                                                uuid = UUID.randomUUID().toString().replaceAll("-", "");
                                                JCGSQLiteHelper h = new JCGSQLiteHelper(getActivity());
                                                h.createActivity(activityName, startDate, startHour, startMin, Duration + "", "CALL", "answer", leadsModels.get(0).getLeadId(),uuid , null, null, null, userId);

                                                AsyncCallWS task = new AsyncCallWS();
                                                task.execute();

                                                AsyncCallWSRecord task1 = new AsyncCallWSRecord();
                                                task1.execute();


                                                recordstarted = false;
                                            }
                                            break;
                                    }
                                }
                            };
                            t_manager.listen(p_listener, PhoneStateListener.LISTEN_CALL_STATE);


                            // ------------------------------
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity().getApplicationContext(), "Could not find an activity to place the call.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    // ---------------------------------- record ----------------------------------
    // send Record  connection

    private class AsyncCallWSRecord extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            // ----- sending a record -----------
            sendRecord("/ws/it.openia.crm.savingRecord?");
         //   testInsert("/ws/it.openia.crm.insertActivity?");


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
    public String sendRecord(String content) {
        try {

            File uploadFile = new File(mFileName);
            HttpURLConnection conn = createConnectionRecord(content, "POST");
            conn.setRequestProperty("pathName", uploadFile.getPath());
            // sending  an id of this object
            conn.setRequestProperty("activityID", uuid);

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


            conn.disconnect();

        } catch (MalformedURLException e) {



        } catch (IOException e) {



        } catch (Exception e) {

        }
        callFlag="";
        return null;


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
    // insert connection
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
    public String testInsert(String content) {
        try {

            long number = Long.parseLong(Duration);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(number);
            DateFormat formatter = new SimpleDateFormat("HH:mm");
            Duration=formatter.format(calendar.getTime())+"";
            HttpURLConnection conn = createConnection( content,"POST");
            conn.connect();
            ArrayList<Object> list=new ArrayList<Object>();
            list.add(0,activityName);
            list.add(1,relatedLead);
            list.add(2,startDate);
            list.add(3,startHour+":"+startMin);
            list.add(4,Duration);
            list.add(5,"CALL");
            list.add(6,"answer");
            list.add(7,"opcrmContacted");
            list.add(8,"");
            list.add(9,userId);
            list.add(10,uuid);
            JSONArray jsArray = new JSONArray(list);
            OutputStream os = conn.getOutputStream();

            String s=jsArray.toString();
            os.write(s.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {

            }

            conn.disconnect();

        } catch (MalformedURLException e) {



        } catch (IOException e) {


        } catch (Exception e) {

        }

        return null;



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
}
